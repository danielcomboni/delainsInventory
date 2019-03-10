package com.delains.ui.suppliers;

import java.math.BigDecimal;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.suppliers.Supplier;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.ButtonRaisedType;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class SupplierFrame extends BorderPane {

	private static TableView < Supplier > tableViewSupplier;
	private TableColumn < Supplier, String > colSupplierName;
	private TableColumn < Supplier, String > colSupplierEmail;
	private TableColumn < Supplier, String > colSupplierPhone;
	private TableColumn < Supplier, String > colSupplierType;

	public static TableView < Supplier > getTableViewSupplier() {
		return tableViewSupplier;
	}

	public static void setTableViewSupplier( TableView < Supplier > tableViewSupplier ) {
		SupplierFrame.tableViewSupplier = tableViewSupplier;
	}

	private Label LabelSupplierEmail;
	private TextField fieldSupplierEmail;

	private Label labelSupplierPhone;
	private TextField fieldSupplierPhone;

	private Label labelSupplierName;
	private TextField fieldSupplierName;

	private Label labelSupplierType;
	private ComboBox < SupplierType > comboBoxSupplierType;

	public ComboBox < SupplierType > getComboBoxSupplierType() {
		return comboBoxSupplierType;
	}

	public void setComboBoxSupplierType( ComboBox < SupplierType > comboBoxSupplierType ) {
		this.comboBoxSupplierType = comboBoxSupplierType;
	}

	private GridPane gridPane;
	private HBox hBox;

	private JFXButton buttonSave;
	private JFXButton buttonCancel;
	private StageForAllPopUps stageForAllPopUps;

	public SupplierFrame() {

		this.buildSupplierFrameComponents();

		clickRow();
		clickRowToDelete();

		Button button = new Button( "export" );
		button.setOnAction( e -> {

			ObservableList < String > attr = FXCollections.observableArrayList();

			attr.add( "getUserName" );
			attr.add( "getUserEmail" );
			attr.add( "getUserPhone" );
			attr.add( "isAdmin" );
			// attr.add( "getItemName" );

			WorkBookUtils < Supplier > wb = new WorkBookUtils <>();
			wb.createNewWorkBook( "user test", tableViewSupplier,
					SupplierHibernation.findAllSuppliersObservableListRefreshed(), attr );

		} );

//		this.setBottom( button );

	}

	private void buildSupplierFrameComponents() {

		this.setId( "main_borderpane" );

		getStylesheets().add( SupplierFrame.class.getResource( "supplier.css" ).toExternalForm() );

		this.hBox = new HBox();
		this.gridPane = new GridPane();
		this.gridPane.setHgap( 10 );
		this.gridPane.setVgap( 10 );
		this.gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelSupplierName = new Label( "supplier name:" );
		this.gridPane.add( this.labelSupplierName, 0, 0 );

		this.fieldSupplierName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldSupplierName, 400 );
		this.gridPane.add( this.fieldSupplierName, 1, 0 );

		this.LabelSupplierEmail = new Label( "supplier email" );
		this.gridPane.add( this.LabelSupplierEmail, 0, 1 );

		this.fieldSupplierEmail = new TextField();
		this.gridPane.add( this.fieldSupplierEmail, 1, 1 );

		this.labelSupplierPhone = new Label( "supplier phone:" );
		this.gridPane.add( this.labelSupplierPhone, 0, 2 );

		this.fieldSupplierPhone = new TextField();
		this.gridPane.add( this.fieldSupplierPhone, 1, 2 );

		this.labelSupplierType = new Label( "supplier type:" );
		this.gridPane.add( this.labelSupplierType, 0, 3 );

		comboBoxSupplierType = new ComboBox <>();
		this.gridPane.add( comboBoxSupplierType, 1, 3 );

		HBox box = new HBox( 10 );
		this.buttonSave = new JFXButton( "save" );
		new ButtonRaisedType( buttonSave );
		box.getChildren().add( buttonSave );

		this.buttonCancel = new JFXButton( "cancel" );
		new ButtonRaisedType( buttonCancel );
		box.getChildren().add( buttonCancel );

		this.gridPane.add( box, 1, 4 );

		this.hBox.getChildren().add( this.gridPane );

		buildTableOfSupplier();
		buildTopOfThisFrame();

		/*
		 * 
		 * listening
		 * 
		 */

		stageForAllPopUps = new StageForAllPopUps( hBox, "add new supplier" );

		this.buttonCancel.setOnAction( e -> {

			stageForAllPopUps.close();

		} );

		this.buttonSave.setOnAction( e -> {

			if ( buttonSave.getText().equals( "save changes" ) ) {
				saveChanges();
			} else {
				addNewSupplier();
			}

		} );

	}

	public void newSupplier() {

		SupplierManipulation.populateComboBoxSupplierType( comboBoxSupplierType );

		// FieldClearance.clearComboBox( comboBoxSupplierType );
		// FieldClearance.clearTextField( fieldSupplierEmail );
		// FieldClearance.clearTextField( fieldSupplierName );
		// FieldClearance.clearTextField( fieldSupplierPhone );

		stageForAllPopUps.setTitle( "add new supplier" );
		buttonSave.setText( "save" );

		stageForAllPopUps.showAndWait();
	}

	private void buildTableOfSupplier() {

		tableViewSupplier = new TableView <>();
		tableViewSupplier.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		colSupplierName = new TableColumn <>( "Name" );
		colSupplierName.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Supplier, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Supplier, String > param ) {
						// TODO Auto-generated method stub
						Supplier s = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( s.getSupplierName() );
						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableViewSupplier.getColumns().add( colSupplierName );

		colSupplierEmail = new TableColumn <>( "Email" );
		colSupplierEmail.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Supplier, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Supplier, String > param ) {
						// TODO Auto-generated method stub
						Supplier s = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( s.getSupplierEmail() );
						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableViewSupplier.getColumns().add( colSupplierEmail );

		colSupplierPhone = new TableColumn <>( "Phone" );
		colSupplierPhone.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Supplier, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Supplier, String > param ) {
						// TODO Auto-generated method stub
						Supplier s = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( s.getSupplierPhone() );
						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableViewSupplier.getColumns().add( colSupplierPhone );

		colSupplierType = new TableColumn <>( "Type" );
		colSupplierType.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < Supplier, String >, ObservableValue < String > >() {

					@Override
					public ObservableValue < String > call( CellDataFeatures < Supplier, String > param ) {
						// TODO Auto-generated method stub
						Supplier s = param.getValue();
						SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
						simpleStringProperty.setValue( s.getType().getType() );
						StringProperty property = simpleStringProperty;

						return property;
					}
				} );
		tableViewSupplier.getColumns().add( colSupplierType );

		/*
		 * 
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 */

		this.setCenter( tableViewSupplier );

		Refresh.setRefreshingDeterminant( 1 );
		SupplierManipulation.populateTable( tableViewSupplier );
		SupplierManipulation.populateComboBoxSupplierType( comboBoxSupplierType );
		Refresh.setRefreshingDeterminant( 0 );

	}

	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing all suppliers and their types" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );

		Button button = new Button();

		// this.setBottom( button );

		button.setOnAction( e -> {

			this.updateSupplier( new Supplier() );

			this.selectSupplierType( new Supplier( BigDecimal.ZERO, "em", "ph", "name", "date",
					new SupplierType( BigDecimal.ZERO, "ttt" ) ) );

		} );

	}

	private void addNewSupplier() {

		String name = null;
		String email = null;
		String phone = null;
		SupplierType type = null;

		new StageForAlerts();

		if ( fieldSupplierName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the supplier name can not be empty!!" );
			return;

		} else {

			name = fieldSupplierName.getText();

		}

		email = fieldSupplierEmail.getText().trim();

		if ( fieldSupplierPhone.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the supplier phone number has to be specified please. It is important!!" );
			return;
		} else {
			phone = fieldSupplierPhone.getText().trim();
		}

		if ( comboBoxSupplierType.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "the type of this supplier has to be specified please!!" );
			return;

		} else {
			// String key =
			// comboBoxSupplierType.getSelectionModel().getSelectedItem().getId().toString();

			type = comboBoxSupplierType.getSelectionModel().getSelectedItem();

		}

		Supplier supplier = new Supplier();

		supplier.setSupplierEmail( email );
		supplier.setSupplierName( name );
		supplier.setSupplierPhone( phone );
		supplier.setType( type );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this supplier details?" );

		if ( StageForAlerts.isDecide() == true ) {
			SupplierHibernation.newSupplier( supplier );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierManipulation.populateTable( tableViewSupplier );
			Refresh.setRefreshingDeterminant( 0 );

			AuditHistoryHibernation.auditValues( "created new supplier: ".concat( supplier.getSupplierName() ),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			FieldClearance.clearTextField( fieldSupplierName );
			FieldClearance.clearTextField( fieldSupplierEmail );
			FieldClearance.clearTextField( fieldSupplierPhone );
			FieldClearance.clearComboBox( comboBoxSupplierType );

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseManipulation.populateComboBoxItems( comboBoxItems );
			// PurchaseFrame.populateComboBoxSupplier();

		}

	}

	private Supplier supplierPrevious;

	public void updateSupplier( Supplier supplier ) {

		SupplierManipulation.populateComboBoxSupplierType( comboBoxSupplierType );

		supplierPrevious = supplier;

		fieldSupplierEmail.setText( supplier.getSupplierEmail() );
		fieldSupplierName.setText( supplier.getSupplierName() );
		fieldSupplierPhone.setText( supplier.getSupplierPhone() );

		selectSupplierType( supplier );

		stageForAllPopUps.setTitle( "edit supplier info" );
		buttonSave.setText( "save changes" );

		stageForAllPopUps.showAndWait();

	}

	public void selectSupplierType( Supplier supplier ) {

		comboBoxSupplierType.getSelectionModel().select( supplier.getType() );

	}

	public void saveChanges() {

		String name = null;
		String email = null;
		String phone = null;

		SupplierType type = null;

		new StageForAlerts();

		if ( fieldSupplierName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the supplier name can not be empty!!" );
			return;

		} else {

			name = fieldSupplierName.getText();

		}

		email = fieldSupplierEmail.getText().trim();

		if ( fieldSupplierPhone.getText().trim().isEmpty() ) {
			StageForAlerts.inform( "alert", "the supplier phone number has to be specified please. It is important!!" );
			return;
		} else {
			phone = fieldSupplierPhone.getText().trim();
		}

		if ( comboBoxSupplierType.getSelectionModel().getSelectedItem() == null ) {

			StageForAlerts.inform( "alert", "the type of this supplier has to be specified please!!" );
			return;

		} else {
			// String key =
			// comboBoxSupplierType.getSelectionModel().getSelectedItem().getId().toString();

			type = comboBoxSupplierType.getSelectionModel().getSelectedItem();
			System.out.println( "type: " + type );
		}

		Supplier supplier = supplierPrevious;

		supplier.setSupplierEmail( email );
		supplier.setSupplierName( name );
		supplier.setSupplierPhone( phone );
		supplier.setType( type );

		new StageForAlerts();

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if ( StageForAlerts.isDecide() == true ) {

			SupplierHibernation.updateSupplier( supplier, supplier.getId() );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierManipulation.populateTable( tableViewSupplier );
			Refresh.setRefreshingDeterminant( 0 );

			AuditHistoryHibernation.auditValues( "edited supplier: ".concat( supplier.getSupplierName() ),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			comboBoxSupplierType.getSelectionModel().clearSelection();
			FieldClearance.clearTextField( fieldSupplierEmail );
			FieldClearance.clearTextField( fieldSupplierName );
			FieldClearance.clearTextField( fieldSupplierPhone );
			stageForAllPopUps.close();
			buttonSave.setText( "save" );
			stageForAllPopUps.setTitle( "add new supplier" );

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseFrame.populateComboBoxSupplier();

		}

	}

	public void clickRow() {
		tableViewSupplier.setRowFactory( tr -> {
			TableRow < Supplier > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {
					Supplier s = row.getItem();
					// System.out.println("from row clicked: " + s);
					this.selectSupplierType( s );
					idOfSupplier = s.getId();
				}
			} );

			return row;
		} );

	}

	private void clickRowToDelete() {
		tableViewSupplier.setRowFactory( tr -> {
			TableRow < Supplier > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {

					Supplier s = row.getItem();

					System.out.println( "from row clicked: " + s );

					// this.selectSupplierType(s);

					System.out.println( "id test: " + s.getId() );

					this.setIdOfSupplier( s.getId() );

					System.out.println( "tttt; " + this.getIdOfSupplier() );

				}
			} );

			return row;
		} );

	}

	private BigDecimal idOfSupplier;

	public BigDecimal getIdOfSupplier() {
		return idOfSupplier;
	}

	public void setIdOfSupplier( BigDecimal idOfSupplier ) {
		this.idOfSupplier = idOfSupplier;
	}

	public void deleteSupplier( BigDecimal id ) {

		new StageForAlerts();

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this supplier permanently?" );

		if ( StageForAlerts.isDecide() == true ) {

			// System.out.println("id: " + this.getIdOfSupplier());

			SupplierHibernation.deleteSupplier( id );

			Refresh.setRefreshingDeterminant( 1 );
			SupplierManipulation.populateTable( tableViewSupplier );
			Refresh.setRefreshingDeterminant( 0 );

			AuditHistoryHibernation.auditValues( "deleted a supplier ", UserLoggedIn.getUserLoggedIn() );
			AuditHistoryManipulation.populateTableWithRefreshing();

			Refresh.setRefreshingDeterminant( 1 );
			// PurchaseFrame.populateComboBoxSupplier();

		}

	}

	// public static ComboBox<SupplierType> getTheComboBoxSupplierType() {
	// SupplierFrame frame = new SupplierFrame();
	// return frame.getComboBoxSupplierType();
	// }

}
