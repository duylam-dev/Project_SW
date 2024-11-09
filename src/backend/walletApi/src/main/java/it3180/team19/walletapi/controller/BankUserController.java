package it3180.team19.walletapi.controller;


import it3180.team19.walletapi.dto.ApiResponse;
import it3180.team19.walletapi.dto.request.BankUserCreationRequest;
import it3180.team19.walletapi.service.BankUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/bankUser")
public class BankUserController {
    private final BankUserService bankUserService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('save-bank')")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody BankUserCreationRequest request) {
        bankUserService.saveBankUsers(request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(ApiResponse.<Void>builder()
                .code(HttpStatus.CREATED.value())
                .message("Success")
                .build()
        );
    }
}
