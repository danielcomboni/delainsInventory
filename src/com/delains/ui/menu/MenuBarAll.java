package com.delains.ui.menu;

import javafx.scene.control.MenuBar;

public class MenuBarAll extends MenuBar {

	private MenuFile menuFile;
	private MenuReport menuReport;

	public MenuBarAll() {

		this.setId( "main_borderpane" );

		getStylesheets().add( MenuBarAll.class.getResource( "menuBar.css" ).toExternalForm() );

		menuFile = new MenuFile();
		this.getMenus().add( menuFile );

		menuReport = new MenuReport();
		this.getMenus().add( menuReport );

	}

}
