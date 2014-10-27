/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import edu.utdallas.videoOnDemand.entities.Comment;

/**
 * @author lei
 *
 */
public class CommentDTO {

	private Long commentID;
	private Long movieID;
	private Long userID;
	private String commentText;
	private String date;
	
	public CommentDTO(){};
	
	public CommentDTO(Comment comment){
		this.commentID = comment.getCommentID();
		this.movieID = comment.getMovieID();
		this.userID = comment.getUserID();
		this.commentText = comment.getCommentText();
		this.date = comment.getDate();
	}
	
	public Long getCommentID() {
		return commentID;
	}
	public void setCommentID(Long commentID) {
		this.commentID = commentID;
	}
	public Long getMovieID() {
		return movieID;
	}
	public void setMovieID(Long movieID) {
		this.movieID = movieID;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
