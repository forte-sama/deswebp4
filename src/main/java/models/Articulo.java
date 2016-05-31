package models;

import freemarker.template.SimpleDate;
import wrappers.GestorEtiquetas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

/**
 * Created by forte on 31/05/16.
 */
public class Articulo {
    private long id;
    private String titulo;
    private String cuerpo;
    private String autorId;
    private Date fecha;

    public Articulo(long id, String titulo, String cuerpo, String autor, Date fecha) {
        this.id = id;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.autorId = autor;
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

    public String getAutorId() {
        return this.autorId;
    }

    public String getFecha() {
        String format = "EEE, d MMM yyyy";
        SimpleDateFormat formatter = new SimpleDateFormat(format);

        return formatter.format(this.fecha);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String preview() {
        int length = this.getCuerpo().length();

        return this.getCuerpo().substring(0,length >= 70 ? 69 : length) + "...";
    }

    public Set<String> etiquetas() {
        return GestorEtiquetas.cargarListaEtiquetas(this.getId());
    }
}
