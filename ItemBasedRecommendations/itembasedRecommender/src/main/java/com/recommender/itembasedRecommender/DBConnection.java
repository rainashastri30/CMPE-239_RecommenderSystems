package com.recommender.itembasedRecommender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection con;

	private static final String Driver = "com.mysql.jdbc.Driver";

	private static final String ConnectionString = "jdbc:mysql://localhost/testOne?useSSL=false";

	private static final String user = "root";

	private static final String pwd = "";

	public static Connection loadDriver() throws SQLException {
		try {

			Class.forName(Driver);

		} catch (ClassNotFoundException ex) {

			System.out.println(ex.getMessage());

		}

		con = DriverManager.getConnection(ConnectionString, user, pwd);

		return con;

	}

}
