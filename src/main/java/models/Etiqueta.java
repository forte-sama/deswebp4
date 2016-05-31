package models;

/**
 * Created by forte on 31/05/16.
 */
public class Etiqueta {
    private long id;
    private String etiqueta;

    public Etiqueta(long id, String etiqueta) {
        this.setId(id);
        this.setEtiqueta(etiqueta);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}