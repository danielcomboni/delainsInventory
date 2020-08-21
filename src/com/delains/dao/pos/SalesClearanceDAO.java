package com.delains.dao.pos;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.purchases.SalesClearanceDAORetrieve;
import com.delains.dao.utils.DBUtils;
import com.delains.model.sales.SalesClearance;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SalesClearanceDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "pos_id", "INTEGER" );
		map.put( "amount_cleared", "DECIMAL(50,5)" );
		map.put( "balance", "DECIMAL(50,5)" );

		return map;

	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "sales_clearance" );
	}

	public static void newSalesClearance( SalesClearance clearance ) {
		createTable();
		SalesClearanceDAOInsertion.newSalesClearance( clearance );
	}

	public static List < SalesClearance > findAllSalesClearancesListUtil() {
		createTable();
		return SalesClearanceDAORetrieve.findAllPurchases();
	}

	public static ObservableList < SalesClearance > changeListUtilToListObservable() {

		ObservableList < SalesClearance > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllSalesClearancesListUtil().size(); i++ ) {
			SalesClearance pc = findAllSalesClearancesListUtil().get( i );
			observableList.add( pc );
		}

		return observableList;

	}

}
