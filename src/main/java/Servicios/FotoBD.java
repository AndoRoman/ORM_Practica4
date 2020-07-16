package Servicios;

import Encapsulacion.Foto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;

public class FotoBD extends GestorBD{
    private static FotoBD instancia;
    public FotoBD() {
        super(Foto.class);
    }

    public static FotoBD getInstancia(){
        if (instancia == null) {
            instancia = new FotoBD();
        }
        return instancia;
    }

    @Transactional
    public void agregarFoto(String nombre, String tipo, String lafoto, String id_producto){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO PRODUCTO_FOTOS (PRODUCTO_ID, LAFOTO,NOMBREFOTO,TIPO)VALUES(:id,:lafoto,:nombreFoto,:tipo)");
        query.setParameter("id", Integer.parseInt(id_producto));
        query.setParameter("lafoto", lafoto);
        query.setParameter("nombreFoto", nombre);
        query.setParameter("tipo", tipo);
        query.executeUpdate();
        em.getTransaction().commit();
    }
}
