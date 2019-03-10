package com.delains.ui.pricing;

import java.math.BigDecimal;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.pricing.Pricing;
import com.delains.model.purchases.Purchase;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.test.ComboBoxAutoComplete;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PricingFrame extends BorderPane {

	public PricingFrame() {
		buildComponents();
		buildPricingTable();
		scanningBarcode();
		
		autoComplete();

	}

	private void autoComplete() {
		comboBoxItem.setTooltip( new Tooltip() );
		new ComboBoxAutoComplete<Item>( comboBoxItem );
	}
	
	
	private Label labelItem;
	private ComboBox < Item > comboBoxItem;

	private Label labelItemAlternative;
	private TextField fieldItem;

	private Label labelPrice;
	private TextField fieldPrice;

	private JFXButton buttonSave;
	// private JFXButton buttonCancel;

	private GridPane gridPane;
	private HBox hBox;

	private void buildComponents() {

		this.setId( "main_borderpane" );

		getStylesheets().add( PricingFrame.class.getResource( "pricing.css" ).toExternalForm() );

		this.hBox = new HBox();

		BorderPane borderPaneCreatePrice = new BorderPane();
		borderPaneCreatePrice.setTop( new HBox( new Label( "Set price from here.." ) ) );

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelItemAlternative = new Label( "item (scanner)" );
		addToGrid( this.labelItemAlternative, 0, 0 );

		this.fieldItem = new TextField();
		addToGrid( this.fieldItem, 1, 0 );

		this.labelItem = new Label( "item" );
		addToGrid( labelItem, 0, 1 );

		this.comboBoxItem = new ComboBox <>();
		addToGrid( comboBoxItem, 1, 1 );

		this.labelPrice = new Label( "set price" );
		addToGrid( labelPrice, 0, 2 );

		this.fieldPrice = new TextField();
		addToGrid( fieldPrice, 1, 2 );

		HBox hBox = new HBox( 10 );
		addToGrid( hBox, 1, 3 );

		this.buttonSave = new JFXButton( "save" );
		hBox.getChildren().add( buttonSave );

		// this.buttonCancel = new JFXButton( "cancel" );
		// hBox.getChildren().add(buttonCancel);

		this.hBox.getChildren().add( gridPane );

		borderPaneCreatePrice.setCenter( this.hBox );

		this.setRight( borderPaneCreatePrice );

		buttonSave.setOnAction( e -> {
			setPrice();
		} );

	}

	private void addToGrid( Node node, int col, int row ) {
		this.gridPane.add( node, col, row );
	}

	private TableView < Pricing > tableView;
	private TableColumn < Pricing, String > colItem;
	private TableColumn < Pricing, String > colPrice;

	private void buildPricingTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Pricing, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Pricing, String > param ) {
						// TODO Auto-generated method stub
						Pricing pricing = param.getValue();
						Item item = pricing.getItemId();

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						StringProperty property = null;

						if ( pricing != null && item != null ) {
							simpleStringProperty.set( item.getItemName() );
							property = simpleStringProperty;
						}

						return property;
					}
				} );
		tableView.getColumns().add( colItem );

		colPrice = new TableColumn <>( "Price" );
		colPrice.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Pricing, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Pricing, String > param ) {
						// TODO Auto-generated method stub

						Pricing pricing = param.getValue();
						// Item item = pricing.getItemId();

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						StringProperty property = null;

						if ( pricing != null ) {
							simpleStringProperty
									.set( NumberFormatting.formatToEnglish( pricing.getPrice().toString() ) );
							property = simpleStringProperty;
						}

						return property;

					}
				} );
		tableView.getColumns().add( colPrice );

		this.setCenter( tableView );

		populateComboBox();

		populateItemPricesTableWithoutRefreshing();

	}

	public void populateComboBox() {

		System.out.println( "loading items into comb price:" );

		comboBoxItem.getItems().clear();
		comboBoxItem.getItems().addAll( ItemHibernation.findAllItemsObservableList() );

		comboBoxItem.setConverter( new StringConverter < Item >() {

			@Override
			public String toString( Item object ) {
				return object.getItemName().concat( " " ).concat( object.getPackageVolume().toString() ).concat( "" )
						.concat( object.getUnitOfMeasurement() );

			}

			@Override
			public Item fromString( String string ) {

				return comboBoxItem.getItems().stream()
						.filter( e -> e.getItemName().concat( " " ).concat( e.getPackageVolume().toString() )
								.concat( "" ).concat( e.getUnitOfMeasurement() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );

		comboBoxItem.valueProperty().addListener( ( obs, oldVal, newVal ) -> {
			if ( newVal != null ) {
				System.out.println( "selected:..." + newVal.getItemName() + " ID:..." + newVal.getId() );
			}
		} );

	}

	private void setPrice() {
		String priceStr = null;
		Item item = null;

		new StageForAlerts();

		if ( fieldPrice.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the price can't be empty" );
			return;

		} else {

			String priceStrTest = fieldPrice.getText();

			priceStr = NumberFormatting.testNumberCorrectness( priceStrTest );

			if ( NumberFormatting.isNumberCorrect() == false ) {

				StageForAlerts.inform( "alert", "the number format is wrong" );
				return;

			}

		}

		System.out.println( "passed: " );

		if ( comboBoxItem.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "select an item to give a price" );
			return;

		} else {

			item = comboBoxItem.getSelectionModel().getSelectedItem();

		}

		StageForAlerts.discontinue( "confirm",
				"item: " + item.getItemName() + "\nprice: " + NumberFormatting.formatToEnglish( priceStr ) );

		BigDecimal price = BigDecimal.ZERO;

		price = new BigDecimal( priceStr );

		Pricing pricing = new Pricing();
		pricing.setItemId( item );
		pricing.setPrice( price );

		System.out.println( "item: " + item );

		// comparing the price being set and the cost price
		Purchase purchaseWhoswCostPriceIsRequired = PurchasesHibernation.mapOfPurchasesToThierItemsID()
				.get( pricing.getItemId().getId() );

		if ( purchaseWhoswCostPriceIsRequired == null ) {
			StageForAlerts.inform( "alert", "this is not yet purchased (stocked) or may be out of stock" );
			return;
		}

		BigDecimal costPriceAtPurchaseTime = purchaseWhoswCostPriceIsRequired.getPrice();

		System.out.println( "priceeeeee....: " + costPriceAtPurchaseTime );

		if ( pricing.getPrice().doubleValue() <= costPriceAtPurchaseTime.doubleValue() ) {

			StageForAlerts.inform( "alert",
					"buying price: " + NumberFormatting.formatToEnglish( costPriceAtPurchaseTime.toString() )
							+ "\nselling price: " + NumberFormatting.formatToEnglish( pricing.getPrice().toString() )
							+ " this is not good for business, you can't record it" );
			return;

		}

		if ( StageForAlerts.isDecide() == true ) {
			PricingHibernation.newPricing( pricing );
			populateItemPricesTableWthRefreshing();
		}

	}

	private void scanningBarcode() {
		fieldItem.setOnKeyPressed( e -> {

			if ( e.getCode() == KeyCode.ENTER ) {

				getItemByBarcode( fieldItem.getText() );
				fieldItem.clear();

			}

		} );
	}

	private void getItemByBarcode( String barcode ) {

		Item item = ItemHibernation.barcodesMappedToThierItems().get( barcode );

		comboBoxItem.getSelectionModel().select( item );

	}

	private void populateItemPricesTableWithoutRefreshing() {
		this.tableView.setItems( PricingHibernation.findAllPricingsObservableList() );
	}

	private void populateItemPricesTableWthRefreshing() {
		this.tableView.getItems().clear();
		this.tableView.getItems().addAll( PricingHibernation.findAllPricingsObservableListRefresh() );
	}

}
