package com.delains.dao.pos;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class POSDAOInvoker {

	private static LinkedHashMap < String, String > posTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER NOT NULL" );
		map.put( "pricing_id", "INTEGER NOT NULL" );
		map.put( "price", "DECIMAL(50,5)" );
		map.put( "quantity", "DECIMAL(50,5)" );
		map.put( "cost", "DECIMAL(50,5)" );
		map.put( "total_cost", "DECIMAL(50,5)" );
		map.put( "amount_paid", "DECIMAL(50,5)" );
		map.put( "credit", "boolean" );
		map.put( "discount_allowed", "DECIMAL(50,2)" );
		map.put( "customer_id", "INTEGER" );
		map.put( "change", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );
		map.put( "medium_of_payment_id", "INTEGER" );

		return map;

	}

	private static void createTablePOS() {
		DBUtils.apiToCreateTable( posTableDefinitions(), "pos" );
	}

	public static void newPOS( ObservableMap < Item, POS > map ) {
		createTablePOS();
		POSDAOInsert.newPOS( map );
	}

	public static Map < BigDecimal, POS > mapPOSToIDsViaUtilMap() {
		createTablePOS();
		return POSDAORetrieve.findAllPOSesMappedToTheirIDs();
	}

	public static ObservableList < POS > changeListUtilToListObservable() {

		ObservableList < POS > observableList = FXCollections.observableArrayList();

		for ( Entry < BigDecimal, POS > pos : mapPOSToIDsViaUtilMap().entrySet() ) {

			POS p = pos.getValue();

			observableList.add( p );

		}

		return observableList;

	}

}
