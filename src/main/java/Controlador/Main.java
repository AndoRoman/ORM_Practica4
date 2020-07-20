package Controlador;

import Servicios.GestorComentarios;
import Servicios.arranque_BD;
import Servicios.GestorProductos;
import Visual.ControladorCarrito;
import Visual.ControladorPlantilla;
import Visual.ControladorSesion;
import io.javalin.Javalin;

import java.io.IOException;

public class Main {

    private static String Conexion = "";

    public static void main(String[] args) throws IOException {

        if (args.length >= 1) {
            Conexion = args[0];
            System.out.println("Modo de Operacion: " + Conexion);
        }

        //Iniciando la base de datos.
        if (Conexion.isEmpty()) {
            arranque_BD.getInstancia();
        }
        //INICIANDO JAVALIN
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/HTML");
            config.addStaticFiles("/Plantilla/Inicio");
            config.addStaticFiles("/Plantilla/AdminPag");
            config.addStaticFiles("/Plantilla/AdminPag/dist");
            config.addStaticFiles("/Plantilla/Producto/css");
            config.addStaticFiles("/IMG/logo.svg");
        }).start(getHerokuAssignedPort());


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

    /**
     * Metodo para indicar el puerto en Heroku
     *
     * @return
     */
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 7000; //Retorna el puerto por defecto en caso de no estar en Heroku.
    }

    public static String getModoConexion() {
        return Conexion;
    }

}
