package com.delains.ui.invoker;

import javafx.scene.layout.StackPane;

public class AccordionStack extends StackPane {

	private AccordionTabs accordionTabs;

	public AccordionStack() {

		accordionTabs = new AccordionTabs();

		this.getChildren().add(accordionTabs);

	}

	public AccordionTabs getAccordionTabs() {
		return accordionTabs;
	}

	public void setAccordionTabs(AccordionTabs accordionTabs) {
		this.accordionTabs = accordionTabs;
	}

}
