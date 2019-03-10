package com.delains.dao.pos;

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

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.delains.model.sales.SalesReturn;

import javafx.collections.ObservableList;

public class SalesReturnsInwardsByCustomerDAORetrieve {

	private static int i = -1;
	private static int rowIndex = 2;

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		SalesReturnsInwardsByCustomerDAORetrieve.workbook = workbook;
	}

	@SuppressWarnings( "deprecation" )
	public static List < SalesReturn > exportList( String sheetName, ObservableList < String > columns,
			BigDecimal customerID ) {

		List < SalesReturn > salesReturns = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
		Map < BigDecimal, Customer > mapCustomer = CustomerHibernation.mapOfCustomersToThierId();
		Map < BigDecimal, POS > mapPOS = POSHibernation.getMapPOSToID();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "sales_return", "WHERE customer_id=?" ) );
			preparedStatement.setBigDecimal( 1, customerID );
			resultSet = preparedStatement.executeQuery();

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

				String date = resultSet.getString( "date" );
				BigDecimal id = resultSet.getBigDecimal( "id" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );
				BigDecimal customerId = resultSet.getBigDecimal( "customer_id" );
				BigDecimal posId = resultSet.getBigDecimal( "pos_id" );
				BigDecimal qtyReturned = resultSet.getBigDecimal( "quantity_returned" );
				BigDecimal qtyReStocked = resultSet.getBigDecimal( "quantity_restocked" );
				BigDecimal qtyDiscarded = resultSet.getBigDecimal( "quantity_discarded" );
				String reason = resultSet.getString( "reason" );

				Item item = mapItem.get( itemId );
				Customer customer = mapCustomer.get( customerId );
				POS pos = mapPOS.get( posId );

				SalesReturn salesReturn = new SalesReturn();
				salesReturn.setDate( date );
				salesReturn.setId( id );
				salesReturn.setItemId( item );
				salesReturn.setCustomerId( customer );
				salesReturn.setPosId( pos );
				salesReturn.setQuantityReturned( qtyReturned );
				salesReturn.setQuantityReStocked( qtyReStocked );
				salesReturn.setQuantityDiscarded( qtyDiscarded );
				salesReturn.setReason( reason );

				XSSFRow row = sheet.createRow( rowIndex );
				row.createCell( 0 ).setCellValue( salesReturn.getDate() );
				row.createCell( 1 ).setCellValue( salesReturn.getItemId().getItemName() );
				row.createCell( 2 ).setCellValue( Double.parseDouble( salesReturn.getQuantityReturned().toString() ) );
				row.createCell( 3 ).setCellValue( Double.parseDouble( salesReturn.getQuantityReStocked().toString() ) );
				row.createCell( 4 ).setCellValue( Double.parseDouble( salesReturn.getQuantityDiscarded().toString() ) );

				if ( salesReturn.getCustomerId() == null ) {
					row.createCell( 5 ).setCellValue( "" );
				} else {
					row.createCell( 5 ).setCellValue( salesReturn.getCustomerId().getCustomerName() );
				}

				row.createCell( 6 ).setCellValue( salesReturn.getReason() );

				rowIndex++;

				salesReturns.add( salesReturn );

			}

			setWorkbook( wb );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return salesReturns;
	}

}
