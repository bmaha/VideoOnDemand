/**
 * 
 */
package edu.utdallas.videoOnDemand.dao;

import java.util.List;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Transaction;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionDAO;
 */

public interface TransactionDAO {

	/*
	 * insert rent/purchase into Transaction table
	 * */
	void addTransaction(Transaction tran) throws DAOException;
	/*
	 * retrieve all transaction history of a user
	 * */
	List<Transaction> retrieveAllHistory(Long long1) throws DAOException;
	/*
	 * retrieve rent or purchase transactions of a user
	 * */
	List<Transaction> retrieveByType(Long userID, String type) throws DAOException;
	
	int getMovieDaysLeft(Long movieId, Long userId) throws DAOException;
	
}
