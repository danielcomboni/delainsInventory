package com.delains.dao.workbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.delains.utilities.Directory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class WorkBookUtils < T > {

	private static XSSFWorkbook workbook;

	public static XSSFWorkbook getWorkbook() {
		return workbook;
	}

	public static void setWorkbook( XSSFWorkbook workbook ) {
		WorkBookUtils.workbook = workbook;
	}

	public ObservableList < T > exportToExcel() {

		ObservableList < T > list = FXCollections.observableArrayList();

		return list;

	}

	public static void writeWorkBookOut( String sheetName, String path ) {
		FileOutputStream fileOut = null;

		String PATH = Directory.createFolderOnDesktop( path );

		try {
			fileOut = new FileOutputStream( PATH + "/" + sheetName + ".xlsx" );
			getWorkbook().write( fileOut );
//			fileOut.close();
			// getWorkbook().close();
		} catch ( Exception e ) {
			e.printStackTrace();
		}

	}

	private static String sheetName;

	public static String getSheetName() {
		return sheetName;
	}

	public static void setSheetName( String sheetName ) {
		WorkBookUtils.sheetName = sheetName;
	}

	/*
	 * returns list from table to be export to excel
	 * 
	 * @param
	 * 
	 */

	/*
	 * @param sheetName type String
	 * 
	 * @param tableViewToGetColumnNamesFrom type TableView to get column names
	 * 
	 * @param valuesFromQuery type ObservableList
	 * 
	 * @param attributesChosen type ObservableList to return real values for the
	 * sheet
	 * 
	 */

	XSSFRow row = null;

	int j = 0;
	int i = -1;
	int k = 0;
	int rowIndex = 2;

	private ObservableList < T > tSObtained = FXCollections.observableArrayList();

	@SuppressWarnings( "deprecation" )
	public void createNewWorkBook( String sheetName, TableView < T > tableViewToGetColumnNamesFrom,
			ObservableList < T > valuesFromQuery, ObservableList < String > attributesChosen ) {
		XSSFWorkbook wb = new XSSFWorkbook();

		XSSFSheet sheet = wb.createSheet( sheetName );

		// creating the header of the sheet
		XSSFRow header = sheet.createRow( 1 );

		CellStyle cs = wb.createCellStyle();
		org.apache.poi.ss.usermodel.Font font = wb.createFont();
		font.setBoldweight( org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL );

		cs.setFont( font );

		tableViewToGetColumnNamesFrom.getColumns().parallelStream().forEachOrdered( e -> {

			i++;
			header.createCell( i ).setCellValue( e.getText() );
			header.setRowStyle( cs );
			sheet.autoSizeColumn( i );

		} );

		valuesFromQuery.parallelStream().forEachOrdered( e -> {
			T t = valuesFromQuery.get( j );

			System.out.println( "j: " + j );

			System.out.println( "t: " + t );

			tSObtained.add( t );

			if ( valuesFromQuery.get( j ) != null ) {

				for ( int f = 0; f < attributesChosen.size(); f++ ) {
					row = sheet.createRow( rowIndex );
					String valueStr = getterNameString( t, attributesChosen.get( f ) );
					row.createCell( f ).setCellValue( valueStr );
				}

			}

			rowIndex++;

			j++;

		} );

		try {

			FileOutputStream fileOut = new FileOutputStream( sheetName + ".xlsx" );
			wb.write( fileOut );
			fileOut.close();
			wb.close();

		} catch ( IOException e1 ) {
			e1.printStackTrace();
		}

		// return wb;
	}

	public String getterNameString( T classObject, String getterNameString ) {
		String getterValue = null;
		System.out.println( "method name: " + getterNameString );
		try {

			Method method = classObject.getClass().getMethod( getterNameString );
			System.out.println( "type name; " + method.getReturnType().getTypeName() );

			if ( method.getReturnType().getTypeName().equals( "boolean" ) ) {

				boolean is = ( boolean ) method.invoke( classObject );

				if ( is == true ) {
					getterValue = "yes";
				} else {
					getterValue = "no";
				}

			}

			else if ( method.getReturnType().getTypeName().equals( "java.lang.String" ) ) {

				getterValue = ( String ) method.invoke( classObject );

			}

			System.out.println( "valuuuuuuuuu: " + getterValue );

		} catch ( NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e ) {
			e.printStackTrace();
		}
		return getterValue;
	}

	private XSSFSheet sheetReturned( XSSFWorkbook wb, String sheetName ) {
		XSSFSheet sheet = wb.createSheet( sheetName );
		xssfSheet = sheet;
		return sheet;
	}

	private XSSFSheet xssfSheet;

	@SuppressWarnings( "deprecation" )
	public void createNewWorkBookForExportation( String sheetName, ObservableList < String > columns,
			XSSFSheet xssfSheet ) {
		XSSFWorkbook wb = new XSSFWorkbook();

		final XSSFSheet sheet = sheetReturned( wb, sheetName );

		// creating the header of the sheet
		XSSFRow header = this.xssfSheet.createRow( 1 );

		CellStyle cs = wb.createCellStyle();
		org.apache.poi.ss.usermodel.Font font = wb.createFont();
		font.setBoldweight( org.apache.poi.ss.usermodel.Font.BOLDWEIGHT_NORMAL );

		cs.setFont( font );

		// these columns will be obtained from the table columns

		// sheet = xssfSheet;s

		columns.parallelStream().forEachOrdered( e -> {

			i++;
			header.createCell( i ).setCellValue( e );
			header.setRowStyle( cs );
			sheet.autoSizeColumn( i );

		} );

		// these rows come from a predefined result set already worked out
		// rows.parallelStream().forEach( r -> {
		//
		// row = r;
		//
		// row = sheet.createRow();
		//
		// } );

		// listForExportation.parallelStream().forEachOrdered( e -> {
		// T t = listForExportation.get( j );
		//
		// System.out.println( "j: " + j );
		//
		// System.out.println( "t: " + t );
		//
		// tSObtained.add( t );
		//
		// if ( valuesFromQuery.get( j ) != null ) {
		//
		// for ( int f = 0; f < attributesChosen.size(); f++ ) {
		// row = sheet.createRow( rowIndex );
		// String valueStr = getterNameString( t, attributesChosen.get( f ) );
		// row.createCell( f ).setCellValue( valueStr );
		// }
		//
		// }
		//
		// rowIndex++;
		//
		// j++;
		//
		// } );

		try {

			FileOutputStream fileOut = new FileOutputStream( sheetName + ".xlsx" );
			wb.write( fileOut );
			fileOut.close();
			wb.close();

		} catch ( IOException e1 ) {
			e1.printStackTrace();
		}

		// return wb;
	}

}
