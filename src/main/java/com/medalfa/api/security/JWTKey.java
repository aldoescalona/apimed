/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;

/**
 *
 * @author Aldo Escalona
 */
@Singleton
public class JWTKey {

    private Key key;


    @PostConstruct
    public void init() {
        key = MacProvider.generateKey();
        // keytool -genseckey -keystore historicos-keystore.pfx -storetype jceks -keyalg AES -keysize 128 -alias historicos-k-store -storepass historicospass
        
    }

    public Key getKey() {
        return key;
    }

    public String token(Object id, String username, String roles, Integer horas) {

        Date issueDate = new Date();
        JwtBuilder builder = Jwts.builder();

        if (roles != null) {
            builder.claim("roles", roles);
        }

        if (horas != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(issueDate);
            calendar.add(Calendar.HOUR, horas);
            Date expireDate = calendar.getTime();
            builder.setExpiration(expireDate);
        }

        builder.setId(id.toString())
                .setSubject(username)
                .setIssuer("http://www.medalfa.mx")
                .setIssuedAt(issueDate)
                .signWith(SignatureAlgorithm.HS512, key).claim("id", id);

        return builder.compact();
    }

}
