package Servicios;

import Encapsulacion.Carrito;
import Encapsulacion.Producto;
import io.javalin.Javalin;

import java.util.HashMap;
import java.util.Map;

public class GestorComentarios {

    public GestorComentarios(Javalin app){

        //AGREGANDO COMENTARIO
        app.post("/agregarcomentario", ctx -> {
            String texto = ctx.formParam("comentario");
            String id_producto = ctx.formParam("id");
            String autor = ctx.sessionAttribute("usuario");

            Producto aux = ProductoBD.getInstancia().find(Integer.parseInt(id_producto));
            ComentarioBD.getInstance().agregarComentario(aux, texto, autor, id_producto);

            Map<String, Object> modelo = new HashMap<>();


            Carrito auxcart =   Coleccion_por_Defecto.getInstancia().getCarro(ctx.sessionAttribute("usuario"));
            String cant = String.valueOf(auxcart.getListaProductos().size());
            modelo.put("LasFOTOS", aux.getProductFoto());
            modelo.put("producto", aux);
            modelo.put("comentarios", ComentarioBD.getInstance().getComentarios(aux.getId()));
            modelo.put("item", "Carrito de Compras(" + cant + ")");
            try{
                if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                    modelo.put("admin", "Administración");
                    modelo.put("adminProduct", "Gestion de Productos");
                    modelo.put("OUT", "Cerrar Session");
                    modelo.put("usuario", "admin");
                }else {
                    modelo.put("IN", "Iniciar Session");
                }
            }catch(Exception e){

            }
            ctx.render("/Plantilla/Producto/index.html", modelo);
        });

        //Eliminar Comentario
        app.post("/eliminarcomentario", ctx -> {
            String texto = ctx.formParam("comentario");
            String id_producto = ctx.formParam("id");
            String autor = ctx.formParam("autor");

            Producto aux = ProductoBD.getInstancia().find(Integer.parseInt(id_producto));
            ComentarioBD.getInstance().eliminarComentario(aux, texto, autor, id_producto);

            Map<String, Object> modelo = new HashMap<>();


            Carrito auxcart = Coleccion_por_Defecto.getInstancia().getCarro(ctx.sessionAttribute("usuario"));
            String cant = String.valueOf(auxcart.getListaProductos().size());
            modelo.put("LasFOTOS", aux.getProductFoto());
            modelo.put("producto", aux);
            modelo.put("comentarios", ComentarioBD.getInstance().getComentarios(aux.getId()));
            modelo.put("item", "Carrito de Compras(" + cant + ")");
            try{
                if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                    modelo.put("admin", "Administración");
                    modelo.put("adminProduct", "Gestion de Productos");
                    modelo.put("OUT", "Cerrar Session");
                    modelo.put("usuario", "admin");
                }else {
                    modelo.put("IN", "Iniciar Session");
                }
            }catch(Exception e){

            }
            ctx.render("/Plantilla/Producto/index.html", modelo);
        });



    }

}
