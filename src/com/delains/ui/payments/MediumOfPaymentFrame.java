package com.delains.ui.payments;

import com.delains.model.payments.MediumOfPayment;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

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

		tableView = new TableView<>();
		tableView.setTableMenuButtonVisible( true );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		TableColumn < MediumOfPayment, String > colName = new TableColumn <>( "payment medium" );
		colName.setCellValueFactory(param -> param.getValue().nameOfMediumOfPaymentProperty());
		tableView.getColumns().add( colName );

		TableColumn < MediumOfPayment, String > colSpecification = new TableColumn <>( "specification" );
		colSpecification.setCellValueFactory(param -> param.getValue().specificationOrDescriptionProperty());
		tableView.getColumns().add( colSpecification );

		this.setCenter( tableView );

		tableView.setItems(MediumOfPaymentData.data);

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

//		MediumOfPayment it = tableView.selectionModelProperty().getValue().getSelectedItem();
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
