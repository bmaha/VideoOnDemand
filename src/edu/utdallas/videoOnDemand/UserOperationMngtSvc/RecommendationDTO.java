/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import edu.utdallas.videoOnDemand.entities.Recommendation;

/**
 * @author lei
 *
 */
public class RecommendationDTO {

	private Long recommendationID;
	private Long movieID;
	private Long userID;
	
	public RecommendationDTO(){}
	
	public RecommendationDTO(Recommendation recom){
		this.recommendationID = recom.getRecommendationID();
		this.movieID = recom.getMovieID();
		this.userID = recom.getUserID();
	}
	
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
