package dao;

import java.util.List;
import models.Producto;

public interface ProductoDAO {
    int obtenerTotalProductos();
    void insertar(Producto producto);
    void actualizar(Producto producto);
    void eliminar(int codigo);
    Producto ver(int codigo);
    List<Producto> buscarProductosPorNombre(String nombre);
    List<Producto> listarTodos(int pagina, int cantidadPorPagina);
    String getDatabaseType();
}
