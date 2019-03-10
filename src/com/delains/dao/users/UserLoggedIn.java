package com.delains.dao.users;

import com.delains.model.users.User;

public class UserLoggedIn {

	private static User userLoggedIn;

	public static User getUserLoggedIn() {
		return userLoggedIn;
	}

	public static void setUserLoggedIn( User userLoggedIn ) {
		UserLoggedIn.userLoggedIn = userLoggedIn;
	}

}
