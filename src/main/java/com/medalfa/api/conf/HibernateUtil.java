/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.conf;

import java.util.*;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author Aldo Escalona
 */
public class HibernateUtil {

    private static Logger logger = Logger.getLogger(HibernateUtil.class);

    //private static final SessionFactory sessionFactory;
    //private static AnnotationConfiguration configuration;
    private static SessionFactory sessionFactory;
    private static Configuration configuration = null;
    private static HashMap<String, SessionFactory> factories = new HashMap<String, SessionFactory>();
    private static HashMap<String, Configuration> configurations = new HashMap<String, Configuration>();

    public static void addConfiguration(Configuration configuration, String name) {
        configurations.put(name, configuration);
    }

    public static void removeConfiguration(String name) {
        configurations.put(name, null);
        factories.put(name, null);
    }

    public static void buildSessionFactory(String name) {

        Configuration c = configurations.get(name);
        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(c.getProperties()).buildServiceRegistry();
        // ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

        SessionFactory f = c.buildSessionFactory(serviceRegistry);
        factories.put(name, f);
    }

    public static void setDefaultConfiguration(String name) {
        sessionFactory = factories.get(name);
        configuration = configurations.get(name);
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(String name) {
        return factories.get(name);
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static Session getSession(String name) {
        Session session = factories.get(name).openSession();
        return session;
    }

}
