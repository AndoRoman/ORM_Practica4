package Servicios;

import Encapsulacion.Comentario;
import Encapsulacion.Producto;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComentarioBD extends GestorBD<Comentario> {
    private static ComentarioBD instancia;

    public ComentarioBD() {
        super(Comentario.class);
    }

    public static ComentarioBD getInstance() {
        if (instancia == null) {
            instancia = new ComentarioBD();
        }
        return instancia;
    }

    @Transactional
    public List<Comentario> getComentarios(int id_producto){
        List<Comentario> comentarioList = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("SELECT * FROM COMENTARIO  WHERE PRODUCTO_ID LIKE :id", Comentario.class);
        query.setParameter("id", id_producto);
        comentarioList = query.getResultList();
        return comentarioList;
    }

    @Transactional
    public void agregarComentario(Producto producto, String comentario, String autor, String id_producto){
        Date fecha = new Date(System.currentTimeMillis());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO COMENTARIO (AUTOR,FECHA,TEXTO, PRODUCTO_ID)VALUES(:autor,:fecha,:texto,:id)");
        query.setParameter("id", Integer.parseInt(id_producto));
        query.setParameter("autor", autor);
        query.setParameter("fecha", fecha.toString());
        query.setParameter("texto", comentario);
;
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Transactional
    public void eliminarComentario (Producto producto, String comentario, String autor, String id_producto){

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("DELETE FROM COMENTARIO WHERE PRODUCTO_ID = :id AND TEXTO like :comentario AND AUTOR like :autor");
        query.setParameter("id", Integer.parseInt(id_producto));
        query.setParameter("comentario", comentario);
        query.setParameter("autor", autor);
        query.executeUpdate();
        em.getTransaction().commit();

    }
}
