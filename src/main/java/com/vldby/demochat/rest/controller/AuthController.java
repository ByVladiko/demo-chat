package com.vldby.demochat.rest.controller;

import com.vldby.demochat.entity.User;
import com.vldby.demochat.rest.dto.LoginDto;
import com.vldby.demochat.rest.dto.UserDto;
import com.vldby.demochat.rest.mapper.UserMapper;
import com.vldby.demochat.security.AuthService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.vldby.demochat.rest.RestConstants.EndPoints;

@RestController(EndPoints.AUTH)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = EndPoints.Auth.LOGIN, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        authService.login(loginDto.getLogin(), loginDto.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = EndPoints.Auth.REGISTRY, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registry(@RequestBody UserDto userDto) {
        UserMapper userMapper = UserMapper.INSTANCE;
        User registerUser = authService.register(userMapper.toUser(userDto));
        return ResponseEntity.ok(registerUser);
    }

}
