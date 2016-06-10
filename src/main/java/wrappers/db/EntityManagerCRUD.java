package wrappers.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created by vacax on 03/06/16.
 */
public class EntityManagerCRUD<T> {

    private static EntityManagerFactory factory;
    private Class<T> entidad;

    public EntityManagerCRUD(Class<T> Clase) {
        if(factory == null) {
            factory = Persistence.createEntityManagerFactory("persistence_unit");
        }

        this.entidad = Clase;
    }

    public javax.persistence.EntityManager getEntityManager(){
        return factory.createEntityManager();
    }

    public boolean crear(T entidad) {
        boolean success = false;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            em.persist(entidad);
            em.getTransaction().commit();

            success = true;
        } catch (Exception ex) {

            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }

        return success;
    }

    public boolean editar(T entidad) {
        boolean success = false;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            em.merge(entidad);
            em.getTransaction().commit();

            success = true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }

        return success;
    }

    public boolean eliminar(T entidad) {
        boolean success = false;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        try {
            //TODO borrar cada comentario antes de borrar articulo
            em.remove(em.contains(entidad) ? entidad : em.merge(entidad));
            em.getTransaction().commit();

            entidad = null;

            success = true;
        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw  ex;
        } finally {
            em.close();
        }

        return success;
    }

    public T find(Object id) {
        EntityManager em = getEntityManager();

        T result = null;

        try{
            result = em.find(entidad, id);
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return result;
    }

    public List<T> findAll(){
        EntityManager em = getEntityManager();

        try{
            CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(entidad);
            criteriaQuery.select(criteriaQuery.from(entidad));

            return em.createQuery(criteriaQuery).getResultList();
        } catch (Exception ex){
            throw  ex;
        }finally {
            em.close();
        }
    }
}
