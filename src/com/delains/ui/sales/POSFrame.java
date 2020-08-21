package com.delains.ui.sales;

import java.math.BigDecimal;
import java.util.*;
import java.util.Map.Entry;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pos.POSDAO;
import com.delains.dao.pos.POSHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.stock.StockDAO;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.stock.StockWarningPointHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.customers.Customer;
import com.delains.model.history.AuditHistory;
import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.pos.ItemPriceBarcode;
import com.delains.model.pos.POS;
import com.delains.model.pricing.Pricing;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockWarningPoint;
import com.delains.ui.customers.CustomerData;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.ButtonRaisedType;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;

import com.delains.ui.item.ItemData;
import com.delains.ui.payments.MediumOfPaymentData;
import com.delains.ui.sales.receiptprinting.PrinterService;
import com.delains.ui.test.AutoCompleteFieldObtainResult;
import com.delains.ui.test.auto.AutoCompleteTextField;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.print.PrintQuality;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class POSFrame extends BorderPane {

	public POSFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( POSFrame.class.getResource( "pos.css" ).toExternalForm() );
		this.buildTop();
		this.buildPOSTable();
		this.buildBottom();

		//populateComboBoxItems();
//		populateComboBoxCustomers();
//		populateComboBoxMediumOfPayment();
		getMediumOfPayment();

		posToFields();

		comboBoxItem.setOnAction( e -> captureBarcodeFromComboBox() );

		calculateChange();

		//autoComplete();
		autoCompleteTextField();

	}

//	private void autoComplete() {
//		comboBoxItem.setTooltip( new Tooltip() );
//		new ComboBoxAutoComplete<>(comboBoxItem);
//	}


	private void autoCompleteTextField() {

		AutoCompleteFieldObtainResult.setStrValue(AutoCompleteFieldObtainResult.setSearchItems());
		List<ItemPriceBarcode> strList = new ArrayList<>(AutoCompleteFieldObtainResult.getItemPriceBarcodes());

		AutoCompleteTextField<ItemPriceBarcode> text = new AutoCompleteTextField(strList);
		text.setMaxEntries(strList.size());
		fieldSearch = text;

		text.getEntryMenu().setOnAction(e ->
		{
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

					text.clear();
					fieldSearch.clear();

				}
			});
		});
	}

	private TextField fieldItem;

	private TextField fieldQuantity;

	private ComboBox < Item > comboBoxItem;

	private TextField fieldSearch;

	private GridPane gridPaneTop;

	private void buildTop() {

		HBox hBoxTop = new HBox();

		this.gridPaneTop = new GridPane();
		this.gridPaneTop.setHgap( 20 );
		this.gridPaneTop.setVgap( 10 );
		this.gridPaneTop.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelItem = new Label("Item:");
		addToGridPaneTop(labelItem, 0, 0 );

		this.fieldItem = new TextField();
		this.fieldItem.setPromptText( "scanner" );
		ComponentWidth.setWidthOfTextField( fieldItem, 200 );
		addToGridPaneTop( fieldItem, 1, 0 );

		Label labelItemAlternative = new Label("Item alternative:");
		addToGridPaneTop(labelItemAlternative, 2, 0 );

		this.comboBoxItem = new ComboBox <>();
		comboBoxItem.setItems(ItemData.data);
		populateComboBoxItems();

		addToGridPaneTop( this.comboBoxItem, 3, 0 );

		Label labelQuantity = new Label("Quantity");
		addToGridPaneTop(labelQuantity, 4, 0 );

		this.fieldQuantity = new TextField();
		fieldQuantity.setPromptText( "quantity" );
		ComponentWidth.setWidthOfTextField( fieldQuantity, 200 );
		addToGridPaneTop( fieldQuantity, 5, 0 );

		this.fieldSearch = new TextField();
		fieldSearch.setPromptText( "search item" );
		ComponentWidth.setWidthOfTextField(fieldSearch, 500);

		autoCompleteTextField();

		addToGridPaneTop( fieldSearch, 1, 1 );
		GridPane.setColumnSpan(fieldSearch,7);


		fieldSearch.setOnKeyPressed( e -> {

			// String barcode = fieldItem.getText();
			// getItemByBarcode( barcode );
			// fieldItem.clear();
		} );

		JFXButton btn = new JFXButton( "sea" );
		// addToGridPaneTop( btn, 5, 2 );
		btn.setOnAction( e -> {
		} );

		hBoxTop.getChildren().add( this.gridPaneTop );

		this.setTop(hBoxTop);

	}

	private void addToGridPaneTop( Node node, int columnIndex, int rowIndex ) {
		this.gridPaneTop.add( node, columnIndex, rowIndex );
	}

	private TableView < POS > tableView;
	private TableColumn < POS, String > colItem;

	private void buildPOSTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellValueFactory(
				param -> {

					POS pos = param.getValue();

					String packVol = pos.getItemId().getPackageVolume().toString().concat( " " )
							.concat( pos.getItemId().getUnitOfMeasurement() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( pos.getItemId().getItemName().concat( " " ).concat( packVol ) );
					return simpleStringProperty;
				});
		tableView.getColumns().add( colItem );

		colItem.setCellFactory( tc -> {

			TableCell < POS, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		TableColumn<POS, String> colQuantity = new TableColumn<>("Quantity");
		colQuantity.setCellValueFactory(
				param -> {

					POS pos = param.getValue();

					String pack = pos.getItemId().getPackageName();

					if ( pos.getQuantity().doubleValue() > 1 ) {
						pack = pack.concat( "(s/ies)" );
					}

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( NumberFormatting.formatToEnglish( pos.getQuantity().toString() )
							.concat( " " ).concat( pack ) );
					return (StringProperty) simpleStringProperty;
				});
		tableView.getColumns().add(colQuantity);

		TableColumn<POS, String> colUnitCost = new TableColumn<>("Unit cost");
		colUnitCost.setCellValueFactory(
				param -> {
					// TODO Auto-generated method stub

					POS pos = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty
							.setValue( NumberFormatting.formatToEnglish( pos.getPricing().getPrice().toString() ) );
					StringProperty property = simpleStringProperty;
					return property;
				});
		tableView.getColumns().add(colUnitCost);

		TableColumn<POS, String> colTotalCost = new TableColumn<>("total cost");
		colTotalCost.setCellValueFactory(
				param -> {

					POS pos = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( NumberFormatting.formatToEnglish( pos.getCost().toString() ) );
					StringProperty property = simpleStringProperty;
					return property;
				});
		tableView.getColumns().add(colTotalCost);

		TableColumn<POS, Void> colIncrease = new TableColumn<>("Increase");
		colIncrease.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.PLUS ) );
		colIncrease.setCellFactory(new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

			@Override
			public TableCell < POS, Void > call( TableColumn < POS, Void > param ) {

				final TableCell < POS, Void > cell = new TableCell < POS, Void >() {

					final Button button = new Button( "", new FontAwesomeIconView( FontAwesomeIcon.PLUS ) );

					{

						button.setOnAction( e -> {

							POS pos = getTableView().getItems().get( getIndex() );

							POSButtonListening.increase( pos, POSDAO.getPosValuesForTheTable() );

							populatePOSTable();

							calculatedTotalCost();

							calculateChangeToReturn();

						} );
					}

					@Override
					protected void updateItem( Void item, boolean empty ) {

						super.updateItem( item, empty );
						if ( empty ) {
							setGraphic( null );
						} else {
							setGraphic( button );
						}
					}

				};

				return cell;
			}
		} );
		tableView.getColumns().add(colIncrease);

		TableColumn<POS, Void> colReduce = new TableColumn<>("Reduce");
		colReduce.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.MINUS ) );
		colReduce.setCellFactory(new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

			@Override
			public TableCell < POS, Void > call( TableColumn < POS, Void > param ) {

				return new TableCell < POS, Void >() {

					final Button button = new Button( "", new FontAwesomeIconView( FontAwesomeIcon.MINUS ) );

					{

						button.setOnAction( e -> {

							POS pos = getTableView().getItems().get( getIndex() );

							POSButtonListening.reduce( pos, POSDAO.getPosValuesForTheTable() );

							populatePOSTable();

							calculatedTotalCost();

							calculateChangeToReturn();

						} );
					}

					@Override
					protected void updateItem( Void item, boolean empty ) {

						super.updateItem( item, empty );
						if ( empty )
							setGraphic(null);
						else
							setGraphic(button);
					}

				};
			}
		} );
		tableView.getColumns().add(colReduce);

		TableColumn<POS, Void> colRemove = new TableColumn<>("Remove");
		colRemove.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.REMOVE ) );
		colRemove.setCellFactory(new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

			@Override
			public TableCell < POS, Void > call( TableColumn < POS, Void > param ) {

				return new TableCell < POS, Void >() {

					final Button button = new Button( "", new FontAwesomeIconView( FontAwesomeIcon.REMOVE ) );

					{

						button.setOnAction( e -> {

							POS pos = getTableView().getItems().get( getIndex() );

							POSButtonListening.remove( pos, POSDAO.getPosValuesForTheTable() );

							populatePOSTable();

							calculatedTotalCost();

							calculateChangeToReturn();

						} );
					}

					@Override
					protected void updateItem( Void item, boolean empty ) {

						super.updateItem( item, empty );
						if ( empty ) {
							setGraphic( null );
						} else {
							setGraphic( button );
						}
					}

				};
			}
		} );
		tableView.getColumns().add(colRemove);

		VBox box = new VBox();
		box.getChildren().add( tableView );

		this.setCenter( box );

	}

	private void addToGridPaneBottomLeft( Node node, int columnIndex, int rowIndex ) {
		this.gridPaneBottomLeft.add( node, columnIndex, rowIndex );
	}

	private GridPane gridPaneBottomLeft;

	private JFXCheckBox checkBoxCredit;

	private TextField fieldAmountPaid;

	private Label labelTotalCostText;

	private Label labelBalanceText;

	private ComboBox < Customer > comboBoxCustomerName;

	private Label labelBalanceToBePaidByCustomerText;

	private void buildBottom() {

		HBox hBoxBottom = new HBox();
		hBoxBottom.setFillHeight( true );

		VBox vBoxBottomLeft = new VBox();
		vBoxBottomLeft.setPrefHeight( 275 );
		vBoxBottomLeft.setPrefWidth( 1000 );

		gridPaneBottomLeft = new GridPane();

		this.gridPaneBottomLeft = new GridPane();
		this.gridPaneBottomLeft.setHgap( 20 );
		this.gridPaneBottomLeft.setVgap( 10 );
		this.gridPaneBottomLeft.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.checkBoxCredit = new JFXCheckBox( "credit?" );
		addToGridPaneBottomLeft( checkBoxCredit, 1, 0 );

		Label labelCustomerName = new Label("customer:");
		addToGridPaneBottomLeft(labelCustomerName, 0, 1 );

		this.comboBoxCustomerName = new ComboBox <>();
		comboBoxCustomerName.setItems(CustomerData.data);
		populateComboBoxCustomers();
		addToGridPaneBottomLeft( comboBoxCustomerName, 1, 1 );

		Label labelAmountPaid = new Label("amount paid:");
		addToGridPaneBottomLeft(labelAmountPaid, 0, 2 );

		this.fieldAmountPaid = new TextField();
		ComponentWidth.setWidthOfTextField( fieldAmountPaid, 400 );
		addToGridPaneBottomLeft( fieldAmountPaid, 1, 2 );

		Label labelTotalCost = new Label("Total cost: ");
		addToGridPaneBottomLeft(labelTotalCost, 0, 3 );

		this.labelTotalCostText = new Label();
		addToGridPaneBottomLeft( labelTotalCostText, 1, 3 );

		Label labelBalance = new Label("change:");
		addToGridPaneBottomLeft(labelBalance, 0, 4 );

		this.labelBalanceText = new Label();
		addToGridPaneBottomLeft( this.labelBalanceText, 1, 4 );

		Label labelBalanceToBePaidByCustomer = new Label("balance:");
		addToGridPaneBottomLeft(labelBalanceToBePaidByCustomer, 0, 5 );

		this.labelBalanceToBePaidByCustomerText = new Label();
		addToGridPaneBottomLeft( labelBalanceToBePaidByCustomerText, 1, 5 );

		comboBoxMediumOfPayment = new ComboBox <>();
		comboBoxMediumOfPayment.setItems(MediumOfPaymentData.data);
		populateComboBoxMediumOfPayment();
		addToGridPaneBottomLeft( new Label( "payment medium:" ), 0, 6 );
		addToGridPaneBottomLeft( comboBoxMediumOfPayment, 1, 6 );

		HBox hBox = new HBox( 10 );

		JFXButton buttonCheckOut = new JFXButton("check out (no print) ", new FontAwesomeIconView(FontAwesomeIcon.SAVE));
		new ButtonRaisedType(buttonCheckOut);
		hBox.getChildren().add(buttonCheckOut);

		JFXButton buttonCheckOutWithoutPrinting = new JFXButton("check out (print)",
				new FontAwesomeIconView(FontAwesomeIcon.PRINT));
		new ButtonRaisedType(buttonCheckOut);
		hBox.getChildren().add(buttonCheckOutWithoutPrinting);

		JFXButton buttonCancel = new JFXButton("cancel", new FontAwesomeIconView(FontAwesomeIcon.REMOVE));
		new ButtonRaisedType(buttonCancel);
		hBox.getChildren().add(buttonCancel);
		addToGridPaneBottomLeft( hBox, 1, 7 );

		vBoxBottomLeft.getChildren().add( gridPaneBottomLeft );

		hBoxBottom.getChildren().add(vBoxBottomLeft);

		this.setBottom(hBoxBottom);

		buttonCancel.setOnAction(e -> cancel());

		buttonCheckOut.setOnAction(e -> checkOut());

		buttonCheckOutWithoutPrinting.setOnAction(e -> checkOutWithPrinting());

		buttonCheckOut.setOnKeyPressed(e -> {
			if ( e.getCode() == KeyCode.ENTER ) {
				checkOut();
			}
		} );

	}

	private void populateComboBoxItems() {

		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {

						setConverterItemComboBox(comboBoxItem);

						return null;

					}

				};

			}

		};

		service.start();

	}

	public static void setConverterItemComboBox(ComboBox<Item> comboBoxItem) {
		comboBoxItem.setConverter(new StringConverter<Item>() {

			@Override
			public String toString(Item object) {

				return object.getItemName().concat(" ").concat(object.getPackageVolume().toString())
						.concat("").concat(object.getUnitOfMeasurement());
			}

			@Override

			public Item fromString(String string) {

				return comboBoxItem.getItems().stream()
						.filter(e -> e.getItemName().concat(" ").concat(e.getPackageVolume().toString())
								.concat("").concat(e.getUnitOfMeasurement()).equals(string))
						.findFirst().orElse(null);

			}

		});
	}

	private void populateComboBoxCustomers() {

		Service<Void> service;
		service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {

						comboBoxCustomerName.setItems( CustomerData.data );
						comboBoxCustomerName.setConverter( new StringConverter < Customer >() {

							@Override
							public String toString( Customer object ) {

								return object.getCustomerName();
							}

							@Override
							public Customer fromString( String string ) {

								return comboBoxCustomerName.getItems().stream()
										.filter( e -> e.getCustomerName().equals( string ) ).findFirst().orElse( null );
							}
						} );

						return null;

					}

				};

			}

		};

		service.start();


	}

	private void posToFields() {

		captureBarcode();

	}

	private void getItemByBarcode( String barcode ) {

		new StageForAlerts();

		POS pos = new POS();

		Item item = ItemHibernation.barcodesMappedToThierItems().get( barcode );

		if ( item == null ) {
			fieldItem.clear();
			StageForAlerts.inform( "alert", "this item does not exist" );
			return;
		}

		pos.setItemId( item );

		pos.setBarCode( barcode );

		pos.setItemName( item.getItemName() );

		comboBoxItem.getSelectionModel().clearSelection();

		Pricing pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( item.getId() );

		if ( pricing == null ) {

			PricingHibernation.findAllPricingObservableListRefresh();


			pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( item.getId() );

			if ( pricing == null ) {
				new StageForAlerts();
				StageForAlerts.inform( "alert", "this item's price is not yet set" );
				return;
			}

		}

		pos.setPricing( pricing );

		if ( POSDAO.getPosValuesForTheTable() == null ) {

			if (!fieldQuantity.getText().trim().isEmpty()) {
				getQuantityOfAnItemInField();
				pos.setQuantity( this.quantity );
			} else {
				pos.setQuantity( BigDecimal.ONE );
			}

			pos.setCost( pos.getQuantity().multiply( pos.getPricing().getPrice() ) );

			pos.setChange( calculateChangeToReturn() );
			pos.setBalanceToBePaidByCustomer( calculateBalanceToBePaidByACreditCustomer() );

			ObservableMap < Item, POS > poses = FXCollections.observableHashMap();

			poses.put( item, pos );

			POSDAO.setPosValuesForTheTable( poses );

		}

		else {

			if ( POSDAO.getPosValuesForTheTable().containsKey( item ) ) {

				POS pos2;

				POS p = POSDAO.getPosValuesForTheTable().get( item );

				BigDecimal qtyOld = p.getQuantity();

				pos2 = p;

				if ( fieldQuantity.getText().trim().isEmpty() ) {
					pos2.setQuantity( qtyOld.add( BigDecimal.ONE ) );
				} else {
					getQuantityOfAnItemInField();
					pos2.setQuantity( qtyOld.add( this.quantity ) );
				}

				pos2.setCost( pos2.getQuantity().multiply( pos2.getPricing().getPrice() ) );

				pos.setChange( calculateChangeToReturn() );
				pos.setBalanceToBePaidByCustomer( calculateBalanceToBePaidByACreditCustomer() );

				POSDAO.getPosValuesForTheTable().put( item, pos2 );

			}

			else {

				if ( fieldQuantity.getText().trim().isEmpty() ) {
					pos.setQuantity( BigDecimal.ONE );
				} else {
					getQuantityOfAnItemInField();
					pos.setQuantity( this.quantity );
				}

				// pos.setQuantity( BigDecimal.ONE );

				pos.setCost( pos.getQuantity().multiply( pos.getPricing().getPrice() ) );

				pos.setChange( calculateChangeToReturn() );
				pos.setBalanceToBePaidByCustomer( calculateBalanceToBePaidByACreditCustomer() );

				ObservableMap < Item, POS > poses = FXCollections.observableHashMap();

				poses.put( item, pos );

				POSDAO.getPosValuesForTheTable().put( item, pos );

			}

		}


		populatePOSTable();
		calculatedTotalCost();
		calculateBalanceToBePaidByACreditCustomer();


	}

	private void populatePOSTable() {

		if ( POSDAO.listForPOSTable() == null ) {

			this.tableView.setItems( POSDAO.listForPOSTable() );

		}

		else {

			this.tableView.getItems().clear();
			this.tableView.getItems().addAll( POSDAO.listForPOSTable() );

		}

	}

	private void calculatedTotalCost() {
		this.labelTotalCostText.setText( NumberFormatting.formatToEnglish( POSDAO.totalCostCalculated().toString() ) );
	}

	private void captureBarcode() {

		fieldItem.setOnKeyPressed( e -> {

			if ( e.getCode() == KeyCode.ENTER ) {

				String barcode = fieldItem.getText();
				getItemByBarcode( barcode );
				fieldItem.clear();

			}

		} );

	}

	private void captureBarcodeFromComboBox() {

		String bar;
		Item item = comboBoxItem.getSelectionModel().getSelectedItem();
		bar = item.getBarcode();
// setBarcode( bar );

		fieldItem.setText( bar );

		fieldItem.requestFocus();

		fieldItem.fireEvent( new KeyEvent( KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, true, true, true, true ) );

		comboBoxItem.getSelectionModel().clearSelection();

	}

	private void calculateChange() {

		fieldAmountPaid.focusedProperty().addListener( ( obs, oldVal, newVal ) -> {

			if ( oldVal ) {
				calculateChangeToReturn();
			}

			if ( newVal ) {
				{
					calculateChangeToReturn();
				}

			}

		} );

	}

	private BigDecimal quantity = BigDecimal.ZERO;

	private void getQuantityOfAnItemInField() {
		new StageForAlerts();
		String qtyStr = NumberFormatting.testNumberCorrectness( fieldQuantity.getText().trim() );
		if (!NumberFormatting.isNumberCorrect()) {
			StageForAlerts.inform( "alert", "check your quantity number format" );
			return;
		}

		quantity = new BigDecimal( qtyStr );

	}

	private BigDecimal calculateChangeToReturn() {

		BigDecimal changeReturn = BigDecimal.ZERO;

		if ( POSDAO.totalCostCalculated().doubleValue() > 0 ) {

			if ( !fieldAmountPaid.getText().trim().isEmpty() ) {

				String amountPaidString = fieldAmountPaid.getText();
				String amountPaidStr = NumberFormatting.testNumberCorrectness( amountPaidString );
				BigDecimal amountPaid = new BigDecimal( amountPaidStr );

				BigDecimal change = amountPaid.subtract( POSDAO.totalCostCalculated() );

				changeReturn = change;

				this.labelBalanceText.setText( NumberFormatting.formatToEnglish( change.toString() ) );

			}

			else {
				fieldAmountPaid.setText( "0" );
				String amountPaidString;
				amountPaidString = fieldAmountPaid.getText();
				String amountPaidStr = NumberFormatting.testNumberCorrectness( amountPaidString );
				BigDecimal amountPaid = new BigDecimal( amountPaidStr );

				BigDecimal change = amountPaid.subtract( POSDAO.totalCostCalculated() );

				changeReturn = change;

				this.labelBalanceText.setText( NumberFormatting.formatToEnglish( change.toString() ) );

			}

		}

		calculateBalanceToBePaidByACreditCustomer();

		return changeReturn;

	}

	private void cancel() {

		POSButtonListening.cancelPOS( POSDAO.getPosValuesForTheTable(), fieldItem, comboBoxItem, fieldQuantity,
				checkBoxCredit, fieldAmountPaid, comboBoxCustomerName, labelTotalCostText, labelBalanceText,
				labelBalanceToBePaidByCustomerText );
		populatePOSTable();

	}

	private void checkOut() {

		//new StageForAlerts();


		// printerService.printString("EPSON-TM-T20II", "\n\n testing testing 1 2 3eeeee \n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");


		calculateBalanceToBePaidByACreditCustomer();

		if ( checkBoxCredit.isSelected() && comboBoxCustomerName.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "for a credit sale, please specify the customer" );

			return;

		}

		BigDecimal amountPaid = new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) );
		boolean credit = false;

		if ( checkBoxCredit.isSelected() ) {
			credit = true;
		}

		BigDecimal bal = calculateBalanceToBePaidByACreditCustomer();
		BigDecimal change = calculateChangeToReturn();
		Customer customer = comboBoxCustomerName.getSelectionModel().getSelectedItem();

		ObservableMap < Item, POS > map = POSCheckOut.breakingDownPosTableForBatchEnchancement(
				POSDAO.getPosValuesForTheTable(), amountPaid, POSDAO.totalCostCalculated(), change, bal, credit,
				customer );



		int[] result = POSHibernation.newPOS( map );

		System.out.println("result pos: " + result);

		// AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

		// batch has to be used to record each item details
		// the total cost, the change and balance all have to be in their own records
		// all columns can be captured but during display, only the required at
		// particular places have to be allowed
		// skip a row (insert an empty row) to allow spacing of the records for every
		// sale made

		POSPrintReceipt posPrintReceipt = new POSPrintReceipt();

		if ( change.doubleValue() < 0 ) {

			posPrintReceipt.buildMain( POSDAO.listForPOSTable(),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) ),
					new BigDecimal( "-" + NumberFormatting.testNumberCorrectness( labelBalanceText.getText() ) ),
					new BigDecimal(
							NumberFormatting.testNumberCorrectness( labelBalanceToBePaidByCustomerText.getText() ) ) );

			if ( ( new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() == 0
					|| new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() < new BigDecimal(
					NumberFormatting.testNumberCorrectness( this.labelTotalCostText.getText() ) )
					.doubleValue() )
					&& !checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert",
						"the amount paid is less, you can not proceed unless it it a credit sale" );

				return;

			}

		}

		else {

			posPrintReceipt.buildMain( POSDAO.listForPOSTable(),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelBalanceText.getText() ) ),
					new BigDecimal(
							NumberFormatting.testNumberCorrectness( labelBalanceToBePaidByCustomerText.getText() ) ) );

			if ( ( new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() == 0
					|| new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() < new BigDecimal(
					NumberFormatting.testNumberCorrectness( this.labelTotalCostText.getText() ) )
					.doubleValue() )
					&& !checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert",
						"the amount paid is less, you can not proceed unless it it a credit sale" );

				return;

			}

		}

		ObservableMap < Item, Stock > stockMappedToItem = FXCollections.observableHashMap();

		for ( Entry < Item, POS > p : POSDAO.getPosValuesForTheTable().entrySet() ) {

			Item item = p.getKey();
			Stock stock = new Stock();
			stock.setItemId( item );
			stock.setItemQuantity( p.getValue().getQuantity() );

			stockMappedToItem.put( p.getKey(), stock );

		}

		StockDAO.reduceStockOnSale( stockMappedToItem );

		ObservableList < POS > list = FXCollections.observableArrayList();
		POSDAO.getPosValuesForTheTable().entrySet().parallelStream().forEachOrdered( p -> list.add( p.getValue() ) );

		cancel();

		print( posPrintReceipt );

		checkBoxCredit.setSelected( false );

		AuditHistory auditHistory = AuditHistoryHibernation.auditValues( " a sale checkout made ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryData.theData.add(auditHistory);
		warnUserOnStockRunningLow( list );

	}

	private void checkOutWithPrinting() {

		new StageForAlerts();

		calculateBalanceToBePaidByACreditCustomer();

		if ( checkBoxCredit.isSelected() && comboBoxCustomerName.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "for a credit sale, please specify the customer" );

			return;

		}

		BigDecimal amountPaid = new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) );
		boolean credit = false;

		if ( checkBoxCredit.isSelected() ) {
			credit = true;
		}

		BigDecimal bal = calculateBalanceToBePaidByACreditCustomer();
		BigDecimal change = calculateChangeToReturn();

		Customer customer = comboBoxCustomerName.getSelectionModel().getSelectedItem();

		ObservableMap < Item, POS > map = POSCheckOut.breakingDownPosTableForBatchEnchancement(
				POSDAO.getPosValuesForTheTable(), amountPaid, POSDAO.totalCostCalculated(), change, bal, credit,
				customer );

		int[] result = POSHibernation.newPOS( map );
		System.out.println("the result: " + result.length);



		// AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

		// batch has to be used to record each item details
		// the total cost, the change and balance all have to be in their own records
		// all columns can be captured but during display, only the required at
		// particular places have to be allowed
		// skip a row (insert an empty row) to allow spacing of the records for every
		// sale made

		POSPrintReceipt posPrintReceipt;
		posPrintReceipt = new POSPrintReceipt();

		if (!(change.doubleValue() < 0)) {

			posPrintReceipt.buildMain( POSDAO.listForPOSTable(),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelBalanceText.getText() ) ),
					new BigDecimal(NumberFormatting.testNumberCorrectness( labelBalanceToBePaidByCustomerText.getText() ) ) );

			if ( ( new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() == 0
					|| new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() < new BigDecimal(
					NumberFormatting.testNumberCorrectness( this.labelTotalCostText.getText() ) )
					.doubleValue() )
					&& !checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert",
						"the amount paid is less, you can not proceed unless it it a credit sale" );

				return;

			}

		} else {

			posPrintReceipt.buildMain( POSDAO.listForPOSTable(),
					new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) ),
					new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) ),
					new BigDecimal( "-" + NumberFormatting.testNumberCorrectness( labelBalanceText.getText() ) ),
					new BigDecimal(
							NumberFormatting.testNumberCorrectness( labelBalanceToBePaidByCustomerText.getText() ) ) );

			if ( ( new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() == 0
					|| new BigDecimal( NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) )
					.doubleValue() < new BigDecimal(
					NumberFormatting.testNumberCorrectness( this.labelTotalCostText.getText() ) )
					.doubleValue() )
					&& !checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert",
						"the amount paid is less, you can not proceed unless it it a credit sale" );

				return;

			}

		}

		ObservableMap < Item, Stock > stockMappedToItem;
		stockMappedToItem = FXCollections.observableHashMap();

		for ( Entry < Item, POS > p : POSDAO.getPosValuesForTheTable().entrySet() ) {

			Item item = p.getKey();
			Stock stock = new Stock();
			stock.setItemId( item );
			stock.setItemQuantity( p.getValue().getQuantity() );

			stockMappedToItem.put( p.getKey(), stock );

		}

		StockDAO.reduceStockOnSale( stockMappedToItem );

		ObservableList < POS > list = FXCollections.observableArrayList();
		POSDAO.getPosValuesForTheTable().entrySet().parallelStream().forEachOrdered( p -> list.add( p.getValue() ) );

		cancel();

		print( posPrintReceipt );

		checkBoxCredit.setSelected( false );


		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {

						AllSalesManipulation.data.clear();
						AllSalesManipulation.data = POSHibernation.findAllPOSesObservableListRefreshed();

						return null;
					}
				};
			}
		};

		service.start();


		AuditHistory auditHistory = AuditHistoryHibernation.auditValues( " a sale checkout made ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryData.theData.add(auditHistory);
		//warnUserOnStockRunningLow( list );

	}

	// TODO this has some issues
	private void warnUserOnStockRunningLow( ObservableList < POS > list ) {

		StockHibernation.findAllStockObservableListRefreshed();

		Map < BigDecimal, Stock > mapStock = StockHibernation.mapOfStockToThierItemIDs();

		StockWarningPointHibernation.findAllStockWarningPointsObservableListRefreshed();

		Map < BigDecimal, StockWarningPoint > map = StockWarningPointHibernation
				.itemIDsMappedToThierStockWarningPoints();

		new StageForAlerts();

		list.parallelStream().forEachOrdered( e -> {

			if (

					map.get( e.getItemId().getId() ).getQuantityLimit().doubleValue() >= mapStock.get( e.getItemId().getId() )
							.getItemQuantity().doubleValue()

			) {

				StageForAlerts.inform( "alert", e.getItemId().getItemName().concat( " " )
						.concat(e.getItemId().getPackageVolume().toString())
						.concat( e.getItemId().getUnitOfMeasurement() ).concat( " is running low in quantity" ) );

			}

		} );

	}

	private void print( Node node ) {
		// Create a printer job for the default printer
		PrinterJob job = PrinterJob.createPrinterJob();

		if ( job != null ) {

			job.getJobSettings().setPrintQuality(PrintQuality.HIGH);
			job.getJobSettings().setPrintResolution(job.getPrinter().getPrinterAttributes().getDefaultPrintResolution());

			PrinterService printerService = new PrinterService();

			// where the printing takes place
			printerService.printString(job.getPrinter().getName(), PrinterService.getBody());

			job.endJob();

		}
	}

	private BigDecimal calculateBalanceToBePaidByACreditCustomer() {

		BigDecimal bal;

		if ( checkBoxCredit.isSelected() ) {

			BigDecimal total;
			total = new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) );
			BigDecimal amountPaid;
			amountPaid = new BigDecimal(
					NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) );

			bal = total.subtract(amountPaid);

			if ( ( total.doubleValue() == amountPaid.doubleValue() )
					|| amountPaid.doubleValue() > total.doubleValue() ) {

				checkBoxCredit.setSelected( false );
				bal = BigDecimal.ZERO;

			}

		}

		else {

			BigDecimal total = new BigDecimal( NumberFormatting.testNumberCorrectness( labelTotalCostText.getText() ) );
			BigDecimal amountPaid = new BigDecimal(
					NumberFormatting.testNumberCorrectness( fieldAmountPaid.getText() ) );
			BigDecimal balance = total.subtract( amountPaid );

			bal = balance;

			if ( ( total.doubleValue() == amountPaid.doubleValue() )
					|| amountPaid.doubleValue() > total.doubleValue() ) {

				checkBoxCredit.setSelected( false );
				bal = BigDecimal.ZERO;

			}

		}

		labelBalanceToBePaidByCustomerText.setText( NumberFormatting.formatToEnglish( bal.toString() ) );

		return bal;

	}

	private ComboBox < MediumOfPayment > comboBoxMediumOfPayment;

	private void populateComboBoxMediumOfPayment() {

		setConverterComboboxMediumOfPayment(comboBoxMediumOfPayment);
	}

	public static void setConverterComboboxMediumOfPayment(ComboBox<MediumOfPayment> comboBoxMediumOfPayment) {
		comboBoxMediumOfPayment.setConverter(new StringConverter< MediumOfPayment >() {
			@Override
			public String toString( MediumOfPayment object ) {
				return object.getNameOfMediumOfPayment().concat( "-" ).concat( object.getSpecificationOrDescription() );
			}

			@Override
			public MediumOfPayment fromString( String string ) {
				return comboBoxMediumOfPayment.getItems().stream()
						.filter( e -> e.getNameOfMediumOfPayment().concat( "-" )
								.concat( e.getSpecificationOrDescription() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );
	}

	private MediumOfPayment mediumOfPayment;

	private void getMediumOfPayment() {
		comboBoxMediumOfPayment.setOnAction( e -> {
			mediumOfPayment = comboBoxMediumOfPayment.getSelectionModel().getSelectedItem();
		} );
	}

}
