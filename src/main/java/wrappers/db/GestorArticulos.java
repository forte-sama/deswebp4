package wrappers.db;

import models.Articulo;

/**
 * Created by forte on 08/06/16.
 */
public class GestorArticulos extends EntityManagerCRUD<Articulo> {

    private static GestorArticulos inst;

    private GestorArticulos() {
        super(Articulo.class);
    }

    public static GestorArticulos getInstance() {
        if(inst == null){
            inst = new GestorArticulos();
        }
        return inst;
    }
}
