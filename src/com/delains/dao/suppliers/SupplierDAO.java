package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.Supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierDAO {

	private static LinkedHashMap<String, String> supplierTableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("supplier_email", "VARCHAR(255)");
		map.put("supplier_phone", "VARCHAR(255)");
		map.put("supplier_name", "VARCHAR(255)");
		map.put("date", "DATE DEFAULT (datetime('now','localtime'))");
		map.put("supplier_type_id", "INTEGER");

		return map;
	}

	private static void createTableSupplier() {
		DBUtils.apiToCreateTable(supplierTableDefinitions(), "suppliers");
	}

	public static Supplier newSupplier(Supplier supplier) {
		createTableSupplier();
		return SupplierDAOInsert.newSupplier(supplier);
	}

	public static Supplier updateSupplier(Supplier supplier, BigDecimal idOfSupplier) {
		return SupplierDAOUpdate.updateSuppler(supplier, idOfSupplier);
	}

	public static void deleteSupplier(BigDecimal id) {
		SupplierDAODelete.deleteSupplier(id);
	}

	public static List<Supplier> findAllSupplierListUtil() {
		createTableSupplier();
		return SupplierDAORetrieve.findAllSuppliers();
	}

	public static ObservableList<Supplier> changeListUtilToListObservable() {

		ObservableList<Supplier> observableList = FXCollections.observableArrayList();

		for (int i = 0; i < findAllSupplierListUtil().size(); i++) {
			Supplier u = findAllSupplierListUtil().get(i);
			observableList.add(u);
			;
		}

		return observableList;
	}

}
