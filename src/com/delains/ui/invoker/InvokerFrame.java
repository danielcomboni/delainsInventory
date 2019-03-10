package com.delains.ui.invoker;

import javafx.scene.control.Accordion;
import javafx.scene.layout.BorderPane;

public class InvokerFrame extends BorderPane {

	private Accordion accordion;

	public InvokerFrame() {
		accordion = new Accordion();
		this.setCenter(accordion);
	}

}
