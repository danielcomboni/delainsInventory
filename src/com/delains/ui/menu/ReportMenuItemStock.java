package com.delains.ui.menu;

import com.delains.ui.report.ReportAllStock;
import com.delains.ui.utils.ReportPopUp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ReportMenuItemStock extends Menu {

	private ObservableList < MenuItem > menuItemsReportStock;

	public ObservableList < MenuItem > getMenuItemsReportStock() {
		return menuItemsReportStock;
	}

	public void setMenuItemsReportStock( ObservableList < MenuItem > menuItemsReportStock ) {
		this.menuItemsReportStock = menuItemsReportStock;
	}

	private MenuItem menuItemAllAvailableStock;
	private MenuItem menuItemAvailableStockByItem;

	public ReportMenuItemStock() {

		this.setText( "stock" );

		ObservableList < MenuItem > items = FXCollections.observableArrayList();
		items.add( new SeparatorMenuItem() );

		menuItemAllAvailableStock = new MenuItem( "all available" );
		items.add( menuItemAllAvailableStock );
		items.add( new SeparatorMenuItem() );

		menuItemAvailableStockByItem = new MenuItem( "available stock by item" );
//		items.add( menuItemAvailableStockByItem );
//		items.add( new SeparatorMenuItem() );

		this.getItems().addAll( items );

		listening();
	}

	private ReportPopUp reportPopUp;

	private void listening() {
		menuItemAllAvailableStock.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportAllStock(), "all stock (inventory) available" );
			reportPopUp.showAndWait();
		} );
	}
}