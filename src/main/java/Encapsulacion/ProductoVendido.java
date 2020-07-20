package Encapsulacion;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
public class ProductoVendido implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nombre;
    private BigDecimal precio;

    public ProductoVendido(){
    }

    public ProductoVendido(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
