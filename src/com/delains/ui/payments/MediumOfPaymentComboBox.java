package com.delains.ui.payments;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.model.payments.MediumOfPayment;

import com.delains.ui.sales.POSFrame;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ComboBox;

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


		Service<Void> service;
		service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						comboBox.getItems().clear();
						comboBox.getItems().addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
						POSFrame.setConverterComboboxMediumOfPayment(comboBox);

						return null;
					}
				};
			}
		};
		service.start();


		return comboBox;
	}

	private MediumOfPayment mediumOfPayment = new MediumOfPayment();

	public MediumOfPayment getMediumOfPayment() {
		comboBox.setOnAction( e -> {
			mediumOfPayment = comboBox.getSelectionModel().getSelectedItem();
		} );
		return mediumOfPayment;
	}

}
