package com.medalfa.api.facade;

import com.medalfa.api.bean.EntradaSalida;
import java.util.List;
import javax.ejb.Stateless;
import com.medalfa.api.conf.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
public class EntradaSalidaFacade extends AbstractFacade<EntradaSalida> {

    public EntradaSalidaFacade() {
        super(EntradaSalida.class);
    }

    public List<EntradaSalida> getEntradaSalidas(Integer id) {
        List<EntradaSalida> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(EntradaSalida.class);
           criteria.add(Restrictions.eq("productoId.id", id));
            criteria.setFetchMode("productoId", FetchMode.JOIN);
            criteria.addOrder(Order.asc("fecha"));
            
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
