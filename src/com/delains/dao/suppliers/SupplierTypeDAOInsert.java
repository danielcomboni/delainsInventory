package com.delains.dao.suppliers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.SupplierType;

public class SupplierTypeDAOInsert {

	private static LinkedHashMap<String, String> supplierTypeInsertionDefinition() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("type", "VARCHAR(255)");

		return map;

	}

	public static void newSupplierType(SupplierType supplierType) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString(supplierTypeInsertionDefinition(), "supplier_type"));

			preparedStatement.setString(1, supplierType.getType());

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}
	}

}
