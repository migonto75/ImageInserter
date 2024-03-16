package de.dlr.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {
	private static String SQLiteFileName =  "ImageInserter.db";
	
	//Die jar Datei immer frisch in die Library einfügen
	//Verbindungsaufbau ohne Username und Passwort
	public static Connection connectToSQLiteDB() {
		try	{
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:" + SQLiteFileName);
			return conn;
		}catch(Exception e)	{
			System.out.println(e.getMessage());
		}
		return null;
	}

	public static Connection getConnection() {
		// TODO Auto-generated method stub
		return null;
	}

}
