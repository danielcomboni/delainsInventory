package com.delains.ui.stock;

import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StockTabInAccordion extends VBox {

	public StockTabInAccordion() {

		gridPane = new GridPane();
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		buttonSetWarningPoint = new JFXButton( "set qty warning point" );
		gridPane.add( buttonSetWarningPoint, 0, 0 );

		this.getChildren().add( gridPane );

		stockQuantityWarningPointDialog = new StockQuantityWarningPointDialog();

		buttonSetWarningPoint.setOnAction( e -> {
			stockQuantityWarningPointDialog.populateComboBox();
			stockQuantityWarningPointDialog.showPopUpToSetLimitInStock();
		} );

	}

	private StockQuantityWarningPointDialog stockQuantityWarningPointDialog;

	private JFXButton buttonSetWarningPoint;
	private GridPane gridPane;

}
