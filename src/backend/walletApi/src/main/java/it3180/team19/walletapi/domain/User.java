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
public class User {
    @Id
    private String phoneNumber;
    private String fullName;
    private String email;
    private String userId;

    @OneToMany(mappedBy = "user")
    private List<Bill> bills;


    @OneToMany(mappedBy = "user")
    private List<BankUser> bankUsers;

}
