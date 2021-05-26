package com.guilherme.springblog.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import com.guilherme.springblog.exception.SpringBlogException;
import com.guilherme.springblog.service.AuthenticationResponse;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

@Service
public class JwtProvider {
    private KeyStore keyStore;

    @PostConstruct
    public void init(){
        try{
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "123456".toCharArray());
        }catch(CertificateException | IOException | KeyStoreException | NoSuchAlgorithmException e){
            throw new SpringBlogException("Exception occurred while loading keystore");
        }
    }

    public AuthenticationResponse generateToken(Authentication authentication){
        User principal = (User) authentication.getPrincipal();
        String jwt = Jwts.builder()
            .setSubject(principal.getUsername())
            .signWith(getPrivateKey())
            .compact();
        return AuthenticationResponse.builder()
                    .authenticationToken(jwt)
                    .username(principal.getUsername())
                    .build();
    }

    public PrivateKey getPrivateKey(){
        try{
            return (PrivateKey) keyStore.getKey("springblog", "123456".toCharArray());
        }catch(KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e){
            throw new SpringBlogException("Exception occurred while retrieving private key from keystore");
        }   
    }

    public boolean validateToken(String jwt){
        Jwts.parser()
        .setSigningKey(getPublicKey())
        .parseClaimsJws(jwt);

        return true;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public PublicKey getPublicKey(){
        try{
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch(KeyStoreException e) {
            throw new SpringBlogException("Exception occurred while retrieving public key from keystore");
        }
        
    }
}
