package com.javastart.deposit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.deposit.controller.dto.DepositResponseDTO;
import com.javastart.deposit.entity.Deposit;
import com.javastart.deposit.exception.DepositServiceException;
import com.javastart.deposit.repository.DepositRepository;
import com.javastart.deposit.rest.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DepositService {

    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.key.deposit";

    private final DepositRepository depositRepository;
    private final AccountServiceClient accountServiceClient;
    private final BillServiceClient billServiceClient;

    private final RabbitTemplate rabbitTemplate;

    public DepositResponseDTO deposit(Long accountId, Long billId, BigDecimal amount) {
        if (accountId == null && billId == null) {
            throw new DepositServiceException("Account and bill both is null");
        }

        if (billId != null) {
            BillResponseDTO billResponseDTO = billServiceClient.getBillById(billId);
            BillRequestDTO billRequestDTO = new BillRequestDTO();
            setBillRequestDTOWithData(amount, billResponseDTO, billRequestDTO);

            billServiceClient.update(billId, billRequestDTO);
            AccountResponseDTO accountById = accountServiceClient.getAccountById(billResponseDTO.getAccountId());
            depositRepository.save(new Deposit(billId, amount, accountById.getEmail(), OffsetDateTime.now()));
            DepositResponseDTO depositResponseDTO = getDepositResponseDTO(amount, accountById);

            return  depositResponseDTO;
        }

        BillResponseDTO defaultBill = getDefaultBill(accountId);
        BillRequestDTO billRequestDTO = new BillRequestDTO();
        setBillRequestDTOWithData(amount, defaultBill, billRequestDTO);
        billServiceClient.update(defaultBill.getBillId(), billRequestDTO);
        AccountResponseDTO accountById = accountServiceClient.getAccountById(accountId);
        depositRepository.save(new Deposit(defaultBill.getBillId(), amount, accountById.getEmail(), OffsetDateTime.now()));

        DepositResponseDTO depositResponseDTO = getDepositResponseDTO(amount, accountById);

        return  depositResponseDTO;
    }

    private DepositResponseDTO getDepositResponseDTO(BigDecimal amount, AccountResponseDTO accountById) {
        DepositResponseDTO depositResponseDTO = new DepositResponseDTO(accountById.getEmail(), amount);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_DEPOSIT, ROUTING_KEY_DEPOSIT, objectMapper.writeValueAsString(depositResponseDTO));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new DepositServiceException("Can't send message to RabbitMQ");
        }
        return depositResponseDTO;
    }

    private void setBillRequestDTOWithData(BigDecimal amount, BillResponseDTO billResponseDTO, BillRequestDTO billRequestDTO) {
        billRequestDTO.builder().accountId(billResponseDTO.getAccountId())
                .isDefault(billResponseDTO.getIsDefault())
                .creationDate(billResponseDTO.getCreationDate())
                .overdraftEnabled(billResponseDTO.getOverdraftEnabled())
                .amount(billResponseDTO.getAmount().add(amount)).build();
    }

    private BillResponseDTO getDefaultBill(Long accountId) {
        return billServiceClient.getBillsByAccountId(accountId)
                .stream().filter(BillResponseDTO::getIsDefault)
                .findAny()
                .orElseThrow(() -> new DepositServiceException("Doesn't exist default bill for given account: " + accountId));
    }

}
