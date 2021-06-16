package com.innowisegroup.simpleblog.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractGenericDao<T extends Serializable> {

    private Class<T> classType;

    private SessionFactory sessionFactory;

    public void setClassType(Class<T> classType) {
        this.classType = classType;
    }

    //TODO: obtain session factory more effectively?
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(getCurrentSession().get(classType, id));
    }

    public List findAll() {
        return getCurrentSession()
                .createQuery("from " + classType.getName()).list();
    }

    public void save(T entity) {
        getCurrentSession().persist(entity);
    }

    public void update(T entity) {
        getCurrentSession().update(entity);
    }

    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    public void deleteById(long id) {
        final Optional<T> entity = findById(id);
        entity.ifPresent(getCurrentSession()::delete);
    }

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
