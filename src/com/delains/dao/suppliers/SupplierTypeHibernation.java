package com.delains.dao.suppliers;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.suppliers.SupplierType;

import javafx.collections.ObservableList;

public class SupplierTypeHibernation {

	private static ObservableList<SupplierType> supplierTypes;

	public static ObservableList<SupplierType> getSupplierTypes() {
		return supplierTypes;
	}

	public static void setSupplierTypes(ObservableList<SupplierType> supplierTypes) {
		SupplierTypeHibernation.supplierTypes = supplierTypes;
	}

	public static SupplierType newSupplierType(SupplierType supplierType) {
		return SupplierTypeDAO.newSupplierType(supplierType);
	}

	public static SupplierType updateSupplier(SupplierType supplierType, BigDecimal idOfSupplierType) {
		return SupplierTypeDAO.updateSupplierType(supplierType, idOfSupplierType);
	}

	public static void deleteSupplierType(BigDecimal id) {
		SupplierTypeDAO.deleteSupplierType(id);
	}

	public static ObservableList<SupplierType> findAllSupplierTypesObservableList() {
		if (getSupplierTypes() == null) {
			setSupplierTypes(SupplierTypeDAO.changeListUtilToListObservable());
		}
		return getSupplierTypes();
	}

	public static ObservableList<SupplierType> findAllSupplierTypesObservableListRefreshed() {
		setSupplierTypes(SupplierTypeDAO.changeListUtilToListObservable());
		return getSupplierTypes();
	}

	public static Map<BigDecimal, SupplierType> mapOfSupplierTypeToThierId() {
		Map<BigDecimal, SupplierType> map = new LinkedHashMap<>();
		for (int i = 0; i < findAllSupplierTypesObservableList().size(); i++) {
			SupplierType type = findAllSupplierTypesObservableList().get(i);
			map.put(type.getId(), type);
		}
		return map;
	}

}
