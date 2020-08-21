package com.delains.ui.purchases;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.purchases.PurchaseReturnHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.history.AuditHistory;
import com.delains.model.purchases.Purchase;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.model.stock.Stock;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import static com.delains.ui.history.AuditHistoryData.*;

public class PurchasesTabInAccordion extends BorderPane {

	public PurchasesTabInAccordion() {

		this.setId( "main_borderpane" );

		getStylesheets().add( PurchasesTabInAccordion.class.getResource( "purchase.css" ).toExternalForm() );

		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonAddNew = new JFXButton("Add new purchase");
		gridPane.add(buttonAddNew, 0, 0 );

		JFXButton buttonSelectForEditing = new JFXButton("Edit purchase info");
		// gridPane.add(buttonSelectForEditing, 0, 1 );

		JFXButton buttonDelete = new JFXButton("purchase return");
		gridPane.add(buttonDelete, 0, 2 );

		JFXButton buttonListOfReturns = new JFXButton("view all purchases returns");
		gridPane.add(buttonListOfReturns, 0, 3 );

		JFXButton buttonClearance = new JFXButton("clear credit purchase");
		gridPane.add(buttonClearance, 0, 4 );

		JFXButton buttonRefresh = new JFXButton("reload table");
		gridPane.add(buttonRefresh, 0, 5 );

		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10));

		vBox.getChildren().add(gridPane);


		setCenter(vBox);

		frame = new PurchaseFrame();

		purchaseReturnDialog = new PurchaseReturnDialog();

		// stageForAllPopUps = new StageForAllPopUps( purchaseReturnDialog, "new
		// purchase return" );

		buttonAddNew.setOnAction( e -> frame.newPurchase());

		buttonSelectForEditing.setOnAction(e -> chooseForEditing());

		buttonDelete.setOnAction(e -> showPurchaseReturnDialog());

		cancel();
		saveAndClose();

		purchasesClearanceDialog = new PurchasesClearanceDialog();

		buttonClearance.setOnAction(e -> showClearanceDialog());

//		buttonRefresh.setOnAction(e -> refreshTable() );

		buttonListOfReturns.setOnAction(e -> purchaseReturnList() );

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

	private void chooseForEditing() {

		new StageForAlerts();

		int row = PurchaseFrame.getTableView().getSelectionModel().getSelectedIndex();
		Purchase p;

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

//	private void refreshTable() {
//
//
//		Service<Void> service;
//		service = new Service<Void>() {
//			@Override
//			protected Task<Void> createTask() {
//				return new Task<Void>() {
//					@Override
//					protected Void call() {
//						System.out.println("reloading..........");
//						PurchaseFrame.getTableView().getItems().clear();
//						PurchaseFrame.getTableView().getItems()
//								.addAll( PurchasesHibernation.findAllPurchasesObservableListRefreshed() );
//						return null;
//					}
//				};
//			}
//		};
//
//		service.start();
//
//	}

	private void savePurchasereturn() {

		new StageForAlerts();

		BigDecimal qtyToBereturned;
		if ( purchaseReturnDialog.getFieldQuantity().getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "please specify the quantity to be returned" );
			return;

		} else {

			String qtyString = purchaseReturnDialog.getFieldQuantity().getText();
			String qtyTest = NumberFormatting.testNumberCorrectness( qtyString );

			if (!NumberFormatting.isNumberCorrect()) {
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
		purchaseReturn.setQuantity(qtyToBereturned);
		purchaseReturn.setSupplierId( purchaseForReturn.getSupplierId() );
		purchaseReturn.setReason( purchaseReturnDialog.getAreaReason().getText() );

		BigDecimal qtyToRemainInPurchaseTable = purchaseForReturn.getQuantityPurchased().subtract(qtyToBereturned);

		purchaseForReturn.setQuantityPurchased( qtyToRemainInPurchaseTable );

		Purchase p = purchaseForReturn;

		Stock stock = StockHibernation.mapOfStockToThierItemIDs().get( p.getItemId().getId() );

		BigDecimal stockQtyPrevious = stock.getItemQuantity();
		BigDecimal stockQtyNew = stockQtyPrevious.subtract(qtyToBereturned);
		stock.setItemQuantity( stockQtyNew );

		purchaseReturnDialog.getFieldQuantity().clear();

		PurchaseReturnHibernation.newPurchaseReturn( purchaseReturn );

		Refresh.setRefreshingDeterminant( 1 );
		PurchaseReturnManipulation.populateTable( PurchaseReturnDialog.getTableView() );
		PurchasesHibernation.updatePurchase( p, p.getId() );
		StockHibernation.updateStock( stock, stock.getId() );
		Refresh.setRefreshingDeterminant( 0 );

		AuditHistory auditHistory = AuditHistoryHibernation.auditValues( " a purchase returned", UserLoggedIn.getUserLoggedIn() );
		theData.add(auditHistory);
	}

	private void saveAndClose() {
		purchaseReturnDialog.getButtonSave().setOnAction( e -> savePurchasereturn());
	}
}
