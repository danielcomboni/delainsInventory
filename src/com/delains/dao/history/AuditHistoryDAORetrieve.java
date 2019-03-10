package com.delains.dao.history;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.users.UserHibernation;
import com.delains.dao.utils.DBUtils;
import com.delains.model.history.AuditHistory;
import com.delains.model.users.User;

public class AuditHistoryDAORetrieve {
	public static List < AuditHistory > findAllAuditHistorys() {

		List < AuditHistory > salesReturns = new ArrayList <>();

		PreparedStatement preparedStatement = null;
		Connection connection = DBUtils.connect();
		ResultSet resultSet = null;

		Map < BigDecimal, User > mapUser = UserHibernation.mapOfUserAndThierId();

		try {

			preparedStatement = connection
					.prepareStatement( DBUtils.getRetrievingCommandString( "audit_history", "" ) );
			resultSet = preparedStatement.executeQuery();

			while ( resultSet.next() ) {

				String date = resultSet.getString( "date" );
				BigDecimal id = resultSet.getBigDecimal( "id" );
				BigDecimal userId = resultSet.getBigDecimal( "user_id" );
				String action = resultSet.getString( "action" );

				AuditHistory auditHistory = new AuditHistory();
				auditHistory.setId( id );
				auditHistory.setDate( date );
				auditHistory.setAction( action );
				auditHistory.setUserid( mapUser.get( userId ) );

				salesReturns.add( auditHistory );

			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, preparedStatement, resultSet );
		}
		return salesReturns;
	}

}
