package avlyakulov.timur.paymentservice.model.entity;

import avlyakulov.timur.paymentservice.model.enums.PaymentTransactionStatus;
import avlyakulov.timur.paymentservice.model.enums.converter.PaymentTransactionStatusConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction extends BaseEntity {

    private BigDecimal amount;
    private String currency;

    @Convert(converter = PaymentTransactionStatusConverter.class)
    private PaymentTransactionStatus paymentTransactionStatus;
    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "sourceBankAccountId")
    private BankAccount sourceBankAccount;

    @ManyToOne
    @JoinColumn(name = "destinationBankAccountId")
    private BankAccount destinationBankAccount;

    @OneToMany(mappedBy = "paymentTransaction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Refund> refunds;

}
