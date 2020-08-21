package com.delains.ui.sales;

import com.delains.dao.pos.SalesReturnHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.sales.SalesReturn;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SalesReturnTable {

	public SalesReturnTable() {
		buildTable();
	}

	private static TableView < SalesReturn > tableView;

	public static TableView < SalesReturn > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SalesReturn > tableView ) {
		SalesReturnTable.tableView = tableView;
	}

	private TableColumn < SalesReturn, String > colDate;
	private TableColumn < SalesReturn, String > colItem;
	private TableColumn < SalesReturn, String > colQtyReturned;
	private TableColumn < SalesReturn, String > colQtyReStocked;
	private TableColumn < SalesReturn, String > colQtyDiscarded;
	private TableColumn < SalesReturn, String > colCustomer;
	// private TableColumn < SalesReturn, Void > colReason;

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setPrefWidth( 1200 );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "Date" );
		textWrap( colDate );
		colDate = setColumnValueFactory( colDate, "date" );
		tableView.getColumns().add( colDate );

		colItem = new TableColumn <>( "Item" );
		textWrap( colItem );
		colItem = setColumnValueFactory( colItem, "item" );
		tableView.getColumns().add( colItem );

		colQtyReturned = new TableColumn <>( "Quantity\nReturned" );
		textWrap( colQtyReturned );
		colQtyReturned = setColumnValueFactory( colQtyReturned, "qty returned" );
		tableView.getColumns().add( colQtyReturned );

		colQtyReStocked = new TableColumn <>( "Quantity\nRestocked" );
		textWrap( colQtyReStocked );
		colQtyReStocked = setColumnValueFactory( colQtyReStocked, "qty restocked" );
		tableView.getColumns().add( colQtyReStocked );

		colQtyDiscarded = new TableColumn <>( "Quantity\nDiscarded" );
		textWrap( colQtyDiscarded );
		colQtyDiscarded = setColumnValueFactory( colQtyDiscarded, "qty discarded" );
		tableView.getColumns().add( colQtyDiscarded );

		colCustomer = new TableColumn <>( "Customer" );
		textWrap( colCustomer );
		colCustomer = setColumnValueFactory( colCustomer, "customer" );
		tableView.getColumns().add( colCustomer );

		// colReason = new TableColumn <>( "Reason\n(Explanation)" );

		addButtonToTable();

		tableView.getItems().clear();
		tableView.getItems().addAll( SalesReturnHibernation.findAllSalesReturnsWithRefreshing() );

		// Refresh.setRefreshingDeterminant( 0 );
		// SalesReturnManipulation.populateTable( tableView );

	}

	private void addButtonToTable() {
		TableColumn < SalesReturn, Void > colBtn = new TableColumn <>( "Reason\n(Explanation)" );

		Callback < TableColumn < SalesReturn, Void >, TableCell < SalesReturn, Void > > cellFactory = new Callback < TableColumn < SalesReturn, Void >, TableCell < SalesReturn, Void > >() {
			@Override
			public TableCell < SalesReturn, Void > call( final TableColumn < SalesReturn, Void > param ) {
				final TableCell < SalesReturn, Void > cell = new TableCell < SalesReturn, Void >() {

					private final Button btn = new Button( "reason" );

					{
						btn.setOnAction( ( ActionEvent event ) -> {

							SalesReturn pr = getTableView().getItems().get( getIndex() );

							// SalesReturn data = getTableView().getPurchaseReturns().get( getIndex() );
new StageForAlerts();
							StageForAlerts.inform( "explanation", pr.getReason() );

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

	private void textWrap( TableColumn < SalesReturn, String > column ) {

		column.setCellFactory( tr -> {
			TableCell < SalesReturn, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( column.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;
		} );

	}

	private TableColumn < SalesReturn, String > setColumnValueFactory( TableColumn < SalesReturn, String > column,
			String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < SalesReturn, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < SalesReturn, String > param ) {

						SalesReturn pr = param.getValue();

						String val = null;

						if ( decideValue.equalsIgnoreCase( "date" ) ) {
							val = pr.getDate();
						}

						if ( decideValue.equalsIgnoreCase( "item" ) ) {
							val = pr.getItemId().getItemName();
						}

						if ( decideValue.equalsIgnoreCase( "qty returned" ) ) {
							val = NumberFormatting.formatToEnglish( pr.getQuantityReturned().toString() );
						}

						if ( decideValue.equalsIgnoreCase( "qty restocked" ) ) {
							val = pr.getQuantityReStocked().toString();
						}

						if ( decideValue.equalsIgnoreCase( "qty discarded" ) ) {
							val = NumberFormatting.formatToEnglish( pr.getQuantityDiscarded().toString() );
						}

						if ( decideValue.equalsIgnoreCase( "customer" ) ) {
							if ( pr.getCustomerId() != null ) {
								val = pr.getCustomerId().getCustomerName();
							} else {
								val = null;
							}
						}

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		return column;

	}

}
