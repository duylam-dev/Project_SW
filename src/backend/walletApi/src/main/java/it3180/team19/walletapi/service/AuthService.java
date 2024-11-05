package it3180.team19.walletapi.service;

import feign.FeignException;
import it3180.team19.walletapi.Exception.AppException;
import it3180.team19.walletapi.Util.EncryptBase64;
import it3180.team19.walletapi.domain.BankUser;
import it3180.team19.walletapi.domain.User;
import it3180.team19.walletapi.domain.Wallet;
import it3180.team19.walletapi.dto.Identity.Credential;
import it3180.team19.walletapi.dto.request.RegistrationRequest;
import it3180.team19.walletapi.dto.Identity.TokenExchangeParam;
import it3180.team19.walletapi.dto.Identity.UserCreationParam;
import it3180.team19.walletapi.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final IdentityClient identityClient;


    @Value("${idp.client.id}")
    private String idpClientId;
    @Value("${idp.client.secret}")
    private String idpClientSecret;


    public void register(RegistrationRequest registrationRequest) {
        ResponseEntity<?> creationResponse = null;
        String userId = "";
        if (userRepository.existsById(registrationRequest.getPhoneNumber())) {
            log.info("Phone number already exists");
            throw new AppException(HttpStatus.CONFLICT.value(), "Phone number already exists");
        }
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .client_id(idpClientId)
                    .client_secret(idpClientSecret)
                    .scope("openid")
                    .grant_type("client_credentials")
                    .build());
            log.info("TokenInfo {}", token);

            creationResponse = identityClient.createUser(
                    "Bearer " + token.getAccessToken(),
                    UserCreationParam.builder()
                            .username(registrationRequest.getUsername())
                            .firstName(registrationRequest.getFirstName())
                            .lastName(registrationRequest.getLastName())
                            .email(registrationRequest.getEmail())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                            .type("password")
                                            .temporary(false)
                                            .value(registrationRequest.getPassword())
                                            .build()
                                    )
                            )
                            .build());
            userId = extractUserId(creationResponse);
        } catch (FeignException e) {
            String response = e.getLocalizedMessage();
            int startIndex = response.indexOf("\"errorMessage\":\"") + 16;
            String errorMessage = response.substring(startIndex, response.length() - 3);
            throw new AppException(HttpStatus.CONFLICT.value(), errorMessage);
        }
        Wallet wallet = Wallet.builder()
                .accountBalance(0.0)
                .username(EncryptBase64.encrypt(registrationRequest.getUsername()))
                .build();
        User user = User.builder()
                .userId(userId)
                .fullName(registrationRequest.getFirstName() + " " + registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .wallet(wallet)
                .build();
        userRepository.save(user);
    }


    private String extractUserId(ResponseEntity<?> response) {
        String location = Objects.requireNonNull(response.getHeaders().getLocation()).toString();
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

}
