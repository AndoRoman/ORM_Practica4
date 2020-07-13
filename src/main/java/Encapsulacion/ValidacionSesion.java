package Encapsulacion;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class ValidacionSesion implements Serializable {

    private String iduser;
    @Id
    private int hash_sesion;

    public ValidacionSesion(){
    }

    public ValidacionSesion(String iduser, int hash_sesion) {
        this.iduser = iduser;
        this.hash_sesion = hash_sesion;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public int getHash_sesion() {
        return hash_sesion;
    }

    public void setHash_sesion(int hash_sesion) {
        this.hash_sesion = hash_sesion;
    }
}
