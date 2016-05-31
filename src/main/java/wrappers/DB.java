package wrappers;

import org.h2.jdbc.JdbcSQLException;
import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by forte on 30/05/16.
 */
public class DB {
    private static final String URL_CON = "jdbc:h2:tcp://localhost/~/practica3";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

    static {

        //iniciar el servidor H2
        try {
            System.out.println("Inicializando servidor H2...");
            Server.createTcpServer("-tcpPort","9092","-tcpAllowOthers").start();
            System.out.println("Servidor H2... Estado: OK!");
        }

        catch (JdbcSQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
//            e.printStackTrace();
            System.out.println("Puede que el Servidor H2 ya este Up...");
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        }

        //crear estructura inicial del esquema de la aplicacion
        try {
            //sentencias de creacion de tablas que componen el models
            String sql_usuario =    "CREATE TABLE IF NOT EXISTS usuarios\n" +
                                    "(\n" +
                                    "username VARCHAR(50) PRIMARY KEY NOT NULL,\n" +
                                    "password VARCHAR(50) NOT NULL,\n" +
                                    "nombre VARCHAR(50) NOT NULL,\n" +
                                    "es_administrador BOOLEAN,\n" +
                                    "es_autor BOOLEAN\n" +
                                    ");";
            String sql_articulo =   "CREATE TABLE IF NOT EXISTS articulos\n" +
                                    "(\n" +
                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
                                    "titulo VARCHAR(500) NOT NULL,\n" +
                                    "cuerpo VARCHAR(10000) NOT NULL,\n" +
                                    "autor VARCHAR(50),\n" +
                                    "fecha DATE,\n" +
                                    "FOREIGN KEY (autor) REFERENCES usuarios(username)\n" +
                                    ");";
            String sql_comentario = "CREATE TABLE IF NOT EXISTS comentarios\n" +
                                    "(\n" +
                                    "id BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,\n" +
                                    "texto VARCHAR(2000) NOT NULL,\n" +
                                    "autor VARCHAR(50),\n" +
                                    "articulo BIGINT,\n" +
                                    "FOREIGN KEY (autor) REFERENCES usuarios(username),\n" +
                                    "FOREIGN KEY (articulo) REFERENCES articulos(id)\n" +
                                    ");";
            String sql_articulo_etiqueta =  "CREATE TABLE IF NOT EXISTS articulos_etiquetas\n" +
                                            "(\n" +
                                            "id_articulo BIGINT,\n" +
                                            "id_etiqueta VARCHAR(20),\n" +
                                            "PRIMARY KEY(id_articulo,id_etiqueta)\n" +
                                            ");";

            String sql_usuario_admin =  "MERGE INTO usuarios(username,password,nombre,es_administrador,es_autor)\n" +
                                        "KEY(username)\n" +
                                        "VALUES('admin','admin','adminDefault','true','true');";

            //obtener conexion
            Connection con = getConnection();
            Statement stm = con.createStatement();

            System.out.println("Creando modelos...");

            //Orden de creacion de estructura importa
            stm.execute(sql_usuario);
            stm.execute(sql_articulo);
            stm.execute(sql_comentario);
            stm.execute(sql_articulo_etiqueta);
            //crear usuario admin por defecto, en caso de no existir
            stm.execute(sql_usuario_admin);

            System.out.println("Modelos... Estado: OK!");

            //cerrar conexion
            con.close();
        } catch (SQLException e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        }
    }

    public static Connection getConnection () throws SQLException {
        return DriverManager.getConnection(URL_CON,DB_USER,DB_PASS);
    }

    public static void test() {
        try {
            Connection con = getConnection();

            con.close();

            System.out.println("Base de datos... Estado: OK!");
        } catch (Exception e) {
            //TODO CAMBIAR MENSAJE DE EXCEPCION
            e.printStackTrace();
        }
    }
}
