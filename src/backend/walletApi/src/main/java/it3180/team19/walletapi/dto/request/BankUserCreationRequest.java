package it3180.team19.walletapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankUserCreationRequest {
    List<Map<String, Object>> infoBank;
    String phoneNumber;
}