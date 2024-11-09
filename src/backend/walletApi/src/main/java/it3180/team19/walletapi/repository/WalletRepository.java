package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

}
