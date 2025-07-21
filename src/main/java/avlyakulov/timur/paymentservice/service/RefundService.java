package avlyakulov.timur.paymentservice.service;

import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionRequest;
import avlyakulov.timur.paymentservice.dto.CancelPaymentTransactionResponse;
import avlyakulov.timur.paymentservice.mapper.PaymentTransactionMapper;
import avlyakulov.timur.paymentservice.mapper.RefundMapper;
import avlyakulov.timur.paymentservice.model.entity.PaymentTransaction;
import avlyakulov.timur.paymentservice.model.enums.RefundStatus;
import avlyakulov.timur.paymentservice.repository.RefundRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefundService {

    private final PaymentTransactionService paymentTransactionService;
    private final BankAccountService bankAccountService;
    private final RefundMapper refundMapper;
    private final RefundRepository refundRepository;

    @Transactional
    public CancelPaymentTransactionResponse createRefund(CancelPaymentTransactionRequest request,
                                                         PaymentTransaction paymentTransaction) {
        var entity = refundMapper.toEntity(request);
        entity.setPaymentTransaction(paymentTransaction);
        entity.setStatus(RefundStatus.COMPLETED);

        var savedEntity = refundRepository.save(entity);

        savedEntity.setPaymentTransaction(paymentTransaction);
        return refundMapper.toResponse(savedEntity);
    }
}