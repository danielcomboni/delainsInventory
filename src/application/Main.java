
package application;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;

import com.delains.dao.history.AuditHistoryHibernation;
import com.delains.dao.imageicon.ImageIconDAO;
import com.delains.dao.users.UserHibernation;
import com.delains.dao.users.UserLoggedIn;
import com.delains.daol.licence.ActivatedKeyHibernation;
import com.delains.daol.licence.LicenceHibernation;
import com.delains.daol.licence.RandomNumberHibernation;
import com.delains.model.imageicon.ImageIcon;
import com.delains.model.licence.ActivatedKey;
import com.delains.model.licence.Licence;
import com.delains.model.users.User;
import com.delains.ui.history.AuditHistoryManipulation;
import com.delains.ui.invoker.AccordionTabs;
import com.delains.ui.invoker.ComponentWidth;
import com.delains.ui.invoker.InvokerUI;
import com.delains.ui.invoker.PrivilegeAtBeginningDisableOtherAccordions;
import com.delains.ui.invoker.StageForAlerts;
import com.delains.ui.invoker.StageForAllPopUps;
import com.delains.ui.licence.LicenceUI;
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

	private Scene scene;

	@Override
	public void start( Stage primaryStage ) {
		try {

			primaryStage.centerOnScreen();

			BorderPane root = new BorderPane();

			root.setCenter( this.buildLoginComponents( primaryStage ) );

			primaryStage.setResizable( false );

			double width = 600;

			scene = new Scene( root, width, 600 );

			primaryStage.centerOnScreen();

			scene.getStylesheets().add( getClass().getResource( "application.css" ).toExternalForm() );

			 String powerdedBy = "		(powered by lazimisha) ";
//			String powerdedBy = "";

			ImageIcon icon = ImageIconDAO.getImageIconAndTitle();

			if ( icon.getBusinessTitle() == null ) {
				primaryStage.setTitle( powerdedBy );
				primaryStage.getIcons().add( new Image( "/application/prof.jpg" ) );
			} else {
				primaryStage.setTitle( icon.getBusinessTitle().concat( powerdedBy ) );
				primaryStage.getIcons().add( icon.getImage() );
			}

			primaryStage.setScene( scene );

			primaryStage.setScene( scene );

			this.buttonLogin.setOnAction( e -> {

				boolean test = login();

				System.out.println( "test login: " + test );

				if ( test == true ) {

					if ( !textArea.getText().isEmpty() && !fieldTitle.getText().isEmpty() ) {

						ImageIcon ic = new ImageIcon();

						if ( !textArea.getText().isEmpty() ) {
							ic.setFile( this.getFile() );
						}

						if ( !fieldTitle.getText().isEmpty() ) {
							ic.setBusinessTitle( fieldTitle.getText() );
						}

						ImageIconDAO.newIconAndTitle( ic );

						ImageIcon icon2 = ImageIconDAO.getImageIconAndTitle();
						System.out.println( "icon: " + icon2 );

						if ( icon2.getBusinessTitle() == null ) {
							primaryStage.setTitle( powerdedBy );
							primaryStage.getIcons().add( new Image( "/application/prof.jpg" ) );
						} else {
							primaryStage.setTitle( icon2.getBusinessTitle().concat( powerdedBy ) );
							primaryStage.getIcons().add( icon2.getImage() );
						}

					} else {

						ImageIcon icon3 = ImageIconDAO.getImageIconAndTitle();
						// System.out.println( "icon: " + icon2 );

						if ( icon3.getBusinessTitle() == null ) {
							primaryStage.setTitle( powerdedBy );
							primaryStage.getIcons().add( new Image( "/application/prof.jpg" ) );
						} else {
							primaryStage.setTitle( icon3.getBusinessTitle().concat( powerdedBy ) );
							primaryStage.getIcons().add( icon3.getImage() );
						}

					}

					InvokerUI invokerUI = new InvokerUI();
					primaryStage.setResizable( true );
					primaryStage.hide();
					primaryStage.setScene( this.setNewScene( invokerUI ) );
					primaryStage.show();

					AuditHistoryHibernation.auditValues( "logged in", user );
					AuditHistoryManipulation.populateTableWithRefreshing();

					UserLoggedIn.setUserLoggedIn( user );

				} else {

					Alert alert = new Alert( AlertType.WARNING );
					alert.setContentText( "please check your login credentials" );
					alert.showAndWait();

				}

			} );

			buttonForgotPassword.setOnAction( e -> {

				if ( !textArea.getText().isEmpty() && !fieldTitle.getText().isEmpty() ) {

					ImageIcon ic = new ImageIcon();

					if ( !textArea.getText().isEmpty() ) {
						ic.setFile( this.getFile() );
					}

					if ( !fieldTitle.getText().isEmpty() ) {
						ic.setBusinessTitle( fieldTitle.getText() );
					}

					ImageIconDAO.newIconAndTitle( ic );

					ImageIcon icon2 = ImageIconDAO.getImageIconAndTitle();
					System.out.println( "icon: " + icon2 );

					if ( icon2.getBusinessTitle() == null ) {
						primaryStage.setTitle( powerdedBy );
						primaryStage.getIcons().add( new Image( "/application/prof.jpg" ) );
					} else {
						primaryStage.setTitle( icon2.getBusinessTitle().concat( powerdedBy ) );
						primaryStage.getIcons().add( icon2.getImage() );
					}

				} else {

					ImageIcon icon3 = ImageIconDAO.getImageIconAndTitle();
					// System.out.println( "icon: " + icon2 );

					if ( icon3.getBusinessTitle() == null ) {
						primaryStage.setTitle( powerdedBy );
						primaryStage.getIcons().add( new Image( "/application/prof.jpg" ) );
					} else {
						primaryStage.setTitle( icon3.getBusinessTitle().concat( powerdedBy ) );
						primaryStage.getIcons().add( icon3.getImage() );
					}

				}

				InvokerUI invokerUI = new InvokerUI();
				primaryStage.setResizable( true );
				primaryStage.hide();
				primaryStage.setScene( this.setNewScene( invokerUI ) );
				primaryStage.show();

				ObservableList < User > list = UserHibernation.findAllUsersRefreshed();

				PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( list );

				PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(), false );

				PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(), false );
				new StageForAlerts();
				StageForAlerts.inform( "alert",
						"you will only have access to the user panel for now. re-create a user with credentials you can easily remember. Then restart the application" );

			} );

			primaryStage.show();

			ActivatedKey key = ActivatedKeyHibernation.getCurrentActivatedKey();
			System.out.println( "key : " + key );

			RandomNumberHibernation.newRandomNumbers();

			Licence lastLicence = LicenceHibernation.getLastLicence();

			if ( key.getLicenceId() == null ) {
				System.out.println( "no key licenL;;;;;;;;;;;;;;;;;;;;" );
				buttonForgotPassword.setDisable( true );
				buttonLogin.setDisable( true );
				StageForAllPopUps stageForAllPopUps = new StageForAllPopUps( new LicenceUI(), "licence verification" );
				stageForAllPopUps.showAndWait();
			}

			else if ( key.getLicenceId().getId() != null ) {
				BigDecimal daysLeft = LicenceHibernation.periodDeterminant( lastLicence.getToDate() );
				System.out.println( "days left: " + daysLeft );
				System.out.println( "no key licenL;;;;;;;;;;;;;;;;;;;;=============" );
				System.out.println( "days:...: " + daysLeft );
				if ( daysLeft.intValue() <= 0 ) {
					buttonForgotPassword.setDisable( true );
					buttonLogin.setDisable( true );
					StageForAllPopUps stageForAllPopUps = new StageForAllPopUps( new LicenceUI(),
							"licence verification" );
					stageForAllPopUps.showAndWait();
				}

			}

			// disabbleOnTestExpirationDate();

		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	private String password = null;
	private User user = null;

	private boolean login() {

		boolean allow = false;

		ObservableList < User > users = UserHibernation.findAllUsersRefreshed();

		users.stream().parallel().forEach( u -> {

			if ( fieldEmail.getText().trim().equals( u.getUserEmail() )
					|| fieldEmail.getText().trim().equals( u.getUserPhone().trim() ) ) {

				password = u.getUserPassword();

				user = u;

			}

		} );

		boolean checkPass = false;

		System.out.println( "us log: " + user );

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

		if ( checkPass == true ) {

			allow = true;

			if ( users.stream().findFirst().get().getUserEmail().equals( "admin@admin.com" ) ) {

				UserHibernation.deleteDefaultUser( user.getId(), "admin@admin.com" );

			}

		} else {

		}

		return allow;

	}

	public static void main( String [ ] args ) {
		launch( args );
	}

	private Scene setNewScene( BorderPane borderPane ) {
		Scene scene = new Scene( borderPane, Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight() );
		borderPane.setBackground( Background.EMPTY );
		return scene;
	}

	private Label labelEmail;
	private TextField fieldEmail;

	private Label labelPassword;
	private PasswordField passwordField;

	private JFXButton buttonLogin;
	private JFXButton buttonForgotPassword;
	private GridPane gridPane;

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

		gridPane = new GridPane();
		gridPane.setId( "gridPane" );

		labelEmail = new Label( "email (phone number)." );

		fieldEmail = new TextField();
		ComponentWidth.setWidthOfTextField( fieldEmail, 300 );

		labelPassword = new Label( "password" );
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

		gridPane.add( labelEmail, 0, 0 );
		gridPane.add( fieldEmail, 1, 0 );

		gridPane.add( labelPassword, 0, 1 );
		gridPane.add( passwordField, 1, 1 );

		gridPane.add( buttonLogin, 1, 2 );
		gridPane.add( buttonForgotPassword, 1, 3 );
		gridPane.setAlignment( Pos.CENTER );

		VBox box = new VBox();
		if ( UserHibernation.findAllUsersRefreshed().size() == 1
				&& UserHibernation.findAllUsersRefreshed().get( 0 ).getUserName().equals( "admin" ) ) {
			box.getChildren().add( defaultLoginCreds() );
		}

		box.getChildren().add( gridPane );

		box.getChildren().add( browseImageIcon( stage ) );

		return box;

	}

	private FileChooser fileChooser;
	private JFXButton buttonBrowseIcon;
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

		buttonBrowseIcon = new JFXButton( "choose icon (optional)" );
		textArea = new TextArea();

		imageView = new ImageView();

		buttonBrowseIcon.setOnAction( e -> {

			file = fileChooser.showOpenDialog( stage );
			if ( file != null ) {
				// desktop.open( file );
				textArea.setText( file.getAbsolutePath() );
				System.out.println( "text file: " + textArea.getText() );
				image = new Image( file.toURI().toString(), 150, 200, true, true );// path, pref width, pref height,
																					// preservation ratio, smooth
				imageView.setImage( image );
				imageView.setFitWidth( 150 );
				imageView.setFitHeight( 200 );
				imageView.setPreserveRatio( true );

				setFile( new File( textArea.getText() ) );

			}
		} );

		vb.getChildren().add( buttonBrowseIcon );
		textArea.setVisible( false );
		vb.getChildren().add( imageView );
		// vb.getChildren().add( textArea );
		vb.setPadding( new Insets( 20 ) );

		Label lableTitle = new Label( "Title(business name):" );
		vb.getChildren().add( lableTitle );

		fieldTitle = new TextField();
		fieldTitle.setPromptText( "enter the name of your business here" );

		vb.getChildren().add( fieldTitle );

		return vb;
	}

	private void disabbleOnTestExpirationDate() {
		LocalDate date = LocalDate.now();
		if ( String.valueOf( date ).equals( "2019-02-27" ) || String.valueOf( date ).contains( "2019-02" )
				|| String.valueOf( date ).contains( "2019-03" ) || String.valueOf( date ).contains( "2019-04" ) ) {
			buttonLogin.setDisable( true );
			buttonForgotPassword.setDisable( true );
			Alert alert = new Alert( AlertType.WARNING );
			alert.setContentText( "your license has expired!!!\nsorry" );
			alert.showAndWait();
		}
	}

}
