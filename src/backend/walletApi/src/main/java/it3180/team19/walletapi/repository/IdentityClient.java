package it3180.team19.walletapi.repository;

import feign.QueryMap;
import it3180.team19.walletapi.dto.Identity.TokenExchangeParam;
import it3180.team19.walletapi.dto.Identity.TokenExchangeResponse;
import it3180.team19.walletapi.dto.Identity.UserCreationParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "identity-client", url = "${idp.url}")
public interface IdentityClient {
    @PostMapping(
            value = "/realms/WalletLogin/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    TokenExchangeResponse exchangeToken(@QueryMap TokenExchangeParam param);

    @PostMapping(value = "/admin/realms/WalletLogin/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createUser(@RequestHeader("authorization") String token, @RequestBody UserCreationParam param);
}
