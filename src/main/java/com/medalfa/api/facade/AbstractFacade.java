/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.medalfa.api.facade;

import java.util.List;
import javax.persistence.EntityManager;
import com.medalfa.api.conf.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Aldo Escalona
 */
public abstract class AbstractFacade<T> {

    protected Session getHibernateUtilSession(){
        return HibernateUtil.getSession();
    }
    
    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }


    public T create(T entity) {
        T t = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            session.save(entity);

            session.flush();
            tx.commit();
        } catch (HibernateException ex) {
            if (session != null && tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return t;
        //getEntityManager().persist(entity);
    }

    public void edit(T entity) {

        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            session.update(entity);

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
        //getEntityManager().merge(entity);
    }

    public void remove(T entity) {

        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            session.delete(session.merge(entity));

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
        //getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id, Criterion... criterions) {

        T t = null;

        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            criteria.add(Restrictions.idEq(id));
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
            t = (T) criteria.uniqueResult();

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
        return t;
        //return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll(Criterion... criterions) {

        List<T> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            for (Criterion criterion : criterions) {
                criteria.add(criterion);
            }
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
    
    
    public List<T> list(Criterion criterion, Order order, String... fetchs) {

        List<T> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            if (criterion != null) {
                criteria.add(criterion);
            }
            
            if (fetchs != null) {
                for (String fetch : fetchs) {
                    criteria.setFetchMode(fetch, FetchMode.JOIN);
                }
            }
            
            if(order != null){
                criteria.addOrder(order);
            }
            
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

    public List<T> findRange(int[] range, Order... orders) {

        List<T> list = null;
        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            for (Order order : orders) {
                criteria.addOrder(order);
            }
            criteria.setMaxResults(range[1] - range[0]);
            criteria.setFirstResult(range[0]);
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
        /*
         * javax.persistence.criteria.CriteriaQuery cq =
         * getEntityManager().getCriteriaBuilder().createQuery();
         * cq.select(cq.from(entityClass)); javax.persistence.Query q =
         * getEntityManager().createQuery(cq); q.setMaxResults(range[1] -
         * range[0]); q.setFirstResult(range[0]); return q.getResultList();
         */
    }

    public int count() {

        int count = -1;

        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            criteria.setProjection(Projections.rowCount());
            count = ((Number) criteria.uniqueResult()).intValue();

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
        return count;
        /*
         * javax.persistence.criteria.CriteriaQuery cq =
         * getEntityManager().getCriteriaBuilder().createQuery();
         * javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
         * cq.select(getEntityManager().getCriteriaBuilder().count(rt));
         * javax.persistence.Query q = getEntityManager().createQuery(cq);
         * return ((Long) q.getSingleResult()).intValue();
         */
    }

    public T get(Object id, String... fetchs) {

        T t = null;

        Session session = null;
        Transaction tx = null;
        try {
            session = getHibernateUtilSession();
            tx = session.beginTransaction();

            Criteria criteria = session.createCriteria(entityClass);
            criteria.add(Restrictions.idEq(id));

            if (fetchs != null) {
                for (String fetch : fetchs) {
                    criteria.setFetchMode(fetch, FetchMode.JOIN);
                }
            }

            t = (T) criteria.uniqueResult();

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
        return t;
        //return getEntityManager().find(entityClass, id);
    }    
}
