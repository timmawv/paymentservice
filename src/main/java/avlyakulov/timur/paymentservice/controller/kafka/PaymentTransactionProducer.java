package avlyakulov.timur.paymentservice.controller.kafka;

import avlyakulov.timur.paymentservice.model.enums.PaymentTransactionCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionProducer {

    public static final String RESULT_TOPIC = "payment-command-result";
    public static final String PAYMENT_TRANSACTION_COMMAND_TYPE_HEADER = "command";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCommandResult(String requestId, String message, PaymentTransactionCommand command) {
        var kafkaMessage = buildMessage(requestId, message, command);
        kafkaTemplate.send(kafkaMessage);
        log.info("Successfully sent command result: {}", kafkaMessage);
    }

    private Message buildMessage(String requestId, String message, PaymentTransactionCommand command) {
        return MessageBuilder.withPayload(message)
                .setHeader(KafkaHeaders.TOPIC, RESULT_TOPIC)
                .setHeader(KafkaHeaders.KEY, requestId)
                .setHeader(PAYMENT_TRANSACTION_COMMAND_TYPE_HEADER, command.toString())
                .build();
    }

}
