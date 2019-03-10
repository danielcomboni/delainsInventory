package com.delains.ui.purchases;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.purchases.PurchaseReturnHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.purchases.Purchase;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.model.stock.Stock;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PurchasesTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonAddNew;
	private JFXButton buttonSelectForEditing;
	private JFXButton buttonDelete;
	private JFXButton buttonClearance;
	private JFXButton buttonRefresh;
	private JFXButton buttonListOfReturns;

	public PurchasesTabInAccordion() {

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonAddNew = new JFXButton( "Add new purchase" );
		gridPane.add( buttonAddNew, 0, 0 );

		buttonSelectForEditing = new JFXButton( "Edit purchase info" );
		gridPane.add( buttonSelectForEditing, 0, 1 );

		buttonDelete = new JFXButton( "purchase return" );
		gridPane.add( buttonDelete, 0, 2 );

		buttonListOfReturns = new JFXButton( "view all purchases returns" );
		gridPane.add( buttonListOfReturns, 0, 3 );

		buttonClearance = new JFXButton( "clear credit purchase" );
		gridPane.add( buttonClearance, 0, 4 );

		buttonRefresh = new JFXButton( "reload table" );
		gridPane.add( buttonRefresh, 0, 5 );
		this.getChildren().add( gridPane );

		frame = new PurchaseFrame();

		purchaseReturnDialog = new PurchaseReturnDialog();

		// stageForAllPopUps = new StageForAllPopUps( purchaseReturnDialog, "new
		// purchase return" );

		buttonAddNew.setOnAction( e -> {
			newPurchasePopUp();
		} );

		buttonSelectForEditing.setOnAction( e -> {
			chooseForEditing();
		} );

		buttonDelete.setOnAction( e -> {
			showPurchaseReturnDialog();
		} );

		cancel();
		saveAndClose();

		purchasesClearanceDialog = new PurchasesClearanceDialog();

		buttonClearance.setOnAction( e -> {
			showClearanceDialog();
		} );

		buttonRefresh.setOnAction( e -> refreshTable() );

		buttonListOfReturns.setOnAction( e -> purchaseReturnList() );

	}

	private PurchasesClearanceDialog purchasesClearanceDialog;

	private void purchaseReturnList() {
		StageForAllPopUps stageForAllPopUps = new StageForAllPopUps( new PurchasesReturnListDialog(),
				"purchase returns list" );
		PurchaseReturnManipulation.populateTable( PurchasesReturnListDialog.getTableView() );

		stageForAllPopUps.setResizable( true );
		stageForAllPopUps.showAndWait();
	}

	private void showClearanceDialog() {

		new StageForAlerts();

		int row = PurchaseFrame.getTableView().getSelectionModel().getSelectedIndex();

		System.out.println( "purch row selected: " + row );

		Purchase p = PurchaseFrame.getTableView().getSelectionModel().getSelectedItem();

		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to clear" );
			return;

		}

		if ( p.getBalanceToBeCleared().doubleValue() <= 0 ) {
			StageForAlerts.inform( "alert", "please choose a row to that has balance greater than 0" );
			return;
		}

		purchasesClearanceDialog.showPopupToClearePurchase( p );
	}

	private PurchaseFrame frame;

	private void newPurchasePopUp() {

		frame.newPurchase();
		
	}

	private void chooseForEditing() {

		new StageForAlerts();

		int row = PurchaseFrame.getTableView().getSelectionModel().getSelectedIndex();
		Purchase p = null;

		if ( row < 0 && frame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && frame.itemObtainedByClickingTable() != null ) {
			p = frame.getPurchasePrevoius();
		} else {

			p = PurchaseFrame.getTableView().getSelectionModel().getSelectedItem();

		}

		frame.populateForUpdate( p );

	}

	private void showPurchaseReturnDialog() {

		new StageForAlerts();

		int row = PurchaseFrame.getTableView().getSelectionModel().getSelectedIndex();

		if ( row < 0 ) {

			StageForAlerts.inform( "alert", "please choose a row to returned" );
			return;

		}

		Purchase p = PurchaseFrame.getTableView().getSelectionModel().getSelectedItem();

		purchaseForReturn = p;

		purchaseReturnDialog.getFieldQuantity().setText( p.getQuantityPurchased().toString() );

		// stageForAllPopUps = new StageForAllPopUps( purchaseReturnDialog, "new
		// purchase return" );

		// new StageForAllPopUps( purchaseReturnDialog, "new purchase return"
		// ).setResizable( true ).showAndWait();

		StageForAllPopUps stage = new StageForAllPopUps( purchaseReturnDialog, "new purchase return" );
		stage.setResizable( true );
		stage.showAndWait();

		// stageForAllPopUps.showAndWait();

	}

	private PurchaseReturnDialog purchaseReturnDialog;

	private void cancel() {
		purchaseReturnDialog.getButtonCancel().setOnAction( e -> {

			purchaseReturnDialog.getDatePicker().setValue( null );

			purchaseReturnDialog.getFieldQuantity().clear();
			StageForAllPopUps.closeStage();

		} );
	}

	private Purchase purchaseForReturn = null;
	private BigDecimal qtyToBereturned = BigDecimal.ZERO;

	private void refreshTable() {

		PurchaseFrame.getTableView().getItems().clear();
		PurchaseFrame.getTableView().getItems()
				.addAll( PurchasesHibernation.findAllPurchasesObservableListRefreshed() );

	}

	private void savePurchasereturn() {

		new StageForAlerts();

		if ( purchaseReturnDialog.getFieldQuantity().getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "please specify the quantity to be returned" );
			return;

		} else {

			String qtyString = purchaseReturnDialog.getFieldQuantity().getText();
			String qtyTest = NumberFormatting.testNumberCorrectness( qtyString );

			if ( NumberFormatting.isNumberCorrect() == false ) {
				StageForAlerts.inform( "alert", "wrong number format for quantity" );
				return;
			} else {
				qtyToBereturned = new BigDecimal( qtyTest );
			}

		}

		PurchaseReturn purchaseReturn = new PurchaseReturn();

		if ( purchaseReturnDialog.getDatePicker().getValue() == null ) {
			LocalDate date = LocalDate.now();
			purchaseReturn.setDate( date.toString() );
		} else {

			purchaseReturn.setDate( purchaseReturnDialog.getDatePicker().getValue().toString() );

		}

		purchaseReturn.setItemId( purchaseForReturn.getItemId() );
		purchaseReturn.setPurchaseId( purchaseForReturn );
		purchaseReturn.setQuantity( qtyToBereturned );
		purchaseReturn.setSupplierId( purchaseForReturn.getSupplierId() );
		purchaseReturn.setReason( purchaseReturnDialog.getAreaReason().getText() );

		BigDecimal qtyToRemainInPurchaseTable = purchaseForReturn.getQuantityPurchased().subtract( qtyToBereturned );

		purchaseForReturn.setQuantityPurchased( qtyToRemainInPurchaseTable );

		Purchase p = new Purchase();
		p = purchaseForReturn;

		Stock stock = StockHibernation.mapOfStockToThierItemIDs().get( p.getItemId().getId() );

		BigDecimal stockQtyPrevious = stock.getItemQuantity();
		BigDecimal stockQtyNew = stockQtyPrevious.subtract( qtyToBereturned );
		stock.setItemQuantity( stockQtyNew );

		purchaseReturnDialog.getFieldQuantity().clear();

		PurchaseReturnHibernation.newPurchaseReturn( purchaseReturn );

		Refresh.setRefreshingDeterminant( 1 );
		PurchaseReturnManipulation.populateTable( PurchaseReturnDialog.getTableView() );
		PurchasesHibernation.updatePurchase( p, p.getId() );
		PurchaseManipulation.populateTable( PurchaseFrame.getTableView() );
		StockHibernation.updateStock( stock, stock.getId() );
		Refresh.setRefreshingDeterminant( 0 );

		AuditHistoryHibernation.auditValues( " a purchase returned", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

	}

	private void saveAndClose() {
		purchaseReturnDialog.getButtonSave().setOnAction( e -> {
			savePurchasereturn();
		} );
	}
}
