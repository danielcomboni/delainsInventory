package com.delains.ui.invoker;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ComponentWidth {

	private ComponentWidth() {

	}

	public static void setWidthOfTextField( TextField textField, int width ) {
		textField.setPrefWidth( width );
	}

	public static void setWidthOfComboBox( ComboBox < Object > comboBox, int width ) {
		comboBox.setPrefWidth( width );
	}

}
