package Servicios;

import Encapsulacion.ValidacionSesion;

public class ValidacionSesionBD extends GestorBD<ValidacionSesion>{
    private static ValidacionSesionBD instancia;

    public ValidacionSesionBD() {
        super(ValidacionSesion.class);
    }

    public static ValidacionSesionBD getInstance() {
        if (instancia == null) {
            instancia = new ValidacionSesionBD();
        }
        return instancia;
    }

}
