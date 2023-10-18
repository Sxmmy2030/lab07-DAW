package dao;

import models.Producto;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductoDAOMySQL implements ProductoDAO {
    
    /**
     * Constructor que inicializa la conexión a la base de datos
     */
    public ProductoDAOMySQL() {
    }

    @Override
    public void insertar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";
    
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar producto", e);
        }
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();

        String sql = "SELECT * FROM producto WHERE nombre LIKE ?";
        
        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + nombre + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));

                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar productos por nombre", e);
        }

        return productos;
    }

    @Override
    public List<Producto> listarTodos(int pagina, int cantidadPorPagina) {
        if (pagina <= 0) {
            pagina = 1;
        }
        
        List<Producto> productos = new ArrayList<>();
        int inicio = (pagina - 1) * cantidadPorPagina;
        String sql = "SELECT codigo, nombre, precio, stock FROM producto LIMIT ?, ?";

        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, inicio);
            statement.setInt(2, cantidadPorPagina);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Producto producto = new Producto();
                producto.setCodigo(resultSet.getInt("codigo"));
                producto.setNombre(resultSet.getString("nombre"));
                producto.setPrecio(resultSet.getDouble("precio"));
                producto.setStock(resultSet.getInt("stock"));

                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al listar productos con límite", e);
        }

        return productos;
    }
    
    @Override
    public int obtenerTotalProductos() {
        String sql = "SELECT COUNT(*) FROM producto";

        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al obtener el total de productos", e);
        }

        return 0;
    }
    
    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, stock = ? WHERE codigo = ?";

        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCodigo());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al editar el producto", e);
        }
    }

    @Override
    public void eliminar(int codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";

        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codigo);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar el producto", e);
        }
    }

    @Override
    public Producto ver(int codigo) {
        Producto producto = null;
        String sql = "SELECT nombre, precio, stock FROM producto WHERE codigo = ?";

        try (Connection connection = ConexionMySQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, codigo);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                producto = new Producto();
                producto.setCodigo(codigo);
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al consultar el producto", e);
        }

        return producto;
    }
    
    @Override
    public String getDatabaseType() {
        return "MySQL";
    }

    // Esta es una excepción personalizada que puedes usar para manejar errores específicos de la base de datos
    public class DatabaseException extends RuntimeException {
        public DatabaseException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
