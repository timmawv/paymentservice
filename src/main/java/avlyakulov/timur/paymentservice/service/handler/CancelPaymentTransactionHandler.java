package avlyakulov.timur.paymentservice.service.handler;

import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionRequest;
import avlyakulov.timur.paymentservice.service.PaymentTransactionValidator;
import avlyakulov.timur.paymentservice.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CancelPaymentTransactionHandler implements PaymentTransactionCommandHandler{

    private JsonConverter jsonConverter;
    PaymentTransactionValidator validator;

    @Override
    public void process(String requestId, String message) {
        var request = jsonConverter.toObject(message, CancelPaymentTransactionRequest.class);
    }
}
