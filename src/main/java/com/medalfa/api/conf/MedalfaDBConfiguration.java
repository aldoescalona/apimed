/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.conf;



import com.medalfa.api.bean.EntradaSalida;
import com.medalfa.api.bean.Producto;
import com.medalfa.api.bean.Usuario;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Aldo Escalona
 */
public class MedalfaDBConfiguration extends Configuration {


    public MedalfaDBConfiguration() {
        super();    
        init();
    }

    private void init() {

        this.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        this.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        this.setProperty("hibernate.connection.datasource", "jdbc/medalfa");
        
        this.addAnnotatedClass(EntradaSalida.class);
        this.addAnnotatedClass(Producto.class);
        this.addAnnotatedClass(Usuario.class);
    }
}
