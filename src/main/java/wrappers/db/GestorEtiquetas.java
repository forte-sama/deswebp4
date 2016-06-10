package wrappers.db;

import models.Articulo;
import models.Comentario;
import models.Etiqueta;
import models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by forte on 10/06/16.
 */
public class GestorEtiquetas extends EntityManagerCRUD<Etiqueta> {

    private static GestorEtiquetas inst;

    private GestorEtiquetas() {
        super(Etiqueta.class);
    }

    public static GestorEtiquetas getInstance() {
        if (inst == null) {
            inst = new GestorEtiquetas();
        }

        return inst;
    }

    /** CRUD METHODS */
    public List<Etiqueta> findByArticle(Articulo article) {
        List<Etiqueta> resp = new ArrayList<>();

        if(article != null) {
            EntityManager em = getEntityManager();

            //do the thing
            try {
                //do the exact thing
                String str_query = "SELECT e FROM Etiqueta e WHERE e.articulo = :articulo";
                TypedQuery<Etiqueta> query = em.createQuery(str_query, Etiqueta.class);

                resp = query.setParameter("articulo", article).getResultList();

            } catch (Exception ex) {
                throw ex;
            } finally {
                em.close();
            }
        }

        return resp;
    }

    public boolean eliminarByArticle(Articulo article) {
        List<Etiqueta> targets = findByArticle(article);

        if(targets.size() > 0) {
            for(Etiqueta e : targets) {
                if(!eliminar(e)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean crearEtiquetasByArticle(Articulo ar,String raw_etiquetas) {

        //tokenizar string de etiquetas
        Set<String> lista_raw_etiquetas = parsearEtiquetas(raw_etiquetas);

        //crear etiqueta para cada token
        for(String str : lista_raw_etiquetas) {
            Etiqueta e = new Etiqueta();

            e.setEtiqueta(str);
            e.setArticulo(ar);

            //guardar cada etiqueta
            crear(e);
        }

        return true;
    }

    /** OTHER METHODS */
    public static Set<String> parsearEtiquetas(String et_raw) {
        String[] etiquetas = et_raw.split(",");

        Set<String> resp = new HashSet<>();

        for(String str : etiquetas) {
            String str_fine = str.trim();

            //solo contar las etiquetas que no estan vacias
            if(!str_fine.isEmpty()) {
                resp.add(str_fine.toLowerCase());
            }
        }

        return resp;
    }

    public static String joinListEtiquetdas(List<Etiqueta> le) {

        ArrayList<String> resp = new ArrayList<>();

        for(Etiqueta e : le) {
            resp.add(e.getEtiqueta());
        }

        return String.join(",",resp);
    }
}
