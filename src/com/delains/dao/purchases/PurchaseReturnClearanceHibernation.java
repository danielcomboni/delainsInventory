package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.purchases.PurchaseReturnClearance;

import javafx.collections.ObservableList;

public class PurchaseReturnClearanceHibernation {

	private static ObservableList < PurchaseReturnClearance > purchaseReturnClearances;

	public static ObservableList < PurchaseReturnClearance > getPurchaseReturnClearances() {
		return purchaseReturnClearances;
	}

	public static void setPurchaseReturnClearances(
			ObservableList < PurchaseReturnClearance > purchaseReturnClearances ) {
		PurchaseReturnClearanceHibernation.purchaseReturnClearances = purchaseReturnClearances;
	}

	public static void newPurchaseReturnClearance( PurchaseReturnClearance clearance ) {
		PurchaseReturnClearanceDAO.newPurchaseReturnClearnce( clearance );
	}

	public static ObservableList < PurchaseReturnClearance > findAllPurchaseReturnsClearnceObservableList() {
		if ( getPurchaseReturnClearances() == null ) {
			setPurchaseReturnClearances( PurchaseReturnClearanceDAO.changeListUtilToListObservable() );
		}
		return getPurchaseReturnClearances();
	}

	public static ObservableList < PurchaseReturnClearance > findAllPurchaseReturnsClearanceObservableListRefreshed() {
		setPurchaseReturnClearances( PurchaseReturnClearanceDAO.changeListUtilToListObservable() );
		return getPurchaseReturnClearances();
	}

	public static Map < BigDecimal, PurchaseReturnClearance > mapOfPurchaseReturnsClearanceToThierId() {
		Map < BigDecimal, PurchaseReturnClearance > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllPurchaseReturnsClearanceObservableListRefreshed().size(); i++ ) {
			PurchaseReturnClearance type = findAllPurchaseReturnsClearanceObservableListRefreshed().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

}
