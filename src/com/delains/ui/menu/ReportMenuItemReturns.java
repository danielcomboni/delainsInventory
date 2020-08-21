package com.delains.ui.menu;

import com.delains.ui.report.ReportAllReturnInwards;
import com.delains.ui.report.ReportAllReturnOutwards;
import com.delains.ui.report.ReportReturnInwardsBetweenDates;
import com.delains.ui.report.ReportReturnInwardsByCustomer;
import com.delains.ui.report.ReportReturnInwardsByCustomerBetweenDates;
import com.delains.ui.utils.ReportPopUp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ReportMenuItemReturns extends Menu {

	private ObservableList < MenuItem > menuItemsReportReturns;

	public ObservableList < MenuItem > getMenuItemsReportReturns() {
		return menuItemsReportReturns;
	}

	public void setMenuItemsReportReturns( ObservableList < MenuItem > menuItemsReportReturns ) {
		this.menuItemsReportReturns = menuItemsReportReturns;
	}

	private MenuItem menuItemAllReturnsInwards;
	private MenuItem menuItemReturnsInwardsByCustomer;
	private MenuItem menuItemReturnsInwardsByCustomerBetweenDates;
	private MenuItem menuItemReturnsInwardsBetweenDates;
	private MenuItem menuItemAllReturnsOutwards;
	private MenuItem menuItemReturnsOutwardsBetweenDates;

	public ReportMenuItemReturns() {

		this.setText( "returns" );

		ObservableList < MenuItem > items = FXCollections.observableArrayList();

		items.add( new SeparatorMenuItem() );
		menuItemAllReturnsInwards = new MenuItem( "all return inwards (sales returns)" );
		items.add( menuItemAllReturnsInwards );

		items.add( new SeparatorMenuItem() );
		menuItemReturnsInwardsByCustomer = new MenuItem( "return inwards by customer" );
		items.add( menuItemReturnsInwardsByCustomer );

		items.add( new SeparatorMenuItem() );
		menuItemReturnsInwardsByCustomerBetweenDates = new MenuItem( "return inwards by customer between dates" );
		items.add( menuItemReturnsInwardsByCustomerBetweenDates );

		items.add( new SeparatorMenuItem() );
		menuItemReturnsInwardsBetweenDates = new MenuItem( "return inwards between dates" );
		items.add( menuItemReturnsInwardsBetweenDates );

		items.add( new SeparatorMenuItem() );
		menuItemAllReturnsOutwards = new MenuItem( "all return outwards (purchases returns)" );
		items.add( menuItemAllReturnsOutwards );

		items.add( new SeparatorMenuItem() );
		menuItemReturnsOutwardsBetweenDates = new MenuItem( "return outwards by date" );
//		items.add( menuItemReturnsOutwardsBetweenDates );
		items.add( new SeparatorMenuItem() );

		this.getItems().addAll( items );

		listening();

	}

	private ReportPopUp reportPopUp;

	private void listening() {
		menuItemAllReturnsInwards.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportAllReturnInwards(), "all sales (inward) return" );
			reportPopUp.showAndWait();
		} );
		menuItemReturnsInwardsByCustomer.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportReturnInwardsByCustomer(), "sales (inward) returns by customer" );
			reportPopUp.showAndWait();
		} );
		menuItemReturnsInwardsByCustomerBetweenDates.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportReturnInwardsByCustomerBetweenDates(),
					"sales (inward) returns by customer between dates" );
			reportPopUp.showAndWait();
		} );
		menuItemReturnsInwardsBetweenDates.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportReturnInwardsBetweenDates(),
					"sales (inward) returns between dates" );
			reportPopUp.showAndWait();
		} );
		menuItemAllReturnsOutwards.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportAllReturnOutwards(), "purchases (outward) returns" );
			reportPopUp.showAndWait();
		} );
	}

}