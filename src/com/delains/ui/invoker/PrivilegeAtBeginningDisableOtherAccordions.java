package com.delains.ui.invoker;

import com.delains.model.users.User;

import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class PrivilegeAtBeginningDisableOtherAccordions {

	private PrivilegeAtBeginningDisableOtherAccordions() {

	}

	private static boolean userCreated;

	public static boolean isUserCreated() {
		return userCreated;
	}

	public static void setUserCreated( boolean userCreated ) {
		PrivilegeAtBeginningDisableOtherAccordions.userCreated = userCreated;
	}

	public static boolean checkIfAUserIsCreated( ObservableList < User > list ) {
		boolean userCreated = false;
		if ( !list.isEmpty() ) {
			userCreated = true;
		} else {
			userCreated = false;
		}

		setUserCreated( userCreated );

		return userCreated;
	}

	public static ObservableList < TitledPane > allAcordions( ObservableList < TitledPane > titledPanes,
			boolean userCreated ) {

		if ( userCreated == false ) {
			for ( int i = 0; i < titledPanes.size(); i++ ) {
				if ( i > 0 ) {
					titledPanes.get( i ).setDisable( true );
				} else {
					titledPanes.get( i ).setDisable( false );
				}
			}
		}
		return titledPanes;
	}

	public static ObservableList < TitledPane > enableAllAccordions( ObservableList < TitledPane > list,
			boolean usersExist ) {

		if ( usersExist == true ) {
			for ( int i = 0; i < list.size(); i++ ) {
				list.get( i ).setDisable( false );
			}
		}

		return list;

	}

	public static ObservableList < Accordion > getAllAccordions() {
		return null;
	}

}
