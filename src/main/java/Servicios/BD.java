package Servicios;

import org.h2.tools.Server;

import java.sql.SQLException;

public class BD {
    private static BD instancia;

    public BD(){

        //INICIALIZANDO BASE DE DATOS
        try {
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
            //Abriendo el cliente web.
            String status = Server.createWebServer("-trace", "-webPort", "0").start().getStatus();
            //
            System.out.println("Status Web: "+status);
        }catch (SQLException ex){
            System.out.println("Problema con la base de datos: "+ex.getMessage());
        }
    }


    public static BD getInstancia(){
        if(instancia == null){
            instancia=new BD();
        }
        return instancia;
    }

}
