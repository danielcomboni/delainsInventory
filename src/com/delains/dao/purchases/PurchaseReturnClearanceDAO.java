package com.delains.dao.purchases;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseReturnClearance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseReturnClearanceDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_returned", "DECIMAL(50,5)" );
		map.put( "purchase_return_id", "INTEGER" );

		return map;
	}

	public static void createTablePurchases() {
		DBUtils.apiToCreateTable( tableDefinitions(), "purchases_return_clearance" );
	}

	public static void newPurchaseReturnClearnce( PurchaseReturnClearance clearance ) {
		createTablePurchases();
		PurchaseReturnClearanceDAOInsertion.newPurchaseReturnClearance( clearance );
	}

	public static List < PurchaseReturnClearance > findAllPurchasesListUtil() {
		createTablePurchases();
		return PurchaseReturnClearanceDAORetieve.findAllPurchaseReturnClearances();
	}

	public static ObservableList < PurchaseReturnClearance > changeListUtilToListObservable() {

		ObservableList < PurchaseReturnClearance > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllPurchasesListUtil().size(); i++ ) {
			PurchaseReturnClearance u = findAllPurchasesListUtil().get( i );
			observableList.add( u );
		}

		return observableList;
	}

}
