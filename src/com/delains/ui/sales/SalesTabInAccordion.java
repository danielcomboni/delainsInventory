package com.delains.ui.sales;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.pos.POSHibernation;
import com.delains.dao.pos.SalesReturnHibernation;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.pos.POS;
import com.delains.model.sales.SalesReturn;
import com.delains.model.stock.Stock;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.Refresh;
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

public class SalesTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonPOS;
	private JFXButton buttonCashSalesRecords;
	private JFXButton buttonCreditSalesRecords;
	private JFXButton buttonAllSalesRecords;
	private JFXButton buttonAllSalesReturn;
	private JFXButton buttonClearCreditSale;
	private JFXButton buttonReceiptHeader;
	private JFXButton buttonRefreshSalesReturn;

	public SalesTabInAccordion() {

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonPOS = new JFXButton( "POS" );
		gridPane.add( buttonPOS, 0, 0 );

		buttonCashSalesRecords = new JFXButton( "cash sales records" );
		// gridPane.add( buttonCashSalesRecords, 0, 1 );

		buttonCreditSalesRecords = new JFXButton( "credit sales records" );
		// gridPane.add( buttonCreditSalesRecords, 0, 2 );

		buttonAllSalesRecords = new JFXButton( "all sales records" );
		gridPane.add( buttonAllSalesRecords, 0, 1 );

		buttonAllSalesReturn = new JFXButton( "sale return" );
		gridPane.add( buttonAllSalesReturn, 0, 2 );

		buttonClearCreditSale = new JFXButton( "clear credit sale" );
		gridPane.add( buttonClearCreditSale, 0, 3 );

		buttonReceiptHeader = new JFXButton( "receipt header" );
		gridPane.add( buttonReceiptHeader, 0, 4 );

		buttonRefreshSalesReturn = new JFXButton( "reload(view) sales returns" );
		// gridPane.add( buttonRefreshSalesReturn, 0, 5 );

		this.getChildren().add( gridPane );

		borderPane = new BorderPane();
		stageForAllPopUps = new StageForAllPopUps( borderPane, "new inward return" );

		buttonSave = new JFXButton( "save" );

		buttonCancel = new JFXButton( "cancel" );

		buttonAllSalesReturn.setOnAction( e -> {
			showSalesReturnPopUp();
		} );

		buttonSave.setOnAction( e -> {

			// System.out.println( ",,,,,,,,,,,,,,,," );

			saveReturned();
		} );

		buttonCancel.setOnAction( e -> {
			fieldQtyDiscarded.clear();
			fieldQtyReStocked.clear();
			fieldQtyReturned.clear();
			areaReason.clear();
			labelQtySoldText.setText( null );
			stageForAllPopUps.close();
		} );

		buildReturnPopUp();

		buttonReceiptHeader.setOnAction(
				e -> new StageForAllPopUps( new ReceiptHeaderDialog(), "setting receipt header" ).showAndWait() );

	}

	// private BigDecimal id;
	// private String date;
	// private Item itemId;
	// private BigDecimal quantityReturned;
	// private BigDecimal quantityReStocked;
	// private BigDecimal quantityDiscarded;
	// private Customer customerId;
	// private POS posId;
	// private String reason;

	private BorderPane borderPane;
	private StageForAllPopUps stageForAllPopUps;

	private Label labelItem;
	private Label labelItemText;

	private Label labelQtySold;
	private Label labelQtySoldText;

	private Label labelQtyReturned;
	private TextField fieldQtyReturned;

	private Label labelQtyReStocked;
	private TextField fieldQtyReStocked;

	private Label labelQtyDiscarded;
	private TextField fieldQtyDiscarded;

	private Label labelReason;
	private TextArea areaReason;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private void buildReturnPopUp() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labelItem = new Label( "Item:" );
		gridPane.add( labelItem, 0, 0 );
		labelItemText = new Label();
		gridPane.add( labelItemText, 1, 0 );

		labelQtySold = new Label( "quantity sold:" );
		gridPane.add( labelQtySold, 0, 1 );
		labelQtySoldText = new Label();
		gridPane.add( labelQtySoldText, 1, 1 );

		labelQtyReturned = new Label( "quantity returned:" );
		gridPane.add( labelQtyReturned, 0, 2 );
		fieldQtyReturned = new TextField();
		gridPane.add( fieldQtyReturned, 1, 2 );

		labelQtyReStocked = new Label( "quantity back to stock:" );
		gridPane.add( labelQtyReStocked, 0, 3 );
		fieldQtyReStocked = new TextField();
		gridPane.add( fieldQtyReStocked, 1, 3 );

		labelQtyDiscarded = new Label( "quantity discarded:" );
		gridPane.add( labelQtyDiscarded, 0, 4 );
		fieldQtyDiscarded = new TextField();
		gridPane.add( fieldQtyDiscarded, 1, 4 );

		labelReason = new Label( "reason (explanation:" );
		gridPane.add( labelReason, 0, 5 );
		areaReason = new TextArea();
		areaReason.setWrapText( true );
		areaReason.setPrefWidth( 400 );
		areaReason.setPrefHeight( 100 );
		gridPane.add( areaReason, 1, 5 );

		HBox hBox = new HBox( 10 );
		gridPane.add( hBox, 1, 6 );

		// buttonSave = new JFXButton( "save" );
		hBox.getChildren().add( buttonSave );

		// buttonCancel = new JFXButton( "cancel" );
		hBox.getChildren().add( buttonCancel );

		VBox vBox = new VBox();

		vBox.getChildren().add( gridPane );

		new SalesReturnTable();
		vBox.getChildren().add( SalesReturnTable.getTableView() );

		borderPane.setCenter( vBox );

		salesClearanceDialog = new SalesClearanceDialog();

		buttonClearCreditSale.setOnAction( e -> {
			showClearanceDialog();
		} );

	}

	private SalesClearanceDialog salesClearanceDialog;

	private void showClearanceDialog() {

		new StageForAlerts();

		int row = AllSalesFrame.getTableView().getSelectionModel().getSelectedIndex();

		System.out.println( "sales row selected: " + row );

		POS p = AllSalesFrame.getTableView().getSelectionModel().getSelectedItem();

		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to clear" );
			return;

		}

		if ( p.getBalanceToBePaidByCustomer().doubleValue() <= 0 ) {
			StageForAlerts.inform( "alert", "please choose a row to that has balance greater than 0" );
			return;
		}

		salesClearanceDialog.showPopupToClearSales( p );

	}

	private POS pos = new POS();

	private void showSalesReturnPopUp() {

		new StageForAlerts();

		int row = AllSalesFrame.getTableView().getSelectionModel().getSelectedIndex();

		System.out.println( "purch row selected: " + row );

		POS p = AllSalesFrame.getTableView().getSelectionModel().getSelectedItem();

		pos = p;

		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to record an inward return" );
			return;

		}

		if ( p.getQuantity().doubleValue() == 0 ) {
			StageForAlerts.inform( "alert", "please a row of an item sold" );
			return;
		}

		labelItemText.setText( p.getItemId().getItemName() );
		labelQtySoldText.setText( p.getQuantity().toString() );

		new SalesReturnTable();

		stageForAllPopUps.showAndWait();

	}

	private BigDecimal qtyReturned = BigDecimal.ZERO;
	private BigDecimal qtyBackToStock = BigDecimal.ZERO;
	private BigDecimal qtyDiscarded = BigDecimal.ZERO;

	private void saveReturned() {

		new StageForAlerts();

		String qtyrReturnedStr = null;
		String qtyReStockedStr = null;
		String qtyDiscardedStr = null;

		if ( fieldQtyReturned.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "quantity returned must be specified" );
			return;
		}

		else {

			qtyrReturnedStr = NumberFormatting.testNumberCorrectness( fieldQtyReturned.getText().trim() );

			if ( NumberFormatting.isNumberCorrect() == false ) {
				StageForAlerts.inform( "alert", "check your number formaty please" );
				return;
			} else {
				qtyReturned = new BigDecimal( qtyrReturnedStr );
			}
		}

		if ( qtyReturned
				.doubleValue() > new BigDecimal( NumberFormatting.testNumberCorrectness( labelQtySold.toString() ) )
						.doubleValue() ) {
			StageForAlerts.inform( "alert", "the returned quantity can not be more than the sold quantity" );
			return;
		}

		qtyReStockedStr = NumberFormatting.testNumberCorrectness( fieldQtyReStocked.getText() );
		qtyBackToStock = new BigDecimal( qtyReStockedStr );

		qtyDiscardedStr = NumberFormatting.testNumberCorrectness( fieldQtyDiscarded.getText().trim() );
		qtyDiscarded = new BigDecimal( qtyDiscardedStr );

		if ( ( qtyDiscarded.add( qtyBackToStock ) ).doubleValue() > qtyReturned.doubleValue() ) {

			StageForAlerts.inform( "alert", "check your summation please" );
			return;

		}

		if ( ( qtyDiscarded.add( qtyBackToStock ) )
				.doubleValue() > new BigDecimal( NumberFormatting.testNumberCorrectness( labelQtySold.toString() ) )
						.doubleValue() ) {
			StageForAlerts.inform( "alert", "check your summation please" );
			return;
		}

		String reason = areaReason.getText();

		SalesReturn sr = new SalesReturn();

		Timestamp timestamp = Timestamp.valueOf( LocalDateTime.now() );

		sr.setDate( timestamp.toString() );
		sr.setCustomerId( pos.getCustomerId() );
		sr.setPosId( pos );
		sr.setQuantityDiscarded( qtyDiscarded );
		sr.setQuantityReStocked( qtyBackToStock );
		sr.setQuantityReturned( qtyReturned );
		sr.setReason( reason );
		sr.setItemId( pos.getItemId() );

		SalesReturnHibernation.newSalesReturn( sr );
		Refresh.setRefreshingDeterminant( 1 );
		SalesReturnManipulation.populateTable( SalesReturnTable.getTableView() );

		AuditHistoryHibernation.auditValues( " a sale returned ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

		Stock stockToChange = StockHibernation.mapOfStockToThierItemIDs().get( pos.getItemId().getId() );
		BigDecimal oldQty = stockToChange.getItemQuantity();
		BigDecimal sumOfQty = oldQty.add( qtyBackToStock );
		if ( qtyBackToStock.doubleValue() > 0 ) {
			stockToChange.setItemQuantity( sumOfQty );
			StockHibernation.updateStock( stockToChange, stockToChange.getId() );
		}

		fieldQtyDiscarded.clear();
		fieldQtyReStocked.clear();
		fieldQtyReturned.clear();
		areaReason.clear();
		labelQtySoldText.setText( null );

	}

	public JFXButton getButtonPOS() {
		return buttonPOS;
	}

	public void setButtonPOS( JFXButton buttonPOS ) {
		this.buttonPOS = buttonPOS;
	}

	public JFXButton getButtonCashSalesRecords() {
		return buttonCashSalesRecords;
	}

	public void setButtonCashSalesRecords( JFXButton buttonCashSalesRecords ) {
		this.buttonCashSalesRecords = buttonCashSalesRecords;
	}

	public JFXButton getButtonCreditSalesRecords() {
		return buttonCreditSalesRecords;
	}

	public void setButtonCreditSalesRecords( JFXButton buttonCreditSalesRecords ) {
		this.buttonCreditSalesRecords = buttonCreditSalesRecords;
	}

	public JFXButton getButtonAllSalesRecords() {
		return buttonAllSalesRecords;
	}

	public void setButtonAllSalesRecords( JFXButton buttonAllSalesRecords ) {
		this.buttonAllSalesRecords = buttonAllSalesRecords;
	}

}
