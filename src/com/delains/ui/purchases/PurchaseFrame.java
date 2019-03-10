package com.delains.ui.purchases;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.stock.StockHibernation;
import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.payments.MediumOfPayment;
import com.delains.model.purchases.Purchase;
import com.delains.model.stock.Stock;
import com.delains.model.suppliers.Supplier;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.test.ComboBoxAutoComplete;
import com.delains.utilities.CurrentTimestamp;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class PurchaseFrame extends BorderPane {

	public ComboBox < Item > getComboBoxItems() {
		return comboBoxItems;
	}

	public void setComboBoxItems( ComboBox < Item > comboBoxItems ) {
		this.comboBoxItems = comboBoxItems;
	}

	public ComboBox < Supplier > getComboBoxSuppliers() {
		return comboBoxSuppliers;
	}

	public void setComboBoxSuppliers( ComboBox < Supplier > comboBoxSuppliers ) {
		this.comboBoxSuppliers = comboBoxSuppliers;
	}

	private Label labelDate;
	private DatePicker datePicker;

	private Label labelItemAlternative;
	private TextField fieldItemAlternative;

	private Label labelItem;
	private ComboBox < Item > comboBoxItems;

	private Label labelQuantity;
	private TextField fieldQuantity;

	private Label labelUnitPrice;
	private TextField fieldUnitPrice;

	private Label labelTotalCost;
	private Label labelTotalCostText;

	private JFXCheckBox checkBoxCredit;

	private Label labelSupplierName;
	private ComboBox < Supplier > comboBoxSuppliers;

	private Label labelAmountPaid;
	private TextField fieldAmountPaid;

	public TextField getFieldAmountPaid() {
		return fieldAmountPaid;
	}

	public void setFieldAmountPaid( TextField fieldAmountPaid ) {
		this.fieldAmountPaid = fieldAmountPaid;
	}

	private Label labelBalance;
	private Label labelBalanceText;

	private Label labelDiscountReceived;
	private TextField fieldDiscountReceived;

	public TextField getFieldDiscountReceived() {
		return fieldDiscountReceived;
	}

	public void setFieldDiscountReceived( TextField fieldDiscountReceived ) {
		this.fieldDiscountReceived = fieldDiscountReceived;
	}

	private HBox hBox;
	private GridPane gridPane;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;
	private HBox hBoxButtons;

	private StageForAllPopUps stageForAllPopUps;

	public PurchaseFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( PurchaseFrame.class.getResource( "purchase.css" ).toExternalForm() );

		buildPurchasesframeComponents();

		buildPurchasesTable();
		buildTopOfThisFrame();

		Refresh.setRefreshingDeterminant( 0 );
		PurchaseManipulation.populateTable( tableView );

		// populateTable();

		// fieldItemAlternative.setOnKeyReleased(e -> {
		//
		// String barCode = fieldItemAlternative.getText();
		//
		// // System.out.println("barcode at purchase: " + barCode);
		//
		// });

		/*
		 * 
		 * when the quantity field is focused or unfocused
		 * 
		 */
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
				completeUpdate();
			} else {
				addNewPurchase();
			}

		} );

		scanningItemBarcodeForPurchase();

		itemObtainedByClickingTable();

		onComboBoxItemSelected();
		onComboBoxSupplierSelected();

		populateComboBoxMediumOfPayment();
		getMediumOfPayment();

	}

	private void buildPurchasesframeComponents() {

		this.hBox = new HBox();

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelDate = new Label( "Date" );
		addToGrid( labelDate, 0, 0 );

		this.datePicker = new DatePicker();
		datePicker.setEditable( false );
		addToGrid( datePicker, 1, 0 );

		labelItemAlternative = new Label( "Item (scanner)" );
		this.addToGrid( this.labelItemAlternative, 0, 1 );

		this.fieldItemAlternative = new TextField();
		this.addToGrid( this.fieldItemAlternative, 1, 1 );

		this.labelItem = new Label( "item" );
		addToGrid( labelItem, 0, 2 );

		comboBoxItems = new ComboBox <>();
		addToGrid( comboBoxItems, 1, 2 );

		this.labelQuantity = new Label( "quantity" );
		addToGrid( labelQuantity, 0, 3 );

		this.fieldQuantity = new TextField();
		ComponentWidth.setWidthOfTextField( fieldQuantity, 400 );
		addToGrid( fieldQuantity, 1, 3 );

		this.labelUnitPrice = new Label( "unit cost" );
		addToGrid( labelUnitPrice, 0, 4 );

		this.fieldUnitPrice = new TextField();
		addToGrid( fieldUnitPrice, 1, 4 );

		this.labelTotalCost = new Label( "total cost:" );
		addToGrid( labelTotalCost, 0, 5 );

		this.labelTotalCostText = new Label();
		addToGrid( labelTotalCostText, 1, 5 );

		this.checkBoxCredit = new JFXCheckBox( "credit?" );
		addToGrid( checkBoxCredit, 1, 6 );

		this.labelSupplierName = new Label( "supplier name:" );
		addToGrid( labelSupplierName, 0, 7 );

		comboBoxSuppliers = new ComboBox <>();
		addToGrid( comboBoxSuppliers, 1, 7 );

		this.labelAmountPaid = new Label( "amount cleared:" );

		addToGrid( labelAmountPaid, 0, 8 );

		this.fieldAmountPaid = new TextField();
		addToGrid( fieldAmountPaid, 1, 8 );

		this.labelDiscountReceived = new Label( "discount received" );
		addToGrid( labelDiscountReceived, 0, 9 );

		this.fieldDiscountReceived = new TextField();
		addToGrid( fieldDiscountReceived, 1, 9 );

		this.labelBalance = new Label( "balance:" );
		addToGrid( labelBalance, 0, 10 );

		this.labelBalanceText = new Label();
		addToGrid( labelBalanceText, 1, 10 );

		comboBoxMediumOfPayment = new ComboBox <>();
		addToGrid( new Label( "payment medium" ), 0, 11 );
		addToGrid( comboBoxMediumOfPayment, 1, 11 );

		this.hBoxButtons = new HBox( 10 );
		this.hBoxButtons.setPadding( new Insets( 10 ) );
		this.buttonSave = new JFXButton( "save" );
		this.hBoxButtons.getChildren().add( buttonSave );

		this.buttonCancel = new JFXButton( "cancel" );
		this.hBoxButtons.getChildren().add( buttonCancel );

		this.gridPane.add( hBoxButtons, 1, 12 );

		this.hBox.getChildren().add( this.gridPane );

		Refresh.setRefreshingDeterminant( 1 );
		populateComboBoxItems();
		populateComboBoxSupplier();

		// populateComboBoxItems();
		// populateComboBoxSuppliers();

		/*
		 * 
		 * listening
		 * 
		 */

		stageForAllPopUps = new StageForAllPopUps( hBox, "add new purchase" );

		this.buttonSave.setOnAction( e -> {

		} );

		this.buttonCancel.setOnAction( e -> {

			stageForAllPopUps.close();

		} );

		autoComplete();
		
	}

	private void autoComplete() {
		comboBoxItems.setTooltip( new Tooltip() );
		new ComboBoxAutoComplete<Item>( comboBoxItems );
	}
	
	private void addToGrid( Node node, int col, int row ) {
		this.gridPane.add( node, col, row );
	}

	public void newPurchase() {

		populateComboBoxSupplier();
		populateComboBoxItems();
		populateComboBoxMediumOfPayment();

		stageForAllPopUps.showAndWait();

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

	private TableColumn < Purchase, String > colDate;
	private TableColumn < Purchase, String > colItem;
	private TableColumn < Purchase, String > colQuantity;
	private TableColumn < Purchase, String > colPrice;
	private TableColumn < Purchase, String > colTotalCost;
	private TableColumn < Purchase, Boolean > colCredit;
	private TableColumn < Purchase, String > colSupplier;
	private TableColumn < Purchase, String > colAmountPaid;
	private TableColumn < Purchase, String > colBalanceToBeCleared;
	private TableColumn < Purchase, String > colDiscountReceived;

	private void buildPurchasesTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "Date" );
		colDate.setCellValueFactory( new PropertyValueFactory <>( "date" ) );
		colDate.setCellFactory( tc -> {

			TableCell < Purchase, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		tableView.getColumns().add( colDate );

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

		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {

						Purchase aPurchase = param.getValue();

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						StringProperty property = simpleStringProperty;

						if ( aPurchase != null && aPurchase.getItemId() != null ) {

							simpleStringProperty = new SimpleStringProperty( aPurchase.getItemId().getItemName() );

							property = simpleStringProperty;

						} else {
							property = null;
						}

						return property;
					}
				} );
		tableView.getColumns().add( colItem );

		colQuantity = new TableColumn <>( "Quantity" );
		colQuantity.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getQuantityPurchased();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() <= 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableView.getColumns().add( colQuantity );

		colPrice = new TableColumn <>( "Cost" );
		colPrice.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getPrice();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() <= 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		tableView.getColumns().add( colPrice );

		colTotalCost = new TableColumn <>( "Total\ncost" );
		colTotalCost.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getTotalCost();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() <= 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		tableView.getColumns().add( colTotalCost );

		colCredit = new TableColumn <>( "Credit?" );
		colCredit.setCellFactory( e -> new CheckBoxTableCell <>() );
		colCredit.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, Boolean >, ObservableValue < Boolean > >() {

					@Override
					public ObservableValue < Boolean > call( CellDataFeatures < Purchase, Boolean > param ) {

						Purchase aPurchase = param.getValue();

						SimpleBooleanProperty booleanProperty = new SimpleBooleanProperty( aPurchase.isCredit() );

						BooleanProperty property = booleanProperty;

						return property;
					}
				} );
		tableView.getColumns().add( colCredit );

		colSupplier = new TableColumn <>( "Supplier" );
		colSupplier.setCellFactory( tc -> {

			TableCell < Purchase, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colItem.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

		colSupplier.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {

						Purchase aPurchase = param.getValue();

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						StringProperty property = null;

						if ( aPurchase.getSupplierId() != null ) {

							simpleStringProperty.setValue( aPurchase.getSupplierId().getSupplierName() );

							property = simpleStringProperty;

						} else {

							simpleStringProperty.setValue( "" );

							property = simpleStringProperty;

						}

						return property;
					}
				} );
		tableView.getColumns().add( colSupplier );

		colAmountPaid = new TableColumn <>( "Amount\nCleared" );
		colAmountPaid.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getAmountPaid();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() <= 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		tableView.getColumns().add( colAmountPaid );

		colBalanceToBeCleared = new TableColumn <>( "Balance" );
		colBalanceToBeCleared.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getBalanceToBeCleared();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() == 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		tableView.getColumns().add( colBalanceToBeCleared );

		colDiscountReceived = new TableColumn <>( "Discount\nreceived" );
		colDiscountReceived.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Purchase, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Purchase, String > param ) {
						// TODO Auto-generated method stub

						Purchase aPurchase = param.getValue();

						BigDecimal qty = aPurchase.getDiscountReceived();

						String qtyStr = NumberFormatting.formatToEnglish( qty.toString() );

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						if ( qty.doubleValue() <= 0 ) {
							simpleStringProperty.setValue( null );
						} else {
							simpleStringProperty.setValue( qtyStr );
						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		tableView.getColumns().add( colDiscountReceived );

		/*
		 * 
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 * 
		 */

		this.setCenter( tableView );

	}

	BigDecimal quantityAdd = BigDecimal.ZERO;

	private void setQuantity() {

		BigDecimal quantity = BigDecimal.ZERO;
		String quantityStr = null;

		if ( fieldQuantity.getText().trim().isEmpty() ) {

			// StageForAlerts.inform("alert", "please specify the quantity");
			System.out.println( "under qty" );

			quantityAdd = BigDecimal.ZERO;

			// return;

		} else {

			String qtyStr = fieldQuantity.getText();

			quantityStr = NumberFormatting.testNumberCorrectness( qtyStr );

			if ( NumberFormatting.isNumberCorrect() == false ) {

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

	BigDecimal unitCostAdd = BigDecimal.ZERO;

	private void setUnitCost() {

		String unitCostStr = null;
		BigDecimal unitCost = BigDecimal.ZERO;

		if ( fieldUnitPrice.getText().trim().isEmpty() ) {

			// StageForAlerts.inform("alert", "please specify the unitCost");
			System.out.println( "under unit cost" );
			unitCostAdd = BigDecimal.ZERO;
			// return;

		} else {

			String unitCostString = fieldUnitPrice.getText();
			unitCostStr = NumberFormatting.testNumberCorrectness( unitCostString );
			if ( NumberFormatting.isNumberCorrect() == false ) {

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
		String discountReceivedString = null;
		String discountStr = null;

		if ( fieldDiscountReceived.getText().trim().isEmpty() ) {
			discountReceivedAdd = BigDecimal.ZERO;
		}

		else {

			discountReceivedString = fieldDiscountReceived.getText();
			discountStr = NumberFormatting.testNumberCorrectness( discountReceivedString );

			if ( NumberFormatting.isNumberCorrect() == false ) {
				// StageForAlerts.inform("alert", "wrong number format for discount received");
				System.out.println( "under discount" );
				return;
			} else {
				discountReceivedAdd = new BigDecimal( discountStr );
			}

		}

	}

	private BigDecimal balanceAdd = BigDecimal.ZERO;
	private BigDecimal amountPaidAdd = BigDecimal.ZERO;

	private void calculateBalance() {

		new StageForAlerts();

		String amountPaidStr = null;
		BigDecimal amountPaid = BigDecimal.ZERO;
		// String balanceStr = null;
		// BigDecimal balance = BigDecimal.ZERO;

		/*
		 * 
		 * if it is a credit purchase
		 * 
		 */
		if ( checkBoxCredit.isSelected() ) {

			setDiscount();

			// the amount paid can be empty
			if ( fieldAmountPaid.getText().trim().isEmpty() ) {

				amountPaid = BigDecimal.ZERO;
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
					if ( NumberFormatting.isNumberCorrect() == false ) {
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

					amountPaid = BigDecimal.ZERO;

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

				if ( NumberFormatting.isNumberCorrect() == false ) {

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

	public Item getItemSelected() {
		return itemSelected;
	}

	public void setItemSelected( Item itemSelected ) {
		this.itemSelected = itemSelected;
	}

	private Item onComboBoxItemSelected() {

		comboBoxItems.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setItemSelected( newVal );

			if ( newVal != null ) {
				setItemSelected( newVal );
				System.out.println( "selected: " + getItemSelected() );
			} else {
				setItemSelected( oldVal );
				System.out.println( "selected: " + getItemSelected() );
			}

		} );

		return getItemSelected();

	}

	private Supplier supplierSelected;

	public Supplier getSupplierSelected() {
		return supplierSelected;
	}

	public void setSupplierSelected( Supplier supplierSelected ) {
		this.supplierSelected = supplierSelected;
	}

	private Supplier onComboBoxSupplierSelected() {

		comboBoxSuppliers.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setSupplierSelected( newVal );

			if ( newVal != null ) {
				setSupplierSelected( newVal );
				System.out.println( "selected: " + getItemSelected() );
			} else {
				setSupplierSelected( oldVal );
				System.out.println( "selected: " + getItemSelected() );
			}

		} );

		return getSupplierSelected();

	}

	private void addNewPurchase() {

		populateComboBoxItems();
		populateComboBoxSupplier();

		new StageForAlerts();

		String date = null;

		// LocalDate date = null;
		if ( datePicker.getValue() == null ) {

			date = CurrentTimestamp.getDateTimeEndAtSecond();

		} else {

			LocalDate localDate = datePicker.getValue();

			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );

		}

		Item item = new Item();

		if ( getItemSelected() == null ) {

			StageForAlerts.inform( "alert", "please specify the item being purchased" );
			return;

		} else {

			item = getItemSelected();

		}

		boolean credit = false;
		if ( checkBoxCredit.isSelected() ) {
			credit = true;
		} else {
			credit = false;
		}

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

			} else {
				supplier = null;
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

		System.out.println( "qtyAdd: " + quantityAdd );

		Purchase aPurchase = new Purchase();

		aPurchase.setDate( date.toString() );
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

		PurchasesHibernation.newPurchase( aPurchase );

		FieldClearance.clearTextField( fieldAmountPaid );
		FieldClearance.clearTextField( fieldQuantity );
		FieldClearance.clearTextField( fieldDiscountReceived );
		FieldClearance.clearTextField( fieldItemAlternative );
		FieldClearance.clearTextField( fieldUnitPrice );
		FieldClearance.clearCheckBox( checkBoxCredit );
		FieldClearance.clearComboBox( comboBoxSuppliers );
		FieldClearance.clearComboBox( comboBoxItems );

		Stock stock = new Stock();
		stock.setDate( date.toString() );
		stock.setItemQuantity( quantityAdd );
		stock.setItemId( item );

		StockHibernation.newStock( stock );

		Refresh.setRefreshingDeterminant( 1 );
		PurchaseManipulation.populateTable( tableView );
		Refresh.setRefreshingDeterminant( 0 );

		AuditHistoryHibernation.auditValues( "added a new purchase: ".concat( aPurchase.getItemId().getItemName() ),
				UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

		// populateTableRefresh();

	}

	private void getItemByBarcode( String barcode ) {

		Item item = ItemHibernation.barcodesMappedToThierItems().get( barcode );

		comboBoxItems.getSelectionModel().select( item );

		System.out.println( "item: " + item.getItemName() );

	}

	// private BigDecimal unitCost = BigDecimal.ZERO;
	// private BigDecimal quantity = BigDecimal.ZERO;

	private void scanningItemBarcodeForPurchase() {

		fieldItemAlternative.setOnKeyPressed( e -> {

			String barCode = fieldItemAlternative.getText();

			if ( e.getCode() == KeyCode.ENTER ) {
				System.out.println( "barcode at purchase: " + barCode );

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

		clickRow();

	}

	private void clickRow() {

		tableView.setRowFactory( tr -> {

			TableRow < Purchase > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					Purchase purchase = row.getItem();

					// this.setIdOfSupplier(s.getId());

					System.out.println( "purchase clicked: " + purchase );

				}
			} );

			return row;
		} );

	}

	private Purchase purchasePrevoius;

	public Purchase getPurchasePrevoius() {
		return purchasePrevoius;
	}

	public void setPurchasePrevoius( Purchase purchasePrevoius ) {
		this.purchasePrevoius = purchasePrevoius;
	}

	public void populateForUpdate( Purchase purchase ) {

		Refresh.setRefreshingDeterminant( 1 );

		populateComboBoxItems();
		populateComboBoxSupplier();
		populateComboBoxMediumOfPayment();

		// purchasePrevoius = purchase;

		setPurchasePrevoius( purchase );

		stageForAllPopUps.setTitle( "edit purchase info" );
		buttonSave.setText( "save changes" );

		System.out.println( getPurchasePrevoius().getItemId().getItemName() );

		comboBoxItems.getSelectionModel().select( getPurchasePrevoius().getItemId() );
		fieldQuantity.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getQuantityPurchased().toString() ) );
		fieldUnitPrice.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getPrice().toString() ) );
		labelTotalCostText.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getTotalCost().toString() ) );
		if ( purchasePrevoius.isCredit() == true ) {
			checkBoxCredit.setSelected( true );
		} else {
			checkBoxCredit.setSelected( false );
		}
		comboBoxSuppliers.getSelectionModel().select( purchasePrevoius.getSupplierId() );
		fieldAmountPaid.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getAmountPaid().toString() ) );
		fieldDiscountReceived
				.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getDiscountReceived().toString() ) );
		labelBalanceText
				.setText( NumberFormatting.formatToEnglish( purchasePrevoius.getBalanceToBeCleared().toString() ) );

		stageForAllPopUps.showAndWait();

	}

	public Purchase itemObtainedByClickingTable() {

		Purchase it = tableView.selectionModelProperty().getValue().getSelectedItem();
		System.out.println( "getValue: " + it );

		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setPurchasePrevoius( newVal );

			if ( newVal != null ) {
				setPurchasePrevoius( newVal );
				// System.out.println( "new Value set Prev: " + getPurchasePrevoius() );
			} else {
				setPurchasePrevoius( oldVal );
			}

		} );

		return getPurchasePrevoius();
	}

	private void completeUpdate() {

		new StageForAlerts();

		String date = null;

		if ( datePicker.getValue() == null ) {

			date = CurrentTimestamp.getDateTimeEndAtMinutes();

		} else {

			LocalDate localDate = datePicker.getValue();

			date = CurrentTimestamp.concatLocalDateToTimeWithoutSecondsAttached( localDate );

		}

		Item item = new Item();

		if ( comboBoxItems.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "please specify the item being purchased" );
			return;

		} else {

			item = comboBoxItems.getSelectionModel().getSelectedItem();

		}

		boolean credit = false;
		if ( checkBoxCredit.isSelected() ) {
			credit = true;
		} else {
			credit = false;
		}

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

			} else {
				supplier = null;
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

		System.out.println( "qtyAdd: " + quantityAdd );

		Purchase aPurchase = new Purchase();

		aPurchase.setId( purchasePrevoius.getId() );
		aPurchase.setDate( date );
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

		Purchase purchaseFinal = makingDifferencesOfFormerAndLatterUpdatevalues( purchasePrevoius, aPurchase );

		Stock stockToChange = StockHibernation.mapOfStockToThierItemIDs().get( aPurchase.getItemId().getId() );

		if ( qtyAdditionalToStock.doubleValue() > 0 ) {

			BigDecimal qty = stockToChange.getItemQuantity();

			stockToChange.setItemQuantity( qty.add( qtyAdditionalToStock ) );

		}

		if ( qtySubractionalFromStock.doubleValue() > 0 ) {

			BigDecimal qty = stockToChange.getItemQuantity();

			stockToChange.setItemQuantity( qty.subtract( qtySubractionalFromStock ) );

		}

		PurchasesHibernation.updatePurchase( purchaseFinal, aPurchase.getId() );

		StockHibernation.updateStock( stockToChange, stockToChange.getId() );

		Refresh.setRefreshingDeterminant( 1 );
		PurchaseManipulation.populateTable( tableView );
		Refresh.setRefreshingDeterminant( 0 );

		AuditHistoryHibernation.auditValues( "edited a purchase: " + purchaseFinal.getItemId().getItemName(),
				UserLoggedIn.getUserLoggedIn() );
		AuditHistoryManipulation.populateTableWithRefreshing();

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
		stageForAllPopUps.close();

	}

	private BigDecimal qtyAdditionalToStock = BigDecimal.ZERO;
	private BigDecimal qtySubractionalFromStock = BigDecimal.ZERO;

	private Purchase makingDifferencesOfFormerAndLatterUpdatevalues( Purchase former, Purchase latter ) {

		BigDecimal qtyFormer = former.getQuantityPurchased();
		BigDecimal qtyLatter = latter.getQuantityPurchased();

		if ( qtyFormer.doubleValue() > qtyLatter.doubleValue() ) {

			qtySubractionalFromStock = qtyFormer.subtract( qtyLatter );

		}

		if ( qtyLatter.doubleValue() > qtyLatter.doubleValue() ) {

			qtyAdditionalToStock = qtyLatter.subtract( qtyFormer );

		}

		if ( checkBoxCredit.isSelected() ) {
			latter.setCredit( true );
		} else {
			latter.setCredit( false );
		}

		return latter;

	}

	private void populateComboBoxItems() {

		comboBoxItems.getItems().clear();

		if ( comboBoxItems.getItems().isEmpty() ) {
			comboBoxItems.setItems( ItemHibernation.findAllItemsObservableListRefreshed() );
		} else {
			comboBoxItems.getItems().clear();
			comboBoxItems.getItems().addAll( ItemHibernation.findAllItemsObservableListRefreshed() );
		}

		comboBoxItems.setConverter( new StringConverter < Item >() {

			@Override
			public String toString( Item object ) {

				return object.getItemName().concat( " " ).concat( object.getPackageVolume().toString() ).concat( "" )
						.concat( object.getUnitOfMeasurement() );
			}

			@Override
			public Item fromString( String string ) {

				// Item type =
				return comboBoxItems.getItems().stream()
						.filter( e -> e.getItemName().concat( " " ).concat( e.getPackageVolume().toString() )
								.concat( "" ).concat( e.getUnitOfMeasurement() ).equals( string ) )
						.findFirst().orElse( null );
			}
		} );

	}

	private void populateComboBoxSupplier() {

		comboBoxSuppliers.getItems().clear();

		if ( comboBoxSuppliers.getItems().isEmpty() ) {
			comboBoxSuppliers.setItems( SupplierHibernation.findAllSuppliersObservableListRefreshed() );
		} else {
			comboBoxSuppliers.getItems().clear();
			comboBoxSuppliers.getItems().addAll( SupplierHibernation.findAllSuppliersObservableListRefreshed() );
		}

		comboBoxSuppliers.setConverter( new StringConverter < Supplier >() {

			@Override
			public String toString( Supplier object ) {

				String type = object.getSupplierName();

				return type;
			}

			@Override
			public Supplier fromString( String string ) {

				Supplier type = comboBoxSuppliers.getItems().stream()
						.filter( e -> e.getSupplierName().equals( string ) ).findFirst().orElse( null );
				return type;
			}
		} );

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
