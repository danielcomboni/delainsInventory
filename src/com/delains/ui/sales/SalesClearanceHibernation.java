package com.delains.ui.sales;

import com.delains.dao.pos.SalesClearanceDAO;
import com.delains.model.sales.SalesClearance;

import javafx.collections.ObservableList;

public class SalesClearanceHibernation {


	private static ObservableList < SalesClearance > SalesClearances;

	public static ObservableList < SalesClearance > getSalesClearances() {
		return SalesClearances;
	}

	public static void setSalesClearances( ObservableList < SalesClearance > SalesClearances ) {
		SalesClearanceHibernation.SalesClearances = SalesClearances;
	}

	public static void newSalesClearance( SalesClearance clearance ) {
		SalesClearanceDAO.newSalesClearance( clearance );
	}

	public static ObservableList < SalesClearance > findAllSalesClearancesWithoutRefreshing() {
		if ( getSalesClearances() == null ) {
			setSalesClearances( SalesClearanceDAO.changeListUtilToListObservable() );
		}
		return getSalesClearances();
	}

	public static ObservableList < SalesClearance > findAllSalesClearancesWithRefreshing() {
		SalesClearanceDAO.findAllSalesClearancesListUtil();
		setSalesClearances( SalesClearanceDAO.changeListUtilToListObservable() );
		return getSalesClearances();
	}



}
