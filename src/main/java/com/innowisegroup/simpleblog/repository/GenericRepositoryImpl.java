package com.innowisegroup.simpleblog.repository;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class GenericRepositoryImpl<T extends Serializable>
        extends AbstractGenericDao<T> implements GenericRepository<T> {

    @Autowired
    public GenericRepositoryImpl(SessionFactory sessionFactory, Class<T> classType) {
        super();
        setSessionFactory(sessionFactory);
        setClassType(classType);
    }

    @Override
    public Optional<T> findById(long id) {
        return super.findById(id);
    }

    @Override
    public List<T> findAll() {
        return super.findAll();
    }

    @Override
    public void create(T entity) {
        super.save(entity);
    }

    @Override
    public void update(T entity) {
        super.update(entity);
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
    }

    @Override
    public void deleteById(long id) {
        super.deleteById(id);
    }
}
