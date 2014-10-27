/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.utdallas.videoOnDemand.dao.UserOperationDAO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Comment;
import edu.utdallas.videoOnDemand.entities.Favorite;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.services.UserOperationMngtService;

/**
 * @author lei
 * 
 */
public class UserOperationMngtServiceImpl implements UserOperationMngtService {

	private static final Logger logger = Logger
			.getLogger(UserOperationMngtServiceImpl.class);

	private UserOperationDAO userOperationDAO;

	@Override
	public void addComment(CommentDTO commentDTO) throws ServiceException {
		logger.debug("addComment " + commentDTO.getCommentText());

		Comment comment = CommentDTOValidator.convert(commentDTO);
		try {
			userOperationDAO.addComment(comment);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		logger.info("New Comment " + comment.getCommentText());

	}

	public UserOperationDAO getuserOperationDAO() {
		return userOperationDAO;
	}

	public void setuserOperationDAO(UserOperationDAO userOperationDAO) {
		this.userOperationDAO = userOperationDAO;
	}

	@Override
	public void addFavorite(FavoriteDTO favoriteDTO) throws ServiceException {
		
		logger.debug("addFavorite " + favoriteDTO.getMovieID() + " for user"+ favoriteDTO.getUserID());
		
		Favorite favorite = FavoriteDTOValidator.convert(favoriteDTO);
		try {
			userOperationDAO.addFavorite(favorite);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MovieDTO> searchMovieByActor(String actor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieDTO> searchMovieByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovieDTO> searchMovieByDirector(String director) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Movie>> autoRecommendMovie(Long userID)
			throws ServiceException {
		Map<String, List<Movie>> movieList = null;
		try{
			movieList = userOperationDAO.autoRecommendMovie(userID);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return movieList;
	}

}
