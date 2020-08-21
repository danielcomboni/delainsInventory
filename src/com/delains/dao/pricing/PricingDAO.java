package com.delains.dao.pricing;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pricing.Pricing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PricingDAO {

	private static LinkedHashMap < String, String > pricingTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "item_id", "INTEGER unique not null" );
		map.put( "price", "DECIMAL(50,2)" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		return map;

	}

	private static void createTablePricing() {
		DBUtils.apiToCreateTable( pricingTableDefinitions(), "pricing" );
	}

	public static Pricing newPricing( Pricing pricing ) {
		createTablePricing();

		return PricingDAOInsert.newPricing( pricing );
	}

	public static Pricing updatePricing( Pricing pricing, BigDecimal idOfPricing ) {
return 		PricingDAOUpdate.updatePricing( pricing, idOfPricing );
	}

	public static void deletePricing( BigDecimal id ) {
		PricingDAODelete.deletePricing( id );
	}

	public static List < Pricing > findAllPricingsListUtil() {
		createTablePricing();
		return PricingDAORetrieve.findAllPricing();
	}

	public static ObservableList < Pricing > changeListUtilToListObservable() {

		ObservableList < Pricing > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllPricingsListUtil().size(); i++ ) {
			Pricing u = findAllPricingsListUtil().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}

}
