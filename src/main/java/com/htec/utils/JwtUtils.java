package com.htec.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.htec.config.props.JwtPropsConfig;
import com.htec.domain.enums.ErrorCodeMessage;
import com.htec.domain.enums.TokenType;
import com.htec.exception.InvalidJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

import static com.htec.utils.Constants.TOKEN_TYPE_CLAIM;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtils {

    private final JwtPropsConfig jwtPropsConfig;

    public Long getUserIdFromToken(DecodedJWT decodedJWT) {
        return Long.parseLong(decodedJWT.getSubject());
    }

    public TokenType getTokenType(DecodedJWT decodedJWT) {
        return TokenType.valueOf(decodedJWT.getClaim(TOKEN_TYPE_CLAIM).asString());
    }

    public DecodedJWT getDecodedJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(jwtPropsConfig.getSecret().getBytes(StandardCharsets.UTF_8));
        try {
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (Exception e) {
            throw new InvalidJwtException(ErrorCodeMessage.INVALID_TOKEN.getMessage());
        }
    }


}
