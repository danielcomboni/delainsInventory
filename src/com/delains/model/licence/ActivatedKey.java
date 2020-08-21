package com.delains.model.licence;

import java.math.BigDecimal;

public class ActivatedKey {

	private BigDecimal id;
	private Licence licenceId;
	private String key;

	public BigDecimal getId() {
		return id;
	}

	public void setId( BigDecimal id ) {
		this.id = id;
	}

	public Licence getLicenceId() {
		return licenceId;
	}

	public void setLicenceId( Licence licenceId ) {
		this.licenceId = licenceId;
	}

	public String getKey() {
		return key;
	}

	public void setKey( String key ) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "ActivatedKey [id=" + id + ", licenceId=" + licenceId + ", key=" + key + "]";
	}

}
