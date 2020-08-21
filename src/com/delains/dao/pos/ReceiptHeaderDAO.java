package com.delains.dao.pos;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.pos.ReceiptHeader;

public class ReceiptHeaderDAO {

	private static LinkedHashMap < String, String > tableDefinitions() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "business_name", "VARCHAR(255)" );
		map.put( "location", "VARCHAR(255)" );
		map.put( "contact", "VARCHAR(255)" );

		return map;
	}

	private static void createTable() {
		DBUtils.apiToCreateTable( tableDefinitions(), "receipt_header" );
	}

	public static void newReceiptHeader( ReceiptHeader header ) {
		createTable();
		if ( getReceiptHeader().getBusinessName() != null ) {
			updateReceiptHeader( header, BigDecimal.ONE );
		} else {
			ReceiptHeaderDAOInsertion.newReceiptHeader( header );
		}

	}

	private static void updateReceiptHeader( ReceiptHeader header, BigDecimal id ) {
		ReceiptHeaderDAOUpdate.updateReceiptHeader( header, id );
	}

	public static ReceiptHeader getReceiptHeader() {
		createTable();
		return ReceiptHeaderDAORetrieve.getReceiptHeaders();
	}

}
