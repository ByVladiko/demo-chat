package com.vldby.demochat.security;

import com.vldby.demochat.entity.User;
import org.springframework.lang.NonNull;

public interface AuthService {
    User register(User user);
    String login(String username, String password);
    String refresh(@NonNull String refreshToken);
}
