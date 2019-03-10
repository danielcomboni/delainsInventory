package com.delains.ui.menu;

import com.delains.ui.report.ReportAllSales;
import com.delains.ui.report.ReportAllSalesByDate;
import com.delains.ui.report.ReportSalesCashBetweenDates;
import com.delains.ui.report.ReportSalesCashOnly;
import com.delains.ui.report.ReportSalesCreditBetweenDates;
import com.delains.ui.report.ReportSalesCreditByCustomer;
import com.delains.ui.report.ReportSalesCreditByCustomerBetweenDates;
import com.delains.ui.report.ReportSalesCreditOnly;
import com.delains.ui.utils.ReportPopUp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ReportMenuItemPOS extends Menu {

	private ObservableList < MenuItem > menuItemsReportSales;

	public ObservableList < MenuItem > getMenuItemsReportSales() {
		return menuItemsReportSales;
	}

	public void setMenuItemsReportSales( ObservableList < MenuItem > menuItemsReportSales ) {
		this.menuItemsReportSales = menuItemsReportSales;
	}

	private ReportPopUp reportPopUp;

	private MenuItem menuItemAllSales;
	private MenuItem menuItemAllSalesByDate;
	private MenuItem menuItemCashSalesOnly;
	private MenuItem menuItemcreditSalesOnly;
	private MenuItem menuItemCashSalesBetweenDates;
	private MenuItem menuItemCreditSalesBetweenDates;
	private MenuItem menuItemCreditSalesByCustomer;
	private MenuItem menuItemCreditSalesByCustomerBetweenDates;

	public ReportMenuItemPOS() {

		this.setText( "sales/POS" );

		ObservableList < MenuItem > items = FXCollections.observableArrayList();
		items.add( new SeparatorMenuItem() );

		menuItemAllSales = new MenuItem( "all sales (cash & credit)" );
		items.add( menuItemAllSales );
		items.add( new SeparatorMenuItem() );

		menuItemAllSalesByDate = new MenuItem( "all sales by date" );
		items.add( menuItemAllSalesByDate );
		items.add( new SeparatorMenuItem() );

		menuItemCashSalesOnly = new MenuItem( "cash sales only" );
		items.add( menuItemCashSalesOnly );
		items.add( new SeparatorMenuItem() );

		menuItemcreditSalesOnly = new MenuItem( "credit sales only" );
		items.add( menuItemcreditSalesOnly );
		items.add( new SeparatorMenuItem() );

		menuItemCashSalesBetweenDates = new MenuItem( "cash sales between dates" );
		items.add( menuItemCashSalesBetweenDates );
		items.add( new SeparatorMenuItem() );

		menuItemCreditSalesBetweenDates = new MenuItem( "credit sales between dates" );
		items.add( menuItemCreditSalesBetweenDates );
		items.add( new SeparatorMenuItem() );

		menuItemCreditSalesByCustomer = new MenuItem( "credit sales by customer" );
		items.add( menuItemCreditSalesByCustomer );
		items.add( new SeparatorMenuItem() );

		menuItemCreditSalesByCustomerBetweenDates = new MenuItem( "credit sales by customer between dates" );
		items.add( menuItemCreditSalesByCustomerBetweenDates );
		items.add( new SeparatorMenuItem() );

		this.getItems().addAll( items );

		listeningToMenuActions();

	}

	private void listeningToMenuActions() {

		menuItemAllSales.setOnAction( e -> {
			ReportAllSales reportAllSales = new ReportAllSales();
			reportPopUp = new ReportPopUp( reportAllSales, "all sales report" );
			reportPopUp.showAndWait();
		} );

		menuItemAllSalesByDate.setOnAction( e -> {
			ReportAllSalesByDate allSalesByDate = new ReportAllSalesByDate();
			reportPopUp = new ReportPopUp( allSalesByDate, "all sales by date" );
			reportPopUp.showAndWait();
		} );

		menuItemCashSalesOnly.setOnAction( e -> {
			ReportSalesCashOnly cashOnly = new ReportSalesCashOnly();
			reportPopUp = new ReportPopUp( cashOnly, "all cash sales report" );
			reportPopUp.showAndWait();
		} );

		menuItemcreditSalesOnly.setOnAction( e -> {
			ReportSalesCreditOnly creditOnly = new ReportSalesCreditOnly();
			reportPopUp = new ReportPopUp( creditOnly, "all credit sales report" );
			reportPopUp.showAndWait();
		} );

		menuItemCashSalesBetweenDates.setOnAction( e -> {
			ReportSalesCashBetweenDates betweenDates = new ReportSalesCashBetweenDates();
			reportPopUp = new ReportPopUp( betweenDates, "cash sales between dates report" );
			reportPopUp.showAndWait();
		} );

		menuItemCreditSalesBetweenDates.setOnAction( e -> {
			ReportSalesCreditBetweenDates betweenDates = new ReportSalesCreditBetweenDates();
			reportPopUp = new ReportPopUp( betweenDates, "credit sales between dates report" );
			reportPopUp.showAndWait();
		} );

		menuItemCreditSalesByCustomer.setOnAction( e -> {
			ReportSalesCreditByCustomer byCustomer = new ReportSalesCreditByCustomer();
			reportPopUp = new ReportPopUp( byCustomer, "credit sales by customer report" );
			reportPopUp.showAndWait();
		} );

		menuItemCreditSalesByCustomerBetweenDates.setOnAction( e -> {
			ReportSalesCreditByCustomerBetweenDates betweenDates = new ReportSalesCreditByCustomerBetweenDates();
			reportPopUp = new ReportPopUp( betweenDates, "credit sales by customer between dates report" );
			reportPopUp.showAndWait();
		} );

	}

}
