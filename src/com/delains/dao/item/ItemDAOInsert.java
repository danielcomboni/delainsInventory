package com.delains.dao.item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;

public class ItemDAOInsert {

	private static LinkedHashMap < String, String > itemInsertionDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "item_name", "VARCHAR(255)" );
		map.put( "item_description", "LONGTEXT" );
		map.put( "unit_of_measurement", "VARCHAR(255)" );
		map.put( "barcode", "VARCHAR(255) UNIQUE" );
		map.put( "package", "VARCHAR(255" );
		map.put( "package_volume", "decimal(50,5)" );

		return map;
	}

	public static Item newItem( Item item ) {

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getInsertCommandString( itemInsertionDefinition(), "items" ) );

			preparedStatement.setString( 1, item.getItemName() );
			preparedStatement.setString( 2, item.getItemDescription() );
			preparedStatement.setString( 3, item.getUnitOfMeasurement() );
			preparedStatement.setString( 4, item.getBarcode() );
			preparedStatement.setString( 5, item.getPackageName() );
			preparedStatement.setBigDecimal( 6, (BigDecimal) item.getPackageVolume());

			preparedStatement.executeUpdate();

			BigDecimal id;
			ResultSet rs = preparedStatement.getGeneratedKeys();

			if ( rs != null && rs.next() ) {
				id = rs.getBigDecimal( 1 );
				item.setId(id);
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, null );
		}
		return item;
	}

}
