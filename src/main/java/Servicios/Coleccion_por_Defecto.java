package Servicios;

import Encapsulacion.Carrito;
import Encapsulacion.Producto;
import Encapsulacion.Ventas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Coleccion_por_Defecto {
    private static Coleccion_por_Defecto instancia;
    private List<Producto> listProduct = new ArrayList<>();
    private List<Producto> indexes = new ArrayList<>();
    private List<Carrito> listCarros = new ArrayList<>();
    private List<Ventas> listVentas = new ArrayList<>();

    private Coleccion_por_Defecto(){

        //reñenando lista de productos por defecto en el carrito

        //Añadiendo Producto
        listProduct.add(new Producto(1, "ProductoPrueba",new BigDecimal("000"), "Este es una producto bueno, bonito y barato"));

    }

    public static Coleccion_por_Defecto getInstancia(){
        if(instancia==null){
            instancia = new Coleccion_por_Defecto();
        }
        return instancia;
    }


    public List<Ventas> getListVentas() {
        return listVentas;
    }

    public void setListVentas(List<Ventas> listVentas) {
        this.listVentas = listVentas;
    }

    //INGRESA UNA VENTA A LA LISTA
    public void SetVenta(Ventas NuevaVenta){
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
            clearIndexes();
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
