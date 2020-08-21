package com.delains.ui.invoker;

import com.jfoenix.controls.JFXCheckBox;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FieldClearance {

	public static void clearTextField(TextField field) {
		field.clear();
	}

	public static void clearComboBox(ComboBox<?> box) {
		box.getSelectionModel().clearSelection();
	}

	public static void clearCheckBox(JFXCheckBox checkBox) {
		checkBox.setSelected(false);
	}

}
