package edu.utdallas.videoOnDemand.dao.impl;

/**
 * @author : Mahalakshmi Balasubramanian;
 * @date 7/15/2014;
 * @version 1;
 * @job AdminDAOImpl;
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.dao.AdminDAO;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.User;

public class AdminDAOImpl extends BaseDAO implements AdminDAO {

	private static final Logger logger = Logger.getLogger(AdminDAOImpl.class);
	
	private final String retrieveAllUsersAllInfoQuery = 
			
		"SELECT u.USER_ID,u.USERNAME,u.FIRST_NAME,u.LAST_NAME,u.ISACTIVE, u.ISADMIN, "
		+" COUNT(DISTINCT m.PURCHASED_ID) AS MOVIES_PURCHASED,"
		+" COUNT(DISTINCT r.RENTED_ID) AS MOVIES_RENTED "
		+" (COUNT(DISTINCT m.PURCHASED_ID) + COUNT(DISTINCT r.RENTED_ID)) AS MAX_ACTIVITY "
		+" FROM TBL_USERS AS u "
		+" LEFT JOIN TBL_MOVIE_PURCHASED AS m "
		+" ON u.USER_ID = m.USER_ID "
		+" AND m.PURCHASED_ON BETWEEN '?' AND '?' "
		+" LEFT JOIN TBL_RENTED_MOVIES AS r "
		+" ON u.USER_ID = r.USER_ID "
		+" AND r.RENTED_ON BETWEEN '?' AND '?' "
		+" GROUP BY u.USER_ID,u.USERNAME,u.FIRST_NAME,u.LAST_NAME,u.ISACTIVE,u.ISADMIN "
		+" ORDER BY MAX_ACTIVITY DESC ";
	
	private final String retrieveAllMoviesInfoQuery = 

			"SELECT mov.MOVIE_ID, mov.IMDB_ID, mov.TITLE, mov.CATEGORY, "
			+ "COUNT(DISTINCT m.PURCHASED_ID) AS NUMBER_OF_PURCHASES, "
			+ "COUNT(DISTINCT r.RENTED_ID) AS NUMBER_OF_RENTS "
			+" (COUNT(DISTINCT m.PURCHASED_ID) + COUNT(DISTINCT r.RENTED_ID)) AS MAX_ACTIVITY "
			+" FROM TBL_MOVIES AS mov "
			+" LEFT JOIN TBL_MOVIE_PURCHASED AS m "
			+" ON mov.MOVIE_ID = m.MOVIE_ID "
			+" AND m.PURCHASED_ON BETWEEN '?' AND '?' "
			+" LEFT JOIN TBL_RENTED_MOVIES AS r " 
			+" ON mov.MOVIE_ID = r.MOVIE_ID "
			+" AND r.RENTED_ON BETWEEN '?' AND '?' "
			+" GROUP BY mov.MOVIE_ID, mov.IMDB_ID, mov.TITLE, mov.CATEGORY "
			+" ORDER BY MAX_ACTIVITY DESC ";

	@Override
	public List<User> retrieveAllUsersInfo() throws DAOException {
		logger.debug("Starting retrieveAllUsersInfo");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<User> users = jdbcTemplate.query(retrieveAllUsersAllInfoQuery, new UserMapper());
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public List<Movie> retrieveAllMoviesInfo() throws DAOException {
		logger.debug("Starting retrieveAllMoviesInfo");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<Movie> movies = jdbcTemplate.query(retrieveAllMoviesInfoQuery, new MovieMapper());
		// TODO Auto-generated method stub
		return movies;
	}
	
	public class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User result = new User();
			result.setUserID(rs.getLong("USER_ID"));
			result.setUsername(rs.getString("USERNAME"));
			result.setPassword(rs.getString("PASSWORD"));
			result.setFirstName(rs.getString("FIRST_NAME"));
			result.setLastName(rs.getString("LAST_NAME"));
			result.setIsActive(rs.getString("ISACTIVE"));
			result.setIsAdmin(rs.getString("ISADMIN"));
			result.setMoviesPurchased(rs.getInt("MOVIES_PURCHASED"));
			result.setMoviesRented(rs.getInt("MOVIES_RENTED"));
			result.setMaxActivity(rs.getInt("MAX_ACTIVITY"));
			return result;
		}
	}	
		class MovieMapper implements RowMapper<Movie> {
			@Override
			public Movie mapRow(ResultSet rs1, int rowNum) throws SQLException {
				Movie movResult = new Movie();
				movResult.setMovieId(rs1.getLong("MOVIE_ID"));
				movResult.setTitle(rs1.getString("TITLE"));
				movResult.setCategory(rs1.getString("CATEGORY"));
				movResult.setImdbId(rs1.getString("IMDB_ID"));
				movResult.setNumOfPurchases(rs1.getInt("NUMBER_OF_PURCHASES"));
				movResult.setNumOfRents(rs1.getInt("NUMBER_OF_RENTS"));
				movResult.setMaxActivity(rs1.getInt("MAX_ACTIVITY"));
				return movResult;
			}
		}
}
