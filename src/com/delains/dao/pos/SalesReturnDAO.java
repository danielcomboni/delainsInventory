package com.delains.dao.pos;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.sales.SalesReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesReturnDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER NOT NULL" );
		map.put( "quantity_returned", "DECIMAL(50,5)" );
		map.put( "quantity_restocked", "DECIMAL(50,5)" );
		map.put( "quantity_discarded", "DECIMAL(50,5)" );
		map.put( "customer_id", "INTEGER" );
		map.put( "pos_id", "INTEGER" );
		map.put( "reason", "longtext" );

		return map;

	}

	private static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "sales_return" );
	}

	public static void newSalesReturn( SalesReturn salesReturn ) {
		createTable();
		SalesReturnDAOInsertion.newSalesReturn( salesReturn );
	}

	public static List < SalesReturn > findAllSalesReturnListUtil() {
		createTable();
		return SalesReturnDAORetrieve.findAllSalesReturns();
	}

	public static ObservableList < SalesReturn > changeListUtilToListObservable() {

		ObservableList < SalesReturn > observableList = FXCollections.observableArrayList();

		for ( SalesReturn sr : findAllSalesReturnListUtil() ) {
			observableList.add( sr );
		}

		return observableList;

	}

}
