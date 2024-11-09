package it3180.team19.walletapi.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransferMoneyRequest {
    int type;
    String bankCode;
    String accountNumber;
    Double amount;
}
