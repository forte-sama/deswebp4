package wrappers;

import models.Etiqueta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by forte on 03/06/16.
 */
public class GestorEtiquetas {
    public static String cargarEtiquetas(long id) {
        String sql  = "SELECT id_etiqueta FROM articulos_etiquetas WHERE id_articulo=?";
        Connection con = null;

        Set<String> resp = new HashSet<>();

        try {
            con = DB.getConnection();
            PreparedStatement pstmCleaning = con.prepareStatement(sql);
            pstmCleaning.setLong(1,id);

            ResultSet rs = pstmCleaning.executeQuery();

            while(rs.next()) {
                resp.add(rs.getString("id_etiqueta"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return String.join(", ",resp);
    }

    public static Set<String> cargarListaEtiquetas(long id) {
        String sql  = "SELECT id_etiqueta FROM articulos_etiquetas WHERE id_articulo=?";
        Connection con = null;

        Set<String> resp = new HashSet<>();

        try {
            con = DB.getConnection();
            PreparedStatement pstmCleaning = con.prepareStatement(sql);
            pstmCleaning.setLong(1,id);

            ResultSet rs = pstmCleaning.executeQuery();

            while(rs.next()) {
                resp.add(rs.getString("id_etiqueta"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return resp;
    }

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

    public static boolean guardarEtiquetas(Set<String> etiquetas,long id) {
        String sqlInsercion = "INSERT INTO articulos_etiquetas(id_articulo,id_etiqueta) values(?,?);";
        String sqlCleaning  = "DELETE FROM articulos_etiquetas WHERE id_articulo=?";
        Connection con = null;
        boolean exito = true;

        try {
            con = DB.getConnection();
            PreparedStatement pstmCleaning = con.prepareStatement(sqlCleaning);
            pstmCleaning.setLong(1,id);

            exito = pstmCleaning.executeUpdate() >= 0;

            if(exito) {
                PreparedStatement pstmInsercion = con.prepareStatement(sqlInsercion);

                for(String s : etiquetas) {
                    pstmInsercion.setLong(1, id);
                    pstmInsercion.setString(2, s);

                    if(pstmInsercion.executeUpdate() <= 0) {
                        exito = false;
                        break;
                    }
                }
            }
        } catch(SQLException e) {
            exito = false;
            e.printStackTrace();
        } finally {
            closeConnection(con);
        }

        return exito;
    }

    private static void closeConnection(Connection con) {
        try {
            con.close();
        } catch(SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        }
    }
}
