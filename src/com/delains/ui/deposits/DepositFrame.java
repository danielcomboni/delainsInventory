package com.delains.ui.deposits;

import com.delains.dao.deposits.DepositHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.deposits.Deposit;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class DepositFrame extends BorderPane {

	private static TableView < Deposit > tableView;

	public static TableView < Deposit > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Deposit > tableView ) {
		DepositFrame.tableView = tableView;
	}

	public DepositFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( DepositFrame.class.getResource( "deposit.css" ).toExternalForm() );

		tableView = new TableView <>();
		tableView.setTableMenuButtonVisible( true );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		TableColumn < Deposit, String > colDate = new TableColumn <>( "date" );
		this.setCellFactoryTextWrapper( colDate );
		colDate.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Deposit, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Deposit, String > param ) {

						return new SimpleStringProperty( param.getValue().getDate() );
					}
				} );
		tableView.getColumns().add( colDate );

		TableColumn < Deposit, String > colAmountDeposited = new TableColumn <>( "amount deposited" );
		colAmountDeposited.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Deposit, String >, ObservableValue < String > >() {
					@Override
					public ObservableValue < String > call( CellDataFeatures < Deposit, String > param ) {
						return new SimpleStringProperty(
								NumberFormatting.formatToEnglish( param.getValue().getAmountDeposited().toString() ) );
					}
				} );
		tableView.getColumns().add( colAmountDeposited );

		TableColumn < Deposit, String > colMediumOfPaymentFrom = new TableColumn <>( "from (medium)" );

		colMediumOfPaymentFrom.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Deposit, String >, ObservableValue < String > >() {
					@Override
					public ObservableValue < String > call( CellDataFeatures < Deposit, String > param ) {

						if ( param.getValue().getMediumOfPaymentFromId() != null ) {

							return new SimpleStringProperty( param.getValue().getMediumOfPaymentFromId()
									.getNameOfMediumOfPayment().concat( "-" ).concat( param.getValue()
											.getMediumOfPaymentFromId().getSpecificationOrDescription() ) );
						} else {
							return null;
						}
					}
				} );
		tableView.getColumns().add( colMediumOfPaymentFrom );

		TableColumn < Deposit, String > colMediumOfPaymentTo = new TableColumn <>( "to (medium)" );

		colMediumOfPaymentTo.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Deposit, String >, ObservableValue < String > >() {
					@Override
					public ObservableValue < String > call( CellDataFeatures < Deposit, String > param ) {
						if ( param.getValue().getMediumOfPaymentToId() != null ) {
							return new SimpleStringProperty( param.getValue().getMediumOfPaymentToId()
									.getNameOfMediumOfPayment().concat( "-" ).concat( param.getValue()
											.getMediumOfPaymentToId().getSpecificationOrDescription() ) );
						}
						return null;
					}
				} );
		tableView.getColumns().add( colMediumOfPaymentTo );

		TableColumn < Deposit, String > colSupplierCleared = new TableColumn <>( "customer" );
		setCellFactoryTextWrapper( colSupplierCleared );
		colSupplierCleared.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Deposit, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Deposit, String > param ) {
						if ( param.getValue().getCustomerId() != null ) {
							return new SimpleStringProperty( param.getValue().getCustomerId().getCustomerName() );
						} else {
							return null;
						}
					}
				} );
		tableView.getColumns().add( colSupplierCleared );

		this.setCenter( tableView );

		DepositFrame.getTableView().getItems().clear();
		DepositFrame.getTableView().getItems().addAll( DepositHibernation.findAllDepositsObservableListRefreshed() );

		itemObtainedByClickingTable();

		populateTable();

	}

	private Deposit mediumOfPayment;

	public Deposit getDeposit() {
		return mediumOfPayment;
	}

	public void setDeposit( Deposit mediumOfPayment ) {
		this.mediumOfPayment = mediumOfPayment;
	}

	public Deposit itemObtainedByClickingTable() {
		Deposit it = tableView.selectionModelProperty().getValue().getSelectedItem();
		System.out.println( "getValue: " + it );
		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setDeposit( newVal );
			if ( newVal != null ) {
				setDeposit( newVal );
			} else {
				setDeposit( oldVal );
			}
		} );
		return getDeposit();
	}

	private void setCellFactoryTextWrapper( TableColumn < Deposit, String > col ) {
		col.setCellFactory( tc -> {
			TableCell < Deposit, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( col.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );
			return cell;
		} );
	}

	public static void populateTable() {
		tableView.getItems().clear();
		tableView.getItems().addAll( DepositHibernation.findAllDepositsObservableListRefreshed() );
	}

}
