package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "Ventas")
public class Ventas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fechaCompra;
    private String nombreCliente;

    @Transient
    private Double monto = 0D;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ventaProducto> listaProductos;

    public Ventas(){

    }

    public Ventas(String fechaCompra, String nombreCliente, List<ventaProducto> listaProductos) {
        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProductos = listaProductos;
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

    public List<ventaProducto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<ventaProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }


    @Transient
    public Double Precio_Total() {
        List<ventaProducto> productos = getListaProductos();
        for (ventaProducto p : productos) {
            this.monto += p.getTotalprecio();
        }
        return this.monto;
    }

}
