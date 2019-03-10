package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.pricing.Pricing;

public class PricingDAORetrieve {

	public static List<Pricing> findAllPricing() {

		List<Pricing> pricings = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("pricing", ""));
			resultSet = preparedStatement.executeQuery();

			Map<BigDecimal, Item> map = ItemHibernation.mapOfItemsToThierId();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				BigDecimal itemId = resultSet.getBigDecimal("item_id");

				Item item = map.get(itemId);

				BigDecimal price = resultSet.getBigDecimal("price");
				String date = resultSet.getString("date");

				Pricing pricing = new Pricing();

				pricing.setDate(date);
				pricing.setId(id);
				pricing.setItemId(item);
				pricing.setPrice(price);
//				pricing.setItemName(item.getItemName());
				pricing.setPriceStr(NumberFormatting.formatToEnglish(pricing.getPrice().toString()));

				pricings.add(pricing);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return pricings;
	}

}
