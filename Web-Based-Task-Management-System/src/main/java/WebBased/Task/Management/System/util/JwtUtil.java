package WebBased.Task.Management.System.util;

import WebBased.Task.Management.System.entity.Users;
import WebBased.Task.Management.System.exception.GenericException;
import WebBased.Task.Management.System.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    @Autowired
    private UserRepository userRepository;
    private String SECRET_KEY = "Ashish secret key";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateAccessToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",user.getRole());
        claims.put("name",user.getName());
        claims.put("password",user.getPassword());
        claims.put("id",user.getId());
        claims.put("createdAt",user.getCreatedAt());
        claims.put("token_type","Access");
        return createToken(claims, user.getEmail(),7 * 60 * 60 * 1000);
    }

    public String generateRefreshToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",user.getRole());
        claims.put("name",user.getName());
        claims.put("password",user.getPassword());
        claims.put("id",user.getId());
        claims.put("createdAt",user.getCreatedAt());
        claims.put("token_type","Refresh");
        return createToken(claims, user.getEmail(),7 * 24 * 60 * 60 * 1000);
    }

    private String createToken(Map<String, Object> claims, String subject,long time) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Boolean isValidRefreshToken(String token) throws GenericException {
        final String username = extractUsername(token);
        var claims=extractAllClaims(token);
        String tokenType= (String) claims.get("token_type");
        userRepository.findByEmail(username)
                .orElseThrow(()->new GenericException(HttpStatus.NOT_FOUND.value(), "User does not exists"));
        return (tokenType.equals("Refresh") && !isTokenExpired(token));
    }

    public Boolean isValidAccessToken(String token) {
        var claims=extractAllClaims(token);
        String tokenType= (String) claims.get("token_type");
        return (tokenType.equals("Access") && !isTokenExpired(token));
    }

    public String getUsername(String token){
        return extractUsername(token);
    }
}