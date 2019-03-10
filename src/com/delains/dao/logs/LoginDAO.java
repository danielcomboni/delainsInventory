package com.delains.dao.logs;

import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.logs.Login;

public class LoginDAO {

	private static LinkedHashMap<String, String> loginTableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("user_id", "INTEGER");
		map.put("date_time_in", "VARCHAR(255)");
		map.put("date_time_out", "VARCHAR(255)");

		return map;
	}

	private static int createTableLogin() {
		int result = 0;
		result = DBUtils.apiToCreateTable(loginTableDefinitions(), "login");
		return result;
	}

	public static void newLogin(Login login) {
		createTableLogin();
		LoginDAOInsert.newLogin(login);
	}

	public static void newLogout(Login logout) {
		LoginDAOInsert.newLogout(logout);
	}

}
