package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {

}
