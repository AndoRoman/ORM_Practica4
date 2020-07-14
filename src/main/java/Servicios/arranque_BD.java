package Servicios;

import Encapsulacion.Producto;
import Encapsulacion.Usuario;
import org.h2.tools.Server;

import java.math.BigDecimal;
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

        //Producto
        ProductoBD.getInstancia().crear(new Producto(1, "Producto de Prueba",new BigDecimal("000"), "Este es un producto bueno, bonito y barato. Como te gustan"));

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
