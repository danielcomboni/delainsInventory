package com.delains.dao.logs;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.users.UserHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.logs.Login;
import com.delains.model.users.User;

public class LoginDAORetrieve {

	public static List<Login> findAllLoginRecords() {

		List<Login> logins = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		Map<BigDecimal, User> map = UserHibernation.mapOfUserAndThierId();

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("login", ""));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal userId = resultSet.getBigDecimal("user_id");
				String dateTimeIn = resultSet.getString("date_time_in");
				String dateTimeOut = resultSet.getString("date_time_out");

				User user = map.get(userId);

				Login login = new Login(id, user, dateTimeIn, dateTimeOut);
				logins.add(login);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}

		return logins;
	}

}
