package edu.utdallas.videoOnDemand.services;
/**
 * @author : Mahalakshmi Balasubramanian;
 * @date 7/3/2014;
 * @version 1;
 * @job AdminManagementService;
 */
import java.util.List;

import edu.utdallas.videoOnDemand.userManagementSvc.*;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.User;
import edu.utdallas.videoOnDemand.movieManagementSvc.*;


public interface AdminManagementService {

	public List<UserDTO> retrieveAllUsersInfo() throws ServiceException;
	
	public List<MovieDTO> retrieveAllMoviesInfo() throws ServiceException;
}