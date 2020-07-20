package Servicios;

import Encapsulacion.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@SuppressWarnings("unchecked")
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

    public List<Producto> findByNombre(String nombre){
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Producto e where e.nombre like :nombre", Producto.class);
        query.setParameter("nombre", nombre+"%");
        return query.getResultList();
    }

    public List<Producto> productosXpagina(int pagina) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCTO", Producto.class);
        query.setFirstResult((pagina - 1) * 10);
        query.setMaxResults(10);
        return query.getResultList();

    }

    public List<Producto> BuscarTODO() {

        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM PRODUCTO", Producto.class);
        return query.getResultList();
    }

    public int cantProducto(){
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT COUNT(P.id) FROM Producto P";
        Query countQuery = entityManager.createQuery(countQ);//consulta JQPL
        return ((Number) countQuery.getSingleResult()).intValue();
    }

}
