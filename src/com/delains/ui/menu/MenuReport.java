package com.delains.ui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.SeparatorMenuItem;

public class MenuReport extends Menu {

	private ReportMenuItemPOS reportMenuItemPOS;
	private ReportMenuItemPurchases reportMenuItemPurchases;
	private ReportMenuItemStock reportMenuItemStock;
	private ReportMenuItemReturns reportMenuItemReturns;

	public MenuReport() {

		this.setText( "Report" );

		this.getItems().add( new SeparatorMenuItem() );
		reportMenuItemPOS = new ReportMenuItemPOS();
		this.getItems().add( reportMenuItemPOS );

		this.getItems().add( new SeparatorMenuItem() );
		reportMenuItemPurchases = new ReportMenuItemPurchases();
		this.getItems().add( reportMenuItemPurchases );

		this.getItems().add( new SeparatorMenuItem() );
		reportMenuItemStock = new ReportMenuItemStock();
		this.getItems().add( reportMenuItemStock );

		this.getItems().add( new SeparatorMenuItem() );
		reportMenuItemReturns = new ReportMenuItemReturns();
		this.getItems().add( reportMenuItemReturns );
		this.getItems().add( new SeparatorMenuItem() );

	}

}
