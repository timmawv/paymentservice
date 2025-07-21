package avlyakulov.timur.paymentservice.service;

import avlyakulov.timur.paymentservice.model.entity.BankAccount;
import avlyakulov.timur.paymentservice.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public Optional<BankAccount> findById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public void saveAll(List<BankAccount> accounts) {
        bankAccountRepository.saveAll(accounts);
    }
}
