package edu.utdallas.videoOnDemand.dao;

/**
 * @author : Mahalakshmi Balasubramanian;
 * @date 7/13/2014;
 * @version 1;
 * @job AdminDAO;
 */
import java.util.List;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.User;

public interface AdminDAO {

	public List<User> retrieveAllUsersInfo() throws DAOException;
	
	public List<Movie> retrieveAllMoviesInfo() throws DAOException;
}