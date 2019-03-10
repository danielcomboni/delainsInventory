package com.delains.ui.sales;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.pos.POSDAO;
import com.delains.dao.pos.POSHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.stock.StockDAO;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.stock.StockWarningPointHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.pos.POS;
import com.delains.model.pricing.Pricing;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockWarningPoint;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ButtonRaisedType;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.test.AutoCompleteFieldObtainResult;
import com.delains.ui.test.AutocompletionlTextField;
import com.delains.ui.test.ComboBoxAutoComplete;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Insets;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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

		populateComboBoxItems();
		populateComboBoxCustomers();
		populateComboBoxMediumOfPayment();
		getMediumOfPayment();

		posToFields();

		comboBoxItem.setOnAction( e -> captureBarcodeFromComboBox() );

		calculateChange();

		autoComplete();
		autoCompleteTextField();

	}

	private void autoComplete() {
		comboBoxItem.setTooltip( new Tooltip() );
		new ComboBoxAutoComplete < Item >( comboBoxItem );
	}

	private void autoCompleteTextField() {
		AutocompletionlTextField textField = new AutocompletionlTextField();

		fieldSearch = textField;

		ObservableList < String > strings = FXCollections.observableArrayList();

		ObservableList < Item > items = ItemHibernation.findAllItemsObservableList();

		for ( Item item : items ) {
			strings.add( item.getItemName().concat( " - " ).concat( item.getItemDescription() ).concat( " - " )
					.concat( item.getPackageVolume().toString() ) );
		}

		textField.getEntries().addAll( strings );

	}

	private Label labelItem;
	private TextField fieldItem;

	private Label labelQuantity;
	private TextField fieldQuantity;

	private Label labelItemAlternative;
	private ComboBox < Item > comboBoxItem;

	private TextField fieldSearch;

	private HBox hBoxTop;
	private GridPane gridPaneTop;

	private void buildTop() {

		this.hBoxTop = new HBox();

		this.gridPaneTop = new GridPane();
		this.gridPaneTop.setHgap( 20 );
		this.gridPaneTop.setVgap( 10 );
		this.gridPaneTop.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelItem = new Label( "Item:" );
		addToGridPaneTop( labelItem, 0, 0 );

		this.fieldItem = new TextField();
		this.fieldItem.setPromptText( "scanner" );
		ComponentWidth.setWidthOfTextField( fieldItem, 200 );
		addToGridPaneTop( fieldItem, 1, 0 );

		this.labelItemAlternative = new Label( "Item alternative:" );
		addToGridPaneTop( this.labelItemAlternative, 2, 0 );

		this.comboBoxItem = new ComboBox <>();

		addToGridPaneTop( this.comboBoxItem, 3, 0 );

		this.labelQuantity = new Label( "Quantity" );
		addToGridPaneTop( labelQuantity, 4, 0 );

		this.fieldQuantity = new TextField();
		fieldQuantity.setPromptText( "quantity" );
		ComponentWidth.setWidthOfTextField( fieldQuantity, 200 );
		addToGridPaneTop( fieldQuantity, 5, 0 );

		this.fieldSearch = new TextField();
		fieldSearch.setPromptText( "search item" );

		autoCompleteTextField();

		addToGridPaneTop( fieldSearch, 5, 1 );

		fieldSearch.setOnKeyPressed( e -> {

			if ( e.getCode() == KeyCode.ENTER ) {

				System.out.println( "stat: " + AutoCompleteFieldObtainResult.getFieldResultObtained() );

				// String barcode = fieldItem.getText();
				// getItemByBarcode( barcode );
				// fieldItem.clear();

			}
		} );

		JFXButton btn = new JFXButton( "sea" );
		addToGridPaneTop( btn, 5, 2 );
		btn.setOnAction( e -> {
			System.out.println( "se btn: " + fieldSearch.getText() );
		} );

		this.hBoxTop.getChildren().add( this.gridPaneTop );

		this.setTop( this.hBoxTop );

	}

	private void addToGridPaneTop( Node node, int columnIndex, int rowIndex ) {
		this.gridPaneTop.add( node, columnIndex, rowIndex );
	}

	private TableView < POS > tableView;
	private TableColumn < POS, String > colItem;
	private TableColumn < POS, String > colQuantity;
	private TableColumn < POS, String > colUnitCost;
	private TableColumn < POS, String > colTotalCost;
	private TableColumn < POS, Void > colIncrease;
	private TableColumn < POS, Void > colReduce;
	private TableColumn < POS, Void > colRemove;

	private void buildPOSTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();

						String packVol = pos.getItemId().getPackageVolume().toString().concat( " " )
								.concat( pos.getItemId().getUnitOfMeasurement() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( pos.getItemId().getItemName().concat( " " ).concat( packVol ) );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
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

		colQuantity = new TableColumn <>( "Quantity" );
		colQuantity.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();

						String pack = pos.getItemId().getPackageName();

						if ( pos.getQuantity().doubleValue() > 1 ) {
							pack = pack.concat( "(s/ies)" );
						}

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( pos.getQuantity().toString() )
								.concat( " " ).concat( pack ) );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colQuantity );

		colUnitCost = new TableColumn <>( "Unit cost" );
		colUnitCost.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty
								.setValue( NumberFormatting.formatToEnglish( pos.getPricing().getPrice().toString() ) );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colUnitCost );

		colTotalCost = new TableColumn <>( "total cost" );
		colTotalCost.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( pos.getCost().toString() ) );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colTotalCost );

		colIncrease = new TableColumn <>( "Increase" );
		colIncrease.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.PLUS ) );
		colIncrease.setCellFactory( new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

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
		tableView.getColumns().add( colIncrease );

		colReduce = new TableColumn <>( "Reduce" );
		colReduce.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.MINUS ) );
		colReduce.setCellFactory( new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

			@Override
			public TableCell < POS, Void > call( TableColumn < POS, Void > param ) {

				final TableCell < POS, Void > cell = new TableCell < POS, Void >() {

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
		tableView.getColumns().add( colReduce );

		colRemove = new TableColumn <>( "Remove" );
		colRemove.setGraphic( new FontAwesomeIconView( FontAwesomeIcon.REMOVE ) );
		colRemove.setCellFactory( new Callback < TableColumn < POS, Void >, TableCell < POS, Void > >() {

			@Override
			public TableCell < POS, Void > call( TableColumn < POS, Void > param ) {

				final TableCell < POS, Void > cell = new TableCell < POS, Void >() {

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

				return cell;
			}
		} );
		tableView.getColumns().add( colRemove );

		VBox box = new VBox();
		box.getChildren().add( tableView );

		this.setCenter( box );

	}

	private void addToGridPaneBottomLeft( Node node, int columnIndex, int rowIndex ) {
		this.gridPaneBottomLeft.add( node, columnIndex, rowIndex );
	}

	private GridPane gridPaneBottomLeft;
	private VBox vBoxBottomLeft;
	private HBox hBoxBottom;

	private JFXCheckBox checkBoxCredit;

	private Label labelAmountPaid;
	private TextField fieldAmountPaid;

	private Label labelTotalCost;
	private Label labelTotalCostText;

	private Label labelBalance;
	private Label labelBalanceText;

	private Label labelCustomerName;
	private ComboBox < Customer > comboBoxCustomerName;

	private Label labelBalanceToBePaidByCustomer;
	private Label labelBalanceToBePaidByCustomerText;

	private JFXButton buttonCheckOut;
	private JFXButton buttonCheckOutWithoutPrinting;
	private JFXButton buttonCancel;

	private void buildBottom() {

		this.hBoxBottom = new HBox();
		this.hBoxBottom.setFillHeight( true );

		vBoxBottomLeft = new VBox();
		this.vBoxBottomLeft.setPrefHeight( 275 );
		this.vBoxBottomLeft.setPrefWidth( 1000 );

		gridPaneBottomLeft = new GridPane();

		this.gridPaneBottomLeft = new GridPane();
		this.gridPaneBottomLeft.setHgap( 20 );
		this.gridPaneBottomLeft.setVgap( 10 );
		this.gridPaneBottomLeft.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.checkBoxCredit = new JFXCheckBox( "credit?" );
		addToGridPaneBottomLeft( checkBoxCredit, 1, 0 );

		this.labelCustomerName = new Label( "customer:" );
		addToGridPaneBottomLeft( labelCustomerName, 0, 1 );

		this.comboBoxCustomerName = new ComboBox <>();
		addToGridPaneBottomLeft( comboBoxCustomerName, 1, 1 );

		this.labelAmountPaid = new Label( "amount paid:" );
		addToGridPaneBottomLeft( labelAmountPaid, 0, 2 );

		this.fieldAmountPaid = new TextField();
		ComponentWidth.setWidthOfTextField( fieldAmountPaid, 400 );
		addToGridPaneBottomLeft( fieldAmountPaid, 1, 2 );

		this.labelTotalCost = new Label( "Total cost: " );
		addToGridPaneBottomLeft( labelTotalCost, 0, 3 );

		this.labelTotalCostText = new Label();
		addToGridPaneBottomLeft( labelTotalCostText, 1, 3 );

		this.labelBalance = new Label( "change:" );
		addToGridPaneBottomLeft( this.labelBalance, 0, 4 );

		this.labelBalanceText = new Label();
		addToGridPaneBottomLeft( this.labelBalanceText, 1, 4 );

		this.labelBalanceToBePaidByCustomer = new Label( "balance:" );
		addToGridPaneBottomLeft( labelBalanceToBePaidByCustomer, 0, 5 );

		this.labelBalanceToBePaidByCustomerText = new Label();
		addToGridPaneBottomLeft( labelBalanceToBePaidByCustomerText, 1, 5 );

		comboBoxMediumOfPayment = new ComboBox <>();
		addToGridPaneBottomLeft( new Label( "payment medium:" ), 0, 6 );
		addToGridPaneBottomLeft( comboBoxMediumOfPayment, 1, 6 );

		HBox hBox = new HBox( 10 );

		this.buttonCheckOut = new JFXButton( "check out (no print) ", new FontAwesomeIconView( FontAwesomeIcon.SAVE ) );
		new ButtonRaisedType( buttonCheckOut );
		hBox.getChildren().add( buttonCheckOut );

		this.buttonCheckOutWithoutPrinting = new JFXButton( "check out (print)",
				new FontAwesomeIconView( FontAwesomeIcon.PRINT ) );
		new ButtonRaisedType( buttonCheckOut );
		hBox.getChildren().add( buttonCheckOutWithoutPrinting );

		this.buttonCancel = new JFXButton( "cancel", new FontAwesomeIconView( FontAwesomeIcon.REMOVE ) );
		new ButtonRaisedType( buttonCancel );
		hBox.getChildren().add( buttonCancel );
		addToGridPaneBottomLeft( hBox, 1, 7 );

		this.vBoxBottomLeft.getChildren().add( gridPaneBottomLeft );

		this.hBoxBottom.getChildren().add( vBoxBottomLeft );

		this.setBottom( this.hBoxBottom );

		buttonCancel.setOnAction( e -> {
			cancel();
		} );

		buttonCheckOut.setOnAction( e -> {
			checkOut();
		} );

		buttonCheckOutWithoutPrinting.setOnAction( e -> {
			checkOutWithPrinting();
		} );

		buttonCheckOut.setOnKeyPressed( e -> {
			if ( e.getCode() == KeyCode.ENTER ) {
				checkOut();
			}
		} );

	}

	public void populateComboBoxItems() {

		comboBoxItem.getItems().clear();

		if ( comboBoxItem.getItems().isEmpty() ) {
			comboBoxItem.setItems( ItemHibernation.findAllItemsObservableListRefreshed() );
		} else {
			comboBoxItem.getItems().clear();
			comboBoxItem.getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );
		}

		comboBoxItem.setConverter( new StringConverter < Item >() {

			@Override
			public String toString( Item object ) {

				String type = object.getItemName().concat( " " ).concat( object.getPackageVolume().toString() )
						.concat( "" ).concat( object.getUnitOfMeasurement() );

				return type;
			}

			@Override
			public Item fromString( String string ) {

				Item type = comboBoxItem.getItems().stream()
						.filter( e -> e.getItemName().concat( " " ).concat( e.getPackageVolume().toString() )
								.concat( "" ).concat( e.getUnitOfMeasurement() ).equals( string ) )
						.findFirst().orElse( null );
				return type;
			}
		} );

	}

	public void populateComboBoxCustomers() {

		comboBoxCustomerName.getItems().clear();

		if ( comboBoxCustomerName.getItems().isEmpty() ) {
			comboBoxCustomerName.setItems( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		} else {
			comboBoxCustomerName.getItems().clear();
			comboBoxCustomerName.getItems().addAll( CustomerHibernation.findAllCustomersObservableListRefreshed() );
		}

		comboBoxCustomerName.setConverter( new StringConverter < Customer >() {

			@Override
			public String toString( Customer object ) {

				String type = object.getCustomerName();

				return type;
			}

			@Override
			public Customer fromString( String string ) {

				Customer type = comboBoxCustomerName.getItems().stream()
						.filter( e -> e.getCustomerName().equals( string ) ).findFirst().orElse( null );
				return type;
			}
		} );

	}

	private void posToFields() {

		// if ( fieldItem.isFocused() ) {
		captureBarcode();
		// }

		// if ( comboBoxItem.isFocused() ) {

		// }

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
		// comboBoxItem.getSelectionModel().select( item );
		comboBoxItem.getSelectionModel().clearSelection();

		Pricing pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( item.getId() );

		if ( pricing == null ) {

			PricingHibernation.findAllPricingsObservableListRefresh();
			pricing = PricingHibernation.mappingItemIDsAsIDOfPrice().get( item.getId() );

			if ( pricing == null ) {
				new StageForAlerts();
				StageForAlerts.inform( "alert", "this item's price is not yet set" );
				return;
			}

		}

		pos.setPricing( pricing );

		if ( POSDAO.getPosValuesForTheTable() == null ) {

			if ( fieldQuantity.getText().trim().isEmpty() ) {
				pos.setQuantity( BigDecimal.ONE );
			} else {
				getQuantityOfAnItemInField();
				pos.setQuantity( this.quantity );
			}

			pos.setCost( pos.getQuantity().multiply( pos.getPricing().getPrice() ) );

			pos.setChange( calculateChangeToReturn() );
			pos.setBalanceToBePaidByCustomer( calculateBalanceToBePaidByACreditCustomer() );

			ObservableMap < Item, POS > poses = FXCollections.observableHashMap();

			poses.put( item, pos );

			POSDAO.setPosValuesForTheTable( poses );

		}

		else if ( POSDAO.getPosValuesForTheTable() != null ) {

			if ( POSDAO.getPosValuesForTheTable().containsKey( item ) ) {

				POS pos2 = null;

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

		} else {

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

		String bar = null;
		Item item = comboBoxItem.getSelectionModel().getSelectedItem();
		bar = item.getBarcode();
		System.out.println( "codfe: " + bar );
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

	BigDecimal quantity = BigDecimal.ZERO;

	private void getQuantityOfAnItemInField() {
		new StageForAlerts();
		String qtyStr = NumberFormatting.testNumberCorrectness( fieldQuantity.getText().trim() );
		if ( NumberFormatting.isNumberCorrect() == false ) {
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
				String amountPaidString = fieldAmountPaid.getText();
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
		// System.out.println( "changel: " + change );
		// System.out.println( "bal: " + bal );
		// System.out.println( "" );

		System.out.println( "map ttt:..." + POSDAO.getPosValuesForTheTable() );

		Customer customer = comboBoxCustomerName.getSelectionModel().getSelectedItem();

		ObservableMap < Item, POS > map = POSCheckOut.breakingDownPosTableForBatchEnchancement(
				POSDAO.getPosValuesForTheTable(), amountPaid, POSDAO.totalCostCalculated(), change, bal, credit,
				customer );

		POSHibernation.newPOS( map );

		AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

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

		// print( posPrintReceipt );

		AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

		checkBoxCredit.setSelected( false );

		AuditHistoryHibernation.auditValues( " a sale checkout made ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

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
		// System.out.println( "changel: " + change );
		// System.out.println( "bal: " + bal );
		// System.out.println( "" );

		System.out.println( "map ttt:..." + POSDAO.getPosValuesForTheTable() );

		Customer customer = comboBoxCustomerName.getSelectionModel().getSelectedItem();

		ObservableMap < Item, POS > map = POSCheckOut.breakingDownPosTableForBatchEnchancement(
				POSDAO.getPosValuesForTheTable(), amountPaid, POSDAO.totalCostCalculated(), change, bal, credit,
				customer );

		POSHibernation.newPOS( map );

		AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

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

		AllSalesManipulation.populateAllSalesTableRefresh( AllSalesFrame.getTableView() );

		checkBoxCredit.setSelected( false );

		AuditHistoryHibernation.auditValues( " a sale checkout made ", UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

		warnUserOnStockRunningLow( list );

	}

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
						.concat( e.getItemId().getPackageVolume().toPlainString() )
						.concat( e.getItemId().getUnitOfMeasurement() ).concat( " is running low in quantity" ) );

			}

		} );

	}

	private void print( Node node ) {
		// Create a printer job for the default printer
		PrinterJob job = PrinterJob.createPrinterJob();

		if ( job != null ) {

			// Print the node
			boolean printed = job.printPage( node );

			if ( printed ) {
				// End the printer job
				job.endJob();
			} else {
				System.out.println( "not printed" );
			}
		} else {
			// Write Error Message
			// jobStatus.setText("Could not create a printer job.");
		}
	}

	private BigDecimal calculateBalanceToBePaidByACreditCustomer() {

		BigDecimal bal = BigDecimal.ZERO;

		if ( checkBoxCredit.isSelected() ) {

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
		comboBoxMediumOfPayment.getItems().clear();
		comboBoxMediumOfPayment.getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );
		comboBoxMediumOfPayment.setConverter( new StringConverter < MediumOfPayment >() {
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

	private MediumOfPayment mediumOfPayment = null;

	private MediumOfPayment getMediumOfPayment() {
		comboBoxMediumOfPayment.setOnAction( e -> {
			mediumOfPayment = comboBoxMediumOfPayment.getSelectionModel().getSelectedItem();
		} );
		return mediumOfPayment;
	}

}
