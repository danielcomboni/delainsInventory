package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.RandomNumber;

public class RandomNumberDAO {

	private static LinkedHashMap < String, String > tableDefinition() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();

		map.put( "id", "INTEGER PRIMARY KEY AUTOINCREMENT" );
		map.put( "ran_num", "INTEGER" );

		return map;
	}

	public static void createTable() {
		DBUtils.apiToCreateTable( tableDefinition(), "random_number" );
	}

	public static void newRandomNumber() {

		createTable();

		if ( getAllRandomNumbers().isEmpty() || getAllRandomNumbers().size() < 900 ) {
			RandomNumberDAOInsert.newRandNum();
		}

	}

	public static List < RandomNumber > getAllRandomNumbers() {
		return RandomNumberDAORetrieve.getAllRandomNumberList();
	}

	public static Map < BigDecimal, RandomNumber > getRandomNumberMappedToId() {
		createTable();
		Map < BigDecimal, RandomNumber > map = new LinkedHashMap <>();
		for ( RandomNumber rn : getAllRandomNumbers() ) {
			map.put( rn.getId(), rn );
		}
		return map;
	}

	public static RandomNumber getARandomNumberBasingOnPeriodChosen( BigDecimal period ) {
		return RandomNumberDAORetrieve.getARandomNumberBasingOnPeriodChosen( period );
	}

	public static void deleteRowUsed(BigDecimal id) {
		RandomNumberDAORetrieve.deleteTheUsedRow( id );
	}
	
}
