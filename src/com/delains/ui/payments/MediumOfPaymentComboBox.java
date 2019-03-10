package com.delains.ui.payments;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.model.payments.MediumOfPayment;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class MediumOfPaymentComboBox {

	private static ComboBox < MediumOfPayment > comboBox;

	private static MediumOfPaymentComboBox instance = null;

	public static MediumOfPaymentComboBox getInstance() {
		if ( instance != null ) {
			return instance;
		} else {
			return new MediumOfPaymentComboBox();
		}
	}

	private MediumOfPaymentComboBox() {
		comboBox = new ComboBox <>();
		populateComboBoxMedium();
		getMediumOfPayment();
	}

	public ComboBox < MediumOfPayment > populateComboBoxMedium() {

		comboBox.getItems().clear();
		comboBox.getItems().addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
		comboBox.setConverter( new StringConverter < MediumOfPayment >() {
			@Override
			public String toString( MediumOfPayment object ) {

				return object.getNameOfMediumOfPayment().concat( "-" ).concat( object.getSpecificationOrDescription() );
			}

			@Override
			public MediumOfPayment fromString( String string ) {
				return comboBox.getItems().stream()
						.filter( e -> e.getNameOfMediumOfPayment().concat( "-" )
								.concat( e.getSpecificationOrDescription() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );
		return comboBox;
	}

	private MediumOfPayment mediumOfPayment = new MediumOfPayment();

	public MediumOfPayment getMediumOfPayment() {
		comboBox.setOnAction( e -> {
			mediumOfPayment = comboBox.getSelectionModel().getSelectedItem();
			System.out.println( "meddddd: " + mediumOfPayment );
			System.out.println( "meddddd2: " + comboBox.getSelectionModel().getSelectedItem() );
		} );
		return mediumOfPayment;
	}

}
