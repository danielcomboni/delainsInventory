package com.delains.ui.invoker;

import com.delains.dao.users.UserHibernation;
import com.delains.model.users.User;
import com.delains.ui.history.AuditHistoryFrame;
import com.delains.ui.item.ItemFrame;
import com.delains.ui.menu.MenuBarAll;
import com.delains.ui.payments.MediumOfPaymentFrame;
import com.delains.ui.pricing.PricingFrame;
import com.delains.ui.purchases.CustomerFrame;
import com.delains.ui.purchases.*;
import com.delains.ui.sales.AllSalesFrame;
import com.delains.ui.sales.POSFrame;
import com.delains.ui.stock.StockFrame;
import com.delains.ui.suppliers.SupplierFrame;
import com.delains.ui.suppliers.SupplierTypeFrame;
import com.delains.ui.users.UserFrame;

import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class InvokerUI extends BorderPane {

	private UserFrame userFrame;

	private SupplierTypeFrame supplierTypeFrame;

	private SupplierFrame supplierFrame;

	private CustomerFrame customerFrame;

	private ItemFrame itemFrame;

	private POSFrame posFrame;

	private PricingFrame pricingFrame;

	private StockFrame stockFrame;

	private AuditHistoryFrame auditHistoryFrame;


	public InvokerUI() {

		this.setId( "main_borderpane" );

		getStylesheets().add( InvokerUI.class.getResource( "invokerui.css" ).toExternalForm() );

		MenuBarAll menuBarAll = new MenuBarAll();
		this.setTop(menuBarAll);

		AccordionStack accordionStack = new AccordionStack();

		this.setLeft(accordionStack);

		userFrame = new UserFrame();

		accordionStack.getAccordionTabs().getTitledPaneUsers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						centerUserFrame();
					}

				} );

		supplierTypeFrame = new SupplierTypeFrame();

		accordionStack.getAccordionTabs().getTitledPaneSupplierType().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						centerSupplierTypeFrame();
					}
				} );

		supplierFrame = new SupplierFrame();
		accordionStack.getAccordionTabs().getTitledPaneSuppliers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createSupplierFrame();
					}

				} );

//		purchaseFrame = new PurchaseFrame();
		accordionStack.getAccordionTabs().getTitledPanePurchases().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createPurchasesFrame();
					}

				} );

		customerFrame = new CustomerFrame();
		accordionStack.getAccordionTabs().getTitledPaneCustomers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createCustomerFrame();
					}

				} );

		posFrame = new POSFrame();

		accordionStack.getAccordionTabs().getSalesTabInAccordion().getButtonPOS().setOnAction(e -> createPOSFrame());

		accordionStack.getAccordionTabs().getSalesTabInAccordion().getButtonAllSalesRecords().setOnAction(e -> createAllSales());

		itemFrame = new ItemFrame();
		accordionStack.getAccordionTabs().getTitledPaneItem().expandedProperty()
				.addListener( ( obs, wasExpaned, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createItemFrame();
					}

				} );

		pricingFrame = new PricingFrame();

		accordionStack.getAccordionTabs().getItemTabInAccordion().getButtonPricing().setOnAction(e -> createPricingFrame());

		stockFrame = new StockFrame();
		accordionStack.getAccordionTabs().getTitledPaneStock().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						createStockFrame();
					}
				} );

		auditHistoryFrame = new AuditHistoryFrame();
		accordionStack.getAccordionTabs().getTitledPaneAuditHistory().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						createAuditHistoryFrame();
						
					}
				} );

		accordionStack.getAccordionTabs().getTitledPaneMediumOfPayment().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						this.setCenter( new MediumOfPaymentFrame() );
					}
				} );

		ObservableList < User > list = UserHibernation.findAllUsersRefreshed();

		PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( list );

		PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(),
				PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

		PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(),
				PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

	}


	private void centerUserFrame() {

		this.setCenter( this.userFrame );

	}

	private void centerSupplierTypeFrame() {

		this.setCenter( this.supplierTypeFrame );

	}

	private void createSupplierFrame() {

		this.setCenter( this.supplierFrame );


	}

	private void createPurchasesFrame() {

		this.setCenter( new PurchaseFrame() );

	}

	private void createCustomerFrame() {

		this.setCenter( this.customerFrame );

	}

	private void createPOSFrame() {
		this.setCenter( this.posFrame );
	}

	private void createAllSales() {

		this.setCenter( new AllSalesFrame() );

}

	private void createItemFrame() {

		this.setCenter( this.itemFrame );
	}

	private void createPricingFrame() {
		this.setCenter( this.pricingFrame );
	}

	private void createStockFrame() {
		this.setCenter( this.stockFrame );
		stockFrame.populateTablesWithRefreshing();
	}

	private void createAuditHistoryFrame() {
		this.setCenter( this.auditHistoryFrame );

	}

}
