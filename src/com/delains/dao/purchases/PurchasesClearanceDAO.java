package com.delains.dao.purchases;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseClearance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchasesClearanceDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))");
		map.put( "purchase_id", "INTEGER" );
		map.put( "amount_cleared", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );

		return map;

	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "purchases_clearance" );
	}

	public static void newPurchasesClearance( PurchaseClearance clearance ) {
		createTable();
		PurchasesClearanceDAOInsertion.newPurchaseSClearance( clearance );
	}

	public static List < PurchaseClearance > findAllPurchaseClearancesListUtil() {
		createTable();
		return PurchasesClearanceDAORetrieve.findAllPurchases();
	}

	public static ObservableList < PurchaseClearance > changeListUtilToListObservable() {

		ObservableList < PurchaseClearance > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllPurchaseClearancesListUtil().size(); i++ ) {
			PurchaseClearance pc = findAllPurchaseClearancesListUtil().get( i );
			observableList.add( pc );
		}

		return observableList;

	}

}
