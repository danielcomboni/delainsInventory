package com.delains.ui.sales;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.model.pos.ReceiptHeader;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.StageForAlerts;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ReceiptHeaderDialog extends BorderPane {

	private GridPane gridPane;

	private Label labelBusinessname;
	private TextField fieldBusinessName;

	private Label labelLocation;
	private TextField fieldLocation;

	private Label labelContact;
	private TextField fieldContact;

	private JFXButton buttonSave;

	public ReceiptHeaderDialog() {

		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labelBusinessname = new Label( "business name:" );
		gridPane.add( labelBusinessname, 0, 0 );

		fieldBusinessName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldBusinessName, 400 );
		gridPane.add( fieldBusinessName, 1, 0 );

		labelLocation = new Label( "location:" );
		gridPane.add( labelLocation, 0, 1 );

		fieldLocation = new TextField();
		ComponentWidth.setWidthOfTextField( fieldLocation, 200 );
		gridPane.add( fieldLocation, 1, 1 );

		labelContact = new Label( "contact:" );
		gridPane.add( labelContact, 0, 2 );

		fieldContact = new TextField();
		ComponentWidth.setWidthOfTextField( fieldContact, 200 );
		gridPane.add( fieldContact, 1, 2 );

		HBox box = new HBox( 10 );
		buttonSave = new JFXButton( "save" );
		box.getChildren().add( buttonSave );
		gridPane.add( box, 1, 3 );

		this.setCenter( gridPane );

		buttonSave.setOnAction( e -> savereceiptHeader() );

	}

	private void savereceiptHeader() {
		new StageForAlerts();
		String name = null;

		if ( !fieldBusinessName.getText().isEmpty() ) {
			name = fieldBusinessName.getText();
		} else {
			StageForAlerts.inform( "alert", "please specify the business name or title" );
			return;
		}

		String location = null;
		if ( !fieldLocation.getText().isEmpty() ) {
			location = fieldLocation.getText();
		} else {
			StageForAlerts.inform( "alert", "please location of the business" );
			return;
		}

		String contact = null;
		if ( !fieldContact.getText().isEmpty() ) {
			contact = fieldContact.getText();
		}

		ReceiptHeader header = new ReceiptHeader();
		header.setBusinessName( name );
		header.setContact( contact );
		header.setLocation( location );

		ReceiptHeaderDAO.newReceiptHeader( header );

		FieldClearance.clearTextField( fieldBusinessName );
		FieldClearance.clearTextField( fieldContact );
		FieldClearance.clearTextField( fieldLocation );

	}

}
