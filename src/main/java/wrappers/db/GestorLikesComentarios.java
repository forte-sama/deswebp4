package wrappers.db;

import models.*;
import wrappers.db.EntityManagerCRUD;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by forte on 10/06/16.
 */
public class GestorLikesComentarios extends EntityManagerCRUD<LikeComentario> {

    private static GestorLikesComentarios inst;

    private GestorLikesComentarios() {
        super(LikeComentario.class);
    }

    public static GestorLikesComentarios getInstance() {
        if (inst == null) {
            inst = new GestorLikesComentarios();
        }

        return inst;
    }

    public boolean darLike(Usuario usuario, Comentario comentario) {
        boolean yaLoHizo = yaDioLike(usuario,comentario);

        boolean success = false;

        if(!yaLoHizo) {
            EntityManager em = getEntityManager();

            //do the thing
            try {
                em.getTransaction().begin();

                //do the exact thing
                LikeComentario lc = new LikeComentario();
                lc.setUsuario(usuario);
                lc.setComentario(comentario);

                success = super.crear(lc);

                em.getTransaction().commit();

            } catch (Exception ex) {
                throw  ex;
            } finally {
                em.close();
            }
        }

        return success;
    }

    public boolean yaDioLike(Usuario usuario, Comentario comentario) {
        boolean success = false;

        EntityManager em = getEntityManager();


        //do the thing
        try {
            //do the exact thing
            String jpql = "SELECT lc FROM LikeComentario lc WHERE lc.usuario=:user AND lc.comentario=:com";
            TypedQuery<LikeComentario> query = em.createQuery(jpql, LikeComentario.class);

            query.setParameter("user",usuario);
            query.setParameter("com",comentario);

            //si no encontro un like, puede dar like
            success = !query.getResultList().isEmpty();

        } catch (Exception ex) {
            throw  ex;
        } finally {
            em.close();
        }

        return success;
    }

    public Long likeCount(Comentario comentario) {
        Long resp = (long)0;

        EntityManager em = getEntityManager();

        //do the thing
        try {
            //do the exact thing
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(lc) " +
                    "FROM LikeComentario lc " +
                    "WHERE lc.comentario = :comment", Long.class);

            resp = query.setParameter("comment",comentario).getSingleResult();
        } catch (Exception ex) {
            throw  ex;
        } finally {
            em.close();
        }

        return resp;
    }
}
