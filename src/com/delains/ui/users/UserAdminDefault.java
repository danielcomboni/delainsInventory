package com.delains.ui.users;

import com.delains.dao.users.UserHibernation;
import com.delains.model.users.User;

public class UserAdminDefault {

	public UserAdminDefault() {
		checkIfAdminDefaultExists();
	}

	public void checkIfAdminDefaultExists() {
		new UserHibernation();

		if ( UserHibernation.findAllUsersRefreshed().isEmpty() ) {

User user = new User();
			user.setAdmin( true );
			user.setUserEmail( "admin@admin.com" );
			user.setUserPhone( null );
			user.setUserName( "admin" );
			user.setUserPassword( "1234" );

			UserHibernation.newuser( user );

		}

	}

}
