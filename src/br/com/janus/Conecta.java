package br.com.janus;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class Conecta {

	public static String status = "";
	
	public static Connection conecta(){
		Connection con = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String url = "jdbc:mysql://localhost:3306/janus?useSSL=false";
			String user = "root";
			String password = "admin";
			con = (Connection) DriverManager.getConnection(url, user, password);
			status = "Conectado com sucesso!";
		}catch (SQLException e) {
			status = e.getMessage();
		}catch (ClassNotFoundException e) {
			status = e.getMessage();
		}catch (Exception e) {
			status = e.getMessage();
		}
		return con;
	}
	
	public Connection getConnection() {
        try {
            return (Connection) DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/janus?useSSL=false", "root", "admin");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
}
