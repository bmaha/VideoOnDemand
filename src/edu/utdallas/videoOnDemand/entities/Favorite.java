/**
 * 
 */
package edu.utdallas.videoOnDemand.entities;

/**
 * @author lei
 *
 */
public class Favorite {

	private Long favoriteID;
	private Long movieID;
	private Long userID;
	public Long getFavoriteID() {
		return favoriteID;
	}
	public void setFavoriteID(Long favoriteID) {
		this.favoriteID = favoriteID;
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
}
