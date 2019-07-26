package com.medalfa.api.facade;

import com.medalfa.api.bean.Usuario;
import javax.ejb.Stateless;
import com.medalfa.api.conf.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;

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
public class UsuarioFacade extends AbstractFacade<Usuario> {

    public UsuarioFacade() {
        super(Usuario.class);
    }

    public Usuario getUsuario(String user, String pass) {

        Usuario ent = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(Usuario.class);
            criteria.add(Restrictions.eq("correo", user));
            criteria.add(Restrictions.sqlRestriction("{alias}.pass = password(?)", pass, StringType.INSTANCE));

            ent = (Usuario) criteria.uniqueResult();

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

        return ent;
    }

    public String cifraPasssword(String passs) {

        String pcif = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSession();
            tx = session.beginTransaction();

            SQLQuery query = session.createSQLQuery("SELECT password(:p)");
            query.setString("p", passs);
            pcif = (String) query.uniqueResult();

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
        return pcif;
    }

}
