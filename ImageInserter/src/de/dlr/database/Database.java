package de.dlr.database;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class Database {
	
	public static final String basePathDB = "S:/Projekte/Programmierung/Praktikum/ImageInserter/src/de/dlr/database/";
	public static final String nameDB = "ImageInserter.db";
	public static final String tableName = "AnalyzedPics";
	public static final String driverSQLite = "jdbc:sqlite:"; 
	
	//-----------------------------------------------------------------------------------------------------//
	/**
	 * createDatabase - Datenbank erstellen
	 * @param basePathDB
	 * @param nameDB
	 */	
	public static void createDatabase() {
		
		try (Connection conn = DriverManager.getConnection(driverSQLite + basePathDB + nameDB)) {
				if (conn != null) {
					DatabaseMetaData meta = conn.getMetaData();
					System.out.println("Der Treibername lautet " + meta.getDriverName());
					System.out.println("Die Datenbank " + nameDB + " wurde erfolgreich erstellt!");
				}			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//-----------------------------------------------------------------------------------------------------//
	/**
	 * createTable - Tabelle erstellen, falls noch nicht vorhanden
	 * @param basePathDB
	 * @param nameDB
	 */
	public static void createTable() {
		
		try (Connection conn = DriverManager.getConnection(driverSQLite + basePathDB + nameDB)) {
			if (conn != null) {
				Statement stmt = conn.createStatement(); //ist für die Kommunikation zuständig
											
				String createTable = "CREATE TABLE IF NOT EXISTS AnalyzedPics" 
								   + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"
								   + "Bild BLOB,"
								   + "Name VARCHAR(260) NOT NULL,"
								   + "Datentyp VARCHAR(260) NOT NULL,"
								   + "Groesse LONG NOT NULL,"
								   + "Checksum VARCHAR(260) NOT NULL)";
					
				stmt = conn.createStatement();
				stmt.executeUpdate(createTable);
				stmt.close();
				System.out.println("Die Tabelle " + tableName +" wurde erfolgreich erstellt.");
			}
			
			} catch (SQLException e) {
			System.out.println(e.getMessage());
		}			
	}

	//-----------------------------------------------------------------------------------------------------//
	/**
	 * insertImageInserter
	 * @param bild
	 * @param name
	 * @param datentyp
	 * @param groesse
	 * @param checksum
	 */	
	public static void insertIntoImageInserter(byte[] bild, String name, String datentyp, long groesse, String checksum) {
		
		try (Connection conn = DriverManager.getConnection(driverSQLite + basePathDB + nameDB)) {
			if (conn != null) {
				PreparedStatement stmt 	= conn.prepareStatement("INSERT INTO AnalyzedPics"
										+ " (Bild,"
										+ " Name,"
										+ " Datentyp,"
										+ " Groesse,"
										+ " Checksum)"
										+ " VALUES(?,?,?,?,?)");
				stmt.setBytes(1, bild);
				stmt.setString(2, name);
				stmt.setString(3, datentyp);
				stmt.setLong(4, groesse);
				stmt.setString(5, checksum);
				stmt.executeUpdate();
				stmt.close();
				System.out.println("Datensatz erfolgreich angelegt.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Datensatz konnte nicht angelegt werden.");
		}		
	}

	//-----------------------------------------------------------------------------------------------------//
	/**
	 * updateImageInserter
	 * @param id
	 * @param bild
	 * @param name
	 * @param datentyp
	 * @param groesse
	 * @param checksum
	 */
	public static void updateImageInserter(int id, Blob bild, String name, String datentyp, long groesse, String checksum) {
		try (Connection conn = DriverManager.getConnection(driverSQLite + basePathDB + nameDB)) {
			if (conn != null) {
				PreparedStatement stmt = conn
						.prepareStatement("UPDATE AnalyzedPics SET Bild = ?, Name = ?, Datentyp = ?, Groesse = ?, Checksum = ? WHERE ID = ?");
				stmt.setBlob(1, bild);
				stmt.setString(2, name);
				stmt.setString(3, datentyp);
				stmt.setLong(4, groesse);
				stmt.setNString(5, checksum);
				stmt.setInt(6, id);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		System.out.println("Datensatz erfolgreich geändert.");
	}

	//-----------------------------------------------------------------------------------------------------//
	/**
	 * deleteImageInserter
	 * @param id
	 */
	public static void deleteImageInserter(int id) {
		try (Connection conn = DriverManager.getConnection(driverSQLite + basePathDB + nameDB)) {
			if (conn != null) {
				PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnalyzedPics WHERE ID = ?");
				stmt.setInt(1, id);
				stmt.executeUpdate();
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		System.out.println("Datensatz erfolgreich gelöscht.");
	}
	
	//-----------------------------------------------------------------------------------------------------//
	
	/**
	 * 
	 * @param filename
	 * @return
	 */
	/*public static List<ImageInserter> selectImageInserter() {
		List<ImageInserter> liste = new ArrayList<>();
		try (Connection conn = ConnectDB.connectToSQLiteDB()) {
			if (conn != null) {
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT ID, Name, Datentyp, Groesse, Checksum FROM AnalyzedPics");

				while (rs.next()) {
					int id = rs.getInt(1);
					String description = rs.getString(2).trim();
					int cash = rs.getInt(3);
					liste.add(new ImageInserter(id, bild, name, datentyp, groesse, checksum));
				}
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// e.printStackTrace();
		}
		System.out.println("Datensatz erfolgreich ausgewählt.");
		return liste;
	}*/
	
	//-----------------------------------------------------------------------------------------------------//
	
}
