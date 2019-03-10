package com.delains.ui.purchases;

import com.delains.dao.purchases.PurchaseReturnClearanceHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.purchases.PurchaseReturnClearance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class PurchaseReturnClearanceTable extends BorderPane {

	private TableView < PurchaseReturnClearance > tableView;
	private TableColumn < PurchaseReturnClearance, String > colDate;
	private TableColumn < PurchaseReturnClearance, String > colSupplier;
	private TableColumn < PurchaseReturnClearance, String > colAmountReceivedBack;
	private TableColumn < PurchaseReturnClearance, String > colItem;

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		colDate = new TableColumn <>( "Date" );
		colDate.setCellFactory( tc -> getTableCellWithTextWrapper() );
		colDate.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < PurchaseReturnClearance, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call(
							CellDataFeatures < PurchaseReturnClearance, String > param ) {
						PurchaseReturnClearance pc = param.getValue();
						return getStringPropertyWithValue( pc.getDate() );
					}
				} );
		tableView.getColumns().add( colDate );

		colSupplier = new TableColumn <>( "Supplier" );
		colSupplier.setCellFactory( tc -> getTableCellWithTextWrapper() );
		colSupplier.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < PurchaseReturnClearance, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call(
							CellDataFeatures < PurchaseReturnClearance, String > param ) {

						if ( param.getValue().getPurchaseReturnId().getPurchaseId().getSupplierId() != null ) {

							return getStringPropertyWithValue( param.getValue().getPurchaseReturnId().getPurchaseId()
									.getSupplierId().getSupplierName() );

						}

				else {
							return null;
						}

					}
				} );
		tableView.getColumns().add( colSupplier );

		colAmountReceivedBack = new TableColumn <>( "Amount received back" );
		colAmountReceivedBack.setCellFactory( tc -> getTableCellWithTextWrapper() );
		colAmountReceivedBack.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < PurchaseReturnClearance, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call(
							CellDataFeatures < PurchaseReturnClearance, String > param ) {

						return getStringPropertyWithValue(
								NumberFormatting.formatToEnglish( param.getValue().getAmountPaid().toString() ) );
					}
				} );
		tableView.getColumns().add( colAmountReceivedBack );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellFactory( tc -> getTableCellWithTextWrapper() );
		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < PurchaseReturnClearance, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call(
							CellDataFeatures < PurchaseReturnClearance, String > param ) {

						return getStringPropertyWithValue(
								param.getValue().getPurchaseReturnId().getPurchaseId().getItemId().getItemName() );

					}
				} );
		tableView.getColumns().add( colItem );

		this.setCenter( tableView );

		tableView.getItems().clear();
		tableView.getItems()
				.addAll( PurchaseReturnClearanceHibernation.findAllPurchaseReturnsClearanceObservableListRefreshed() );

	}

	private TableCell < PurchaseReturnClearance, String > getTableCellWithTextWrapper() {
		TableCell < PurchaseReturnClearance, String > cell = new TableCell <>();
		Text text = new Text();
		text.getStyleClass().add( "text-id" );
		cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
		text.wrappingWidthProperty().bind( colItem.widthProperty() );
		text.textProperty().bind( cell.itemProperty() );
		cell.setGraphic( text );

		return cell;

	}

	private StringProperty getStringPropertyWithValue( String valueToProperty ) {
		SimpleStringProperty simpleStringProperty = new SimpleStringProperty( valueToProperty );
		StringProperty property = simpleStringProperty;
		return property;
	}

	public PurchaseReturnClearanceTable() {
		buildTable();
	}

}
