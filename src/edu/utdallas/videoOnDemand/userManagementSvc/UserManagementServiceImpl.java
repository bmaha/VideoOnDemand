package edu.utdallas.videoOnDemand.userManagementSvc;

/**
 * @author Lei Cui;
 * @date 6/27/2014;
 * @version 1;
 * @job UserManagementServiceImpl;
 */

import java.io.Console;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import edu.utdallas.videoOnDemand.dao.UserDAO;
import edu.utdallas.videoOnDemand.entities.CreditCard;
import edu.utdallas.videoOnDemand.entities.User;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.services.UserManagementService;

public class UserManagementServiceImpl implements UserManagementService {
	private static final Logger logger = Logger
			.getLogger(UserManagementServiceImpl.class);

	private UserDAO userDAO;

	@Override
	public boolean addUser(UserDTO userDTO, CreditCardDTO creditCardDTO) throws ServiceException {
		boolean flag = false;
		try {
			logger.debug("addUser " + userDTO.getUsername());
			CreditCard creditCard = CreditCardDTOValidator.convert(creditCardDTO);
			User user = UserDTOValidator.convert(userDTO);
			Long userID = userDAO.addUser(user,creditCard);

			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			HttpSession session = request.getSession();
			Long userId = user.getUserID();

			if (userID > 0) {
				session.setAttribute("userId", userId);
				flag = true;
			}
			logger.info("New User addeed " + user.getUsername());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public boolean addAdmin(UserDTO userDTO) throws ServiceException
	{
		logger.info(" ADD New Admin " + userDTO.getUsername());
		boolean flag = false;
		try {
			logger.debug("addAdmin " + userDTO.getUsername());
			User user = UserDTOValidator.convert(userDTO);
			Long userID = userDAO.addAdmin(user);
			Long userId = user.getUserID();
			if (userID > 0) {
				flag = true;
			}
			logger.info("New Admin addeed " + user.getUsername());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public boolean validateUser(UserDTO userDTO) throws ServiceException {
		boolean flag = false;
		try {
			User user = UserDTOValidator.convert(userDTO);
			flag = userDAO.validateUser(user);
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public UserDTO retrieveUserInfo() throws ServiceException {
		UserDTO userDTO = null;
		try {
			Long userId = getUserIDfromSession();
			if (userId > 0) {
				User user = userDAO.retrieveUserInfo(userId);
				if (user != null) {
					userDTO = UserDTOValidator.convert(user);
					logger.debug("User Info: " + userDTO.getUserID());
				}
			}
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return userDTO;
	}

	@Override
	public UserDTO retrieveUserInfo(Long parameter) throws ServiceException {
		try {
			User user = userDAO.retrieveUserInfo(parameter);
			UserDTO userDTO = UserDTOValidator.convert(user);
			logger.debug("User Info: " + userDTO.getUsername());
			return userDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void updateUser(UserDTO userDTO) throws ServiceException {
		logger.debug("update User " + userDTO.getUsername());
		try {
			User user = UserDTOValidator.convert(userDTO);
			//userDAO.addUser(user);
			logger.info("New User Updated " + user.getUsername());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<UserDTO> retrieveAllUsers() throws ServiceException {
		try {
			List<User> users = userDAO.retrieveAllUsers();
			List<UserDTO> userDTO = UserDTOValidator.covertToDTO(users);
			return userDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean login(String username, String password)
			throws ServiceException {
		boolean flag = false;
		try {

			User user = userDAO.login(username, password);

			if (user == null) {
				return flag;
			}

			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			HttpSession session = request.getSession();
			Long userId = user.getUserID();
			session.setAttribute("userId", userId);
			logger.debug("authenticated");
			flag = true;

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
		return flag;
	}

	@Override
	public boolean authenticate() throws ServiceException {
		boolean flag = false;
		Long userId = null;
		try {
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			HttpSession session = request.getSession();

			if (session.getAttribute("userId") != null) {
				userId =  (Long) session.getAttribute("userId");
			}

			if (userId != null) {
				logger.debug("authenticated");
				flag = true;
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	public boolean logout() throws ServiceException {
		boolean flag = false;
		Long userId = null;
		try {
			logger.debug("loging out user");
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			HttpSession session = request.getSession();

			if (session.getAttribute("userId") != null) {
				userId = (Long) session.getAttribute("userId");
			}

			if (userId != null) {
				session.removeAttribute("userId");
				logger.debug("loged out user id:" + String.valueOf(userId));
				flag = true;
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage());
		}
		return flag;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public Long getUserIDfromSession() {
		Long userId = 0L;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();

		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null) {
			userId = (Long) session.getAttribute("userId");
		}

		return userId;
	}

	public void test()
	{
		logger.debug("inside user mgmnt service");
	}

	@Override
	public boolean validateCreditCard(CreditCardDTO creditCardDTO)
			throws ServiceException {
		boolean flag = false;
		CreditCard creditCard = null;
		try{
			creditCard = CreditCardDTOValidator.convert(creditCardDTO);
			flag = userDAO.validateCreditCard(creditCard);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return flag;
	}

}
