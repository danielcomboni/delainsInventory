package com.delains.dao.payments;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.payments.MediumOfPayment;

import javafx.collections.ObservableList;

public class MediumOfPaymentHibernation {

	private static ObservableList < MediumOfPayment > purchases;

	public static ObservableList < MediumOfPayment > getMediumOfPayments() {
		return purchases;
	}

	public static void setMediumOfPayments( ObservableList < MediumOfPayment > purchases ) {
		MediumOfPaymentHibernation.purchases = purchases;
	}

	public static void newMediumOfPayment( MediumOfPayment purchase ) {
		MediumOfPaymentDAO.newMediumOfPayment( purchase );
	}

	public static void updateMediumOfPayment( MediumOfPayment purchase, BigDecimal idOfMediumOfPayment ) {
		MediumOfPaymentDAO.updateMediumOfPayment( purchase, idOfMediumOfPayment );
	}

	public static void deleteMediumOfPayment( BigDecimal id ) {
		MediumOfPaymentDAO.deleteMediumOfPayment( id );
	}

	public static ObservableList < MediumOfPayment > findAllMediumOfPaymentsObservableList() {
		if ( getMediumOfPayments() == null ) {
			setMediumOfPayments( MediumOfPaymentDAO.changeListUtilToListObservable() );
		}
		return getMediumOfPayments();
	}

	public static ObservableList < MediumOfPayment > findAllMediumOfPaymentsObservableListRefreshed() {
		setMediumOfPayments( MediumOfPaymentDAO.changeListUtilToListObservable() );
		return getMediumOfPayments();
	}

	public static Map < BigDecimal, MediumOfPayment > mapOfMediumOfPaymentsToThierId() {
		Map < BigDecimal, MediumOfPayment > map = new LinkedHashMap <>();
		for ( int i = 0; i < findAllMediumOfPaymentsObservableList().size(); i++ ) {
			MediumOfPayment type = findAllMediumOfPaymentsObservableList().get( i );
			map.put( type.getId(), type );
		}
		return map;
	}

	// public static Map < BigDecimal, MediumOfPayment >
	// mapOfMediumOfPaymentsToThierItemsID() {
	// Map < BigDecimal, MediumOfPayment > map = new LinkedHashMap <>();
	// for ( int i = 0; i < findAllMediumOfPaymentsObservableList().size(); i++ ) {
	// MediumOfPayment type = findAllMediumOfPaymentsObservableList().get( i );
	//
	// map.put( type.getItemId().getId(), type );
	// }
	// return map;
	// }

}
