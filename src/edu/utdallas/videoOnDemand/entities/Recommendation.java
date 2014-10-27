/**
 * 
 */
package edu.utdallas.videoOnDemand.entities;

/**
 * @author lei
 *
 */
public class Recommendation {

	private Long recommendationID;
	private Long movieID;
	private Long userID;
	public Long getRecommendationID() {
		return recommendationID;
	}
	public void setRecommendationID(Long recommendationID) {
		this.recommendationID = recommendationID;
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
