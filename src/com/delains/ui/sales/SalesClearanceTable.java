package com.delains.ui.sales;

import com.delains.dao.utils.NumberFormatting;
import com.delains.model.sales.SalesClearance;
import com.delains.ui.invoker.Refresh;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class SalesClearanceTable {
	public SalesClearanceTable() {
		buildTableColumns();
	}

	private static TableView < SalesClearance > tableView;

	public static TableView < SalesClearance > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SalesClearance > tableView ) {
		SalesClearanceTable.tableView = tableView;
	}

	private TableColumn < SalesClearance, String > colDate;
	private TableColumn < SalesClearance, String > colCreditor;
	private TableColumn < SalesClearance, String > colAmountCleared;
	private TableColumn < SalesClearance, String > colBalance;

	private void buildTableColumns() {

		tableView = new TableView <>();

		tableView.setPrefWidth( 1200 );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "date" );
		textWrap( colDate );
		colDate = setColumnValueFactory( colDate, "date" );
		tableView.getColumns().add( colDate );

		colCreditor = new TableColumn <>( "customer" );
		textWrap( colCreditor );
		colCreditor = setColumnValueFactory( colCreditor, "customer" );
		tableView.getColumns().add( colCreditor );

		colAmountCleared = new TableColumn <>( "amount\ncleared" );
		textWrap( colAmountCleared );
		colAmountCleared = setColumnValueFactory( colAmountCleared, "amount cleared" );
		tableView.getColumns().add( colAmountCleared );

		colBalance = new TableColumn <>( "balance" );
		textWrap( colBalance );
		colBalance = setColumnValueFactory( colBalance, "balance" );
		tableView.getColumns().add( colBalance );

		Refresh.setRefreshingDeterminant( 0 );
		SalesClearanceManipulation.populateTable( tableView );

	}

	private void textWrap( TableColumn < SalesClearance, String > column ) {

		column.setCellFactory( tr -> {
			TableCell < SalesClearance, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( column.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;
		} );

	}

	private TableColumn < SalesClearance, String > setColumnValueFactory( TableColumn < SalesClearance, String > column,
			String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < SalesClearance, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < SalesClearance, String > param ) {

						SalesClearance pc = param.getValue();

						String val = null;

						if ( decideValue.equalsIgnoreCase( "date" ) ) {
							val = pc.getDate();
						}

						if ( decideValue.equalsIgnoreCase( "customer" ) ) {

							if ( pc.getPosId().getCustomerId() != null ) {
								val = pc.getPosId().getCustomerId().getCustomerName();
							} else {
								val = null;
							}

						}

						if ( decideValue.equalsIgnoreCase( "amount cleared" ) ) {
							val = NumberFormatting.formatToEnglish( pc.getAmountCleared().toString() );
						}

						if ( decideValue.equalsIgnoreCase( "balance" ) ) {
							if ( pc.getBalanceToBeCleared().doubleValue() > 0 ) {
								val = NumberFormatting.formatToEnglish( pc.getBalanceToBeCleared().toString() );
							} else {
								val = "cleared";
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
