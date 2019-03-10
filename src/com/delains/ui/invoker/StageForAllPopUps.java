package com.delains.ui.invoker;

import com.delains.dao.imageicon.ImageIconDAO;
import com.delains.model.imageicon.ImageIcon;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class StageForAllPopUps extends Stage {

	private BorderPane borderPane;
	private Scene scene;

	public StageForAllPopUps() {

	}

	public static void closeStage() {
		new StageForAllPopUps().close();
	}

	private static Image image;

	public static Image getImage() {
		return image;
	}

	public static void setImage( Image image ) {
		StageForAllPopUps.image = image;
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

	public StageForAllPopUps( Node nod, String stageTitle ) {

		if ( getImage() == null ) {
			getIcon();
		}

		this.initModality( Modality.APPLICATION_MODAL );

		this.setTitle( stageTitle );
		this.getIcons().add( getImage() );

		this.setResizable( false );

		this.borderPane = new BorderPane();
		this.borderPane.setId( "main_borderpane" );
		this.borderPane.setCenter( nod );

		scene = new Scene( borderPane );

		scene.getStylesheets().add( getClass().getResource( "alerts.css" ).toExternalForm() );

		this.setScene( scene );

	}

}
