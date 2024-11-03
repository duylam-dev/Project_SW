package it3180.team19.walletapi.dto.Identity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenExchangeResponse {
    String accessToken;
    String expiresIn;
    String refreshExpiresIn;
    String tokenType;
    String idToken;
    String scope;
}
