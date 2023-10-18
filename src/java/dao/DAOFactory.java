package dao;

import java.sql.SQLException;

public class DAOFactory {
    public static ProductoDAO getProductoDAO(String dbType) throws SQLException {
        if ("mysql".equals(dbType)) {
            return new ProductoDAOMySQL();
        } else if ("postgresql".equals(dbType)) {
            return new ProductoDAOPostgreSQL();
        } else if ("mongodb".equals(dbType)) {
            return new ProductoDAOMongoDB();
        } else {
            throw new RuntimeException("Tipo de base de datos no soportado");
        }
    }
}
