/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.historicos.api.v1.rest;

import com.medalfa.api.bean.EntradaSalida;
import com.medalfa.api.bean.Producto;
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
import com.medalfa.api.facade.ProductoFacade;
import com.medalfa.api.security.Secured;
import com.medalfa.api.security.UserContext;
import com.medalfa.api.util.APIUtil;
import com.medalfa.api.v1.model.Busqueda;
import com.medalfa.api.v1.model.CUDResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Aldo Escalona
 */
@Path("producto")
public class ProductoREST {

    @EJB
    private ProductoFacade productoFacade;
    
    @EJB
    private EntradaSalidaFacade entradaSalidaFacade;

    private static Logger logger = Logger.getLogger(ProductoREST.class);

    public ProductoREST() {
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response create(Producto entity, @Context SecurityContext securityContext) {

        
        System.out.println(" PRODUCTO: " + entity);
        try {

            
            productoFacade.create(entity);
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
    public Response edit(@PathParam("id") Integer id, Producto entity, @Context SecurityContext securityContext) {

        try {
            
            Producto persistente = productoFacade.get(id);
            persistente.setNombre(entity.getNombre());
            persistente.setDescripcion(entity.getDescripcion());
            persistente.setLote(entity.getLote());
            persistente.setCaducidad(entity.getCaducidad());
            
            productoFacade.edit(persistente);
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
            productoFacade.remove(productoFacade.find(id));
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

            Producto ent = productoFacade.find(id);

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
    public Response findAll(@Context SecurityContext securityContext) {

        try {

            List<Producto> list = productoFacade.findAll();
            
            System.out.println("LIST: " + list);

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
    
    
    @POST
    @Path("buscar")
    @Consumes({MediaType.APPLICATION_JSON})
    // @Secured
    public Response buscar(Busqueda bus, @Context SecurityContext securityContext) {
       
        try {
            System.out.println(" BUS: " + bus.getTermino());
            List<Producto> list = productoFacade.getBuscaProductos(bus);

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
    @Path("reporte/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    // @Secured
    public Response reporte(@PathParam("id") Integer id, @Context SecurityContext securityContext) {

        try {

            List<EntradaSalida> list = entradaSalidaFacade.getEntradaSalidas(id);
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

            List<Producto> list = productoFacade.findRange(new int[]{from, to});

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
        return String.valueOf(productoFacade.count());
    }

}
