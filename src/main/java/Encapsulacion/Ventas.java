package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Ventas")
public class Ventas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fechaCompra;
    private String nombreCliente;

    @ManyToMany
    private List<Producto> listaProductos;


    public Ventas(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Transient
    public Double Precio_Total() {
        Double monto = 0.0;
        List<Producto> productos = getListaProductos();
        for (Producto p : productos) {
            monto += (p.getPrecio()).doubleValue()*p.getCantidad();
        }
        return monto;
    }

}
