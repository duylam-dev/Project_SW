package it3180.team19.walletapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String password;
    private Double accountBalance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number", referencedColumnName = "phoneNumber")
    private User user;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
}
