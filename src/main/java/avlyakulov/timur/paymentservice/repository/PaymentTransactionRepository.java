package avlyakulov.timur.paymentservice.repository;

import avlyakulov.timur.paymentservice.model.entity.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {

    @Query("SELECT pt from PaymentTransaction  pt left join fetch pt.refunds")
    Optional<PaymentTransaction> findByWithIdRefunds(Long id);
}