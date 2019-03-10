package com.delains.ui.login;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXPasswordField;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class LoginUI extends HBox {

	private Label labelEmail;
	private TextField fieldEmail;

	private Label labelPassword;
	private JFXPasswordField passwordField;

	private JFXButton buttonLogin;
	private JFXButton buttonForgotPassword;
	private GridPane gridPane;

	public TextField getFieldEmail() {
		return fieldEmail;
	}

	public void setFieldEmail(TextField fieldEmail) {
		this.fieldEmail = fieldEmail;
	}

	public JFXPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JFXPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JFXButton getButtonLogin() {
		return buttonLogin;
	}

	public void setButtonLogin(JFXButton buttonLogin) {
		this.buttonLogin = buttonLogin;
	}

	public JFXButton getButtonForgotPassword() {
		return buttonForgotPassword;
	}

	public void setButtonForgotPassword(JFXButton buttonForgotPassword) {
		this.buttonForgotPassword = buttonForgotPassword;
	}

	public LoginUI() {
		buildLoginComponents();
	}

	private void buildLoginComponents() {

		getStylesheets().add(LoginUI.class.getResource("login.css").toExternalForm());

		this.setId("loginBox");

		gridPane = new GridPane();
		gridPane.setId("gridPane");

		labelEmail = new Label("Email or phone No.");
		fieldEmail = new TextField();

		labelPassword = new Label("Password");
		labelPassword.setId("labelPassword");
		passwordField = new JFXPasswordField();
		passwordField.setId("#passwordField");

		buttonLogin = new JFXButton("Login");
		buttonLogin.setButtonType(ButtonType.RAISED);
		buttonLogin.setStyle("-fx-background-color:white");

		buttonForgotPassword = new JFXButton("forgot password?");
		buttonForgotPassword.setButtonType(ButtonType.RAISED);
		buttonForgotPassword.setStyle("-fx-background-color:white");

		// adding components to grid pane
		gridPane.setPadding(new Insets(20, 20, 20, 20));
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.resize(500, 500);
		gridPane.setStyle("-fx-background-color:rgb(0,0,0,0.55)");

		gridPane.add(labelEmail, 0, 0);
		gridPane.add(fieldEmail, 1, 0);

		gridPane.add(labelPassword, 0, 1);
		gridPane.add(passwordField, 1, 1);

		gridPane.add(buttonLogin, 1, 2);
		gridPane.add(buttonForgotPassword, 1, 3);
		gridPane.setAlignment(Pos.CENTER);
		this.getChildren().add(gridPane);

	}

}
