package com.delains.dao.users;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.users.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

	private static LinkedHashMap < String, String > usersTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "user_name", "VARCHAR(255)" );
		map.put( "user_email", "VARCHAR(255)" );
		map.put( "user_phone", "VARCHAR(255)" );
		map.put( "user_password", "VARCHAR(255)" );
		map.put( "is_admin", "boolean" );

		return map;
	}

	private static int createTableUsers() {
		int result = 0;
		result = DBUtils.apiToCreateTable( usersTableDefinitions(), "users" );
		return result;
	}

	public static User newUser( User user ) {
		createTableUsers();
		return  UserDAOinsertion.newUser( user );
	}

	public static User updateUser( User user, BigDecimal idOfUser ) {
		return UserDAOUpdate.updateUser( user, idOfUser );
	}

	public static void deleteUser( BigDecimal id ) {
		UserDAODelete.deleteUser( id );
	}

	public static void deleteDefaultUser( BigDecimal id, String email ) {
		UserDAODelete.deleteDefaultUser( id, email );
	}

	public static List < User > findAllUsers() {
		createTableUsers();
		return UserDAORetrieve.findAllUsers();
	}

	public static ObservableList < User > changeListUtilToListObservable() {

		ObservableList < User > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllUsers().size(); i++ ) {
			User u = findAllUsers().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}

	public static void main( String [ ] args ) {

// createTableUsers();
		//
		// User user = new User();
		// user.setAdmin(false);
		// user.setUserEmail("test2@gmail.com");
		// user.setUserPassword("12345");
		// user.setUserPhone("0775 07 99 87");
		//
//
		// newUser(user);
		//
}

}
