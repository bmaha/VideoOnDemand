/**
 * 
 */
package edu.utdallas.videoOnDemand.transactionSvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import edu.utdallas.videoOnDemand.entities.Transaction;
import edu.utdallas.videoOnDemand.services.ServiceException;
/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionDTOValidator;
 */

public class TransactionDTOValidator {

	static public Transaction convert(TransactionDTO transDTO) throws ServiceException
	{		
		Transaction trans = new Transaction();
		
		trans.setUserID(transDTO.getUserID());
		trans.setMovieID(transDTO.getMovieID());
		trans.setTransType(transDTO.getType());
		trans.setAmount(transDTO.getAmount());
		trans.setDate(new Date(transDTO.getDate()));
		
		return trans;
	}
	
	static public List<TransactionDTO> covertToDTO(List<Transaction> trans) 
	{
		List<TransactionDTO> results = new ArrayList<TransactionDTO>();
		for(Transaction tran: trans) {
			TransactionDTO transDTO = new TransactionDTO(tran);
			results.add(transDTO);
		}
		return results;
	}

}
