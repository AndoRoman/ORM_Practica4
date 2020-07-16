package Servicios;

import Encapsulacion.Carrito;
import Encapsulacion.Foto;
import Encapsulacion.Producto;
import io.javalin.Javalin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class GestorProductos {

    Coleccion_por_Defecto servicio = Coleccion_por_Defecto.getInstancia();

    public GestorProductos(Javalin app){

        //BOTÓN AGREGAR
        app.post("/agregar", ctx -> {
            String producto = ctx.formParam("NombreProducto");
            String precio = ctx.formParam("precioProducto");
            String decri = ctx.formParam("descripcion");

            //Comentario

            //foto
            List<Foto> fotoList = new ArrayList<>();
            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                try {
                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(producto, uploadedFile.getContentType(), encodedString);
                    fotoList.add(foto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            System.out.println("El Producto: " + producto + " Ha sido Agregado: " + agregarProduct(producto, precio, decri, fotoList));


            ctx.redirect("/");
        });

        //BOTÓN modificar
        app.post("/modificar", ctx -> {
            boolean token = false;
            String id_producto = ctx.formParam("IDproductoModificar");

            String precio = null;
            String decri = null;
            String nombre = null;


            try{
                precio = ctx.formParam("precioProducto");
                decri = ctx.formParam("descri");
                nombre = ctx.formParam("NombreProducto");

                if(precio != null){
                    token = true;
                }
            }catch (Exception e){
                token = false;
            }

            if(token){
                System.out.println("El Producto: " + nombre + " Ha sido Modificado: " + modificarProducto(id_producto, precio, nombre, decri));
                ctx.redirect("/");
            }else{

                if(ctx.sessionAttribute("usuario") == null){
                    ctx.render("/Plantilla/AdminPag/dist/401.html");
                }
                Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                Map<String, Object> view = new HashMap<>();
                view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                view.put("admin", "Lista de Compras Realizadas");
                view.put("adminProduct", "Gestion de Productos");
                view.put("OUT", "Cerrar Session");
                view.put("listaProductos", servicio.getListProduct());
                view.put("productos", ProductoBD.getInstancia().find(Integer.parseInt(id_producto)));
                view.put("boton", "Modificar");

                ctx.render("/HTML/Gestor.html", view);

            }

        });

        //BOTÓN ELIMINAR
        app.post("/delete", ctx -> {
            String producto = ctx.formParam("NombreProductoEliminar");
            System.out.println("Producto: "+ producto + " Ha sido Eliminado: "+eliminar(producto));
            ctx.redirect("/Gestor");
        });




    }
    public boolean agregarProduct(String producto, String precio, String decri, List<Foto> fotos){

        Producto aux = new Producto(producto.hashCode(), producto, new BigDecimal(precio), decri);
        servicio.getListProduct().add(aux);
        //AGREGANDO A BD
        ProductoBD.getInstancia().crear(aux);

        for (Foto f: fotos) {
            FotoBD.getInstancia().agregarFoto(f.getNombreFoto(),f.getTipo(),f.getLafoto(), String.valueOf(aux.getId()));
        }

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

    public boolean modificarProducto(String id, String precio, String nombre, String descripcion){
        boolean ok = false;
        for (Producto i : servicio.getListProduct()) {
            if(i.getId() == Integer.parseInt(id)) {
                i.setNombre(nombre);
                i.setPrecio(new BigDecimal(precio));
                i.setDescripcion(descripcion);
                //ACTUALIZANDO EN BD
                ProductoBD.getInstancia().editar(i);
                ok = true;
                break;
            }
        }



        return ok;
    }


}
