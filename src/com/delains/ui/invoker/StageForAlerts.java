package com.delains.ui.invoker;

import com.delains.dao.imageicon.ImageIconDAO;
import com.delains.model.imageicon.ImageIcon;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class StageForAlerts extends BorderPane {

	private static StackPane borderPane;

	private static JFXButton buttonContinue;
	private static JFXButton buttonDiscontinue;

	private static Image image;

	public static Image getImage() {
		return image;
	}

	public static void setImage( Image image ) {
		StageForAlerts.image = image;
	}

	public static boolean discontinue( String title, String contentText ) {


		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(contentText);
		// alert.setContentText("C:/MyFile.txt");

		Optional<ButtonType> option = alert.showAndWait();

		if (option.get() == ButtonType.OK){
			decide = true;
		}else{
			decide = false;
		}

		/*
		if (option.get() == null) {
			this.label.setText("No selection!");
		} else if (option.get() == ButtonType.OK) {
			this.label.setText("File deleted!");
		} else if (option.get() == ButtonType.CANCEL) {
			this.label.setText("Cancelled!");
		} else {
			this.label.setText("-");
		}

*/

		boolean continuation = false;

		/*
		labelContenttext.setText( contentText );
		stage.setTitle( title );
		stage.getIcons().add( getImage() );

		stage.showAndWait();
*/
		return continuation;
	}

	public static void inform( String title, String contentText ) {


		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(title);
		alert.setContentText(contentText);

		alert.showAndWait();

		/*stage.setTitle( title );

		stage.getIcons().add( getImage() );

		labelContenttext.setText( contentText );
		buttonContinue.setVisible( false );
		buttonDiscontinue.setText( "OK" );

		buttonDiscontinue.requestFocus();

		stage.showAndWait();
*/
	}

	private static Stage stage;
	private static TextArea labelContenttext;

	private static boolean decide = false;

	public static boolean isDecide() {
		return decide;
	}

	public static void setDecide( boolean decide ) {
		StageForAlerts.decide = decide;
	}

	private Image getIcon() {
		ImageIcon icon = ImageIconDAO.getImageIconAndTitle();
		if ( icon.getBusinessTitle() != null ) {

			if ( getImage() == null ) {
				setImage( icon.getImage() );
			}
			return getImage();
		} else {
			setImage( new Image( "/application/rain_drop.jpg" ) );
			return getImage();
		}
	}

	public StageForAlerts() {

		if ( getImage() == null ) {
			getIcon();
		}

		borderPane = new StackPane();
		borderPane.setPadding( new Insets( 20, 20, 20, 20 ) );

		borderPane.setStyle( "-fx-background-image:url(/com/delains/ui/images/bg_delains_zero.png)" );

		borderPane.setId( "main_borderpane" );

		stage = new Stage();

		Scene scene = new Scene( borderPane );

		stage.setScene( scene );

		scene.getStylesheets().add( getClass().getResource( "alerts.css" ).toExternalForm() );

		VBox box = new VBox();
		labelContenttext = new TextArea();
		labelContenttext.setWrapText( true );
		labelContenttext.setPrefWidth( 100 );
		labelContenttext.setPrefHeight( 80 );
		labelContenttext.setEditable( false );

		box.getChildren().add( labelContenttext );

		GridPane gridPane = new GridPane();

		gridPane.setHgap( 10 );
		gridPane.setVgap( 10 );
		gridPane.setPadding( new Insets( 20, 20, 20, 20 ) );
		gridPane.setStyle( "	"

				+ "-fx-background-color: rgba(0, 0, 0, 0.55);"

				+ "" );

		String styleButtons = ""

				+ "	-fx-text-fill: white;" +

				"	-fx-font-family: Arial Narrow;"

				+ "-fx-font-weight: bold;"

				+ "-fx-background-color: linear-gradient(#61a2b1, #2A5058);"

				+ "-fx-effect: dropshadow(three-pass-box, rgba(0, 0, 0, 0.6), 5, 0.0, 0, 1);";

		buttonContinue = new JFXButton( "continue" );

		buttonContinue.setStyle( styleButtons );

		gridPane.add( buttonContinue, 0, 0 );

		buttonDiscontinue = new JFXButton( "cancel" );
		buttonDiscontinue.setStyle( styleButtons );
		gridPane.add( buttonDiscontinue, 1, 0 );

		// borderPane.setStyle("-fx-background-image:
		// url(/com/delains/ui/images/bg_delains_zero.png)");

		box.getChildren().add( gridPane );

		borderPane.getChildren().add( box );
		// borderPane.getChildren().add(gridPane);
		// borderPane.setTop(box);

		// borderPane.setCenter(gridPane);

		stage.initStyle( StageStyle.UNIFIED );
		stage.initModality( Modality.APPLICATION_MODAL );
		stage.setResizable( false );

		buttonContinue.setOnAction( e -> {
			setDecide( true );
			stage.close();
		} );

		buttonDiscontinue.setOnAction( e -> {
			setDecide( false );
			stage.close();
		} );

	}

}
