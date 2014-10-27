/**
 * 
 */
package edu.utdallas.videoOnDemand.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.dao.UserOperationDAO;
import edu.utdallas.videoOnDemand.entities.Comment;
import edu.utdallas.videoOnDemand.entities.Favorite;
import edu.utdallas.videoOnDemand.entities.Movie;

/**
 * @author lei
 * 
 */
public class UserOperationDAOImpl extends BaseDAO implements UserOperationDAO {

	private static final Logger logger = Logger
			.getLogger(UserOperationDAOImpl.class);

	private static final String insertUserQuery = "insert into tbl_movie_comments (movie_id,created_by,comment_text,created_on) values (?,?,?,?)";

	@Override
	public void addComment(Comment comment) throws DAOException {

		logger.debug("addComment " + comment);

		if (comment.getCommentID() != null) {
			throw new DAOException(
					"Entities with keys can not be (re)inserted into the database");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(insertUserQuery,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, comment.getMovieID());
				ps.setLong(2, comment.getUserID());
				ps.setString(3, comment.getCommentText());
				ps.setString(4, comment.getDate());

				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					Long commentID = rs.getLong(1);
					comment.setCommentID(commentID);
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	private static final String insertFavoriteQuery = "insert into tbl_user_favorites (movie_id,user_id) values (?,?)";

	@Override
	public void addFavorite(Favorite favorite) throws DAOException {
		logger.debug("addFavorite " + favorite);

		if (favorite.getFavoriteID() != null) {
			throw new DAOException(
					"Entities with keys can not be (re)inserted into the database");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(insertFavoriteQuery,
						Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, favorite.getMovieID());
				ps.setLong(2, favorite.getUserID());

				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					Long favoriteID = rs.getLong(1);
					favorite.setFavoriteID(favoriteID);
					;
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}

	}

	private static final String queryMovieListByActor = "select * from TBL_MOVIES where lower(actors) like lower(?)";

	@Override
	public List<Movie> searchMovieByActor(String actor) throws DAOException {
		logger.debug("Starting searchMovieListByActor");

		// JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		// Object[] args = { actor };
		// int[] types = { Types.VARCHAR };
		// List<Movie> movies = jdbcTemplate.query(queryMovieListByActor,
		// args,types, new MovieMapper());

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(queryMovieListByActor);
				ps.setString(1, actor);
				ResultSet rs = ps.executeQuery();
				List<Movie> movies = new ArrayList<Movie>();
				Movie movie = null;
				while (rs.next()) {
					movie = new Movie();
					movie.setMovieId(rs.getLong(1));

					movies.add(movie);
				}
				return movies;
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	private static final String queryMovieListByTitle = "select * from TBL_MOVIES where lower(title) like lower(?)";

	@Override
	public List<Movie> searchMovieByTitle(String title) throws DAOException {
		logger.debug("Starting search MovieList By Title");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object[] args = { title };
		int[] types = { Types.VARCHAR };
		List<Movie> movies = jdbcTemplate.query(queryMovieListByTitle, args,
				types, new MovieMapper());
		return movies;
	}

	private static final String queryMovieListByDirector = "select * from TBL_MOVIES where lower(director) like lower(?)";

	@Override
	public List<Movie> searchMovieByDirector(String director)
			throws DAOException {
		logger.debug("Starting search MovieList By Director");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Object[] args = { director };
			int[] types = { Types.VARCHAR };
			List<Movie> movies = jdbcTemplate.query(queryMovieListByDirector,
					args, types, new MovieMapper());
			return movies;
		} catch (EmptyResultDataAccessException ex) {
			return null;
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
			result.setRating(rs.getInt("RATING"));
			result.setVideoURL(rs.getString("VIDEO_URL"));
			result.setImdbId(rs.getString("IMDB_ID"));
			java.sql.Date temp = rs.getDate("ADDED_ON");
			result.setAddedOn(new java.util.Date(temp.getTime()));
			result.setRentAmount(rs.getDouble("RENT_AMOUNT"));
			result.setPurchaseAmount(rs.getDouble("PURCHASE_AMOUNT"));
			result.setPosterURL(rs.getString("MOVIE_POSTER"));
			return result;
		}
	}

	public Map<String, List<Movie>> autoRecommendMovie(Long userID)
			throws DAOException {
		logger.debug("Starting autoRecommendMovie");

		Map<String, List<Movie>> result = new HashMap<String, List<Movie>>();
		List<String> categoryList = getRecommendedCategory(userID);
		List<Movie> movieList = null;
		
		for(int i=0;i<categoryList.size();i++){
			movieList = getRecommendedMovieByCategory(categoryList.get(i).toString());
			result.put(categoryList.get(i).toString(), movieList);
			
		}
		System.out.println(result);
		
		return result;
	}
	
	/**
	 * Extract the recommended categories that the user likely likes
	 * @param: userID;
	 * @return: List<String>
	 * */
	private static final String queryRecommendedCategory = "select category, count(category) from tbl_movies where movie_id in (select movie_id from favoritemovie_test where user_id=?) group by category";
	private List<String> getRecommendedCategory(Long userID) {
		List<String> categoryList = new ArrayList<String>();
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(queryRecommendedCategory);
				ps.setLong(1, userID);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					categoryList.add(rs.getString("CATEGORY"));
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return categoryList;
	}
	
	/**
	 * Extract the movies according to the each category received from the function getRecommendedCategory
	 * The movies are those that should be recommended to the given user
	 * @param: String, in which there are the recommended categories;
	 * @return: List<Movie>, which corresponds to a given category
	 * */
	private static final String queryRecommendedMovie = "select * from tbl_movies where category = ? order by rating desc";
	private List<Movie> getRecommendedMovieByCategory(String category){
		List<Movie> movieList = new ArrayList<Movie>();
		Movie movie = null;
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(queryRecommendedMovie);
				ps.setString(1, category);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					movie = new Movie();
					
					movie.setMovieId(rs.getLong("MOVIE_ID"));
					movie.setTitle(rs.getString("TITLE"));
					movie.setDescription(rs.getString("DESCRIPTION"));
					movie.setDirector(rs.getString("DIRECTOR"));
					movie.setActors(rs.getString("ACTORS"));
					movie.setCategory(rs.getString("CATEGORY"));
					movie.setRating(rs.getInt("RATING"));
					movie.setVideoURL(rs.getString("VIDEO_URL"));
					movie.setImdbId(rs.getString("IMDB_ID"));
					java.sql.Date temp = rs.getDate("ADDED_ON");
					movie.setAddedOn(new java.util.Date(temp.getTime()));
					movie.setRentAmount(rs.getDouble("RENT_AMOUNT"));
					movie.setPurchaseAmount(rs.getDouble("PURCHASE_AMOUNT"));
					movie.setPosterURL(rs.getString("MOVIE_POSTER"));
					
					movieList.add(movie);					
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return movieList;
	}
}
