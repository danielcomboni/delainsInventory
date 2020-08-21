package com.delains.ui.pricing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.pos.ItemPriceBarcode;
import com.delains.model.pricing.Pricing;
import com.delains.model.purchases.Purchase;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.item.ItemData;
import com.delains.ui.test.AutoCompleteFieldObtainResult;
import com.delains.ui.test.ComboBoxAutoComplete;
import com.delains.ui.test.auto.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class PricingFrame extends BorderPane {

	public PricingFrame() {
		buildComponents();
		buildPricingTable();
		scanningBarcode();

		autoComplete();

		AutoCompleteFieldObtainResult.refreshItemsToSearch();

	}

	private void autoComplete() {
		comboBoxItem.setTooltip( new Tooltip() );
		new ComboBoxAutoComplete<>(comboBoxItem);
	}

	private TextField fieldSearch;

	private ComboBox < Item > comboBoxItem;

	private TextField fieldItem;

	private TextField fieldPrice;

	private GridPane gridPane;

	private void buildComponents() {

		this.setId( "main_borderpane" );

		getStylesheets().add( PricingFrame.class.getResource( "pricing.css" ).toExternalForm() );

		HBox hBox1 = new HBox();

		BorderPane borderPaneCreatePrice = new BorderPane();
		borderPaneCreatePrice.setTop( new HBox( new Label( "Set price from here.." ) ) );

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelItemAlternative = new Label("item (scanner)");
//		addToGrid(labelItemAlternative, 0, 0 );

		this.fieldItem = new TextField();
		fieldItem.setPromptText("scan barcode");
//		addToGrid( this.fieldItem, 1, 0 );

		Label labelItem = new Label("item");
		addToGrid(labelItem, 0, 1 );

		this.comboBoxItem = new ComboBox <>();
		comboBoxItem.setItems(ItemData.data);
		ItemData.setConverter(comboBoxItem);
		addToGrid( comboBoxItem, 1, 1 );

		Label labelPrice = new Label("set price");
		addToGrid(labelPrice, 0, 2 );

		this.fieldPrice = new TextField();
		fieldPrice.setPromptText("set price...");
		addToGrid( fieldPrice, 1, 2 );

		Label labelSearch = new Label("Search");
		addToGrid(labelSearch, 0,3);

		this.fieldSearch = new TextField();

		searchItem();

		this.fieldSearch.setPromptText("search item");
		addToGrid(fieldSearch, 1,3);

		HBox hBox = new HBox( 10 );
		addToGrid( hBox, 1, 4 );

		JFXButton buttonSave = new JFXButton("save");
		hBox.getChildren().add(buttonSave);

		hBox1.getChildren().add( gridPane );

		borderPaneCreatePrice.setCenter(hBox1);

		this.setRight( borderPaneCreatePrice );

		buttonSave.setOnAction(e -> setPrice());

	}

	private void addToGrid( Node node, int col, int row ) {
		this.gridPane.add( node, col, row );
	}

	private void buildPricingTable() {

		TableView<Pricing> tableView = new TableView<>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		tableView.setItems(PricingData.data);

		TableColumn<Pricing, String> colItem = new TableColumn<>("Item");
		colItem.setCellValueFactory(
				param -> {
					Pricing pricing = param.getValue();
					Item item = pricing.getItemId();

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					StringProperty property = null;

					if (item != null) {
						simpleStringProperty.set( item.getItemName() );
						property = simpleStringProperty;
					}

					return property;
				});
		tableView.getColumns().add(colItem);

		TableColumn<Pricing, String> colPrice = new TableColumn<>("Price");
		colPrice.setCellValueFactory(
				param -> {

					Pricing pricing = param.getValue();

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					StringProperty property = null;

					if ( pricing != null ) {
						simpleStringProperty
								.set( NumberFormatting.formatToEnglish( pricing.getPrice().toString() ) );
						property = simpleStringProperty;
					}

					return property;

				});
		tableView.getColumns().add(colPrice);

		this.setCenter(tableView);


	}

	private void setPrice() {
		String priceStr;
		Item item;

		new StageForAlerts();

		if ( fieldPrice.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the price can't be empty" );
			return;

		} else {

			String priceStrTest = fieldPrice.getText();

			priceStr = NumberFormatting.testNumberCorrectness( priceStrTest );

			if (!NumberFormatting.isNumberCorrect()) {

				StageForAlerts.inform( "alert", "the number format is wrong" );
				return;

			}

		}

		if ( comboBoxItem.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "select an item to give a price" );
			return;

		} else
			item = comboBoxItem.getSelectionModel().getSelectedItem();

		StageForAlerts.discontinue( "confirm",
				"item: " + item.getItemName() + "\nprice: " + NumberFormatting.formatToEnglish( priceStr ) );

		BigDecimal price;

		price = new BigDecimal( priceStr );

		Pricing pricing = new Pricing();
		pricing.setItemId( item );
		pricing.setPrice( price );

// comparing the price being set and the cost price
		Purchase purchaseWhoswCostPriceIsRequired = PurchasesHibernation.mapOfPurchasesToThierItemsID()
				.get( pricing.getItemId().getId() );

		if ( purchaseWhoswCostPriceIsRequired == null ) {
			StageForAlerts.inform( "alert", "this is not yet purchased (stocked) or may be out of stock" );
			return;
		}

		BigDecimal costPriceAtPurchaseTime = purchaseWhoswCostPriceIsRequired.getPrice();

		if ( pricing.getPrice().doubleValue() <= costPriceAtPurchaseTime.doubleValue() ) {

			StageForAlerts.inform( "alert",
					"buying price: " + NumberFormatting.formatToEnglish( costPriceAtPurchaseTime.toString() )
							+ "\nselling price: " + NumberFormatting.formatToEnglish( pricing.getPrice().toString() )
							+ " this is not good for business, you can't record it" );
			return;

		}

		if (StageForAlerts.isDecide()) {
			PricingHibernation.newPricing( pricing );

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

	private void searchItem(){

		List<ItemPriceBarcode> list = AutoCompleteFieldObtainResult.setSearchItems();
		AutoCompleteFieldObtainResult.setStrValue(list);
		List<ItemPriceBarcode> strList = new ArrayList<>(AutoCompleteFieldObtainResult.getItemPriceBarcodes());

		AutoCompleteTextField<ItemPriceBarcode> text = new AutoCompleteTextField(strList);
		text.setMaxEntries(strList.size());
		fieldSearch = text;

		text.getEntryMenu().setOnAction(e ->
				((MenuItem) e.getTarget()).addEventHandler(Event.ANY, event ->
				{
					if (text.getLastSelectedObject() != null)
					{
						text.setText(text.getLastSelectedObject().toString());

						fieldItem.setText( text.getLastSelectedObject().getItem().getBarcode() );

						fieldItem.requestFocus();

						fieldItem.fireEvent(
								new KeyEvent( KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
										true, true, true, true ) );

						fieldSearch.clear();

					}
				}));
	}

}
