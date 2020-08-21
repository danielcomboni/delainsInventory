package com.delains.dao.item;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemDAO {

	private static LinkedHashMap < String, String > itemTableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "item_name", "VARCHAR(255)" );
		map.put( "item_description", "LONGTEXT" );
		map.put( "unit_of_measurement", "VARCHAR(255)" );
		map.put( "barcode", "VARCHAR(255) UNIQUE" );
		map.put( "package", "VARCHAR(255)" );
		map.put( "package_volume", "decimal(50,5)" );

		return map;
	}

	private static void createTableUsers() {
		DBUtils.apiToCreateTable( itemTableDefinitions(), "items" );
	}

	public static Item newItem( Item item ) {
		createTableUsers();
		return ItemDAOInsert.newItem(item);
	}

	public static Item updateItem( Item item, BigDecimal idOfItem ) {
		return ItemDAOUpdate.updateUser( item, idOfItem );
	}

	public static void deleteItem( BigDecimal id ) {
		ItemsDAODelete.deleteItem( id );
		;
	}

	public static List < Item > findAllItemsListUtil() {
		createTableUsers();
		return ItemDAORetrieve.findAllItems();
	}

	public static ObservableList < Item > changeListUtilToListObservable() {

		ObservableList < Item > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllItemsListUtil().size(); i++ ) {
			Item u = findAllItemsListUtil().get( i );
			observableList.add( u );
			;
		}

		return observableList;
	}
}
