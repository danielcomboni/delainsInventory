package com.delains.dao.expenses;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.expenses.Expenses;

import javafx.collections.ObservableList;

public class ExpensesHibernation {

	private static ObservableList < Expenses > purchases;

	public static ObservableList < Expenses > getExpensess() {
		return purchases;
	}

	public static void setExpensess( ObservableList < Expenses > purchases ) {
		ExpensesHibernation.purchases = purchases;
	}

	public static void newExpenses( Expenses purchase ) {
		ExpensesDAO.newExpense( purchase );
	}

	public static void updateExpenses( Expenses purchase, BigDecimal idOfExpenses ) {
		ExpensesDAO.updateExpense( purchase, idOfExpenses );
	}

	public static void deleteExpenses( BigDecimal id ) {
		ExpensesDAO.deleteExpense( id );
	}

	public static ObservableList < Expenses > findAllExpensessObservableList() {
		if ( getExpensess() == null ) {
			setExpensess( ExpensesDAO.changeListUtilToListObservable() );
		}
		return getExpensess();
	}

	public static ObservableList < Expenses > findAllExpensessObservableListRefreshed() {
		setExpensess( ExpensesDAO.changeListUtilToListObservable() );
		return getExpensess();
	}

	public static Map < BigDecimal, Expenses > mapOfExpensessToThierId() {
		Map < BigDecimal, Expenses > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllExpensessObservableList().size(); i++ ) {
			Expenses type = findAllExpensessObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

}
