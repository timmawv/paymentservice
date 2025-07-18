package avlyakulov.timur.paymentservice.exception;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class PaymentTransactionValidationException extends RuntimeException {

    private List<String> errors;
}