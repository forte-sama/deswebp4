package models;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by forte on 31/05/16.
 */
@Entity
public class Etiqueta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String etiqueta;
    @ManyToOne(fetch = FetchType.EAGER)
    private Articulo articulo;

    public Etiqueta() { }

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

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
