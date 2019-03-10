package com.delains.dao.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.users.User;

public class UserDAORetrieve {

	public static List<User> findAllUsers() {
		List<User> allUsers = new ArrayList<>();

		PreparedStatement preparedStatementSelectAllUsers = null;
		Connection connectionSelectAllUsers = DBUtils.connect();
		ResultSet resultSetFOrAllUsers = null;

		try {

			preparedStatementSelectAllUsers = connectionSelectAllUsers
					.prepareStatement(DBUtils.getRetrievingCommandString("users", ""));

			resultSetFOrAllUsers = preparedStatementSelectAllUsers.executeQuery();

			while (resultSetFOrAllUsers.next()) {
				BigDecimal id = resultSetFOrAllUsers.getBigDecimal("id");
				String userEmail = resultSetFOrAllUsers.getString("user_email");
				String userPhone = resultSetFOrAllUsers.getString("user_phone");
				String userPassword = resultSetFOrAllUsers.getString("user_password");
				boolean admin = resultSetFOrAllUsers.getBoolean("is_admin");
				String userName = resultSetFOrAllUsers.getString("user_name");

				User user = new User(id, userName, userEmail, userPhone, userPassword, admin);

				allUsers.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allUsers;
	}

}
