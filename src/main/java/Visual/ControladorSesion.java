package Visual;

import Encapsulacion.Usuario;
import Encapsulacion.ValidacionSesion;
import Servicios.UsuarioBD;
import Servicios.ValidacionSesionBD;
import io.javalin.Javalin;
import org.jasypt.util.numeric.BasicIntegerNumberEncryptor;

import java.math.BigInteger;

public class ControladorSesion {
    public void control(Javalin app){

        app.get("/LoginOUT", ctx -> {
            if(ctx.cookie("userssession") != null) {
                String userssession = ctx.cookie("userssession");
                LimpiezaBD(userssession);
                //Eliminando Cookies
                ctx.removeCookie("userssession");
            }
            ctx.removeCookie("usuario");
            ctx.sessionAttribute("usuario", null);
            ctx.redirect("/");
        });

        //VERIFICANDO SESSIÓN
        app.get("/LoginPag", ctx -> {
            String user = null;
            String pass= null;

            if(ctx.cookie("userssession") != null){
                try {
                     user = ValidadoCookie(ctx.cookie("userssession")).getUsuario();
                     pass = ValidadoCookie(ctx.cookie("userssession")).getPassword();
                }catch (Exception e){
                    ctx.redirect("/");
                }
                if(autenticacionBD(user, pass)) {
                    ctx.sessionAttribute("usuario", user);
                    ctx.redirect("/ListCompras.html");
                }
            }
            else{
                ctx.render("/Plantilla/AdminPag/dist/login.html");
            }
        });


        //autenticación de sesión
        app.post("/login", ctx -> {
            String user = ctx.formParam("usuario");
            String pass = ctx.formParam("password");
            String boton = ctx.formParam("check");


            if(autenticacionBD(user, pass)){
                //creando una cookie
                ctx.cookie("usuario", user);
                ctx.sessionAttribute("usuario", user);

                //RECORDAR USUARIO
                if(boton != null && ctx.sessionAttribute("userssession") == null) {
                    int login_HASH = (int) Math.random();

                    //ENCRIPTACIÓN DEL HASH de la Cookie
                    BasicIntegerNumberEncryptor numberEncryptor = new BasicIntegerNumberEncryptor();
                    numberEncryptor.setPassword("secreto");
                    BigInteger myEncryptedNumber = numberEncryptor.encrypt(new BigInteger(String.valueOf(login_HASH)));

                    // CREANDO COOKIE DE 604800 seg =  1 semana
                    ctx.cookie("userssession", myEncryptedNumber.toString(), 604800);

                    //PERSISTIENDO DATOS DE USUARIO
                    ValidacionSesionBD.getInstance().crear(new ValidacionSesion(user, login_HASH));

                }
                //redireccionando
                ctx.redirect("/ListCompras.html");
            }else {
                ctx.status(401);
            }


        });
    }

    //Funcion para eliminar la tabla de validación del usuario
    private void LimpiezaBD(String userssession) {
        //DESENCRIPTANDO
        BasicIntegerNumberEncryptor numberEncryptor = new BasicIntegerNumberEncryptor();
        numberEncryptor.setPassword("secreto");
        //HASH Limpio
        BigInteger plainLogin_hash = numberEncryptor.decrypt(new BigInteger(userssession));
        //Eliminando Datos de sesion en BD
        ValidacionSesionBD.getInstance().eliminar(plainLogin_hash.intValue());
    }


    private boolean autenticacionBD(String user, String pass){
        boolean token = false;
        try{
            if (UsuarioBD.getInstancia().find(user).getPassword().matches(pass)){
                token = true;
            }
        }catch (Exception e){

        }
        return token;
    }

    private Usuario ValidadoCookie(String userssession) {
        Usuario validation = null;
        //DESENCRIPTANDO HASH
        System.out.println("VALIDANDO...");
        BasicIntegerNumberEncryptor numberEncryptor = new BasicIntegerNumberEncryptor();
        numberEncryptor.setPassword("secreto");
        //HASH Limpio
        BigInteger plainLogin_hash = numberEncryptor.decrypt(new BigInteger(userssession));
        //Busco en la BD
        ValidacionSesion hash_user = ValidacionSesionBD.getInstance().find(plainLogin_hash.intValue());
        //extraigo usuario dueño del hash
        try {
            String casi_user = hash_user.getIduser();
            //Usuario completo
            validation = UsuarioBD.getInstancia().find(casi_user);
        }catch (Exception e){
            LimpiezaBD(userssession);

        }
        return validation;
    }

}
