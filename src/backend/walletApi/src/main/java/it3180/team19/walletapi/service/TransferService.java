package it3180.team19.walletapi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;

import it3180.team19.walletapi.Exception.AppException;
import it3180.team19.walletapi.Util.SecurityUtil;
import it3180.team19.walletapi.domain.BankUser;
import it3180.team19.walletapi.domain.Wallet;
import it3180.team19.walletapi.dto.request.TransferMoneyRequest;

import it3180.team19.walletapi.repository.BankUserRepository;
import it3180.team19.walletapi.repository.UserRepository;
import it3180.team19.walletapi.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransferService {
    private final BankUserRepository bankUserRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final RedisTemplate<Object, Object> redisTemplate;
    @Transactional
    public Map<String, Object> handlePreTransfer(TransferMoneyRequest request) throws JOSEException {
        final String userIdLogin = SecurityUtil.getCurrentUserLogin().orElse(null);
        Wallet walletUserFrom = userRepository.getWalletByUserId(userIdLogin);
        Object userTo;
        String numberTo;
        if (request.getType() == 0) {
            if (!bankUserRepository.existByNumberAccountAndCode(request.getAccountNumber(), request.getBankCode())) {
                throw new AppException(HttpStatus.NOT_FOUND.value(), "Account number not exist!");
            }
            userTo = bankUserRepository.findByNumberAccountAndBankCode(request.getAccountNumber(), request.getBankCode());
            numberTo = ((BankUser) userTo).getUser().getPhoneNumber();
        } else {
            userTo = userRepository.getWalletByPhoneNumber(request.getAccountNumber());
            numberTo = userRepository.findByWalletId(((Wallet) userTo).getId()).getPhoneNumber();
        }
        if (walletUserFrom.getAccountBalance() < request.getAmount()) {
            throw new AppException(HttpStatus.NOT_IMPLEMENTED.value(), "Insufficient balance!");
        }


        Map<String, Object> params = new HashMap<>();
        params.put("type", request.getType());
        params.put("userTo", userTo);
        params.put("numberTo", numberTo);
        params.put("userFrom", walletUserFrom);
        params.put("amount", request.getAmount());

        return params;
    }

    @Transactional
    public void transfer(Map<String, Object> params) {
        String otp = params.get("otp").toString();
        String validOtp = Objects.requireNonNull(redisTemplate.opsForValue().get("otpValid")).toString().replace("   ", "");
        if(!otp.equals(validOtp)) throw new AppException(HttpStatus.BAD_REQUEST.value(), "Otp error!");
        handleTransfer(params);
    }

    protected void handleTransfer(Map<String, Object> params) {
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        try {
            var userFrom = mapper.convertValue(params.get("userFrom"), Wallet.class);
            Double amount = (Double) params.get("amount");

            userFrom.setAccountBalance(userFrom.getAccountBalance() - amount);
            walletRepository.save(userFrom);
            if (Integer.parseInt(params.get("type").toString()) == 0) {
                var userTo = mapper.convertValue(params.get("userTo"), BankUser.class);
                userTo.setBalance(userTo.getBalance() + amount);
                bankUserRepository.save(userTo);
            } else {
                var userTo = mapper.convertValue(params.get("userTo"), Wallet.class);
                userTo.setAccountBalance(userTo.getAccountBalance() + amount);
                walletRepository.save(userTo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
