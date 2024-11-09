package it3180.team19.walletapi.dto.OTP;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SendOTPRequest {
    formatFromTo from;
    List<formatFromTo> to;
    List<formatAction> actions;

    @Data
    @Builder
    public static class formatFromTo{
        String type;
        String number;
        String alias;
    }


    @Data
    @Builder
    public static class formatAction{
        String action;
        String text;
        String loop;
    }
}
