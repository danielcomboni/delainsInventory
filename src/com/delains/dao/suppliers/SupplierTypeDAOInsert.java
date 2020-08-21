package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

	public static SupplierType newSupplierType(SupplierType supplierType) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getInsertCommandString(supplierTypeInsertionDefinition(), "supplier_type"));

			preparedStatement.setString(1, supplierType.getType());

			long i = preparedStatement.executeUpdate();

			if (i > 0 ){

				BigDecimal id;
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if ( rs != null && rs.next() ) {
					id = rs.getBigDecimal( 1 );
					supplierType.setId(id);
				}

				if (rs != null) {
					rs.close();
				}

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}

		return supplierType;

	}

}
