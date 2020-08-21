package com.delains.ui.purchases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.history.AuditHistory;
import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.pos.ItemPriceBarcode;
import com.delains.model.purchases.Purchase;
import com.delains.model.stock.Stock;
import com.delains.model.suppliers.Supplier;
import com.delains.ui.GeneralDialog;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.item.ItemData;
import com.delains.ui.payments.MediumOfPaymentData;
import com.delains.ui.suppliers.SupplierData;
import com.delains.ui.test.AutoCompleteFieldObtainResult;
import com.delains.ui.test.ComboBoxAutoComplete;
import com.delains.ui.test.auto.AutoCompleteTextField;
import com.delains.utilities.CurrentTimestamp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class PurchaseFrame extends BorderPane {

	private DatePicker datePicker;

	private TextField fieldItemAlternative;

	private ComboBox < Item > comboBoxItems;

	private TextField fieldSearch;

	private TextField fieldQuantity;

	private TextField fieldUnitPrice;

	private Label labelTotalCostText;

	private JFXCheckBox checkBoxCredit;

	private ComboBox < Supplier > comboBoxSuppliers;

	private TextField fieldAmountPaid;


	private Label labelBalanceText;

	private TextField fieldDiscountReceived;

	private GridPane gridPane;

	private JFXButton buttonSave;

	private StageForAllPopUps stageForAllPopUps;

	public PurchaseFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( PurchaseFrame.class.getResource( "purchase.css" ).toExternalForm() );

		buildPurchasesframeComponents();

		buildPurchasesTable();
		buildTopOfThisFrame();

		Refresh.setRefreshingDeterminant( 0 );
		tableView.setItems(PurchaseManipulation.data);

		fieldQuantity.focusedProperty().addListener( ( obs, oldVal, newVal ) -> {

			// unfocused
			if ( oldVal ) {

				if ( !fieldUnitPrice.getText().trim().isEmpty() && !fieldQuantity.getText().trim().isEmpty() ) {

					String unitCostText = fieldUnitPrice.getText().trim();
					fieldUnitPrice.setText( unitCostText );

					String qtyText = fieldQuantity.getText().trim();
					fieldQuantity.setText( qtyText );

					getTotalCost();

					calculateBalance();

				}

			}

			// focused
			else if ( newVal ) {

				if ( !fieldUnitPrice.getText().trim().isEmpty() && !fieldQuantity.getText().trim().isEmpty() ) {

					String unitCostText = fieldUnitPrice.getText().trim();
					fieldUnitPrice.setText( unitCostText );

					String qtyText = fieldQuantity.getText().trim();
					fieldQuantity.setText( qtyText );

					getTotalCost();

					calculateBalance();

				}

			}

		} );

		/*
		 *
		 * when unit price field is focused or unfocused
		 *
		 */
		fieldUnitPrice.focusedProperty().addListener( ( obs, oldVal, newVal ) -> {

			// unfocused
			if ( oldVal ) {

				if ( !fieldUnitPrice.getText().trim().isEmpty() && !fieldQuantity.getText().trim().isEmpty() ) {

					String unitCostText = fieldUnitPrice.getText().trim();
					fieldUnitPrice.setText( unitCostText );

					String qtyText = fieldQuantity.getText().trim();
					fieldQuantity.setText( qtyText );

					getTotalCost();

					calculateBalance();

				}

			}

			// focus
			else if ( newVal ) {

				if ( !fieldUnitPrice.getText().trim().isEmpty() && !fieldQuantity.getText().trim().isEmpty() ) {

					String unitCostText = fieldUnitPrice.getText().trim();
					fieldUnitPrice.setText( unitCostText );

					String qtyText = fieldQuantity.getText().trim();
					fieldQuantity.setText( qtyText );

					getTotalCost();

					calculateBalance();

				}

			}

		} );

		fieldAmountPaid.focusedProperty().addListener( ( obs, oldVal, newVal ) -> {

			if ( oldVal ) {

				calculateBalance();

			}

			if ( newVal ) {

				calculateBalance();

			}

		} );

		fieldDiscountReceived.focusedProperty().addListener( ( obs, oldVal, newVal ) -> {

			if ( oldVal ) {

				calculateBalance();

			}

			if ( newVal ) {

				calculateBalance();

			}

		} );

		buttonSave.setOnAction( e -> {
			if ( buttonSave.getText().equals( "save changes" ) ) {
//				completeUpdate();
			} else {
				addNewPurchase();
			}

		} );

		scanningItemBarcodeForPurchase();

		itemObtainedByClickingTable();

		onComboBoxItemSelected();
		onComboBoxSupplierSelected();

		getMediumOfPayment();

		AutoCompleteFieldObtainResult.refreshItemsToSearch();

	}

	private HBox hBox;
	private void buildPurchasesframeComponents() {

		hBox = new HBox();


		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelDate = new Label("Date");
		addToGrid(labelDate, 0, 0 );

		this.datePicker = new DatePicker();
		datePicker.setEditable( false );
		addToGrid( datePicker, 1, 0 );

		Label labelItemAlternative = new Label("Item (scanner)");
		this.addToGrid(labelItemAlternative, 0, 1 );

		this.fieldItemAlternative = new TextField();
		this.addToGrid( this.fieldItemAlternative, 1, 1 );

		Label labelItem = new Label("item");
		addToGrid(labelItem, 0, 2 );

		comboBoxItems = new ComboBox <>();
		comboBoxItems.setItems(ItemData.data);
		//addToGrid( comboBoxItems, 1, 2 );

		fieldSearch = new TextField();
		// VERY IMPORTANT
		// this method has to be invoked immediately after initializing the field and before adding the field to any node/pane
		autoCompleteTextField();




		addToGrid(fieldSearch,1,2);
		fieldSearch.setPromptText("Search item");
		//GridPane.setColumnSpan(fieldSearch, 5);


		Label labelQuantity = new Label("quantity");
		addToGrid(labelQuantity, 0, 4 );

		this.fieldQuantity = new TextField();
		ComponentWidth.setWidthOfTextField( fieldQuantity, 400 );

		addToGrid( fieldQuantity, 1, 4 );


		Label labelUnitPrice = new Label("unit cost");
		addToGrid(labelUnitPrice, 0, 5 );

		this.fieldUnitPrice = new TextField();
		addToGrid( fieldUnitPrice, 1, 5 );

		Label labelTotalCost = new Label("total cost:");
		addToGrid(labelTotalCost, 0, 6 );

		this.labelTotalCostText = new Label();
		addToGrid( labelTotalCostText, 1, 6 );

		this.checkBoxCredit = new JFXCheckBox( "credit?" );
		addToGrid( checkBoxCredit, 1, 7 );

		Label labelSupplierName = new Label("supplier name:");
		addToGrid(labelSupplierName, 0, 8 );

		comboBoxSuppliers = new ComboBox <>();
		comboBoxSuppliers.setItems(SupplierData.data);
		SupplierData.setConverter(comboBoxSuppliers);
		addToGrid( comboBoxSuppliers, 1, 8 );

		Label labelAmountPaid = new Label("amount cleared:");

		addToGrid(labelAmountPaid, 0, 9 );

		this.fieldAmountPaid = new TextField();
		addToGrid( fieldAmountPaid, 1, 9);

		Label labelDiscountReceived = new Label("discount received");
		addToGrid(labelDiscountReceived, 0, 10 );

		this.fieldDiscountReceived = new TextField();
		addToGrid( fieldDiscountReceived, 1, 10);

		Label labelBalance = new Label("balance:");
		addToGrid(labelBalance, 0, 11 );

		this.labelBalanceText = new Label();
		addToGrid( labelBalanceText, 1, 11 );

		comboBoxMediumOfPayment = new ComboBox <>();
		comboBoxMediumOfPayment.setItems(MediumOfPaymentData.data);
		MediumOfPaymentData.setConverter(comboBoxMediumOfPayment);
		addToGrid( new Label( "payment medium" ), 0, 12 );
		addToGrid( comboBoxMediumOfPayment, 1, 12 );

		HBox hBoxButtons = new HBox(10);
		hBoxButtons.setPadding( new Insets( 10 ) );
		this.buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		JFXButton buttonCancel = new JFXButton("cancel");
//		hBoxButtons.getChildren().add(buttonCancel);

		this.gridPane.add(hBoxButtons, 1, 13 );

		hBox.getChildren().add( this.gridPane );

		/*
		 *
		 * listening
		 *
		 */

		stageForAllPopUps = new StageForAllPopUps(hBox, "add new purchase" );
/*
		this.buttonSave.setOnAction( e -> {

		} );*/

		buttonCancel.setOnAction(e -> stageForAllPopUps.close());

		autoComplete();


	}

	private void autoComplete() {
		comboBoxItems.setTooltip( new Tooltip() );
		new ComboBoxAutoComplete<>(comboBoxItems);
	}

	private void addToGrid( Node node, int col, int row ) {
		this.gridPane.add( node, col, row );
	}

	void newPurchase() {

		GeneralDialog.showDialog("make a new purchase","Make a new purchase",hBox);

	}

	/*
	 *
	 * building table
	 *
	 **/

	private static TableView < Purchase > tableView;

	public static TableView < Purchase > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Purchase > tableView ) {
		PurchaseFrame.tableView = tableView;
	}

	private TableColumn < Purchase, String > colItem;

	private void buildPurchasesTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		TableColumn<Purchase, String> colDate = new TableColumn<>("Date");
		colDate.setCellValueFactory( new PropertyValueFactory <>( "date" ) );
		colDate.setCellFactory(tc -> {

			TableCell < Purchase, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		tableView.getColumns().add(colDate);

		colItem = new TableColumn <>( "Item" );
		colItem.setCellFactory( tc -> {

			TableCell < Purchase, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		colItem.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					SimpleStringProperty simpleStringProperty;

					StringProperty property;

					if ( aPurchase != null && aPurchase.getItemId() != null ) {

						simpleStringProperty = new SimpleStringProperty( aPurchase.getItemId().getItemName()



								+ " (" +  aPurchase.getItemId().getPackageVolume() +"/"

								+ aPurchase.getItemId().getPackageName() +


								")"

						);

						property = simpleStringProperty;

					} else {
						property = null;
					}

					return property;
				});
		tableView.getColumns().add( colItem );

		TableColumn<Purchase, String> colQuantity = new TableColumn<>("Quantity");
		colQuantity.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getQuantityPurchased();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() <= 0 )
						simpleStringProperty.setValue(null);

					else
						simpleStringProperty.setValue(qtyStr);

					return simpleStringProperty;
				});
		tableView.getColumns().add(colQuantity);

		TableColumn<Purchase, String> colPrice = new TableColumn<>("Cost");
		colPrice.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getPrice();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() <= 0 )
						simpleStringProperty.setValue(null);

					else
						simpleStringProperty.setValue(qtyStr);

					return simpleStringProperty;
				});

		tableView.getColumns().add(colPrice);

		TableColumn<Purchase, String> colTotalCost = new TableColumn<>("Total\ncost");
		colTotalCost.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getTotalCost();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() <= 0 )
						simpleStringProperty.setValue(null);

					else
						simpleStringProperty.setValue(qtyStr);

					return simpleStringProperty;
				});

		tableView.getColumns().add(colTotalCost);

		TableColumn<Purchase, Boolean> colCredit = new TableColumn<>("Credit?");
		colCredit.setCellFactory(e -> new CheckBoxTableCell <>() );
		colCredit.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					return new SimpleBooleanProperty( aPurchase.isCredit() );
				});
		tableView.getColumns().add(colCredit);

		TableColumn<Purchase, String> colSupplier = new TableColumn<>("Supplier");
		colSupplier.setCellFactory(tc -> {
			TableCell < Purchase, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		colSupplier.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					StringProperty property;

					if ( aPurchase.getSupplierId() != null ) {

						simpleStringProperty.setValue( aPurchase.getSupplierId().getSupplierName() );

						property = simpleStringProperty;

					} else {

						simpleStringProperty.setValue( "" );

						property = simpleStringProperty;

					}

					return property;
				});
		tableView.getColumns().add(colSupplier);

		TableColumn<Purchase, String> colAmountPaid = new TableColumn<>("Amount\nCleared");
		colAmountPaid.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getAmountPaid();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() <= 0 )
						simpleStringProperty.setValue(null);
					else
						simpleStringProperty.setValue(qtyStr);

					return simpleStringProperty;
				});

		tableView.getColumns().add(colAmountPaid);

		TableColumn<Purchase, String> colBalanceToBeCleared = new TableColumn<>("Balance");
		colBalanceToBeCleared.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getBalanceToBeCleared();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() == 0 ) {
						simpleStringProperty.setValue( null );
					} else {
						simpleStringProperty.setValue( qtyStr );
					}

					return simpleStringProperty;
				});

		tableView.getColumns().add(colBalanceToBeCleared);

		TableColumn<Purchase, String> colDiscountReceived = new TableColumn<>("Discount\nreceived");
		colDiscountReceived.setCellValueFactory(param -> {

					Purchase aPurchase = param.getValue();

					BigDecimal qty = aPurchase.getDiscountReceived();

					String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

					if ( qty.doubleValue() <= 0 )
						simpleStringProperty.setValue(null);

					else
						simpleStringProperty.setValue(qtyStr);

					return simpleStringProperty;
				});

		tableView.getColumns().add(colDiscountReceived);

		TableColumn<Purchase, Void> columnDelete = new TableColumn<>("Edit");
		columnDelete.setCellFactory(param -> new TableCell<Purchase,Void>(){
			JFXButton button = new JFXButton("Edit");

			{
				button.setAlignment(Pos.CENTER);

				button.setOnAction(e -> {

					TableRow row = this.getTableRow();

					Purchase purchase = tableView.getItems().get(row.getIndex());

					populateForUpdate(purchase);

					buttonSave.setText("save changes");

						buttonSave.setOnAction(ee -> {

							completeUpdate(purchase);


						});

						GeneralDialog.showDialog("","makes changes and save", gridPane);

				});
			}

			@Override
			protected void updateItem(Void item, boolean empty) {
				super.updateItem(item, empty);
				if ( empty ) {
					setGraphic( null );
				} else {
					setGraphic( button );
				}
			}
		});

		tableView.getColumns().add(columnDelete);

		/*
		 *
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 *
		 */

		this.setCenter( tableView );

	}

	private BigDecimal quantityAdd = BigDecimal.ZERO;

	private void setQuantity() {

		BigDecimal quantity = BigDecimal.ZERO;
		String quantityStr;

		if ( fieldQuantity.getText().trim().isEmpty() ) {

			// StageForAlerts.inform("alert", "please specify the quantity");
			quantityAdd = BigDecimal.ZERO;

			// return;

		} else {

			String qtyStr = fieldQuantity.getText();

			quantityStr = NumberFormatting.testNumberCorrectness( qtyStr );

			if (!NumberFormatting.isNumberCorrect()) {

				StageForAlerts.inform( "alert", "wrong number format for quantity" );

				return;

			} else {

				quantity = new BigDecimal( quantityStr );

			}

		}

		quantityAdd = quantity;

	}

	private BigDecimal getQuantity() {
		setQuantity();
		return quantityAdd;
	}

	private BigDecimal unitCostAdd = BigDecimal.ZERO;

	private void setUnitCost() {

		String unitCostStr;
		BigDecimal unitCost = BigDecimal.ZERO;

		if ( fieldUnitPrice.getText().trim().isEmpty() ) {

			// StageForAlerts.inform("alert", "please specify the unitCost");
			unitCostAdd = BigDecimal.ZERO;
			// return;

		} else {

			String unitCostString = fieldUnitPrice.getText();
			unitCostStr = NumberFormatting.testNumberCorrectness( unitCostString );
			if (!NumberFormatting.isNumberCorrect()) {

				StageForAlerts.inform( "alert", "wrong number format for unit cost" );

				return;

			} else {

				unitCost = new BigDecimal( unitCostStr );

			}

		}

		unitCostAdd = unitCost;

	}

	private BigDecimal getUnitCost() {
		setUnitCost();
		return unitCostAdd;
	}

	private BigDecimal getTotalCost() {
		BigDecimal totalCost = getQuantity().multiply( getUnitCost() );

		this.labelTotalCostText.setText( NumberFormatting.formatToEnglish( totalCost.toString() ) );

		return totalCost;
	}

	private BigDecimal discountReceivedAdd = BigDecimal.ZERO;

	private void setDiscount() {
		String discountReceivedString;
		String discountStr;

		if ( fieldDiscountReceived.getText().trim().isEmpty() ) {
			discountReceivedAdd = BigDecimal.ZERO;
		}

		else {

			discountReceivedString = fieldDiscountReceived.getText();
			discountStr = NumberFormatting.testNumberCorrectness( discountReceivedString );

			if (!NumberFormatting.isNumberCorrect()) {
				 StageForAlerts.inform("alert", "wrong number format for discount received");
			} else {
				discountReceivedAdd = new BigDecimal( discountStr );
			}

		}

	}

	private BigDecimal balanceAdd = BigDecimal.ZERO;
	private BigDecimal amountPaidAdd = BigDecimal.ZERO;

	private void calculateBalance() {

		new StageForAlerts();

		String amountPaidStr;
		BigDecimal amountPaid = BigDecimal.ZERO;

		/*
		 *
		 * if it is a credit purchase
		 *
		 */
		if ( checkBoxCredit.isSelected() ) {

			setDiscount();

			// the amount paid can be empty
			if ( fieldAmountPaid.getText().trim().isEmpty() ) {

				amountPaidAdd = amountPaid;

				/*
				 * this area is for considering discount given
				 */

				if ( fieldDiscountReceived.getText().trim().isEmpty() ) {

					setDiscount();

					if ( fieldQuantity.getText().trim().isEmpty() || fieldUnitPrice.getText().trim().isEmpty() ) {
						balanceAdd = BigDecimal.ZERO;
					} else {
						balanceAdd = getTotalCost();
					}

				} else {
					if ( fieldQuantity.getText().trim().isEmpty() || fieldUnitPrice.getText().trim().isEmpty() ) {
						balanceAdd = BigDecimal.ZERO;
					} else {

						setDiscount();
						balanceAdd = getTotalCost().subtract( discountReceivedAdd );

					}

				}

			}

			// or some amount can be paid
			else {

				if ( !fieldAmountPaid.getText().trim().isEmpty() ) {

					String amountPaidString = fieldAmountPaid.getText();
					amountPaidStr = NumberFormatting.testNumberCorrectness( amountPaidString );

					if (!NumberFormatting.isNumberCorrect()) {
						StageForAlerts.inform( "alert", "wrong number format for amount paid" );
						return;
					}

					else {

						amountPaid = new BigDecimal( amountPaidStr );

						amountPaidAdd = amountPaid;

						if ( fieldQuantity.getText().trim().isEmpty() || fieldUnitPrice.getText().trim().isEmpty() ) {
							balanceAdd = BigDecimal.ZERO;
						} else {
							balanceAdd = getTotalCost().subtract( discountReceivedAdd ).subtract( amountPaidAdd );
						}

					}

				} else {

					if ( fieldQuantity.getText().trim().isEmpty() || fieldUnitPrice.getText().trim().isEmpty() ) {
						balanceAdd = BigDecimal.ZERO;
					} else {
						balanceAdd = getTotalCost().subtract( discountReceivedAdd ).subtract( amountPaidAdd );
					}

				}

			}

			this.labelBalanceText.setText( NumberFormatting.formatToEnglish( balanceAdd.toString() ) );

		}

		/*
		 *
		 * but if it is not a credit purchase, the amount paid can not be empty (null/0)
		 *
		 */

		else {

			if ( fieldAmountPaid.getText().trim().isEmpty() ) {

				amountPaidAdd = BigDecimal.ZERO;

				// StageForAlerts.inform("alert", "please specify the amount paid");
				// return;

				balanceAdd = getTotalCost();

			}

			else {

				String amountPaidString = fieldAmountPaid.getText();
				amountPaidStr = NumberFormatting.testNumberCorrectness( amountPaidString );

				if (!NumberFormatting.isNumberCorrect()) {

					StageForAlerts.inform( "alert", "wrong number format for amount paid" );
					return;

				} else {

					amountPaid = new BigDecimal( amountPaidStr );
					amountPaidAdd = amountPaid;

				}

				setDiscount();
				balanceAdd = getTotalCost().subtract( discountReceivedAdd ).subtract( amountPaidAdd );

				// balanceAdd = getTotalCost().subtract(amountPaidAdd);

			}

		}

		this.labelBalanceText.setText( NumberFormatting.formatToEnglish( balanceAdd.toString() ) );

	}

	private Item itemSelected = null;

	private Item getItemSelected() {
		return itemSelected;
	}

	private void setItemSelected(Item itemSelected) {
		this.itemSelected = itemSelected;
	}

	private void onComboBoxItemSelected() {

		comboBoxItems.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setItemSelected( newVal );

			if ( newVal != null ) {
				setItemSelected( newVal );
			} else {
				setItemSelected( oldVal );
			}

		} );

	}

	private Supplier supplierSelected;

	private Supplier getSupplierSelected() {
		return supplierSelected;
	}

	private void setSupplierSelected(Supplier supplierSelected) {
		this.supplierSelected = supplierSelected;
	}

	private void onComboBoxSupplierSelected() {

		comboBoxSuppliers.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setSupplierSelected( newVal );

			if ( newVal != null ) {
				setSupplierSelected( newVal );
			} else {
				setSupplierSelected( oldVal );
			}

		} );

	}

	private void addNewPurchase() {

		new StageForAlerts();

		String date;

		// LocalDate date = null;
		if ( datePicker.getValue() == null )
			date = CurrentTimestamp.getDateTimeEndAtSecond();
		else {

			LocalDate localDate = datePicker.getValue();

			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );

		}

		Item item;

		if ( getItemSelected() == null ) {

			StageForAlerts.inform( "alert", "please specify the item being purchased" );
			return;

		} else {

			item = getItemSelected();

		}

		boolean credit = false;
		if ( checkBoxCredit.isSelected() )
			credit = true;

		setQuantity();

		if ( quantityAdd.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "the quantity can not be empty" );

			return;

		}

		if ( unitCostAdd.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "the unit cost of the item can not be 0" );

			return;

		}

		Supplier supplier = null;
		if ( getSupplierSelected() == null ) {

			if ( checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert", "please specify the supplier" );

				return;

			}
		}

		else {

			supplier = getSupplierSelected();

		}

		BigDecimal amountCleared = amountPaidAdd;

		if ( !checkBoxCredit.isSelected() && amountCleared.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "it is not a credit purchase, please specify the amount cleared" );

			return;

		}

		BigDecimal discountGot = discountReceivedAdd;

		BigDecimal balance = balanceAdd;

		Purchase aPurchase = new Purchase();

		aPurchase.setDate(date);
		aPurchase.setItemId( item );
		aPurchase.setQuantityPurchased( quantityAdd );
		aPurchase.setCredit( credit );
		aPurchase.setSupplierId( supplier );
		aPurchase.setAmountPaid( amountCleared );
		aPurchase.setDiscountReceived( discountGot );
		aPurchase.setBalanceToBeCleared( balance );
		aPurchase.setPrice( unitCostAdd );
		aPurchase.setTotalCost( this.getTotalCost() );
		aPurchase.setMediumOfPaymentId( mediumOfPayment );


		Service<Void> service;
		String finalDate = date;
		Item finalItem = item;
		service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {
						FieldClearance.clearTextField( fieldAmountPaid );
						FieldClearance.clearTextField( fieldQuantity );
						FieldClearance.clearTextField( fieldDiscountReceived );
						FieldClearance.clearTextField( fieldItemAlternative );
						FieldClearance.clearTextField( fieldUnitPrice );
						FieldClearance.clearCheckBox( checkBoxCredit );
						FieldClearance.clearComboBox( comboBoxSuppliers );
						FieldClearance.clearComboBox( comboBoxItems );

						Purchase purchase = PurchasesHibernation.newPurchase( aPurchase );

						Stock stock = new Stock();
						stock.setDate(finalDate);
						stock.setItemQuantity( quantityAdd );
						stock.setItemId(finalItem);

						StockHibernation.newStock( stock );

						PurchaseManipulation.data.add(purchase);

						AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "added a new purchase: ".concat( aPurchase.getItemId().getItemName() ),
										UserLoggedIn.getUserLoggedIn() );

						AuditHistoryData.theData.add(auditHistory);

						return null;
					}
				};
			}
		};
		service.start();

	}

	private void getItemByBarcode( String barcode ) {

		Item item = ItemHibernation.barcodesMappedToThierItems().get( barcode );

		comboBoxItems.getSelectionModel().select( item );

	}

	// private BigDecimal unitCost = BigDecimal.ZERO;
	// private BigDecimal quantity = BigDecimal.ZERO;

	private void scanningItemBarcodeForPurchase() {

		fieldItemAlternative.setOnKeyPressed( e -> {

			String barCode = fieldItemAlternative.getText();

			if ( e.getCode() == KeyCode.ENTER ) {
// this item is set to the combo box
				getItemByBarcode( barCode );

				fieldItemAlternative.clear();

			}

		} );

	}

	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing all purchases" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );


	}

//	private void clickRow() {
//
//		tableView.setRowFactory( tr -> {
//
//			TableRow < Purchase > row = new TableRow <>();
//
//			row.setOnMouseClicked( e -> {
//				if (row.isEmpty() || e.getButton() != MouseButton.PRIMARY || e.getClickCount() != 1) {
//				}
//
//				// this.setIdOfSupplier(s.getId());
//
//			} );
//
//			return row;
//		} );
//
//	}

	private Purchase purchasePrevoius;

	Purchase getPurchasePrevoius() {
		return purchasePrevoius;
	}

	private void setPurchasePrevoius(Purchase purchasePrevoius) {
		this.purchasePrevoius = purchasePrevoius;
	}

	void populateForUpdate(Purchase purchase) {

		setPurchasePrevoius( purchase );

		stageForAllPopUps.setTitle( "edit purchase info" );

		buttonSave.setText( "save changes" );

		comboBoxItems.getSelectionModel().select( getPurchasePrevoius().getItemId() );
		fieldQuantity.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getQuantityPurchased().toString() ) );
		fieldUnitPrice.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getPrice().toString() ) );
		labelTotalCostText.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getTotalCost().toString() ) );

		if (purchasePrevoius.isCredit())
			checkBoxCredit.setSelected(true);
		else
			checkBoxCredit.setSelected(false);

		comboBoxSuppliers.getSelectionModel().select( purchasePrevoius.getSupplierId() );
		fieldAmountPaid.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getAmountPaid().toString() ) );
		fieldDiscountReceived
				.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getDiscountReceived().toString() ) );
		labelBalanceText
				.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getBalanceToBeCleared().toString() ) );

	}

	public Purchase itemObtainedByClickingTable() {

		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setPurchasePrevoius( newVal );

			if ( newVal != null ) {
				setPurchasePrevoius( newVal );
			} else {
				setPurchasePrevoius( oldVal );
			}

		} );

		return getPurchasePrevoius();
	}

	private void completeUpdate(Purchase purchasePrevious) {

//		new StageForAlerts();

		String date;

		if ( datePicker.getValue() == null )
			date = CurrentTimestamp.getDateTimeEndAtMinutes();

		else {

			LocalDate localDate = datePicker.getValue();

			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );

		}

		Item item;

		if ( comboBoxItems.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "please specify the item being purchased" );
			return;

		} else {

			item = comboBoxItems.getSelectionModel().getSelectedItem();

		}

		boolean credit;
		credit = checkBoxCredit.isSelected();

		setQuantity();

		if ( quantityAdd.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "the quantity can not be empty" );

			return;

		}

		if ( unitCostAdd.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "the unit cost of the item can not be 0" );

			return;

		}

		Supplier supplier = null;

		if ( comboBoxSuppliers.getSelectionModel().getSelectedItem() == null ) {

			if ( checkBoxCredit.isSelected() ) {

				StageForAlerts.inform( "alert", "please specify the supplier" );

				return;

			}
		}

		else {

			supplier = comboBoxSuppliers.getSelectionModel().getSelectedItem();

		}

		BigDecimal amountCleared = amountPaidAdd;

		if ( !checkBoxCredit.isSelected() && amountCleared.doubleValue() <= 0 ) {

			StageForAlerts.inform( "alert", "it is not a credit purchase, please specify the amount cleared" );

			return;

		}

		BigDecimal discountGot = discountReceivedAdd;

		BigDecimal balance = balanceAdd;

		Purchase aPurchase = new Purchase();

		aPurchase.setId( purchasePrevious.getId() );
		aPurchase.setDate( date );
		aPurchase.setItemId( item );
		aPurchase.setQuantityPurchased( getQuantity() );
		aPurchase.setCredit( credit );
		aPurchase.setSupplierId( supplier );
		aPurchase.setAmountPaid( amountCleared );
		aPurchase.setDiscountReceived( discountGot );
		aPurchase.setBalanceToBeCleared( balance );
		aPurchase.setPrice( getUnitCost() );
		aPurchase.setTotalCost( this.getTotalCost() );
		aPurchase.setMediumOfPaymentId( mediumOfPayment );


		Service<Void> service;
		service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() {

						//TODO make sure stock updates properly

//						Purchase purchaseFinal = makingDifferencesOfFormerAndLatterUpdatevalues( purchasePrevoius, aPurchase );

                        BigDecimal qtyLatter = aPurchase.getQuantityPurchased();
                        BigDecimal qtyFormer = purchasePrevious.getQuantityPurchased();

                        BigDecimal qtyDifference = qtyLatter.subtract(qtyFormer);

						Stock stockToChange = StockHibernation.mapOfStockToThierItemIDs().get( aPurchase.getItemId().getId() );

                        BigDecimal qty = stockToChange.getItemQuantity().add(qtyDifference);

                        stockToChange.setItemQuantity(qty);

						Purchase purchase = PurchasesHibernation.updatePurchase( aPurchase, aPurchase.getId() );

						StockHibernation.updateStock( stockToChange, stockToChange.getId() );

						PurchaseManipulation.data.set(

								PurchaseManipulation.data.indexOf(purchasePrevoius),

								purchase

						);

						AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "edited a purchase: " + aPurchase.getItemId().getItemName(),
								UserLoggedIn.getUserLoggedIn() );

						AuditHistoryData.theData.add(auditHistory);
						return null;
					}
				};
			}
		};
		service.start();

		stageForAllPopUps.setTitle( "add new purchase" );
		buttonSave.setText( "save" );

		labelBalanceText.setText( null );
		labelTotalCostText.setText( null );
		FieldClearance.clearTextField( fieldQuantity );
		FieldClearance.clearTextField( fieldUnitPrice );
		FieldClearance.clearTextField( fieldAmountPaid );
		FieldClearance.clearTextField( fieldDiscountReceived );
		FieldClearance.clearTextField( fieldItemAlternative );
		FieldClearance.clearCheckBox( checkBoxCredit );
		FieldClearance.clearComboBox( comboBoxItems );
		FieldClearance.clearComboBox( comboBoxSuppliers );
//		stageForAllPopUps.close();

	}

//	private BigDecimal qtyAdditionalToStock = BigDecimal.ZERO;
//	private BigDecimal qtySubractionalFromStock = BigDecimal.ZERO;

//	private Purchase makingDifferencesOfFormerAndLatterUpdatevalues( Purchase former, Purchase latter ) {
//
//		BigDecimal qtyFormer = former.getQuantityPurchased();
//		BigDecimal qtyLatter = latter.getQuantityPurchased();
//
//		if ( qtyFormer.doubleValue() > qtyLatter.doubleValue() ) {
//
//			qtySubractionalFromStock = qtyFormer.subtract( qtyLatter );
//
//		}
//
//		if ( qtyLatter.doubleValue() > qtyLatter.doubleValue() ) {
//
//			qtyAdditionalToStock = qtyLatter.subtract( qtyFormer );
//
//		}
//
//		if ( checkBoxCredit.isSelected() ) {
//			latter.setCredit( true );
//		} else {
//			latter.setCredit( false );
//		}
//
//		return latter;
//
//	}

	private void autoCompleteTextField() {

		List<ItemPriceBarcode> list;
		list = AutoCompleteFieldObtainResult.setSearchItems();
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

						fieldItemAlternative.setText( text.getLastSelectedObject().getItem().getBarcode() );

						fieldItemAlternative.requestFocus();

						fieldItemAlternative.fireEvent(
								new KeyEvent( KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER,
										true, true, true, true ) );

					}
				}));
	}

	private ComboBox < MediumOfPayment > comboBoxMediumOfPayment;

	private MediumOfPayment mediumOfPayment = null;

	private void getMediumOfPayment() {
		comboBoxMediumOfPayment.setOnAction( e -> mediumOfPayment = comboBoxMediumOfPayment.getSelectionModel().getSelectedItem());
	}

}
