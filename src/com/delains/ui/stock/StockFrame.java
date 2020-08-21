package com.delains.ui.stock;

import java.math.BigDecimal;

import com.delains.dao.stock.StockHibernation;
import com.delains.dao.stock.StockHistoryHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.stock.Stock;
import com.delains.model.stock.StockHistory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class StockFrame extends BorderPane {

	public StockFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( StockFrame.class.getResource( "stock.css" ).toExternalForm() );

		buildTopOfThisFrame();
		buildTables();

		// stockQuantityWarningPointDialog = new StockQuantityWarningPointDialog();

	}

	// private StockQuantityWarningPointDialog stockQuantityWarningPointDialog;

	private TableView < Stock > tableViewStock;
	private TableColumn < Stock, String > colItem;
	private TableColumn < Stock, String > colQuantity;

	public TableView < Stock > getTableViewStock() {
		return tableViewStock;
	}

	public void setTableViewStock( TableView < Stock > tableViewStock ) {
		this.tableViewStock = tableViewStock;
	}

	public static TableView < Stock > getTableView() {
		return new StockFrame().getTableViewStock();
	}

	private TableView < Stock > buildStockTable() {

		tableViewStock = new TableView <>();
		tableViewStock.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableViewStock.setTableMenuButtonVisible( true );

		colItem = new TableColumn <>( "Item" );
		colItem.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Stock, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Stock, String > param ) {
						// TODO Auto-generated method stub
						Stock stock = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						StringProperty property = null;

						if ( stock != null && stock.getItemId() != null ) {
							simpleStringProperty = new SimpleStringProperty();
							simpleStringProperty.setValue( stock.getItemId().getItemName() );
							property = simpleStringProperty;
						}

						return property;
					}
				} );
		tableViewStock.getColumns().add( colItem );

		colQuantity = new TableColumn <>( "Quantity in stock" );
		colQuantity.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Stock, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Stock, String > param ) {
						// TODO Auto-generated method stub
						Stock stock = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						String qtyFormmated = NumberFormatting.formatToEnglish( stock.getItemQuantity().toString() );
						simpleStringProperty.setValue( qtyFormmated );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableViewStock.getColumns().add( colQuantity );

		this.setCenter( tableViewStock );

		return tableViewStock;

	}

	private TableView < StockHistory > tableViewStockHistory;
	private TableColumn < StockHistory, String > colDateHistory;
	private TableColumn < StockHistory, String > colItemHistory;
	private TableColumn < StockHistory, String > colStockQuantity;

	private TableView < StockHistory > buildStockHistoryTable() {

		tableViewStockHistory = new TableView <>();
		tableViewStockHistory.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableViewStockHistory.setTableMenuButtonVisible( true );

		colDateHistory = new TableColumn <>( "Date purchased" );
		colDateHistory.setCellValueFactory( new PropertyValueFactory <>( "date" ) );
		tableViewStockHistory.getColumns().add( colDateHistory );

		colItemHistory = new TableColumn <>( "Item" );
		colItemHistory.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < StockHistory, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < StockHistory, String > param ) {
						// TODO Auto-generated method stub
						StockHistory history = param.getValue();
						Stock stock = history.getStockId();
						Item item = stock.getItemId();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();

						StringProperty property = null;

						if ( stock != null && item != null && history != null ) {
							simpleStringProperty = new SimpleStringProperty();
							simpleStringProperty.setValue( item.getItemName() );
							property = simpleStringProperty;
						}

						return property;
					}
				} );
		tableViewStockHistory.getColumns().add( colItemHistory );

		colStockQuantity = new TableColumn <>( "Quantity purchased" );
		colStockQuantity.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < StockHistory, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < StockHistory, String > param ) {
						// TODO Auto-generated method stub
						StockHistory history = param.getValue();
						BigDecimal stockQty = history.getStockQuantity();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( NumberFormatting.formatToEnglish( stockQty.toString() ) );
						StringProperty property = simpleStringProperty;
						return property;
					}
				} );
		tableViewStockHistory.getColumns().add( colStockQuantity );

		return tableViewStockHistory;
	}

	private VBox vBoxTables;

	private void buildTables() {

		this.vBoxTables = new VBox();

		this.vBoxTables.getChildren().add( this.buildStockTable() );

		this.vBoxTables.getChildren().add( this.buildStockHistoryTable() );

		this.setCenter( this.vBoxTables );

		populateTablesWithoutrefreshing();

	}

	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing stock and stock history" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );
	}

	public void populateTablesWithoutrefreshing() {
		this.tableViewStock.setItems( StockHibernation.findAllStockObservableList() );
		this.tableViewStockHistory.setItems( StockHistoryHibernation.findAllStockHistorysObservableList() );
	}

	public void populateTablesWithRefreshing() {

		this.tableViewStock.getItems().clear();
		this.tableViewStock.getItems().addAll( StockHibernation.findAllStockObservableListRefreshed() );

		this.tableViewStockHistory.getItems().clear();
		this.tableViewStockHistory.getItems()
				.addAll( StockHistoryHibernation.findAllStockHistorysObservableListRefreshed() );
	}

}
