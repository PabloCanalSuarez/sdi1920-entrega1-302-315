package com.uniovi.tests.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.uniovi.entities.User;

public class DataBaseAccess {
	static private Connection getConnection() {
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
		String query = "SELECT * FROM User";
		
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
				user.setRole( rs.getString("role") );
				//user.setLastName( rs.getString("lastName") );
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
	
	public static void removeUser(String email) {
		String query = "DELETE FROM User WHERE email = ?";
		
		Connection c = null;
		PreparedStatement st = null;

		try {			
			c = getConnection();	
			st = c.prepareStatement(query);	
			st.setString(1, email);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if ( st != null) { st.close(); }
				if ( c != null) { c.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	public static void removeFriends(Long idClara) {
		String query = "UPDATE Friendship " + 
				"SET accepted = 'F'" + 
				"WHERE user_to_id=? OR user_from_id=?";
		
		Connection c = null;
		PreparedStatement st = null;

		try {			
			c = getConnection();	
			st = c.prepareStatement(query);	
			st.setLong(1, idClara);
			st.setLong(2, idClara);
			st.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if ( st != null) { st.close(); }
				if ( c != null) { c.close(); }
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static Long getUserId(String email) {
		String query = "SELECT id FROM User WHERE email=?";
		
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Long result = null;

		try {			
			c = getConnection();	
			st = c.prepareStatement(query);
			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				result = rs.getLong("id");
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
		return result;
	}

	public static Integer getPostsByUser(String email) {
		String query = "SELECT count(*) FROM Post p, User u WHERE u.email=? AND p.user_id = u.id";
		
		Connection c = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		Integer result = null;

		try {			
			c = getConnection();	
			st = c.prepareStatement(query);
			st.setString(1, email);
			rs = st.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
}
