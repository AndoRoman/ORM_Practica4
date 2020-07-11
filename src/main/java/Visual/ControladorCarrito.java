package Visual;

import Encapsulacion.Carrito;
import Encapsulacion.Producto;
import Encapsulacion.Ventas;
import Servicios.Coleccion_por_Defecto;
import io.javalin.Javalin;

import java.time.Instant;
import java.util.*;

public class ControladorCarrito {

    private static ControladorCarrito instancia;
    Coleccion_por_Defecto servicio = Coleccion_por_Defecto.getInstancia();

    public void ManejadorCarro(Javalin app) {
        //Para Eliminar del carro
        app.post("/sacar", ctx -> {
            String product = ctx.formParam("nombre");
            String usuario = ctx.sessionAttribute("usuario");
            SacarDelCarro(product, usuario);
            ctx.redirect("/Carrito.html");
        });

        //BOTON COMPRAR!
        app.get("/comprar", ctx -> {
            String user = ctx.sessionAttribute("usuario");
            System.out.println(user);
            Map<String, Object> view = new HashMap<>();

            //Auntenticando Usuario
            if (user.matches("admin")) {


                System.out.println("Compra de " + user + " Facturada: " + CrearCompra(user)); //<--CREANDO COMPRA EN BD

                int Index = servicio.getListVentas().size() - 1; //<-- indices de ultima venta
                Ventas aux = servicio.getListVentas().get(Index);//BaseDatos.getInstancia().getVentaBD().get(Index); //Tomando venta de la BD
                System.out.println("VENTA FACTURADA: " + aux.getListaProductos().toString());
                view.put("listaProductos", aux.getListaProductos());
                view.put("total", "Total Pagado: " + monto(aux.getListaProductos()) + "($RD)    " + "     Realizada el: " + Date.from(Instant.now()));
                try {
                    view.put("user", "Carrito de: " + ctx.sessionAttribute("usuario"));
                    if (ctx.sessionAttribute("usuario").toString().matches("admin")) {
                        view.put("admin", "Lista de Compras Realizadas");
                        view.put("adminProduct", "Gestion de Productos");
                    }
                } catch (Exception e) {
                    view.put("user", "Tu Carrito de Compras");
                }
                ctx.render("/HTML/CompraDONE.html", view);
            } else {
                ctx.redirect("/Login.html");
            }
        });

    }

    public static ControladorCarrito getInstancia(){
        if(instancia==null){
            instancia = new ControladorCarrito();
        }
        return instancia;
    }

    private double monto(List<Producto> P){
        double total = 0;
        for (Producto i: P){
            total = total + i.getPrecio().floatValue();
        }
        return total;
    }

    private boolean CrearCompra(String user) {
        Carrito i = null;
        int posicion_carrito = 0;
        for (Carrito carroComprado: servicio.getListCarros()) {
            if (carroComprado.getUsuario().matches(user)){
                i = carroComprado;
                //Eliminamos ese carrito
                servicio.getListCarros().remove(carroComprado);
                break;
            }
        }
        //CREANDO NUEVA VENTA
        Ventas aux = new Ventas();
        aux.setId(servicio.getListVentas().size() + 1);
        aux.setFechaCompra(Date.from(Instant.now()).toString());
        aux.setNombreCliente(i.getUsuario());
        aux.setListaProductos(i.getListaProductos());

        servicio.SetVenta(aux);
        //VERIFICACIÓN
        return servicio.getListVentas().contains(aux);

    }

    private void SacarDelCarro(String nombreProducto, String usuario){
        int index = 0;
        //BUSCO EL CARRITO QUE LE PERTENECE A ESE USUARIO
        for (Carrito i : servicio.getListCarros()) {
            if(i.getUsuario().matches(usuario)){
                index = servicio.getListCarros().indexOf(i);
                break;
            }
        }
        //BUSCO EL PRODUCTO
        for (Producto rechazado: servicio.getListCarros().get(index).getListaProductos()) {
            if(rechazado.getNombre().matches(nombreProducto)){
                servicio.getListCarros().get(index).getListaProductos().remove(rechazado);
                break;
            }
        }
    }

    public void AgregarAlCarro(String nombreProducto, String user){
        int indexUser = 0, indexProduct = 0;
        Producto nuevoProducto;

        //PRIMERO BUSCO EL CARRITO QUE LE PERTENECE A ESE USUARIO
        for (Carrito i : servicio.getListCarros()) {
            if(i.getUsuario().matches(user)){
                indexUser = servicio.getListCarros().indexOf(i);
                break;
            }
        }
        //SEGUNDO BUSCO EL PRODUCTO QUE SE QUIERE AGREGAR AL CARRITO
        for (Producto j: servicio.getListProduct()) {
            if (j.getNombre().matches(nombreProducto)){
                indexProduct = servicio.getListProduct().indexOf(j);
                break;
            }
        }
        //CLONO EL PRODUCTO QUE SE QUIERE AGREGAR
        nuevoProducto = servicio.getListProduct().get(indexProduct);
        //LO AGREGO AL CARRITO
        servicio.getListCarros().get(indexUser).getListaProductos().add(nuevoProducto);
    }


}
