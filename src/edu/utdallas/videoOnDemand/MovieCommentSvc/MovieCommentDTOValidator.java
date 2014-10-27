package edu.utdallas.videoOnDemand.MovieCommentSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.MovieComment;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job MovieCommentDTOValidator;
 */

public class MovieCommentDTOValidator {

	static public MovieComment convert(MovieCommentDTO movieCommentDTO)
			throws ServiceException {
		
		MovieComment movieComment = new MovieComment();

		movieComment.setCommentId(movieCommentDTO.getCommentId());
		movieComment.setUserId(movieCommentDTO.getUserID());
		movieComment.setMovieId(movieCommentDTO.getMovieId());
		movieComment.setCommentText(movieCommentDTO.getCommentText());
		movieComment.setCreatedOn(movieCommentDTO.getCreatedOn());
		movieComment.setUsername(movieCommentDTO.getUsername());

		return movieComment;
	}

	static public List<MovieCommentDTO> covertToDTO(
			List<MovieComment> movieComment) {
		List<MovieCommentDTO> results = new ArrayList<MovieCommentDTO>();
		for (MovieComment comment : movieComment) {
			MovieCommentDTO movieCommentDTO = new MovieCommentDTO(comment);
			results.add(movieCommentDTO);
		}
		return results;
	}

}
