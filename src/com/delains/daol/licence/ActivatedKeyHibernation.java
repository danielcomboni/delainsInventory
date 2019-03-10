package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.delains.model.licence.ActivatedKey;

public class ActivatedKeyHibernation {

	private ActivatedKeyHibernation() {

	}

	public static void newActivatedKey( ActivatedKey key ) {
		ActivatedKeyDAO.newActivatedKey( key );
	}

	public static ActivatedKey getCurrentActivatedKey() {
		return ActivatedKeyDAO.getTheCurrentActivatedKey();
	}

	public static List < ActivatedKey > getAllActivatedKeysList() {
		return ActivatedKeyDAO.getAllActivatedKeyList();
	}

	public static Map < BigDecimal, ActivatedKey > getMapOfActivatedKeysToThierIDs() {
		return ActivatedKeyDAO.getMapOfActivatedKeysToTheirIDs();
	}

}
