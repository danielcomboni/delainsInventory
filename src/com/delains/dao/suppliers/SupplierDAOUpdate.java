package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.Supplier;

public class SupplierDAOUpdate {

	private static LinkedHashMap<String, String> mapsForUpdatingSuppliers() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("supplier_email", "supplier_email");
		map.put("supplier_phone", "supplier_phone");
		map.put("supplier_name", "supplier_name");
		map.put("date", "date");
		map.put("supplier_type_id", "supplier_type_id");

		return map;

	}

	public static Supplier updateSuppler(Supplier supplier, BigDecimal idOfSupplier) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString("suppliers", mapsForUpdatingSuppliers(), "WHERE id=?"));

			supplier.setId(idOfSupplier);

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			supplier.setDate(timestamp.toString());

			preparedStatement.setString(1, supplier.getSupplierEmail());
			preparedStatement.setString(2, supplier.getSupplierPhone());
			preparedStatement.setString(3, supplier.getSupplierName());
			preparedStatement.setString(4, supplier.getDate());
			preparedStatement.setBigDecimal(5, supplier.getType().getId());
			preparedStatement.setBigDecimal(6, supplier.getId());

			long i = preparedStatement.executeUpdate();

			if (i > 0 ){

				BigDecimal id;
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
		}
		return supplier;
	}

}
