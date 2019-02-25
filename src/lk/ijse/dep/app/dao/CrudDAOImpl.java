package lk.ijse.dep.app.dao;

import lk.ijse.dep.app.entity.SuperEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract class CrudDAOImpl<T extends SuperEntity, ID extends Serializable> implements CrudDAO<T, ID> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> entity;

    public CrudDAOImpl(){
        entity = (Class<T>) (((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    @Override
    public Session  getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) throws Exception {
        getSession().save(entity);
    }

    @Override
    public void update(T entity) throws Exception {
        getSession().update(entity);
    }

    @Override
    public void delete(ID key) throws Exception {
        getSession().delete(getSession().load(entity,key));
    }

    @Override
    public Optional<T> find(ID key) throws Exception {
        return Optional.ofNullable(getSession().find(entity,key));
    }

    @Override
    public Optional<List<T>> findAll() throws Exception {
        return Optional.ofNullable(getSession().createQuery("FROM " + entity.getName()).list());
    }
}
