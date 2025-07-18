package avlyakulov.timur.paymentservice.mapper;

import avlyakulov.timur.paymentservice.dto.CreatePaymentTransactionRequest;
import avlyakulov.timur.paymentservice.dto.CreatePaymentTransactionResponse;
import avlyakulov.timur.paymentservice.model.entity.PaymentTransaction;
import org.mapstruct.Mapper;

@Mapper
public interface PaymentTransactionMapper {

    PaymentTransaction mapToEntity(CreatePaymentTransactionRequest request);

    CreatePaymentTransactionResponse mapToResponse(PaymentTransaction paymentTransaction);
}