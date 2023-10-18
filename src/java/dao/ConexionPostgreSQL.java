package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgreSQL {
    private static final String JDBC_URL = "jdbc:postgresql://containers-us-west-164.railway.app:6488/railway";
    private static final String JDBC_USER = "postgres";
    private static final String JDBC_PASS = "ui54lpUcR7lxDE2G64IJ";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de la base de datos", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }
}
