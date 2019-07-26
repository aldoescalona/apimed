package com.medalfa.api.security;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import javax.ejb.EJB;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class RestSecurityFilter implements ContainerRequestFilter {

    @EJB
    private JWTKey app;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        
        if(authorizationHeader == null){
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        try {
            String token =  authorizationHeader; 
            
            System.out.println(" APPTOKENBEAN: " + app +  " Token: " + token);
            
            Jws<Claims> claims = Jwts.parser().setSigningKey(app.getKey()).parseClaimsJws(token);
            Number usuarioId = (Number)claims.getBody().get("id");
            

            UserContext usuario = new UserContext();
            usuario.setUsername(claims.getBody().getSubject());
            usuario.setId(usuarioId.intValue());
            
            String roles = (String) claims.getBody().get("roles");
            if(roles != null){
                usuario.setRoles(roles);
            }
            System.out.println(" ROLES: " + roles);
            

            RestSecurityContext secContext = new RestSecurityContext(usuario, requestContext.getSecurityContext().isSecure());
            requestContext.setSecurityContext(secContext);

        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            e.printStackTrace();
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
