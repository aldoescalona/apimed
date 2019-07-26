/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.historicos.api.v1.rest;

import com.medalfa.api.bean.EntradaSalida;
import java.util.Date;
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
import com.medalfa.api.facade.EntradaSalidaFacade;
import com.medalfa.api.security.Secured;
import com.medalfa.api.v1.model.CUDResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Aldo Escalona
 */
@Path("entradaSalida")
public class EntradaSalidaREST {

    @EJB
    private EntradaSalidaFacade entradaSalidaFacade;

    private static Logger logger = Logger.getLogger(EntradaSalidaREST.class);

    public EntradaSalidaREST() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response create(EntradaSalida entity, @Context SecurityContext securityContext) {

        try {
            System.out.println(" EntradaSalida: " + entity);
            
            entity.setFecha(new Date());
            
            entradaSalidaFacade.create(entity);
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

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response edit(@PathParam("id") Integer id, EntradaSalida entity, @Context SecurityContext securityContext) {

        try {
            entradaSalidaFacade.edit(entity);
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
            entradaSalidaFacade.remove(entradaSalidaFacade.find(id));
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

            EntradaSalida ent = entradaSalidaFacade.find(id);

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

            List<EntradaSalida> list = entradaSalidaFacade.findAll();
            list.stream().forEach(e -> e.setUsuarioId(null));
            
            
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

            List<EntradaSalida> list = entradaSalidaFacade.findRange(new int[]{from, to});

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
        return String.valueOf(entradaSalidaFacade.count());
    }

}
