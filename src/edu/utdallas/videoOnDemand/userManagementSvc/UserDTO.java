/**
 * 
 */
package edu.utdallas.videoOnDemand.userManagementSvc;

import java.io.Serializable;

import edu.utdallas.videoOnDemand.entities.User;

/**
 * @author Lei Cui;
 * @date 6/27/2014;
 * @version 1;
 * @job UserDTO;
 */
@SuppressWarnings("serial")
public class UserDTO implements Serializable
{

	private Long userID;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String email;
	private String isActive;
	private String isAdmin;
	private Integer moviesPurchased; //from purchase info table
	private Integer moviesRented; // from rental info table
	private Integer maxActivity; // from query results for report
	
	/**
	 * Constructor without any parameters
	 */
	public UserDTO()
	{

	}

	/**
	 * Constructor with User entity
	 */
	public UserDTO(User user)
	{
		this.userID = user.getUserID();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.first_name = user.getFirstName();
		this.last_name = user.getLastName();
		this.email = user.getEmail();
		this.isActive = user.getIsActive();
		this.isAdmin = user.getIsAdmin();
		this.moviesPurchased = user.getMoviesPurchased();
		this.moviesRented = user.getMoviesRented();
		this.maxActivity = user.getMaxActivity();
	}

	public Long getUserID()
	{
		return userID;
	}

	public void setUserID(Long userID)
	{
		this.userID = userID;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getFirst_name()
	{
		return first_name;
	}

	public void setFirst_name(String first_name)
	{
		this.first_name = first_name;
	}

	public String getLast_name()
	{
		return last_name;
	}

	public void setLast_name(String last_name)
	{
		this.last_name = last_name;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}


	public String getIsActive()
	{
		return isActive;
	}

	public void setIsActive(String isActive)
	{
		this.isActive = isActive;
	}

	public String getIsAdmin()
	{
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin)
	{
		this.isAdmin = isAdmin;
	}
	public Integer getMoviesPurchased() {
		return moviesPurchased;
	}

	public void setMoviesPurchased(Integer moviesPurchased) {
		this.moviesPurchased = moviesPurchased;
	}

	public Integer getMoviesRented() {
		return moviesRented;
	}

	public void setMoviesRented(Integer moviesRented) {
		this.moviesRented = moviesRented;
	}
	
	public Integer getMaxActivity() {
		return maxActivity;
	}

	public void setMaxActivity(Integer maxActivity) {
		this.maxActivity = maxActivity;
	}

}
