package Encapsulacion;

import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "Producto")
public class Producto implements Serializable {
    @Id
    private int id;
    @NotNull
    private String nombre;
    private BigDecimal precio;
    private String descripcion;
    /*
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Foto> productFoto;
    */

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Comentario> Comentarios;


    public Producto() {
    }

    public Producto(int id, @NotNull String nombre, BigDecimal precio, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /*
    public List<Foto> getProductFoto() {
        return productFoto;
    }

    public void setProductFoto(List<Foto> productFoto) {
        this.productFoto = productFoto;
    }*/

    public List<Comentario> getComentarios() {
        return Comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        Comentarios = comentarios;
    }
}
