package edu.utdallas.videoOnDemand.services;

import java.util.List;

import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.MovieInfoDTO;

public interface MovieManagementService {

	List<MovieDTO> retrieveAllMovies() throws ServiceException;

	MovieDTO retrieveMovie(Long movieID) throws ServiceException;

	MovieInfoDTO retrieveMovieInfo(String imdbID) throws ServiceException;

	void addMovie(MovieDTO movieDTO) throws ServiceException;
	
	void updateMovie(MovieDTO movieDTO) throws ServiceException;

	boolean addMovieToUserFavorite(Long movieID) throws ServiceException;

	boolean removeMovieFromUserFavorite(Long movieID) throws ServiceException;

	boolean checkMovieAsFavorite(Long movieID) throws ServiceException;

	List<MovieDTO> retrieveFavoriteMovies() throws ServiceException;

	List<MovieDTO> retrieveWatchHistory() throws ServiceException;

	void playMovie(Long movieID) throws ServiceException;

	List<MovieDTO> searchMovies(String keyword, String type) throws ServiceException;
	
	MovieDTO rateMovie(Long movieID, int rating) throws ServiceException;

	void removeMovie(MovieDTO movieDTO) throws ServiceException;

	void showMovie(MovieDTO movieDTO) throws ServiceException;

	void hideMovie(MovieDTO movieDTO) throws ServiceException;
}
