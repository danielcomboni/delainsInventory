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
import com.delains.model.items.Item;
import com.delains.model.purchases.Purchase;
import com.delains.model.purchases.PurchaseReturn;
import com.delains.model.suppliers.Supplier;

import javafx.collections.ObservableList;

public class PurchaseReturnDAORetieve {

	public static List < PurchaseReturn > findAllPurchaseReturns() {

		List < PurchaseReturn > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "purchases_return", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
			Map < BigDecimal, Supplier > mapSupplier = SupplierHibernation.mapOfSuppliersToThierId();
			Map < BigDecimal, Purchase > mapPurchase = PurchasesHibernation.mapOfPurchasesToThierId();

			while ( resultSet.next() ) {

				// map.put("date", "VARCHAR(255)");
				// map.put("item_id", "INTEGER");
				// map.put("quantity", "DECIMAL(50,5)");
				// map.put("supplier_id", "INTEGER");
				// map.put("reason", "LONGTEXT");
				// map.put("purchase_id", "INTEGER");

				BigDecimal id = resultSet.getBigDecimal( "id" );
				String date = resultSet.getString( "date" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );

				Item item = mapItem.get( itemId );

				BigDecimal supplierId = resultSet.getBigDecimal( "supplier_id" );

				Supplier supplier = mapSupplier.get( supplierId );

				BigDecimal quantity = resultSet.getBigDecimal( "quantity" );
				BigDecimal purchaseId = resultSet.getBigDecimal( "purchase_id" );

				String reason = resultSet.getString( "reason" );

				PurchaseReturn purch = new PurchaseReturn();
				purch.setId( id );
				purch.setDate( date );
				purch.setItemId( item );
				purch.setPurchaseId( mapPurchase.get( purchaseId ) );
				purch.setQuantity( quantity );
				purch.setReason( reason );
				purch.setSupplierId( supplier );

				purchases.add( purch );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return purchases;
	}

	private static int i = -1;
	private static int rowIndex = 2;

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		PurchaseReturnDAORetieve.workbook = workbook;
	}

	@SuppressWarnings( "deprecation" )
	public static List < PurchaseReturn > exportList( String sheetName, ObservableList < String > columns,
			String whereQuery ) {

		List < PurchaseReturn > purchases = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "purchases_return", whereQuery ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
			Map < BigDecimal, Supplier > mapSupplier = SupplierHibernation.mapOfSuppliersToThierId();
			Map < BigDecimal, Purchase > mapPurchase = PurchasesHibernation.mapOfPurchasesToThierId();

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
				// sheet.autoSizeColumn( i );
				sheet.autoSizeColumn( i, true );
				// sheet.setColumnWidth( i, 256 * 25 );
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

				BigDecimal quantity = resultSet.getBigDecimal( "quantity" );
				BigDecimal purchaseId = resultSet.getBigDecimal( "purchase_id" );

				String reason = resultSet.getString( "reason" );

				PurchaseReturn purch = new PurchaseReturn();
				purch.setId( id );
				purch.setDate( date );
				purch.setItemId( item );
				purch.setPurchaseId( mapPurchase.get( purchaseId ) );
				purch.setQuantity( quantity );
				purch.setReason( reason );
				purch.setSupplierId( supplier );

				XSSFRow row = sheet.createRow( rowIndex );

				row.createCell( 0 ).setCellValue( purch.getDate() );
				row.createCell( 1 ).setCellValue( purch.getItemId().getItemName() );
				row.createCell( 2 ).setCellValue( Double.parseDouble( purch.getQuantity().toString() ) );

				if ( purch.getSupplierId() != null ) {
					row.createCell( 3 ).setCellValue( purch.getSupplierId().getSupplierName() );
				} else {
					row.createCell( 3 ).setCellValue( "" );
				}

				row.createCell( 4 ).setCellValue( purch.getReason() );

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
