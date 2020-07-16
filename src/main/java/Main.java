import Servicios.GestorComentarios;
import Servicios.arranque_BD;
import Servicios.GestorProductos;
import Visual.ControladorCarrito;
import Visual.ControladorPlantilla;
import Visual.ControladorSesion;
import io.javalin.Javalin;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        //INICIANDO BASE DE DATOS
        arranque_BD.getInstancia();
        //INICIANDO JAVALIN
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/HTML");
            config.addStaticFiles("/Plantilla/Inicio");
            config.addStaticFiles("/Plantilla/AdminPag");
            config.addStaticFiles("/Plantilla/AdminPag/dist");
            config.addStaticFiles("/Plantilla/Producto/css");
            config.addStaticFiles("/IMG/logo.svg");
        }).start(7000);


        new ControladorPlantilla().Rutas(app);
        //Login
        new ControladorSesion().control(app);
        //Gestor
        new GestorProductos(app);
        //Manejador del Carrito
        new ControladorCarrito().ManejadorCarro(app);
        //comentarios
        new GestorComentarios(app);
    }
}
