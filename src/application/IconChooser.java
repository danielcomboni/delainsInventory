package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.jfoenix.controls.JFXButton;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class IconChooser {

	private Desktop desktop = Desktop.getDesktop();
	private FileChooser fileChooser;
	private JFXButton buttonBrowseIcon;
	private File file;

	public JFXButton getButtonBrowseIcon() {
		return buttonBrowseIcon;
	}

	public void setButtonBrowseIcon( JFXButton buttonBrowseIcon ) {
		this.buttonBrowseIcon = buttonBrowseIcon;
	}

	public IconChooser( Stage stage ) {

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(

				new ExtensionFilter( "image files", "*.png", "*.jpg", "*jpeg", "*.PNG" ),
				new ExtensionFilter( "image files", "*." )

		);

		buttonBrowseIcon = new JFXButton( "choose icon (optional)" );

		buttonBrowseIcon.setOnAction( e -> {
			file = fileChooser.showOpenDialog( stage );
			if ( file != null ) {
				try {
					desktop.open( file );
				} catch ( IOException e1 ) {
					e1.printStackTrace();
				}
			}
		} );

	}

}
