package it3180.team19.walletapi.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
public class User {
    @Id
    private Integer phoneNumber;
    private String fullName;
    private String age;
    private String email;

    @OneToOne(mappedBy = "user")
    private Wallet wallet;


    @OneToMany(mappedBy = "user")
    private List<Bill> bills;


    @OneToMany(mappedBy = "user")
    private List<BankUser> bankUsers;

}
