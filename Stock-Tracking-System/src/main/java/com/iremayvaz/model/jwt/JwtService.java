package com.iremayvaz.model.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET_KEY = "R8HFcGTlOF8shhqFqp+o8FADLCohD6C5v2bHbfbQhnQ=";

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("role", "ADMIN");

        return Jwts.builder() // Token oluşturucu başlat
                .setSubject(userDetails.getUsername()) // Kullanıcı adını token payload'una koy
                .addClaims(claimsMap)
                .setIssuedAt(new Date()) // Token ne zaman oluşturuldu?
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*2)) // Token ne kadar geçerli? // 1000ms = 1s
                .signWith(getKey(), // Token'ı oluştururken ve çözerken kullanılacak key
                        SignatureAlgorithm.HS256) // HMAC-SHA256 algosu ile imzala
                .compact(); // Token'ı string olarak dön
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
        return new Date().before(expiredDate);
    }

    public Key getKey(){ // Token'ı oluşturacak ve çözecek "key"
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
