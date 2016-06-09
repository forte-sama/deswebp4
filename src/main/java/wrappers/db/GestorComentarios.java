package wrappers.db;

import models.Comentario;

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
}
