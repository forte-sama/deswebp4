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
        if (inst == null) {
            inst = new GestorUsuarios();
            inst.crearAdminDefault();
        }
        return inst;
    }

    /** CRUD METHODS */
    @Override
    public boolean crear(Usuario user) {
        boolean success = false;

        if (validarDatos(user,true)) {
            success = super.crear(user);
        }

        return success;
    }

    @Override
    public boolean editar(Usuario user) {
        boolean success = false;

        if(validarDatos(user,false)) {
            success = super.editar(user);
        }

        return success;
    }


    /** OTHER METHODS */

    public void crearAdminDefault() {
        Usuario admin_default = new Usuario();
        admin_default.setUsername("admin");
        admin_default.setPassword("admin");
        admin_default.setNombre("admin");
        admin_default.setAdministrador(true);
        admin_default.setAutor(true);

        //editar fue usado por el merge de la superclase
        //inserta si no esta y modifica si esta
        super.editar(admin_default);
    }

    private boolean validarDatos(Usuario target, boolean estaCreando) {
        boolean validUsername = stringValido(target.getUsername(), 50);
        boolean validPassword = stringValido(target.getPassword(), 50);
        boolean validNombre   = stringValido(target.getNombre(), 50);

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

        try {
            Usuario userTarget = find(username);

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
        } catch (Exception e) {
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
