package wrappers;

import models.Usuario;
import org.h2.command.Prepared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by forte on 31/05/16.
 */
public class GestorUsuarios {
    private GestorUsuarios() { }

    public static boolean saveUsuario(Usuario target, boolean estaCreando) {
        boolean exito = true;
        Connection con = null;

        try {
            con = DB.getConnection();

            boolean datosValidos = validarDatos(target,estaCreando);

            if(datosValidos) {
                PreparedStatement pstm = null;
                //si no esta creando, esta editando un usuario
                if(estaCreando) {
                    String sql = "INSERT INTO usuarios(username,password,nombre,es_administrador,es_autor) VALUES(?,?,?,?,?);";
                    pstm = con.prepareStatement(sql);

                    pstm.setString(1,target.getUsername());
                    pstm.setString(2,target.getPassword());
                    pstm.setString(3,target.getNombre());
                    pstm.setBoolean(4,target.isAdministrador());
                    pstm.setBoolean(5,target.isAutor());
                }
                else {
                    String sql = "UPDATE usuarios SET password=?, nombre=?, es_administrador=?, es_autor=? WHERE username=?";
                    pstm = con.prepareStatement(sql);

                    pstm.setString(1,target.getPassword());
                    pstm.setString(2,target.getNombre());
                    pstm.setBoolean(3,target.isAdministrador());
                    pstm.setBoolean(4,target.isAutor());
                    pstm.setString(5,target.getUsername());
                }

                if(pstm != null) {
                    exito = pstm.executeUpdate() > 0;
                }
                else {
                    exito = false;
                }
            }
            else {
                exito = false;
            }
        } catch(SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return exito;
    }

    private static boolean validarDatos(Usuario target, boolean estaCreando) {
        boolean validUsername = !target.getUsername().isEmpty() && target.getUsername().length() <= 50;
        boolean validPassword = !target.getPassword().isEmpty() && target.getPassword().length() <= 50;
        boolean validNombre   = !target.getNombre().isEmpty() && target.getNombre().length() <= 50;

        if(estaCreando) {
            validUsername = validUsername && esUsernameNuevo(target.getUsername());
        }
        else {
            validNombre = validNombre && esUsernameExistente(target.getUsername());
        }

        return validNombre && validPassword && validUsername;
    }

    private static boolean esUsernameExistente(String username) {
        Usuario us = getUsuario(username);

        return us != null;
    }

    public static Usuario getUsuario(String username_target) {
        Usuario user = null;
        Connection con = null;
        String sql = "SELECT username,password,nombre,es_administrador,es_autor FROM usuarios WHERE username=?;";

        try {
            //obtener conexion
            con = DB.getConnection();
            //crear preparedstatement para ejecutar consulta
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,username_target);
            //ejecutar consulta
            ResultSet rs = pstm.executeQuery();
            //si encontro usuario, target no es usuario nuevo, de lo contrario si
            if(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String nombre   = rs.getString("nombre");
                boolean es_administrador = rs.getBoolean("es_administrador");
                boolean es_autor = rs.getBoolean("es_autor");

                user = new Usuario(username,password,nombre,es_administrador,es_autor);
            }

        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();

        } finally {
            closeConnection(con);
        }

        return user;
    }

    public static boolean deleteUsuario(String username) {
        Usuario target = getUsuario(username);

        boolean exito = true;

        //borrar usuario si existe uno con username
        if(target != null) {
            String sql = "DELETE FROM usuarios WHERE username=?;";
            Connection con = null;

            try {
                con = DB.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1,target.getUsername());

                exito = pstm.executeUpdate() > 0;
            } catch (SQLException e) {
                //TODO CAMBIAR MENSAJE DE EXCEPCION
                e.printStackTrace();
            } finally {
                closeConnection(con);
            }
        }
        else {
            exito = false;
        }

        return exito;
    }

    public static ArrayList<Usuario> getUsuarios() {
        ArrayList<Usuario> resp = new ArrayList<>();
        Connection con = null;

        try {
            //obtener conexion
            con = DB.getConnection();
            //obtener todos los registros de usuarios
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM usuarios");

            while(rs.next()) {
                resp.add(new Usuario(rs.getString("username"),
                                     rs.getString("password"),
                                     rs.getString("nombre"),
                                     rs.getBoolean("es_administrador"),
                                     rs.getBoolean("es_autor")));
            }
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return resp;
    }

    private static void closeConnection(Connection con) {
        try {
            con.close();
        } catch(SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        }
    }

    public static boolean credencialesValidas(String username, String password) {
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
        } finally {
            closeConnection(con);
        }

        return exito;
    }

    private static boolean esUsernameNuevo(String target) {
        Usuario user = getUsuario(target);

        return user == null;
    }
}
