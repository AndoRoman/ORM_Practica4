package Servicios;

import Encapsulacion.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ProductoBD extends GestorBD<Producto>{
    private static ProductoBD instancia;


    public ProductoBD() {
        super(Producto.class);
    }


    public static ProductoBD getInstancia(){
        if(instancia==null){
            instancia = new ProductoBD();
        }
        return instancia;
    }

    public Producto findByNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Producto e where e.nombre like :nombre", Producto.class);
        query.setParameter("nombre", nombre+"%");
        Producto P = query.getResultList();
        return P;
    }


    public List<Producto> consultaNativa(){
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Producto ", Producto.class);
        List<Producto> lista = query.getResultList();
        return lista;
    }

}
