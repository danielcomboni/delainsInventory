package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.delains.model.licence.RandomNumber;

public class RandomNumberHibernation {

	private RandomNumberHibernation() {

	}

	public static void newRandomNumbers() {
		RandomNumberDAO.newRandomNumber();
	}

	public static Map < BigDecimal, RandomNumber > getMapOfRandomNumberToItsID() {
		return RandomNumberDAO.getRandomNumberMappedToId();
	}

	public static List < RandomNumber > getAllRandomNumberList() {
		return RandomNumberDAO.getAllRandomNumbers();
	}

	public static RandomNumber getARandomNumberBasingOnPeriodChosen( BigDecimal period ) {
		return RandomNumberDAO.getARandomNumberBasingOnPeriodChosen( period );
	}

	public static void deleteUsedRow( BigDecimal id ) {
		RandomNumberDAO.deleteRowUsed( id );
	}

}
