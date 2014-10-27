/**
 * 
 */
package edu.utdallas.videoOnDemand.dao;

import java.util.List;
import java.util.Map;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Comment;
import edu.utdallas.videoOnDemand.entities.Favorite;
import edu.utdallas.videoOnDemand.entities.Movie;
/**
 * @author lei
 *
 */
public interface UserOperationDAO {

	/**
	 * Method of adding comments for users
	 * @param: CommentDTO commentDTO
	 * @return
	 * @throws DAOException 
	 * */
	void addComment(Comment comment) throws DAOException;
	/**
	 * 
	 * */
	void addFavorite(Favorite favorite) throws DAOException;
//	/**
//	 * 
//	 * */
//	void deleteComment();
	/**
	 * Users can search the movie through the attribute of Actor
	 * @param: String 
	 * @return: Movie entity
	 * */
	public List<Movie> searchMovieByActor(String actor) throws DAOException;
	/**
	 * Users can search the movie through the attribute of Title
	 * @param: String 
	 * @return: Movie entity
	 * */
	public List<Movie> searchMovieByTitle(String title) throws DAOException;
	/**
	 * Users can search the movie through the attribute of Director
	 * @param: String 
	 * @return: Movie entity
	 * */
	public List<Movie> searchMovieByDirector(String director) throws DAOException;
	/**
	 * Extract the reccomended movies
	 * @param: String userID
	 * @return: Map<String, ArrayList<Moive>>
	 * */
	public Map<String, List<Movie>> autoRecommendMovie(Long userID)throws DAOException;
}
