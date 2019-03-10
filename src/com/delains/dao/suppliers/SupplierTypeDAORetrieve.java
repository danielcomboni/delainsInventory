package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.SupplierType;

public class SupplierTypeDAORetrieve {

	public static List<SupplierType> findAllSupplierTypes() {

		List<SupplierType> supplierTypes = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("supplier_type", ""));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				String type = resultSet.getString("type");
				SupplierType supplierType = new SupplierType(id, type);
				supplierTypes.add(supplierType);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return supplierTypes;
	}

}
