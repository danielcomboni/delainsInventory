package com.delains.ui.menu;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuFile extends Menu {

	private MenuItem menuItemExit;

	public MenuItem getMenuItemExit() {
		return menuItemExit;
	}

	public void setMenuItemExit( MenuItem menuItemExit ) {
		this.menuItemExit = menuItemExit;
	}

	public MenuFile() {

		this.setText( "File" );

		menuItemExit = new MenuItem( "exit" );
		this.getItems().add( menuItemExit );

		menuItemExit.setOnAction( e -> exitClose() );

	}

	private void exitClose() {

		System.exit( 0 );

	}

}
