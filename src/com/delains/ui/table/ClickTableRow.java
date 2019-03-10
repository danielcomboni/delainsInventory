package com.delains.ui.table;

import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;

public class ClickTableRow < T > {

	private T rowClicked;

	public T getRowClicked() {
		return rowClicked;
	}

	private TableView < T > tableView;

	public TableView < T > getTableView() {
		return tableView;
	}

	public void setTableView( TableView < T > tableView ) {
		this.tableView = tableView;
	}

	public ClickTableRow( T tClass ) {

		clickRow();
		rowIndexClicked();

	}

	public void setRowClicked( T rowClicked ) {
		this.rowClicked = rowClicked;
	}

	public T clickRow() {

		this.tableView.setRowFactory( tr -> {

			TableRow < T > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					T t = row.getItem();

					rowClicked = t;

					System.out.println( "row clicked in clicktablrow class: " + t );

				}
			} );

			return row;
		} );

		return rowClicked;

	}

	public int rowIndexClicked() {
		int rowIndex = tableView.getSelectionModel().getSelectedIndex();
		return rowIndex;
	}

}
