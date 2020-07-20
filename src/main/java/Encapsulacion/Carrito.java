package Encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Carrito implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String usuario;
    private List<Producto> listaProductos;

    public Carrito(long id, List<Producto> listaProductos, String usuario) {
            this.id = id;
            this.listaProductos = listaProductos;
            this.usuario = usuario;
        }


    public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public List<Producto> getListaProductos() {
            return listaProductos;
        }

        public void setListaProductos(List<Producto> listaProductos) {
            this.listaProductos = listaProductos;
        }

        public String getUsuario() {
            return usuario;
        }

        public void setUsuario(String usuario) {
            this.usuario = usuario;
        }
}
