package wrappers;

import models.Articulo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by forte on 02/06/16.
 */
public class GestorArticulos {

    public static boolean newArticulo(String username, String titulo, String cuerpo, Set<String> etiquetas) {
        //TODO CREAR UN ARTICULO
        String sql = "INSERT INTO articulos(titulo,cuerpo,autor,fecha) VALUES(?,?,?,?);";

        Connection con = null;

        boolean exito = validarDatos(titulo,cuerpo);

        //solo procesar informacion y guardarla, si los datos provenientes son validos
        if(exito) {
            try {
                con = DB.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);

                pstm.setString(1, titulo);
                pstm.setString(2, cuerpo.replace("'","").replace("\"",""));
                pstm.setString(3, username);
                pstm.setDate(4, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));

                exito = pstm.executeUpdate() > 0;

                if(exito) {
                    exito = GestorEtiquetas.guardarEtiquetas(etiquetas,lastArticulo(username));
                }
            } catch (SQLException e) {
                //TODO CAMBIAR MENSAJE EXCEPCION
                e.printStackTrace();
                exito = false;
            } finally {
                closeConnection(con);
            }
        }

        return exito;
    }

    public static Articulo getArticulo(long id) {
        String sql = "SELECT id,titulo,cuerpo,autor,fecha FROM articulos WHERE id=?";

        Connection con = null;
        Articulo articulo = null;

        try {
            con = DB.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setLong(1,id);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()) {
                articulo = new Articulo(id,
                                        rs.getString("titulo"),
                                        rs.getString("cuerpo"),
                                        rs.getString("autor"),
                                        rs.getDate("fecha"));
            }

        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return articulo;
    }

    public static ArrayList<Articulo> getArticulos() {
        String sql = "SELECT id,titulo,cuerpo,autor,fecha FROM articulos ORDER BY id DESC;";

        Connection con = null;

        ArrayList<Articulo> resp = new ArrayList<>();

        try {
            con = DB.getConnection();

            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while(rs.next()) {
                resp.add(new Articulo(rs.getLong("id"),
                                        rs.getString("titulo"),
                                        rs.getString("cuerpo"),
                                        rs.getString("autor"),
                                        rs.getDate("fecha")));
            }

        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return resp;
    }

    public static  boolean editArticulo(long id, String username, String titulo, String cuerpo,Set<String> etiquetas) {
        //TODO CREAR UN ARTICULO
        String sql = "UPDATE articulos SET titulo=?,cuerpo=?,autor=? WHERE id=?";

        Connection con = null;

        boolean exito = validarDatos(titulo,cuerpo);

        //solo procesar informacion y actualizarla, si los datos provenientes son validos
        if(exito) {
            try {
                con = DB.getConnection();
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, titulo);
                pstm.setString(2, cuerpo);
                pstm.setString(3, username);
                pstm.setLong(4, id);

                exito = pstm.executeUpdate() > 0;

                if(exito) {
                    exito = GestorEtiquetas.guardarEtiquetas(etiquetas,lastArticulo(username));
                }
            } catch (SQLException e) {
                //TODO CAMBIAR MENSAJE EXCEPCION
                e.printStackTrace();
                exito = false;
            } finally {
                closeConnection(con);
            }
        }

        return exito;
    }

    public static boolean deleteArticulo(long id) {
        String sql = "DELETE FROM articulos WHERE id=?";
        Connection con = null;

        boolean exito = true;

        try {
            con = DB.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1,id);

            exito = pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            exito = false;
            e.printStackTrace();
        }

        return exito;
    }

    private static long lastArticulo(String username) {
        String sql = "SELECT MAX(id) as maximo FROM articulos WHERE autor=?";
        Connection con = null;
        long resp = -1;

        try {
            con = DB.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setString(1,username);

            ResultSet rs = pstm.executeQuery();

            if(rs.next()) {
                resp = rs.getLong("maximo");
            }
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE EXCEPCION
            e.printStackTrace();
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

    private static  boolean validarDatos(String titulo, String cuerpo) {
        boolean tituloValido = !titulo.isEmpty() && titulo.length() <= 500;
        boolean cuerpoValido = !cuerpo.isEmpty() && cuerpo.length() <= 10000;

        return tituloValido && cuerpoValido;
    }
}
