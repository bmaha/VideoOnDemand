package edu.utdallas.videoOnDemand.MovieCommentSvc;

import java.sql.Date;

import edu.utdallas.videoOnDemand.entities.MovieComment;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job User;
 */

public class MovieCommentDTO {

	private Long commentId;
	private Long movieId;
	private Long userID;
	private String commentText;
	private Date createdOn;
	private String username;

	/*
	 * Constructor without any parameters
	 */
	public MovieCommentDTO() {

	}

	/*
	 * Constructor with MovieComment entity
	 */
	public MovieCommentDTO(MovieComment comment) {
		this.setCommentId(comment.getCommentId());
		this.setMovieId(comment.getMovieId());
		this.setUserID(comment.getUserId());
		this.setCommentText(comment.getCommentText());
		this.setCreatedOn(comment.getCreatedOn());
		this.setUsername(comment.getUsername());
	}

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

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
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