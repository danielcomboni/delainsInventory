package com.delains.ui.item;

import java.math.BigDecimal;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.GenerateBarcode;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.pricing.PricingFrame;
import com.delains.ui.sales.POSFrame;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ItemFrame extends BorderPane {

	public ItemFrame() {

		setId( "main_borderpane" );

		getStylesheets().add( ItemFrame.class.getResource( "itemFrame.css" ).toExternalForm() );

		buildComponents();

		// itemManipulation = new ItemManipulation();

		tableView = new TableView <>();

		setTableView( ItemManipulation.getTableView() );

		// tableView = ItemManipulation.getTableView();

		setCenter( tableView );
		ItemManipulation.populateTableWithRefreshing();

		// Item item = this.clickRow();
		// System.out.println( "itititit: " + item );

		itemObtainedByClickingTable();

	}

	private static TableView < Item > tableView;

	// private ItemManipulation itemManipulation;

	public static TableView < Item > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Item > tableView ) {
		ItemFrame.tableView = tableView;
	}

	private Label labelItemName;
	private TextField fieldItemName;

	private Label lableItemDescription;
	private TextArea areaItemDescription;

	private Label labelUnitOfMeasurement;
	private TextField fieldUnitOfMeasurement;
	private Label labelPackage;
	private TextField fieldPackage;

	private Label labelPackageUnit;
	private TextField fieldPackageUnit;

	private Label labelBarCode;
	private TextField fieldBarcode;

	private GridPane gridPane;
	private HBox hBox;
	private HBox hBoxButtons;
	private JFXButton buttonSave;
	private JFXButton buttonGenerateBarcode;
	private JFXButton buttonCancel;
	private StageForAllPopUps stageForAllPopUps;

	private void buildComponents() {

		hBox = new HBox();

		gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labelItemName = new Label( "item name:" );
		addToGrid( labelItemName, 0, 0 );

		fieldItemName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldItemName, 400 );
		addToGrid( fieldItemName, 1, 0 );

		lableItemDescription = new Label( "item description:" );
		addToGrid( lableItemDescription, 0, 1 );

		areaItemDescription = new TextArea();
		areaItemDescription.setPrefWidth( 20 );
		areaItemDescription.setPrefHeight( 70 );
		areaItemDescription.setWrapText( true );
		addToGrid( areaItemDescription, 1, 1 );

		labelUnitOfMeasurement = new Label( "unit of measurement:" );
		addToGrid( labelUnitOfMeasurement, 0, 2 );

		HBox boxPackage = new HBox( 10 );
		labelPackage = new Label( "package:" );
		boxPackage.getChildren().add( labelPackage );

		fieldPackage = new TextField();
		boxPackage.getChildren().add( fieldPackage );

		labelPackageUnit = new Label( "package vol:" );
		boxPackage.getChildren().add( labelPackageUnit );

		fieldPackageUnit = new TextField();
		boxPackage.getChildren().add( fieldPackageUnit );

		fieldUnitOfMeasurement = new TextField();
		addToGrid( fieldUnitOfMeasurement, 1, 2 );

		addToGrid( boxPackage, 1, 3 );

		labelBarCode = new Label( "barcode:" );
		addToGrid( labelBarCode, 0, 4 );

		fieldBarcode = new TextField();
		addToGrid( fieldBarcode, 1, 4 );

		hBoxButtons = new HBox( 10 );
		buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		buttonGenerateBarcode = new JFXButton( "generate barcode" );
		hBoxButtons.getChildren().add( buttonGenerateBarcode );

		buttonCancel = new JFXButton( "cancel" );
		hBoxButtons.getChildren().add( buttonCancel );

		addToGrid( hBoxButtons, 1, 5 );

		hBox.getChildren().add( gridPane );

		stageForAllPopUps = new StageForAllPopUps( hBox, "add new item" );

		buttonSave.setOnAction( e -> {

			if ( stageForAllPopUps.getTitle().equals( "edit item" ) ) {
				saveChanges();
				stageForAllPopUps.setTitle( "add new item" );
				buttonSave.setText( "save" );
				stageForAllPopUps.close();
				new PricingFrame().populateComboBox();
			} else {
				createNewItem();
				new PricingFrame().populateComboBox();
			}

		} );

		buttonCancel.setOnAction( e -> {

			stageForAllPopUps.close();
			Refresh.setRefreshingDeterminant( 1 );

			// ItemManipulation.populateTableWithRefreshing();

		} );

		buttonGenerateBarcode.setOnAction( e -> generateBarcode() );

	}

	private void generateBarcode() {
		fieldBarcode.setText( GenerateBarcode.getTheGeneratedbarcode() );
	}

	private void addToGrid( Node node, int col, int row ) {
		gridPane.add( node, col, row );
	}

	private void createNewItem() {

		String itemName = null;
		String itemDescription = null;
		String unitOfMeasurement = null;
		String barcode = null;

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

		if ( StageForAlerts.isDecide() == true ) {

			ItemHibernation.newItem( item );

			clearFields();
			Refresh.setRefreshingDeterminant( 1 );
			ItemManipulation.populateTableWithRefreshing();

			tableView.getItems().clear();
			tableView.getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );

			// this.setCenter( null );
			// setTableView( null );
			// setTableView( ItemManipulation.getTableView() );
			// this.setCenter( ItemManipulation.getTableView() );

			AuditHistoryHibernation.auditValues( "created new item: " + item.getItemName(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseFrame.populateComboBoxItems();
			new POSFrame().populateComboBoxItems();
		}

	}

	public void newItem() {
		stageForAllPopUps.showAndWait();
	}

	private Item itemPrev;

	public Item getItemPrev() {
		return itemPrev;
	}

	public void setItemPrev( Item itemPrev ) {
		this.itemPrev = itemPrev;
	}

	public void showUpdatePopUp( Item item ) {

		// Item item = ItemManipulation.getAddColumnsToTable().clickRow();

		// itemPrev = item;

		setItemPrev( item );

		// System.out.println( "getItem; " + this.getItemPrev() );

		System.out.println( "it...........: " + item );

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

		String itemName = null;
		String itemDescription = null;
		String unitOfMeasurement = null;
		String barcode = null;

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

		item.setBarcode( barcode );
		item.setItemDescription( itemDescription );
		item.setUnitOfMeasurement( unitOfMeasurement );
		item.setItemName( itemName );
		item.setPackageName( pack );
		item.setPackageVolume( packVol );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if ( StageForAlerts.isDecide() == true ) {

			ItemHibernation.updateItem( item, item.getId() );

			clearFields();

			// Refresh.setRefreshingDeterminant( 1 );
			// ItemManipulation.populateTableWithRefreshing();
			//
			// this.setCenter( null );
			// setTableView( null );
			// setTableView( ItemManipulation.getTableView() );
			// // this.setCenter( ItemManipulation.getTableView() );

			tableView.getItems().clear();
			tableView.getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );

			AuditHistoryHibernation.auditValues( "edited item: " + item.getItemName(), UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			itemPrev = null;

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseFrame.populateComboBoxItems();

			new POSFrame().populateComboBoxItems();
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

	private static Item item = null;

	public static Item clickRow() {

		getTableView().setRowFactory( tr -> {

			TableRow < Item > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					Item purchase = row.getItem();

					// this.setIdOfSupplier(s.getId());

					System.out.println( "item clicked in its frame: " + purchase );

					item = purchase;

				}
			} );

			return row;
		} );
		return item;
	}

	public Item itemObtainedByClickingTable() {

		Item it = tableView.selectionModelProperty().getValue().getSelectedItem();
		System.out.println( "getValue: " + it );

		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setItemPrev( newVal );
			System.out.println( "current value: " + tableView.getSelectionModel().selectedItemProperty().get() );

			if ( newVal != null ) {
				setItemPrev( newVal );
				System.out.println( "new Value set Prev: " + getItemPrev() );
			} else {
				setItemPrev( oldVal );
			}

		} );

		return getItemPrev();
	}

}
