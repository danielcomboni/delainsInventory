package com.delains.dao.deposits;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.deposits.Deposit;

import javafx.collections.ObservableList;

public class DepositHibernation {

	private DepositHibernation() {

	}

	private static ObservableList < Deposit > purchases;

	public static ObservableList < Deposit > getDeposits() {
		return purchases;
	}

	public static void setDeposits( ObservableList < Deposit > purchases ) {
		DepositHibernation.purchases = purchases;
	}

	public static void newDeposit( Deposit purchase ) {
		DepositDAO.newDeposit( purchase );
	}

	public static void updateDeposit( Deposit purchase, BigDecimal idOfDeposit ) {
		DepositDAO.updateDeposit( purchase, idOfDeposit );
	}

	public static void deleteDeposit( BigDecimal id ) {
		DepositDAO.deleteDeposit( id );
	}

	public static ObservableList < Deposit > findAllDepositsObservableList() {
		if ( getDeposits() == null ) {
			setDeposits( DepositDAO.changeListUtilToListObservable() );
		}
		return getDeposits();
	}

	public static ObservableList < Deposit > findAllDepositsObservableListRefreshed() {
		setDeposits( DepositDAO.changeListUtilToListObservable() );
		return getDeposits();
	}

	public static Map < BigDecimal, Deposit > mapOfDepositsToThierId() {
		Map < BigDecimal, Deposit > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllDepositsObservableList().size(); i++ ) {
			Deposit type = findAllDepositsObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

}
