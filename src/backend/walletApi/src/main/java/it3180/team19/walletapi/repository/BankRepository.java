package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Long> {
    Optional<Bank> findByCode(String code);
}
