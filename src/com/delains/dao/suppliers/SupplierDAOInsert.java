package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.Supplier;

public class SupplierDAOInsert {

	/* SQLite does not support foreign keys */

	// private static void alterTableSuppliersAddForeignKey() {
	//
	// PreparedStatement preparedStatement = null;
	// Connection connection = DBUtils.connect();
	//
	// String sql = "ALTER TABLE suppliers ADD FOREIGN KEY (supplier_type_id)
	// REFERENCES supplier_type (id) ON UPDATE CASCADE ON DELETE RESTRICT";
	//
	// try {
	// preparedStatement = connection.prepareStatement(sql);
	// preparedStatement.executeUpdate();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// } finally {
	// DBUtils.closeConnections(connection, preparedStatement, null);
	// }
	//
	// }

	private static LinkedHashMap<String, String> insertIntoSuppliersTable() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("supplier_email", "VARCHAR(255)");
		map.put("supplier_phone", "VARCHAR(255)");
		map.put("supplier_name", "VARCHAR(255)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");
		map.put("supplier_type_id", "INTEGER");
		return map;
	}

	public static Supplier newSupplier(Supplier supplier) {

		// alterTableSuppliersAddForeignKey();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getInsertCommandString(insertIntoSuppliersTable(), "suppliers"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			supplier.setDate(timestamp.toString());

			preparedStatement.setString(1, supplier.getSupplierEmail());
			preparedStatement.setString(2, supplier.getSupplierPhone());
			preparedStatement.setString(3, supplier.getSupplierName());
			preparedStatement.setString(4, supplier.getDate());
			preparedStatement.setBigDecimal(5, supplier.getType().getId());

			int i = preparedStatement.executeUpdate();

			if (i > 0 ){

				BigDecimal id = BigDecimal.ZERO;
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if ( rs != null && rs.next() ) {
					id = rs.getBigDecimal( 1 );
					supplier.setId(id);
				}

				if (rs != null) {
					rs.close();
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}return supplier;
	}
}
