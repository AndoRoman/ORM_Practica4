package Encapsulacion;

import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.io.Serializable;

@Embeddable
public class Foto implements Serializable {

    private String nombre;
    private String tipo;
    @Lob
    private String lafoto;

    public Foto(){

    }

    public Foto(String nombre, String tipo, String lafoto) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.lafoto = lafoto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLafoto() {
        return lafoto;
    }

    public void setLafoto(String lafoto) {
        this.lafoto = lafoto;
    }
}
