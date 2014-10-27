/**
 * 
 */
package edu.utdallas.videoOnDemand.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.MovieCommentSvc.MovieCommentDTO;
import edu.utdallas.videoOnDemand.dao.MovieCommentDAO;
import edu.utdallas.videoOnDemand.dao.impl.MovieDAOImpl.MovieMapper;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.MovieComment;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job MovieCommentDAO;
 */
public class MovieCommentDAOImpl extends BaseDAO implements MovieCommentDAO {

	private static final Logger logger = Logger.getLogger(MovieCommentDAOImpl.class);
	
	private final String insertMovieComment = "INSERT INTO TBL_MOVIE_COMMENTS (MOVIE_ID,CREATED_BY,COMMENT_TEXT,CREATED_ON) VALUES (?,?,?,?)";

	@Override
	public boolean addMovieComment(Long movieID, String comment, Long userID)	throws DAOException {
		
		logger.debug("addMovieComment");
		if (movieID == null && userID == null) {
			throw new DAOException("Required keys movie id and user id");
		}
		
		java.util.Date utilDate = new Date();
		java.sql.Date date = new java.sql.Date(utilDate.getTime());
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { movieID, userID, comment,date};
		jdbcTemplate.update(insertMovieComment, values);
		return true;
	}

	
	private final String retrieveAllQuery = "SELECT c.COMMENT_ID,c.MOVIE_ID,c.CREATED_BY,c.COMMENT_TEXT,c.CREATED_ON,u.USERNAME FROM TBL_MOVIE_COMMENTS AS c JOIN TBL_USERS AS u ON c.CREATED_BY = u.USER_ID WHERE c.MOVIE_ID = ? ORDER BY c.COMMENT_ID DESC";
	
	@Override
	public List<MovieComment> retrieveMovieComments(Long movieID)
			throws DAOException {
		
		logger.debug("retrieveMovieComments");
		
		if (movieID == null) {
			throw new DAOException("Required keys movie id");
		}
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Object args[] = { movieID };
			List<MovieComment> result = jdbcTemplate.query(retrieveAllQuery, args,new MovieCommentMapper());
			return result;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	class MovieCommentMapper implements RowMapper<MovieComment>
	{
		@Override
		public MovieComment mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			MovieComment result = new MovieComment();
			result.setCommentId(rs.getLong("COMMENT_ID"));
			result.setUserId(rs.getLong("CREATED_BY"));
			result.setMovieId(rs.getLong("MOVIE_ID"));
			result.setCommentText(rs.getString("COMMENT_TEXT"));
			result.setCreatedOn(rs.getDate("CREATED_ON"));
			result.setUsername(rs.getString("USERNAME"));

			return result;
		}
	}

	
	
}
