
package edu.utdallas.videoOnDemand.entities;
import java.util.Date;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job User;
 */

public class Transaction {

	private Long transID;
	private Long userID;
	private Long movieID;
	private String transType;
	private double amount;
	private Date date;
	
	public Long getTransID() {
		return transID;
	}
	public void setTransID(Long transID) {
		this.transID = transID;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Long getMovieID() {
		return movieID;
	}
	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
		
}
