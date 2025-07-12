package avlyakulov.timur.paymentservice.model.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccount extends BaseEntity {

    private String number;
    private BigDecimal balance;
    private Long customerId;
    private String currency;
}
