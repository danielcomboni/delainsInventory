package com.delains.tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directory {

	public static void main( String [ ] args ) {
		System.out.println( System.getProperty( "user.home" ).concat( "/Desktop" ) );
	}

	public static String createFolderOnDesktop( String folerPath ) {
		String deskTopPath = System.getProperty( "user.home" ).concat( "/Desktop" )
				.concat( "/Delains Supermarket Reports" ).concat( folerPath );
		Path path = Paths.get( deskTopPath );
		try {
			if ( !Files.exists( path ) ) {
				Files.createDirectories( path );
			}
		} catch ( IOException e ) {
			e.printStackTrace();
		}
		return deskTopPath;
	}

}
