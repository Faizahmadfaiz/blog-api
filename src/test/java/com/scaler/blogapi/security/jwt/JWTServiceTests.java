package com.scaler.blogapi.security.jwt;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JWTServiceTests {
    private JWTService jwtService = new JWTService();
    @Test
    void canCreateJWTFromUserId() {
        var userId = 123;
        var jwt = jwtService.createJWT(userId, new Date(1677082), new Date(1677687));
        assertEquals("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjE2NzcsImlhdCI6MTY3N30.wOSHjo8O3auVMYHTpEJ3Q6qKwwUx3-a26lsHeq4O53I", jwt);
    }

    @Test
    void canVerifyJWT() {
        var jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjMiLCJleHAiOjE2Nzc3NzUzMjEsImlhdCI6MTY3NzE3MDUyMX0.Dl2VHTP31jScXg-SVw8_KioTMWcHwTx4M2BcdKJxZVE";
        var userId = jwtService.getUserIdFromJWT(jwt);
        assertEquals(123, userId);
    }
}
