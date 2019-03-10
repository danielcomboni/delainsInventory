package com.delains.ui.history;

import javafx.scene.layout.BorderPane;

public class AuditHistoryFrame extends BorderPane {

	public AuditHistoryFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( AuditHistoryFrame.class.getResource( "auditHistory.css" ).toExternalForm() );

		this.setCenter( AuditHistoryManipulation.getTableView() );

		AuditHistoryManipulation.populateTableWithRefreshing();

	}

}
