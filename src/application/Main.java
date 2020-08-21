
package application;

import java.io.File;

import com.delains.model.history.AuditHistory;
import com.delains.ui.history.AuditHistoryData;
import org.mindrot.jbcrypt.BCrypt;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.imageicon.ImageIconDAO;
import com.delains.dao.users.UserHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.model.imageicon.ImageIcon;
import com.delains.model.users.User;
import com.delains.ui.invoker.AccordionTabs;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.InvokerUI;
import com.delains.ui.invoker.PrivilegeAtBeginningDisableOtherAccordions;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.users.UserAdminDefault;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start( Stage primaryStage ) {
		try {

			primaryStage.centerOnScreen();

			BorderPane root = new BorderPane();

			root.setCenter( this.buildLoginComponents( primaryStage ) );

			primaryStage.setResizable( false );

			double width = 600;

			Scene scene = new Scene(root, width, 600);

			primaryStage.centerOnScreen();

			scene.getStylesheets().add( getClass().getResource( "application.css" ).toExternalForm() );

			String powerdedBy = "Total Mbarara Rwizi inventory system-  ( developed by 0789 018 675 / 0781 750 721 )";
//			String powerdedBy = "";

			ImageIcon icon;
			icon = ImageIconDAO.getImageIconAndTitle();

			if ( icon.getBusinessTitle() == null ) {
				primaryStage.setTitle( powerdedBy );
				primaryStage.getIcons().add( new Image( "/application/total.png" ) );
			} else {
				primaryStage.setTitle( icon.getBusinessTitle().concat( powerdedBy ) );
				primaryStage.getIcons().add( icon.getImage() );
			}

			primaryStage.setScene(scene);

			primaryStage.setScene(scene);

			this.buttonLogin.setOnAction( e -> {

				boolean test = login();

				if (test){

					showStageAtLogin(primaryStage, powerdedBy);
					AuditHistory auditHistory = AuditHistoryHibernation.auditValues("logged in", user);
					AuditHistoryData.theData.add(auditHistory);
					UserLoggedIn.setUserLoggedIn(user);

				}

				else {

					Alert alert = new Alert( AlertType.WARNING );
					alert.setContentText( "please check your login credentials" );
					alert.showAndWait();

				}

			} );

			buttonForgotPassword.setOnAction( e -> {

				showStageAtLogin(primaryStage, powerdedBy);

				ObservableList < User > list = UserHibernation.findAllUsersRefreshed();

				PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( list );

				PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(), false );

				PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(), false );
				new StageForAlerts();
				StageForAlerts.inform( "alert",
						"you will only have access to the user panel for now. re-create a user with credentials you can easily remember. Then restart the application" );

			} );

			primaryStage.show();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private void showStageAtLogin(Stage primaryStage, String powerdedBy) {
		if (!textArea.getText().isEmpty() && !fieldTitle.getText().isEmpty()) {

			ImageIcon ic = new ImageIcon();

			if (!textArea.getText().isEmpty()) {
				ic.setFile(this.getFile());
			}

			if (!fieldTitle.getText().isEmpty()) {
				ic.setBusinessTitle(fieldTitle.getText());
			}

			ImageIconDAO.newIconAndTitle(ic);

			ImageIcon icon2 = ImageIconDAO.getImageIconAndTitle();
			if (icon2.getBusinessTitle() == null) {
				primaryStage.setTitle(powerdedBy);
				primaryStage.getIcons().add(new Image("/application/prof.jpg"));
			} else {
				primaryStage.setTitle(icon2.getBusinessTitle().concat(powerdedBy));
				primaryStage.getIcons().add(icon2.getImage());
			}

		} else {

			ImageIcon icon3 = ImageIconDAO.getImageIconAndTitle();
			if (icon3.getBusinessTitle() == null) {
				primaryStage.setTitle(powerdedBy);
				primaryStage.getIcons().add(new Image("/application/prof.jpg"));
			} else {
				primaryStage.setTitle(icon3.getBusinessTitle().concat(powerdedBy));
				primaryStage.getIcons().add(icon3.getImage());
			}

		}

		InvokerUI invokerUI = new InvokerUI();
		primaryStage.setResizable(true);
		primaryStage.hide();
		primaryStage.setScene(this.setNewScene(invokerUI));
		primaryStage.show();

//		primaryStage.setResizable(true);
//		primaryStage.hide();
//		primaryStage.show();
//		InvokerUI invokerUI = new InvokerUI();
//		primaryStage.setScene(this.setNewScene(invokerUI));


	}

	private String password = null;
	private User user = null;

	private boolean login() {

		boolean allow = false;

		ObservableList < User > users = UserHibernation.findAllUsersRefreshed();

		// check username
		users.stream().parallel().forEach( u -> {

			if ( fieldEmail.getText().trim().equals( u.getUserEmail() )
					|| fieldEmail.getText().trim().equals( u.getUserPhone().trim() ) ) {

				password = u.getUserPassword();

				user = u;

			}

		} );

		boolean checkPass = false;

// validate password
		if ( user != null ) {

			checkPass = BCrypt.checkpw( passwordField.getText().trim(), password );

		} else {

			if ( UserHibernation.findAllUsersRefreshed().size() == 1
					&& UserHibernation.findAllUsersRefreshed().get( 0 ).getUserName().equals( "admin" ) ) {

				Alert alert = new Alert( AlertType.WARNING );
				alert.setContentText( "please following the instructions\nand write the correct default email" );
				alert.showAndWait();

				checkPass = false;

			}

		}

		if (checkPass) {

			allow = true;

			if ( users.stream().findFirst().get().getUserEmail().equals( "admin@admin.com" ) ) {

				UserHibernation.deleteDefaultUser( user.getId(), "admin@admin.com" );

			}

		}

		return allow;

	}


	public static void main( String [ ] args ) {
		launch( args );
	}

	private Scene setNewScene( BorderPane borderPane ) {

		double minifiedHeight = Screen.getPrimary().getBounds().getHeight() * (4.5/5);
//		double minifiedHeight = Screen.getPrimary().getBounds().getHeight() * (3/4);

		Scene scene = new Scene( borderPane, Screen.getPrimary().getBounds().getWidth(),
				minifiedHeight  );
		borderPane.setBackground( Background.EMPTY );
		return scene;
	}

	private TextField fieldEmail;

	private PasswordField passwordField;

	private JFXButton buttonLogin;
	private JFXButton buttonForgotPassword;

	private VBox defaultLoginCreds() {

		VBox box = new VBox( 10 );

		Label labelDirective = new Label(
				"use the following default credentials to allow access\nthen immediately create users please" );
		Label labelEmail = new Label( "email: admin@admin.com" );
		Label labelPassword = new Label( "password: 1234" );

		box.getChildren().addAll( labelDirective, labelEmail, labelPassword );

		return box;

	}

	private VBox buildLoginComponents( Stage stage ) {

		new UserAdminDefault();

		GridPane gridPane = new GridPane();
		gridPane.setId( "gridPane" );

		Label labelEmail = new Label("email (phone number).");

		fieldEmail = new TextField();
		ComponentWidth.setWidthOfTextField( fieldEmail, 300 );

		Label labelPassword = new Label("password");
		labelPassword.setId( "labelPassword" );
		passwordField = new PasswordField();
		passwordField.setId( "#passwordField" );

		buttonLogin = new JFXButton( "Login" );
		buttonLogin.setButtonType( ButtonType.RAISED );

		buttonForgotPassword = new JFXButton( "forgot password?" );
		buttonForgotPassword.setButtonType( ButtonType.RAISED );

		// adding components to grid pane
		gridPane.setPadding( new Insets( 20, 20, 20, 20 ) );
		gridPane.setVgap( 5 );
		gridPane.setHgap( 5 );
		gridPane.resize( 800, 500 );

		gridPane.add(labelEmail, 0, 0 );
		gridPane.add( fieldEmail, 1, 0 );

		gridPane.add(labelPassword, 0, 1 );
		gridPane.add( passwordField, 1, 1 );

		gridPane.add( buttonLogin, 1, 2 );
		gridPane.add( buttonForgotPassword, 1, 3 );
		gridPane.setAlignment( Pos.CENTER );

		VBox box = new VBox();
		if ( UserHibernation.findAllUsersRefreshed().size() == 1
				&& UserHibernation.findAllUsersRefreshed().get( 0 ).getUserName().equals( "admin" ) ) {
			box.getChildren().add( defaultLoginCreds() );
		}

		box.getChildren().add(gridPane);

		box.getChildren().add( browseImageIcon( stage ) );

		return box;

	}

	private FileChooser fileChooser;
	private File file;

	private TextArea textArea;

	private ImageView imageView;
	private Image image;

	private TextField fieldTitle;

	public File getFile() {
		return file;
	}

	public void setFile( File file ) {
		this.file = file;
	}

	private VBox browseImageIcon( Stage stage ) {

		VBox vb = new VBox( 10 );

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(

				new ExtensionFilter( "image files", "*.png", "*.jpg", "*jpeg", "*.PNG" ),
				new ExtensionFilter( "image files", "*." )

		);

		String userDirectory = System.getProperty( "user.home" );
		file = new File( userDirectory );
		fileChooser.setInitialDirectory( file );

		JFXButton buttonBrowseIcon = new JFXButton("choose icon (optional)");
		textArea = new TextArea();

		imageView = new ImageView();

		buttonBrowseIcon.setOnAction(e -> {

			file = fileChooser.showOpenDialog( stage );
			if ( file != null ) {
				// desktop.open( file );
				textArea.setText( file.getAbsolutePath() );
				image = new Image( file.toURI().toString(), 150, 200, true, true );// path, pref width, pref height,
				// preservation ratio, smooth
				imageView.setImage( image );
				imageView.setFitWidth( 150 );
				imageView.setFitHeight( 200 );
				imageView.setPreserveRatio( true );

				setFile( new File( textArea.getText() ) );

			}
		} );

		vb.getChildren().add(buttonBrowseIcon);
		textArea.setVisible( false );
		vb.getChildren().add( imageView );
		// vb.getChildren().add( textArea );
		vb.setPadding( new Insets( 20 ) );

		Label labelTitle = new Label( "Title(business name):" );
		vb.getChildren().add( labelTitle );

		fieldTitle = new TextField();
		fieldTitle.setPromptText( "enter the name of your business here" );

		vb.getChildren().add( fieldTitle );

		return vb;
	}

	/*private void disabbleOnTestExpirationDate() {
		LocalDate date = LocalDate.now();
		if ( String.valueOf( date ).equals( "2019-02-27" ) || String.valueOf( date ).contains( "2019-02" )
				|| String.valueOf( date ).contains( "2019-03" ) || String.valueOf( date ).contains( "2019-04" ) ) {
			buttonLogin.setDisable( true );
			buttonForgotPassword.setDisable( true );
			Alert alert = new Alert( AlertType.WARNING );
			alert.setContentText( "your license has expired!!!\nsorry" );
			alert.showAndWait();
		}
	}*/

}
