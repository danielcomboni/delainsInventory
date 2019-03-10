package com.delains.ui.payments;

import com.delains.dao.payments.MediumOfPaymentHibernation;
import com.delains.model.payments.MediumOfPayment;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class MediumOfPaymentFrame extends BorderPane {

	private static TableView < MediumOfPayment > tableView;

	public static TableView < MediumOfPayment > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < MediumOfPayment > tableView ) {
		MediumOfPaymentFrame.tableView = tableView;
	}

	public MediumOfPaymentFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( MediumOfPaymentFrame.class.getResource( "mediumOfPayment.css" ).toExternalForm() );

		tableView = new TableView <>();
		tableView.setTableMenuButtonVisible( true );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		TableColumn < MediumOfPayment, String > colName = new TableColumn <>( "payment medium" );
		colName.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < MediumOfPayment, String >, ObservableValue < String > >() {
					@Override
					public ObservableValue < String > call( CellDataFeatures < MediumOfPayment, String > param ) {
						return new SimpleStringProperty( param.getValue().getNameOfMediumOfPayment() );
					}
				} );
		tableView.getColumns().add( colName );

		TableColumn < MediumOfPayment, String > colSpecification = new TableColumn <>( "specification" );
		colSpecification.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < MediumOfPayment, String >, ObservableValue < String > >() {
					@Override
					public ObservableValue < String > call( CellDataFeatures < MediumOfPayment, String > param ) {
						return new SimpleStringProperty( param.getValue().getSpecificationOrDescription() );
					}
				} );
		tableView.getColumns().add( colSpecification );

		this.setCenter( tableView );

		MediumOfPaymentFrame.getTableView().getItems().clear();
		MediumOfPaymentFrame.getTableView().getItems()
				.addAll( MediumOfPaymentHibernation.findAllMediumOfPaymentsObservableListRefreshed() );

		itemObtainedByClickingTable();

	}

	private MediumOfPayment mediumOfPayment;

	public MediumOfPayment getMediumOfPayment() {
		return mediumOfPayment;
	}

	public void setMediumOfPayment( MediumOfPayment mediumOfPayment ) {
		this.mediumOfPayment = mediumOfPayment;
	}

	public MediumOfPayment itemObtainedByClickingTable() {

		MediumOfPayment it = tableView.selectionModelProperty().getValue().getSelectedItem();
		System.out.println( "getValue: " + it );

		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setMediumOfPayment( newVal );

			if ( newVal != null ) {
				setMediumOfPayment( newVal );
			} else {
				setMediumOfPayment( oldVal );
			}

		} );

		return getMediumOfPayment();
	}

}
