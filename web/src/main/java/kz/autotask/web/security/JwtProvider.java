package kz.autotask.web.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    private static final int BEARER_TOKEN_START_INDEX = 6;

    @Value("$(jwt.secret)")
    private String jwtSecret;

    public static String getTokenFromHeader(String authHeader) {
        return authHeader.substring(BEARER_TOKEN_START_INDEX);
    }

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
//            log.severe("Token expired");
        } catch (UnsupportedJwtException unsEx) {
//            log.severe("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
//            log.severe("Malformed jwt");
        } catch (SignatureException sEx) {
//            log.severe("Invalid signature");
        } catch (Exception e) {
//            log.severe("invalid token");
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}
