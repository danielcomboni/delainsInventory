package com.delains.dao.item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;

public class ItemDAORetrieve {

	public static List<Item> findAllItems() {

		List<Item> items = new ArrayList<>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement(DBUtils.getRetrievingCommandString("items", ""));
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				BigDecimal id = resultSet.getBigDecimal("id");
				String itemName = resultSet.getString("item_name");
				String itemDescription = resultSet.getString("item_description");
				String unitOfMeasurement = resultSet.getString("unit_of_measurement");
				String barcode = resultSet.getString("barcode");

				String pack = resultSet.getString("package");
				BigDecimal packVol = resultSet.getBigDecimal("package_volume");


				Item item = new Item();

				item.setPackageName(pack);

				item.setBarcode(barcode);

				item.setPackageVolume(packVol);
				item.setId(id);
				item.setItemName(itemName);
				item.setItemDescription(itemDescription);
				item.setUnitOfMeasurement(unitOfMeasurement);

				items.add(item);


				//	items.add(new Item(id, itemName, itemDescription, unitOfMeasurement, barcode, pack, packVol));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections(connection, preparedStatement, resultSet);
		}
		return items;
	}

}
