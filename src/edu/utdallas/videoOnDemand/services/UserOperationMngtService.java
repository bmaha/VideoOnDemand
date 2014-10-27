/**
 * 
 */
package edu.utdallas.videoOnDemand.services;

import java.util.List;
import java.util.Map;

import edu.utdallas.videoOnDemand.UserOperationMngtSvc.CommentDTO;
import edu.utdallas.videoOnDemand.UserOperationMngtSvc.FavoriteDTO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;

/**
 * @author lei
 *
 */
public interface UserOperationMngtService {
	
	/**
	 * Method of adding comments for users
	 * @param: CommentDTO commentDTO
	 * @return
	 * @throws ServiceException 
	 * @throws DAOException 
	 * */
	void addComment(CommentDTO commentDTO) throws ServiceException;
	/**
	 * 
	 * */
	void addFavorite(FavoriteDTO favoriteDTO) throws ServiceException;
//	/**
//	 * 
//	 * */
//	void deleteFavorite();
	/**
	 * Users can search the movie through the attribute of Actor
	 * @param: String 
	 * @return: Movie entity
	 * */
	List<MovieDTO> searchMovieByActor(String actor);
	/**
	 * Users can search the movie through the attribute of Title
	 * @param: String 
	 * @return: Movie entity
	 * */
	List<MovieDTO> searchMovieByTitle(String title);
	/**
	 * Users can search the movie through the attribute of Director
	 * @param: String 
	 * @return: Movie entity
	 * */
	List<MovieDTO> searchMovieByDirector(String director);
	/**
	 * Extract the reccomended movies
	 * @param: String userID
	 * @return: Map<String, ArrayList<Moive>>
	 * */
	public Map<String, List<Movie>> autoRecommendMovie(Long userID) throws ServiceException;
}
