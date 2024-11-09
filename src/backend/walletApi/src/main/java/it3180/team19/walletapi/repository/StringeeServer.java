package it3180.team19.walletapi.repository;

import it3180.team19.walletapi.dto.OTP.SendOTPRequest;
import it3180.team19.walletapi.dto.OTP.SendOTPResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "stringee-server", url = "${otp.url}")
public interface StringeeServer {

    @PostMapping("/call2/callout")
    SendOTPResponse sendOTP(@RequestHeader("Authorization") String token, SendOTPRequest smsRequest);
}
