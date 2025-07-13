package avlyakulov.timur.paymentservice.dto.enums;

import avlyakulov.timur.paymentservice.model.enums.PaymentTransactionStatus;
import lombok.Getter;

@Getter
public enum CommandResultStatus {

    SUCCESS,
    FAILED;

    public static CommandResultStatus fromString(String status) {
        for (CommandResultStatus commandResultStatus : CommandResultStatus.values())
            if (commandResultStatus.toString().equalsIgnoreCase(status))
                return commandResultStatus;
        throw new IllegalArgumentException("Invalid PaymentTransactionStatus: " + status);
    }
}