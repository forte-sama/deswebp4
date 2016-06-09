package wrappers.db;

import models.Articulo;
import models.Usuario;

import static utils.Utils.stringValido;

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
