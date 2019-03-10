package com.delains.dao.logs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.logs.Login;

public class LoginDAOInsert {

	private static LinkedHashMap<String, String> loginString() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("user_id", "INTEGER");
		map.put("date_time_in", "VARCHAR(255)");
		// map.put("date_time_out", "VARCHAR(255)");

		return map;
	}

	public static void newLogin(Login login) {

		PreparedStatement preparedStatementNewLogin = null;
		Connection connectionNewLogin = DBUtils.connect();

		try {

			preparedStatementNewLogin = connectionNewLogin
					.prepareStatement(DBUtils.getInsertCommandString(loginString(), "login"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

			login.setDateTimeIn(timestamp.toString());

			preparedStatementNewLogin.setBigDecimal(1, login.getUserId().getId());
			preparedStatementNewLogin.setString(2, login.getDateTimeIn());

			preparedStatementNewLogin.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connectionNewLogin, preparedStatementNewLogin, null);
		}

	}

	private static LinkedHashMap<String, String> logoutString() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("user_id", "INTEGER");
		// map.put("date_time_in", "VARCHAR(255)");
		map.put("date_time_out", "VARCHAR(255)");

		return map;
	}

	public static void newLogout(Login login) {

		PreparedStatement preparedStatementNewLogin = null;
		Connection connectionNewLogin = DBUtils.connect();

		try {

			preparedStatementNewLogin = connectionNewLogin
					.prepareStatement(DBUtils.getInsertCommandString(logoutString(), "login"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

			login.setDateTimeOut(timestamp.toString());

			preparedStatementNewLogin.setBigDecimal(1, login.getUserId().getId());
			preparedStatementNewLogin.setString(2, login.getDateTimeOut());

			preparedStatementNewLogin.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connectionNewLogin, preparedStatementNewLogin, null);
		}

	}

}
