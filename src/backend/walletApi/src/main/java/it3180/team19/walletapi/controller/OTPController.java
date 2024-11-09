package it3180.team19.walletapi.controller;

import com.nimbusds.jose.JOSEException;
import it3180.team19.walletapi.dto.ApiResponse;
import it3180.team19.walletapi.dto.OTP.SendOTPResponse;
import it3180.team19.walletapi.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/otp")
public class OTPController {

    private final OTPService otpService;
    @GetMapping("/getOtp")
    @PreAuthorize("hasRole('transfer-money')")
    public ResponseEntity<ApiResponse<SendOTPResponse>> getOTP(@RequestParam String numberTo) throws JOSEException {
        return ResponseEntity.ok(ApiResponse.<SendOTPResponse>builder()
                        .code(200)
                        .result(otpService.sendOTP(numberTo))
                .build());
    }
}
