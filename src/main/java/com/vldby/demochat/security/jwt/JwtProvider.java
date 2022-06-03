package com.vldby.demochat.security.jwt;

import com.vldby.demochat.entity.Role;
import com.vldby.demochat.entity.User;
import com.vldby.demochat.security.CustomUserDetailsService;
import com.vldby.demochat.service.TimeSource;
import io.jsonwebtoken.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    private final Log logger = LogFactory.getLog(JwtProvider.class);

    private static final String AUTH_KEY = "auth";

    @Value("${security.jwt.access.secret}")
    private String jwtAccessSecret;

    @Value("${security.jwt.access.expire-minutes:5}")
    private long validityTokenMinutes;

    private final TimeSource timeSource;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtProvider(TimeSource timeSource, CustomUserDetailsService userDetailsService) {
        this.timeSource = timeSource;
        this.userDetailsService = userDetailsService;
    }

    public String generateAccessToken(@NonNull User user) {
        final ZonedDateTime now = timeSource.now();
        final Instant accessExpirationInstant = now.plusMinutes(validityTokenMinutes).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(accessExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecret)
                .claim(AUTH_KEY, getRolesStr(user))
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public boolean validateToken(@NonNull String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }

        return false;
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    private Claims getClaims(@NonNull String token, @NonNull String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private String getRolesStr(User user) {
        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.joining(","));
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtAccessSecret).parseClaimsJws(token).getBody().getSubject();
    }
}
