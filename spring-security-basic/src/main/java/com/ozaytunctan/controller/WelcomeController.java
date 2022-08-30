package com.ozaytunctan.controller;


import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/welcome")
public class WelcomeController {


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "")
    public ResponseEntity<String> welcome(){
        return ResponseEntity.ok("Spring Boot Projesine Hoş Geldiniz... ");
    }
}
