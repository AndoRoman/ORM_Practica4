package Servicios;

import Encapsulacion.*;
import org.h2.tools.Server;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;

public class arranque_BD {
    private static arranque_BD instancia;

    public arranque_BD() throws IOException {

        //INICIALIZANDO BASE DE DATOS
        try {
            Server.createTcpServer("-tcpPort",
                    "9092",
                    "-tcpAllowOthers",
                    "-tcpDaemon").start();
            //Abriendo el cliente web.
            String status = Server.createWebServer("-webPort", "9000", "-webDaemon", "-webAllowOthers").start().getStatus();
            //
            System.out.println("Status Web: " + status);
        } catch (SQLException ex) {
            System.out.println("Problema con la base de datos: " + ex.getMessage());
        }


        //Rellenando Base de Datos

        //Producto
        Producto platano = new Producto();
        platano.setNombre("Platano");
        platano.setPrecio(BigDecimal.valueOf(10.00));
        platano.setDescripcion("El Real Platano Compai.... de 20 Pulgada");
        ProductoBD.getInstancia().crear(platano);

        ProductoBD.getInstancia().crear(new Producto(1, "Producto de Prueba",new BigDecimal("000"), "Este es un producto bueno, bonito y barato. Como te gustan"));
        ProductoBD.getInstancia().crear(new Producto(2, "Producto de Prueba2",new BigDecimal("000"), "Este es un producto bueno, bonito y barato."));
        ProductoBD.getInstancia().crear(new Producto(3, "Producto de Prueba3",new BigDecimal("000"), "Este es un producto bueno"));
        ProductoBD.getInstancia().crear(new Producto(4, "Salami",new BigDecimal("250"), "DON PEDRO"));
        ProductoBD.getInstancia().crear(new Producto(5, "Manzana",new BigDecimal("50"), "Roja de Buena Calidad"));
        ProductoBD.getInstancia().crear(new Producto(6, "Espaguetis",new BigDecimal("25"), "MILANO"));
        ProductoBD.getInstancia().crear(new Producto(7, "Arroz",new BigDecimal("50"), "Arroz Blanco"));
        ProductoBD.getInstancia().crear(new Producto(8, "Tacos",new BigDecimal("75"), "MRIA"));
        //Foto
/*        BufferedImage bImage = ImageIO.read(new File("/IMG/supermercado.jpg"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(bImage, "jpg", bos );
        byte [] data = bos.toByteArray();
        String encodedString = Base64.getEncoder().encodeToString(data);
        Foto foto = new Foto("supermercado", "jpg", encodedString);
        FotoBD.getInstancia().agregarFoto(foto.getNombreFoto(), foto.getTipo(), foto.getLafoto(), "1");
        //Usuario*/
        UsuarioBD.getInstancia().crear(new Usuario("admin", "admin", "admin"));

    }


    public static arranque_BD getInstancia() throws IOException {
        if(instancia == null){
            instancia=new arranque_BD();
        }
        return instancia;
    }

}
