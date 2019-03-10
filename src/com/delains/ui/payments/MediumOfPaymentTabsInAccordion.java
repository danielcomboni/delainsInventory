package com.delains.ui.payments;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.model.payments.MediumOfPayment;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MediumOfPaymentTabsInAccordion extends VBox {

	public MediumOfPaymentTabsInAccordion() {
		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonShowPopForNewMediumOfPayment = new JFXButton( "new medium" );
		gridPane.add( buttonShowPopForNewMediumOfPayment, 0, 0 );

		JFXButton buttonEdit = new JFXButton( "edit" );
		gridPane.add( buttonEdit, 0, 1 );

		JFXButton buttonDelete = new JFXButton( "delete" );
		gridPane.add( buttonDelete, 0, 2 );

		this.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps( new BorderPane( buildMediumOfpaymentPopUp() ),
				"new medium of payment" );

		frame = new MediumOfPaymentFrame();

		buttonShowPopForNewMediumOfPayment.setOnAction( e -> {
			buttonSave.setText( "save" );
			fieldNameOfMedium.setText( null );
			areaSpecificationOfMedium.setText( null );
			stageForAllPopUps.showAndWait();
		} );

		buttonSave.setOnAction( e -> {
			if ( buttonSave.getText().equalsIgnoreCase( "save" ) ) {
				saveNewMediumOfPayment();
			} else {
				saveChanges();
			}
		} );

		buttonCancel.setOnAction( e -> stageForAllPopUps.close() );
		buttonEdit.setOnAction( e -> showPopupForEditing() );
		buttonDelete.setOnAction( e -> deleteMediumOfPayment() );

	}

	private StageForAllPopUps stageForAllPopUps;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private TextField fieldNameOfMedium;
	private TextArea areaSpecificationOfMedium;

	private GridPane buildMediumOfpaymentPopUp() {

		GridPane gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		gridPane.add( new Label( "name of payment medium:" ), 0, 0 );
		fieldNameOfMedium = new TextField();
		fieldNameOfMedium.setPromptText( "e.g bank" );
		gridPane.add( fieldNameOfMedium, 1, 0 );

		gridPane.add( new Label( "specify (describe medium):" ), 0, 1 );
		areaSpecificationOfMedium = new TextArea();
		areaSpecificationOfMedium.setWrapText( true );
		areaSpecificationOfMedium.setPromptText( "e.g standbic bank" );
		areaSpecificationOfMedium.setPrefHeight( 20 );
		areaSpecificationOfMedium.setPrefWidth( 10 );
		gridPane.add( areaSpecificationOfMedium, 1, 1 );

		buttonSave = new JFXButton( "save" );
		buttonCancel = new JFXButton( "cancel" );

		gridPane.add( new HBox( 10, buttonSave, buttonCancel ), 1, 2 );

		return gridPane;
	}

	private void saveNewMediumOfPayment() {
		String name = null;
		String specification = null;
		new StageForAlerts();
		if ( fieldNameOfMedium.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please specify the name of the medium of payment" );
			return;
		} else {
			name = fieldNameOfMedium.getText();
		}
		if ( areaSpecificationOfMedium.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please give the specification of the payment means" );
			return;
		} else {
			specification = areaSpecificationOfMedium.getText();
		}
		MediumOfPayment payment = new MediumOfPayment();
		payment.setNameOfMediumOfPayment( name );
		payment.setSpecificationOrDescription( specification );
		MediumOfPaymentHibernation.newMediumOfPayment( payment );

		FieldClearance.clearTextField( fieldNameOfMedium );
		areaSpecificationOfMedium.clear();

		MediumOfPaymentFrame.getTableView().getItems().clear();
		MediumOfPaymentFrame.getTableView().getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
	}

	private MediumOfPaymentFrame frame;

	private void showPopupForEditing() {

		new StageForAlerts();

		int row = MediumOfPaymentFrame.getTableView().getSelectionModel().getSelectedIndex();
		MediumOfPayment p = null;

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getMediumOfPayment();
		} else {
			p = MediumOfPaymentFrame.getTableView().getSelectionModel().getSelectedItem();
		}

		buttonSave.setText( "save changes" );

		mediumOfPayment = p;

		fieldNameOfMedium.setText( p.getNameOfMediumOfPayment() );
		areaSpecificationOfMedium.setText( p.getSpecificationOrDescription() );
		stageForAllPopUps.showAndWait();

	}

	private MediumOfPayment mediumOfPayment = null;

	private void saveChanges() {
		MediumOfPayment paymentNew = mediumOfPayment;

		String name = null;
		String specification = null;

		if ( fieldNameOfMedium.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please specify the name of the medium of payment" );
			return;
		} else {
			name = fieldNameOfMedium.getText();
		}
		if ( areaSpecificationOfMedium.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please give the specification of the payment means" );
			return;
		} else {
			specification = areaSpecificationOfMedium.getText();
		}

		paymentNew.setNameOfMediumOfPayment( name );
		paymentNew.setSpecificationOrDescription( specification );

		MediumOfPaymentHibernation.updateMediumOfPayment( paymentNew, paymentNew.getId() );

		MediumOfPaymentFrame.getTableView().getItems().clear();
		MediumOfPaymentFrame.getTableView().getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );

	}

	private void deleteMediumOfPayment() {
		new StageForAlerts();

		int row = MediumOfPaymentFrame.getTableView().getSelectionModel().getSelectedIndex();
		MediumOfPayment p = null;

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getMediumOfPayment();
		} else {
			p = MediumOfPaymentFrame.getTableView().getSelectionModel().getSelectedItem();
		}

		MediumOfPaymentHibernation.deleteMediumOfPayment( p.getId() );

		MediumOfPaymentFrame.getTableView().getItems().clear();
		MediumOfPaymentFrame.getTableView().getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );

	}

}
