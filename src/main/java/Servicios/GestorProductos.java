package Servicios;

import Encapsulacion.Comentario;
import Encapsulacion.Foto;
import Encapsulacion.Producto;
import io.javalin.Javalin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class GestorProductos {

    Coleccion_por_Defecto servicio = Coleccion_por_Defecto.getInstancia();

    public GestorProductos(Javalin app){

        //BOTÓN AGREGAR y MODIFICAR
        app.post("/agregar", ctx -> {
            String producto = ctx.formParam("NombreProducto");
            String precio = ctx.formParam("precioProducto");
            String decri = ctx.formParam("decripcionProducto");

            //Comentario

            //foto
            /*
            List<Foto> fotoList = new ArrayList<>();
            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                try {
                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                    fotoList.add(foto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });*/

            boolean toke = false;
            for (Producto aux: servicio.getListProduct()){
                if (aux.getNombre().matches(producto)) {
                    System.out.println("El Producto: " + producto + " Ha sido Modificado: " + modificarProducto(producto, precio));
                    toke = true;
                }
            }
            if(!toke){
                System.out.println("El Producto: " + producto + " Ha sido Agregado: " + agregarProduct(producto, precio, decri));
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
    public boolean agregarProduct(String producto, String precio, String decri){

        Producto aux = new Producto(producto.hashCode(), producto, new BigDecimal(precio), decri);
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

    public boolean modificarProducto(String producto, String precio){
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
