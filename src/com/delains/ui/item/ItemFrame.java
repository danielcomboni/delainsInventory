package com.delains.ui.item;

import java.math.BigDecimal;
import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.GenerateBarcode;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.history.AuditHistory;
import com.delains.model.items.Item;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.pricing.PricingFrame;
import com.delains.ui.test.AutoCompleteFieldObtainResult;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ItemFrame extends BorderPane {

	public ItemFrame() {

		setId( "main_borderpane" );

		getStylesheets().add( ItemFrame.class.getResource( "itemFrame.css" ).toExternalForm() );

		buildComponents();

		tableView = new TableView<>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		TableColumn<Item,String> columnItemName = new TableColumn<>("item");
		tableView.getColumns().add(columnItemName);
		columnItemName.setCellValueFactory(param -> param.getValue().itemNameProperty());

		TableColumn<Item,String> columnUnitOfMeasurement = new TableColumn<>("unit\nof\nmeasurement");
		columnUnitOfMeasurement.setCellValueFactory(param -> param.getValue().unitOfMeasurementProperty());
		tableView.getColumns().add(columnUnitOfMeasurement);

		TableColumn<Item,String> columnPackage = new TableColumn<>("package");
		columnPackage.setCellValueFactory(param -> param.getValue().packageNameProperty());
		tableView.getColumns().add(columnPackage);

		TableColumn<Item,String> columnPackageVolume = new TableColumn<>("package\nvolume");
		columnPackageVolume.setCellValueFactory(param -> {

			String volStr = param.getValue().packageVolumeProperty().getValue().toString();

			return new SimpleStringProperty(NumberFormatting.formatToEnglish(volStr));

		});

		tableView.getColumns().add(columnPackageVolume);

		tableView.setItems(ItemData.data);

		setCenter( tableView );
		itemObtainedByClickingTable();

	}

	public static TableView < Item > tableView ;

	private TextField fieldItemName;

	private TextArea areaItemDescription;

	private TextField fieldUnitOfMeasurement;
	private TextField fieldPackage;

	private TextField fieldPackageUnit;

	private TextField fieldBarcode;

	private GridPane gridPane;
	private JFXButton buttonSave;
	private StageForAllPopUps stageForAllPopUps;

	private void buildComponents() {

		HBox hBox = new HBox();

		gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelItemName = new Label("item name:");
		addToGrid(labelItemName, 0, 0 );

		fieldItemName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldItemName, 400 );
		addToGrid( fieldItemName, 1, 0 );

		Label labelItemDescription = new Label("item description:");
		addToGrid(labelItemDescription, 0, 1 );

		areaItemDescription = new TextArea();
		areaItemDescription.setPrefWidth( 20 );
		areaItemDescription.setPrefHeight( 70 );
		areaItemDescription.setWrapText( true );
		addToGrid( areaItemDescription, 1, 1 );

		Label labelUnitOfMeasurement = new Label("unit of measurement:");
		addToGrid(labelUnitOfMeasurement, 0, 2 );

		HBox boxPackage = new HBox( 10 );
		Label labelPackage = new Label("package:");
		boxPackage.getChildren().add(labelPackage);

		fieldPackage = new TextField();
		boxPackage.getChildren().add( fieldPackage );

		Label labelPackageUnit = new Label("package vol:");
		boxPackage.getChildren().add(labelPackageUnit);

		fieldPackageUnit = new TextField();
		boxPackage.getChildren().add( fieldPackageUnit );

		fieldUnitOfMeasurement = new TextField();
		addToGrid( fieldUnitOfMeasurement, 1, 2 );

		addToGrid( boxPackage, 1, 3 );

		Label labelBarCode = new Label("barcode:");
		addToGrid(labelBarCode, 0, 4 );

		fieldBarcode = new TextField();
		addToGrid( fieldBarcode, 1, 4 );

		HBox hBoxButtons = new HBox(10);
		buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		JFXButton buttonGenerateBarcode = new JFXButton("generate barcode");
		hBoxButtons.getChildren().add(buttonGenerateBarcode);

		JFXButton buttonCancel = new JFXButton("cancel");
		hBoxButtons.getChildren().add(buttonCancel);

		addToGrid(hBoxButtons, 1, 5 );

		hBox.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps(hBox, "add new item" );

		buttonSave.setOnAction( e -> {

			if ( stageForAllPopUps.getTitle().equals( "edit item" ) ) {
				saveChanges();
				stageForAllPopUps.setTitle( "add new item" );
				buttonSave.setText( "save" );
				stageForAllPopUps.close();

//				refreshPricing();

			} else {

				createNewItem();

//				refreshPricing();

			}

		} );

		buttonCancel.setOnAction(e -> {

			stageForAllPopUps.close();
			Refresh.setRefreshingDeterminant( 1 );

			// ItemManipulation.populateTableWithRefreshing();

		} );

		buttonGenerateBarcode.setOnAction(e -> generateBarcode() );

	}

	private void generateBarcode() {
		fieldBarcode.setText( GenerateBarcode.getTheGeneratedbarcode() );
	}

	private void addToGrid( Node node, int col, int row ) {
		gridPane.add( node, col, row );
	}

	private void createNewItem() {

		String itemName;
		String itemDescription;
		String unitOfMeasurement;
		String barcode;

		new StageForAlerts();

		if ( fieldItemName.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the item name can not be empty please" );
			return;
		} else {
			itemName = fieldItemName.getText();
		}

		itemDescription = areaItemDescription.getText();

		if ( fieldUnitOfMeasurement.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the unit of measurement is important, please include it" );
			return;
		} else {
			unitOfMeasurement = fieldUnitOfMeasurement.getText();
		}

		String pack = fieldPackage.getText();
		String packVolString = fieldPackageUnit.getText();
		String packVolStr = NumberFormatting.testNumberCorrectness( packVolString );
		BigDecimal packVol = new BigDecimal( packVolStr );

		if ( fieldBarcode.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the barcode is required" );
			return;
		} else {
			barcode = fieldBarcode.getText();
		}

		Item item = new Item();
		item.setBarcode( barcode );
		item.setItemDescription( itemDescription );
		item.setUnitOfMeasurement( unitOfMeasurement );
		item.setItemName( itemName );
		item.setPackageName( pack );
		item.setPackageVolume( packVol );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this item?" );

		if (StageForAlerts.isDecide()) {

			Service<Void> service;
			service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() {

							Item item1 = ItemHibernation.newItem( item );
							clearFields();
							ItemData.data.add(item1);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "created new item: " + item.getItemName(),
									UserLoggedIn.getUserLoggedIn() );
							AuditHistoryData.theData.add(auditHistory);

							return null;

						}
					};
				}
			};
			service.start();

		}

	}

	void newItem() {
		stageForAllPopUps.showAndWait();
	}

	private Item itemPrev;

	Item getItemPrev() {
		return itemPrev;
	}

	private void setItemPrev(Item itemPrev) {
		this.itemPrev = itemPrev;
	}

	void showUpdatePopUp(Item item) {

		// Item item = ItemManipulation.getAddColumnsToTable().clickRow();

		// itemPrev = item;

		setItemPrev( item );

		fieldBarcode.setText( item.getBarcode() );
		areaItemDescription.setText( item.getItemDescription() );
		fieldItemName.setText( item.getItemName() );
		fieldUnitOfMeasurement.setText( item.getUnitOfMeasurement() );
		fieldPackage.setText( item.getPackageName() );
		fieldPackageUnit.setText( item.getPackageVolume().toString() );

		buttonSave.setText( "save changes" );

		stageForAllPopUps.setTitle( "edit item" );

		stageForAllPopUps.showAndWait();

	}

	private void saveChanges() {

		Item item = this.itemPrev;

		String itemName;
		String unitOfMeasurement;
		String barcode;

		new StageForAlerts();

		if (!fieldItemName.getText().trim().isEmpty()) {
			itemName = fieldItemName.getText();
		} else {
			StageForAlerts.inform( "alert", "the item name can not be empty please" );
			return;
		}

		String itemDescription = areaItemDescription.getText();

		if (!fieldUnitOfMeasurement.getText().trim().isEmpty()) {
			unitOfMeasurement = fieldUnitOfMeasurement.getText();
		} else {
			StageForAlerts.inform( "alert", "the unit of measurement is important, please include it" );
			return;
		}

		String pack;
		pack = fieldPackage.getText();
		String packVolString;
		packVolString = fieldPackageUnit.getText();
		String packVolStr = NumberFormatting.testNumberCorrectness( packVolString );
		BigDecimal packVol = new BigDecimal( packVolStr );

		if ( fieldBarcode.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the barcode is required" );
			return;
		} else {
			barcode = fieldBarcode.getText();
		}

		item.setBarcode( barcode );
		item.setItemDescription( itemDescription );
		item.setUnitOfMeasurement( unitOfMeasurement );
		item.setItemName( itemName );
		item.setPackageName( pack );
		item.setPackageVolume( packVol );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if (StageForAlerts.isDecide()) {

			clearFields();

			Service<Void> service;
			service = new Service<Void>() {
				@Override
				protected Task<Void> createTask() {
					return new Task<Void>() {
						@Override
						protected Void call() {

							Item i = ItemHibernation.updateItem( item, item.getId() );

							ItemData.data.set(ItemData.data.indexOf(itemPrev), i);

//							ItemManipulation.data.set(
//									ItemManipulation.data.indexOf(itemPrev),
//									i
//							);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "edited item: " + item.getItemName(), UserLoggedIn.getUserLoggedIn() );
							AuditHistoryData.theData.add(auditHistory);

							itemPrev = null;

							Refresh.setRefreshingDeterminant( 1 );
							// PurchaseFrame.populateComboBoxItems();

						//	new POSFrame().populateComboBoxItems();
							return null;
						}
					};
				}
			};
			service.start();
}

	}

	private void clearFields() {
		FieldClearance.clearTextField( fieldItemName );
		FieldClearance.clearTextField( fieldPackage );
		FieldClearance.clearTextField( fieldPackageUnit );
		FieldClearance.clearTextField( fieldUnitOfMeasurement );
		FieldClearance.clearTextField( fieldBarcode );
		areaItemDescription.clear();

	}

	static void clickRow() {

		tableView.setRowFactory( (TableView<Item> tr) -> {

			TableRow < Item > row;
			row = new TableRow <>();

			row.setOnMouseClicked( e -> {
//				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {
//
//					// this.setIdOfSupplier(s.getId());
//
//				}
			} );

			return row;
		} );
	}

	public Item itemObtainedByClickingTable() {

//		Item it = tableView.selectionModelProperty().getValue().getSelectedItem();
		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setItemPrev( newVal );
			if ( newVal != null ) {
				setItemPrev( newVal );
			} else {
				setItemPrev( oldVal );
			}

		} );

		return getItemPrev();
	}

//	private void refreshPricing(){
//
//		Service<Void> service;
//		service = new Service<Void>() {
//			@Override
//			protected Task<Void> createTask() {
//				return new Task<Void>() {
//					@Override
//					protected Void call() {
//
//						new PricingFrame().populateComboBox();
//
//						AutoCompleteFieldObtainResult.refreshItemsToSearch();
//
//						return null;
//
//					}
//				};
//			}
//		};
//		service.start();
//	}

}
