package it3180.team19.walletapi.controller;

import it3180.team19.walletapi.dto.ApiResponse;
import it3180.team19.walletapi.dto.request.RegistrationRequest;
import it3180.team19.walletapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    ApiResponse<Void> register(@RequestBody RegistrationRequest request) {
        authService.register(request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Success")
        .build();
    }
}
