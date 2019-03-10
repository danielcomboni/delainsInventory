package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.purchases.Purchase;

import javafx.collections.ObservableList;

public class PurchasesHibernation {

	private static ObservableList<Purchase> purchases;

	public static ObservableList<Purchase> getPurchases() {
		return purchases;
	}

	public static void setPurchases(ObservableList<Purchase> purchases) {
		PurchasesHibernation.purchases = purchases;
	}

	public static void newPurchase(Purchase purchase) {
		PurchasesDAO.newPurchase(purchase);
	}

	public static void updatePurchase(Purchase purchase, BigDecimal idOfPurchase) {
		PurchasesDAO.updatePurchase(purchase, idOfPurchase);
	}

	public static void deletePurchase(BigDecimal id) {
		PurchasesDAO.deletePurchase(id);
	}

	public static ObservableList<Purchase> findAllPurchasesObservableList() {
		if (getPurchases() == null) {
			setPurchases(PurchasesDAO.changeListUtilToListObservable());
		}
		return getPurchases();
	}

	public static ObservableList<Purchase> findAllPurchasesObservableListRefreshed() {
		setPurchases(PurchasesDAO.changeListUtilToListObservable());
		return getPurchases();
	}

	public static Map<BigDecimal, Purchase> mapOfPurchasesToThierId() {
		Map<BigDecimal, Purchase> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllPurchasesObservableList().size(); i++) {
			Purchase type = findAllPurchasesObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

	public static Map<BigDecimal, Purchase> mapOfPurchasesToThierItemsID() {
		Map<BigDecimal, Purchase> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllPurchasesObservableList().size(); i++) {
			Purchase type = findAllPurchasesObservableList().get(i);

			map.put(type.getItemId().getId(), type);
		}
		return map;
	}

}
