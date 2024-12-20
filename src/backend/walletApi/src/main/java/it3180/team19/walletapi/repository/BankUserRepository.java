package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.domain.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankUserRepository extends JpaRepository<BankUser, Long> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM BankUser a WHERE a.numberAccount = :number AND a.bank.code = :code")
    boolean existByNumberAccountAndCode(@Param("number") String number, @Param("code") String code);

    BankUser findByNumberAccountAndBankCode(String number, String bankCode);

}

