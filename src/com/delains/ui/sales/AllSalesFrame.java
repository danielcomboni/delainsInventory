package com.delains.ui.sales;

import java.math.BigDecimal;

import com.delains.dao.pos.POSHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class AllSalesFrame extends BorderPane {

	public AllSalesFrame() {
		this.setId( "main_borderpane" );

		getStylesheets().add( AllSalesFrame.class.getResource( "allSales.css" ).toExternalForm() );

		buildTable();


		tableView.setItems(AllSalesManipulation.data);


	}

	private static TableView < POS > tableView;
	private TableColumn < POS, String > colDate;
	private TableColumn < POS, String > colItem;
	private TableColumn < POS, String > colQty;
	private TableColumn < POS, String > colCost;
	private TableColumn < POS, String > colTotalCost;
	private TableColumn < POS, Boolean > colCredit;
	private TableColumn < POS, String > colDiscount;
	private TableColumn < POS, String > colAmountPaid;
	private TableColumn < POS, String > colTotalRequired;
	private TableColumn < POS, String > colChange;
	private TableColumn < POS, String > colBalance;

	public static TableView < POS > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < POS > tableView ) {
		AllSalesFrame.tableView = tableView;
	}

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "Date" );
		colDate.setCellValueFactory(param -> {
					POS pos = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( pos.getDate() );

					if ( pos.getQuantity().doubleValue() == 0 ) {

						simpleStringProperty.setValue( null );

					}

					StringProperty property = simpleStringProperty;

					return property;
				});
		tableView.getColumns().add( colDate );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();
						Item item = pos.getItemId();
						String itemName = item.getItemName();

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( itemName );
						if ( pos.getQuantity().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}
						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
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
		tableView.getColumns().add( colItem );

		colQty = new TableColumn <>( "Quantity" );
		colQty.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal price = pos.getQuantity();
						String priceString = NumberFormatting.formatToEnglish( price.toString() );
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( priceString );

						if ( pos.getQuantity().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableView.getColumns().add( colQty );

		colCost = new TableColumn <>( "Price" );
		colCost.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal price = pos.getPrice();
						String priceString = NumberFormatting.formatToEnglish( price.toString() );
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( priceString );

						if ( pos.getQuantity().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableView.getColumns().add( colCost );

		colTotalCost = new TableColumn <>( "Cost" );
		colTotalCost.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();
						BigDecimal total = pos.getCost();
						String totalString = NumberFormatting.formatToEnglish( total.toString() );
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( totalString );

						if ( pos.getQuantity().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableView.getColumns().add( colTotalCost );

		colCredit = new TableColumn <>( "credit?" );
		colCredit.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, Boolean >, ObservableValue < Boolean > >() {

					@Override
					public ObservableValue < Boolean > call( CellDataFeatures < POS, Boolean > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();
						boolean credit = pos.isCredit();
						SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty();
						simpleBooleanProperty.setValue( credit );

						BooleanProperty property = simpleBooleanProperty;
						return property;
					}
				} );

		colCredit.setCellFactory( tc -> new CheckBoxTableCell <>() );
		tableView.getColumns().add( colCredit );

		colDiscount = new TableColumn <>( "Discount" );
		colDiscount.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub
						POS pos = param.getValue();
						BigDecimal discount = pos.getDiscountAllowed();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( discount.toString() ) );

						if ( pos.getQuantity().doubleValue() != 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		// tableView.getColumns().add( colDiscount );

		colAmountPaid = new TableColumn <>( "Amount\npaid" );
		colAmountPaid.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal amountPaid = pos.getAmountPaid();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( amountPaid.toString() ) );

						if ( pos.getQuantity().doubleValue() != 0 ) {

							simpleStringProperty.setValue( null );

						}

						if ( pos.getAmountPaid().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colAmountPaid );

		colTotalRequired = new TableColumn <>( "Total" );
		colTotalRequired.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal amountPaid = pos.getTotalCost();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( amountPaid.toString() ) );

						if ( pos.getQuantity().doubleValue() != 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colTotalRequired );

		colChange = new TableColumn <>( "Change" );
		colChange.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal amountPaid = pos.getChange();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( amountPaid.toString() ) );

						if ( pos.getQuantity().doubleValue() != 0 ) {

							simpleStringProperty.setValue( null );

						}

						if ( pos.getChange().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableView.getColumns().add( colChange );

		colBalance = new TableColumn <>( "Balance" );

		colBalance.setCellValueFactory(

				new Callback < TableColumn.CellDataFeatures < POS, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < POS, String > param ) {
						// TODO Auto-generated method stub

						POS pos = param.getValue();
						BigDecimal amountPaid = pos.getBalanceToBePaidByCustomer();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( amountPaid.toString() ) );

						if ( pos.getQuantity().doubleValue() != 0 ) {

							simpleStringProperty.setValue( null );

						}

						if ( pos.getBalanceToBePaidByCustomer().doubleValue() == 0 ) {

							simpleStringProperty.setValue( null );

						}

						StringProperty property = simpleStringProperty;

						return property;

					}
				} );
		tableView.getColumns().add( colBalance );

		this.setCenter( tableView );

		clickRow();

	}

	private void clickRow() {

		tableView.setRowFactory( tr -> {

			TableRow < POS > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					POS pos = row.getItem();
				}
			} );

			return row;
		} );

	}

}
