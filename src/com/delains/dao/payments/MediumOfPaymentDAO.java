package com.delains.dao.payments;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;

import com.delains.dao.utils.DBUtils;
import com.delains.model.payments.MediumOfPayment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MediumOfPaymentDAO {

	/**
	 * create table with its definitions
	 * 
	 * @return nothing
	 */
	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "name_of_medium_of_payment", "VARCHAR(255)" );
		map.put( "specification", "VARCHAR(255)" );

		return map;
	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "medium_of_payment" );
	}

	public static void newMediumOfPayment( MediumOfPayment mediumOfPayment ) {
		createTable();
		MediumOfPaymentDAOInsert.newMediumOfPayment( mediumOfPayment );
	}

	public static void updateMediumOfPayment( MediumOfPayment mediumOfPayment, BigDecimal idOfPurchase ) {
		MediumOfPaymentDAOUpdate.updateMediumOfPayment( mediumOfPayment, idOfPurchase );
	}

	public static void deleteMediumOfPayment( BigDecimal id ) {
		MediumOfPaymentDAODelete.deleteMediumOfPayment( id );
	}

	public static List < MediumOfPayment > findAllMediumsOfPaymentListUtil() {
		createTable();
		return MediumOfPaymentDAORetrieve.findAllMediumsOfPayment();
	}

	public static ObservableList < MediumOfPayment > changeListUtilToListObservable() {

		ObservableList < MediumOfPayment > observableList = FXCollections.observableArrayList();

		for ( int i = 0; i < findAllMediumsOfPaymentListUtil().size(); i++ ) {
			MediumOfPayment u = findAllMediumsOfPaymentListUtil().get( i );
			observableList.add( u );
		}

		return observableList;
	}
}
