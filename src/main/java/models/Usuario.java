package models;

/**
 * Created by forte on 31/05/16.
 */
public class Usuario {
    private String username;
    private String password;
    private String nombre;
    private boolean administrador;
    private boolean autor;

    public Usuario(String username, String password, String nombre, boolean administrador, boolean autor) {
        this.setUsername(username);
        this.setPassword(password);
        this.setNombre(nombre);
        this.setAdministrador(administrador);
        this.setAutor(this.isAdministrador() || autor);
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

    public boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(boolean administrador) {
        this.administrador = administrador;
    }

    public boolean isAutor() {
        return autor;
    }

    public void setAutor(boolean esAutor) {
        this.autor = esAutor;
    }
}
