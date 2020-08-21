package com.delains.ui.users;

import java.math.BigDecimal;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.users.UserHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.dao.workbook.WorkBookUtils;
import com.delains.model.history.AuditHistory;
import com.delains.model.users.User;
import com.delains.ui.history.AuditHistoryData;
import com.delains.ui.history.AuditHistoryFrame;
import com.delains.ui.invoker.AccordionTabs;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.FieldClearance;
import com.delains.ui.invoker.PrivilegeAtBeginningDisableOtherAccordions;
import com.delains.ui.invoker.Refresh;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class UserFrame extends BorderPane {

	private HBox hBoxUserFields;

	private GridPane gridPaneUserFields;

	private Label labelUserName;
	private TextField fieldUserName;

	private Label labelUserEmail;
	private TextField fieldUserEmail;

	private Label labelUserPhone;
	private TextField fieldUserPhone;

	private Label labelUserPassword;
	private PasswordField fieldUserPassword;

	private JFXCheckBox checkBoxUserIsAdmin;

	private static TableView < User > tableViewUsers;
	private TableColumn < User, String > colUserName;
	private TableColumn < User, String > colUserEmail;
	private TableColumn < User, String > colUserPhone;
	private TableColumn < User, Boolean > colUserAdmin;

	private HBox hBoxButtons;
	private JFXButton buttonSave;
	private JFXButton buttonCancel;

	private StageForAllPopUps stageForAllPopUps;

	// constructor
	public UserFrame() {

		buildUserFrameComponents();

		// populateUsersTableWithoutRefreshing();

		Refresh.setRefreshingDeterminant( 1 );
		//UserManipulation.populateUserTable( tableViewUsers );

		tableViewUsers.setItems(UserManipulation.data);

		listening();

		Button button = new Button( "export" );
		button.setOnAction( e -> {

			ObservableList < String > attr = FXCollections.observableArrayList();

			attr.add( "getUserName" );
			attr.add( "getUserEmail" );
			attr.add( "getUserPhone" );
			attr.add( "isAdmin" );

			WorkBookUtils < User > wb = new WorkBookUtils <>();
			wb.createNewWorkBook( "user test", tableViewUsers, UserHibernation.findAllUsersRefreshed(), attr );

		} );

	}

	private void buildUserFrameComponents() {

		this.setId( "main_borderpane" );

		getStylesheets().add( UserFrame.class.getResource( "userFrame.css" ).toExternalForm() );

		this.hBoxUserFields = new HBox();

		this.gridPaneUserFields = new GridPane();
		this.gridPaneUserFields.setHgap( 10 );
		this.gridPaneUserFields.setVgap( 10 );
		this.gridPaneUserFields.setPadding( new Insets( 5, 5, 5, 5 ) );

		this.labelUserName = new Label( "User name:" );
		this.gridPaneUserFields.add( this.labelUserName, 0, 0 );

		this.fieldUserName = new TextField();
		ComponentWidth.setWidthOfTextField( fieldUserName, 400 );
		this.gridPaneUserFields.add( fieldUserName, 1, 0 );

		this.labelUserEmail = new Label( "User email:" );
		this.gridPaneUserFields.add( this.labelUserEmail, 0, 1 );
		this.fieldUserEmail = new TextField();
		this.gridPaneUserFields.add( this.fieldUserEmail, 1, 1 );

		this.labelUserPhone = new Label( "User phone:" );
		this.gridPaneUserFields.add( this.labelUserPhone, 0, 2 );
		this.fieldUserPhone = new TextField();
		this.gridPaneUserFields.add( this.fieldUserPhone, 1, 2 );

		this.labelUserPassword = new Label( "set user password" );
		this.gridPaneUserFields.add( this.labelUserPassword, 0, 3 );
		this.fieldUserPassword = new PasswordField();
		this.gridPaneUserFields.add( this.fieldUserPassword, 1, 3 );

		this.checkBoxUserIsAdmin = new JFXCheckBox( "admin?" );
		checkBoxUserIsAdmin.setVisible( false );
		// this.gridPaneUserFields.add( this.checkBoxUserIsAdmin, 1, 4 );

		this.hBoxUserFields.getChildren().add( this.gridPaneUserFields );

		this.hBoxButtons = new HBox();

		hBoxButtons.setPadding( new Insets( 10, 10, 10, 10 ) );
		hBoxButtons.setSpacing( 10 );

		this.buttonSave = new JFXButton( "save" );
		this.hBoxButtons.getChildren().add( buttonSave );

		this.buttonCancel = new JFXButton( "cancel" );
		this.hBoxButtons.getChildren().add( buttonCancel );

		this.gridPaneUserFields.add( hBoxButtons, 1, 4 );

		buildTopOfThisFrame();
		buildTableOfUser();

		/*
		 * 
		 * 
		 * Listening
		 * 
		 * 
		 */

		// stage.setScene(new Scene(new BorderPane(hBoxUserFields)));
		stageForAllPopUps = new StageForAllPopUps( hBoxUserFields, "add new user" );

		this.buttonCancel.setOnAction( e -> {

			stageForAllPopUps.close();

		} );

	}

	public void newUser() {

		stageForAllPopUps.showAndWait();

	}

/*	private User userClicked(){

		int row = tableViewUsers.getSelectionModel().getSelectedIndex();

		if (row >= 0) {
			User user = tableViewUsers.getSelectionModel().getSelectedItem();
			System.out.println("user to update: clicked" + user);
		} else {
			new StageForAlerts();
			StageForAlerts.inform("alert", "please choose a row from the table");
			return;
		}

	}*/

	private User userPrev;

	public void updateUser( User user ) {



		stageForAllPopUps.setTitle( "edit user" );

		// stageForAllPopUps.setTitle("edit user");

		fieldUserEmail.setText( user.getUserEmail() );
		fieldUserName.setText( user.getUserName() );
		fieldUserPassword.setText( user.getUserPassword() );
		fieldUserPhone.setText( user.getUserPhone().trim() );

		if ( user.isAdmin() == true ) {
			checkBoxUserIsAdmin.setSelected( true );
		} else {
			checkBoxUserIsAdmin.setSelected( false );
		}

		buttonSave.setText( "save changes" );

		userPrev = user;

		stageForAllPopUps.showAndWait();

	}

	private User userPrevious;

	private void saveChanges() {

		new StageForAlerts();

		userPrevious = userPrev;

		String userNameNew = null;
		String userPasswordNew = null;
		String userPhoneNew = null;
		String userEmailNew = null;

		if ( fieldUserName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the name can not be empty" );
			return;

		} else {

			userNameNew = fieldUserName.getText();

		}

		userEmailNew = fieldUserEmail.getText();

		if (!fieldUserPhone.getText().trim().isEmpty()) {

			userPhoneNew = fieldUserPhone.getText().trim();

		} else {

			StageForAlerts.inform( "alert", "phone number must be specified" );
			return;

		}

		if ( fieldUserPassword.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "password must be specified" );
			return;

		} else {

			userPasswordNew = fieldUserPassword.getText();

		}

		boolean admin = false;

		if ( checkBoxUserIsAdmin.isSelected() ) {

			admin = true;

		} else {

			admin = false;

		}



		StageForAlerts.discontinue( "confirm", "are you sure you want to save changes?" );

		if ( StageForAlerts.isDecide() == true ) {

			/*
			User user = userPrevious;
			user.setAdmin( admin );
			user.setUserEmail( userEmailNew );
			user.setUserPhone( userPhoneNew );
			user.setUserName( userNameNew );
			user.setUserPassword( userPasswordNew );

			*/

		//	Processing.setProcessingMessage(Processing.DEFAULT_MESSAGE);
		//	Processing.progressDialog(true);

			if (userPrevious.getId().equals(BigDecimal.ZERO)){
				System.out.println("zero id: " + userPrevious.getId());
				return;
			}

			User updatedUser = UserHibernation.updateUser(
					//	public User(BigDecimal id, String userName, String userEmail, String userPhone, String userPassword, boolean admin) {

				new User(userPrevious.getId(),userNameNew, userEmailNew,userPhoneNew,userPasswordNew,admin)

					, userPrevious.getId() );

			UserManipulation.data.set(

					UserManipulation.data.indexOf(userPrevious)

					,

					updatedUser

			);

					AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "edited user: " + updatedUser.getUserName(), UserLoggedIn.getUserLoggedIn() );
					AuditHistoryData.theData.add(auditHistory);

		}

	}

	public static TableView < User > getTableViewUsers() {
		return tableViewUsers;
	}

	public static void setTableViewUsers( TableView < User > tableViewUsers ) {
		UserFrame.tableViewUsers = tableViewUsers;
	}

	private void buildTableOfUser() {

		tableViewUsers = new TableView <>();
		tableViewUsers.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY );

		colUserName = new TableColumn <>( "Name" );
		colUserName.setCellValueFactory( new PropertyValueFactory <>( "userName" ) );
		tableViewUsers.getColumns().add( colUserName );

		colUserEmail = new TableColumn <>( "Email" );
		colUserEmail.setCellValueFactory( new PropertyValueFactory <>( "userEmail" ) );
		tableViewUsers.getColumns().add( colUserEmail );

		colUserPhone = new TableColumn <>( "Phone" );
		colUserPhone.setCellValueFactory( new PropertyValueFactory <>( "userPhone" ) );
		tableViewUsers.getColumns().add( colUserPhone );

		colUserAdmin = new TableColumn <>( "admin?" );
		colUserAdmin.setCellFactory( tc -> new CheckBoxTableCell <>() );

		colUserAdmin.setCellValueFactory(
				new Callback < TableColumn.CellDataFeatures < User, Boolean >, ObservableValue < Boolean > >() {

					@Override
					public ObservableValue < Boolean > call( CellDataFeatures < User, Boolean > param ) {
						User u = param.getValue();
						SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty( u.isAdmin() );
						BooleanProperty property = simpleBooleanProperty;
						return property;
					}
				} );

		// tableViewUsers.getColumns().add( colUserAdmin );

		// VBox vBox = new VBox();F
		// vBox.getChildren().add( tableViewUsers );
		this.setCenter( tableViewUsers );

		// new TableViewCSS(tableViewUsers);



	}

	private void buildTopOfThisFrame() {
		HBox hBox = new HBox();
		Label labelTitleOfTable = new Label( "Table showing all users" );
		hBox.getChildren().add( labelTitleOfTable );
		hBox.setAlignment( Pos.CENTER );
		this.setTop( hBox );
	}

	private void createNewUser() {

		String userName = null;
		String userEmail = null;
		String userPhone = null;
		String userPassword = null;
		boolean admin = false;

		new StageForAlerts();

		if ( fieldUserName.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "the name can not be empty" );
			return;

		} else {

			userName = fieldUserName.getText();

		}

		userEmail = fieldUserEmail.getText().trim();

		if ( fieldUserPhone.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "phone number must be specified" );
			return;

		} else {

			userPhone = fieldUserPhone.getText().trim();

		}

		if ( fieldUserPassword.getText().trim().isEmpty() ) {

			StageForAlerts.inform( "alert", "password must be specified" );
			return;

		} else {

			userPassword = fieldUserPassword.getText();

		}

		if ( checkBoxUserIsAdmin.isSelected() ) {

			admin = true;

		} else {

			admin = false;

		}

		User user = new User();
		user.setAdmin( admin );
		user.setUserEmail( userEmail.trim() );
		user.setUserPhone( userPhone.trim() );
		user.setUserName( userName );
		user.setUserPassword( userPassword );

		StageForAlerts.discontinue( "confirm", "are you sure you want to create this user?" );

		if ( StageForAlerts.isDecide() == true ) {

			// start processing

			User newUser = UserHibernation.newuser(user);
			UserManipulation.data.add(newUser);
			AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "create new user: " + user.getUserName(),
					UserLoggedIn.getUserLoggedIn() );
			AuditHistoryData.theData.add(auditHistory);

			ObservableList < User > list = UserHibernation.findAllUsersRefreshed();

			PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( list );
			PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(),
					PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

			PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(),
					PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

			// end processing

			if ( list.size() < 2 ) {
				new StageForAlerts();
				StageForAlerts.inform( "alert",
						"the system will close down. you must restart the application in order to keep track of every user action" );

				System.exit( 0 );

			}

		}

	}

	public void deleteUser( BigDecimal userId ) {

		StageForAlerts.discontinue( "confirm", "are you sure you want to permanently this user?" );

		if ( StageForAlerts.isDecide() == true ) {


			// start processing

			UserHibernation.deleteUser( userId );

			UserManipulation.data.remove(UserHibernation.mapOfUserAndThierId().get(userId));

		//	Processing.startUpdateDaemonTask(() -> Refresh.setRefreshingDeterminant( 1 ));



			AuditHistory auditHistory = AuditHistoryHibernation.auditValues( "deleted a user", UserLoggedIn.getUserLoggedIn() );

			AuditHistoryData.theData.add(auditHistory);

			PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( UserHibernation.findAllUsersRefreshed() );
			PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(),
					PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );
			PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(),
					PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

		}

	}

	private void listening() {

		buttonSave.setOnAction( e -> {
			if ( stageForAllPopUps.getTitle().equals( "edit user" ) ) {

				saveChanges();

				clearFields();
				stageForAllPopUps.close();
				stageForAllPopUps.setTitle( "add new user" );
				buttonSave.setText( "save" );


			} else {
				createNewUser();
				clearFields();
			}

		} );

	}

	private void clearFields() {

		FieldClearance.clearTextField( fieldUserEmail );
		FieldClearance.clearTextField( fieldUserName );
		FieldClearance.clearTextField( fieldUserPassword );
		FieldClearance.clearTextField( fieldUserPhone );
	}

}
