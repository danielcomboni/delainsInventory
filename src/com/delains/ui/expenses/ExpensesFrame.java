package com.delains.ui.expenses;

import com.delains.dao.expenses.ExpensesHibernation;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.expenses.Expenses;
import com.delains.ui.invoker.StageForAlerts;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ExpensesFrame extends BorderPane {

	private static TableView < Expenses > tableView;

	public static TableView < Expenses > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < Expenses > tableView ) {
		ExpensesFrame.tableView = tableView;
	}

	public ExpensesFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( ExpensesFrame.class.getResource( "expenses.css" ).toExternalForm() );

		tableView = new TableView <>();
		tableView.setTableMenuButtonVisible( true );
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		TableColumn < Expenses, String > colDate = new TableColumn <>( "date" );
		colDate.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Expenses, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Expenses, String > param ) {
						return new SimpleStringProperty( param.getValue().getDate() );
					}
				} );
		colDate.setCellFactory( tc -> {
			TableCell < Expenses, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colDate.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );
			return cell;
		} );
		tableView.getColumns().add( colDate );

		TableColumn < Expenses, String > colAmountSpent = new TableColumn <>( "amount spent" );
		colAmountSpent.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Expenses, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Expenses, String > param ) {
						return new SimpleStringProperty(
								NumberFormatting.formatToEnglish( param.getValue().getAmountSpent().toString() ) );
					}
				} );
		colAmountSpent.setCellFactory( tc -> {
			TableCell < Expenses, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colDate.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );
			return cell;
		} );
		tableView.getColumns().add( colAmountSpent );

		TableColumn < Expenses, String > colMediumOfPayment = new TableColumn <>( "payment medium" );
		colMediumOfPayment.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Expenses, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Expenses, String > param ) {
						if ( param.getValue().getMediumOfPaymentId() == null ) {
							return null;
						} else {
							return new SimpleStringProperty( param.getValue().getMediumOfPaymentId()
									.getNameOfMediumOfPayment().concat( "-" ).concat(
											param.getValue().getMediumOfPaymentId().getSpecificationOrDescription() ) );
						}
					}
				} );
		colMediumOfPayment.setCellFactory( tc -> {
			TableCell < Expenses, String > cell = new TableCell <>();
			Text text = new Text();
			text.getStyleClass().add( "text-id" );
			cell.setPrefHeight( Control.USE_COMPUTED_SIZE );
			text.wrappingWidthProperty().bind( colDate.widthProperty() );
			text.textProperty().bind( cell.itemProperty() );
			cell.setGraphic( text );
			return cell;
		} );
		tableView.getColumns().add( colMediumOfPayment );

		addButtonToTable( tableView );

		this.setCenter( tableView );

		ExpensesFrame.getTableView().getItems().clear();
		ExpensesFrame.getTableView().getItems().addAll( ExpensesHibernation.findAllExpensessObservableListRefreshed() );

		itemObtainedByClickingTable();

	}

	private static void addButtonToTable( TableView < Expenses > tableView ) {
		TableColumn < Expenses, Void > colBtn = new TableColumn <>( "Description" );

		Callback < TableColumn < Expenses, Void >, TableCell < Expenses, Void > > cellFactory = new Callback < TableColumn < Expenses, Void >, TableCell < Expenses, Void > >() {
			@Override
			public TableCell < Expenses, Void > call( final TableColumn < Expenses, Void > param ) {
				final TableCell < Expenses, Void > cell = new TableCell < Expenses, Void >() {

					private final Button btn = new Button( "desc" );

					{
						btn.setOnAction( ( ActionEvent event ) -> {
							Expenses data = getTableView().getItems().get( getIndex() );

							new StageForAlerts();
							StageForAlerts.inform( "expense reason", data.getReason() );

						} );
					}

					@Override
					public void updateItem( Void item, boolean empty ) {
						super.updateItem( item, empty );
						if ( empty ) {
							setGraphic( null );
						} else {
							setGraphic( btn );
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory( cellFactory );

		tableView.getColumns().add( colBtn );

	}

	private Expenses expenses;

	public Expenses getExpenses() {
		return expenses;
	}

	public void setExpenses( Expenses expenses ) {
		this.expenses = expenses;
	}

	public Expenses itemObtainedByClickingTable() {
		tableView.getSelectionModel().selectedItemProperty().addListener( ( obs, oldVal, newVal ) -> {
			setExpenses( newVal );
			if ( newVal != null ) {
				setExpenses( newVal );
			} else {
				setExpenses( oldVal );
			}
		} );
		return getExpenses();
	}

}
