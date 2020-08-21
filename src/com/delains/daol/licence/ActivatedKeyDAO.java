package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.ActivatedKey;

public class ActivatedKeyDAO {

	private ActivatedKeyDAO() {

	}

	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "licence_id", "INTEGER" );
		map.put( "key", "LONGTEXT" );

		return map;

	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "activated_key" );
	}

	public static void newActivatedKey( ActivatedKey key ) {
		createTable();
		ActivatedKeyDAOInsert.newActivatedKey( key );
	}

	public static ActivatedKey getTheCurrentActivatedKey() {
		createTable();
		return ActivatedKeyDAORetrieve.getCurrentActivatedKey();
	}

	public static List < ActivatedKey > getAllActivatedKeyList() {
		createTable();
		return ActivatedKeyDAORetrieve.getAllActivatedKeyList();
	}

	public static Map < BigDecimal, ActivatedKey > getMapOfActivatedKeysToTheirIDs() {
		Map < BigDecimal, ActivatedKey > map = new LinkedHashMap <>();
		for ( ActivatedKey key : getAllActivatedKeyList() ) {
			map.put( key.getId(), key );
		}
		return map;
	}

}
