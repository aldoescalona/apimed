/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.conf;

import java.io.File;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;


/**
 * Web application lifecycle listener.
 *
 * @author Aldo Escalona
 */
@WebListener()
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {

            log4j();

            MedalfaDBConfiguration conf = new MedalfaDBConfiguration();
            HibernateUtil.addConfiguration(conf, "MED");
            HibernateUtil.buildSessionFactory("MED");
            HibernateUtil.setDefaultConfiguration("MED");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }

    private void log4j() {

        File logdir = new File("/var/smx/logs");
        if (!logdir.exists()) {
            logdir.mkdirs();
        }

        DOMConfigurator.configure(AppListener.class.getResource("/logging.xml"));

        Logger log = Logger.getLogger("org.hibernate");
        log.setLevel(Level.ALL);
        

    }


}
