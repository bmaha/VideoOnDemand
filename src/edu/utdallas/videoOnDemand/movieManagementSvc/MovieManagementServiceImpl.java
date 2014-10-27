package edu.utdallas.videoOnDemand.movieManagementSvc;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import edu.utdallas.videoOnDemand.dao.MovieDAO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.History;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.User;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.ImdbMovieLookup;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.MovieInfoDTO;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.MovieInfoLookup;
import edu.utdallas.videoOnDemand.services.MovieManagementService;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.userManagementSvc.UserDTOValidator;

public class MovieManagementServiceImpl implements MovieManagementService {
	private static final Logger logger = Logger
			.getLogger(MovieManagementServiceImpl.class);

	private MovieDAO movieDAO;
	private MovieInfoLookup movieLookup;

	@Override
	public List<MovieDTO> retrieveAllMovies() throws ServiceException {
		try {
			List<Movie> movies = movieDAO.retrieveAllMovies();
			List<MovieDTO> moviesDTO = MovieDTOValidator.covertToDTO(movies);
			return moviesDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@SuppressWarnings("null")
	@Override
	public MovieDTO retrieveMovie(Long movieID) throws ServiceException {
		try {
			Movie movie = movieDAO.retrieveMovie(movieID);
			List<Movie> movies = new ArrayList<Movie>();
			movies.add(movie);
			List<MovieDTO> moviesDTO = MovieDTOValidator.covertToDTO(movies);
			MovieDTO movieDTO = moviesDTO.get(0);
			return movieDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public MovieInfoDTO retrieveMovieInfo(String movieIDStr)
			throws ServiceException {
		logger.debug("Movie ID: " + movieIDStr);

		try {
			Long movieID = Long.parseLong(movieIDStr);
			Movie movie = movieDAO.retrieveMovie(movieID);
			String imdbID = movie.getImdbId();

			MovieInfoDTO movieInfo = movieLookup.lookupMovie(imdbID);
			movieInfo.setImdbID(imdbID);
			logger.debug("Movie Info: " + movieInfo.getActors());

			return movieInfo;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void addMovie(MovieDTO movieDTO) throws ServiceException {
		logger.debug("addMovie " + movieDTO.getTitle());

		try {
			MovieInfoDTO imdbInfo = new ImdbMovieLookup().lookupMovie(movieDTO.getImdbId());			

			movieDTO.setTitle(imdbInfo.getTitle());
			movieDTO.setDescription(imdbInfo.getPlot());
			movieDTO.setDirector(imdbInfo.getDirector());
			movieDTO.setActors(imdbInfo.getActors());
			movieDTO.setCategory(imdbInfo.getGenre());
			movieDTO.setPosterURL(imdbInfo.getPoster());
			movieDTO.setIsVisible("Y");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Movie movie = MovieDTOValidator.convert(movieDTO);
		try {
			List<String> movieTitles = movieDAO.retriveAllMovieTitles();
			if(!movieTitles.contains(movie.getTitle()))
			{
				movieDAO.insertMovie(movie);
				logger.info("New Movie Added " + movie.getMovieId());
			}
			else
			{
				logger.info("Movie with same title already available in the Database" + movie.getMovieId());
			}

		} catch (DAOException ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void removeMovie(MovieDTO movieDTO) throws ServiceException {
		logger.debug("removeMovie " + movieDTO.getMovieId());
		try {
			movieDAO.removeMovie(movieDTO.getMovieId());
			logger.info("Movie Removed " + movieDTO.getMovieId());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void showMovie(MovieDTO movieDTO) throws ServiceException {
		logger.debug("showMovie " + movieDTO.getMovieId());
		try {
			movieDAO.showMovie(movieDTO.getMovieId());
			logger.info("Movie Changed to SHOW MOVIE " + movieDTO.getMovieId());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}
	
	@Override
	public void hideMovie(MovieDTO movieDTO) throws ServiceException {
		logger.debug("hideMovie " + movieDTO.getMovieId());
		try {
			movieDAO.hideMovie(movieDTO.getMovieId());
			logger.info("Movie Changed to HIDE MOVIE " + movieDTO.getMovieId());
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}
	
	
	public void updateMovie(MovieDTO movieDTO) throws ServiceException {
		logger.debug("updateMovie " + movieDTO.getTitle());

		try {

			movieDAO.removeMovie(movieDTO.getMovieId());
			logger.info("Movie Removed " + movieDTO.getMovieId());

			Movie movie = MovieDTOValidator.convert(movieDTO);
			movieDAO.updateMovie(movie);
			

		} catch (DAOException ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	public void setMovieLookup(MovieInfoLookup movieLookup) {
		this.movieLookup = movieLookup;
	}

	public void setMovieDAO(MovieDAO movieDAO) {
		this.movieDAO = movieDAO;
	}


	@Override
	public boolean addMovieToUserFavorite(Long movieID) throws ServiceException {
		boolean flag = false;
		try {
			logger.debug("Adding to user favorite movie id: " + movieID);

			Long userId = getUserIDfromSession();			
			if (userId > 0) {
				flag = this.movieDAO.addMovieToFavorite(
						movieID, userId);
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public boolean removeMovieFromUserFavorite(Long movieID) throws ServiceException {
		boolean flag = false;
		try {
			logger.debug("Adding to user favorite movie id: " + movieID);

			Long userId = getUserIDfromSession();			
			if (userId > 0) {
				flag = this.movieDAO.removeMovieFromFavorite(
						movieID, userId);
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public boolean checkMovieAsFavorite(Long movieID) throws ServiceException {
		boolean flag = false;
		try {
			logger.debug("checking is movie in user favorite, movie id: " + movieID);

			Long userId = getUserIDfromSession();			
			if (userId > 0) {
				flag = this.movieDAO.checkMovieAsFavorite(
						movieID, userId);
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
		return flag;
	}

	@Override
	public List<MovieDTO> retrieveFavoriteMovies() throws ServiceException {
		try {
			List<MovieDTO> moviesDTO = null;
			Long userId = getUserIDfromSession();
			if (userId > 0) {
				List<Movie> movies = movieDAO.retrieveFavoriteMovies(userId);
				moviesDTO = MovieDTOValidator.covertToDTO(movies);
			}
			return moviesDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> searchMovies(String keyword, String type) throws ServiceException {
		try {
			if(keyword == null || type==null)
			{
				return null;
			}
			keyword = URLDecoder.decode(keyword);
			List<Movie> movies = movieDAO.searchMovies(keyword, type);
			List<MovieDTO> moviesDTO = MovieDTOValidator.covertToDTO(movies);
			return moviesDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public List<MovieDTO> retrieveWatchHistory() throws ServiceException
	{
		try {
			List<Movie> movies = movieDAO.retrieveWatchHistory(getUserIDfromSession());
			List<MovieDTO> moviesDTO = MovieDTOValidator.covertToDTO(movies);
			return moviesDTO;
		}
		catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public void playMovie(Long movieID) throws ServiceException
	{
		logger.debug("playMovie " + movieID);
		try {
			History history = new History();
			history.setMovieID(movieID);
			history.setUserID(getUserIDfromSession());
			history.setDate(new Date());
			movieDAO.insertHistory(history);
			logger.info("Movie added to watch history");
		}
		catch (DAOException ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}	    
	}


	public Long getUserIDfromSession()
	{
		Long userId = null;
		WebContext ctx = WebContextFactory.get();
		HttpServletRequest request = ctx.getHttpServletRequest();

		HttpSession session = request.getSession();

		if (session.getAttribute("userId") != null) {
			userId = (Long) session.getAttribute("userId");
		}

		return userId;
	}
	@Override
	public MovieDTO rateMovie(Long movieID, int rating) throws ServiceException {
		try {
			Movie movie = movieDAO.retrieveMovie(movieID);
			int numTimesRated = movie.getNumTimesRated() + 1;
			double average = (movie.getRating() + rating) / numTimesRated;
			
			//save new rating
			movie.setRating(average);
			movie.setNumTimesRated(numTimesRated);
			movieDAO.updateMovieRating(movie);
			MovieDTO movieDTO = new MovieDTO(movie);
			return movieDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}


}
