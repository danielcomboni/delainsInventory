package com.delains.dao.customers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;

public class CustomerDAOInsert {

	private static LinkedHashMap<String, String> customerInsertionDefinition() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("customer_email", "VARCHAR(255)");
		map.put("customer_phone", "VARCHAR(255)");
		map.put("customer_name", "VARCHAR(255)");
		map.put("date", "VARCHAR(255)");

		return map;
	}

	public static void newCustomer(Customer customer) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getInsertCommandString(customerInsertionDefinition(), "customers"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			customer.setDate(timestamp.toString());

			preparedStatement.setString(1, customer.getCustomerEmail());
			preparedStatement.setString(2, customer.getCustomerPhone());
			preparedStatement.setString(3, customer.getCustomerName());
			preparedStatement.setString(4, customer.getDate());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

}
