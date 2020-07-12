package Servicios;

import Encapsulacion.Producto;
import io.javalin.Javalin;

import java.math.BigDecimal;

public class GestorProductos {

    Coleccion_por_Defecto servicio = Coleccion_por_Defecto.getInstancia();

    public GestorProductos(Javalin app){

        //BOTÓN AGREGAR y MODIFICAR
        app.post("/agree", ctx -> {
            int idProducto = Integer.parseInt(ctx.formParam("idProducto"));
            String producto = ctx.formParam("NombreProducto");
            String precio = ctx.formParam("precioProducto");

            boolean toke = false;
            for (Producto aux: servicio.getListProduct()){
                if (aux.getNombre().matches(producto)) {
                    System.out.println("El Producto: " + producto + " Ha sido Modificado: " + modificarProducto(idProducto, producto, precio));
                    toke = true;
                }
            }
            if(!toke){
                System.out.println("El Producto: " + producto + " Ha sido Agregado: " + agregarProduct(producto, precio));
            }

            ctx.redirect("/");
        });
        //BOTÓN ELIMINAR
        app.post("/delete", ctx -> {
            String producto = ctx.formParam("NombreProductoEliminar");
            System.out.println("Producto: "+ producto + " Ha sido Eliminado: "+eliminar(producto));
            ctx.redirect("/");
        });


    }
    public boolean agregarProduct(String producto, String precio){
        Producto aux = new Producto(producto.hashCode(), producto, new BigDecimal(precio));
        servicio.getListProduct().add(aux);
        //AGREGANDO A BD
        ProductoBD.getInstancia().crear(aux);

        return true;
    }
    public boolean eliminar(String producto){
        int index = 0;
        int id = 0;
        boolean ok = false;
        for (Producto i : servicio.getListProduct()) {
            if (i.getNombre().matches(producto)) {
                index = servicio.getListProduct().indexOf(i);
                id = i.getId();
                ok = true;
            }
        }
        servicio.getListProduct().remove(index);
        //Eliminando de BD
        ProductoBD.getInstancia().eliminar(id);
        return ok;
    }

    public boolean modificarProducto(int id, String producto, String precio){
        boolean ok = false;
        for (Producto i : servicio.getListProduct()) {
            if(i.getNombre().matches(producto)){
                i.setPrecio(new BigDecimal(precio));

                //ACTUALIZANDO EN BD
                ProductoBD.getInstancia().editar(i);
                ok = true;
                break;
            }
        }



        return ok;
    }


}
