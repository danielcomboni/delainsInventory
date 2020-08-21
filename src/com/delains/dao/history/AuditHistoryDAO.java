package com.delains.dao.history;

import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.history.AuditHistory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AuditHistoryDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "action", "longtext" );
		map.put( "user_id", "INTEGER" );

		return map;

	}

	private static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "audit_history" );
	}

	public static AuditHistory newAuditHistory( AuditHistory salesReturn ) {
		createTable();
		return AuditHistoryDAOInsertion.newAuditHistory( salesReturn );
	}

	public static List < AuditHistory > findAllAuditHistoryListUtil() {
		createTable();
		return AuditHistoryDAORetrieve.findAllAuditHistorys();
	}

	public static ObservableList < AuditHistory > changeListUtilToListObservable() {

		ObservableList < AuditHistory > observableList = FXCollections.observableArrayList();

		for ( AuditHistory sr : findAllAuditHistoryListUtil() ) {
			observableList.add( sr );
		}

		return observableList;

	}

}
