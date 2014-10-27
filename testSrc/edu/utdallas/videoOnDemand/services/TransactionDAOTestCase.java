/**
 * 
 */
package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.TransactionDAO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Transaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "serviceContext.xml" })

/**
 * @author lei
 *
 */
public class TransactionDAOTestCase {

	@Autowired
	private TransactionDAO transactionDAO;

	@Test
	public void testWiring() {
		assertNotNull(transactionDAO);
	}
	/**
	 * Test method for {@link edu.utdallas.videoOnDemand.dao.impl.TransactionDAOImpl#addTransaction(edu.utdallas.videoOnDemand.entities.Transaction)}.
	 * @throws Exception 
	 */
	@Test
	public void testAddTransaction() throws Exception {
		Transaction trans = buildTransaction();
		transactionDAO.addTransaction(trans);
	}

	/**
	 * Test method for {@link edu.utdallas.videoOnDemand.dao.impl.TransactionDAOImpl#retrieveAllHistory(java.lang.Long)}.
	 * @throws DAOException 
	 */
	@Test
	public void testRetrieveAllHistory() throws DAOException {
		List<Transaction> userList = transactionDAO.retrieveAllHistory(new Long(3));
		System.out.println("testRetrieveAllUsers");
	}

	/**
	 * Test method for {@link edu.utdallas.videoOnDemand.dao.impl.TransactionDAOImpl#retrieveByType(java.lang.Long, java.lang.String)}.
	 * @throws DAOException 
	 */
	@Test
	public void testRetrieveByType() throws DAOException {
		List<Transaction> userList = transactionDAO.retrieveByType(new Long(3),"R");
		System.out.println("testRetrieveAllUsers");
	}

	/**
	 * Test method for {@link edu.utdallas.videoOnDemand.dao.impl.TransactionDAOImpl#retrieveTransactionID()}.
	 * @throws DAOException 
	 */
	
	private Transaction buildTransaction() {
		Transaction trans = new Transaction();
		trans.setUserID(new Long(3));
		trans.setMovieID(new Long(2));
		trans.setTransType("R");
		trans.setAmount(10);

		return trans;
	}

}
