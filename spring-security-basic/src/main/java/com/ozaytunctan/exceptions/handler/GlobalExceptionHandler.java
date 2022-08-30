package com.ozaytunctan.exceptions.handler;

import com.ozaytunctan.dtos.Error;
import com.ozaytunctan.dtos.ServiceResultDto;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ServiceResultDto<Void>> exceptions(AccessDeniedException e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error("Erişmek istediğiniz kaynağa erişim yetkiniz yok.!", 403);
        errors.add(error);
        ServiceResultDto<Void> serviceResultDto = new ServiceResultDto<>(errors);
        return ResponseEntity.ok(serviceResultDto);
    }

    @ExceptionHandler(value = {JwtException.class, MalformedJwtException.class})
    public ResponseEntity<ServiceResultDto<Void>> jwtExceptions(Exception e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error(e.getMessage(), 401);
        errors.add(error);
        return ResponseEntity.ok(new ServiceResultDto<>(errors));
    }

    @ExceptionHandler(value = {BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ServiceResultDto<Void>> badCredentialsException(Exception e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error("Lütfen Giriş Bilgilerinizi Kontrol Ediniz. Şifre veya Kullanıcı Adı Hatalı", 401);
        errors.add(error);
        return ResponseEntity.ok(new ServiceResultDto<>(errors));
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ServiceResultDto<Void>> exception(Exception e) {
        List<Error> errors = new ArrayList<>();
        Error error = new Error(e.getMessage(), 401);
        errors.add(error);
        return ResponseEntity.ok(new ServiceResultDto<>(errors));
    }


}
