package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.Licence;

public class LicenceDAO {

	private LicenceDAO() {

	}

	// select * from pos order by id desc LIMIT 1;

	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "date_from", "DATE DEFAULT (datetime('now','localtime'))" );
		map.put( "date_to", "DATE" );
		map.put( "period", "INTEGER" );
		map.put( "rand_num_id", "INTEGER" );

		return map;

	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "licence" );
	}

	public static void newLicence( Licence licence ) {
		createTable();
		LicenceDAOInsert.newLicence( licence );
	}

	public static List < Licence > getAllLicences() {
		createTable();
		return LicenceDAORetrieve.getAllLicences();
	}

	public static Map < BigDecimal, Licence > getLicencesMappedToThierIDs() {
		Map < BigDecimal, Licence > map = new LinkedHashMap <>();
		for ( Licence l : getAllLicences() ) {
			map.put( l.getId(), l );
		}
		return map;
	}

	// public static BigDecimal periodDeterminant( String dateTo ) {
	// createTable();
	// return LicenceDAOInsert.periodDeterminant( dateTo );
	// }

	public static Licence getLastLicence() {
		return LicenceDAORetrieve.getLastLicence();
	}

	public static BigDecimal periodDeterminant( String dateTo ) {
		return LicenceDAORetrieve.periodDeterminant( dateTo );
	}

}
