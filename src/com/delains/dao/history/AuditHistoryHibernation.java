package com.delains.dao.history;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

import com.delains.model.history.AuditHistory;
import com.delains.model.users.User;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class AuditHistoryHibernation {
	private static AuditHistory newAuditHistory( AuditHistory salesReturn ) {
		return AuditHistoryDAO.newAuditHistory( salesReturn );
	}

	public static AuditHistory auditValues( String action, User user ) {

						AuditHistory auditHistory = new AuditHistory();
						auditHistory.setAction( action );
						auditHistory.setUserId( user );

						return newAuditHistory( auditHistory );

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

	private void performAnyActionAsync(Function<Object,Object> function, Object object){
		Service<Void> service = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						function.apply(object);
						return null;
					}
				};
			}
		};
		service.start();
	}

}
