package com.delains.dao.expenses;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.expenses.Expenses;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExpensesDAO {

	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "amount_spent", "DECIMAL(50,5)" );
		map.put( "reason", "LONGTEXT" );
		map.put( "medium_of_payment_id", "INTEGER" );

		return map;
	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "expenses" );
	}

	public static void newExpense( Expenses expenses ) {
		createTable();
		ExpensesDAOInsert.newExpense( expenses );
	}

	public static void updateExpense( Expenses expenses, BigDecimal idOfExpense ) {
		ExpensesDAOUpdate.updateExpenses( expenses, idOfExpense );
	}

	public static void deleteExpense( BigDecimal id ) {
		ExpensesDAODelete.deleteExpense( id );
	}

	public static List < Expenses > findAllExpensesListUtil() {
		createTable();
		return ExpensesDAORetrieve.findAllExpenses();
	}

	public static ObservableList < Expenses > changeListUtilToListObservable() {

		ObservableList < Expenses > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllExpensesListUtil().size(); i++ ) {
			Expenses u = findAllExpensesListUtil().get( i );
			observableList.add( u );
		}

		return observableList;
	}
}
