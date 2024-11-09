package it3180.team19.walletapi.service;

import com.nimbusds.jose.JOSEException;
import it3180.team19.walletapi.Util.SecurityUtil;
import it3180.team19.walletapi.dto.OTP.SendOTPRequest;
import it3180.team19.walletapi.dto.OTP.SendOTPResponse;
import it3180.team19.walletapi.repository.StringeeServer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class OTPService {
    private final StringeeServer otpService;
    private final SecurityUtil securityUtil;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Value("${otp.number}")
    private String numberSendOTP;

    public SendOTPResponse sendOTP(String numberTo) throws JOSEException {
        String otp = generateOTP();
        redisTemplate.opsForValue().set("otpValid", otp);
        return otpService.sendOTP("Bearer " + securityUtil.generateToken(),
                SendOTPRequest.builder()
                        .from(SendOTPRequest.formatFromTo.builder()
                                .type("external")
                                .number(numberSendOTP)
                                .alias("wallet_otp")
                                .build())
                        .to(Collections.singletonList(SendOTPRequest.formatFromTo.builder()
                                .type("external")
                                .number("84" + numberTo.substring(1))
                                .alias("to_number")
                                .build()))
                        .actions(Collections.singletonList(SendOTPRequest.formatAction.builder()
                                .action("talk")
                                .text("Mã otp code của bạn là " + otp)
                                .loop("2")
                                .build()))
                        .build()
        );
    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            otp.append(digit).append("   ");
        }
        return otp.toString();
    }

}
