package com.delains.dao.pos;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.dao.customers.CustomerHibernation;
import com.delains.dao.item.ItemHibernation;
import com.delains.dao.pricing.PricingHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.customers.Customer;
import com.delains.model.items.Item;
import com.delains.model.pos.POS;
import com.delains.model.pricing.Pricing;

import javafx.collections.ObservableList;

public class ReportAllSalesCreditByCustomerBetweenDatesRetrieve {

	private static int i = -1;
	private static int rowIndex = 2;

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		ReportAllSalesCreditByCustomerBetweenDatesRetrieve.workbook = workbook;
	}

	@SuppressWarnings( "deprecation" )
	public static Map < BigDecimal, POS > exportList( String sheetName, ObservableList < String > columns,
			String dateFrom, String dateTo, BigDecimal customerID ) {

		Map < BigDecimal, POS > poss = new LinkedHashMap <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			// select * from pos WHERE DATE(date) BETWEEN '2019-01-11' AND '2019-01-20';

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "pos",
					"WHERE DATE(date) between ? AND ? AND customer_id=? AND credit=1" ) );
			preparedStatement.setString( 1, dateFrom );
			preparedStatement.setString( 2, dateTo );
			preparedStatement.setBigDecimal( 3, customerID );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > mapItem = ItemHibernation.mapOfItemsToThierId();
			Map < BigDecimal, Customer > mapCustomer = CustomerHibernation.mapOfCustomersToThierId();
			Map < BigDecimal, Pricing > mapPricingToItem = PricingHibernation.mappingItemIDsAsIDOfPrice();

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
				// BigDecimal pricingId = resultSet.getBigDecimal("pricing_id");
				BigDecimal price = resultSet.getBigDecimal( "price" );
				BigDecimal cost = resultSet.getBigDecimal( "cost" );
				BigDecimal totalCost = resultSet.getBigDecimal( "total_cost" );
				boolean credit = resultSet.getBoolean( "credit" );
				BigDecimal discount = resultSet.getBigDecimal( "discount_allowed" );
				BigDecimal customerId = resultSet.getBigDecimal( "customer_id" );
				BigDecimal change = resultSet.getBigDecimal( "change" );
				BigDecimal balance = resultSet.getBigDecimal( "balance" );
				BigDecimal quantity = resultSet.getBigDecimal( "quantity" );
				BigDecimal amountPaid = resultSet.getBigDecimal( "amount_paid" );

				Timestamp timestamp = Timestamp.valueOf( date );
				timestamp.getDate();

LocalDate localDate = timestamp.toLocalDateTime().toLocalDate();
				LocalTime hour = timestamp.toLocalDateTime().toLocalTime();
				int h = hour.getHour();
				int m = hour.getMinute();
				int s = hour.getSecond();
				String time = h + ":" + m + ":" + s;

				String dateTime = localDate + " " + time;

				POS pos = new POS();
				pos.setAmountPaid( amountPaid );
				pos.setBalanceToBePaidByCustomer( balance );
				pos.setChange( change );
				pos.setCost( cost );
				pos.setCredit( credit );
				pos.setCustomerId( mapCustomer.get( customerId ) );
				pos.setDate( date );
				pos.setDiscountAllowed( discount );
				pos.setId( id );
				pos.setItemId( mapItem.get( itemId ) );
				pos.setPrice( price );
				pos.setPricing( mapPricingToItem.get( pos.getItemId().getId() ) );
				pos.setQuantity( quantity );
				pos.setTotalCost( totalCost );
				pos.setItemName( pos.getItemId().getItemName() );

				// POS pos2 = new POS();

				XSSFRow row = sheet.createRow( rowIndex );

				if ( pos.getQuantity().doubleValue() == 0 ) {

					row.createCell( 0 ).setCellValue( "" );

					row.createCell( 1 ).setCellValue( "" );
					row.createCell( 2 ).setCellValue( "" );
					row.createCell( 3 ).setCellValue( "" );
					row.createCell( 4 ).setCellValue( "" );

					if ( pos.isCredit() == true ) {
						row.createCell( 5 ).setCellValue( "yes" );
					} else {
						row.createCell( 5 ).setCellValue( "no" );
					}

					row.createCell( 6 ).setCellValue( Double.parseDouble( pos.getAmountPaid().toString() ) );
					row.createCell( 7 ).setCellValue( Double.parseDouble( pos.getTotalCost().toString() ) );
					row.createCell( 8 ).setCellValue( Double.parseDouble( pos.getChange().toString() ) );
					row.createCell( 9 )
							.setCellValue( Double.parseDouble( pos.getBalanceToBePaidByCustomer().toString() ) );

				} else {

					row.createCell( 0 ).setCellValue( pos.getDate() );
					row.createCell( 1 )
							.setCellValue( pos.getItemId().getItemName().concat( " " ).concat( pos.getItemId()
									.getPackageVolume().toString().concat( pos.getItemId().getUnitOfMeasurement() ) ) );
					row.createCell( 2 ).setCellValue( Double.parseDouble( pos.getQuantity().toString() ) );
					row.createCell( 3 ).setCellValue( Double.parseDouble( pos.getPrice().toString() ) );
					row.createCell( 4 ).setCellValue( Double.parseDouble( pos.getCost().toString() ) );

					if ( pos.isCredit() == true ) {
						row.createCell( 5 ).setCellValue( "yes" );
					} else {
						row.createCell( 5 ).setCellValue( "no" );
					}

					row.createCell( 6 ).setCellValue( "" );
					row.createCell( 7 ).setCellValue( "" );
					row.createCell( 8 ).setCellValue( "" );
					row.createCell( 9 ).setCellValue( "" );

				}

				rowIndex++;

				poss.put( id, pos );

			}

			setWorkbook( wb );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return poss;
	}

}
