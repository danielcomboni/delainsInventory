package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pricing.Pricing;

public class PricingDAOInsert {

	// private BigDecimal id;
	// private Item itemId;
	// private BigDecimal price;
	// private String date;

	private static LinkedHashMap<String, String> pricingInsertionDefinition() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("item_id", "INTEGER not null");
		map.put("price", "DECIMAL(50,2)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");
		return map;

	}

	public static Pricing newPricing(Pricing pricing) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement(DBUtils.getInsertCommandString(pricingInsertionDefinition(), "pricing"));

			Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
			pricing.setDate(timestamp.toString());

			preparedStatement.setBigDecimal(1, pricing.getItemId().getId());
			preparedStatement.setBigDecimal(2, pricing.getPrice());
			preparedStatement.setString(3, pricing.getDate());

			preparedStatement.executeUpdate();

			BigDecimal id;
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if ( rs != null && rs.next() ) {
				id = rs.getBigDecimal( 1 );
				pricing.setId(id);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, null);
		}return pricing;
	}

}
