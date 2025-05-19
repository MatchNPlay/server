package com.matchnplay.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final String issuer;
    private final Duration ttl;
    private final JwtEncoder jwtEncoder;

    public String generateToken(final String username) {
        var claimsSet = JwtClaimsSet.builder()
                .subject(username)
                .issuer(issuer)
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(ttl))
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
    }
}
