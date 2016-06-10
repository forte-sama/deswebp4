package models;

import org.omg.CORBA.COMM_FAILURE;

import javax.persistence.*;

/**
 * Created by forte on 31/05/16.
 */
@Entity
public class Comentario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 2000)
    private String comentario;
    @OneToOne(fetch = FetchType.EAGER)
    private Usuario autor;
    @OneToOne(fetch = FetchType.LAZY)
    private Articulo articulo;
    private boolean voto;

    public Comentario() { }

    public Comentario(long id, String comentario, Usuario autor, Articulo articulo) {
        this.id = id;
        this.comentario = comentario;
        this.autor = autor;
        this.articulo = articulo;
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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autorId) {
        this.autor = autorId;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public boolean isVoto() {
        return voto;
    }

    public void setVoto(boolean voto) {
        this.voto = voto;
    }
}
