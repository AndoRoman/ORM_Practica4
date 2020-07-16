package Visual;

import Encapsulacion.Carrito;
import Encapsulacion.Foto;
import Encapsulacion.Producto;
import Servicios.*;
import io.javalin.Javalin;
import io.javalin.http.InternalServerErrorResponse;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;

public class ControladorPlantilla {
    Coleccion_por_Defecto servicio = Coleccion_por_Defecto.getInstancia();
    ControladorCarrito servicioCarrito = ControladorCarrito.getInstancia();

    public ControladorPlantilla() {
        registrarPlantilla();
    }

    public void registrarPlantilla() {
        JavalinRenderer.register(JavalinThymeleaf.INSTANCE, ".html");
    }

    public void Rutas(Javalin app){

        app.routes(() -> {
            //HOME
            path("/", () -> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null){
                        ctx.cookie("usuario",ctx.cookie("JSESSIONID"));
                        ctx.sessionAttribute("usuario" ,ctx.cookie("JSESSIONID"));
                    }
                    List<Producto> listaProductos = ProductoBD.getInstancia().findAll();
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    String cant = String.valueOf(aux.getListaProductos().size());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Bienvenido");
                    modelo.put("listaProducto", listaProductos);
                    modelo.put("item", "Carrito de Compras(" + cant + ")");
                    try{
                        if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                            modelo.put("admin", "Administración");
                            modelo.put("adminProduct", "Gestion de Productos");
                            modelo.put("OUT", "Cerrar Session");
                        }else {
                            modelo.put("IN", "Iniciar Session");
                        }
                    }catch(Exception e){

                    }
                    ctx.render("/Plantilla/Inicio/index.html", modelo);
                });

                //AGREGAR PRODUCTO
                app.post("/agregarProduct", ctx -> {
                    String NombreProducto = ctx.formParam("producto_nombre");
                    //INSTANCIA
                    servicioCarrito.AgregarAlCarro(NombreProducto, ctx.sessionAttribute("usuario"));
                    ctx.redirect("/Carrito.html");
                });
            });


            //VISTA DE DESCRIPCION DE PRODUCTO
            app.post("/Producto", ctx -> {
                String id_string = ctx.formParam("idProducto");
                int id = Integer.parseInt(id_string);

                Carrito auxcart = servicio.getCarro(ctx.sessionAttribute("usuario"));
                String cant = String.valueOf(auxcart.getListaProductos().size());

                Map<String, Object> modelo = new HashMap<>();
                Producto aux = ProductoBD.getInstancia().find(id);
                Foto auxfoto= null;
                for (Foto f: aux.getProductFoto()) {
                    auxfoto = f;
                    break;
                }
                modelo.put("foto", auxfoto);
                modelo.put("producto", aux);
                modelo.put("comentarios", ComentarioBD.getInstance().getComentarios(aux.getId()));
                modelo.put("item", "Carrito de Compras(" + cant + ")");
                try {
                    if (ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        modelo.put("admin", "Administración");
                        modelo.put("adminProduct", "Gestion de Productos");
                        modelo.put("OUT", "Cerrar Session");
                        modelo.put("usuario", "admin");
                    } else {
                        modelo.put("IN", "Iniciar Session");
                    }
                } catch (Exception e) {

                }
                ctx.render("/Plantilla/Producto/index.html", modelo);
            });


            //VISTA DEL ADMINISTRADOR
            path("/ListCompras.html", ()-> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null){
                        ctx.render("/Plantilla/AdminPag/dist/401.html");
                        ctx.cookie("usuario",ctx.cookie("JSESSIONID"));
                        ctx.sessionAttribute("usuario" ,ctx.cookie("JSESSIONID"));
                    }
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    Map<String, Object> view = new HashMap<>();
                    view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                    view.put("ventasProductos", VentasBD.getInstance().findAll());
                    if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        view.put("admin", "Lista de Compras Realizadas");
                        view.put("adminProduct", "Gestion de Productos");
                        view.put("OUT", "Cerrar Session");
                    }

                    ctx.render("/HTML/ListCompras.html", view);
                });
            });


            //VISTA DE MODIFICACION DE PRODUCTOS
            path("/Gestor", ()-> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null || ctx.cookie("usuario") == null){
                        ctx.render("/Plantilla/AdminPag/dist/401.html");
                        ctx.cookie("usuario",ctx.cookie("JSESSIONID"));
                        ctx.sessionAttribute("usuario" ,ctx.cookie("JSESSIONID"));
                    }
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    Map<String, Object> view = new HashMap<>();
                    view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                    if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        view.put("admin", "Lista de Compras Realizadas");
                        view.put("adminProduct", "Gestion de Productos");
                        view.put("OUT", "Cerrar Session");
                        view.put("listaProductos", servicio.getListProduct());
                        view.put("fotos", servicio.getListfoto());
                        view.put("boton", "Buscar Producto");
                    }
                    ctx.render("/HTML/Gestor.html", view);
                });
            });

            //VISTA DE CARRITO
            path("/Carrito.html", ()-> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null){
                        ctx.cookie("usuario",ctx.cookie("JSESSIONID"));
                        ctx.sessionAttribute("usuario" ,ctx.cookie("JSESSIONID"));
                    }
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    Map<String, Object> view = new HashMap<>();

                    try {
                        view.put("user", "Carrito de: " +ctx.sessionAttribute("usuario"));
                        if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                            view.put("admin", "Lista de Compras Realizadas");
                            view.put("adminProduct", "Gestion de Productos");
                            view.put("OUT", "Cerrar Session");
                        }
                    }catch(Exception e){
                        view.put("user", "Tu Carrito de Compras");
                    }
                    view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                    view.put("listaProductos", aux.getListaProductos());
                    view.put("total", "Total a Pagar: " + monto(aux.getListaProductos()) + "($RD)");
                    ctx.render("/HTML/Carrito.html", view);

                });
            });
        });


        //MANEJADOR DE EXCEPCIONES
        app.exception(FileNotFoundException.class, (e, ctx) -> {
            ctx.status(404);
        }).error(404, ctx -> {
            ctx.render("/Plantilla/AdminPag/dist/404.html");
        });
        app.exception(InternalServerErrorResponse.class, (e, ctx) -> {
            ctx.status(500);
        }).error(500, ctx -> {
            ctx.render("/Plantilla/AdminPag/dist/500.html");
        });
        app.exception(UnauthorizedResponse.class, (e, ctx) -> {
           ctx.status(401);
        }).error(401, ctx -> {
            ctx.render("/Plantilla/AdminPag/dist/401.html");
        });


    }



    private double monto(List<Producto> P){
        double total = 0;
        for (Producto i: P){
            total = total + i.getPrecio().floatValue();
        }
        return total;
    }

}

