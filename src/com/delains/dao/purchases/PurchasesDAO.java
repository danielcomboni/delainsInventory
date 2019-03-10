package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.Purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchasesDAO {

	private static LinkedHashMap < String, String > purchasesTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "item_id", "INTEGER" );
		map.put( "quantity", "DECIMAL(50,5)" );
		map.put( "price", "DECIMAL(50,5)" );
		map.put( "total_cost", "DECIMAL(50,5)" );
		map.put( "is_credit", "boolean" );
		map.put( "supplier_id", "INTEGER" );
		map.put( "amount_paid", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );
		map.put( "discount_received", "DECIMAL(50,5)" );
		map.put( "medium_of_payment_id", "INTEGER" );

		return map;
	}

	public static void createTablePurchases() {
		DBUtils.apiToCreateTable( purchasesTableDefinitions(), "purchases" );
	}

	public static void newPurchase( Purchase purchase ) {
		createTablePurchases();
		PurchasesDAOInsert.newPurchase( purchase );
	}

	public static void updatePurchase( Purchase purchase, BigDecimal idOfPurchase ) {
		PurchasesDAOUpdate.updatePurchase( purchase, idOfPurchase );
	}

	public static void deletePurchase( BigDecimal id ) {
		PurchasesDAODelete.deletePurchase( id );
	}

	public static List < Purchase > findAllPurchasesListUtil() {
		createTablePurchases();
		return PurchasesDAORetrieve.findAllPurchases();
	}

	public static ObservableList < Purchase > changeListUtilToListObservable() {

		ObservableList < Purchase > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllPurchasesListUtil().size(); i++ ) {
			Purchase u = findAllPurchasesListUtil().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}

}
