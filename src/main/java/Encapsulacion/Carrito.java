package Encapsulacion;

import java.util.List;

public class Carrito {
    private long id;
    private List<Producto> listaProductos;
    private String usuario;

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
