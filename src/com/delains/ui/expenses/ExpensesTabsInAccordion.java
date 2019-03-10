package com.delains.ui.expenses;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.delains.dao.expenses.ExpensesHibernation;
import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.expenses.Expenses;
import com.delains.model.payments.MediumOfPayment;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.payments.MediumOfPaymentComboBox;
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

public class ExpensesTabsInAccordion extends VBox {

	public ExpensesTabsInAccordion() {
		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonShowPopForNewExpense = new JFXButton( "new expense" );
		gridPane.add( buttonShowPopForNewExpense, 0, 0 );

		JFXButton buttonEdit = new JFXButton( "edit" );
		gridPane.add( buttonEdit, 0, 1 );

		JFXButton buttonDelete = new JFXButton( "delete" );
		gridPane.add( buttonDelete, 0, 2 );

		this.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps( new BorderPane( buildMediumOfpaymentPopUp() ), "new expense" );

		frame = new ExpensesFrame();

		buttonShowPopForNewExpense.setOnAction( e -> {
			buttonSave.setText( "save" );
			fieldAmountSpent.setText( null );
			areaReason.setText( null );
			stageForAllPopUps.showAndWait();
		} );

		buttonSave.setOnAction( e -> {
			if ( buttonSave.getText().equalsIgnoreCase( "save" ) ) {
				saveExpense();
				ExpensesFrame.getTableView().getItems().clear();
				ExpensesFrame.getTableView().getItems()
						.addAll( ExpensesHibernation.findAllExpensessObservableListRefreshed() );
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

	private DatePicker datePicker;

	private TextField fieldAmountSpent;

	private TextArea areaReason;

	private ComboBox < MediumOfPayment > comboBox;

	private GridPane buildMediumOfpaymentPopUp() {

		GridPane gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		gridPane.add( new Label( "date:" ), 0, 0 );
		datePicker = new DatePicker();
		gridPane.add( datePicker, 1, 0 );

		gridPane.add( new Label( "amount spent:" ), 0, 1 );
		fieldAmountSpent = new TextField();
		gridPane.add( fieldAmountSpent, 1, 1 );

		gridPane.add( new Label( "reason: " ), 0, 2 );
		areaReason = new TextArea();
		areaReason.setWrapText( true );
		areaReason.setPrefHeight( 30 );
		areaReason.setPrefWidth( 15 );
		gridPane.add( areaReason, 1, 2 );

		gridPane.add( new Label( "payment medium: " ), 0, 3 );
		comboBox = new ComboBox <>();
		gridPane.add( comboBox, 1, 3 );

		buttonSave = new JFXButton( "save" );
		buttonCancel = new JFXButton( "cancel" );

		gridPane.add( new HBox( 10, buttonSave, buttonCancel ), 1, 4 );

		populateComboBoxMedium();

		getMediumOfPayment();

		return gridPane;
	}

	private void saveExpense() {

		String date = null;
		String amountSpentStr = null;
		String reason = null;

		new StageForAlerts();

		if ( datePicker.getValue() == null ) {
			date = CurrentTimestamp.getDateTimeEndAtMinutes();
		} else {
			LocalDate localDate = datePicker.getValue();
			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );
		}

		if ( fieldAmountSpent.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please specify the name amount spent" );
			return;
		} else {
			amountSpentStr = NumberFormatting.testNumberCorrectness( fieldAmountSpent.getText() );
		}

		if ( areaReason.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please give the reason for spending this amount" );
			return;
		} else {
			reason = areaReason.getText().trim();
		}

		Expenses expenses = new Expenses();
		expenses.setAmountSpent( new BigDecimal( amountSpentStr ) );
		expenses.setDate( date );
		expenses.setMediumOfPaymentId( mediumOfPayment );
		expenses.setReason( reason );

		ExpensesHibernation.newExpenses( expenses );

		FieldClearance.clearTextField( fieldAmountSpent );
		areaReason.clear();

		ExpensesFrame.getTableView().getItems().clear();
		ExpensesFrame.getTableView().getItems().addAll( ExpensesHibernation.findAllExpensessObservableList() );
	}

	private ExpensesFrame frame;

	private void showPopupForEditing() {

		new StageForAlerts();

		Expenses p = null;

		int row = ExpensesFrame.getTableView().getSelectionModel().getSelectedIndex();

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getExpenses();
		} else {
			p = ExpensesFrame.getTableView().getSelectionModel().getSelectedItem();
		}

		this.expenses = p;

		buttonSave.setText( "save changes" );

		fieldAmountSpent.setText( NumberFormatting.formatToEnglish( p.getAmountSpent().toString() ) );
		areaReason.setText( p.getReason() );
		stageForAllPopUps.showAndWait();

	}

	private Expenses expenses = null;

	private void saveChanges() {

		String date = null;
		String amountSpentStr = null;
		String reason = null;

		Expenses e = expenses;

		if ( datePicker.getValue() == null ) {
			date = CurrentTimestamp.getDateTimeEndAtMinutes();
		} else {
			LocalDate localDate = datePicker.getValue();
			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );
		}

		if ( fieldAmountSpent.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please specify the name amount spent" );
			return;
		} else {
			amountSpentStr = NumberFormatting.testNumberCorrectness( fieldAmountSpent.getText() );
		}

		if ( areaReason.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "please give the reason for spending this amount" );
			return;
		} else {
			reason = areaReason.getText().trim();
		}

		e.setDate( date );
		e.setMediumOfPaymentId( MediumOfPaymentComboBox.getInstance().getMediumOfPayment() );
		e.setReason( reason );
		e.setAmountSpent( new BigDecimal( amountSpentStr ) );

		ExpensesHibernation.updateExpenses( e, e.getId() );
		ExpensesFrame.getTableView().getItems().clear();
		ExpensesFrame.getTableView().getItems().addAll( ExpensesHibernation.findAllExpensessObservableListRefreshed() );
	}

	private void deleteMediumOfPayment() {
		new StageForAlerts();

		int row = ExpensesFrame.getTableView().getSelectionModel().getSelectedIndex();
		Expenses p = null;

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getExpenses();
		} else {
			p = ExpensesFrame.getTableView().getSelectionModel().getSelectedItem();
		}

		ExpensesHibernation.deleteExpenses( p.getId() );
		ExpensesFrame.getTableView().getItems().clear();
		ExpensesFrame.getTableView().getItems().addAll( ExpensesHibernation.findAllExpensessObservableListRefreshed() );
	}

	private void populateComboBoxMedium() {
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
	}

	private MediumOfPayment mediumOfPayment = null;

	private MediumOfPayment getMediumOfPayment() {
		comboBox.setOnAction( e -> {
			mediumOfPayment = comboBox.getSelectionModel().getSelectedItem();
		} );
		return mediumOfPayment;
	}

}
