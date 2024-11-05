package it3180.team19.walletapi.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private Double accountBalance;

    @OneToMany(mappedBy = "wallet")
    private List<Transaction> transactions;
}
