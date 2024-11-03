package it3180.team19.walletapi.service;

import feign.FeignException;
import it3180.team19.walletapi.Util.EncryptBase64;
import it3180.team19.walletapi.domain.User;
import it3180.team19.walletapi.domain.Wallet;
import it3180.team19.walletapi.dto.Identity.Credential;
import it3180.team19.walletapi.dto.request.RegistrationRequest;
import it3180.team19.walletapi.dto.Identity.TokenExchangeParam;
import it3180.team19.walletapi.dto.Identity.UserCreationParam;
import it3180.team19.walletapi.repository.IdentityClient;
import it3180.team19.walletapi.repository.UserRepository;
import it3180.team19.walletapi.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final IdentityClient identityClient;


    @Value("${idp.client.id}")
    private String idpClientId;
    @Value("${idp.client.secret}")
    private String idpClientSecret;


    public void register(RegistrationRequest registrationRequest) {
        try {
            var token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .client_id(idpClientId)
                    .client_secret(idpClientSecret)
                    .scope("openid")
                    .grant_type("client_credentials")
                    .build());
            log.info("TokenInfo {}", token);

            var creationResponse = identityClient.createUser(
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
                                    .build()))
                            .build());
            String userId = extractUserId(creationResponse);
            User user = User.builder()
                    .userId(userId)
                    .fullName(registrationRequest.getFirstName() + " " + registrationRequest.getLastName())
                    .email(registrationRequest.getEmail())
                    .phoneNumber(registrationRequest.getPhoneNumber())
                    .build();

            Wallet wallet = Wallet.builder()
                    .accountBalance(0.0)
                    .username(EncryptBase64.encrypt(registrationRequest.getUsername()))
                    .user(user)
                    .build();

            walletRepository.save(wallet);
        }catch (FeignException e) {
            e.printStackTrace();
        }
    }
    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().getLocation().toString();
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
    }

}
