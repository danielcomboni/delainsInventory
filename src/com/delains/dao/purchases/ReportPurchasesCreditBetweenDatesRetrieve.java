package com.delains.dao.purchases;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.item.ItemHibernation;
import com.delains.dao.suppliers.SupplierHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.dao.utils.NumberFormatting;
import com.delains.model.items.Item;
import com.delains.model.purchases.Purchase;
import com.delains.model.suppliers.Supplier;

import javafx.collections.ObservableList;

public class ReportPurchasesCreditBetweenDatesRetrieve {
	private static int i = -1;
	private static int rowIndex = 2;

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		ReportPurchasesCreditBetweenDatesRetrieve.workbook = workbook;
	}

	@SuppressWarnings( "deprecation" )
	public static List < Purchase > exportList( String sheetName, ObservableList < String > columns, String dateFrom,
			String dateTo ) {

		List < Purchase > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "purchases",
					"WHERE DATE(date) between ? AND ? AND is_credit=1" ) );
			preparedStatement.setString( 1, dateFrom );
			preparedStatement.setString( 2, dateTo );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
			Map < BigDecimal, Supplier > mapSupplier = SupplierHibernation.mapOfSuppliersToThierId();

			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet( sheetName );

			XSSFRow header = sheet.createRow( 0 );

			CellStyle cs = wb.createCellStyle();
			org.apache.poi.ss.usermodel.Font font = wb.createFont();
			font.setBoldweight( org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_BOLD );

			cs.setFont( font );

			columns.parallelStream().forEachOrdered( e -> {

				i++;
				header.createCell( i ).setCellValue( e );
				header.setRowStyle( cs );
				sheet.autoSizeColumn( i, true );
			} );

			sheet.setColumnWidth( 0, 256 * 15 );
			sheet.setColumnWidth( 1, 256 * 25 );

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );

				Item item = mapItem.get( itemId );

				BigDecimal supplierId = resultSet.getBigDecimal( "supplier_id" );

				Supplier supplier = mapSupplier.get( supplierId );

				BigDecimal totalCost = resultSet.getBigDecimal( "total_cost" );
				BigDecimal amountPaid = resultSet.getBigDecimal( "amount_paid" );
				BigDecimal balance = resultSet.getBigDecimal( "balance" );
				BigDecimal discountReceived = resultSet.getBigDecimal( "discount_received" );
				BigDecimal price = resultSet.getBigDecimal( "price" );
				BigDecimal quantityPurchased = resultSet.getBigDecimal( "quantity" );
				boolean isCredit = resultSet.getBoolean( "is_credit" );

				Purchase purch = new Purchase();
				purch.setId( id );
				purch.setDate( date );
				purch.setItemId( item );

				purch.setQuantityPurchased( quantityPurchased );
				purch.setQuantityPurchasedStr(
						NumberFormatting.formatToEnglish( purch.getQuantityPurchased().toString() ) );

				purch.setPrice( price );
				purch.setPriceStr( NumberFormatting.formatToEnglish( purch.getPrice().toString() ) );

				purch.setSupplierId( supplier );

				purch.setAmountPaid( amountPaid );
				purch.setAmountPaidStr( NumberFormatting.formatToEnglish( purch.getAmountPaid().toString() ) );

				purch.setDiscountReceived( discountReceived );
				purch.setDiscountReceivedStr(
						NumberFormatting.formatToEnglish( purch.getDiscountReceived().toString() ) );

				purch.setTotalCost( totalCost );
				purch.setTotalCostStr( NumberFormatting.formatToEnglish( purch.getTotalCost().toString() ) );

				purch.setBalanceToBeCleared( balance );
				purch.setBalanceToBeClearedStr(
						NumberFormatting.formatToEnglish( purch.getBalanceToBeCleared().toString() ) );

				purch.setCredit( isCredit );

				XSSFRow row = sheet.createRow( rowIndex );

				row.createCell( 0 ).setCellValue( purch.getDate() );
				row.createCell( 1 ).setCellValue( purch.getItemId().getItemName() );
				row.createCell( 2 ).setCellValue( Double.parseDouble( purch.getQuantityPurchased().toString() ) );
				row.createCell( 3 ).setCellValue( Double.parseDouble( purch.getPrice().toString() ) );
				row.createCell( 4 ).setCellValue( Double.parseDouble( purch.getTotalCost().toString() ) );

				if ( purch.isCredit() == true ) {
					row.createCell( 5 ).setCellValue( "yes" );
				} else {
					row.createCell( 5 ).setCellValue( "no" );
				}

				if ( purch.getSupplierId() != null ) {
					row.createCell( 6 ).setCellValue( purch.getSupplierId().getSupplierName() );
				} else {
					row.createCell( 6 ).setCellValue( "" );
				}

				row.createCell( 7 ).setCellValue( Double.parseDouble( purch.getAmountPaid().toString() ) );
				row.createCell( 8 ).setCellValue( Double.parseDouble( purch.getBalanceToBeCleared().toString() ) );
				row.createCell( 9 ).setCellValue( Double.parseDouble( purch.getDiscountReceived().toString() ) );

				rowIndex++;

				purchases.add( purch );

			}

			setWorkbook( wb );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

}
