package com.delains.dao.pos;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.sales.SalesReturn;

import javafx.collections.ObservableList;

public class SalesReturnHibernation {

	public static void newSalesReturn( SalesReturn salesReturn ) {
		SalesReturnDAO.newSalesReturn( salesReturn );
	}

	private static ObservableList < SalesReturn > salesReturns;

	public static ObservableList < SalesReturn > getSalesReturns() {
		return salesReturns;
	}

	public static void setSalesReturns( ObservableList < SalesReturn > salesReturns ) {
		SalesReturnHibernation.salesReturns = salesReturns;
	}

	public static ObservableList < SalesReturn > findAllSalesReturnsWithoutRefreshing() {

		if ( getSalesReturns() == null ) {
			setSalesReturns( SalesReturnDAO.changeListUtilToListObservable() );
		}

		return getSalesReturns();

	}

	public static ObservableList < SalesReturn > findAllSalesReturnsWithRefreshing() {

		setSalesReturns( SalesReturnDAO.changeListUtilToListObservable() );
		return getSalesReturns();

	}

	public static Map < BigDecimal, SalesReturn > mapOfSalesReturnToThierIDs() {

		Map < BigDecimal, SalesReturn > map = new LinkedHashMap <>();

		for ( SalesReturn sr : findAllSalesReturnsWithRefreshing() ) {
			map.put( sr.getId(), sr );
		}

		return map;

	}
}
