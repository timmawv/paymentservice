package avlyakulov.timur.paymentservice.repository;

import avlyakulov.timur.paymentservice.model.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

}