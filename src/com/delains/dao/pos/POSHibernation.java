package com.delains.dao.pos;

import java.math.BigDecimal;
import java.util.Map;

import com.delains.model.items.Item;
import com.delains.model.pos.POS;

import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class POSHibernation {

	public static int[] newPOS( ObservableMap < Item, POS > map ) {

		return POSDAOInvoker.newPOS( map );

	}

	public static Map < BigDecimal, POS > mapPOSToItsID;

	public static Map < BigDecimal, POS > getMapPOSToItsID() {
		return mapPOSToItsID;
	}

	public static void setMapPOSToItsID( Map < BigDecimal, POS > mapPOSToItsID ) {
		POSHibernation.mapPOSToItsID = mapPOSToItsID;
	}

	public static Map < BigDecimal, POS > getMapPOSToID() {

		if ( getMapPOSToItsID() == null ) {

			setMapPOSToItsID( POSDAOInvoker.mapPOSToIDsViaUtilMap() );

		}

		return getMapPOSToItsID();

	}

	private static ObservableList < POS > allPOSs;

	private static ObservableList < POS > getAllPOSs() {
		return allPOSs;
	}

	private static void setAllPOSs( ObservableList < POS > allPOSs ) {
		POSHibernation.allPOSs = allPOSs;
	}

	public static ObservableList < POS > findAllPOSesObservableList() {
		if ( getAllPOSs() == null ) {
			setAllPOSs( POSDAOInvoker.changeListUtilToListObservable() );
		}
		return getAllPOSs();
	}

	public static ObservableList < POS > findAllPOSesObservableListRefreshed() {
		setAllPOSs( POSDAOInvoker.changeListUtilToListObservable() );
		return getAllPOSs();
	}

}
