package edu.utdallas.videoOnDemand.services;


import java.util.List;

import edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionService;
 */

public interface TransactionService
{
	/*
	 * insert Rental into Rented_Movies
	 * insert transaction into Transactions
	 * */
	void rentMovie(TransactionDTO transDTO) throws ServiceException;
	/*
	 * insert Rental into Rented_Movies
	 * insert transaction into Transactions
	 * */
	void purchaseMovie(TransactionDTO transDTO) throws ServiceException;
	
	/*
	 * retrieveAll transaction history for a user
	 * */
	List<MovieDTO> retrieveAllHistory(TransactionDTO transDTO) throws ServiceException;
	
	/*
	 * retrieve all rental history for a user
	 * */
	List<MovieDTO> retrieveRentalHistory() throws ServiceException;
	
	/*
	 * retrieve all purchase history for a user
	 * */
	List<MovieDTO> retrievePurchaseHistory() throws ServiceException;
	
	int getMovieDaysLeft(Long movieID) throws ServiceException;
	
	
	
}
