package avlyakulov.timur.paymentservice.dto;

import avlyakulov.timur.paymentservice.dto.enums.CommandResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CancelPaymentTransactionResponse {

    private Long refundId;
    private CommandResultStatus status;
    private String errorMessage;
}