/**
 * 
 */
package edu.utdallas.videoOnDemand.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.utdallas.videoOnDemand.dao.UserDAO;
import edu.utdallas.videoOnDemand.entities.CreditCard;
import edu.utdallas.videoOnDemand.entities.User;

/**
 * @author Lei Cui;
 * @date 6/27/2014;
 * @version 1;
 * @job UserDAOImpl;
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {
	private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

	private static final String insertUserQuery = "insert into tbl_users (username,password,first_name,last_name,email,credit_card_id,isactive,isadmin) values (?,?,?,?,?,?,?,?)";
	private static final String insertCCInfo = "INSERT INTO TBL_CREDIT_CARD_INFO (NAME,CREDIT_CARD_NUMBER,CREDIT_CARD_TYPE, SECURITY_CODE,EXPIRY_MONTH,EXPIRY_YEAR) VALUES (?,?,?,?,?,?)";

	@Override
	public Long addUser(User user, CreditCard creditCard) throws DAOException {
		logger.debug("addUser " + user);
		Long userID = 0L;

		if (user.getUserID() != null) {
			throw new DAOException("Inserted User must have a 0 ID");
		}

		try {
			Connection conn = null;
			PreparedStatement psCC = null;
			PreparedStatement ps = null;
			int CCID = 0;
			try {
				conn = dataSource.getConnection();

				psCC = conn.prepareStatement(insertCCInfo,
						Statement.RETURN_GENERATED_KEYS);
				psCC.setString(1, creditCard.getName());
				psCC.setString(2, creditCard.getCreditcardNumber());
				psCC.setString(3, creditCard.getCreditcardType());
				psCC.setString(4, creditCard.getSecurityCode());
				psCC.setString(5, creditCard.getExpiryMonth());
				psCC.setString(6, creditCard.getExpiryYear());

				psCC.executeUpdate();

				ResultSet rsCC = psCC.getGeneratedKeys();
				while (rsCC.next()) {
					CCID = rsCC.getInt(1);
				}

				if (CCID != 0) {
					ps = conn.prepareStatement(insertUserQuery,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, user.getUsername());
					ps.setString(2, user.getPassword());
					ps.setString(3, user.getFirstName());
					ps.setString(4, user.getLastName());
					ps.setString(5, user.getEmail());
					ps.setLong(6, CCID);
					ps.setString(
							7,
							user.getIsActive() == null ? "1" : user
									.getIsActive());
					ps.setString(8,
							user.getIsAdmin() == null ? "0" : user.getIsAdmin());

					ps.executeUpdate();
					ResultSet rs = ps.getGeneratedKeys();
					while (rs.next()) {
						userID = rs.getLong(1);
						user.setUserID(userID);
					}
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}

		return userID;
	}

	@Override
	public Long addAdmin(User user) throws DAOException {
		logger.debug("addAdmin " + user.getFirstName());
		Long userID = 0L;

		if (user.getUserID() != null) {
			throw new DAOException("Inserted Admin must have a 0 ID");
		}

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			conn = dataSource.getConnection();
			try {

				ps = conn.prepareStatement(insertUserQuery,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getFirstName());
				ps.setString(4, user.getLastName());
				ps.setString(5, user.getEmail());
				ps.setLong(6, 2);
				ps.setString(7, "1");
				ps.setString(8, "1");
				ps.executeUpdate();
				ResultSet rs = ps.getGeneratedKeys();
				while (rs.next()) {
					userID = rs.getLong(1);
					user.setUserID(userID);
				}

			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}

		return userID;
	}

	@Override
	public boolean validateUser(User user) throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}

	private final String retrieveUserQueryByID = "SELECT u.USER_ID,u.USERNAME,u.PASSWORD,u.FIRST_NAME,u.LAST_NAME,u.EMAIL,u.ISACTIVE,u.ISADMIN,c.NAME,c.CREDIT_CARD_NUMBER,c.CREDIT_CARD_TYPE,c.SECURITY_CODE,c.EXPIRY_MONTH,c.EXPIRY_YEAR FROM TBL_USERS AS u JOIN TBL_CREDIT_CARD_INFO AS c ON u.CREDIT_CARD_ID = c.CREDIT_CARD_ID WHERE u.USER_ID = ?";

	@Override
	public User retrieveUserInfo(Long userID) throws DAOException {
		logger.debug("Starting retrieveUserInfo by ID");
		if (userID == 0) {
			throw new DAOException(
					"Entities with keys can not be (re)inserted Long the database");
		}

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Object values[] = { userID };
		User searchUser = null;
		List<User> users = jdbcTemplate.query(retrieveUserQueryByID, values,
				new UserMapper());
		if (users.size() > 0) {
			searchUser = users.get(0);
		}
		return searchUser;
	}

	private final String updateUserQuery = "update tbl_users set username=?, password=?, first_name = ?, last_name = ?, email = ?, credit_card_id = ?, isactive=?, isadmin=? where user_id=?";

	@Override
	public void updateUser(User user) throws DAOException {
		if (user.getUserID() == -1) {
			throw new DAOException("Updated entities must have keys");
		}

		// JdbcTemplate template = new JdbcTemplate(dataSource);
		// Object[] params = { user.getUsername(), user.getPassword(),
		// user.getFirstName(), user.getLastName(), user.getEmail(),
		// user.getCreditCardID(),
		// user.getIsActive(), user.getIsAdmin(), user.getUserID() };
		// Long[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
		// Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR,
		// Types.VARCHAR, Types.INTEGER };
		// template.update(updateUserQuery, params, types);
	}

	private final String retrieveAllUsersQuery = "Select * from TBL_USERS";

	@Override
	public List<User> retrieveAllUsers() throws DAOException {
		logger.debug("Starting retrieveAllUsers");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<User> users = jdbcTemplate.query(retrieveAllUsersQuery,
				new UserMapper());
		return users;
	}

	private final String login = "SELECT u.USER_ID,u.USERNAME,u.PASSWORD,u.FIRST_NAME,u.LAST_NAME,u.EMAIL,u.ISACTIVE,u.ISADMIN,c.NAME,c.CREDIT_CARD_NUMBER,c.CREDIT_CARD_TYPE,c.SECURITY_CODE,c.EXPIRY_MONTH,c.EXPIRY_YEAR FROM TBL_USERS AS u JOIN TBL_CREDIT_CARD_INFO AS c ON u.CREDIT_CARD_ID = c.CREDIT_CARD_ID WHERE u.USERNAME = ? AND u.PASSWORD = ?";

	@Override
	public User login(String username, String password) {
		User user = null;
		JdbcTemplate template = new JdbcTemplate(dataSource);
		Object[] params = { username, password };
		List<User> users = template.query(login, params, new UserMapper());
		if (users.size() == 1) {
			user = users.get(0);
		}
		return user;
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
			result.setEmail(rs.getString("EMAIL"));
			result.setCctype(rs.getString("CREDIT_CARD_TYPE"));
			result.setCcnumber(rs.getString("CREDIT_CARD_NUMBER"));
			result.setCcname(rs.getString("NAME"));
			result.setCcmonth(rs.getString("EXPIRY_MONTH"));
			result.setCcyear(rs.getString("EXPIRY_YEAR"));
			result.setCccvv(rs.getString("SECURITY_CODE"));
			result.setIsActive(rs.getString("ISACTIVE"));
			result.setIsAdmin(rs.getString("ISADMIN"));

			return result;
		}
	}

	private final String validateCreditCard = "SELECT * from TBL_CREDIT_CARD_INFO_TEST where lower(name)=lower(?) and credit_card_number = ? and security_code=?";

	@Override
	public boolean validateCreditCard(CreditCard creditCard)
			throws DAOException {
		logger.debug("Starting validating the credit card");
		boolean flag = false;

		try {
			Connection conn = null;
			PreparedStatement ps = null;
			try {
				conn = dataSource.getConnection();
				ps = conn.prepareStatement(validateCreditCard,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, creditCard.getName());
				ps.setString(2, creditCard.getCreditcardNumber());
				ps.setString(3, creditCard.getSecurityCode());
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()){
					flag = true;
				}
			} finally {
				DbUtils.close(ps);
				DbUtils.close(conn);
			}
		} catch (Exception ex) {
			throw new DAOException(ex.getMessage(), ex);
		}
		return flag;
	}

}
