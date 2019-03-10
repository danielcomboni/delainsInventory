package com.delains.ui.suppliers;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.suppliers.SupplierTypeHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class SupplierTypeFrame extends BorderPane {

	private HBox hBox;
	private GridPane gridPane;

	private Label labelSupplierType;
	private TextField fieldSupplierType;
	private StageForAllPopUps stageForAllPopUps;

	private HBox hBoxButtons;
	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	public SupplierTypeFrame() {
		buildSupplierTypeFrame();
		buildTable();

		Refresh.setRefreshingDeterminant( 1 );
		SupplierTypeManipulation.populateUserTable( tableView );

		buttonExporttest = new JFXButton( "export" );
//		this.setBottom( buttonExporttest );

		buttonExporttest.setOnAction( e -> {

			ObservableList < String > attr = FXCollections.observableArrayList();
			attr.add( "getType" );

			WorkBookUtils < SupplierType > wb = new WorkBookUtils <>();
			wb.createNewWorkBook( "test name", tableView, SupplierTypeHibernation.findAllSupplierTypesObservableList(),
					attr );

		} );

	}

	private JFXButton buttonExporttest;

	private void buildSupplierTypeFrame() {

		this.setId( "main_borderpane" );

		getStylesheets().add( SupplierTypeFrame.class.getResource( "supplierType.css" ).toExternalForm() );

		hBox = new HBox();
		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		labelSupplierType = new Label( "Supplier type:" );
		this.gridPane.add( this.labelSupplierType, 0, 0 );

		fieldSupplierType = new TextField();
		ComponentWidth.setWidthOfTextField( fieldSupplierType, 400 );
		this.gridPane.add( fieldSupplierType, 1, 0 );

		hBoxButtons = new HBox();
		hBoxButtons.setSpacing( 10 );
		this.buttonSave = new JFXButton( "save" );
		this.hBoxButtons.getChildren().add( buttonSave );

		buttonCancel = new JFXButton( "cancel" );
		this.hBoxButtons.getChildren().add( buttonCancel );

		this.gridPane.add( this.hBoxButtons, 1, 1 );

		this.hBox.getChildren().add( this.gridPane );

		// this.setCenter(hBox);

		stageForAllPopUps = new StageForAllPopUps( hBox, "add new supplier type" );

		buttonCancel.setOnAction( e -> {
			stageForAllPopUps.close();
		} );

		buttonSave.setOnAction( e -> {

			if ( stageForAllPopUps.getTitle().equals( "add new supplier type" ) ) {
				addNewType();
			} else {
				saveChanges();
			}

		} );

	}

	public void newSupplierType() {
		stageForAllPopUps.showAndWait();
	}

	private static TableView < SupplierType > tableView;
	private TableColumn < SupplierType, String > colType;

	public static TableView < SupplierType > getTableView() {
		return tableView;
	}

	public static void setTableView( TableView < SupplierType > tableView ) {
		SupplierTypeFrame.tableView = tableView;
	}

	private void buildTable() {

		tableView = new TableView <>();
		tableView.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		colType = new TableColumn <>( "Type of supplier" );
		colType.setCellValueFactory( new PropertyValueFactory <>( "type" ) );
		tableView.getColumns().add( colType );

		this.setCenter( tableView );

	}

	private void addNewType() {

		String type = null;

		new StageForAlerts();

		if ( fieldSupplierType.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the type of the supplier can not be empty" );
			return;
		} else {
			type = fieldSupplierType.getText();
		}

		SupplierType supplierType = new SupplierType();
		supplierType.setType( type );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this supplier type?" );

		if ( StageForAlerts.isDecide() == true ) {

			SupplierTypeHibernation.newSupplierType( supplierType );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierTypeManipulation.populateUserTable( tableView );
			// SupplierManipulation.populateComboBoxSupplierType(SupplierFrame.getTheComboBoxSupplierType());
			Refresh.setRefreshingDeterminant( 0 );
			AuditHistoryHibernation.auditValues( "new supplier type created: " + supplierType.getType(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			clearFields();

		}

	}

	private SupplierType typePrevious;

	public void chooseTypeForEditing( SupplierType type ) {

		typePrevious = type;

		fieldSupplierType.setText( type.getType() );
		buttonSave.setText( "save changes" );
		stageForAllPopUps.setTitle( "edit supplier type" );
		stageForAllPopUps.showAndWait();

	}

	private void saveChanges() {
		SupplierType type = typePrevious;

		String newType = null;

		if ( fieldSupplierType.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the type of the supplier can not be empty" );
			return;
		} else {
			newType = fieldSupplierType.getText();
		}

		SupplierType typeNew = type;
		typeNew.setType( newType );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if ( StageForAlerts.isDecide() == true ) {

			SupplierTypeHibernation.updateSupplier( typeNew, typeNew.getId() );

			stageForAllPopUps.setTitle( "add new supplier type" );

			stageForAllPopUps.close();
			fieldSupplierType.clear();
			buttonSave.setText( "save" );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierTypeManipulation.populateUserTable( tableView );

			Refresh.setRefreshingDeterminant( 0 );

			AuditHistoryHibernation.auditValues( "supplier type edited: " + typeNew.getType(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

		}
		clearFields();
	}

	private void clearFields() {
		FieldClearance.clearTextField( fieldSupplierType );
	}

}
