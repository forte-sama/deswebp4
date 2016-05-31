package models;

/**
 * Created by forte on 31/05/16.
 */
public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private boolean esAdministrador;
    private boolean esAutor;

    public Usuario(String username, String password, String nombre, boolean esAdministrador, boolean esAutor) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setEsAdministrador(esAdministrador);
        this.setEsAutor(esAutor);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public boolean isEsAutor() {
        return esAutor;
    }

    public void setEsAutor(boolean esAutor) {
        this.esAutor = esAutor;
    }
}
