package com.delains.ui.history;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.model.history.AuditHistory;
import com.delains.ui.table.AddColumnsToTable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class AuditHistoryManipulation {

	private static AddColumnsToTable < AuditHistory > addColumnsToTable;

	public static TableView < AuditHistory > getTableView() {

		AuditHistory auditHistory = new AuditHistory();

		addColumnsToTable = new AddColumnsToTable <>( auditHistory );

		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "date" ), "date" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "action" ), "action" );
		setColumnValueFactory( addColumnsToTable.addStringColumnToTable( "user" ), "user" );

		return addColumnsToTable.getTableViewGeneric().getTableView();

	}

	public static void populateTableWithRefreshing() {

		addColumnsToTable.populateTableRefresh( AuditHistoryHibernation.findAllAuditHistorysWithRefreshing() );

	}

	public static void populatetableWithoutRefreshing() {
		addColumnsToTable
				.populateTableWithoutRefreshing( AuditHistoryHibernation.findAllAuditHistorysWithoutRefreshing() );
	}

	private static TableColumn < AuditHistory, String > setColumnValueFactory(
			TableColumn < AuditHistory, String > column, String decideValue ) {

		column.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < AuditHistory, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < AuditHistory, String > param ) {

						AuditHistory pc = param.getValue();

						String val = null;

						if ( decideValue.equalsIgnoreCase( "date" ) ) {
							val = pc.getDate();
						}

						if ( decideValue.equalsIgnoreCase( "action" ) ) {

							val = pc.getAction();

						}

						if ( decideValue.equalsIgnoreCase( "user" ) ) {
							if ( pc.getUserid() != null ) {
								val = pc.getUserid().getUserName();
							} else {
								val = null;
							}

						}

						SimpleStringProperty simpleStringProperty = new SimpleStringProperty( val );

						StringProperty property = simpleStringProperty;

						return property;
					}
				} );

		return column;

	}

}
