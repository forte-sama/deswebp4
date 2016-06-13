package wrappers.db;

import models.Articulo;
import models.Comentario;
import models.Usuario;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static utils.Utils.stringValido;

/**
 * Created by forte on 08/06/16.
 */
public class GestorArticulos extends EntityManagerCRUD<Articulo> {

    private static GestorArticulos inst;
    private Integer pageSize;
    private boolean hasMore;

    private GestorArticulos() {
        super(Articulo.class);

        hasMore  = false;
        pageSize = 5;
    }

    public static GestorArticulos getInstance() {
        if(inst == null){
            inst = new GestorArticulos();
        }
        return inst;
    }

    /** CRUDS METHODS */
    @Override
    public boolean crear(Articulo articulo) {
        boolean success = false;

        //validar datos
        boolean datosValidos = stringValido(articulo.getTitulo(),500) && stringValido(articulo.getCuerpo(),10000);
        datosValidos = datosValidos && articulo.getAutor() != null;

        if(datosValidos) {
            success = super.crear(articulo);
        }

        return success;
    }

    @Override
    public boolean editar(Articulo articulo) {
        boolean success = false;

        boolean datosValidos = stringValido(articulo.getTitulo(),500) && stringValido(articulo.getCuerpo(),10000);

        if(datosValidos) {
            success = super.editar(articulo);
            articulo.limpiarEtiquetas();
        }

        return success;
    }

    /** OTHER METHODS */

    public List<Articulo> find_page(Integer pageNumber) {

        int offset = (pageNumber - 1) * pageSize;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        TypedQuery<Articulo> query = em.createQuery("SELECT a FROM Articulo a ORDER BY a.fecha DESC", Articulo.class);

        hasMore = query.getResultList().size() >= offset + pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        List<Articulo> resp = query.getResultList();

        return resp;
    }

    public List<Articulo> find_by_tag(Integer pageNumber, String etiqueta) {

        int offset = (pageNumber - 1) * pageSize;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

//        TypedQuery<Articulo> query = em.createQuery("SELECT a " +
//                "FROM Articulo a, Etiqueta e " +
//                "WHERE e.etiqueta = :etiqueta AND e.articulo = a " +
//                "ORDER BY a.fecha DESC", Articulo.class);

        TypedQuery<Articulo> query = em.createQuery("SELECT e.articulo " +
                                                    "FROM Etiqueta e " +
                                                    "WHERE e.etiqueta = :etiqueta " +
                                                    "ORDER BY e.articulo.fecha DESC", Articulo.class);

        query.setParameter("etiqueta",etiqueta);

        hasMore = query.getResultList().size() > offset + pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        List<Articulo> resp = query.getResultList();

        return resp;
    }

    public boolean hasMoreArticles() {
        return hasMore;
    }
}
