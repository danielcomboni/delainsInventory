package com.delains.dao.stock;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.model.stock.StockWarningPoint;

import javafx.collections.ObservableList;

public class StockWarningPointHibernation {

	private static ObservableList < StockWarningPoint > allStockWarningPoints;

	public static ObservableList < StockWarningPoint > getAllStockWarningPoints() {
		return allStockWarningPoints;
	}

	public static void setAllStockWarningPoints( ObservableList < StockWarningPoint > allStockWarningPoints ) {
		StockWarningPointHibernation.allStockWarningPoints = allStockWarningPoints;
	}

	public static ObservableList < StockWarningPoint > findAllStockWarningPointsObservableList() {
		if ( getAllStockWarningPoints() == null ) {
			setAllStockWarningPoints( StockWarningPointDAO.changeListUtilToListObservable() );
		}
		return getAllStockWarningPoints();
	}

	public static ObservableList < StockWarningPoint > findAllStockWarningPointsObservableListRefreshed() {
		StockWarningPointDAO.changeListUtilToListObservable().clear();
		setAllStockWarningPoints( StockWarningPointDAO.changeListUtilToListObservable() );
		return getAllStockWarningPoints();
	}

	public static Map < BigDecimal, StockWarningPoint > mapOfStockWarningPointsToThierId() {
		Map < BigDecimal, StockWarningPoint > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllStockWarningPointsObservableList().size(); i++ ) {
			StockWarningPoint type = findAllStockWarningPointsObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

	public static Map < BigDecimal, StockWarningPoint > itemIDsMappedToThierStockWarningPoints() {
		Map < BigDecimal, StockWarningPoint > map = new LinkedHashMap <>();
		for ( Entry < BigDecimal, StockWarningPoint > m : mapOfStockWarningPointsToThierId().entrySet() ) {
			map.put( m.getValue().getItemId().getId(), m.getValue() );
		}
		return map;
	}

	public static void newStockWarningPoint( StockWarningPoint item ) {
		StockWarningPointDAO.newStockWarning( item );
	}

	public static void updateStockWarningPoint( StockWarningPoint item, BigDecimal idOfStockWarningPoint ) {
		StockWarningPointDAO.updateStockWarningPoint( item, idOfStockWarningPoint );
	}

}
