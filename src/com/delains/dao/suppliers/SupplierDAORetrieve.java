package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.Supplier;
import com.delains.model.suppliers.SupplierType;

public class SupplierDAORetrieve {

	public static List<Supplier> findAllSuppliers() {

		List<Supplier> suppliers = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("suppliers", ""));
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, SupplierType> map = SupplierTypeHibernation.mapOfSupplierTypeToThierId();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				String supplierEmail = resultSet.getString("supplier_email");
				String supplierPhone = resultSet.getString("supplier_phone");
				BigDecimal supplierTypeId = resultSet.getBigDecimal("supplier_type_id");

				SupplierType type = map.get(supplierTypeId);

				String supplierName = resultSet.getString("supplier_name");
				String date = resultSet.getString("date");

				Supplier s = new Supplier();
				s.setDate(date);
				s.setSupplierEmail(supplierEmail);
				s.setSupplierName(supplierName);
				s.setType(type);
				s.setSupplierPhone(supplierPhone);
				s.setId(id);

				suppliers.add(s);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return suppliers;
	}

}
