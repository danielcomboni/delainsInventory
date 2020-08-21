package com.delains.dao.deposits;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.deposits.Deposit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepositDAO {

	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_deposited", "DECIMAL(50,5)" );
		map.put( "medium_of_payment_from_id", "INTEGER" );
		map.put( "medium_of_payment_to_id", "INTEGER" );
		map.put( "supplier_cleared_id", "INTEGER" );
		map.put( "reason", "LONGTEXT" );

		return map;
	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "deposits" );
	}

	public static void newDeposit( Deposit deposit ) {
		createTable();
		DepositDAOInsert.newDeposit( deposit );
	}

	public static void updateDeposit( Deposit deposit, BigDecimal idOfDeposit ) {
		DepositDAOUpdate.updateDeposit( deposit, idOfDeposit );
	}

	public static void deleteDeposit( BigDecimal id ) {
		DepositDAODelete.deleteDeposit( id );
	}

	public static List < Deposit > findAllDepositListUtil() {
		createTable();
		return DepositDAORetrieve.findAllDeposit();
	}

	public static ObservableList < Deposit > changeListUtilToListObservable() {

		ObservableList < Deposit > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllDepositListUtil().size(); i++ ) {
			Deposit u = findAllDepositListUtil().get( i );
			observableList.add( u );
		}

		return observableList;
	}
}
