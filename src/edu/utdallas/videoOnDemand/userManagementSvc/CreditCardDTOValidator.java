/**
 * 
 */
package edu.utdallas.videoOnDemand.userManagementSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.CreditCard;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author Abhishek Poonia;
 * @date 7/20/2014;
 * @version 1;
 * @job CreditCardDTOValidator;
 */

public class CreditCardDTOValidator {

	static public CreditCard convert(CreditCardDTO creditCardDTO)
			throws ServiceException {
		// TODO Validate the contents of the DTO

		CreditCard creditCard = new CreditCard();

		creditCard.setCreditcardId(creditCardDTO.getCreditcardId());
		creditCard.setCreditcardNumber(creditCardDTO.getCreditcardNumber());
		creditCard.setCreditcardType(creditCardDTO.getCreditcardType());
		creditCard.setName(creditCardDTO.getName());
		creditCard.setSecurityCode(creditCardDTO.getSecurityCode());
		creditCard.setExpiryMonth(creditCardDTO.getExpiryMonth());
		creditCard.setExpiryYear(creditCardDTO.getExpiryYear());

		return creditCard;
	}

	static public List<CreditCardDTO> covertToDTO(List<CreditCard> creditCards) {
		List<CreditCardDTO> results = new ArrayList<CreditCardDTO>();
		for (CreditCard creditCard : creditCards) {
			CreditCardDTO creditCardDTO = new CreditCardDTO(creditCard);
			results.add(creditCardDTO);
		}
		return results;
	}

	static public CreditCardDTO convert(CreditCard creditCard)
			throws ServiceException {
		// TODO Validate the contents of the DTO

		CreditCardDTO creditCardDTO = new CreditCardDTO();

		creditCardDTO.setCreditcardId(creditCard.getCreditcardId());
		creditCardDTO.setCreditcardNumber(creditCard.getCreditcardNumber());
		creditCardDTO.setCreditcardType(creditCard.getCreditcardType());
		creditCardDTO.setName(creditCard.getName());
		creditCardDTO.setSecurityCode(creditCard.getSecurityCode());
		creditCardDTO.setExpiryMonth(creditCard.getExpiryMonth());
		creditCardDTO.setExpiryYear(creditCard.getExpiryYear());

		return creditCardDTO;
	}
}
