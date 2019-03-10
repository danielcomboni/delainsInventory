
package com.delains.dao.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DBUtils {

	/**
	 * Connect to the database
	 */
	public static Connection connect() {
		Connection conn = null;
		try {
			/*
			 * this will create the database automatically
			 */
			// db parameters
			// String url = "jdbc:sqlite:arafat.db";
			// String url = "jdbc:sqlite:ogola.db";
			// String url = "jdbc:sqlite:src/application/sample2.db";
			// String url = "jdbc:sqlite:src/application/inventory.db";
			String url = "jdbc:sqlite:inventory.db";
			// create a connection to the database

			// String url = "jdbc:sqlite:supermarket.db";
			conn = DriverManager.getConnection( url );
			// System.out.println( "Connection to SQLite has been established." );
			System.out.println( "url: " + url );

			conn.setAutoCommit( true );

		} catch ( SQLException e ) {
			e.printStackTrace();
			System.out.println();
		}

		return conn;

	}

	public static void makeSureConnectionIsClosed() {
		try {
			if ( !DBUtils.connect().isClosed() ) {
				DBUtils.connect().close();
			}
		} catch ( SQLException e ) {
			e.printStackTrace();
		}

	}

	/*
	 * 
	 * closing connection, prepared statements and result sets
	 * 
	 * 
	 */

	public static void closeConnections( Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet ) {
		System.out.println();
		try {
			connection.setAutoCommit( true );

			if ( resultSet != null ) {
				resultSet.close();
				System.out.println( "rs closed" );
			}

			if ( preparedStatement != null ) {
				preparedStatement.closeOnCompletion();
				preparedStatement.close();
				System.out.println( "ps closed" );
			}

			if ( connection != null ) {

				connection.close();
				System.out.println( "conn closed" );
			}

		} catch ( Exception e ) {
			e.printStackTrace();
		}
		System.out.println();
	}

	/*
	 * this creates a table of the specifications provided.
	 * 
	 * it carries key-value LinkedHashMap where the key is the name of the column
	 * 
	 * and the value is the definition of the column (data type and constraints)
	 */
	public static int apiToCreateTable( LinkedHashMap < String, String > linkedHashMapToCreateTable,
			String tableName ) {
		int result = 0;

		String columnNamesAndDataTypesAndConstraints = "";
		List < String > listOfColumnDefinitions = new LinkedList <>();
		String comma = ",";
		String space = " ";

		for ( Entry < String, String > map : linkedHashMapToCreateTable.entrySet() ) {

			String columnName = map.getKey();
			String dataTypeAndConstraints = map.getValue();

			columnNamesAndDataTypesAndConstraints = columnName.concat( space ).concat( dataTypeAndConstraints )
					.concat( space ).concat( comma );

			listOfColumnDefinitions.add( columnNamesAndDataTypesAndConstraints );

		}

		columnNamesAndDataTypesAndConstraints = "";

		for ( int i = 0; i < listOfColumnDefinitions.size(); i++ ) {
			String details = listOfColumnDefinitions.get( i );
			columnNamesAndDataTypesAndConstraints = columnNamesAndDataTypesAndConstraints.concat( details );
		}

		String columnNamesAndDataTypesAndConstraintsFinal = "";
		for ( int i = 0; i < columnNamesAndDataTypesAndConstraints.length() - 1; i++ ) {

			columnNamesAndDataTypesAndConstraintsFinal = columnNamesAndDataTypesAndConstraintsFinal
					.concat( String.valueOf( columnNamesAndDataTypesAndConstraints.charAt( i ) ) );
		}

		String createFirstCommand = "CREATE TABLE IF NOT EXISTS".concat( space ).concat( tableName ).concat( space )
				.concat( "(" );
		String closingParenthesis = ")";

		String finalCreateTableCommand = createFirstCommand.concat( columnNamesAndDataTypesAndConstraintsFinal )
				.concat( closingParenthesis ).concat( ";" );

		System.out.println( "final create code:\n" + finalCreateTableCommand );

		PreparedStatement preparedStatementToCreateTable = null;
		Connection connection = connect();
		try {

			preparedStatementToCreateTable = connection.prepareStatement( finalCreateTableCommand );
			result = preparedStatementToCreateTable.executeUpdate();

		} catch ( SQLException e ) {
			System.out.println( e.getMessage() );
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatementToCreateTable, null );
		}

		return result;
	}

	/*
	 * 
	 * adding a new user
	 * 
	 */
	public static String getInsertCommandString( LinkedHashMap < String, String > linkedHashMapToInsertIntotable,
			String tableName ) {
		String columnNamesAndDataTypesAndConstraints = "";
		List < String > listOfColumnDefinitions = new LinkedList <>();
		String comma = ",";
		String space = " ";

		for ( Entry < String, String > map : linkedHashMapToInsertIntotable.entrySet() ) {

			String columnName = map.getKey();

			columnNamesAndDataTypesAndConstraints = columnName.concat( space ).concat( space ).concat( comma );

			listOfColumnDefinitions.add( columnNamesAndDataTypesAndConstraints );

		}

		columnNamesAndDataTypesAndConstraints = "";

		for ( int i = 0; i < listOfColumnDefinitions.size(); i++ ) {
			String details = listOfColumnDefinitions.get( i );
			columnNamesAndDataTypesAndConstraints = columnNamesAndDataTypesAndConstraints.concat( details );
		}

		String columnNamesAndDataTypesAndConstraintsFinal = "";
		for ( int i = 0; i < columnNamesAndDataTypesAndConstraints.length() - 1; i++ ) {

			columnNamesAndDataTypesAndConstraintsFinal = columnNamesAndDataTypesAndConstraintsFinal
					.concat( String.valueOf( columnNamesAndDataTypesAndConstraints.charAt( i ) ) );
		}

		// System.out.println("ll: " + listOfColumnDefinitions.size());

		String createFirstCommand = "INSERT INTO ".concat( space ).concat( tableName ).concat( space ).concat( "(" );
		String closingParenthesis = ")";

		String almostFinalInsertCommand = "VALUES(";

		for ( int i = 0; i < listOfColumnDefinitions.size(); i++ ) {

			if ( i < listOfColumnDefinitions.size() - 1 ) {
				almostFinalInsertCommand = almostFinalInsertCommand.concat( "?," );
			} else {
				almostFinalInsertCommand = almostFinalInsertCommand.concat( "?" );
			}

		}

		String finalCreateTableCommand = createFirstCommand.concat( columnNamesAndDataTypesAndConstraintsFinal )
				.concat( closingParenthesis );

		finalCreateTableCommand = finalCreateTableCommand.concat( space ).concat( almostFinalInsertCommand )
				.concat( ");" );

		System.out.println( "final create code:\n" + finalCreateTableCommand );

		return finalCreateTableCommand;
	}

	/*
	 * 
	 * updating table
	 * 
	 */

	public static String getUpdateCommandString( String nameOfTableTBeUpdated,
			LinkedHashMap < String, String > linkedHashMapToUpdateTableValues, String whereClause ) {

		String columnNamesToBeUpdated = "";
		String space = " ";

		String firstCommands = "UPDATE".concat( space ).concat( nameOfTableTBeUpdated ).concat( space ).concat( "SET" )
				.concat( space );

		String wildCard = " = ?";
		String comma = ",";

		List < String > listOfColumnDefinitions = new LinkedList <>();

		for ( Entry < String, String > map : linkedHashMapToUpdateTableValues.entrySet() ) {
			listOfColumnDefinitions.add( map.getValue().concat( wildCard ).concat( comma ) );
		}

		for ( int i = 0; i < listOfColumnDefinitions.size(); i++ ) {
			String aColumnDetails = listOfColumnDefinitions.get( i );
			columnNamesToBeUpdated = columnNamesToBeUpdated.concat( aColumnDetails );
		}

		// System.out.println(columnNamesToBeUpdated);

		String columnNamesToBeUpdateFinal = "";
		for ( int i = 0; i < columnNamesToBeUpdated.length() - 1; i++ ) {

			char ch = columnNamesToBeUpdated.charAt( i );
			columnNamesToBeUpdateFinal = columnNamesToBeUpdateFinal.concat( String.valueOf( ch ) );

		}

		// System.out.println("final: " + columnNamesToBeUpdateFinal);

		String finalUpdateCommand = firstCommands.concat( columnNamesToBeUpdateFinal ).concat( space )
				.concat( whereClause );

		System.out.println( finalUpdateCommand );

		return finalUpdateCommand;
	}

	public static String getDeleteCommandString( String tableNameFromWhichToBeDeleted, String whereClause ) {
		String space = " ";
		String firstCommand = "DELETE FROM ".concat( tableNameFromWhichToBeDeleted ).concat( space )
				.concat( whereClause );
		System.out.println( "deletion: " + firstCommand );
		return firstCommand;
	}

	/*
	 * retrieving
	 */
	public static String getRetrievingCommandString( String tableName, String whereClause ) {
		String space = " ";
		String firstCommand = "SELECT * FROM ".concat( tableName ).concat( space ).concat( whereClause );
		System.out.println( "retieve: " + firstCommand );
		return firstCommand;
	}

	public static void main( String [ ] args ) throws SQLException {
		/* create */
		// LinkedHashMap<String, String> map = new LinkedHashMap<>();
		// map.put("id", "integer primary key autoincrement");
		// map.put("item_name", "varchar(255)");
		// map.put("item_category", "varchar(255)");
		// map.put("item_sub_category", "varchar(255)");
		//
		// String tableName = "items";
		// apiToCreateTable(map, tableName);

		/* update */
		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "item_name", "item_name" );
		map.put( "item_category", "item_category" );
		map.put( "item_sub_category", "item_sub_category" );
		String tableName = "items";
		String whereClause = "WHERE id=?";

		getUpdateCommandString( tableName, map, whereClause );

		/* deletion */
		// String tableName = "item";
		// String whereClause = "WHERE id=?";
		// getDeleteCommandString(tableName, whereClause);

		// String tableName = "item";
		// String whereClause = "";
		// getRetrievingCommandString(tableName, whereClause);

		// LinkedHashMap<String, String> map = new LinkedHashMap<>();
		//
		// map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		// map.put("user_email", "VARCHAR(255)");
		// map.put("user_phone", "VARCHAR(255)");
		// map.put("user_password", "VARCHAR(255)");
		// map.put("is_admin", "boolean");
		//
		// apiToCreateTable(map, "users");
		//
		// String c = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT
		// ,user_email VARCHAR(255) "
		//
		// + ",user_phone VARCHAR(255) ,user_password VARCHAR(255) ,is_admin boolean
		// );\n" + "";
		//
		// PreparedStatement ps = null;
		// // ps = connect().prepareStatement(c);
		// // int result = ps.executeUpdate();
		// // System.out.println("res: " + result);
		//
		// getInsertCommandString(map, "USERS");

	}

	private static int createTableTest() {
		int result = 0;
		String sql = "create table test(id integer primary key autoincrement, item varchar(255))";

		PreparedStatement ps = null;

		// Connection conn = connect() ;

		try {
			ps = connect().prepareStatement( sql );

			result = ps.executeUpdate();

			System.out.println( "result: " + result );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			try {
				if ( connect() != null ) {
					connect().close();
					System.out.println( "conn closed" );
				}
			} catch ( SQLException ex ) {

				System.out.println( ex.getMessage() );
			}
		}
		return result;
	}

	public static int insert( String item ) {
		createTableTest();
		int result = 0;
		PreparedStatement ps = null;
		String sql = "insert into test(item) values(?)";

		try {
			ps = connect().prepareStatement( sql );
			ps.setString( 1, item );
			result = ps.executeUpdate();
			System.out.println( "result of insertion: " + result );
		} catch ( SQLException e ) {
			Alert alert = new Alert( AlertType.INFORMATION );
			alert.setContentText( "at insert" + e.getMessage() );
			alert.setResizable( true );
			alert.showAndWait();
			e.printStackTrace();
		} finally {
			try {
				if ( connect() != null ) {
					connect().close();
					System.out.println( "conn closed" );
				}
			} catch ( SQLException ex ) {
				System.out.println( ex.getMessage() );
			}
		}

		return result;
	}

}
