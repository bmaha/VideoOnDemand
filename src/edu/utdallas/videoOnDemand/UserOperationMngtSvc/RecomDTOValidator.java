/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.Recommendation;

/**
 * @author lei
 * 
 */
public class RecomDTOValidator {

	static public Recommendation convert(RecommendationDTO recomDTO) {

		Recommendation recom = new Recommendation();

		recom.setMovieID(recomDTO.getMovieID());
		recom.setRecommendationID(recomDTO.getRecommendationID());
		recom.setUserID(recomDTO.getUserID());

		return recom;
	}

	static public List<RecommendationDTO> covertToDTO(List<Recommendation> recom) {
		List<RecommendationDTO> results = new ArrayList<RecommendationDTO>();
		for (Recommendation rcom : recom) {
			RecommendationDTO recommendationDTO = new RecommendationDTO(rcom);
			results.add(recommendationDTO);
		}
		return results;
	}
}
