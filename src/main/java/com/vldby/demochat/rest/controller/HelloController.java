package com.vldby.demochat.rest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.vldby.demochat.rest.RestConstants.EndPoints;

@RestController(EndPoints.HELLO)
public class HelloController {

    @GetMapping(EndPoints.Hello.USER)
    public ResponseEntity<String> helloUser() {
        return ResponseEntity.ok("Hello, User");
    }

    @GetMapping(EndPoints.Hello.ADMIN)
    public ResponseEntity<String> helloAdmin() {
        return ResponseEntity.ok("Hello, Admin");
    }

}
