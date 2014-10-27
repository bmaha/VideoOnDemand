/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import edu.utdallas.videoOnDemand.entities.Favorite;

/**
 * @author lei
 *
 */
public class FavoriteDTO {

	private Long favoriteID;
	private Long movieID;
	private Long userID;
	
	public FavoriteDTO(){};
	
	public FavoriteDTO(Favorite favorite){
		this.favoriteID = favorite.getFavoriteID();
		this.movieID = favorite.getMovieID();
		this.userID = favorite.getUserID();
	}
	
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
