package it3180.team19.walletapi.controller;

import com.nimbusds.jose.JOSEException;
import it3180.team19.walletapi.dto.ApiResponse;
import it3180.team19.walletapi.dto.request.TransferMoneyRequest;
import it3180.team19.walletapi.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transfer")
public class TransferMoneyController {
    private final TransferService transferService;

    @PostMapping("/pre-transfer")
    @PreAuthorize("hasRole('transfer-money')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> handlePreTransfer(@RequestBody TransferMoneyRequest request) throws JOSEException {
        return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.<Map<String, Object>>builder()
                        .code(HttpStatus.OK.value())
                        .message("success")
                        .result(transferService.handlePreTransfer(request))
                .build());
    }


    @PostMapping("/transfer")
    @PreAuthorize("hasRole('transfer-money')")
    public ResponseEntity<ApiResponse<Void>> transfer(@RequestBody Map<String, Object> params) throws JOSEException, InterruptedException {
        transferService.transfer(params);
        return ResponseEntity.status(HttpStatus.OK.value()).body(ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .build());
    }
}
