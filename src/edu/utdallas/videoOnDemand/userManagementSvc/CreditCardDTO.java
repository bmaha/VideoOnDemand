/**
 * 
 */
package edu.utdallas.videoOnDemand.userManagementSvc;

import java.io.Serializable;

import edu.utdallas.videoOnDemand.entities.CreditCard;

/**
 * @author Abhishek Poonia;
 * @date 7/20/2014;
 * @version 1;
 * @job CreditCardDTO;
 */
@SuppressWarnings("serial")
public class CreditCardDTO implements Serializable {

	private Long creditcardId;
	private String name;
	private String creditcardNumber;
	private String creditcardType;
	private String securityCode;
	private String expiryMonth;
	private String expiryYear;

	/**
	 * Constructor without any parameters
	 */
	public CreditCardDTO() {

	}

	/**
	 * Constructor with User entity
	 */
	public CreditCardDTO(CreditCard ccInfo) {
		this.setCreditcardId(ccInfo.getCreditcardId());
		this.setCreditcardNumber(ccInfo.getCreditcardNumber());
		this.setCreditcardType(ccInfo.getCreditcardType());
		this.setName(ccInfo.getName());
		this.setSecurityCode(ccInfo.getSecurityCode());
		this.setExpiryMonth(ccInfo.getExpiryMonth());
		this.setExpiryYear(ccInfo.getExpiryYear());
	}

	public Long getCreditcardId() {
		return creditcardId;
	}

	public void setCreditcardId(Long creditcardId) {
		this.creditcardId = creditcardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreditcardNumber() {
		return creditcardNumber;
	}

	public void setCreditcardNumber(String creditcardNumber) {
		this.creditcardNumber = creditcardNumber;
	}

	public String getCreditcardType() {
		return creditcardType;
	}

	public void setCreditcardType(String creditcardType) {
		this.creditcardType = creditcardType;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

}
