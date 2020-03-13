package com.uniovi.tests.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.uniovi.entities.User;

public class DataBaseAccess {
	static public Connection getConnection() {
		/*
		 * Crea una conexión usando los datos en el archivo config.properties
		 */
		Properties prop = new Properties();
		String URL = null;
		String USERNAME = null;
		String PASSWORD = null;

		InputStream input;
		try {
			input = new FileInputStream("src/main/resources/application.properties");
		
	
			// load properties file
			prop.load(input);
	
			// get the property values
			URL = prop.getProperty("spring.datasource.url");
			USERNAME = prop.getProperty("spring.datasource.username");
			PASSWORD = prop.getProperty("spring.datasource.password");
	
			return DriverManager.getConnection(URL, USERNAME, PASSWORD);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static List<User> listUsers() {
		String query = "SELECT * FROM TUSER";
		
		Connection c = null;
		Statement st = null;
		ResultSet rs = null;
		List<User> users = new ArrayList<User>();

		try {			
			c = getConnection();	
			st = c.createStatement();
			rs = st.executeQuery(query);
			while(rs.next()) {
				User user = new User();
				user.setId( rs.getInt("id") );
				user.setEmail( rs.getString("email") );
				user.setName( rs.getString("name") );
				user.setLastName( rs.getString("lastName") );
				users.add(user);
			}						
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if ( rs != null) { rs.close(); }				
				if ( st != null) { st.close(); }
				if ( c != null) { c.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
		return users;
	}
}
