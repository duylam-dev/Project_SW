package it3180.team19.walletapi.Exception;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

}
