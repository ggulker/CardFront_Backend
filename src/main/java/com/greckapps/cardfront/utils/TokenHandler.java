package com.greckapps.cardfront.utils;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

import io.jsonwebtoken.*;

import java.util.Date;


public class TokenHandler {
    private static final String SECRET_KEY = System.getenv("token_key");

    private static String createToken( String issuer, String subject, long ttlMillis){
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary( SECRET_KEY );
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public static String createStandToken(String username)
    {
        long timeToExpire = 86400000;
        String issuer = "Greck";

        return createToken(issuer, username, timeToExpire);
    }

    private static Claims decodeToken(String jwt) {
        //This line will throw an exception if it is not a signed JWS (as expected)
        JwtParser parser = Jwts.parserBuilder().setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY)).build();
        Claims claims = parser.parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static boolean ValidateToken(String jwt)
    {
        try{
            Claims claims = decodeToken(jwt);
            Date now = new Date();
            if(claims.getExpiration().after(now)){
                return true;
            }
            else{
                System.out.println("INFO::TOKEN EXPIRED RETURNING TO LOGIN");
                return false;
            }
        }
        catch(Exception e)
        {
            System.err.println("ERROR::POSSIBLE INVALID TOKEN HAS BEEN SENT");
            return false;
        }
    }
    
    public static String getTokenSubject(String jwt)
    {
        try{
            return decodeToken(jwt).getSubject();
        }
        catch(Exception e)
        {
            System.err.println("ERROR::POSSIBLE INVALID TOKEN HAS BEEN SENT");
            return "";
        }
    }
}
