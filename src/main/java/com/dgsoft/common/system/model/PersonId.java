package com.dgsoft.common.system.model;
// Generated Aug 19, 2014 10:57:39 AM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * PersonId generated by hbm2java
 */
@Embeddable
public class PersonId implements java.io.Serializable {

    public enum CredentialsType{
        MASTER_ID,OTHER;
    }

	private CredentialsType credentialsType;
	private String credentialsNumber;

	public PersonId() {
	}

	public PersonId(CredentialsType credentialsType, String credentialsNumber) {
		this.credentialsType = credentialsType;
		this.credentialsNumber = credentialsNumber;
	}

    @Enumerated(EnumType.STRING)
	@Column(name = "CREDENTIALS_TYPE", nullable = false, length = 32)
	@NotNull
	public CredentialsType getCredentialsType() {
		return this.credentialsType;
	}

	public void setCredentialsType(CredentialsType credentialsType) {
		this.credentialsType = credentialsType;
	}

	@Column(name = "CREDENTIALS_NUMBER", nullable = false, length = 100)
	@NotNull
	@Size(max = 100)
	public String getCredentialsNumber() {
		return this.credentialsNumber;
	}

	public void setCredentialsNumber(String credentialsNumber) {
		this.credentialsNumber = credentialsNumber;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof PersonId))
			return false;
		PersonId castOther = (PersonId) other;

		return ((this.getCredentialsType() == castOther.getCredentialsType()) || (this
				.getCredentialsType() != null
				&& castOther.getCredentialsType() != null && this
				.getCredentialsType().equals(castOther.getCredentialsType())))
				&& ((this.getCredentialsNumber() == castOther
						.getCredentialsNumber()) || (this
						.getCredentialsNumber() != null
						&& castOther.getCredentialsNumber() != null && this
						.getCredentialsNumber().equals(
								castOther.getCredentialsNumber())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getCredentialsType() == null ? 0 : this.getCredentialsType()
						.hashCode());
		result = 37
				* result
				+ (getCredentialsNumber() == null ? 0 : this
						.getCredentialsNumber().hashCode());
		return result;
	}

}
