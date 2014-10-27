package edu.utdallas.videoOnDemand.dao;

import java.util.List;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.History;

public interface MovieDAO
{
	Movie retrieveMovie(Long movieID) throws DAOException;

	void insertMovie(Movie movie) throws DAOException;

	void updateMovie(Movie movie) throws DAOException;

	void removeMovie(Long movieID) throws DAOException;

	List<Movie> retrieveAllMovies() throws DAOException;
	
	List<String> retriveAllMovieTitles() throws DAOException;
	
	List<Movie> retrieveWatchHistory(Long userID) throws DAOException;
	
	void insertHistory(History history) throws DAOException;

	List<Movie> retrieveFavoriteMovies(Long userId) throws DAOException;

	boolean addMovieToFavorite(Long movieID, Long userID) throws DAOException;

	boolean removeMovieFromFavorite(Long movieID, Long userID)
			throws DAOException;

	boolean checkMovieAsFavorite(Long movieID, Long userId) throws DAOException;

	List<Movie> searchMovies(String keyword, String type) throws DAOException;

	void updateMovieRating(Movie movie) throws DAOException;

	void hideMovie(Long movieId);

	void showMovie(Long movieId);
	
} 
