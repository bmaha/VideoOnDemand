package edu.utdallas.videoOnDemand.services;

import java.util.List;

import edu.utdallas.videoOnDemand.MovieCommentSvc.MovieCommentDTO;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job MovieCommentService;
 */

public interface MovieCommentService {
	/*
	 * insert movie comments
	 */
	boolean addMovieComment(Long movieID, String comment)
			throws ServiceException;

	/*
	 * retrieve all movie comments
	 */
	List<MovieCommentDTO> retrieveMovieComments(Long movieID)
			throws ServiceException;

	
}
