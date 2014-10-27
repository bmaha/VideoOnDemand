/**
 * 
 */
package edu.utdallas.videoOnDemand.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.dao.TransactionDAO;
import edu.utdallas.videoOnDemand.entities.Transaction;

/**
 * @author Amber Gullatte;
 * @date 6/29/2014;
 * @version 1;
 * @job TransactionDAOImpl;
 */
public class TransactionDAOImpl extends BaseDAO implements TransactionDAO {

	private static final Logger logger = Logger.getLogger(TransactionDAOImpl.class);

	private final String insertQuery = "insert into TBL_TRANSACTIONS (user_id,movie_id,transaction_type,transaction_amount,transaction_date) values(?,?,?,?,?)";

	private final String insertIntoPurchase = "INSERT INTO TBL_MOVIE_PURCHASED (MOVIE_ID,USER_ID,PURCHASED_ON) VALUES (?,?,?)";
	
	private final String insertIntoRent = "INSERT INTO TBL_RENTED_MOVIES (MOVIE_ID,USER_ID,RENTED_ON) VALUES (?,?,?)";
	
	private final String deleteFromRent = "DELETE FROM TBL_RENTED_MOVIES WHERE MOVIE_ID = ? AND USER_ID = ?";
	
	@Override
	public void addTransaction(Transaction tran) throws DAOException {
		logger.debug("insertTransaction");
		
		if (tran.getMovieID() == null || tran.getUserID() == null) {
			throw new DAOException("Entities with keys can not be (re)inserted into the database");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setLong(1, tran.getUserID()); // USER_ID
				ps.setLong(2, tran.getMovieID()); // MOVIE_ID
				ps.setString(3, tran.getTransType()); // TRANSACTION_TYPE
				ps.setDouble(4, tran.getAmount()); // TRANSACTION_AMOUNT
				ps.setDate(5, new java.sql.Date(tran.getDate().getTime())); // TRANSACTION_DATE
				
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				while(rs.next()) {
					Long tranID = rs.getLong(1);
					tran.setTransID(tranID);
				}
				
				java.util.Date utilDate = new Date();
				java.sql.Date date = new java.sql.Date(utilDate.getTime());
				
				String transType = tran.getTransType();
				
				JdbcTemplate jdbcTemplateNew = new JdbcTemplate(dataSource);
				Object valuesNew[] = { tran.getMovieID(), tran.getUserID(), date};		
				Object valuesDelete[] = { tran.getMovieID(), tran.getUserID()};
				
				if(transType.equalsIgnoreCase("P"))
				{
					jdbcTemplateNew.update(deleteFromRent, valuesDelete);
					jdbcTemplateNew.update(insertIntoPurchase, valuesNew);
				}
				else if(transType.equalsIgnoreCase("R"))
				{			
					jdbcTemplateNew.update(insertIntoRent, valuesNew);
				}
			}
			finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		}
		catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
	}

	private final String retrieveAllQuery = "select * from tbl_transactions where user_id = ?";

	@Override
	public List<Transaction> retrieveAllHistory(Long userID)
			throws DAOException {
		logger.debug("Starting retrieveAllHistory");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Object args[] = { userID };
			List<Transaction> result = jdbcTemplate.query(retrieveAllQuery,
					args, new TransMapper());
			return result;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	private final String retrieveByTypeQuery = "select * from tbl_transactions where user_id = ? AND transaction_type = ?";

	@Override
	public List<Transaction> retrieveByType(Long userID, String type)
			throws DAOException {
		// TODO Auto-generated method stub
		logger.debug("Starting retrieveByTypeQuery");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		try {
			Object args[] = { userID, type };
			List<Transaction> result = jdbcTemplate.query(retrieveByTypeQuery,
					args, new TransMapper());
			return result;
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	private final String retrieveByMovieQuery = "SELECT * FROM TBL_TRANSACTIONS WHERE USER_ID = ? AND MOVIE_ID = ? ORDER BY TRANSACTION_ID DESC";
	private final String getMovieDaysLeft = "SELECT {fn timestampdiff(SQL_TSI_DAY,t.TRANSACTION_DATE, CURRENT_DATE)} FROM TBL_TRANSACTIONS t WHERE t.TRANSACTION_ID = ?";

	@SuppressWarnings("deprecation")
	@Override
	public int getMovieDaysLeft(Long movieId, Long userId) throws DAOException {
		int daysLeft = -1;
		logger.debug("getMovieDaysLeft");
		if (movieId == 0 && userId ==0) {
			throw new DAOException("Entities with keys can not be (re)inserted into the database");
		}
				
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { userId, movieId };
		
		List<Transaction> result = jdbcTemplate.query(retrieveByMovieQuery, values, new TransMapper());
		
		if(result.size()>0)
		{
			Transaction trans = result.get(0);			
			String transType = trans.getTransType();
			
			if(transType.equalsIgnoreCase("P"))
			{
				daysLeft = 365;
			}
			else if(transType.equalsIgnoreCase("R"))
			{
				JdbcTemplate jdbcTemplateNew = new JdbcTemplate(dataSource);
				Object valuesNew[] = { trans.getTransID() };
				
				daysLeft = jdbcTemplateNew.queryForInt(getMovieDaysLeft,valuesNew);
			}
		}
		
		return daysLeft;
	}
	


	class TransMapper implements RowMapper<Transaction> {
		@Override
		public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
			Transaction result = new Transaction();
			result.setTransID(rs.getLong("TRANSACTION_ID"));
			result.setUserID(rs.getLong("USER_ID"));
			result.setMovieID(rs.getLong("MOVIE_ID"));
			result.setTransType(rs.getString("TRANSACTION_TYPE"));
			result.setAmount(rs.getDouble("TRANSACTION_AMOUNT"));
			result.setDate(rs.getDate("TRANSACTION_DATE"));

			return result;
		}
	}
}
