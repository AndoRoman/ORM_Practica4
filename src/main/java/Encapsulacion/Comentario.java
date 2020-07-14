package Encapsulacion;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class Comentario implements Serializable {

    private String texto;
    private String autor;
    private String fecha;

    public Comentario() {

    }

    public Comentario(String texto, String autor, String fecha) {
        this.texto = texto;
        this.autor = autor;
        this.fecha = fecha;
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
