package it3180.team19.walletapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String accountNumberReceiver;
    private Double cost;
    private String content;
    private Instant dateTime;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
