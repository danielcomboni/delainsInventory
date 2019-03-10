package com.delains.ui.invoker;

import com.delains.dao.users.UserHibernation;
import com.delains.model.users.User;
import com.delains.ui.deposits.DepositFrame;
import com.delains.ui.expenses.ExpensesFrame;
import com.delains.ui.history.AuditHistoryFrame;
import com.delains.ui.item.ItemFrame;
import com.delains.ui.menu.MenuBarAll;
import com.delains.ui.payments.MediumOfPaymentFrame;
import com.delains.ui.pricing.PricingFrame;
import com.delains.ui.purchases.CustomerFrame;
import com.delains.ui.purchases.PurchaseFrame;
import com.delains.ui.sales.AllSalesFrame;
import com.delains.ui.sales.POSFrame;
import com.delains.ui.stock.StockFrame;
import com.delains.ui.suppliers.SupplierFrame;
import com.delains.ui.suppliers.SupplierTypeFrame;
import com.delains.ui.users.UserFrame;
import com.delains.ui.users.UserManipulation;

import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;

public class InvokerUI extends BorderPane {

	private AccordionStack accordionStack;
	private MenuBarAll menuBarAll;

	private UserFrame userFrame;

	private SupplierTypeFrame supplierTypeFrame;

	private SupplierFrame supplierFrame;

	private PurchaseFrame purchaseFrame;

	private CustomerFrame customerFrame;

	private ItemFrame itemFrame;

	private POSFrame posFrame;

	private AllSalesFrame allSalesFrame;

	private PricingFrame pricingFrame;

	private StockFrame stockFrame;

	private AuditHistoryFrame auditHistoryFrame;

	private MediumOfPaymentFrame mediumOfPaymentFrame;

	private ExpensesFrame expensesFrame;

	public InvokerUI() {

		this.setId( "main_borderpane" );

		getStylesheets().add( InvokerUI.class.getResource( "invokerui.css" ).toExternalForm() );

		menuBarAll = new MenuBarAll();
		this.setTop( menuBarAll );

		accordionStack = new AccordionStack();

		this.setLeft( accordionStack );

		userFrame = new UserFrame();

		accordionStack.getAccordionTabs().getTitledPaneUsers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						System.out.println( "hghhh" );
						centerUserFrame();
					}

					if ( wasExpanded ) {
						System.out.println( "was" );
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
		this.accordionStack.getAccordionTabs().getTitledPaneSuppliers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createSupplierFrame();
					}

				} );

		purchaseFrame = new PurchaseFrame();
		this.accordionStack.getAccordionTabs().getTitledPanePurchases().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createPurchasesFrame();
					}

				} );

		customerFrame = new CustomerFrame();
		this.accordionStack.getAccordionTabs().getTitledPaneCustomers().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createCustomerFrame();
					}

				} );

		posFrame = new POSFrame();

		this.accordionStack.getAccordionTabs().getSalesTabInAccordion().getButtonPOS().setOnAction( e -> {
			createPOSFrame();
		} );

		allSalesFrame = new AllSalesFrame();
		this.accordionStack.getAccordionTabs().getSalesTabInAccordion().getButtonAllSalesRecords().setOnAction( e -> {
			createAllSales();
		} );

		itemFrame = new ItemFrame();
		this.accordionStack.getAccordionTabs().getTitledPaneItem().expandedProperty()
				.addListener( ( obs, wasExpaned, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						createItemFrame();
					}

				} );

		pricingFrame = new PricingFrame();

		this.accordionStack.getAccordionTabs().getItemTabInAccordion().getButtonPricing().setOnAction( e -> {
			createPricingFrame();
		} );

		stockFrame = new StockFrame();
		this.accordionStack.getAccordionTabs().getTitledPaneStock().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						createStockFrame();
					}
				} );

		auditHistoryFrame = new AuditHistoryFrame();
		this.accordionStack.getAccordionTabs().getTitledPaneAuditHistory().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						createAuditHistoryFrame();
					}
				} );

		mediumOfPaymentFrame = new MediumOfPaymentFrame();
		this.accordionStack.getAccordionTabs().getTitledPaneMediumOfPayment().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						this.setCenter( new MediumOfPaymentFrame() );
					}
				} );

		this.accordionStack.getAccordionTabs().getTitledPaneExpenses().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {
					if ( isNowExpanded ) {
						this.setCenter( new ExpensesFrame() );
					}
				} );

		this.accordionStack.getAccordionTabs().getTitledPaneDeposit().expandedProperty()
				.addListener( ( obs, wasExpanded, isNowExpanded ) -> {

					if ( isNowExpanded ) {
						this.setCenter( new DepositFrame() );
					}

				} );

		// privilegeAtBeginningDisableOtherAccordions = new
		// PrivilegeAtBeginningDisableOtherAccordions();

		ObservableList < User > list = UserHibernation.findAllUsersRefreshed();

		PrivilegeAtBeginningDisableOtherAccordions.checkIfAUserIsCreated( list );

		PrivilegeAtBeginningDisableOtherAccordions.allAcordions( AccordionTabs.getTitledPanes(),
				PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

		PrivilegeAtBeginningDisableOtherAccordions.enableAllAccordions( AccordionTabs.getTitledPanes(),
				PrivilegeAtBeginningDisableOtherAccordions.isUserCreated() );

	}

	// private PrivilegeAtBeginningDisableOtherAccordions
	// privilegeAtBeginningDisableOtherAccordions;

	private void centerUserFrame() {

		this.setCenter( this.userFrame );
		Refresh.setRefreshingDeterminant( 0 );
		UserManipulation.populateUserTable( UserFrame.getTableViewUsers() );
		// this.userFrame.populateUsersTableWithRefreshing();

	}

	private void centerSupplierTypeFrame() {

		this.setCenter( this.supplierTypeFrame );

	}

	private void createSupplierFrame() {

		this.setCenter( this.supplierFrame );

		// this.supplierFrame.populateSupplierWithRefreshing();

	}

	private void createPurchasesFrame() {

		this.setCenter( new PurchaseFrame() );

	}

	private void createCustomerFrame() {

		this.setCenter( this.customerFrame );

	}

	private void createPOSFrame() {
		this.setCenter( this.posFrame );
		this.posFrame.populateComboBoxCustomers();
		this.posFrame.populateComboBoxItems();
	}

	private void createAllSales() {

		this.setCenter( new AllSalesFrame() );

		// this.setCenter( this.allSalesFrame );
		// AllSalesFrame.getTableView().getItems().clear();
		// AllSalesFrame.getTableView().getItems().addAll(
		// POSHibernation.findAllPOSesObservableListRefreshed() );
		// System.out.println( "refreshed" );
	}

	private void createItemFrame() {

		this.setCenter( this.itemFrame );
		// ItemManipulation.populateTableWithRefreshing();

	}

	private void createPricingFrame() {
		this.setCenter( this.pricingFrame );
		pricingFrame.populateComboBox();
	}

	private void createStockFrame() {
		this.setCenter( this.stockFrame );
		stockFrame.populateTablesWithRefreshing();
	}

	private void createAuditHistoryFrame() {
		this.setCenter( this.auditHistoryFrame );
	}

}
