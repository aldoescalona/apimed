/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.historicos.api.v1.rest;

import com.medalfa.api.bean.Usuario;
import javax.ejb.EJB;
import javax.naming.AuthenticationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import com.medalfa.api.facade.UsuarioFacade;
import com.medalfa.api.security.JWTKey;
import com.medalfa.model.Credencial;
import com.medalfa.model.Token;
import com.medalfa.api.security.Secured;
import com.medalfa.api.security.UserContext;
import com.medalfa.api.util.APIUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author Aldo Escalona
 */
@Path("auth")
public class AuthREST {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private JWTKey jwtkey;
    
    public static int HORAS = 3000;
    private static Logger logger = Logger.getLogger(AuthREST.class);

    public AuthREST() {
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUserRoot(Credencial authu) {
        try {

            String username = authu.getUsername();
            String password = authu.getPassword();

            Usuario ent = authenticate(username, password);
            String token = jwtkey.token(ent.getId(), username, "usuario", HORAS);

            Token r = new Token(token);
            System.out.println(" USR: " + ent + ", " + jwtkey);

            Response.ResponseBuilder rb = Response.ok(r);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .header("X-Cached-Result", "true")
                    .header("X-Cache-Remaining", "99")
                    .header("X-Cache-Expires", APIUtil.getExpiresHeader())
                    .header("Date", APIUtil.getDateHeader())
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }

    @POST
    @Path("/renovar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public Response renovarToken(@Context SecurityContext securityContext) {
        try {

            UserContext user = (UserContext) securityContext.getUserPrincipal();
            if (!user.verificaPermiso("cliente")) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            Usuario ent = usuarioFacade.get(user.getId());
            String token = jwtkey.token(ent.getId(), ent.getCorreo(), "cliente.638", HORAS);

            Token r = new Token(token);

            Response.ResponseBuilder rb = Response.ok(r);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .header("X-Cached-Result", "true")
                    .header("X-Cache-Remaining", "99")
                    .header("X-Cache-Expires", APIUtil.getExpiresHeader())
                    .header("Date", APIUtil.getDateHeader())
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
    }
    
    private Usuario authenticate(String username, String password) throws Exception {

        Usuario ent = usuarioFacade.getUsuario(username, password);
        if (ent == null) {
            throw new AuthenticationException();
        }
        return ent;
    }

}
