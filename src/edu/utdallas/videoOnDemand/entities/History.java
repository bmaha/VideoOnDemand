package edu.utdallas.videoOnDemand.entities;

import java.util.Date;

/**
 * @author Amber Gullatte;
 * @date 7/7/2014;
 * @version 1;
 * @job User;
 */

public class History {

	private Long userID;
	private Long movieID;
	private Date date;

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public Long getMovieID() {
		return movieID;
	}

	public void setMovieID(Long movieID) {
		this.movieID = movieID;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
