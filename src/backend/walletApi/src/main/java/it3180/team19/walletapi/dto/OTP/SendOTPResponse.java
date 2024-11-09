package it3180.team19.walletapi.dto.OTP;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendOTPResponse {
    int r;
    String call_id;
}
