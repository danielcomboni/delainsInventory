package com.delains.dao.item;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.model.items.Item;

import javafx.collections.ObservableList;

public class ItemHibernation {

	private static ObservableList < Item > allItems;

	public static ObservableList < Item > getAllItems() {
		return allItems;
	}

	public static void setAllItems( ObservableList < Item > allItems ) {
		ItemHibernation.allItems = allItems;
	}

	public static ObservableList < Item > findAllItemsObservableList() {
		if ( getAllItems() == null ) {
setAllItems( ItemDAO.changeListUtilToListObservable() );
		}
		return getAllItems();
	}


	public static ObservableList < Item > findAllItemsObservableListRefreshed() {
		ItemDAO.changeListUtilToListObservable().clear();
		setAllItems( ItemDAO.changeListUtilToListObservable() );
		return getAllItems();
	}

	public static Map < BigDecimal, Item > mapOfItemsToThierId() {
		Map < BigDecimal, Item > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllItemsObservableList().size(); i++ ) {
			Item type = findAllItemsObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

	public static Map < String, Item > barcodesMappedToThierItems() {
		Map < String, Item > map = new LinkedHashMap < String, Item >();
		for ( Entry < BigDecimal, Item > m : mapOfItemsToThierId().entrySet() ) {
			map.put( m.getValue().getBarcode(), m.getValue() );
		}
		return map;
	}

	public static Item newItem( Item item ) {
		return ItemDAO.newItem( item );
	}

	public static Item updateItem( Item item, BigDecimal idOfItem ) {
		return ItemDAO.updateItem( item, idOfItem );
	}

	public static void deleteItem( BigDecimal id ) {
		ItemDAO.deleteItem( id );
	}

}
