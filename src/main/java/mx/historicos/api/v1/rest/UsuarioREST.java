/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.historicos.api.v1.rest;

import com.medalfa.api.bean.Usuario;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import com.medalfa.api.facade.UsuarioFacade;
import com.medalfa.api.security.Secured;
import com.medalfa.api.security.UserContext;
import com.medalfa.api.util.KeyGenerator;
import com.medalfa.api.v1.model.CUDResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Aldo Escalona
 */
@Path("usuario")
public class UsuarioREST {

    @EJB
    private UsuarioFacade usuarioFacade;

    private static Logger logger = Logger.getLogger(UsuarioREST.class);

    public UsuarioREST() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response create(Usuario entity, @Context SecurityContext securityContext) {

        try {

            String pass = KeyGenerator.generate(8);
            String cif = usuarioFacade.cifraPasssword(pass);
            entity.setPass(cif);

            usuarioFacade.create(entity);

            CUDResponse root = new CUDResponse();
            root.setOk(Boolean.TRUE);
            root.setId(entity.getId().toString());
            root.setMensaje("Se ha generado una contraseña: " + pass);

            Response.ResponseBuilder rb = Response.ok(root);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response edit(@PathParam("id") Integer id, Usuario entity, @Context SecurityContext securityContext) {

        try {

            Usuario persistente = usuarioFacade.get(id);
            persistente.setCorreo(entity.getCorreo());
            persistente.setNombre(entity.getNombre());
            usuarioFacade.edit(persistente);

            CUDResponse root = new CUDResponse();
            root.setOk(Boolean.TRUE);
            root.setId(entity.getId().toString());

            Response.ResponseBuilder rb = Response.ok(root);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("{id}")
    // @Secured
    public Response remove(@PathParam("id") Integer id, @Context SecurityContext securityContext) {

        try {
            usuarioFacade.remove(usuarioFacade.find(id));
            CUDResponse root = new CUDResponse();
            root.setOk(Boolean.TRUE);
            root.setId(id.toString());

            Response.ResponseBuilder rb = Response.ok(root);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    // @Secured
    public Response find(@PathParam("id") Integer id, @Context SecurityContext securityContext) {

        try {

            Usuario ent = usuarioFacade.find(id);
            ent.setPass("No disponible");

            Response.ResponseBuilder rb = Response.ok(ent);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("id")
    @Produces({MediaType.APPLICATION_JSON})
    @Secured
    public Response findSecurityContext(@PathParam("id") Integer id, @Context SecurityContext securityContext) {

        try {

            UserContext user = (UserContext) securityContext.getUserPrincipal();
            if (!user.verificaPermiso("usuario")) {
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            Usuario ent = usuarioFacade.find(user.getId());
            ent.setPass("No disponible");

            Response.ResponseBuilder rb = Response.ok(ent);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    // @Secured
    public Response findAll(@Context SecurityContext securityContext) {

        try {

            List<Usuario> list = usuarioFacade.findAll();

            list.stream().forEach(u -> u.setPass("No disponible"));

            Response.ResponseBuilder rb = Response.ok(list);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_JSON})
    // @Secured
    public Response findRange(@PathParam("from") Integer from, @PathParam("to") Integer to, @Context SecurityContext securityContext) {

        try {

            List<Usuario> list = usuarioFacade.findRange(new int[]{from, to});

            Response.ResponseBuilder rb = Response.ok(list);
            Response resp = rb.header("Pragma", "no-cache")
                    .header("Expires", "-1")
                    .build();

            return resp;
        } catch (Exception e) {
            logger.error(e, e);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    // @Secured
    public String countREST(@Context SecurityContext securityContext) {
        return String.valueOf(usuarioFacade.count());
    }

}
