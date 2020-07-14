package Servicios;

import Encapsulacion.Comentario;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Date;

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
    public void agregarComentario(String comentario, String autor, String id_producto){
        Date fecha = new Date(System.currentTimeMillis());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("INSERT INTO PRODUCTO_COMENTARIOS (PRODUCTO_ID,AUTOR,TEXTO,FECHA)VALUES(:id,:autor,:texto,:fecha)");
        query.setParameter("id", Integer.parseInt(id_producto));
        query.setParameter("autor", autor);
        query.setParameter("texto", comentario);
        query.setParameter("fecha", fecha.toString());
        query.executeUpdate();
        em.getTransaction().commit();
    }

    @Transactional
    public void eliminarComentario (String comentario, String autor, String id_producto){

        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery("DELETE FROM PRODUCTO_COMENTARIOS WHERE PRODUCTO_ID = :id AND TEXTO like :comentario AND AUTOR like :autor");
        query.setParameter("id", Integer.parseInt(id_producto));
        query.setParameter("comentario", comentario);
        query.setParameter("autor", autor);
        query.executeUpdate();
        em.getTransaction().commit();

    }
}
