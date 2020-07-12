package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class ventaProducto implements Serializable {

    @Embedded
    private ProductoVendido producto;
    private int cantidad;

    public ventaProducto() {
    }


    public ventaProducto(ProductoVendido producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public ProductoVendido getProducto() {
        return producto;
    }

    public void setProducto(ProductoVendido producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Transient
    public Double getTotalprecio(){
        return this.producto.getPrecio().doubleValue()*this.cantidad;
    }

}
