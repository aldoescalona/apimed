/*
 * Creado el 2/08/2007
 *
 * TODO Para cambiar la plantilla de este archivo generado, vaya a
 * Ventana - Preferencias - Java - Estilo de c�digo - Plantillas de c�digo
 */
package com.medalfa.api.conf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.type.Type;

public class Generator implements Configurable, IdentifierGenerator {

    public static long ctrl = 0L;
    private String entityId;

    public Generator() {
        System.out.println(" - Generator --");
    }

    @Override
    public Integer generate(SessionImplementor session, Object obj) throws HibernateException {

        Integer id = null;
        try {

            Connection conn = session.connection();
            PreparedStatement pst = conn.prepareStatement("SELECT NextVal(?)");
            pst.setString(1, entityId);
            
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                id = rs.getInt(1);
            }
            
        } catch (Exception e) {
            throw new HibernateException(e);
        }
        
        return id;
    }

    @Override
    public void configure(Type type, Properties props, Dialect dialect) throws MappingException {
        System.out.println(" -- Generator.conf -- " + props.getProperty("entityId"));
        setEntityId(props.getProperty("entityId"));
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

}
