package it3180.team19.walletapi.dto.Identity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationParam {
    String username;

    boolean enabled;
    String email;
    boolean emailVerified;
    String firstName;
    String lastName;
    List<Credential> credentials;
}
