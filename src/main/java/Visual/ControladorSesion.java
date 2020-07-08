package Visual;

import io.javalin.Javalin;

public class ControladorSesion {
    public void control(Javalin app){

        app.get("/LoginOUT", ctx -> {
            ctx.removeCookie("userssession");
            ctx.removeCookie("usuario");
            ctx.sessionAttribute("usuario", null);
            ctx.redirect("/");
        });


        //autenticación de sesión
        app.post("/login", ctx -> {
            String user = ctx.formParam("usuario");
            String pass = ctx.formParam("password");
            String boton = ctx.formParam("check");


            if(user.matches("admin")){
                //creando una cookie
                ctx.cookie("usuario", user);
                ctx.sessionAttribute("usuario", user);
                ctx.redirect("/ListCompras.html");

            }else {
                ctx.redirect("/401.html");
            }
        });


    }

}
