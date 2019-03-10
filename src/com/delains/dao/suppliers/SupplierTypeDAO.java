package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.suppliers.SupplierType;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SupplierTypeDAO {

	private static LinkedHashMap<String, String> supplierTypeTableDefinitions() {

		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("id", "INTEGER PRIMARY KEY AUTOINCREMENT");
		map.put("type", "VARCHAR(255)");

		return map;
	}

	private static void createTableSupplierType() {
		DBUtils.apiToCreateTable(supplierTypeTableDefinitions(), "supplier_type");
	}

	public static void newSupplierType(SupplierType supplierType) {
		createTableSupplierType();
		SupplierTypeDAOInsert.newSupplierType(supplierType);
	}

	public static void updateSupplierType(SupplierType supplierType, BigDecimal idOfSupplierType) {
		SupplierTypeDAOUpdate.updateUser(supplierType, idOfSupplierType);
	}

	public static void deleteSupplierType(BigDecimal id) {
		SupplierTypeDAODelete.deleteSupplierType(id);
	}

	public static List<SupplierType> findAllSupplierTypes() {
		createTableSupplierType();
		return SupplierTypeDAORetrieve.findAllSupplierTypes();
	}

	public static ObservableList<SupplierType> changeListUtilToListObservable() {

		ObservableList<SupplierType> observableList = FXCollections.observableArrayList();

		for (int i = 0; i < findAllSupplierTypes().size(); i++) {
			SupplierType u = findAllSupplierTypes().get(i);
			observableList.add(u);
			;
		}

		return observableList;
	}
}
