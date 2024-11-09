package it3180.team19.walletapi.service;

import it3180.team19.walletapi.Exception.AppException;
import it3180.team19.walletapi.domain.BankUser;
import it3180.team19.walletapi.dto.request.BankUserCreationRequest;
import it3180.team19.walletapi.repository.BankRepository;
import it3180.team19.walletapi.repository.BankUserRepository;
import it3180.team19.walletapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankUserService {
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final BankUserRepository bankUserRepository;

    @Transactional
    public void saveBankUsers(BankUserCreationRequest bankUserCreationRequest) {
        List<BankUser> bankUsers = new ArrayList<>();
        var infoBank = bankUserCreationRequest.getInfoBank();
        infoBank.forEach(item -> {
                    var bank = bankRepository.findByCode(item.getOrDefault("code", " ").toString()).orElse(null);
                    if (bank == null) {
                        log.info("Bank not found");
                        throw new AppException(HttpStatus.NOT_FOUND.value(), "Bank not found");
                    }
                    if (bankUserRepository.existByNumberAccountAndCode(item.getOrDefault("numberAccount", " ").toString(), bank.getCode())) {
                        throw new AppException(HttpStatus.CONFLICT.value(), "Number Account already exists in a Bank");
                    }
                    bankUsers.add(BankUser.builder()
                            .user(userRepository.findById(bankUserCreationRequest.getPhoneNumber()).orElse(null))
                            .bank(bank)
                            .balance(Double.parseDouble(item.getOrDefault("balance", 0.0).toString()))
                            .numberAccount(item.getOrDefault("numberAccount", " ").toString())
                            .build()
                    );
                }
        );
        bankUserRepository.saveAll(bankUsers);
    }
}
