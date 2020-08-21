package com.delains.dao.stock;

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
import com.delains.dao.utils.DBUtils;
import com.delains.model.items.Item;
import com.delains.model.stock.Stock;

import javafx.collections.ObservableList;

public class StockDAORetrieve {

	public static List < Stock > findAllStocks() {

		List < Stock > stocks = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "stock", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > map = ItemHibernation.mapOfItemsToThierId();

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );
				BigDecimal itemQuantity = resultSet.getBigDecimal( "item_quantity" );
				String date = resultSet.getString( "date" );

				Item item = map.get( itemId );

				Stock stock = new Stock( id, item, itemQuantity, date );

				stocks.add( stock );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}

		return stocks;
	}

	private static int i = -1;
	private static int rowIndex = 2;

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		StockDAORetrieve.workbook = workbook;
	}

	@SuppressWarnings( "deprecation" )
	public static List < Stock > exportList( String sheetName, ObservableList < String > columns, String whereQuery ) {

		List < Stock > stocks = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		try {

			preparedStatement = connection.prepareStatement( DBUtils.getRetrievingCommandString( "stock", "" ) );
			resultSet = preparedStatement.executeQuery();

			Map < BigDecimal, Item > map = ItemHibernation.mapOfItemsToThierId();

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

			sheet.setColumnWidth( 0, 256 * 25 );
			sheet.setColumnWidth( 1, 256 * 15 );

			while ( resultSet.next() ) {

				BigDecimal id = resultSet.getBigDecimal( "id" );
				BigDecimal itemId = resultSet.getBigDecimal( "item_id" );
				BigDecimal itemQuantity = resultSet.getBigDecimal( "item_quantity" );
				String date = resultSet.getString( "date" );

				Item item = map.get( itemId );

				Stock stock = new Stock( id, item, itemQuantity, date );

				XSSFRow row = sheet.createRow( rowIndex );

				row.createCell( 0 ).setCellValue( stock.getItemId().getItemName() );
				row.createCell( 1 ).setCellValue( Double.parseDouble( stock.getItemQuantity().toString() ) );

				rowIndex++;

				stocks.add( stock );

			}

			setWorkbook( wb );

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}

		return stocks;
	}

}
