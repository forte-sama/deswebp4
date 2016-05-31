package models;

/**
 * Created by forte on 31/05/16.
 */
public class Comentario {
    private long id;
    private String comentario;
    private long autorId;
    private long articuloId;

    public Comentario(long id, String comentario, long autorId, long articuloId) {
        this.id = id;
        this.comentario = comentario;
        this.autorId = autorId;
        this.articuloId = articuloId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public long getAutorId() {
        return autorId;
    }

    public void setAutorId(long autorId) {
        this.autorId = autorId;
    }

    public long getArticuloId() {
        return articuloId;
    }

    public void setArticuloId(long articuloId) {
        this.articuloId = articuloId;
    }
}
