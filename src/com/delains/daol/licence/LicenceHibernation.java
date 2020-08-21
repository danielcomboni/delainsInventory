package com.delains.daol.licence;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.delains.model.licence.Licence;

public class LicenceHibernation {

	private LicenceHibernation() {

	}

	public static void newLicence( Licence licence ) {
		LicenceDAO.newLicence( licence );
	}

	public static Map < BigDecimal, Licence > getMapOfLicencesToThierIDs() {
		return LicenceDAO.getLicencesMappedToThierIDs();
	}

	public static List < Licence > getAllLicences() {
		return LicenceDAO.getAllLicences();
	}

	public static BigDecimal periodDeterminant( String dateTo ) {
		return LicenceDAO.periodDeterminant( dateTo );
	}

	public static Licence getLastLicence() {
		return LicenceDAO.getLastLicence();
	}

}
