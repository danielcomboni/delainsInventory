package com.delains.ui.table;

import javafx.scene.control.TableView;

public class TableViewGeneric < T > {

	private T t;

	public T getT() {
		return t;
	}

	public void setT( T t ) {
		this.t = t;
	}

	public TableView < T > getTableView() {
		return tableView;
	}

	public void setTableView( TableView < T > tableView ) {
		this.tableView = tableView;
	}

	private TableView < T > tableView;

}
