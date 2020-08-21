package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.SupplierType;

public class SupplierTypeDAOUpdate {

	private static LinkedHashMap<String, String> mapsForUpdatingSupplierType() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("type", "type");

		return map;

	}

	public static SupplierType updateUser(SupplierType supplierType, BigDecimal idOfSupplierType) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString("supplier_type", mapsForUpdatingSupplierType(), "WHERE id=?"));

			supplierType.setId(idOfSupplierType);

			preparedStatement.setString(1, supplierType.getType());
			preparedStatement.setBigDecimal(2, supplierType.getId());

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
