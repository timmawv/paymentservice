package avlyakulov.timur.paymentservice.service.handler;

public interface PaymentTransactionCommandHandler {

    void process(String requestId, String message);
}
