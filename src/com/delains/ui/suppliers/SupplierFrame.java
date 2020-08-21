package com.delains.ui.suppliers;

import java.math.BigDecimal;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.history.AuditHistory;
import com.delains.model.suppliers.Supplier;
import com.delains.model.suppliers.SupplierType;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.invoker.ButtonRaisedType;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class SupplierFrame extends BorderPane {

	private static TableView < Supplier > tableViewSupplier = new TableView<>();

	static TableView < Supplier > getTableViewSupplier() {
		return tableViewSupplier;
	}

//	public static void setTableViewSupplier( TableView < Supplier > tableViewSupplier ) {
//		SupplierFrame.tableViewSupplier = tableViewSupplier;
//	}

	private TextField fieldSupplierEmail;

	private TextField fieldSupplierPhone;

	private TextField fieldSupplierName;

	private ComboBox < SupplierType > comboBoxSupplierType;

//	public ComboBox < SupplierType > getComboBoxSupplierType() {
//		return comboBoxSupplierType;
//	}

//	public void setComboBoxSupplierType( ComboBox < SupplierType > comboBoxSupplierType ) {
//		this.comboBoxSupplierType = comboBoxSupplierType;
//	}

	private JFXButton buttonSave;
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

		HBox hBox = new HBox();
		GridPane gridPane = new GridPane();
		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 5, 5, 5, 5 ) );

		Label labelSupplierName = new Label("supplier name:");
		gridPane.add(labelSupplierName, 0, 0 );

		this.fieldSupplierName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldSupplierName, 400 );
		gridPane.add( this.fieldSupplierName, 1, 0 );

		Label labelSupplierEmail = new Label("supplier email");
		gridPane.add(labelSupplierEmail, 0, 1 );

		this.fieldSupplierEmail = new TextField();
		gridPane.add( this.fieldSupplierEmail, 1, 1 );

		Label labelSupplierPhone = new Label("supplier phone:");
		gridPane.add(labelSupplierPhone, 0, 2 );

		this.fieldSupplierPhone = new TextField();
		gridPane.add( this.fieldSupplierPhone, 1, 2 );

		Label labelSupplierType = new Label("supplier type:");
		gridPane.add(labelSupplierType, 0, 3 );

		comboBoxSupplierType = new ComboBox <>();
		comboBoxSupplierType.setItems(SupplierTypeData.data);
		setConverterComboboxSupplierType();
		gridPane.add( comboBoxSupplierType, 1, 3 );

		HBox box = new HBox( 10 );
		this.buttonSave = new JFXButton( "save" );
		new ButtonRaisedType( buttonSave );
		box.getChildren().add( buttonSave );

		JFXButton buttonCancel = new JFXButton("cancel");
		new ButtonRaisedType(buttonCancel);
		box.getChildren().add(buttonCancel);

		gridPane.add( box, 1, 4 );

		hBox.getChildren().add(gridPane);

		buildTableOfSupplier();
		buildTopOfThisFrame();

		/*
		 *
		 * listening
		 *
		 */

		stageForAllPopUps = new StageForAllPopUps(hBox, "add new supplier" );

		buttonCancel.setOnAction(e -> stageForAllPopUps.close());

		this.buttonSave.setOnAction( e -> {

			if ( buttonSave.getText().equals( "save changes" ) ) {
				saveChanges();
			} else {
				addNewSupplier();
			}

		} );

	}

	void newSupplier() {

		stageForAllPopUps.setTitle( "add new supplier" );
		buttonSave.setText( "save" );
		stageForAllPopUps.showAndWait();

	}

	private void buildTableOfSupplier() {

		tableViewSupplier = new TableView <>();
		tableViewSupplier.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		TableColumn<Supplier, String> colSupplierName = new TableColumn<>("Name");
		colSupplierName.setCellValueFactory(
				param -> {

					Supplier s = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( s.getSupplierName() );

					return simpleStringProperty;
				});
		tableViewSupplier.getColumns().add(colSupplierName);

		TableColumn<Supplier, String> colSupplierEmail = new TableColumn<>("Email");
		colSupplierEmail.setCellValueFactory(
				param -> {

					Supplier s = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( s.getSupplierEmail() );

					return simpleStringProperty;
				});
		tableViewSupplier.getColumns().add(colSupplierEmail);

		TableColumn<Supplier, String> colSupplierPhone = new TableColumn<>("Phone");
		colSupplierPhone.setCellValueFactory(
				param -> {

					Supplier s = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( s.getSupplierPhone() );

					return simpleStringProperty;
				});
		tableViewSupplier.getColumns().add(colSupplierPhone);

		TableColumn<Supplier, String> colSupplierType = new TableColumn<>("Type");
		colSupplierType.setCellValueFactory(
				param -> {

					Supplier s = param.getValue();
					SimpleStringProperty simpleStringProperty = new SimpleStringProperty();
					simpleStringProperty.setValue( s.getType().getType() );

					return simpleStringProperty;
				});
		tableViewSupplier.getColumns().add(colSupplierType);

		/*
		 *
		 * the use of a vbox makes it half way filled and using table view direct into
		 * the centre of the border pane fills the whole screen up
		 */

		tableViewSupplier.setItems(SupplierData.data);

		this.setCenter( tableViewSupplier );

//		Refresh.setRefreshingDeterminant( 1 );
//		SupplierManipulation.populateTable( tableViewSupplier );
//		SupplierManipulation.populateComboBoxSupplierType( comboBoxSupplierType );
//		Refresh.setRefreshingDeterminant( 0 );

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

		String name;
		String email;
		String phone;
		SupplierType type;

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

			type = comboBoxSupplierType.getSelectionModel().getSelectedItem();

		}

		Supplier supplier = new Supplier();

		supplier.setSupplierEmail( email );
		supplier.setSupplierName( name );
		supplier.setSupplierPhone( phone );
		supplier.setType( type );

		StageForAlerts.discontinue( "confirm", "are you sure you want to save this supplier details?" );

		if (StageForAlerts.isDecide()) {

			Service service = new Service() {
				@Override
				protected Task createTask() {
					return new Task() {
						@Override
						protected Object call() {

							FieldClearance.clearTextField( fieldSupplierName );
							FieldClearance.clearTextField( fieldSupplierEmail );
							FieldClearance.clearTextField( fieldSupplierPhone );
							FieldClearance.clearComboBox( comboBoxSupplierType );

							Supplier supplier1 = SupplierHibernation.newSupplier( supplier );

							SupplierData.data.add(supplier1);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "created new supplier: ".concat( supplier.getSupplierName() ),
									UserLoggedIn.getUserLoggedIn() );
							AuditHistoryData.theData.add(auditHistory);

							return null;
						}
					};
				}
			};
			service.start();
		}
	}

	private Supplier supplierPrevious;

	private void setConverterComboboxSupplierType(){

		comboBoxSupplierType.setConverter(new StringConverter<SupplierType>() {

			@Override
			public String toString(SupplierType object) {

				return object.getType();
			}

			@Override
			public SupplierType fromString(String string) {

				return comboBoxSupplierType.getItems().stream().filter(e -> e.getType().equals(string)).findFirst()
						.orElse(null);
			}
		});
	}

	void updateSupplier(Supplier supplier) {

		supplierPrevious = supplier;
		fieldSupplierEmail.setText( supplier.getSupplierEmail() );
		fieldSupplierName.setText( supplier.getSupplierName() );
		fieldSupplierPhone.setText( supplier.getSupplierPhone() );

		selectSupplierType( supplier );

		stageForAllPopUps.setTitle( "edit supplier info" );
		buttonSave.setText( "save changes" );

		stageForAllPopUps.showAndWait();

	}

	private void selectSupplierType(Supplier supplier) {

		comboBoxSupplierType.getSelectionModel().select( supplier.getType() );

	}

	private void saveChanges() {

		String name;
		String email;
		String phone;

		SupplierType type;

		new StageForAlerts();

		if ( fieldSupplierName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the supplier name can not be empty!!" );
			return;

		} else
			name = fieldSupplierName.getText();

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

		Supplier supplier = supplierPrevious;

		supplier.setSupplierEmail( email );
		supplier.setSupplierName( name );
		supplier.setSupplierPhone( phone );
		supplier.setType( type );

		new StageForAlerts();

		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if (StageForAlerts.isDecide()) {

			Service service = new Service() {
				@Override
				protected Task createTask() {
					return new Task() {
						@Override
						protected Object call() {

							Supplier supplier1 = SupplierHibernation.updateSupplier( supplier, supplier.getId() );

							SupplierData.data.add(supplier1);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "edited supplier: ".concat( supplier.getSupplierName() ),
									UserLoggedIn.getUserLoggedIn() );
							AuditHistoryData.theData.add(auditHistory);

							comboBoxSupplierType.getSelectionModel().clearSelection();
							FieldClearance.clearTextField( fieldSupplierEmail );
							FieldClearance.clearTextField( fieldSupplierName );
							FieldClearance.clearTextField( fieldSupplierPhone );
							stageForAllPopUps.close();
							buttonSave.setText( "save" );
							stageForAllPopUps.setTitle( "add new supplier" );


							return null;

						}
					};
				}
			};
			service.start();

		}

	}

	private void clickRow() {
		tableViewSupplier.setRowFactory( tr -> {
			TableRow < Supplier > row = new TableRow <>();

			row.setOnMouseClicked( e -> {
				if ( !row.isEmpty() && e.getButton() == MouseButton.PRIMARY && e.getClickCount() == 1 ) {
					Supplier s = row.getItem();
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


					this.setIdOfSupplier( s.getId() );

				}
			} );

			return row;
		} );

	}

	private BigDecimal idOfSupplier;

//	public BigDecimal getIdOfSupplier() {
//		return idOfSupplier;
//	}

	private void setIdOfSupplier(BigDecimal idOfSupplier) {
		this.idOfSupplier = idOfSupplier;
	}

	void deleteSupplier(BigDecimal id, Supplier supplier) {

		new StageForAlerts();

		StageForAlerts.discontinue( "confirm", "are you sure you want to delete this supplier permanently?" );

		if (StageForAlerts.isDecide()) {


			Service service = new Service() {
				@Override
				protected Task createTask() {
					return new Task() {
						@Override
						protected Object call() {

							SupplierHibernation.deleteSupplier( id );

							SupplierData.data.remove(supplier);

							AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "deleted a supplier ", UserLoggedIn.getUserLoggedIn() );
							AuditHistoryData.theData.add(auditHistory);

							return null;
						}
					};
				}
			};
			service.start();

		}

	}


}
