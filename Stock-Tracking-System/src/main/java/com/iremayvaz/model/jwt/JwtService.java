package com.iremayvaz.model.jwt;

import com.iremayvaz.model.entity.User;
import com.iremayvaz.model.userDetails.AppUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    public static final String SECRET_KEY = "R8HFcGTlOF8shhqFqp+o8FADLCohD6C5v2bHbfbQhnQ=";
    private static final Duration ACCESS_TTL = Duration.ofHours(2);

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claimsMap = buildClaims(userDetails);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + ACCESS_TTL.toMillis());

        return Jwts.builder() // Token oluşturucu başlat
                .setSubject(userDetails.getUsername()) // Kullanıcı adını token payload'una koy
                .addClaims(claimsMap)
                .setIssuedAt(now) // Token ne zaman oluşturuldu?
                .setExpiration(expiredDate) // Token ne kadar geçerli?
                .signWith(getKey(), // Token'ı oluştururken ve çözerken kullanılacak key
                        SignatureAlgorithm.HS256) // HMAC-SHA256 algosu ile imzala
                .compact(); // Token'ı string olarak dön
    }

    public Map<String, Object> buildClaims(UserDetails userDetails){ // Doğrulanmış kullanıcının token'ı içine koyulacak claim'leri hazırlar.
        Map<String, Object> claims = new HashMap<>();

        if(userDetails instanceof AppUserDetails appUserDetails){
            claims.put("user_id", appUserDetails.getId());
            claims.put("email", appUserDetails.getEmail());
            claims.put("role", appUserDetails.getRoleName().name());
            claims.put("permissions", appUserDetails.getPermissions()
                                                    .stream()
                                                    .map(Enum::name)
                                                    .toList());
        } else {
            var all = userDetails.getAuthorities()
                                 .stream()
                                 .map(org.springframework.security.core.GrantedAuthority::getAuthority)
                                 .toList();

            claims.put("role", all.stream().filter(a -> a.startsWith("ROLE_")).toList());
            claims.put("permissions", all.stream().filter(a -> !a.startsWith("ROLE_")).toList());
        }
        return claims;
    }

    public Object getClaimsByKey(String token, String key){
        Claims claims = getClaims(token);
        return claims.get(key);
    }

    public Claims getClaims(String token){
        // Claims, token içindeki payload{subject, expiration, custom alanlar}.
        // claimsFunction, hangi bilgiyi istiyorsan onu çıkarır.
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getKey()) // Doğrulama için secret key
                .build()
                .parseClaimsJws(token) // Token'ı çöz
                .getBody(); // Payload kısmını al
        return claims;
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsFunction){ // Token'ı çözmek için
        Claims claims = getClaims(token);
        return claimsFunction.apply(claims); // İstenen bilgiyi döndür
    }

    public String getUsernameByToken(String token){
        return exportToken(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token){
        Date expiredDate = exportToken(token, Claims::getExpiration);
        // Now: 15.40
        // expiredDate : 15.45
        return new Date().after(expiredDate); // şu anki zaman expiredDate'i geçtiyse TRUE yani token süresi dolmuş!
    }

    public Key getKey(){ // Token'ı oluşturacak ve çözecek "key"
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
