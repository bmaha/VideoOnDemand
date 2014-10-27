package edu.utdallas.videoOnDemand.adminManagementSvc;
/**
 * @author : Mahalakshmi Balasubramanian;
 * @date 7/10/2014;
 * @version 1;
 * @job AdminManagementServiceImpl;
 */
import java.util.List;

import org.apache.log4j.Logger;

import edu.utdallas.videoOnDemand.dao.AdminDAO;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.User;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTOValidator;
import edu.utdallas.videoOnDemand.services.AdminManagementService;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.userManagementSvc.UserDTO;
import edu.utdallas.videoOnDemand.userManagementSvc.UserDTOValidator;
import edu.utdallas.videoOnDemand.userManagementSvc.UserManagementServiceImpl;

public class AdminManangementServiceImpl implements AdminManagementService {
	private AdminDAO AdminDAOfetchingUserDAO;
	private AdminDAO AdminDAOfetchingmovieDAO;
	private static final Logger logger = Logger
			.getLogger(UserManagementServiceImpl.class);
	@Override
	public List<UserDTO> retrieveAllUsersInfo() throws ServiceException {
		logger.info("Retrieving AllUsersInfo");
		try {
			List<User> users = AdminDAOfetchingUserDAO.retrieveAllUsersInfo();
			List<UserDTO> userDTO = UserDTOValidator.covertToDTO(users);
			return userDTO; 
		} catch (Exception ex) {
		throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> retrieveAllMoviesInfo() throws ServiceException {
		logger.info("Retrieving AllMoviesInfo");
		try {
			List<Movie> movies = AdminDAOfetchingmovieDAO.retrieveAllMoviesInfo();
			List<MovieDTO> movieDTO = MovieDTOValidator.covertToDTO(movies);
			return movieDTO; 
		} catch (Exception ex) {
		throw new ServiceException(ex.getMessage(), ex);
		}
	}


}
