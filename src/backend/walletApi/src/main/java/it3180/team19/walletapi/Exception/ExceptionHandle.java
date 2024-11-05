package it3180.team19.walletapi.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandle {


    @ExceptionHandler(value = {AppException.class})
    public ResponseEntity<Map<String, Object>> AppException(AppException e) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("code", e.getCode());
        return ResponseEntity.status(e.getCode()).body(map);
    }


}
