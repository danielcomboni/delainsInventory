package com.delains.dao.purchases;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.purchases.PurchaseReturn;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PurchaseReturnDAO {

	private static LinkedHashMap<String, String> tableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();

		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");
		map.put("item_id", "INTEGER");
		map.put("quantity", "DECIMAL(50,5)");
		map.put("supplier_id", "INTEGER");
		map.put("reason", "LONGTEXT");
		map.put("purchase_id", "INTEGER");

		return map;
	}

	public static void createTablePurchases() {
		DBUtils.apiToCreateTable(tableDefinitions(), "purchases_return");
	}

	public static void newPurchaseReturn(PurchaseReturn purchaseReturn) {
		createTablePurchases();
		PurchaseReturnDAOInsertion.newPurchaseReturn(purchaseReturn);
	}

	public static List<PurchaseReturn> findAllPurchasesListUtil() {
		createTablePurchases();
		return PurchaseReturnDAORetieve.findAllPurchaseReturns();
	}

	public static ObservableList<PurchaseReturn> changeListUtilToListObservable() {

		ObservableList<PurchaseReturn> observableList = FXCollections.observableArrayList();

		for (int i = 0; i < findAllPurchasesListUtil().size(); i++) {
			PurchaseReturn u = findAllPurchasesListUtil().get(i);
			observableList.add(u);
			;
		}

		return observableList;
	}

}
