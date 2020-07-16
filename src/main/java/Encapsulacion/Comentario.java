package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Comentario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Producto producto;
    private String texto;
    private String autor;
    private String fecha;

    public Comentario() {

    }

    public Comentario(Producto producto, String texto, String autor, String fecha) {
        this.producto = producto;
        this.texto = texto;
        this.autor = autor;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
