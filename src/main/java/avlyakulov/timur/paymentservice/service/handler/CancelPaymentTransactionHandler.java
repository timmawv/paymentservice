package avlyakulov.timur.paymentservice.service.handler;

import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionRequest;
import avlyakulov.timur.paymentservice.service.BankAccountService;
import avlyakulov.timur.paymentservice.service.PaymentTransactionService;
import avlyakulov.timur.paymentservice.service.PaymentTransactionValidator;
import avlyakulov.timur.paymentservice.service.RefundService;
import avlyakulov.timur.paymentservice.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentTransactionHandler implements PaymentTransactionCommandHandler{

    private JsonConverter jsonConverter;
    private PaymentTransactionValidator validator;
    private final PaymentTransactionService paymentTransactionService;
    private final BankAccountService bankAccountService;
    private final RefundService refundService;

    @Override
    public void process(String requestId, String message) {
        var request = jsonConverter.toObject(message, CancelPaymentTransactionRequest.class);
        validator.validateCancelPaymentTransactionRequest(request);

        var sourceTransaction = paymentTransactionService.findById(request.getTransactionId()).get();
        var sourceBankAccount = sourceTransaction.getSourceBankAccount();
        sourceBankAccount.setBalance(
                sourceBankAccount.getBalance().add(request.getRefundedAmount())
        );

        if (sourceTransaction.getDestinationBankAccount() != null) {
            var destinationBankAccount = sourceTransaction.getDestinationBankAccount();
            destinationBankAccount.setBalance(
                    destinationBankAccount.getBalance().add(request.getRefundedAmount())
            );
        }

        refundService.createRefund(request, sourceTransaction);
    }
}
