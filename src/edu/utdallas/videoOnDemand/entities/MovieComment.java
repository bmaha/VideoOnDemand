package edu.utdallas.videoOnDemand.entities;

import java.sql.Date;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job User;
 */

public class MovieComment {

	private Long commentId;
	private Long movieId;
	private Long userId;
	private String commentText;
	private Date createdOn;
	private String username;

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
