package wrappers;

import models.Comentario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by forte on 03/06/16.
 */
public class GestorComentarios {
    public static boolean newComentario(String username, String comentario, long articulo_id) {
        String sql = "INSERT INTO comentarios(texto,autor,articulo) VALUES(?,?,?)";
        Connection con = null;

        boolean exito = !comentario.isEmpty() && comentario.length() <= 10000;

        if(exito) {
            try {
                con = DB.getConnection();

                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, comentario);
                pstm.setString(2, username);
                pstm.setLong(3, articulo_id);

                exito = pstm.executeUpdate() > 0;
            } catch (SQLException e) {
                //TODO CAMBIAR MENSAJE DE EXCEPCION
                e.printStackTrace();
            } finally {
                closeConnection(con);
            }
        }

        return exito;
    }

    public static boolean deleteComentario(long comentario_id) {
        String sql = "DELETE FROM comentarios WHERE id=?";
        Connection con = null;

        boolean exito = false;

        try {
            con = DB.getConnection();

            PreparedStatement pstm = con.prepareStatement(sql);
            pstm.setLong(1, comentario_id);

            exito = pstm.executeUpdate() > 0;
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
            exito = false;
        } finally {
            closeConnection(con);
        }

        return exito;
    }

    public static ArrayList<Comentario> getComentarios(long articulo_id) {
        String sql = "SELECT id,texto,autor,articulo FROM comentarios WHERE articulo=? ORDER BY id DESC";
        Connection con = null;

        ArrayList<Comentario> resp = new ArrayList<>();

        try {
            con = DB.getConnection();
            PreparedStatement pstm = con.prepareStatement(sql);

            pstm.setLong(1,articulo_id);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {
                resp.add(new Comentario(rs.getLong("id"),
                                        rs.getString("texto"),
                                        rs.getString("autor"),
                                        rs.getLong("articulo")));
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
}
