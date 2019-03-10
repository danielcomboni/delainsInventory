package com.delains.dao.logs;

import java.math.BigDecimal;
import java.util.Map.Entry;

import org.mindrot.jbcrypt.BCrypt;

import com.delains.dao.users.UserHibernation;
import com.delains.model.logs.Login;
import com.delains.model.users.User;

public class LoginHibernation {

	private static BigDecimal userId;

	public static BigDecimal getUserId() {
		return userId;
	}

	public static void setUserId(BigDecimal userId) {
		LoginHibernation.userId = userId;
	}

	public static boolean checkUserName(String userName) {

		boolean result = false;

		for (Entry<BigDecimal, User> u : UserHibernation.mapOfUserAndThierId().entrySet()) {
			if (u.getValue().getUserEmail().contains(userName) || u.getValue().getUserPhone().equals(userName)) {
				result = true;
				setUserId(u.getKey());
				break;
			}
		}
		return result;
	}

	public static boolean checkPass(String plainPassword) {

		boolean result = false;

		User user = UserHibernation.mapOfUserAndThierId().get(getUserId());

		String hashedPassword = user.getUserPassword();

		if (BCrypt.checkpw(plainPassword, hashedPassword)) {
			result = true;
		} else {
			result = false;
		}

		return result;

	}

	public static void newLogin(Login login) {
		LoginDAO.newLogin(login);
	}

	public static void newLogout(Login logout) {
		LoginDAO.newLogout(logout);
	}

}
