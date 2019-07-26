package com.medalfa.api.facade;

import com.medalfa.api.bean.Producto;
import java.util.List;
import javax.ejb.Stateless;
import com.medalfa.api.conf.HibernateUtil;
import java.util.Calendar;
import com.medalfa.api.v1.model.Busqueda;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Aldo Escalona
 */
@Stateless
public class ProductoFacade extends AbstractFacade<Producto> {

    public ProductoFacade() {
        super(Producto.class);
    }

    public List<Producto> getProductos() {
        List<Producto> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Producto.class);
           
            
            list = criteria.list();

            session.flush();
            tx.commit();
        } catch (RuntimeException ex) {
            if (session != null && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }

        return list;
    }
    
    
    public List<Producto> getBuscaProductos(Busqueda bus) {
        
        
        List<Producto> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Producto.class);
            
            Junction jun = Restrictions.disjunction();
            jun.add(Restrictions.like("nombre", bus.getTermino(), MatchMode.ANYWHERE));
            jun.add(Restrictions.like("lote", bus.getTermino(), MatchMode.ANYWHERE));
            criteria.add(jun);
            criteria.addOrder(Order.desc("nombre"));
            list = criteria.list();

            session.flush();
            tx.commit();
        } catch (RuntimeException ex) {
            if (session != null && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }
    
}
