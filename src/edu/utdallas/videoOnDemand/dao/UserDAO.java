/**
 * 
 */
package edu.utdallas.videoOnDemand.dao;

import java.util.List;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.CreditCard;
import edu.utdallas.videoOnDemand.entities.User;

/**
 * @author Lei Cui;
 * @date 6/27/2014;
 * @version 1;
 * @job UserDAO;
 */

public interface UserDAO {

	/*
	 * insert user into table
	 */
	Long addUser(User user, CreditCard creditCard) throws DAOException;

	/*
	 * validate user
	 * 
	 * @parameter: userDTO
	 * 
	 * @return: boolean, if true, pass validation; otherwise, false represents
	 * no pass;
	 */
	boolean validateUser(User user) throws DAOException;

	/*
	 * retrieve a user through a username
	 * 
	 * @parameter: username
	 * 
	 * @return a userDTO
	 */
	User retrieveUserInfo(Long userId) throws DAOException;

	
	/*
	 * update a user
	 * 
	 * @parameter: userDTO
	 */
	void updateUser(User user) throws DAOException;

	/*
	 * retrieve all users
	 * 
	 * @parameter:
	 * 
	 * @return a List<userDTO>
	 */
	List<User> retrieveAllUsers() throws DAOException;

	/*
	 * login
	 * 
	 * @parameter: username, passord
	 * 
	 * @return: if true, login successfully, otherwise, failure
	 */
	User login(String username, String password) throws DAOException;

	/*
	 * insert user into table
	 */
	Long addAdmin(User user) throws DAOException;
	/**
	 * Validate the credit card
	 * @param: CreditCard entity
	 * @return boolean, which means whether the current credit card
	 * is valid
	 * */
	boolean validateCreditCard(CreditCard creditCard) throws DAOException;
}
