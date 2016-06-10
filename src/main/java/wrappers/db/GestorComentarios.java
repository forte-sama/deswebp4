package wrappers.db;

import models.Articulo;
import models.Comentario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.stringValido;

/**
 * Created by forte on 08/06/16.
 */
public class GestorComentarios extends EntityManagerCRUD<Comentario> {

    private static GestorComentarios inst;

    private GestorComentarios() {
        super(Comentario.class);
    }

    public static GestorComentarios getInstance() {
        if(inst == null){
            inst = new GestorComentarios();
        }
        return inst;
    }

    @Override
    public boolean crear(Comentario comentario) {
        boolean success = false;

        boolean datosValidos = stringValido(comentario.getComentario(), 10000);

        datosValidos = datosValidos && comentario.getAutor() != null && comentario.getArticulo() != null;

        if(datosValidos) {
            success = super.crear(comentario);
        }

        return success;
    }

    public List<Comentario> findByArticle(Articulo article) {
        List<Comentario> resp = new ArrayList<>();

        EntityManager em = getEntityManager();

        //do the thing
        try {
            //do the exact thing
            TypedQuery<Comentario> query = em.createQuery("SELECT c FROM Comentario c WHERE c.articulo = :articulo", Comentario.class);

            resp = query.setParameter("articulo",article).getResultList();


        } catch (Exception ex) {
            throw  ex;
        } finally {
            em.close();
        }

        return resp;
    }

    public Long likeCount(Articulo article) {
        Long resp = (long)0;

        EntityManager em = getEntityManager();

        //do the thing
        try {
            //do the exact thing
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(c) FROM Comentario c WHERE c.articulo = :articulo AND c.voto = true", Long.class);

            resp = query.setParameter("articulo",article).getSingleResult();
        } catch (Exception ex) {
            throw  ex;
        } finally {
            em.close();
        }

        return resp;
    }
}
