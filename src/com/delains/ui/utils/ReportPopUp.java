package com.delains.ui.utils;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ReportPopUp extends Stage {

	private BorderPane borderPane;
	private Scene scene;

	public ReportPopUp() {

	}

	public ReportPopUp( Node nod, String stageTitle ) {

		this.initModality( Modality.APPLICATION_MODAL );

		this.setTitle( stageTitle );

		this.setResizable( true );

		this.borderPane = new BorderPane();
		this.borderPane.setId( "main_borderpane" );
		this.borderPane.setCenter( nod );

		scene = new Scene( borderPane );

		scene.getStylesheets().add( getClass().getResource( "alerts.css" ).toExternalForm() );

		this.setScene( scene );

	}

}
