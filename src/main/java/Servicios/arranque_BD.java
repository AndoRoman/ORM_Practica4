package Servicios;

import Encapsulacion.Producto;
import Encapsulacion.Usuario;
import org.h2.tools.Server;

import java.sql.SQLException;

public class arranque_BD {
    private static arranque_BD instancia;

    public arranque_BD() {

        //INICIALIZANDO BASE DE DATOS
        try {
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
            //Abriendo el cliente web.
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            //
            System.out.println("Status Web: " + status);
        } catch (SQLException ex) {
            System.out.println("Problema con la base de datos: " + ex.getMessage());
        }


        //Rellenando Base de Datos

        //Productos
        for (Producto p: Coleccion_por_Defecto.getInstancia().getListProduct())
        {
            ProductoBD.getInstancia().crear(new Producto(p.getId(), p.getNombre(), p.getPrecio()));
        }
        //Usuario
        UsuarioBD.getInstancia().crear(new Usuario("admin", "admin", "admin"));

    }


    public static arranque_BD getInstancia(){
        if(instancia == null){
            instancia=new arranque_BD();
        }
        return instancia;
    }

}
