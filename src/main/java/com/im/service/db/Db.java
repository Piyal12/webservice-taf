package com.im.service.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.im.service.session.Session;
import com.im.service.exception.PostgresqlException;

public class Db {

	public static Connection getConnection (Session session) {
		Connection con = null;
		try {
			String dbURL = "jdbc:postgresql://"+session.getDbServer()+"/"+session.getDbName();
			con = DriverManager.getConnection(dbURL, session.getDbUserName(), session.getDbPassword());
		} catch (Exception e) {
			throw new PostgresqlException (e.getMessage());
		}
		return con;
	}
	
	public static void closeConnection (Connection con) {
		try {
			con.close();
		} catch (Exception e) {
			throw new PostgresqlException (e.getMessage());
		}
	}
	
}
