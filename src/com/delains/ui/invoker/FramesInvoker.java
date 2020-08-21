package com.delains.ui.invoker;

import com.delains.ui.users.UserFrame;

import javafx.scene.layout.BorderPane;

public class FramesInvoker extends BorderPane {

	private UserFrame userFrame;

	public FramesInvoker() {
		// TODO Auto-generated constructor stub
	}

	public FramesInvoker(BorderPane borderPaneToBeCentered) {
		buildComponents();
		this.setCentering(borderPaneToBeCentered);
	}

	private void buildComponents() {
		userFrame = new UserFrame();
	}

	private void setCentering(BorderPane node) {
		this.setCenter(node);
	}

	public UserFrame getUserFrame() {
		return userFrame;
	}

	public void setUserFrame(UserFrame userFrame) {
		this.userFrame = userFrame;
	}

}
