package it3180.team19.walletapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {

    String username;

    String password;

    String email;
    String firstName;
    String lastName;
    String phoneNumber;

}