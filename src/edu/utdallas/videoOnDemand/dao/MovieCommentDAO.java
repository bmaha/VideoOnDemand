package edu.utdallas.videoOnDemand.dao;

import java.util.List;

import edu.utdallas.videoOnDemand.MovieCommentSvc.MovieCommentDTO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.MovieComment;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job MovieCommentDAO;
 */

public interface MovieCommentDAO {

	/*
	 * insert comments into MovieComment table
	 */
	boolean addMovieComment(Long movieID, String comment, Long userID)
			throws DAOException;

	/*
	 * retrieve all movie comments
	 */
	List<MovieComment> retrieveMovieComments(Long movieID)
			throws DAOException;

}
