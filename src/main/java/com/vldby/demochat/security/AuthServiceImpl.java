package com.vldby.demochat.security;

import com.vldby.demochat.entity.User;
import com.vldby.demochat.exception.JwtAuthenticationException;
import com.vldby.demochat.exception.UserAlreadyExistsException;
import com.vldby.demochat.repo.RoleRepo;
import com.vldby.demochat.repo.UserRepo;
import com.vldby.demochat.security.jwt.JwtProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final Log logger = LogFactory.getLog(AuthServiceImpl.class);

    private final JwtProvider jwtProvider;
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthServiceImpl(JwtProvider jwtProvider,
                           UserRepo userRepo,
                           RoleRepo roleRepo,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authManager) {
        this.jwtProvider = jwtProvider;
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.authManager = authManager;
    }

    @Override
    public User register(User user) {
        if (userRepo.existsByUsername(user.getUsername()))
            throw new UserAlreadyExistsException("User " + user.getUsername() + " already exists");

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepo.findByDefaultRoleTrue());

        User registeredUser = userRepo.save(user);

        logger.info("User " + user.getUsername() + " successfully registered");

        return registeredUser;
    }

    public String login(String username, String password) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtProvider.generateAccessToken(user);
        } catch (AuthenticationException e) {
            logger.error(e);
            throw new JwtAuthenticationException("Invalid username/password supplied");
        }
    }

    public String refresh(@NonNull String username) {
        return userRepo.findByUsername(username)
                .map(jwtProvider::generateAccessToken)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

}
