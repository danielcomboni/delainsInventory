package com.delains.dao.stock;

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
import com.delains.model.items.Item;
import com.delains.model.stock.StockWarningPoint;

public class StockWarningPointDAORetrieve {

	public static List < StockWarningPoint > findAllItems() {

		List < StockWarningPoint > items = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "stock_warning", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > mapIte = ItemHibernation.mapOfItemsToThierId();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );
				BigDecimal quantityLimit = resultSet.getBigDecimal( "quantity_limit" );

				StockWarningPoint item = new StockWarningPoint();

				item.setId( id );
				item.setItemId( mapIte.get( itemId ) );
				item.setQuantityLimit( quantityLimit );

				items.add( item );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return items;
	}

}
