package com.ar.apiMovies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAO {

    protected Connection conn;
    protected ResultSet rs;
    protected Statement stmt;
    private final String USER = "root";
    private final String PASSWORD = "admin123";
    private final String DATABASE = "cac_movies";
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";

    protected void conectarBase() throws Exception {
        try {
            Class.forName(DRIVER);
            String urlBaseDeDatos = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false";
            conn = DriverManager.getConnection(urlBaseDeDatos, USER, PASSWORD);
            System.out.println("Conectado...");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected void desconectarBase() throws Exception {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            System.out.println("Desconectado...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void insertarModificarEliminar(String sql) throws Exception {
        try {
            conectarBase();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            throw ex;
        } finally {
            desconectarBase();
        }
    }

    protected void consultarBase(String sql) throws Exception {
        try {
            conectarBase();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
