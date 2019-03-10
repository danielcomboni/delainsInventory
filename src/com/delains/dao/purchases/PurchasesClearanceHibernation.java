package com.delains.dao.purchases;

import com.delains.model.purchases.PurchaseClearance;

import javafx.collections.ObservableList;

public class PurchasesClearanceHibernation {

	private static ObservableList < PurchaseClearance > purchaseClearances;

	public static ObservableList < PurchaseClearance > getPurchaseClearances() {
		return purchaseClearances;
	}

	public static void setPurchaseClearances( ObservableList < PurchaseClearance > purchaseClearances ) {
		PurchasesClearanceHibernation.purchaseClearances = purchaseClearances;
	}

	public static void newPurchasesClearance( PurchaseClearance clearance ) {
		PurchasesClearanceDAO.newPurchasesClearance( clearance );
	}

	public static ObservableList < PurchaseClearance > findAllPurchasesClearancesWithoutRefreshing() {
		if ( getPurchaseClearances() == null ) {
			setPurchaseClearances( PurchasesClearanceDAO.changeListUtilToListObservable() );
		}
		return getPurchaseClearances();
	}

	public static ObservableList < PurchaseClearance > findAllPurchasesClearancesWithRefreshing() {
		PurchasesClearanceDAO.findAllPurchaseClearancesListUtil();
		setPurchaseClearances( PurchasesClearanceDAO.changeListUtilToListObservable() );
		return getPurchaseClearances();
	}

}
