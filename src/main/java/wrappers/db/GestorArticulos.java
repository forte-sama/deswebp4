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

    public List<Articulo> find_page(Integer pageNumber) {

        int offset = (pageNumber - 1) * pageSize;

        EntityManager em = getEntityManager();
        em.getTransaction().begin();

        TypedQuery<Articulo> query = em.createQuery("SELECT a FROM Articulo a", Articulo.class);

        hasMore = query.getResultList().size() >= offset + pageSize;

        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        List<Articulo> resp = query.getResultList();

        return resp;
    }

    public boolean hasMoreArticles() {
        return hasMore;
    }

    @Override
    public boolean crear(Articulo articulo) {
        boolean success = false;

        //validar datos
        boolean datosValidos = stringValido(articulo.getTitulo(),500) && stringValido(articulo.getCuerpo(),10000);

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
        }

        return success;
    }
}
