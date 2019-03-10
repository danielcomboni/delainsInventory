package com.delains.ui.licence;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.delains.dao.licence.LicenceRandomNumbers;
import com.delains.daol.licence.ActivatedKeyHibernation;
import com.delains.daol.licence.LicenceHibernation;
import com.delains.daol.licence.RandomNumberHibernation;
import com.delains.model.licence.ActivatedKey;
import com.delains.model.licence.Licence;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.utilities.CurrentTimestamp;
import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LicenceUI extends BorderPane {

	private GridPane gridPane;
	private ComboBox < String > comboBoxPeriodicNumber;

	private DatePicker daterPickerLastDay;
	private TextField fieldKey;
	private Label labelRandomNumberGenerated;

	private JFXButton buttonSave;

	private void buildComponents() {

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 15, 5 ) );

		gridPane.add( new Label( "choose period:" ), 0, 0 );
		comboBoxPeriodicNumber = new ComboBox <>( populateComboBox() );
		gridPane.add( comboBoxPeriodicNumber, 1, 0 );

		gridPane.add( new Label( "access number:" ), 0, 1 );
		labelRandomNumberGenerated = new Label();
		gridPane.add( labelRandomNumberGenerated, 1, 1 );

		gridPane.add( new Label( "last date" ), 0, 2 );
		daterPickerLastDay = new DatePicker();
		daterPickerLastDay.setEditable( false );
		gridPane.add( daterPickerLastDay, 1, 2 );

		gridPane.add( new Label( "enter key:" ), 0, 3 );
		fieldKey = new TextField();
		ComponentWidth.setWidthOfTextField( fieldKey, 400 );
		fieldKey.setPromptText( "enter key here..." );
		gridPane.add( fieldKey, 1, 3 );

		HBox hBoxButtons = new HBox( 10 );
		buttonSave = new JFXButton( "confirm" );
		hBoxButtons.getChildren().add( buttonSave );
		gridPane.add( hBoxButtons, 1, 4 );

		this.setCenter( gridPane );
	}

	private static ObservableList < String > populateComboBox() {
		ObservableList < String > months = FXCollections.observableArrayList();

		for ( int i = 1; i < 10; i++ ) {

			if ( i != 1 ) {
				months.add( i + " - months" );
			} else {
				months.add( i + " - month" );
			}

		}

		return months;
	}

	private String periodChosen = null;

	private String chooseFromComboBox() {
		periodChosen = comboBoxPeriodicNumber.getSelectionModel().getSelectedItem();

		comboBoxPeriodicNumber.setOnAction( e -> {
			periodChosen = comboBoxPeriodicNumber.getSelectionModel().getSelectedItem();
		} );

		return comboBoxPeriodicNumber.getSelectionModel().getSelectedItem();
	}

	// private MediumOfPayment getMediumOfPayment() {
	// comboBoxMediumOfPayment.setOnAction( e -> {
	// mediumOfPayment =
	// comboBoxMediumOfPayment.getSelectionModel().getSelectedItem();
	// } );
	// return mediumOfPayment;
	// }

	private static void chooseRandomNumber() {

	}

	private char firstCharDigit;
	private BigDecimal theDigit = BigDecimal.ZERO;

	private BigDecimal numberOfDays = BigDecimal.ZERO;

	private LocalDate dateLast;

	// private RandomNumber randomNumber;

	public LicenceUI() {

		buildComponents();
		// chooseFromComboBox();

		System.out.println( "hhh: " + comboBoxPeriodicNumber.getSelectionModel().getSelectedItem() );

		comboBoxPeriodicNumber.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {

			if ( newVal != null ) {
				periodChosen = newVal;
				CharSequence n = periodChosen.subSequence( 0, periodChosen.length() );
				char firstChar = n.charAt( 0 );
				firstCharDigit = firstChar;
				theDigit = new BigDecimal( String.valueOf( firstCharDigit ) );
				labelRandomNumberGenerated.setText( RandomNumberHibernation
						.getARandomNumberBasingOnPeriodChosen( theDigit ).getRandNum().toString() );
			}

			else {
				periodChosen = oldVal;
				CharSequence n = periodChosen.subSequence( 0, periodChosen.length() );
				char firstChar = n.charAt( 0 );
				firstCharDigit = firstChar;
				theDigit = new BigDecimal( String.valueOf( firstCharDigit ) );
				labelRandomNumberGenerated.setText( RandomNumberHibernation
						.getARandomNumberBasingOnPeriodChosen( theDigit ).getRandNum().toString() );
			}

		} );

		daterPickerLastDay.setOnAction( e -> {
			dateLast = daterPickerLastDay.getValue();
			numberOfDays = LicenceHibernation.periodDeterminant( dateLast.toString() );
			System.out.println( "num of days: " + numberOfDays );

		} );

		buttonSave.setOnAction( e -> {
			Licence licence = new Licence();
			licence.setDaysLeft( numberOfDays );
			licence.setFromDate( CurrentTimestamp.getDateWithoutTimeAttached() );
			licence.setRandomNumberId( RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ) );
			licence.setToDate( dateLast.toString() );

			ActivatedKey key = new ActivatedKey();

			new StageForAlerts();
			if ( fieldKey.getText().trim().isEmpty() ) {
				StageForAlerts.inform( "alert", "the key must be specified" );
				return;
			}

			if ( !labelRandomNumberGenerated.getText().trim().isEmpty() ) {
				String number = labelRandomNumberGenerated.getText();

				if ( !fieldKey.getText().equals( LicenceRandomNumbers.generateLicenceKeyHashed( number ) ) ) {
					StageForAlerts.inform( "alert", "wrong key" );
					return;
				} else {

					String checkNumberOfDays = "please check your period range";

					char firstDigit = labelRandomNumberGenerated.getText().charAt( 0 );

					if ( firstDigit == '1' && numberOfDays.intValue() <= 30 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );

						System.out.println( "1==================================" );

						key.setKey(

								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '2' && numberOfDays.intValue() <= 60 ) {

						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );

						System.out.println( "2==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '3' && numberOfDays.intValue() <= 90 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "3==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '4' && numberOfDays.intValue() <= 120 ) {
						System.out.println( "4==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '5' && numberOfDays.intValue() <= 150 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "5==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '6' && numberOfDays.intValue() <= 180 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "6==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '7' && numberOfDays.intValue() <= 210 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "7==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );
						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );
					}

					else if ( firstDigit == '8' && numberOfDays.intValue() <= 240 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "8==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );

						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );

					}

					else if ( firstDigit == '9' && numberOfDays.intValue() <= 270 ) {
						LicenceHibernation.newLicence( licence );
						key.setLicenceId( LicenceHibernation.getLastLicence() );
						System.out.println( "9==================================" );

						key.setKey(
								LicenceRandomNumbers.generateLicenceKeyHashed( labelRandomNumberGenerated.getText() ) );
						ActivatedKeyHibernation.newActivatedKey( key );

						RandomNumberHibernation.deleteUsedRow(
								RandomNumberHibernation.getARandomNumberBasingOnPeriodChosen( theDigit ).getId() );

						clearFieldsAndRestartAppOrder( fieldKey, labelRandomNumberGenerated, buttonSave );

					} else {
						StageForAlerts.inform( "alert", checkNumberOfDays );
						return;
					}

				}

			}

		} );

	}

	private static void clearFieldsAndRestartAppOrder( TextField field, Label label, JFXButton button ) {
		new StageForAlerts();
		StageForAlerts.inform( "alert", "now restart the application please" );
		button.setDisable( true );
		field.clear();
		label.setText( null );
	}

}
