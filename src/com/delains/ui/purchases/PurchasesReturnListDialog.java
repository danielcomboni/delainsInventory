package com.delains.ui.purchases;

import com.delains.dao.utils.NumberFormatting;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class PurchasesReturnListDialog extends BorderPane {

	public PurchasesReturnListDialog() {
		setPrefWidth( 1200 );
		buildTable();
	}

	public static TableView < PurchaseReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < PurchaseReturn > tableView ) {
		PurchasesReturnListDialog.tableView = tableView;
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

		this.setCenter( tableView );

		this.setPrefWidth( 1200 );

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

}
