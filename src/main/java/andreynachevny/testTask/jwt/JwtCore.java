package andreynachevny.testTask.jwt;

import andreynachevny.testTask.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtCore {
    private final SecretKey accessKey;

    public JwtCore( @Value("${access_token}") String accessKey){
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessKey));
    }

    public String generateAccessToken(User user){

        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusDays(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getName())
                .claim("id",user.getId())
                .setExpiration(accessExpiration)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateAccessToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            System.out.println("TokenDto expired");
        } catch (UnsupportedJwtException unsEx) {
            System.out.println("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            System.out.println("Malformed jwt");
        } catch (SignatureException sEx) {
            System.out.println("Invalid signature");
        } catch (Exception e) {
            System.out.println("invalid token");
        }
        return false;
    }

    public Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(accessKey)
                .build().parseClaimsJws(token)
                .getBody();
    }
}
