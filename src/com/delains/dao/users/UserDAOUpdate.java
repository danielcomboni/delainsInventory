package com.delains.dao.users;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import com.delains.dao.utils.DBUtils;
import com.delains.model.users.User;

public class UserDAOUpdate {

	private static LinkedHashMap < String, String > mapsForUpdatingUser() {

		LinkedHashMap < String, String > map = new LinkedHashMap <>();
		map.put( "user_name", "user_name" );
		map.put( "user_email", "user_email" );
		map.put( "user_phone", "user_phone" );
		map.put( "user_password", "user_password" );
		map.put( "is_admin", "is_admin" );
		return map;

	}

	public static User updateUser( User user, BigDecimal idOfUser ) {

		PreparedStatement preparedStatementUpdateUser = null;
		Connection connectionUpdateUser = DBUtils.connect();

		try {

			preparedStatementUpdateUser = connectionUpdateUser
					.prepareStatement( DBUtils.getUpdateCommandString( "users", mapsForUpdatingUser(), "WHERE id=?" ) );

			user.setId( idOfUser );

			String hashPassword = BCrypting.hashPassword( user.getUserPassword() );

			preparedStatementUpdateUser.setString( 1, user.getUserName() );
			preparedStatementUpdateUser.setString( 2, user.getUserEmail().replaceAll( " ", "" ) );
			preparedStatementUpdateUser.setString( 3, user.getUserPhone().replaceAll( " ", "" ) );
			preparedStatementUpdateUser.setString( 4, hashPassword );
			preparedStatementUpdateUser.setBoolean( 5, user.isAdmin() );
			preparedStatementUpdateUser.setBigDecimal( 6, user.getId() );

			System.out.println("id: " + user.getId());

			preparedStatementUpdateUser.executeUpdate();

			BigDecimal id;
			ResultSet rs = preparedStatementUpdateUser.getGeneratedKeys();

			if ( rs != null && rs.next() ) {
				id = rs.getBigDecimal( 1 );
				user.setId(id);
			}

		} catch ( SQLException e ) {
			e.printStackTrace();
		} finally {
			DBUtils.closeConnections( connectionUpdateUser, preparedStatementUpdateUser, null );
		}

		return user;
	}

	public static void main( String [ ] args ) {
		// updateUser(new User(BigDecimal.ONE, "test1", "0781", "123456789", true),
		// BigDecimal.ONE);
	}

}
