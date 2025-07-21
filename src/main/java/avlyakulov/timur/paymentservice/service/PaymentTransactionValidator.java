package avlyakulov.timur.paymentservice.service;

import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionRequest;
import avlyakulov.timur.paymentservice.dto.CreatePaymentTransactionRequest;
import avlyakulov.timur.paymentservice.exception.PaymentTransactionValidationException;
import avlyakulov.timur.paymentservice.model.entity.BankAccount;
import avlyakulov.timur.paymentservice.model.entity.PaymentTransaction;
import avlyakulov.timur.paymentservice.model.entity.Refund;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionValidator {

    private final Validator validator;
    private final BankAccountService bankAccountService;
    private final PaymentTransactionService paymentTransactionService;

    public void validateCreatePaymentTransactionRequest(CreatePaymentTransactionRequest request) {
        var violations = validator.validate(request);

        List<String> errors = new ArrayList<>(violations.stream()
                .map(ConstraintViolation::getMessage)
                .toList()
        );

        Optional<BankAccount> sourceBank = Optional.empty();
        if (request.getSourceBankAccountId() != null) {
            sourceBank = bankAccountService.findById(request.getSourceBankAccountId());
            if (sourceBank.isEmpty())
                errors.add("Source bank account not found, source account id: " + request.getSourceBankAccountId());
        }

        if (request.getDestinationBankAccountId() != null) {
            var destinationBank = bankAccountService.findById(request.getDestinationBankAccountId());
            if (destinationBank.isEmpty())
                errors.add("Destination bank account not found, source account id: " + request.getSourceBankAccountId());
        }

        if (request.getAmount() != null && sourceBank.isPresent()) {
            if (sourceBank.get().getBalance().compareTo(request.getAmount()) < 0) {
                errors.add("Source bank account balance less than requested amount, source account id: " + request.getSourceBankAccountId());
            }
        }

        if (!errors.isEmpty()) {
            throw new PaymentTransactionValidationException(errors);
        }
    }

    public void validateCancelPaymentTransactionRequest(CancelPaymentTransactionRequest request) {
        var violations = validator.validate(request);

        List<String> errors = new ArrayList<>(
                violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .toList()
        );

        if (request.getTransactionId() != null) {
            var sourceTransaction = paymentTransactionService.findById(request.getTransactionId());
            if (sourceTransaction.isEmpty())
               errors.add("Source transaction not found, transaction id: " + request.getTransactionId());
            else {
                var existedSourceTransaction = sourceTransaction.get();
                var refundedAmount = sourceTransaction.get().getRefunds().stream()
                        .map(Refund::getRefundAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (existedSourceTransaction.getAmount().subtract(refundedAmount).compareTo(request.getRefundedAmount()) < 0) {
                    errors.add("Requested amount for refund bigger than source transaction amount, transaction id " + request.getTransactionId());
                }
            }

            if (!errors.isEmpty()) {
                throw new PaymentTransactionValidationException(errors);
            }
        }
    }
}