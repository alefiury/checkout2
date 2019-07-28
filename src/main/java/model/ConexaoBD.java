package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {
    private static String url = "jdbc:postgresql://localhost:5432/banco_precos2";
    private static String user = "postgres";
    private static String password = "test123";
    private static Connection connection;
    private static boolean conectado = false;


    public static void conectar() throws SQLException {
        if (!conectado) {
            connection = DriverManager.getConnection(url, user, password);
            conectado = true;
        }
    }

    public static void desconectar() throws SQLException {
        if (conectado) {
            connection.close();
            conectado = false;
        }
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || conectado == false) {
            conectar();
        }
        return connection;
    }
}