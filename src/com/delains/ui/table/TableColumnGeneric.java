package com.delains.ui.table;

import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class TableColumnGeneric < T > {

	private T t;

	private TableColumn < T, String > tableColumString;

	private ObservableList < TableColumn < T, String > > listOfColumns;

	public ObservableList < TableColumn < T, String > > getListOfColumns() {
		return listOfColumns;
	}

	public void setListOfColumns( ObservableList < TableColumn < T, String > > listOfColumns ) {
		this.listOfColumns = listOfColumns;
	}

	public T getT() {
		return t;
	}

	public void setT( T t ) {
		this.t = t;
	}

	public TableColumn < T, String > getTableColumString() {
		return tableColumString;
	}

	public void setTableColumString( TableColumn < T, String > tableColumString ) {
		this.tableColumString = tableColumString;
	}

}
