package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class Foto implements Serializable {

    private String nombreFoto;
    private String tipo;
    @Lob
    private String lafoto;

    public Foto(){
    }

    public Foto(String nombreFoto, String tipo, String lafoto) {
        this.nombreFoto = nombreFoto;
        this.tipo = tipo;
        this.lafoto = lafoto;
    }

    public String getNombreFoto() {
        return nombreFoto;
    }

    public void setNombreFoto(String nombre) {
        this.nombreFoto = nombre;
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
