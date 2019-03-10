package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.suppliers.Supplier;

import javafx.collections.ObservableList;

public class SupplierHibernation {

	private static ObservableList<Supplier> suppliers;

	public static ObservableList<Supplier> getSuppliers() {
		return suppliers;
	}

	public static void setSuppliers(ObservableList<Supplier> suppliers) {
		SupplierHibernation.suppliers = suppliers;
	}

	public static void newSupplier(Supplier supplier) {
		SupplierDAO.newSupplier(supplier);
	}

	public static void updateSupplier(Supplier supplier, BigDecimal idOfSupplier) {
		SupplierDAO.updateSupplier(supplier, idOfSupplier);
	}

	public static void deleteSupplier(BigDecimal id) {
		SupplierDAO.deleteSupplier(id);
	}

	public static ObservableList<Supplier> findAllSuppliersObservableList() {
		if (getSuppliers() == null) {
			setSuppliers(SupplierDAO.changeListUtilToListObservable());
		}
		return getSuppliers();
	}

	public static ObservableList<Supplier> findAllSuppliersObservableListRefreshed() {
		System.out.println("sup hib:....ref");
		setSuppliers(SupplierDAO.changeListUtilToListObservable());
		return getSuppliers();
	}

	public static Map<BigDecimal, Supplier> mapOfSuppliersToThierId() {
		Map<BigDecimal, Supplier> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllSuppliersObservableList().size(); i++) {
			Supplier type = findAllSuppliersObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

}
