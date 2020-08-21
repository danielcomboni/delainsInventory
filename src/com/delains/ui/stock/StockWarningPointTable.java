package com.delains.ui.stock;

import com.delains.dao.stock.StockWarningPointHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.stock.StockWarningPoint;
import com.delains.ui.table.AddColumnsToTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

public class StockWarningPointTable {

	private static AddColumnsToTable < StockWarningPoint > addColumnsToTable;

	public static AddColumnsToTable < StockWarningPoint > getAddColumnsToTable() {
		return addColumnsToTable;
	}

	public static void setAddColumnsToTable( AddColumnsToTable < StockWarningPoint > addColumnsToTable ) {
		StockWarningPointTable.addColumnsToTable = addColumnsToTable;
	}

	public static TableView < StockWarningPoint > getTableView() {

		StockWarningPoint StockWarningPoint = new StockWarningPoint();
		addColumnsToTable = new AddColumnsToTable <>( StockWarningPoint );

		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "item" ), "item" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "warning quantity" ), "warning quantity" );

		return addColumnsToTable.getTableViewGeneric().getTableView();

	}

	public static void populateTableWithRefreshing() {

		addColumnsToTable.populateTableRefresh(
				StockWarningPointHibernation.findAllStockWarningPointsObservableListRefreshed() );

	}

	public static void populatetableWithoutRefreshing() {
		addColumnsToTable.populateTableWithoutRefreshing(
				StockWarningPointHibernation.findAllStockWarningPointsObservableListRefreshed() );
	}

	private static TableColumn < StockWarningPoint, String > setColumnValueFactory(
			TableColumn < StockWarningPoint, String > column, String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < StockWarningPoint, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < StockWarningPoint, String > param ) {

						StockWarningPoint pc = param.getValue();

						String val = null;

						if ( decideValue.equalsIgnoreCase( "item" ) ) {
							val = pc.getItemId().getItemName();
						}

						if ( decideValue.equalsIgnoreCase( "warning quantity" ) ) {

							val = NumberFormatting.formatToEnglish( pc.getQuantityLimit().toString() );

						}

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		return column;

	}

	private static StockWarningPoint itemPrev;

	public static StockWarningPoint getStockWarningPointPrev() {
		return itemPrev;
	}

	public static void setStockWarningPointPrev( StockWarningPoint itemPrev ) {
		StockWarningPointTable.itemPrev = itemPrev;
	}

	public static StockWarningPoint clickRow() {

		getTableView().setRowFactory( tr -> {

			TableRow < StockWarningPoint > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					StockWarningPoint purchase = row.getItem();

					// this.setIdOfSupplier(s.getId());

itemPrev = purchase;

				}
			} );

			return row;
		} );
		return itemPrev;
	}

}
