package com.delains.ui.sales;

import java.math.BigDecimal;

import com.delains.dao.pos.ReceiptHeaderDAO;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.pos.POS;
import com.delains.model.pos.ReceiptHeader;
import com.delains.ui.sales.receiptprinting.PrinterService;
import com.delains.utilities.CurrentTimestamp;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class POSPrintReceipt extends VBox {




	private String smallFont = "-fx-font-weight:bold; -fx-font-size:xx-small;";

	public POSPrintReceipt() {
		header();
		buildGrid();
		gratitude();
	}

	private GridPane gridPane;

	private Label labelTotalCost;
	private Label labelTotalCostText;

	private Label labelAmountPaid;
	private Label labelAmountPaidText;

	private Label labelChange;
	private Label labelChangeText;

	private Label labelBalance;
	private Label labelBalanceText;

	private GridPane gridPaneTotal;

	public Label getLabelTotalCostText() {
		return labelTotalCostText;
	}

	public void setLabelTotalCostText( Label labelTotalCostText ) {
		this.labelTotalCostText = labelTotalCostText;
	}

	public Label getLabelAmountPaidText() {
		return labelAmountPaidText;
	}

	public void setLabelAmountPaidText( Label labelAmountPaidText ) {
		this.labelAmountPaidText = labelAmountPaidText;
	}

	public Label getLabelChangeText() {
		return labelChangeText;
	}

	public void setLabelChangeText( Label labelChangeText ) {
		this.labelChangeText = labelChangeText;
	}

	public Label getLabelBalanceText() {
		return labelBalanceText;
	}

	public void setLabelBalanceText( Label labelBalanceText ) {
		this.labelBalanceText = labelBalanceText;
	}

	private void buildGrid() {
		gridPane = new GridPane();
		// gridPane.setVgap( 1 );
		// gridPane.setHgap( 3 );
		// gridPane.setPadding( new Insets( 1 ) );

		gridPaneTotal = new GridPane();
		// gridPaneTotal.setVgap( 1 );
		// gridPaneTotal.setHgap( 1 );
		// gridPaneTotal.setPadding( new Insets( 1 ) );
		// gridPaneTotal.setAlignment( Pos.BOTTOM_RIGHT );

		labelTotalCost = new Label( "total cost:" );
		labelTotalCost.setStyle(smallFont);
		gridPaneTotal.add( labelTotalCost, 0, 0 );
		labelTotalCostText = new Label( "to..." );
		labelTotalCostText.setStyle(smallFont);
		gridPaneTotal.add( labelTotalCostText, 1, 0 );

		labelAmountPaid = new Label( "amount paid:" );
		labelAmountPaid.setStyle(smallFont);
		gridPaneTotal.add( labelAmountPaid, 0, 1 );
		labelAmountPaidText = new Label();
		labelAmountPaidText.setStyle(smallFont);
		gridPaneTotal.add( labelAmountPaidText, 1, 1 );

		labelChange = new Label( "change:" );
		labelChange.setStyle(smallFont);
		gridPaneTotal.add( labelChange, 0, 2 );
		labelChangeText = new Label();
		labelChangeText.setStyle(smallFont);
		gridPaneTotal.add( labelChangeText, 1, 2 );

		labelBalance = new Label( "balance:" );
		gridPaneTotal.add( labelBalance, 0, 3 );
		labelBalanceText = new Label();
		gridPaneTotal.add( labelBalanceText, 1, 3 );

		this.getChildren().add( gridPane );

		Separator separator = new Separator();
		// separator.setPadding( new Insets( 1 ) );
		this.getChildren().add( separator );

		this.getChildren().add( gridPaneTotal );

	}

	private void addToGrid( Node node, BigDecimal col, BigDecimal row ) {
		gridPane.add( node, col.intValue(), row.intValue() );
	}

	public void buildMain( ObservableList < POS > posList, BigDecimal totalCost, BigDecimal amountPaid,
			BigDecimal change, BigDecimal balance ) {

		BigDecimal colItemDetail = BigDecimal.ZERO;
		BigDecimal rowItemDetail = BigDecimal.ZERO;

		BigDecimal colItemCost = BigDecimal.ZERO;
		BigDecimal rowItemCost = BigDecimal.ZERO;

		String bodyOne = headerForTextPrinting.concat("\n...................................................\n");
		String costOfEachForBody = "";

		String ugx = "Shs ";

		for ( POS pos : posList ) {

			String pack = pos.getItemId().getPackageName();

			String details = NumberFormatting.formatToEnglish( pos.getQuantity().toString() ).concat( " " )
					.concat( pos.getItemId().getItemName() ).concat( " " )
					.concat( pos.getItemId().getPackageVolume().toString() )
					.concat( pos.getItemId().getUnitOfMeasurement() ).concat( " @ " ).concat( ugx )
					.concat( NumberFormatting.formatToEnglish( pos.getPricing().getPrice().toString() ) );

			if (posList.indexOf(pos) == posList.size() - 1){

				costOfEachForBody = costOfEachForBody.concat(details);

			}else {

				costOfEachForBody = costOfEachForBody.concat(details).concat("\n");

			}

			/* working for the cost of the item */
			String cost = NumberFormatting.formatToEnglish( pos.getCost().toString() ).concat( " " );
			Label labelItemCost = getLabelForItemCost( cost );
			Label labelItemDetails = getLabelForMain( details );

			BigDecimal index = new BigDecimal( posList.indexOf( pos ) );

			if ( posList.indexOf( pos ) == 0 ) {

				colItemDetail = BigDecimal.ZERO;
				colItemCost = colItemCost.add( BigDecimal.ONE );

				rowItemDetail = index;
				rowItemCost = index;

}

			if ( posList.indexOf( pos ) > 0 ) {

				rowItemCost = rowItemCost.add( index );
				rowItemDetail = rowItemDetail.add( index );

				colItemDetail = BigDecimal.ZERO;
				colItemCost = BigDecimal.ONE;

			}

			addToGrid( labelItemDetails, colItemDetail, rowItemDetail );

			String costLabelText = labelItemCost.getText();
			String itemCostPlusUGX = ugx + costLabelText;
			labelItemCost.setText( itemCostPlusUGX );
			addToGrid( labelItemCost, colItemCost, rowItemCost );

		}


		bodyOne = bodyOne.concat(costOfEachForBody);

		bodyOne = bodyOne.concat("\n..............................................\n");

		// gridPane.setVgap( 1 );
		// gridPane.setHgap( 3 );
		// gridPane.setPadding( new Insets( 1 ) );

		// set the total cost for everything
		getLabelTotalCostText().setText( ugx + NumberFormatting.formatToEnglish( totalCost.toString() ) );
		bodyOne = bodyOne.concat("total: "+ ugx + NumberFormatting.formatToEnglish( totalCost.toString() )).concat("\n");

		// set the amount paid in by the customer
		getLabelAmountPaidText().setText( ugx + NumberFormatting.formatToEnglish( amountPaid.toString() ) );
		bodyOne = bodyOne.concat("amount paid: "+ugx + NumberFormatting.formatToEnglish( amountPaid.toString() )).concat("\n");

		// set the change to given back to the customer
		getLabelChangeText().setText( ugx + NumberFormatting.formatToEnglish( change.toString() ) );
		bodyOne = bodyOne.concat("change: "+ ugx + NumberFormatting.formatToEnglish( change.toString() )).concat("\n");

		// set the balance to be paid by the customer
		getLabelBalanceText().setText( ugx + NumberFormatting.formatToEnglish( balance.toString() ) );
		bodyOne = bodyOne.concat("balance: "+ugx + NumberFormatting.formatToEnglish( balance.toString() )).concat("\n");

		bodyOne = bodyOne.concat("..............................................\n");

		String bold = "\u001B[1m ";
		String boldernAllAfter = "\033[0;1m";
		String gratitude =  "Thank you. Please come again".concat("\n\n\n\n\n\n\n\n\n") ;


		bodyOne = bodyOne.concat(gratitude);

		PrinterService.setBody(bodyOne);

	}

	private Label getLabelForMain( String itemDetails ) {
		Label label = new Label();
		label.setText( itemDetails );
		label.setWrapText( true );
		label.setTextAlignment( TextAlignment.JUSTIFY );
		return label;
	}

	private Label getLabelForItemCost( String itemCost ) {
		Label label = new Label();
		label.setText( itemCost );
		label.setWrapText( true );
		label.setTextAlignment( TextAlignment.JUSTIFY );
		return label;
	}

	private Label labelSupermarketName;
	private Label labelAddress;
	private Label labelPhone;

	private ReceiptHeader receiptHeader;

	public ReceiptHeader getReceiptHeader() {
		return receiptHeader;
	}

	public void setReceiptHeader( ReceiptHeader receiptHeader ) {
		this.receiptHeader = receiptHeader;
	}

	private ReceiptHeader getHeader() {

		if ( getReceiptHeader()== null ) {
			setReceiptHeader( ReceiptHeaderDAO.getReceiptHeader() );
			return getReceiptHeader();
		} else {
			return getReceiptHeader();
		}

	}

	private String headerForTextPrinting;

	private void header() {

		GridPane gridPane = new GridPane();
		gridPane.setVgap( 1 );
		gridPane.setHgap( 1 );
		gridPane.setPadding( new Insets( 2 ) );
		gridPane.setAlignment( Pos.TOP_LEFT );

		ReceiptHeader receipt = null;

		if ( getReceiptHeader() != null ) {
			receipt = getReceiptHeader();
		} else {
			receipt = getHeader();
		}

headerForTextPrinting = new String();

		headerForTextPrinting = headerForTextPrinting.concat(receipt.getBusinessName()).concat("\n");
		headerForTextPrinting = headerForTextPrinting.concat(receipt.getLocation()).concat("\n");
		headerForTextPrinting = headerForTextPrinting.concat(receipt.getContact()).concat("\n");
		headerForTextPrinting = headerForTextPrinting.concat(CurrentTimestamp.getDateTimeEndAtSecond());

		String styleHeader = "-fx-font-weight:bold; -fx-font-size:xx-small;";
		this.labelSupermarketName = new Label( receipt.getBusinessName() );
		labelSupermarketName.setStyle( styleHeader );
		this.labelAddress = new Label( receipt.getLocation() );
		labelAddress.setStyle( styleHeader );
		this.labelPhone = new Label( "tel: " + receipt.getContact() );
		labelPhone.setStyle( styleHeader );

		gridPane.add( labelSupermarketName, 0, 0 );
		gridPane.add( labelAddress, 0, 1 );
		gridPane.add( labelPhone, 0, 2 );

		Label labelTime = new Label( CurrentTimestamp.getDateTimeEndAtSecond() );
		labelTime.setStyle( styleHeader );
		gridPane.add( labelTime, 0, 3 );

		this.getChildren().add( gridPane );

		Separator separator = new Separator();
		separator.setPadding( new Insets( 1 ) );
		this.getChildren().add( separator );

	}

	private void gratitude() {
		Label label = new Label( "Thank you.!!!",
				new FontAwesomeIconView( FontAwesomeIcon.THUMBS_UP ) );
		label.setAlignment( Pos.BOTTOM_LEFT );
		label.setStyle( "-fx-font-weight:bold; -fx-font-size:xx-small;" );
		VBox box = new VBox( 5 );
		Separator separator = new Separator();
		separator.setPadding( new Insets( 1 ) );
		box.getChildren().add( separator );
		box.getChildren().add( label );

		box.setAlignment( Pos.BOTTOM_CENTER );
		this.getChildren().add( box );
	}

}
