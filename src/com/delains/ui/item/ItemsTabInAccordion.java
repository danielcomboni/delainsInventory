package com.delains.ui.item;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.history.AuditHistory;
import com.delains.model.items.Item;
import com.delains.ui.GeneralDialog;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.item.categories.CategoriesFrame;
import com.delains.ui.item.categories.ItemAndCategoryFrame;
import com.jfoenix.controls.JFXButton;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class ItemsTabInAccordion extends VBox {

	private JFXButton buttonPricing;

	public ItemsTabInAccordion() {

		GridPane gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		JFXButton buttonAddNew = new JFXButton("Create new item");
		gridPane.add(buttonAddNew, 0, 0 );

		JFXButton buttonSelectForEditing = new JFXButton("Edit edit info");
		gridPane.add(buttonSelectForEditing, 0, 1 );

		JFXButton buttonDelete = new JFXButton("Delete item");
		gridPane.add(buttonDelete, 0, 2 );

		buttonPricing = new JFXButton( "Pricing" );
		gridPane.add( buttonPricing, 0, 3 );

		JFXButton buttonRefreshed = new JFXButton( "reload items table" );
		gridPane.add( buttonRefreshed, 0, 4 );
		buttonRefreshed.setOnAction( e -> refreshTable() );

		JFXButton buttonNewCategories = new JFXButton("item categories");

		gridPane.add(buttonNewCategories, 0, 5);
		buttonNewCategories.setOnAction(e -> GeneralDialog.showDialog("Create and View categories","Item Categories", new CategoriesFrame()));

		JFXButton buttonAssignCategories = new JFXButton("assign categories to items");

		gridPane.add( buttonAssignCategories, 0, 6);
		buttonAssignCategories.setOnAction(e -> GeneralDialog.showDialog("Assign categories", "assign categories to items", new ItemAndCategoryFrame()));

		this.getChildren().add(gridPane);

		itemFrame = new ItemFrame();

		buttonAddNew.setOnAction(e -> newItemPopUp());

		buttonSelectForEditing.setOnAction(e -> showEditingPopUp());

		buttonDelete.setOnAction(e -> deleteAnItem());

		ItemFrame.clickRow();

	}

	private void refreshTable() {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						ItemData.data.clear();
						ItemData.data.addAll(ItemHibernation.findAllItemsObservableListRefreshed());
						return null;
					}
				};
			}
		};
		service.start();

	}

	private void deleteAnItem() {
		new StageForAlerts();
		Item item;

		int row = ItemFrame.tableView.getSelectionModel().getSelectedIndex();

		if (row >= 0 || itemFrame.itemObtainedByClickingTable() != null) {
			if ( row < 0 && itemFrame.itemObtainedByClickingTable() != null ) {
				item = itemFrame.getItemPrev();
			} else {

				item = ItemFrame.tableView.getSelectionModel().getSelectedItem();

			}
		} else {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		}

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this item permanently?" );

		if (StageForAlerts.isDecide()) {

			ItemHibernation.deleteItem( item.getId() );
			ItemData.data.remove(item);

			AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "an item deleted: " + item.getItemName(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryData.theData.add(auditHistory);
			Refresh.setRefreshingDeterminant( 1 );
		}

	}

	private void showEditingPopUp() {

		new StageForAlerts();

		Item item;

		int row = ItemFrame.tableView.getSelectionModel().getSelectedIndex();

		if ( row < 0 && itemFrame.itemObtainedByClickingTable() == null ) {
			StageForAlerts.inform( "alert", "please choose a row for this action" );
			return;
		}

		else if ( row < 0 && itemFrame.itemObtainedByClickingTable() != null ) {
			item = itemFrame.getItemPrev();
		}

		else {

			item = ItemFrame.tableView.getSelectionModel().getSelectedItem();

		}

		itemFrame.showUpdatePopUp( item );

	}

	private ItemFrame itemFrame;

	private void newItemPopUp() {

		itemFrame.newItem();

	}

	public JFXButton getButtonPricing() {
		return buttonPricing;
	}

}
