package com.delains.ui.item;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.items.Item;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.pricing.PricingFrame;
import com.delains.ui.sales.POSFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ItemsTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonAddNew;
	private JFXButton buttonSelectForEditing;
	private JFXButton buttonDelete;
	private JFXButton buttonPricing;

	public ItemsTabInAccordion() {
		// TODO Auto-generated constructor stub

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonAddNew = new JFXButton( "Create new item" );
		gridPane.add( buttonAddNew, 0, 0 );

		buttonSelectForEditing = new JFXButton( "Edit edit info" );
		gridPane.add( buttonSelectForEditing, 0, 1 );

		buttonDelete = new JFXButton( "Delete item" );
		gridPane.add( buttonDelete, 0, 2 );

		buttonPricing = new JFXButton( "Pricing" );
		gridPane.add( buttonPricing, 0, 3 );

		JFXButton buttonRefreshed = new JFXButton( "reload items table" );
		gridPane.add( buttonRefreshed, 0, 4 );
		buttonRefreshed.setOnAction( e -> refreshTable() );

		this.getChildren().add( gridPane );

		itemFrame = new ItemFrame();

		buttonAddNew.setOnAction( e -> {
			newItemPopUp();
		} );

		buttonPricing.setOnAction( e -> {

		} );

		buttonSelectForEditing.setOnAction( e -> {
			showEditingPopUp();
		} );

		buttonDelete.setOnAction( e -> {
			deleteAnItem();
			new PricingFrame().populateComboBox();
		} );

		ItemFrame.clickRow();

	}

	private void refreshTable() {
		ItemFrame.getTableView().getItems().clear();
		ItemFrame.getTableView().getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );
		new PricingFrame().populateComboBox();
	}

	private void deleteAnItem() {
		new StageForAlerts();
		Item item = null;

		if ( itemFrame.itemObtainedByClickingTable() != null ) {
			System.out.println( "it aint damn null: " + itemFrame.getItemPrev() );
		} else {
			System.out.println( "it is damn null: " + itemFrame.getItemPrev() );
		}

		int row = ItemFrame.getTableView().getSelectionModel().getSelectedIndex();

		if ( row < 0 && itemFrame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && itemFrame.itemObtainedByClickingTable() != null ) {
			item = itemFrame.getItemPrev();
		} else {

			item = ItemFrame.getTableView().getSelectionModel().getSelectedItem();

		}

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this item permanently?" );
		if ( StageForAlerts.isDecide() == true ) {

			ItemHibernation.deleteItem( item.getId() );

			ItemFrame.getTableView().getItems().clear();
			ItemFrame.getTableView().getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );

			// Refresh.setRefreshingDeterminant( 1 );
			// ItemManipulation.populateTableWithRefreshing();
			//
			// ItemFrame.setTableView( null );
			// ItemFrame.setTableView( ItemManipulation.getTableView() );

			AuditHistoryHibernation.auditValues( "an item deleted: " + item.getItemName(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseFrame.populateComboBoxItems();
			new POSFrame().populateComboBoxItems();
		}

	}

	private void showEditingPopUp() {

		new StageForAlerts();

		Item item = null;

		if ( itemFrame.itemObtainedByClickingTable() != null ) {
			System.out.println( "it aint damn null: " + itemFrame.getItemPrev() );
		} else {
			System.out.println( "it is damn null: " + itemFrame.getItemPrev() );
		}

		int row = ItemFrame.getTableView().getSelectionModel().getSelectedIndex();

		if ( row < 0 && itemFrame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		} else if ( row < 0 && itemFrame.itemObtainedByClickingTable() != null ) {
			item = itemFrame.getItemPrev();
		} else {

			item = ItemFrame.getTableView().getSelectionModel().getSelectedItem();

		}
		//
		// if ( row < 0 && itemFrame.itemObtainedByClickingTable() != null ) {
		//
		// }

		itemFrame.showUpdatePopUp( item );

	}

	private ItemFrame itemFrame;

	private void newItemPopUp() {

		itemFrame.newItem();

	}

	public JFXButton getButtonAddNew() {
		return buttonAddNew;
	}

	public void setButtonAddNew( JFXButton buttonAddNew ) {
		this.buttonAddNew = buttonAddNew;
	}

	public JFXButton getButtonSelectForEditing() {
		return buttonSelectForEditing;
	}

	public void setButtonSelectForEditing( JFXButton buttonSelectForEditing ) {
		this.buttonSelectForEditing = buttonSelectForEditing;
	}

	public JFXButton getButtonDelete() {
		return buttonDelete;
	}

	public void setButtonDelete( JFXButton buttonDelete ) {
		this.buttonDelete = buttonDelete;
	}

	public JFXButton getButtonPricing() {
		return buttonPricing;
	}

	public void setButtonPricing( JFXButton buttonPricing ) {
		this.buttonPricing = buttonPricing;
	}

}
