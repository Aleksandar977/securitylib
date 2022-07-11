package com.htec.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.htec.domain.enums.TokenType;
import com.htec.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtils jwtUtils;

    @Override
    public Long getUserIdFromToken(DecodedJWT decodedJWT) {
        return jwtUtils.getUserIdFromToken(decodedJWT);
    }

    @Override
    public DecodedJWT getDecodedJwt(String token) {
        return jwtUtils.getDecodedJwt(token);
    }

    @Override
    public TokenType getTokenTypeFromToken(DecodedJWT decodedJWT) {
        return jwtUtils.getTokenType(decodedJWT);
    }
}
