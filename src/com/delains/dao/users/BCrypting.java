package com.delains.dao.users;

import org.mindrot.jbcrypt.BCrypt;

public class BCrypting {

	private BCrypting() {

	}

	public static String hashPassword( String plainTextPassword ) {
		return BCrypt.hashpw( plainTextPassword, BCrypt.gensalt() );

	}

}
