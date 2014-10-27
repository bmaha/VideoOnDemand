package edu.utdallas.videoOnDemand.transactionSvc;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionServiceImpl;
 */

import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import edu.utdallas.videoOnDemand.dao.TransactionDAO;
import edu.utdallas.videoOnDemand.entities.Transaction;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.services.TransactionService;
import edu.utdallas.videoOnDemand.movieManagementSvc.*;

public class TransactionServiceImpl implements TransactionService {

	private static final Logger logger = Logger
			.getLogger(TransactionServiceImpl.class);

	private TransactionDAO transactionDAO;
	private MovieManagementServiceImpl movieService;

	@Override
	public void rentMovie(TransactionDTO transDTO) throws ServiceException {
		try {
			transDTO.setUserID(getUserSessionID());
			logger.debug("user: " + transDTO.getUserID() + "rents movie "
					+ transDTO.getMovieID());
			Transaction tran = TransactionDTOValidator.convert(transDTO);
			tran.setTransType("R");
			transactionDAO.addTransaction(tran);
			logger.info("New Rental Added for " + transDTO.getUserID());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void purchaseMovie(TransactionDTO transDTO) throws ServiceException {
		try {
			transDTO.setUserID(getUserSessionID());
			logger.debug("user: " + transDTO.getUserID() + "purchased movie "
					+ transDTO.getMovieID());
			Transaction tran = TransactionDTOValidator.convert(transDTO);
			tran.setTransType("P");
			transactionDAO.addTransaction(tran);
			logger.info("New Purchase Added for " + transDTO.getUserID());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> retrieveAllHistory(TransactionDTO transDTO)
			throws ServiceException {
		try {
			List<MovieDTO> results = new ArrayList<MovieDTO>();
			
			transDTO.setUserID(getUserSessionID());
			List<Transaction> trans = transactionDAO.retrieveAllHistory(transDTO
					.getUserID());
			
			for(Transaction tran:trans)
			{
				results.add(movieService.retrieveMovie(tran.getMovieID()));
			}

			return results;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> retrieveRentalHistory()
			throws ServiceException {
		try {
			List<MovieDTO> results = new ArrayList<MovieDTO>();
			List<Transaction> trans = transactionDAO.retrieveByType(
					getUserSessionID(), "R");
			for(Transaction tran:trans)
			{
				results.add(movieService.retrieveMovie(tran.getMovieID()));
			}
			return results;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> retrievePurchaseHistory()
			throws ServiceException {
		try {
			List<MovieDTO> results = new ArrayList<MovieDTO>();
			
			List<Transaction> trans = transactionDAO.retrieveByType(
					getUserSessionID(), "P");
			
			for(Transaction tran:trans)
			{
				results.add(movieService.retrieveMovie(tran.getMovieID()));
			}
			return results;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public Long getUserSessionID() throws ServiceException
	{
		long userId = 0;
		try {
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();
			HttpServletResponse response = ctx.getHttpServletResponse();

			HttpSession session = request.getSession();
			if (!session.isNew()) {
				userId = (Long) session.getAttribute("userId");
			}
		}
		catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return userId;
	}
	public TransactionDAO gettransactionDAO() {
		return transactionDAO;
	}

	public void settransactionDAO(TransactionDAO transactionDAO) {
		this.transactionDAO = transactionDAO;
	}

	@Override
	public int getMovieDaysLeft(Long movieID) throws ServiceException {
		int days = 0;
		try {
			logger.debug("getting number of days left to watch on rent for movie id:"+movieID+" and userID"+getUserSessionID());

			Long userId = getUserSessionID();			
			if (userId > 0) {
				days = transactionDAO.getMovieDaysLeft(
						movieID, userId);
			}
			
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return days;
	}

	
}
