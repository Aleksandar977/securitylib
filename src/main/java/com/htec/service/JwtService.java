package com.htec.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.htec.domain.enums.TokenType;

public interface JwtService {

    Long getUserIdFromToken(DecodedJWT decodedJWT);

    DecodedJWT getDecodedJwt(String token);

    TokenType getTokenTypeFromToken(DecodedJWT decodedJWT);
}
