package com.delains.ui.history;

import com.delains.model.history.AuditHistory;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;


public class AuditHistoryFrame extends BorderPane {

	private static TableView<AuditHistory> tableView = new TableView<>();

	public AuditHistoryFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( AuditHistoryFrame.class.getResource( "auditHistory.css" ).toExternalForm() );

		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );
		tableView.setTableMenuButtonVisible( true );

		TableColumn<AuditHistory,String> columnDate = new TableColumn<>("Date");
		TableColumn<AuditHistory,String> columnAction = new TableColumn<>("Action");
		TableColumn<AuditHistory,String> columnUser = new TableColumn<>("User");

		columnDate.setCellValueFactory(param -> param.getValue().dateProperty());

		columnAction.setCellValueFactory(param -> param.getValue().actionProperty());

		columnUser.setCellValueFactory(param -> {

			String name;
			if (param.getValue().getUserId() == null)
				name = "deleted";
			else
				name = param.getValue().getUserId().userNameProperty().getName();

			return new SimpleStringProperty(name);
		});

		tableView.getColumns().addAll(columnDate,columnAction,columnUser);

		tableView.setItems(AuditHistoryData.theData);

		setCenter(tableView);

	}



}
