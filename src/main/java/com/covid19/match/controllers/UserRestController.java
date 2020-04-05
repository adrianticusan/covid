package com.covid19.match.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/")
public class UserRestController {

    @GetMapping("/login-failed")
    public ResponseEntity<String> getLoginFailed() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/login-success")
    @PreAuthorize("HAS_ROLE('USER')")
    public ResponseEntity<String> getLoginSuccess(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
