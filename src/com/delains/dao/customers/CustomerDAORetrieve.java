package com.delains.dao.customers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;

public class CustomerDAORetrieve {

	public static List<Customer> findAllCustomers() {

		List<Customer> customers = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("customers", ""));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				String customerEmail = resultSet.getString("customer_email");
				String customerPhone = resultSet.getString("customer_phone");
				String customerName = resultSet.getString("customer_name");
				String date = resultSet.getString("date");

				Customer customer = new Customer(id, customerEmail, customerPhone, customerName, date);

				customers.add(customer);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		 finally {
				DBUtils.closeConnections(connection, preparedStatement, resultSet);
			}
		return customers;
	}

}
