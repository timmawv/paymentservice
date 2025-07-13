package avlyakulov.timur.paymentservice.service.handler;

import avlyakulov.timur.paymentservice.dto.CreatePaymentTransactionRequest;
import avlyakulov.timur.paymentservice.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreatePaymentTransactionHandler implements PaymentTransactionCommandHandler {

    private final JsonConverter jsonConverter;

    @Override
    public void process(String requestId, String message) {
        var request = jsonConverter.toObject(message, CreatePaymentTransactionRequest.class);

    }
}