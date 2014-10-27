/**
 * 
 */
package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "serviceContext.xml" })
/**
 * @author lei
 *
 */
public class TransactionMngtServiceImplTestCase {

	@Autowired
	private TransactionService transactionManagementSvc;

	@Test
	public void testWiring() {
		assertNotNull(transactionManagementSvc);
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#rentMovie(edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO)}
	 * .
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testRentMovie() throws ServiceException {
		TransactionDTO transDTO = buildTransactionDTO();
		transactionManagementSvc.rentMovie(transDTO);
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#purchaseMovie(edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO)}
	 * .
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testPurchaseMovie() throws ServiceException {
		TransactionDTO transDTO = buildTransactionDTO();
		transactionManagementSvc.purchaseMovie(transDTO);
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#retrieveAllHistory(edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO)}
	 * .
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testRetrieveAllHistory() throws Exception {
		TransactionDTO transDTO = buildTransactionDTO();
		List<MovieDTO> list = transactionManagementSvc.retrieveAllHistory(transDTO);
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#retrieveRentalHistory(edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO)}
	 * .
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testRetrieveRentalHistory() throws ServiceException {
		TransactionDTO transDTO = buildTransactionDTO();
		List<MovieDTO> list = transactionManagementSvc.retrieveRentalHistory();
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#retrievePurchaseHistory(edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO)}
	 * .
	 * 
	 * @throws ServiceException
	 */
	@Test
	public void testRetrievePurchaseHistory() throws ServiceException {
		TransactionDTO transDTO = buildTransactionDTO();
		List<MovieDTO> list = transactionManagementSvc.retrievePurchaseHistory();
	}

	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.transactionSvc.TransactionServiceImpl#setTranDAO(edu.utdallas.videoOnDemand.dao.TransactionDAO)}
	 * .
	 */
	@Test
	public void testSetTranDAO() {

	}

	private TransactionDTO buildTransactionDTO() {

		TransactionDTO result = new TransactionDTO();
		result.setMovieID(new Long(2));
		result.setUserID(new Long(4));
		result.setDate("2014-07-05");
		result.setAmount(50);
		result.setType("r");
		return result;
	}

}
