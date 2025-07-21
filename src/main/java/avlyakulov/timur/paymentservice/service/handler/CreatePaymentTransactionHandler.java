package avlyakulov.timur.paymentservice.service.handler;

import avlyakulov.timur.paymentservice.controller.kafka.PaymentTransactionProducer;
import avlyakulov.timur.paymentservice.dto.CreatePaymentTransactionRequest;
import avlyakulov.timur.paymentservice.mapper.PaymentTransactionMapper;
import avlyakulov.timur.paymentservice.model.entity.BankAccount;
import avlyakulov.timur.paymentservice.model.entity.PaymentTransaction;
import avlyakulov.timur.paymentservice.model.enums.PaymentTransactionCommand;
import avlyakulov.timur.paymentservice.model.enums.PaymentTransactionStatus;
import avlyakulov.timur.paymentservice.service.BankAccountService;
import avlyakulov.timur.paymentservice.service.PaymentTransactionService;
import avlyakulov.timur.paymentservice.service.PaymentTransactionValidator;
import avlyakulov.timur.paymentservice.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler {

    private final JsonConverter jsonConverter;
    private final PaymentTransactionValidator paymentTransactionValidator;
    private final BankAccountService bankAccountService;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentTransactionService paymentTransactionService;
    private final PaymentTransactionProducer paymentTransactionProducer;

    @Override
    public void process(String requestId, String message) {
        var request = jsonConverter.toObject(message, CreatePaymentTransactionRequest.class);
        paymentTransactionValidator.validateCreatePaymentTransactionRequest(request);

        var sourceBankAccount = bankAccountService.findById(request.getSourceBankAccountId()).get();
        sourceBankAccount.setBalance(
                sourceBankAccount.getBalance().subtract(request.getAmount())
        );

        Optional<BankAccount> destinationBankAccount = Optional.empty();
        if (request.getDestinationBankAccountId() != null) {
            destinationBankAccount = bankAccountService.findById(request.getDestinationBankAccountId());
            destinationBankAccount.get().setBalance(
                    destinationBankAccount.get().getBalance().add(request.getAmount())
            );
        }

        PaymentTransaction entity = paymentTransactionMapper.mapToEntity(request);
        entity.setSourceBankAccount(sourceBankAccount);
        destinationBankAccount.ifPresent(entity::setDestinationBankAccount);
        entity.setPaymentTransactionStatus(PaymentTransactionStatus.SUCCESS);

        //todo here make response
        PaymentTransaction savedEntity = paymentTransactionService.save(entity);

        if (destinationBankAccount.isPresent())
            bankAccountService.saveAll(List.of(sourceBankAccount, destinationBankAccount.get()));
        else
            bankAccountService.saveAll(List.of(sourceBankAccount));

        paymentTransactionProducer.sendCommandResult(requestId, jsonConverter.toJson(savedEntity), PaymentTransactionCommand.CREATE);
    }
}