package com.delains.dao.history;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import com.delains.model.history.AuditHistory;
import com.delains.model.users.User;

import javafx.collections.ObservableList;

public class AuditHistoryHibernation {
	private static void newAuditHistory( AuditHistory salesReturn ) {
		AuditHistoryDAO.newAuditHistory( salesReturn );
	}

	public static AuditHistory auditValues( String action, User user ) {
		AuditHistory auditHistory = new AuditHistory();
		auditHistory.setAction( action );
		auditHistory.setUserid( user );
		newAuditHistory( auditHistory );
		return auditHistory;
	}

	private static ObservableList < AuditHistory > salesReturns;

	public static ObservableList < AuditHistory > getAuditHistorys() {
		return salesReturns;
	}

	public static void setAuditHistorys( ObservableList < AuditHistory > salesReturns ) {
		AuditHistoryHibernation.salesReturns = salesReturns;
	}

	public static ObservableList < AuditHistory > findAllAuditHistorysWithoutRefreshing() {

		if ( getAuditHistorys() == null ) {
			setAuditHistorys( AuditHistoryDAO.changeListUtilToListObservable() );
		}

		return getAuditHistorys();

	}

	public static ObservableList < AuditHistory > findAllAuditHistorysWithRefreshing() {

		setAuditHistorys( AuditHistoryDAO.changeListUtilToListObservable() );

		return getAuditHistorys();

	}

	public static Map < BigDecimal, AuditHistory > mapOfAuditHistoryToThierIDs() {

		Map < BigDecimal, AuditHistory > map = new LinkedHashMap <>();

		for ( AuditHistory sr : findAllAuditHistorysWithRefreshing() ) {
			map.put( sr.getId(), sr );
		}

		return map;

	}

}
