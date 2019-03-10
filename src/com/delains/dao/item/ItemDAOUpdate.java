package com.delains.dao.item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;

public class ItemDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingItems() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "item_name", "item_name" );
		map.put( "item_description", "item_description" );
		map.put( "unit_of_measurement", "unit_of_measurement" );
		map.put( "package", "package" );
		map.put( "package_volume", "package_volume" );
		map.put( "barcode", "barcode" );

		return map;

	}

	public static void updateUser( Item item, BigDecimal idOfItem ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection.prepareStatement(
					DBUtils.getUpdateCommandString( "items", mapsForUpdatingItems(), "WHERE id=?" ) );

			item.setId( idOfItem );

			preparedStatement.setString( 1, item.getItemName() );
			preparedStatement.setString( 2, item.getItemDescription() );
			preparedStatement.setString( 3, item.getUnitOfMeasurement() );
			preparedStatement.setString( 4, item.getPackageName() );
			preparedStatement.setBigDecimal( 5, item.getPackageVolume() );
			preparedStatement.setString( 6, item.getBarcode() );
			preparedStatement.setBigDecimal( 7, item.getId() );

			preparedStatement.executeUpdate();

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
	}

}
