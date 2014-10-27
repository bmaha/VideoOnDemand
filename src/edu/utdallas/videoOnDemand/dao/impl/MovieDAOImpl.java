package edu.utdallas.videoOnDemand.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.dao.MovieDAO;
import edu.utdallas.videoOnDemand.entities.History;
import edu.utdallas.videoOnDemand.entities.Movie;

public class MovieDAOImpl extends BaseDAO implements MovieDAO {
	private static final Logger logger = Logger.getLogger(MovieDAOImpl.class);

	private final String retrieveMovieQuery = "SELECT * FROM TBL_MOVIES WHERE MOVIE_ID = ?";

	@Override
	public Movie retrieveMovie(Long movieID) throws DAOException {
		logger.debug("retrieveMovie " + movieID);

		Movie result = null;
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Object args[] = { movieID };
			result = jdbcTemplate.queryForObject(retrieveMovieQuery, args,
					new MovieMapper());
			return result;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	private final String insertMovieQuery = "INSERT INTO TBL_MOVIES (TITLE,DESCRIPTION,DIRECTOR,ACTORS,CATEGORY,ADDED_ON,POSTER_URL,VIDEO_URL,IMDB_ID,IS_VISIBLE) VALUES (?,?,?,?,?,?,?,?,?,?)";

	@Override
	public void insertMovie(Movie movie) throws DAOException {
		logger.debug("insertMovie");

		if (movie.getImdbId() == null) {
			throw new DAOException(
					"Movie IMDB ID cannot be null");
		}

		java.util.Date utilDate = new Date();
		java.sql.Date date = new java.sql.Date(utilDate.getTime());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { movie.getTitle(), movie.getDescription(),
				movie.getDirector(), movie.getActors(), movie.getCategory(),
				date, movie.getPosterURL(), movie.getVideoURL(),
				movie.getImdbId(),movie.getIsVisible() };
		jdbcTemplate.update(insertMovieQuery, values);
	}

	private final String updateMovieQuery = "update tbl_movies set TITLE=?, DESCRIPTION=?, DIRECTOR=?, ACTORS=?, CATEGORY=?, RATING=?, VIDEO_URL=?, "
			+ "IMDB_ID=?, ADDED_ON=?, RENT_AMOUNT=?, PURCHASE_AMOUNT=?, POSTER_URL=? where movie_id = ? ";

	@Override
	public void updateMovie(Movie movie) throws DAOException {
		if (movie.getImdbId() == null) {
			throw new DAOException("Updated entities must have keys");
		}

		try {

			java.util.Date utilDate = new Date();
			java.sql.Date date = new java.sql.Date(utilDate.getTime());

			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(updateMovieQuery);
				ps.setString(1, movie.getTitle()); // TITLE
				ps.setString(2, movie.getDescription()); // DESCRIPTION
				ps.setString(3, movie.getDirector()); // DIRECTOR
				ps.setString(4, movie.getActors()); // ACTORS
				ps.setString(5, movie.getCategory()); // CATEGORY
				ps.setDouble(6, movie.getRating()); // RATING
				ps.setString(7, movie.getVideoURL()); // VIDEO_URL
				ps.setString(8, movie.getImdbId()); // IMDB_ID
				ps.setDate(9, date); // ADDED_ON
				ps.setDouble(10, movie.getRentAmount()); // RENT_AMOUNT
				ps.setDouble(11, movie.getPurchaseAmount()); // PURCHASE_AMOUNT
				ps.setString(12, movie.getPosterURL()); // MOVIE_POSTER
				ps.setLong(13, movie.getMovieId()); // MOVIE_ID

				ps.executeUpdate();
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}


	private final String updateMovieRatingQuery = "update tbl_movies set RATING=?, NUM_TIMES_RATED=? where movie_id = ? ";


	@Override
	public void updateMovieRating(Movie movie) throws DAOException {
		if (movie.getMovieId() == null) {
			throw new DAOException("Updated entities must have keys");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(updateMovieRatingQuery);
				ps.setDouble(1, movie.getRating()); // RATING
				ps.setInt(2, movie.getNumTimesRated()); // NUM_TIMES_RATED
				ps.setLong(13, movie.getMovieId()); // MOVIE_ID

				ps.executeUpdate();
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	private final String retrieveAllMoviesQuery = "SELECT * FROM TBL_MOVIES ORDER BY ADDED_ON DESC";

	@Override
	public List<Movie> retrieveAllMovies() throws DAOException {
		logger.debug("Starting retrieveAllMovies");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Movie> movies = jdbcTemplate.query(retrieveAllMoviesQuery,
				new MovieMapper());
		return movies;
	}

	private final String retrieveFavoriteMovies = "SELECT m.MOVIE_ID,m.TITLE,m.DESCRIPTION,m.DIRECTOR,m.ACTORS,m.CATEGORY,m.RATING,m.VIDEO_URL,m.IMDB_ID,m.ADDED_ON,m.RENT_AMOUNT,m.PURCHASE_AMOUNT,m.POSTER_URL,m.IS_VISIBLE,m.NUM_TIMES_RATED FROM TBL_MOVIES m JOIN TBL_USER_FAVORITES f ON f.MOVIE_ID = m.MOVIE_ID WHERE f.USER_ID = ? ORDER BY f.FAVORITE_ID DESC";

	@Override
	public List<Movie> retrieveFavoriteMovies(Long userId) throws DAOException {
		logger.debug("Starting retrieveFavoriteMovies");
		if (userId == null) {
			throw new DAOException(
					"Required keys user id");
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { userId };
		List<Movie> movies = jdbcTemplate.query(retrieveFavoriteMovies, values,
				new MovieMapper());
		return movies;
	}

	private final String insertMovieToFavorite = "INSERT INTO TBL_USER_FAVORITES (MOVIE_ID,USER_ID) VALUES (?,?)";

	@Override
	public boolean addMovieToFavorite(Long movieID, Long userID)
			throws DAOException {

		logger.debug("addMovieToFavorite");
		if (movieID == null && userID == null) {
			throw new DAOException(
					"Required keys movie id and user id");
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { movieID, userID };
		jdbcTemplate.update(insertMovieToFavorite, values);
		return true;
	}

	private final String removeMovieFromFavorite = "DELETE FROM TBL_USER_FAVORITES WHERE MOVIE_ID = ? AND USER_ID = ?";

	@Override
	public boolean removeMovieFromFavorite(Long movieID, Long userID)
			throws DAOException {

		logger.debug("removeMovieFromFavorite");
		if (movieID == null && userID == null) {
			throw new DAOException(
					"Required keys movie id and user id");
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { movieID, userID };
		jdbcTemplate.update(removeMovieFromFavorite, values);
		return true;
	}

	private final String checkMovieAsFavorite = "SELECT COUNT(*) FROM TBL_USER_FAVORITES WHERE MOVIE_ID = ? AND USER_ID = ?";

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkMovieAsFavorite(Long movieID, Long userId)
			throws DAOException {
		logger.debug("checkMovieAsFavorite");
		if (movieID == null && userId == null) {
			throw new DAOException(
					"Required keys movie id and user id");
		}

		int count = 0;

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { movieID, userId };
		count = (int) jdbcTemplate.queryForInt(checkMovieAsFavorite, values);
		if (count > 0) {
			return true;
		}
		return false;
	}

	private final String searchMovieByTitle = "SELECT * FROM VODAPP.TBL_MOVIES WHERE UPPER(TITLE) LIKE UPPER(?) ORDER BY ADDED_ON DESC";
	private final String searchMovieByDir = "SELECT * FROM VODAPP.TBL_MOVIES WHERE UPPER(DIRECTOR) LIKE UPPER(?) ORDER BY ADDED_ON DESC";
	private final String searchMovieByActor = "SELECT * FROM VODAPP.TBL_MOVIES WHERE UPPER(ACTORS) LIKE UPPER(?) ORDER BY ADDED_ON DESC";

	@Override
	public List<Movie> searchMovies(String keyword, String type)
			throws DAOException {
		logger.debug("Starting searchMovies");

		List<Movie> movies = new ArrayList<Movie>();
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			
			try {
				conn = dataSource.getConnection();
				
				if (type.equalsIgnoreCase("title")) // title
				{
					ps = conn.prepareStatement(searchMovieByTitle);
									
				} else if (type.equalsIgnoreCase("director")) // director
				{
					ps = conn.prepareStatement(searchMovieByDir);
					
				} else if (type.equalsIgnoreCase("actor")) // actor
				{
					ps = conn.prepareStatement(searchMovieByActor);
				}

				
				ps.setString(1, "%"+keyword+"%");				
				ResultSet rs = ps.executeQuery();
				int rowNum = 0;
				while (rs.next()) {
					Movie movie = new MovieMapper().mapRow(rs, rowNum);
					movies.add(movie);
				    rowNum++;
				}
			
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
						
		return movies;
	}

	private final String retrieveWatchHistory = "SELECT * FROM TBL_MOVIES WHERE MOVIE_ID in (SELECT DISTINCT MOVIE_ID from TBL_WATCH_HISTORY WHERE user_id = ?)";

	@Override
	public List<Movie> retrieveWatchHistory(Long userID) throws DAOException {
		logger.debug("Starting retrieveWatchHistory");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object args[] = { userID };
		List<Movie> movies = jdbcTemplate.query(retrieveWatchHistory, args,
				new MovieMapper());
		return movies;
	}

	private final String insertHistoryQuery = "INSERT INTO TBL_WATCH_HISTORY (MOVIE_ID,USER_ID,WATCHED_ON) VALUES (?,?,?)";

	@Override
	public void insertHistory(History history) throws DAOException {
		logger.debug("insertHistory");

		if (history.getMovieID() == null || history.getUserID() == null) {
			throw new DAOException(
					"Entities with keys can not be (re)inserted into the database");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(insertHistoryQuery);
				ps.setLong(1, history.getMovieID()); // MOVIE_ID
				ps.setLong(2, history.getUserID()); // USER_ID
				ps.setTimestamp(3, new java.sql.Timestamp(history.getDate()
						.getTime())); // WATCHED_ON

				ps.executeUpdate();
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	class MovieMapper implements RowMapper<Movie> {
		@Override
		public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
			Movie result = new Movie();
			result.setMovieId(rs.getLong("MOVIE_ID"));
			result.setTitle(rs.getString("TITLE"));
			result.setDescription(rs.getString("DESCRIPTION"));
			result.setDirector(rs.getString("DIRECTOR"));
			result.setActors(rs.getString("ACTORS"));
			result.setCategory(rs.getString("CATEGORY"));
			result.setRating(rs.getDouble("RATING"));
			result.setVideoURL(rs.getString("VIDEO_URL"));
			result.setImdbId(rs.getString("IMDB_ID"));
			result.setAddedOn(rs.getDate("ADDED_ON"));
			result.setRentAmount(rs.getDouble("RENT_AMOUNT"));
			result.setPurchaseAmount(rs.getDouble("PURCHASE_AMOUNT"));
			result.setPosterURL(rs.getString("POSTER_URL"));
			result.setIsVisible(rs.getString("IS_VISIBLE"));
			result.setNumTimesRated(rs.getInt("NUM_TIMES_RATED"));

			return result;
		}
	}
	
	@SuppressWarnings("null")
	@Override
	public List<String> retriveAllMovieTitles() throws DAOException
	{
		logger.debug("Starting retrieveAllMovieTitles");
		List<String> movieTitles = new ArrayList<String>(); 
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Movie> movies = jdbcTemplate.query(retrieveAllMoviesQuery,
				new MovieMapper());
		for (Movie movie : movies)
		{
			movieTitles.add(movie.getTitle());
		}
		return movieTitles;
	}
	
	private final String deleteMovieQuery = "delete from TBL_MOVIES where movie_id = ?";

	@Override
	public void removeMovie(Long movieId)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		Object[] params = { movieId };
		int[] types = { Types.INTEGER };
		jdbcTemplate.update(deleteMovieQuery, params, types);		
	}

	private final String showMovieQuery = "UPDATE TBL_MOVIES SET IS_VISIBLE = 'Y' where movie_id = ?";

	@Override
	public void showMovie(Long movieId)
	{
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] params = { movieId };
		int[] types = { Types.INTEGER };
		jdbcTemplate.update(showMovieQuery, params, types);		
	}

	private final String hideMovieQuery = "UPDATE TBL_MOVIES SET IS_VISIBLE = 'N' where movie_id = ?";

	@Override
	public void hideMovie(Long movieId)
	{
		logger.debug("hideMovie in DAO " + movieId);

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] params = { movieId };
		int[] types = { Types.INTEGER };
		jdbcTemplate.update(hideMovieQuery, params, types);		
	}


}
