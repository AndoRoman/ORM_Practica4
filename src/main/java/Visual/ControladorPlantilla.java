package Visual;

import Encapsulacion.Carrito;
import Encapsulacion.Producto;
import Servicios.Coleccion_por_Defecto;
import io.javalin.Javalin;
import io.javalin.plugin.rendering.JavalinRenderer;
import io.javalin.plugin.rendering.template.JavalinThymeleaf;

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
                    List<Producto> listaProductos = servicio.getListProduct();
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    String cant = String.valueOf(aux.getListaProductos().size());
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Bienvenido");
                    modelo.put("listaProducto", listaProductos);
                    modelo.put("item", "Carrito de Compras(" + cant + ")");
                    try{
                        if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                            modelo.put("admin", "Lista de Compras Realizadas");
                            modelo.put("adminProduct", "Gestion de Productos");
                            modelo.put("OUT", "Cerrar Session");
                        }
                    }catch(Exception e){

                    }
                    ctx.render("/HTML/Productos.html", modelo);
                });

                //AGREGAR PRODUCTO
                app.post("agregarProduct", ctx -> {
                    String NombreProducto = ctx.formParam("name");
                    //INSTANCIA
                    servicioCarrito.AgregarAlCarro(NombreProducto, ctx.sessionAttribute("usuario"));
                    ctx.result("El producto ha sido anadido al carrito");
                });
            });

            //VISTA DEL ADMINISTRADOR
            path("/ListCompras.html", ()-> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null){
                        ctx.sessionAttribute("usuario",ctx.cookie("JSESSIONID"));
                    }
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    Map<String, Object> view = new HashMap<>();
                    view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                    view.put("ventasProductos", servicio.getListVentas());
                    if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        view.put("admin", "Lista de Compras Realizadas");
                        view.put("adminProduct", "Gestion de Productos");
                        view.put("OUT", "Cerrar Session");
                    }

                    ctx.render("/HTML/ListCompras.html", view);
                });
            });


            //VISTA DE MODIFICACION DE PRODUCTOS
            path("/HTML/Gestor.html", ()-> {
                get("/", ctx -> {
                    //Dando identidad al usuario
                    if(ctx.sessionAttribute("usuario") == null){
                        ctx.sessionAttribute("usuario",ctx.cookie("JSESSIONID"));

                    }
                    Carrito aux = servicio.getCarro(ctx.sessionAttribute("usuario"));
                    Map<String, Object> view = new HashMap<>();
                    view.put("item", "Carrito de Compras(" + aux.getListaProductos().size() + ")");
                    if(ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        view.put("admin", "Lista de Compras Realizadas");
                        view.put("adminProduct", "Gestion de Productos");
                        view.put("OUT", "Cerrar Session");
                        view.put("listaProductos", servicio.getListProduct());
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

    }




    private List<Producto> getProductos() {
        List<Producto> lista = new ArrayList<>();

        lista.addAll(servicio.getListProduct());
        return lista;
    }

    private double monto(List<Producto> P){
        double total = 0;
        for (Producto i: P){
            total = total + i.getPrecio().floatValue();
        }
        return total;
    }

}

