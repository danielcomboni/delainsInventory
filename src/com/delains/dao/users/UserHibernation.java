package com.delains.dao.users;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.users.User;

import javafx.collections.ObservableList;

public class UserHibernation {

	public static ObservableList < User > allusers;

	public static ObservableList < User > getAllusers() {
		return allusers;
	}

	public static void setAllusers( ObservableList < User > allusers ) {
		UserHibernation.allusers = allusers;
	}

	public static ObservableList < User > findAllUsers() {
		if ( getAllusers() == null ) {
			setAllusers( UserDAO.changeListUtilToListObservable() );
		}
		return getAllusers();
	}

	public static ObservableList < User > findAllUsersRefreshed() {
		setAllusers( UserDAO.changeListUtilToListObservable() );
return getAllusers();
	}

	public static Map < BigDecimal, User > mapOfUserAndThierId() {
		Map < BigDecimal, User > map = new LinkedHashMap <>();
		for ( User u : findAllUsers() ) {
			map.put( u.getId(), u );
		}
		return map;
	}

	public static User newuser( User user ) {
		return UserDAO.newUser( user );
	}

	public static User updateUser( User user, BigDecimal idOfUser ) {
		return UserDAO.updateUser( user, idOfUser );
	}

	public static void deleteUser( BigDecimal id ) {
		UserDAO.deleteUser( id );
	}

	public static void deleteDefaultUser( BigDecimal id, String email ) {
		UserDAO.deleteDefaultUser( id, email );
	}

}
