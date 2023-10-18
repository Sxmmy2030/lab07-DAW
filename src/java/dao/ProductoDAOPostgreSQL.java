package dao;

import models.Producto;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductoDAOPostgreSQL implements ProductoDAO {
    
    public ProductoDAOPostgreSQL() throws SQLException {
        ConexionPostgreSQL.getConnection();
    }

    public void insertar(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, stock) VALUES (?, ?, ?)";
    
        try (Connection connection = ConexionPostgreSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar producto", e);
        }
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConexionPostgreSQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM producto WHERE nombre LIKE ?")) {

            stmt.setString(1, "%" + nombre + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setCodigo(rs.getInt("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setStock(rs.getInt("stock"));

                productos.add(producto);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return productos;
    }

    public List<Producto> listarTodos(int pagina, int cantidadPorPagina) {
        
        if (pagina <= 0) {
            pagina = 1;
        }
        
        List<Producto> productos = new ArrayList<>();

        int inicio = (pagina - 1) * cantidadPorPagina;

        String sql = "SELECT codigo, nombre, precio, stock FROM producto LIMIT ? OFFSET ?";

        try (Connection connection = ConexionPostgreSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, cantidadPorPagina);
            statement.setInt(2, inicio);

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
            throw new RuntimeException("Error al listar productos con límite", e);
        }

        return productos;
    }
    
    public int obtenerTotalProductos() {
        String sql = "SELECT COUNT(*) FROM producto";
        try (Connection connection = ConexionPostgreSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el total de productos", e);
        }
        return 0;
    }
    
    
    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ?, stock = ? WHERE codigo = ?";
        try (Connection connection = ConexionPostgreSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, producto.getNombre());
            statement.setDouble(2, producto.getPrecio());
            statement.setInt(3, producto.getStock());
            statement.setInt(4, producto.getCodigo());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al editar el producto", e);
        }
    }

    @Override
    public void eliminar(int codigo) {
        String sql = "DELETE FROM producto WHERE codigo = ?";
        try (Connection connection = ConexionPostgreSQL.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, codigo);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el producto", e);
        }
    }

    @Override
    public Producto ver(int codigo) {
        Producto producto = null;
        String sql = "SELECT nombre, precio, stock FROM producto WHERE codigo = ?";
        try (Connection connection = ConexionPostgreSQL.getConnection();
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
            throw new RuntimeException("Error al consultar el producto", e);
        }
        return producto;
    }
    
    @Override
    public String getDatabaseType() {
        return "PostgreSQL";
    }
}
