package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.purchases.PurchaseReturn;

import javafx.collections.ObservableList;

public class PurchaseReturnHibernation {

	private static ObservableList<PurchaseReturn> purchaseReturns;

	public static ObservableList<PurchaseReturn> getPurchaseReturns() {
		return purchaseReturns;
	}

	public static void setPurchaseReturns(ObservableList<PurchaseReturn> purchaseReturns) {
		PurchaseReturnHibernation.purchaseReturns = purchaseReturns;
	}

	public static void newPurchaseReturn(PurchaseReturn purchase) {
		PurchaseReturnDAO.newPurchaseReturn(purchase);
	}

	public static void newPurchaseReturnreturn(PurchaseReturn purchaseReturn) {
		PurchaseReturnDAO.newPurchaseReturn(purchaseReturn);
	}

	public static ObservableList<PurchaseReturn> findAllPurchaseReturnsObservableList() {
		if (getPurchaseReturns() == null) {
			setPurchaseReturns(PurchaseReturnDAO.changeListUtilToListObservable());
		}
		return getPurchaseReturns();
	}

	public static ObservableList<PurchaseReturn> findAllPurchaseReturnsObservableListRefreshed() {
		setPurchaseReturns(PurchaseReturnDAO.changeListUtilToListObservable());
		return getPurchaseReturns();
	}

	public static Map<BigDecimal, PurchaseReturn> mapOfPurchaseReturnsToThierId() {
		Map<BigDecimal, PurchaseReturn> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllPurchaseReturnsObservableList().size(); i++) {
			PurchaseReturn type = findAllPurchaseReturnsObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

	public static Map<BigDecimal, PurchaseReturn> mapOfPurchaseReturnsToThierItemsID() {
		Map<BigDecimal, PurchaseReturn> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllPurchaseReturnsObservableList().size(); i++) {
			PurchaseReturn type = findAllPurchaseReturnsObservableList().get(i);

			map.put(type.getItemId().getId(), type);
		}
		return map;
	}

}
