package com.delains.ui.deposits;

import java.math.BigDecimal;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.deposits.DepositHibernation;
import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.customers.Customer;
import com.delains.model.deposits.Deposit;
import com.delains.model.payments.MediumOfPayment;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.utilities.CurrentTimestamp;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class DepositTabInAccordion extends VBox {

	public DepositTabInAccordion() {
		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonShowPopForNewMediumOfPayment = new JFXButton( "new deposit" );
		gridPane.add( buttonShowPopForNewMediumOfPayment, 0, 0 );

		JFXButton buttonDelete = new JFXButton( "delete" );
		gridPane.add( buttonDelete, 0, 1 );

		this.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps( new BorderPane( buildMediumOfpaymentPopUp() ),
				"new medium of payment" );

		frame = new DepositFrame();

		buttonShowPopForNewMediumOfPayment.setOnAction( e -> {

			buttonSave.setText( "save" );

			populateComboBoxMediumFromId();
			populateComboBoxMediumToId();
			populateComboBoxSupplier();

			stageForAllPopUps.showAndWait();
		} );

		buttonSave.setOnAction( e -> {
			if ( buttonSave.getText().equalsIgnoreCase( "save" ) ) {
				saveNewDeposit();
			}

		} );

		buttonCancel.setOnAction( e -> {
			stageForAllPopUps.close();
			this.clearFields();
		} );
		buttonDelete.setOnAction( e -> delete() );

		this.getMediumOfPaymentFromId();
		this.getMediumOfPaymentToId();
		this.getSupplierClearedSelectedId();

	}

	private StageForAllPopUps stageForAllPopUps;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private DatePicker datePicker;
	private TextField fieldAmountDeposited;
	private TextArea areaReason;

	private GridPane buildMediumOfpaymentPopUp() {

		GridPane gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		gridPane.add( new Label( "date: " ), 0, 0 );
		datePicker = new DatePicker();
		datePicker.setEditable( false );
		gridPane.add( datePicker, 1, 0 );

		gridPane.add( new Label( "amount deposited: " ), 0, 1 );
		fieldAmountDeposited = new TextField();
		gridPane.add( fieldAmountDeposited, 1, 1 );

		gridPane.add( new Label( "from (medium)" ), 0, 2 );
		comboBoxMediumOfPaymentFromId = new ComboBox <>();
		gridPane.add( comboBoxMediumOfPaymentFromId, 1, 2 );

		gridPane.add( new Label( "to (medium)" ), 0, 3 );
		comboBoxMediumOfPaymentToId = new ComboBox <>();
		gridPane.add( comboBoxMediumOfPaymentToId, 1, 3 );

		gridPane.add( new Label( "customer:" ), 0, 4 );
		comboBoxSuppliers = new ComboBox <>();
		gridPane.add( comboBoxSuppliers, 1, 4 );

		gridPane.add( new Label( "reason:" ), 0, 5 );
		areaReason = new TextArea();
		areaReason.setWrapText( true );
		areaReason.setPrefWidth( 20 );
		areaReason.setPrefHeight( 20 );
		gridPane.add( areaReason, 1, 5 );

		buttonSave = new JFXButton( "save" );
		buttonCancel = new JFXButton( "cancel" );

		gridPane.add( new HBox( 10, buttonSave, buttonCancel ), 1, 6 );

		return gridPane;
	}

	private void clearFields() {
		fieldAmountDeposited.clear();
		FieldClearance.clearComboBox( comboBoxMediumOfPaymentFromId );
		FieldClearance.clearComboBox( comboBoxMediumOfPaymentToId );
		FieldClearance.clearComboBox( comboBoxSuppliers );
	}

	private void saveNewDeposit() {
		String date = null;
		if ( datePicker.getValue() != null ) {
			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( datePicker.getValue() );
		} else {
			date = CurrentTimestamp.getDateTimeEndAtMinutes();
		}

		BigDecimal amountDeposited = BigDecimal.ZERO;

		new StageForAlerts();
		if ( fieldAmountDeposited.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please specify the amount deposited" );
			return;
		} else {
			String amountDepositedStr = NumberFormatting.testNumberCorrectness( fieldAmountDeposited.getText().trim() );
			if ( NumberFormatting.isNumberCorrect() == true ) {
				amountDeposited = new BigDecimal( amountDepositedStr );
			} else {
				StageForAlerts.inform( "alert", "incorrect format" );
				return;
			}
		}

		Deposit d = new Deposit();
		d.setDate( date );
		d.setMediumOfPaymentFromId( mediumOfPaymentFromId );
		d.setMediumOfPaymentToId( mediumOfPaymentToId );
		d.setCustomerId( customer );
		d.setReason( areaReason.getText().trim() );
		d.setAmountDeposited( amountDeposited );

		DepositHibernation.newDeposit( d );
		this.clearFields();
		DepositFrame.populateTable();
	}

	private DepositFrame frame;

	private void delete() {

		new StageForAlerts();

		int row = DepositFrame.getTableView().getSelectionModel().getSelectedIndex();
		Deposit p = null;

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getDeposit();
		} else {
			p = DepositFrame.getTableView().getSelectionModel().getSelectedItem();
		}

		DepositHibernation.deleteDeposit( p.getId() );

		DepositFrame.getTableView().getItems().clear();
		DepositFrame.getTableView().getItems().addAll( DepositHibernation.findAllDepositsObservableListRefreshed() );

	}

	/**
	 * workig for the medium of payment FROM ID
	 */

	private ComboBox < MediumOfPayment > comboBoxMediumOfPaymentFromId = null;

	private void populateComboBoxMediumFromId() {
		comboBoxMediumOfPaymentFromId.getItems().clear();
		comboBoxMediumOfPaymentFromId.getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
		comboBoxMediumOfPaymentFromId.setConverter( new StringConverter < MediumOfPayment >() {
			@Override
			public String toString( MediumOfPayment object ) {
				return object.getNameOfMediumOfPayment().concat( "-" ).concat( object.getSpecificationOrDescription() );
			}

			@Override
			public MediumOfPayment fromString( String string ) {
				return comboBoxMediumOfPaymentFromId.getItems().stream()
						.filter( e -> e.getNameOfMediumOfPayment().concat( "-" )
								.concat( e.getSpecificationOrDescription() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );
	}

	private MediumOfPayment mediumOfPaymentFromId = null;

	private MediumOfPayment getMediumOfPaymentFromId() {
		comboBoxMediumOfPaymentFromId.setOnAction(
				e -> mediumOfPaymentFromId = comboBoxMediumOfPaymentFromId.getSelectionModel().getSelectedItem() );
		return mediumOfPaymentFromId;
	}

	/**
	 * working for the medium of payment TO ID
	 */
	private ComboBox < MediumOfPayment > comboBoxMediumOfPaymentToId;
	private MediumOfPayment mediumOfPaymentToId = null;

	private void populateComboBoxMediumToId() {
		comboBoxMediumOfPaymentToId.getItems().clear();
		comboBoxMediumOfPaymentToId.getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
		comboBoxMediumOfPaymentToId.setConverter( new StringConverter < MediumOfPayment >() {
			@Override
			public String toString( MediumOfPayment object ) {

				return object.getNameOfMediumOfPayment().concat( "-" ).concat( object.getSpecificationOrDescription() );
			}

			@Override
			public MediumOfPayment fromString( String string ) {
				return comboBoxMediumOfPaymentToId.getItems().stream()
						.filter( e -> e.getNameOfMediumOfPayment().concat( "-" )
								.concat( e.getSpecificationOrDescription() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );
	}

	private MediumOfPayment getMediumOfPaymentToId() {
		comboBoxMediumOfPaymentToId.setOnAction( e -> {
			mediumOfPaymentToId = comboBoxMediumOfPaymentToId.getSelectionModel().getSelectedItem();
		} );
		return mediumOfPaymentToId;
	}

	/**
	 * populating the supplier combobox
	 */

	private ComboBox < Customer > comboBoxSuppliers;

	private void populateComboBoxSupplier() {
		comboBoxSuppliers.getItems().clear();

		if ( comboBoxSuppliers.getItems().isEmpty() ) {
			comboBoxSuppliers.setItems( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		} else {
			comboBoxSuppliers.getItems().clear();
			comboBoxSuppliers.getItems().addAll( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		}

		comboBoxSuppliers.setConverter( new StringConverter < Customer >() {

			@Override
			public String toString( Customer object ) {
				return object.getCustomerName();
			}

			@Override
			public Customer fromString( String string ) {
				return comboBoxSuppliers.getItems().stream().filter( e -> e.getCustomerName().equals( string ) )
						.findFirst().orElse( null );
			}
		} );

	}

	private Customer customer;

	private Customer getSupplierClearedSelectedId() {
		comboBoxSuppliers.setOnAction( e -> customer = comboBoxSuppliers.getSelectionModel().getSelectedItem() );
		return customer;
	}

}
