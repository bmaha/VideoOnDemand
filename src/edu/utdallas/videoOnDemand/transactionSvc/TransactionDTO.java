/**
 * 
 */
package edu.utdallas.videoOnDemand.transactionSvc;

import java.util.Date;
import edu.utdallas.videoOnDemand.entities.Transaction;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionDTO;
 */
public class TransactionDTO {
	
	private Long transID;
	private Long userID;
	private Long movieID;
	private String transType;
	private double amount;
	private String date;
	
	/*
	 * Constructor without any parameters
	 * */
	public TransactionDTO(){
		
	}
	
	/*
	 * Constructor with Transaction entity
	 * */
	public TransactionDTO(Transaction trans){
		this.transID = trans.getTransID();
		this.userID = trans.getUserID();
		this.movieID = trans.getMovieID();
		this.transType = trans.getTransType();
		this.amount = trans.getAmount();
		this.date = trans.getDate().toString();
	}

	public void setTransactionID(Long id) {
		this.transID = id;
	}
	public Long getTransactionID() {
		return transID;
	}
	
	public void setUserID(Long id) {
		this.userID = id;
	}
	public Long getUserID() {
		return userID;
	}
	
	public void setMovieID(Long id) {
		this.movieID = id;
	}
	public Long getMovieID() {
		return movieID;
	}
	
	public void setType(String type) {
		this.transType = type;
	}
	public String getType() {
		return transType;
	}
	
	public void setAmount(double amt) {
		this.amount = amt;
	}
	public double getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
	
	
}
