package avlyakulov.timur.paymentservice.mapper;

import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionRequest;
import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionResponse;
import avlyakulov.timur.paymentservice.model.entity.Refund;
import org.mapstruct.Mapper;

@Mapper
public interface RefundMapper {

    Refund toEntity(CancelPaymentTransactionRequest request);
    CancelPaymentTransactionResponse toResponse(Refund refund);
}
