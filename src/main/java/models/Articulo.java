package models;

import wrappers.db.GestorEtiquetas;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by forte on 31/05/16.
 */
@Entity
public class Articulo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500)
    private String titulo;
    @Column(length = 10000)
    private String cuerpo;
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario autor;
    private Date fecha;

    public Articulo() { }

    public Articulo(long id, String titulo, String cuerpo, Usuario autor, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autor = autor;
        this.fecha = fecha;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public Usuario getAutor() {
        return this.autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public Date getFecha() { return this.fecha; }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaFormateada() {
        String format = "EEE, d MMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(this.fecha);
    }

    public String preview() {
        int length = this.getCuerpo().length();

        return this.getCuerpo().substring(0,length >= 70 ? 69 : length) + "...";
    }

    public List<Etiqueta> etiquetas() {
        return GestorEtiquetas.getInstance().findByArticle(this);
    }

    public boolean limpiarEtiquetas() {
        return GestorEtiquetas.getInstance().eliminarByArticle(this);
    }
}
