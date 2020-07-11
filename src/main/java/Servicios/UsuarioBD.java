package Servicios;

import Encapsulacion.Producto;
import Encapsulacion.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UsuarioBD extends GestorBD<Usuario> {

    private static UsuarioBD instancia;

    public UsuarioBD() {
        super(Usuario.class);
    }


    public static UsuarioBD getInstancia(){

        if(instancia==null){
            instancia = new UsuarioBD();
        }
        return instancia;
    }

    //Utilizando JPQL PARA BUSCAR USUARIOS
    public Usuario findByUsuario(String usuario){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Usuario e where e.usuuario like :usuario", Usuario.class);
        query.setParameter("usuario", usuario+"%");
        Usuario user = query.getResultList();
        return user;
    }

}
