package com.delains.ui.table;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class AddColumnsToTable < T > {

	private T t;

	public T getT() {
		return t;
	}

	public void setT( T t ) {
		this.t = t;
	}

	private TableColumnGeneric < T > tableColumnGeneric;

	public TableColumnGeneric < T > getTableColumnGeneric() {
		return tableColumnGeneric;
	}

	public void setTableColumnGeneric( TableColumnGeneric < T > tableColumnGeneric ) {
		this.tableColumnGeneric = tableColumnGeneric;
	}

	private TableViewGeneric < T > tableViewGeneric;

	public TableViewGeneric < T > getTableViewGeneric() {
		return tableViewGeneric;
	}

	public void setTableViewGeneric( TableViewGeneric < T > tableViewGeneric ) {
		this.tableViewGeneric = tableViewGeneric;
	}

	public AddColumnsToTable( T tableClass ) {

		TableView < T > tableView = new TableView <>();

		tableViewGeneric = new TableViewGeneric <>();
		tableViewGeneric.setTableView( tableView );

		tableViewGeneric.getTableView().setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableViewGeneric.getTableView().setTableMenuButtonVisible( true );

		tableColumnGeneric = new TableColumnGeneric <>();

		this.tableViewGeneric.setT( tableClass );

		this.tableColumnGeneric.setT( tableClass );

		tableSelectedRow();

	}

	public AddColumnsToTable() {

	}

	public TableColumn < T, String > addStringColumnToTable( String columHeader ) {

		TableColumn < T, String > col = new TableColumn <>( columHeader );

		if ( this.tableColumnGeneric.getListOfColumns() == null ) {

			ObservableList < TableColumn < T, String > > listOfColumns = FXCollections.observableArrayList();

			textWrap( col );

			listOfColumns.add( col );

			this.tableColumnGeneric.setListOfColumns( listOfColumns );
			this.tableViewGeneric.getTableView().getColumns().addAll( this.tableColumnGeneric.getListOfColumns() );

		} else {

			textWrap( col );

			this.getTableColumnGeneric().getListOfColumns().add( col );

			this.tableViewGeneric.getTableView().getColumns().clear();
			this.tableViewGeneric.getTableView().getColumns().addAll( this.tableColumnGeneric.getListOfColumns() );

		}

		return col;

	}

	public void populateTableRefresh( ObservableList < T > listToPopulateTableWithRefreshing ) {

		if ( this.tableViewGeneric.getTableView().getItems() == null ) {
			this.tableViewGeneric.getTableView().setItems( listToPopulateTableWithRefreshing );
		} else {
			this.tableViewGeneric.getTableView().getItems().clear();
			this.tableViewGeneric.getTableView().getItems().addAll( listToPopulateTableWithRefreshing );
		}

	}

	public void populateTableWithoutRefreshing( ObservableList < T > listToPopulateTableWithoutRefreshing ) {

		this.tableViewGeneric.getTableView().getItems().clear();
		this.tableViewGeneric.getTableView().getItems().addAll( listToPopulateTableWithoutRefreshing );

	}

	private void textWrap( TableColumn < T, String > column ) {

		column.setCellFactory( tc -> {

			TableCell < T, String > cell = new TableCell <>();

			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( column.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );

			return cell;

		} );

	}

	private T selected_T_valueFromTableRow;

	public T getSelected_T_valueFromTableRow() {
		return selected_T_valueFromTableRow;
	}

	public void setSelected_T_valueFromTableRow( T selected_T_valueFromTableRow ) {
		this.selected_T_valueFromTableRow = selected_T_valueFromTableRow;
	}

	private int selected_Row_Index;

	public int getSelected_Row_Index() {
		return selected_Row_Index;
	}

	public void setSelected_Row_Index( int selected_Row_Index ) {
		this.selected_Row_Index = selected_Row_Index;
	}

	public T tableSelectedRow() {
		this.tableViewGeneric.getTableView().getSelectionModel().selectedItemProperty().addListener( e -> {

			T t = this.tableViewGeneric.getTableView().selectionModelProperty().getValue().getSelectedItem();
setSelected_T_valueFromTableRow( t );
			setSelected_Row_Index( this.tableViewGeneric.getTableView().getSelectionModel().getSelectedIndex() );

} );
		return getSelected_T_valueFromTableRow();
	}

}
