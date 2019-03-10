package com.delains.ui.invoker;

import com.delains.ui.customers.CustomersTabInAccordion;
import com.delains.ui.deposits.DepositTabInAccordion;
import com.delains.ui.expenses.ExpensesTabsInAccordion;
import com.delains.ui.history.AuditHistoryTabInAccordion;
import com.delains.ui.item.ItemsTabInAccordion;
import com.delains.ui.payments.MediumOfPaymentTabsInAccordion;
import com.delains.ui.purchases.PurchasesTabInAccordion;
import com.delains.ui.sales.SalesTabInAccordion;
import com.delains.ui.stock.StockTabInAccordion;
import com.delains.ui.suppliers.SupplierTypeTabInAccordion;
import com.delains.ui.suppliers.SuppliersTabInAccordion;
import com.delains.ui.users.UserTabInAccordion;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

public class AccordionTabs extends Accordion {

	private TitledPane titledPaneUsers;
	private UserTabInAccordion userTabInAccordion;

	private TitledPane titledPaneSuppliers;
	private SuppliersTabInAccordion suppliersTabInAccordion;

	private TitledPane titledPanePurchases;
	private PurchasesTabInAccordion purchasesTabInAccordion;

	private TitledPane titledPaneCustomers;
	private CustomersTabInAccordion customersTabInAccordion;

	private TitledPane titledPaneSales;
	private SalesTabInAccordion salesTabInAccordion;

	private TitledPane titledPaneItem;
	private ItemsTabInAccordion itemTabInAccordion;

	private TitledPane titledPaneSupplierType;
	private SupplierTypeTabInAccordion supplierTypeTabInAccordion;

	private TitledPane titledPaneStock;
	private StockTabInAccordion stockTabInAccordion;

	private TitledPane titledPaneAuditHistory;
	private AuditHistoryTabInAccordion auditHistoryTabInAccordion;

	private TitledPane titledPaneMediumOfPayment;
	private MediumOfPaymentTabsInAccordion mediumOfPaymentTabsInAccordion;

	private TitledPane titledPaneExpenses;
	private ExpensesTabsInAccordion expensesTabsInAccordion;

	private TitledPane titledPaneDeposit;
	private DepositTabInAccordion depositTabInAccordion;

	private static ObservableList < TitledPane > titledPanes;

	public static ObservableList < TitledPane > getTitledPanes() {
		return titledPanes;
	}

	public static void setTitledPanes( ObservableList < TitledPane > titledPanes ) {
		AccordionTabs.titledPanes = titledPanes;
	}

	public AccordionTabs() {

		titledPanes = FXCollections.observableArrayList();

		userTabInAccordion = new UserTabInAccordion();
		titledPaneUsers = new TitledPane( "Users", userTabInAccordion );
		this.getPanes().add( titledPaneUsers );

		if ( getTitledPanes().isEmpty() ) {

			ObservableList < TitledPane > list = FXCollections.observableArrayList();
			list.add( titledPaneUsers );
			setTitledPanes( list );

		} else {
			getTitledPanes().add( titledPaneUsers );
		}

		itemTabInAccordion = new ItemsTabInAccordion();
		titledPaneItem = new TitledPane( "Items", itemTabInAccordion );
		this.getPanes().add( titledPaneItem );
		getTitledPanes().add( titledPaneItem );

		supplierTypeTabInAccordion = new SupplierTypeTabInAccordion();
		titledPaneSupplierType = new TitledPane( "Supplier type", supplierTypeTabInAccordion );
		this.getPanes().add( titledPaneSupplierType );

		getTitledPanes().add( titledPaneSupplierType );

		suppliersTabInAccordion = new SuppliersTabInAccordion();
		titledPaneSuppliers = new TitledPane( "Suppliers", suppliersTabInAccordion );
		this.getPanes().add( titledPaneSuppliers );
		getTitledPanes().add( titledPaneSuppliers );

		purchasesTabInAccordion = new PurchasesTabInAccordion();
		titledPanePurchases = new TitledPane( "Purchases", purchasesTabInAccordion );
		this.getPanes().add( titledPanePurchases );
		getTitledPanes().add( titledPanePurchases );

		customersTabInAccordion = new CustomersTabInAccordion();
		titledPaneCustomers = new TitledPane( "Customers", customersTabInAccordion );
		this.getPanes().add( titledPaneCustomers );
		getTitledPanes().add( titledPaneCustomers );

		salesTabInAccordion = new SalesTabInAccordion();
		titledPaneSales = new TitledPane( "Sales", salesTabInAccordion );
		this.getPanes().add( titledPaneSales );
		getTitledPanes().add( titledPaneSales );

		expensesTabsInAccordion = new ExpensesTabsInAccordion();
		titledPaneExpenses = new TitledPane( "expenses", expensesTabsInAccordion );
		this.getPanes().add( titledPaneExpenses );
		getTitledPanes().add( titledPaneExpenses );

		mediumOfPaymentTabsInAccordion = new MediumOfPaymentTabsInAccordion();
		titledPaneMediumOfPayment = new TitledPane( "medium of\npayment", mediumOfPaymentTabsInAccordion );
		this.getPanes().add( titledPaneMediumOfPayment );
		getTitledPanes().add( titledPaneMediumOfPayment );

		depositTabInAccordion = new DepositTabInAccordion();
		titledPaneDeposit = new TitledPane( "deposit", depositTabInAccordion );
		this.getPanes().add( titledPaneDeposit );
		getTitledPanes().add( titledPaneDeposit );

		stockTabInAccordion = new StockTabInAccordion();
		titledPaneStock = new TitledPane( "Stock", stockTabInAccordion );
		this.getPanes().add( titledPaneStock );
		getTitledPanes().add( titledPaneStock );

		auditHistoryTabInAccordion = new AuditHistoryTabInAccordion();
		titledPaneAuditHistory = new TitledPane( "History", auditHistoryTabInAccordion );
		this.getPanes().add( titledPaneAuditHistory );
		getTitledPanes().add( titledPaneAuditHistory );

	}

	public TitledPane getTitledPaneDeposit() {
		return titledPaneDeposit;
	}

	public void setTitledPaneDeposit( TitledPane titledPaneDeposit ) {
		this.titledPaneDeposit = titledPaneDeposit;
	}

	public TitledPane getTitledPaneUsers() {
		return titledPaneUsers;
	}

	public void setTitledPaneUsers( TitledPane titledPaneUsers ) {
		this.titledPaneUsers = titledPaneUsers;
	}

	public UserTabInAccordion getUserTabInAccordion() {
		return userTabInAccordion;
	}

	public void setUserTabInAccordion( UserTabInAccordion userTabInAccordion ) {
		this.userTabInAccordion = userTabInAccordion;
	}

	public TitledPane getTitledPaneSuppliers() {
		return titledPaneSuppliers;
	}

	public void setTitledPaneSuppliers( TitledPane titledPaneSuppliers ) {
		this.titledPaneSuppliers = titledPaneSuppliers;
	}

	public SuppliersTabInAccordion getSuppliersTabInAccordion() {
		return suppliersTabInAccordion;
	}

	public void setSuppliersTabInAccordion( SuppliersTabInAccordion suppliersTabInAccordion ) {
		this.suppliersTabInAccordion = suppliersTabInAccordion;
	}

	public TitledPane getTitledPanePurchases() {
		return titledPanePurchases;
	}

	public void setTitledPanePurchases( TitledPane titledPanePurchases ) {
		this.titledPanePurchases = titledPanePurchases;
	}

	public PurchasesTabInAccordion getPurchasesTabInAccordion() {
		return purchasesTabInAccordion;
	}

	public void setPurchasesTabInAccordion( PurchasesTabInAccordion purchasesTabInAccordion ) {
		this.purchasesTabInAccordion = purchasesTabInAccordion;
	}

	public TitledPane getTitledPaneCustomers() {
		return titledPaneCustomers;
	}

	public void setTitledPaneCustomers( TitledPane titledPaneCustomers ) {
		this.titledPaneCustomers = titledPaneCustomers;
	}

	public CustomersTabInAccordion getCustomersTabInAccordion() {
		return customersTabInAccordion;
	}

	public void setCustomersTabInAccordion( CustomersTabInAccordion customersTabInAccordion ) {
		this.customersTabInAccordion = customersTabInAccordion;
	}

	public TitledPane getTitledPaneSales() {
		return titledPaneSales;
	}

	public void setTitledPaneSales( TitledPane titledPaneSales ) {
		this.titledPaneSales = titledPaneSales;
	}

	public SalesTabInAccordion getSalesTabInAccordion() {
		return salesTabInAccordion;
	}

	public void setSalesTabInAccordion( SalesTabInAccordion salesTabInAccordion ) {
		this.salesTabInAccordion = salesTabInAccordion;
	}

	public TitledPane getTitledPaneItem() {
		return titledPaneItem;
	}

	public void setTitledPaneItem( TitledPane titledPaneItem ) {
		this.titledPaneItem = titledPaneItem;
	}

	public ItemsTabInAccordion getItemTabInAccordion() {
		return itemTabInAccordion;
	}

	public void setItemTabInAccordion( ItemsTabInAccordion itemTabInAccordion ) {
		this.itemTabInAccordion = itemTabInAccordion;
	}

	public TitledPane getTitledPaneSupplierType() {
		return titledPaneSupplierType;
	}

	public void setTitledPaneSupplierType( TitledPane titledPaneSupplierType ) {
		this.titledPaneSupplierType = titledPaneSupplierType;
	}

	public SupplierTypeTabInAccordion getSupplierTypeTabInAccordion() {
		return supplierTypeTabInAccordion;
	}

	public void setSupplierTypeTabInAccordion( SupplierTypeTabInAccordion supplierTypeTabInAccordion ) {
		this.supplierTypeTabInAccordion = supplierTypeTabInAccordion;
	}

	public TitledPane getTitledPaneStock() {
		return titledPaneStock;
	}

	public void setTitledPaneStock( TitledPane titledPaneStock ) {
		this.titledPaneStock = titledPaneStock;
	}

	public StockTabInAccordion getStockTabInAccordion() {
		return stockTabInAccordion;
	}

	public void setStockTabInAccordion( StockTabInAccordion stockTabInAccordion ) {
		this.stockTabInAccordion = stockTabInAccordion;
	}

	public TitledPane getTitledPaneMediumOfPayment() {
		return titledPaneMediumOfPayment;
	}

	public void setTitledPaneMediumOfPayment( TitledPane titledPaneMediumOfPayment ) {
		this.titledPaneMediumOfPayment = titledPaneMediumOfPayment;
	}

	public TitledPane getTitledPaneExpenses() {
		return titledPaneExpenses;
	}

	public void setTitledPaneExpenses( TitledPane titledPaneExpenses ) {
		this.titledPaneExpenses = titledPaneExpenses;
	}

	public TitledPane getTitledPaneAuditHistory() {
		return titledPaneAuditHistory;
	}

	public void setTitledPaneAuditHistory( TitledPane titledPaneAuditHistory ) {
		this.titledPaneAuditHistory = titledPaneAuditHistory;
	}

	public AuditHistoryTabInAccordion getAuditHistoryTabInAccordion() {
		return auditHistoryTabInAccordion;
	}

	public void setAuditHistoryTabInAccordion( AuditHistoryTabInAccordion auditHistoryTabInAccordion ) {
		this.auditHistoryTabInAccordion = auditHistoryTabInAccordion;
	}

}
