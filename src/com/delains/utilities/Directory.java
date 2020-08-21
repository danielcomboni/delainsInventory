package com.delains.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directory {
	public static String createFolderOnDesktop( String folerPath ) {
		String deskTopPath = System.getProperty( "user.home" ).concat( "/Desktop" ).concat( "/" ).concat( folerPath );
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
