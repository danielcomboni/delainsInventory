package com.delains.ui.sales;

import java.math.BigDecimal;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.pos.POS;
import com.delains.model.sales.SalesClearance;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.utilities.CurrentTimestamp;
import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SalesClearanceDialog {

	private StageForAllPopUps stageForAllPopUps;

	private GridPane gridPane;
	private BorderPane borderPane;

	private Label labeldate;
	private DatePicker datePicker;

	private Label labelAmountPaid;
	private TextField fieldAmountPaid;

	private Label labelBalance;
	private Label labelBalanceText;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private void buildComponents() {

		borderPane = new BorderPane();

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labeldate = new Label( "date:" );
		gridPane.add( labeldate, 0, 0 );
		datePicker = new DatePicker();
		gridPane.add( datePicker, 1, 0 );

		labelAmountPaid = new Label( "amount cleared:" );
		gridPane.add( labelAmountPaid, 0, 1 );
		fieldAmountPaid = new TextField();
		gridPane.add( fieldAmountPaid, 1, 1 );

		labelBalance = new Label( "balance:" );
		gridPane.add( labelBalance, 0, 2 );
		labelBalanceText = new Label();
		gridPane.add( labelBalanceText, 1, 2 );

		HBox hBoxButtons = new HBox( 10 );
		buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		buttonCancel = new JFXButton( "cancel" );
		hBoxButtons.getChildren().add( buttonCancel );
		gridPane.add( hBoxButtons, 1, 3 );

		VBox vBox = new VBox();
		vBox.getChildren().add( gridPane );

		new SalesClearanceTable();
		vBox.getChildren().add( SalesClearanceTable.getTableView() );

		borderPane.setCenter( vBox );

		stageForAllPopUps = new StageForAllPopUps( borderPane, "sales clearance" );

		buttonCancel.setOnAction( e -> {
			closeDialog();
		} );

		buttonSave.setOnAction( e -> {
			saveClearance();
		} );

	}

	private POS pos;

	public void showPopupToClearSales( POS p ) {

		BigDecimal amountCleared = getAmountCleared( p );

		BigDecimal totalCost = p.getTotalCost();

		BigDecimal balance = totalCost.subtract( amountCleared ).subtract( p.getDiscountAllowed() );

		labelBalanceText.setText( NumberFormatting.formatToEnglish( balance.toString() ) );
		fieldAmountPaid.setText( "0" );
		this.pos = p;
		stageForAllPopUps.showAndWait();

	}

	private BigDecimal getAmountCleared( POS p ) {
		BigDecimal balance = BigDecimal.ZERO;
		BigDecimal id = p.getId();

		ObservableList < SalesClearance > pcS = SalesClearanceHibernation.findAllSalesClearancesWithoutRefreshing();

		ObservableList < BigDecimal > clearances = FXCollections.observableArrayList();

		for ( int i = 0; i < pcS.size(); i++ ) {

			if ( pcS.get( i ).getPosId().getId().doubleValue() == id.doubleValue() ) {
				clearances.add( pcS.get( i ).getAmountCleared() );
			}

		}

		for ( int j = 0; j < clearances.size(); j++ ) {
			balance = balance.add( clearances.get( j ) );
		}

		balance = balance.add( p.getAmountPaid() );

		return balance;

	}

	private void closeDialog() {
		stageForAllPopUps.close();
	}

	private void saveClearance() {

		new StageForAlerts();

		String amountClearedStr = null;

		BigDecimal amountCleared = BigDecimal.ZERO;

		if ( fieldAmountPaid.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the amount cleared can not be empty " );
			return;
		} else {

			amountClearedStr = NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText().trim() );
			if ( NumberFormatting.isNumberCorrect() == false ) {
				StageForAlerts.inform( "alert", "check the number format " );
				return;
			}
			amountCleared = new BigDecimal( amountClearedStr );
		}

		String date = null;

		if ( datePicker.getValue() == null ) {

			date = CurrentTimestamp.getDateTimeEndAtSecond();

		}

		else {

			date = CurrentTimestamp.concatLocalDateToTimeWithSecondsAttached( datePicker.getValue() );

		}

		BigDecimal balPrev = new BigDecimal( NumberFormatting.testNumberCorrectness( labelBalanceText.getText() ) );
		BigDecimal balance = balPrev.subtract( amountCleared );

		if ( balance.doubleValue() < 0 ) {
			return;
		}

		if ( amountCleared.doubleValue() == 0 ) {
			return;
		}

		SalesClearance pc = new SalesClearance();
		pc.setDate( date );
		pc.setAmountCleared( amountCleared );
		pc.setBalanceToBeCleared( balance );
		pc.setPosId( this.pos );

		fieldAmountPaid.clear();

		System.out.println( "sc: " + pc );

		SalesClearanceHibernation.newSalesClearance( pc );
		Refresh.setRefreshingDeterminant( 1 );
		SalesClearanceManipulation.populateTable( SalesClearanceTable.getTableView() );

		labelBalanceText.setText( null );

		AuditHistoryHibernation.auditValues( " a credit sale cleared ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

	}

	public SalesClearanceDialog() {
		buildComponents();
	}

}
