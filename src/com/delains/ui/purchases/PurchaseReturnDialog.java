package com.delains.ui.purchases;

import java.math.BigDecimal;

import com.delains.dao.purchases.PurchaseReturnClearanceHibernation;
import com.delains.dao.purchases.PurchasesHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.purchases.Purchase;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.model.purchases.PurchaseReturnClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PurchaseReturnDialog extends BorderPane {

	public PurchaseReturnDialog() {

		buildComponents();

		buildTable();

		tableClicked();

		buttonSaveAmount.setOnAction( e -> saveAmountReturned() );

	}

	private Label labelDate;
	private DatePicker datePicker;

	private Label labelQuantity;
	private TextField fieldQuantity;

	private Label labelReason;
	private TextArea areaReason;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;
	private HBox hBoxButtons;

	private VBox vBoxmain;
	private HBox hBoxFields;
	private GridPane gridPane;

	public JFXButton getButtonSave() {
		return buttonSave;
	}

	public void setButtonSave( JFXButton buttonSave ) {
		this.buttonSave = buttonSave;
	}

	public JFXButton getButtonCancel() {
		return buttonCancel;
	}

	public void setButtonCancel( JFXButton buttonCancel ) {
		this.buttonCancel = buttonCancel;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public void setDatePicker( DatePicker datePicker ) {
		this.datePicker = datePicker;
	}

	public TextField getFieldQuantity() {
		return fieldQuantity;
	}

	public void setFieldQuantity( TextField fieldQuantity ) {
		this.fieldQuantity = fieldQuantity;
	}

	public TextArea getAreaReason() {
		return areaReason;
	}

	public void setAreaReason( TextArea areaReason ) {
		this.areaReason = areaReason;
	}

	private GridPane gridPane2;
	private JFXButton buttonpopulateAmountReturndetails;

	private Label labelAmountExpected;
	private Label labelAmountExpectedText;

	private Label labelActualAmountReceived;
	private TextField fieldActualAmountReceived;

	private JFXButton buttonSaveAmount;

	private GridPane buildAmountReeceivedFromReturnCompnonents() {

		this.gridPane2 = new GridPane();
		this.gridPane2.setHgap( 10 );
		this.gridPane2.setVgap( 10 );
		this.gridPane2.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonpopulateAmountReturndetails = new JFXButton( "amount received back list" );
		gridPane2.add( buttonpopulateAmountReturndetails, 0, 0 );

		labelAmountExpected = new Label( "amount expected:" );
		gridPane2.add( labelAmountExpected, 0, 1 );

		labelAmountExpectedText = new Label();
		gridPane2.add( labelAmountExpectedText, 0, 2 );

		labelActualAmountReceived = new Label( "actual amount received back:" );
		gridPane2.add( labelActualAmountReceived, 0, 3 );

		fieldActualAmountReceived = new TextField();
		gridPane2.add( fieldActualAmountReceived, 0, 4 );

		buttonSaveAmount = new JFXButton( "save" );
		gridPane2.add( new HBox( buttonSaveAmount ), 0, 5 );

		buttonpopulateAmountReturndetails.setOnAction( e -> {
			StageForAllPopUps stage = new StageForAllPopUps( new PurchaseReturnClearanceTable(),
					"all purchase return clearances" );
			stage.setResizable( true );
			stage.showAndWait();
		} );

		return gridPane2;

	}

	private void buildComponents() {

		vBoxmain = new VBox();
		hBoxFields = new HBox();
		vBoxTable = new VBox();

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labelDate = new Label( "Date" );
		addToGrid( labelDate, 0, 0 );
		datePicker = new DatePicker();
		datePicker.setEditable( false );
		addToGrid( datePicker, 1, 0 );

		labelQuantity = new Label( "Quantity" );
		addToGrid( labelQuantity, 0, 1 );
		fieldQuantity = new TextField();
		addToGrid( fieldQuantity, 1, 1 );

		labelReason = new Label( "Reason:" );
		addToGrid( labelReason, 0, 2 );
		areaReason = new TextArea();
		addToGrid( areaReason, 1, 2 );

		hBoxButtons = new HBox( 10 );
		addToGrid( hBoxButtons, 1, 3 );

		buttonSave = new JFXButton( "save" );
		hBoxButtons.getChildren().add( buttonSave );

		buttonCancel = new JFXButton( "cancel" );
		hBoxButtons.getChildren().add( buttonCancel );

		hBoxFields.getChildren().add( gridPane );

		Separator separator = new Separator( Orientation.VERTICAL );
		hBoxFields.getChildren().add( separator );

		hBoxFields.getChildren().add( buildAmountReeceivedFromReturnCompnonents() );

		vBoxmain.getChildren().add( hBoxFields );

		vBoxmain.getChildren().add( vBoxTable );

		this.setCenter( vBoxmain );
	}

	private void addToGrid( Node node, int col, int row ) {
		gridPane.add( node, col, row );
	}

	private VBox vBoxTable;

	public static TableView < PurchaseReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < PurchaseReturn > tableView ) {
		PurchaseReturnDialog.tableView = tableView;
	}

	private static TableView < PurchaseReturn > tableView;

	private TableColumn < PurchaseReturn, String > colDate;
	private TableColumn < PurchaseReturn, String > colItem;
	private TableColumn < PurchaseReturn, String > colQuantity;
	private TableColumn < PurchaseReturn, String > colSupplier;

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "Date" );
		colDate = setColumnValueFactory( colDate, "date" );
		tableView.getColumns().add( colDate );

		colItem = new TableColumn <>( "Item" );
		colItem = setColumnValueFactory( colItem, "item" );
		tableView.getColumns().add( colItem );

		colQuantity = new TableColumn <>( "Quantity" );
		colQuantity = setColumnValueFactory( colQuantity, "qty" );
		tableView.getColumns().add( colQuantity );

		colSupplier = new TableColumn <>( "Supplier" );
		colSupplier = setColumnValueFactory( colSupplier, "supplier" );
		tableView.getColumns().add( colSupplier );

		addButtonToTable();

		vBoxTable.getChildren().add( tableView );

		vBoxTable.setPrefWidth( 1200 );

		Refresh.setRefreshingDeterminant( 0 );
		PurchaseReturnManipulation.populateTable( tableView );

	}

	private TableColumn < PurchaseReturn, String > setColumnValueFactory( TableColumn < PurchaseReturn, String > column,
			String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < PurchaseReturn, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < PurchaseReturn, String > param ) {

						PurchaseReturn pr = param.getValue();

						String val = null;

						if ( decideValue.equalsIgnoreCase( "date" ) ) {
							val = pr.getDate();
						}

						if ( decideValue.equalsIgnoreCase( "item" ) ) {
							val = pr.getItemId().getItemName();
						}

						if ( decideValue.equalsIgnoreCase( "qty" ) ) {
							val = NumberFormatting.formatToEnglish( pr.getQuantity().toString() );
						}

						if ( decideValue.equalsIgnoreCase( "supplier" ) ) {
							if ( pr.getSupplierId() != null ) {
								val = pr.getSupplierId().getSupplierName();
							} else {
								val = "";
							}
						}

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		return column;

	}

	private void addButtonToTable() {
		TableColumn < PurchaseReturn, Void > colBtn = new TableColumn <>( "Reason" );

		Callback < TableColumn < PurchaseReturn, Void >, TableCell < PurchaseReturn, Void > > cellFactory = new Callback < TableColumn < PurchaseReturn, Void >, TableCell < PurchaseReturn, Void > >() {
			@Override
			public TableCell < PurchaseReturn, Void > call( final TableColumn < PurchaseReturn, Void > param ) {
				final TableCell < PurchaseReturn, Void > cell = new TableCell < PurchaseReturn, Void >() {

					private final Button btn = new Button( "reason" );

					{
						btn.setOnAction( ( ActionEvent event ) -> {

							PurchaseReturn pr = getTableView().getItems().get( getIndex() );

							// PurchaseReturn data = getTableView().getPurchaseReturns().get( getIndex() );
new StageForAlerts();
							StageForAlerts.inform( "description of item", pr.getReason() );

						} );
					}

					@Override
					public void updateItem( Void item, boolean empty ) {
						super.updateItem( item, empty );
						if ( empty ) {
							setGraphic( null );
						} else {
							setGraphic( btn );
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory( cellFactory );

		tableView.getColumns().add( colBtn );

	}

	private PurchaseReturn purchaseReturn;

	public PurchaseReturn getPurchaseReturn() {
		return purchaseReturn;
	}

	public void setPurchaseReturn( PurchaseReturn purchaseReturn ) {
		this.purchaseReturn = purchaseReturn;
	}

	private PurchaseReturn purchaseReturnWhenTableRowClicked;

	public PurchaseReturn getPurchaseReturnWhenTableRowClicked() {
		return purchaseReturnWhenTableRowClicked;
	}

	public void setPurchaseReturnWhenTableRowClicked( PurchaseReturn purchaseReturnWhenTableRowClicked ) {
		this.purchaseReturnWhenTableRowClicked = purchaseReturnWhenTableRowClicked;
	}

	private PurchaseReturn tableClicked() {

		tableView.setOnMouseClicked( e -> {
			PurchaseReturn pr = tableView.getSelectionModel().getSelectedItem();
			BigDecimal expectedBackAmount = pr.getPurchaseId().getPrice().multiply( pr.getQuantity() );
			labelAmountExpectedText.setText( NumberFormatting.formatToEnglish( expectedBackAmount.toString() ) );

			setPurchaseReturnWhenTableRowClicked( pr );

		} );

		return getPurchaseReturnWhenTableRowClicked();

	}

	private void saveAmountReturned() {

		new StageForAlerts();

		if ( fieldActualAmountReceived.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "you can not leave the amount returned empty" );
			return;
		}

		String amountReturnedStr = NumberFormatting.testNumberCorrectness( fieldActualAmountReceived.getText().trim() );

		BigDecimal amountReturned = new BigDecimal( amountReturnedStr );

		PurchaseReturnClearance clearance = new PurchaseReturnClearance();
		clearance.setAmountPaid( amountReturned );
		clearance.setPurchaseReturnId( getPurchaseReturnWhenTableRowClicked() );

		if ( clearance.getPurchaseReturnId() == null ) {
			StageForAlerts.inform( "alert", "please make sure a row to be affected is chosen from the table" );
			return;
		}

		Purchase purchase = clearance.getPurchaseReturnId().getPurchaseId();

BigDecimal amountPaidOfOriginalPurchase = purchase.getAmountPaid();
		BigDecimal amountPaidOfPurchaseAfterSubtraction = amountPaidOfOriginalPurchase
				.subtract( clearance.getAmountPaid() );

purchase.setAmountPaid( amountPaidOfPurchaseAfterSubtraction );

		PurchaseReturnClearanceHibernation.newPurchaseReturnClearance( clearance );
		PurchasesHibernation.updatePurchase( purchase, purchase.getId() );

	}

}
