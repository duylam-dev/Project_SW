package it3180.team19.walletapi.dto.Identity;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenExchangeParam {
    String grant_type;
    String client_id;
    String client_secret;
    String scope;
}