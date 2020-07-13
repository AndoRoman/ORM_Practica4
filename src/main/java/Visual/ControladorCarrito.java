package Visual;

import Encapsulacion.*;
import Servicios.Coleccion_por_Defecto;
import Servicios.GestorBD;
import Servicios.ProductoBD;
import Servicios.VentasBD;
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
        app.post("/comprar", ctx -> {
            String user = ctx.sessionAttribute("usuario");
            System.out.println(user);
            Map<String, Object> view = new HashMap<>();

            //Auntenticando Usuario
            if (user.matches("admin")) {


                System.out.println("Compra de " + user + " Facturada: " + CrearCompra(user)); //<--CREANDO COMPRA EN BD

                long Index = servicio.getListVentas().size(); //<-- indice de ultima venta
                System.out.println("el id : " + Index);
                Ventas aux = VentasBD.getInstance().find(Index); //Tomando venta de la BD
                System.out.println("VENTA FACTURADA: " + aux.getListaProductos().toString());
                view.put("listaProductos", aux.getListaProductos());
                view.put("total", "Total Pagado: " + aux.Precio_Total() + "($RD)    " + "     Realizada el: " + Date.from(Instant.now()));
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
                ctx.redirect("/LoginPag");
            }
        });

    }

    public static ControladorCarrito getInstancia(){
        if(instancia==null){
            instancia = new ControladorCarrito();
        }
        return instancia;
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
        //CREANDO NUEVA VENTA en La app
        Ventas aux = new Ventas();
        aux.setId(servicio.getListVentas().size());
        aux.setFechaCompra(Date.from(Instant.now()).toString());
        aux.setNombreCliente(i.getUsuario());



        //CONVIRTIENDO PRODUCTOS DEL CARRITO A PRODUCTOS VENDIDOS
        List<ventaProducto> vendidos = new ArrayList<>();
        for (Producto p: i.getListaProductos()) {
            ProductoVendido unProductoVendido = new ProductoVendido(p.getNombre(), p.getPrecio());
            vendidos.add(new ventaProducto(unProductoVendido, cantidadProducto(unProductoVendido.getNombre(), i)));
        }
        aux.setListaProductos(vendidos);

        servicio.SetVenta(aux);

        //CREANDO NUEVA VENTA EN BD
        VentasBD.getInstance().crear(new Ventas(Date.from(Instant.now()).toString(), aux.getNombreCliente(), aux.getListaProductos()));

        return true; //VentasBD.getInstance().GetAllVentas().contains(aux);

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
        //CLONO EL PRODUCTO QUE SE QUIERE AGREGAR DESDE LA BD
        nuevoProducto = ProductoBD.getInstancia().find(indexProduct);
        //LO AGREGO AL CARRITO
        servicio.getListCarros().get(indexUser).getListaProductos().add(nuevoProducto);
    }


    //DEBIDO A QUE AL CARRITO SE AGREGA CADA PRODUCTO COMO UN OBJECTO COMO TAL SE OBTIENE LA CANTIDAD DE ESTA FORMA
    private int cantidadProducto(String nombreProducto , Carrito cart){
        int cant = 0;
        for (Producto p: cart.getListaProductos()) {
            if (p.getNombre().matches(nombreProducto)){
                cant++;
            }
        }
        return cant;
    }


}
