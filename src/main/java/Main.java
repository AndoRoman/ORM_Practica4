import Servicios.arranque_BD;
import Servicios.GestorProductos;
import Visual.ControladorCarrito;
import Visual.ControladorPlantilla;
import Visual.ControladorSesion;
import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {

        //INICIANDO BASE DE DATOS
        arranque_BD.getInstancia();
        //INICIANDO JAVALIN
        Javalin app = Javalin.create(config -> {
            config.addStaticFiles("/HTML");
            config.addStaticFiles("/HTML/Login.html");
        }).start(7000);


        new ControladorPlantilla().Rutas(app);
        //Login
        new ControladorSesion().control(app);
        //Gestor
        new GestorProductos(app);
        //Manejador del Carrito
        new ControladorCarrito().ManejadorCarro(app);
    }
}
