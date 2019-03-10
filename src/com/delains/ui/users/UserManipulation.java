package com.delains.ui.users;

import com.delains.dao.users.UserHibernation;
import com.delains.model.users.User;
import com.delains.ui.invoker.Refresh;

import javafx.scene.control.TableView;

public class UserManipulation {

	private static void populateUsersTableWithoutRefreshing(TableView<User> tableView) {

		if (tableView.getItems().isEmpty() || tableView.getItems() == null) {

			tableView.setItems(UserHibernation.findAllUsers());

		}

	}

	private static void populateAllSalesTableRefresh(TableView<User> tableView) {

		tableView.getItems().clear();
		tableView.getItems().addAll(UserHibernation.findAllUsersRefreshed());

	}

	public static void populateUserTable(TableView<User> tableView) {
		if (Refresh.getRefreshingDeterminant() == 1) {
			populateAllSalesTableRefresh(tableView);
		} else {
			populateUsersTableWithoutRefreshing(tableView);
		}
	}

}
