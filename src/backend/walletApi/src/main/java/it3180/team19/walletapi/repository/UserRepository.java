package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.domain.User;
import it3180.team19.walletapi.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserRepository extends JpaRepository<User, String> {
    User findByUserId(String userId);
    @Query("select w from User u inner join Wallet w on u.wallet.id = w.id where u.userId = :userId")
    Wallet getWalletByUserId(@Param("userId") String userId);

    @Query("select w from User u inner join Wallet w on u.wallet.id = w.id where u.phoneNumber = :phoneNumber")
    Wallet getWalletByPhoneNumber(@Param("phoneNumber") String phoneNumber);


    User findByWalletId(long wallet_id);
}
