package com.delains.daol.licence;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.delains.dao.utils.DBUtils;
import com.delains.model.licence.ActivatedKey;
import com.delains.model.licence.Licence;

public class ActivatedKeyDAORetrieve {

	private ActivatedKeyDAORetrieve() {

	}

	public static ActivatedKey getCurrentActivatedKey() {

		ActivatedKey list = new ActivatedKey();
		PreparedStatement pst = null;
		Connection connection = DBUtils.connect();
		ResultSet rs = null;

		Map < BigDecimal, Licence > map = LicenceHibernation.getMapOfLicencesToThierIDs();

		try {
			pst = connection.prepareStatement( "select * from activated_key order by id desc limit 1" );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				ActivatedKey key = new ActivatedKey();
				key.setId( rs.getBigDecimal( "id" ) );
				key.setKey( rs.getString( "key" ) );
				key.setLicenceId( map.get( rs.getBigDecimal( "licence_id" ) ) );
				list = key;
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}

		return list;

	}

	public static List < ActivatedKey > getAllActivatedKeyList() {

		List < ActivatedKey > list = new ArrayList <>();
		PreparedStatement pst = null;
		Connection connection = DBUtils.connect();
		ResultSet rs = null;

		Map < BigDecimal, Licence > map = LicenceHibernation.getMapOfLicencesToThierIDs();

		try {
			pst = connection.prepareStatement( "select * from activated_key" );
			rs = pst.executeQuery();
			while ( rs.next() ) {
				ActivatedKey key = new ActivatedKey();
				key.setId( rs.getBigDecimal( "id" ) );
				key.setKey( rs.getString( "key" ) );
				key.setLicenceId( map.get( rs.getBigDecimal( "" ) ) );
				list.add( key );
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connection, pst, rs );
		}

		return list;

	}

}
