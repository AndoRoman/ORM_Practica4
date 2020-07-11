package Servicios;

import Encapsulacion.Ventas;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class VentasBD extends GestorBD<Ventas> {
    private static VentasBD instancia;

    public VentasBD() {
        super(Ventas.class);
    }

    public static VentasBD getInstance(){
        if(instancia==null){
            instancia = new VentasBD();
        }
        return instancia;
    }

    public List<Ventas> GetAllVentas() {
        EntityManager em = getEntityManager();
        Query query = em.createQuery("select e from Ventas", Ventas.class);
        List<Ventas> lista = query.getResultList();
        return lista;
    }


}
