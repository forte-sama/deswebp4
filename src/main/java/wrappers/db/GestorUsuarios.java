package wrappers.db;

import models.Usuario;

import java.sql.Connection;
import java.sql.SQLException;

import static javafx.scene.input.KeyCode.T;
import static utils.Utils.stringValido;
import static wrappers.db._GestorUsuarios.getUsuario;

/**
 * Created by forte on 08/06/16.
 */
public class GestorUsuarios extends EntityManagerCRUD<Usuario> {

    private static GestorUsuarios inst;

    private GestorUsuarios() {
        super(Usuario.class);
    }

    public static GestorUsuarios getInstance() {
        if(inst == null){
            inst = new GestorUsuarios();
        }
        return inst;
    }

    /** CRUD METHODS */
    public boolean crear(Usuario user) {
        boolean success = false;

        if(validarDatos(user,true)) {
            super.crear(user);
            success = true;
        }

        return success;
    }


    /** OTHER METHODS */
    private boolean validarDatos(Usuario target, boolean estaCreando) {
        boolean validUsername = stringValido(target.getUsername(), 50);
        boolean validPassword = stringValido(target.getPassword(), 50);
        boolean validNombre   = stringValido(target.getNombre(),50);

        if(estaCreando) {
            validUsername = validUsername && esUsernameNuevo(target.getUsername());
        }
        else {
            validNombre = validNombre && esUsernameExistente(target.getUsername());
        }

        return validNombre && validPassword && validUsername;
    }

    private boolean esUsernameExistente(String username) {
        Usuario us = find(username);

        return us != null;
    }

    public boolean credencialesValidas(String username, String password) {
        boolean exito = true;
        Connection con = null;

        try {
            con = DB.getConnection();
            Usuario userTarget = getUsuario(username);

            //si no encontro usuario con username, falla
            if(userTarget != null) {
                //si username/password no coinciden, falla
                if(!userTarget.getPassword().contentEquals(password)) {
                    exito = false;
                }
            }
            else {
                exito = false;
            }
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
            //si ocurrio algun fallo en la bd, falla
            exito = false;
        }

        return exito;
    }

    private boolean esUsernameNuevo(String target) {
        Usuario user = find(target);

        return user == null;
    }
}
