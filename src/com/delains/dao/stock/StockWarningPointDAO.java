package com.delains.dao.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.stock.StockWarningPoint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class StockWarningPointDAO {

	private static LinkedHashMap < String, String > usersTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "item_id", "INTEGER UNIQUE NOT NULL" );
		map.put( "quantity_limit", "DECIMAL(50,5)" );

		return map;
	}

	private static void createTableUsers() {
		DBUtils.apiToCreateTable( usersTableDefinitions(), "stock_warning" );
	}

	public static void newStockWarning( StockWarningPoint stock ) {
		createTableUsers();
		StockWarningPointDAOInsert.newStockWarnigF( stock );
	}

	public static void updateStockWarningPoint( StockWarningPoint item, BigDecimal idOfStockWarningPoint ) {
		StockWarningPointDAOUpdate.updateStockWarning( item, idOfStockWarningPoint );
	}

	public static List < StockWarningPoint > findAllStockWarningPointsListUtil() {
		createTableUsers();
		return StockWarningPointDAORetrieve.findAllItems();
	}

	public static ObservableList < StockWarningPoint > changeListUtilToListObservable() {

		ObservableList < StockWarningPoint > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllStockWarningPointsListUtil().size(); i++ ) {
			StockWarningPoint u = findAllStockWarningPointsListUtil().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}

}
