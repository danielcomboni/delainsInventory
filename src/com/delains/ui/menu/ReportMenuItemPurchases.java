package com.delains.ui.menu;

import com.delains.ui.report.ReportAllPurchases;
import com.delains.ui.report.ReportPurchasesCashBetweenDates;
import com.delains.ui.report.ReportPurchasesCashOnly;
import com.delains.ui.report.ReportPurchasesCreditBetweenDates;
import com.delains.ui.report.ReportPurchasesCreditOnly;
import com.delains.ui.utils.ReportPopUp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class ReportMenuItemPurchases extends Menu {

	private ObservableList < MenuItem > menuItemsReportPurchases;

	public ObservableList < MenuItem > getMenuItemsReportPurchases() {
		return menuItemsReportPurchases;
	}

	public void setMenuItemsReportPurchases( ObservableList < MenuItem > menuItemsReportPurchases ) {
		this.menuItemsReportPurchases = menuItemsReportPurchases;
	}

	private MenuItem menuItemAllPurchases;
	private MenuItem menuItemCashPurchasesOnly;
	private MenuItem menuItemCreditPurchasesOnly;
	private MenuItem menuItemCashPurchasesBetweenDates;
	private MenuItem menuItemCreditPurchasesBetweenDates;

	private ReportPopUp reportPopUp;

	public ReportMenuItemPurchases() {

		this.setText( "purchases" );

		ObservableList < MenuItem > items = FXCollections.observableArrayList();

		items.add( new SeparatorMenuItem() );
		menuItemAllPurchases = new MenuItem( "all purchases (cash & credit)" );
		items.add( menuItemAllPurchases );

		menuItemCashPurchasesOnly = new MenuItem( "cash purchases only" );
		items.add( new SeparatorMenuItem() );
		items.add( menuItemCashPurchasesOnly );

		menuItemCreditPurchasesOnly = new MenuItem( "credit purchases only" );
		items.add( new SeparatorMenuItem() );
		items.add( menuItemCreditPurchasesOnly );

		items.add( new SeparatorMenuItem() );
		menuItemCashPurchasesBetweenDates = new MenuItem( "cash purchases between dates" );
		items.add( menuItemCashPurchasesBetweenDates );

		items.add( new SeparatorMenuItem() );
		menuItemCreditPurchasesBetweenDates = new MenuItem( "credit purchases between dates" );
		items.add( menuItemCreditPurchasesBetweenDates );
		items.add( new SeparatorMenuItem() );

		this.getItems().addAll( items );

		listeningToMenuActions();

	}

	private void listeningToMenuActions() {

		menuItemAllPurchases.setOnAction( e -> {
			ReportAllPurchases reportAllSales = new ReportAllPurchases();
			reportPopUp = new ReportPopUp( reportAllSales, "all purchases report" );
			reportPopUp.showAndWait();
		} );

		menuItemCashPurchasesOnly.setOnAction( e -> {
			ReportPurchasesCashOnly cashOnly = new ReportPurchasesCashOnly();
			reportPopUp = new ReportPopUp( cashOnly, "cash purchases report" );
			reportPopUp.showAndWait();
		} );

		menuItemCreditPurchasesOnly.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportPurchasesCreditOnly(), "credit purchases report" );
			reportPopUp.showAndWait();
		} );

		menuItemCashPurchasesBetweenDates.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportPurchasesCashBetweenDates(),
					"cash purchases report between dates" );
			reportPopUp.showAndWait();
		} );

		menuItemCreditPurchasesBetweenDates.setOnAction( e -> {
			reportPopUp = new ReportPopUp( new ReportPurchasesCreditBetweenDates(),
					"credit purchases report between dates" );
			reportPopUp.showAndWait();
		} );

	}

}
