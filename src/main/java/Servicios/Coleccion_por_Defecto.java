package Servicios;

import Encapsulacion.Carrito;
import Encapsulacion.Producto;
import Encapsulacion.VentasProductos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Coleccion_por_Defecto {
    private static Coleccion_por_Defecto instancia;
    private List<Producto> listProduct = new ArrayList<>();
    private List<Producto> indexes = new ArrayList<>();
    private List<Carrito> listCarros = new ArrayList<>();
    private List<VentasProductos> listVentas = new ArrayList<>();

    private Coleccion_por_Defecto(){

        //reñenando lista de productos por defecto en el carrito

        //indexes.add(new Producto(1, "ticket",new BigDecimal("000"), 1));

        //Añadiendo Productos
        listProduct.add(new Producto(listProduct.size() + 1, "ticket",new BigDecimal("000"), 1));
        listProduct.add(new Producto(listProduct.size() + 1, "Lata de Maiz", new BigDecimal("50"), 1));
        listProduct.add(new Producto(listProduct.size() + 1, "Lata de Salsa", new BigDecimal("75"), 1));
        listProduct.add(new Producto(listProduct.size()+ 1, "Espaguetis", new BigDecimal("30"), 1));
        listProduct.add(new Producto(listProduct.size()+ 1, "Pollo", new BigDecimal("38"), 1));
        listProduct.add(new Producto(listProduct.size() + 1, "Producto de Prueba",new BigDecimal("1234"), 1));

    }

    public static Coleccion_por_Defecto getInstancia(){
        if(instancia==null){
            instancia = new Coleccion_por_Defecto();
        }
        return instancia;
    }


    public List<VentasProductos> getListVentas() {
        return listVentas;
    }

    public void setListVentas(List<VentasProductos> listVentas) {
        this.listVentas = listVentas;
    }

    //INGRESA UNA VENTA A LA LISTA
    public void SetVenta(VentasProductos NuevaVenta){
        listVentas.add(NuevaVenta);
    }

    public List<Producto> getListProduct() {
        return listProduct;
    }

    public void setListProduct(List<Producto> listProduct) {
        this.listProduct = listProduct;
    }

    //REGRESA UN CARRITO DE COMPRA
    public Carrito getCarro(String user) {
        Carrito carro = null;
        for (Carrito cart: listCarros){
            if(cart.getUsuario().matches(user)){
                carro = cart;
                break;
            }
        }
        if(carro==null){
            carro = new Carrito(listCarros.size() + 1, indexes, user);

            listCarros.add(carro);
        }
        return carro;
    }

    public void clearIndexes(){
        indexes.clear();
    }

    public List<Carrito> getListCarros(){
        return listCarros;
    }
}
