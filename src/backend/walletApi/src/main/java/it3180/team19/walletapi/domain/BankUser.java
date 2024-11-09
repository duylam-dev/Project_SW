package it3180.team19.walletapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String numberAccount;
    private Double balance;
    @ManyToOne
    @JoinColumn(name = "bank_id")

    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "phone_number")
    private User user;
}
