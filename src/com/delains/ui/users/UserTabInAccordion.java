package com.delains.ui.users;

import com.delains.model.users.User;
import com.delains.ui.invoker.StageForAlerts;
import com.jfoenix.controls.JFXButton;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UserTabInAccordion extends VBox {

	private GridPane gridPane;
	private JFXButton buttonAddNewUser;
	private JFXButton buttonSelectUserForEditing;
	private JFXButton buttonDeleteUser;

	public JFXButton getButtonSelectUserForEditing() {
		return buttonSelectUserForEditing;
	}

	public void setButtonSelectUserForEditing(JFXButton buttonSelectUserForEditing) {
		this.buttonSelectUserForEditing = buttonSelectUserForEditing;
	}

	public JFXButton getButtonDeleteUser() {
		return buttonDeleteUser;
	}

	public void setButtonDeleteUser(JFXButton buttonDeleteUser) {
		this.buttonDeleteUser = buttonDeleteUser;
	}

	private UserFrame frame;

	public UserTabInAccordion() {

		gridPane = new GridPane();
		gridPane.setVgap(5);
		gridPane.setHgap(5);
		gridPane.setPadding(new Insets(5, 5, 5, 5));

		buttonAddNewUser = new JFXButton("Add new user");
		gridPane.add(buttonAddNewUser, 0, 0);

		buttonSelectUserForEditing = new JFXButton("Edit user info");
		gridPane.add(buttonSelectUserForEditing, 0, 1);

		buttonDeleteUser = new JFXButton("Delete user");
		gridPane.add(buttonDeleteUser, 0, 2);

		this.getChildren().add(gridPane);
		frame = new UserFrame();

		buttonAddNewUser.setOnAction(e -> {
			popUpNewUserAddition();
		});

		buttonSelectUserForEditing.setOnAction(e -> {
			popUpUserChange();
		});

		buttonDeleteUser.setOnAction(e -> {
			deleteUser();
		});

	}

	private void popUpNewUserAddition() {

		frame.newUser();
	}

	private void popUpUserChange() {

		int row = UserFrame.getTableViewUsers().getSelectionModel().getSelectedIndex();

		if (row < 0) {
			new StageForAlerts();
			StageForAlerts.inform("alert", "please choose a row from the table");
			return;
		}

		User user = UserFrame.getTableViewUsers().getSelectionModel().getSelectedItem();
		System.out.println("user to update: " + user);
		frame.updateUser(user);

	}

	private void deleteUser() {
		new StageForAlerts();

		int row = UserFrame.getTableViewUsers().getSelectionModel().getSelectedIndex();
		if (row < 0) {

			StageForAlerts.inform("alert", "choose a row to be delete");
			return;

		} else {
			User user = UserFrame.getTableViewUsers().getSelectionModel().getSelectedItem();
			frame.deleteUser(user.getId());
		}

	}

}
