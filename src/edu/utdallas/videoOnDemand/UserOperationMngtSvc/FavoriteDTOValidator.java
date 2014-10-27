/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.Favorite;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author lei
 * 
 */
public class FavoriteDTOValidator {

	static public Favorite convert(FavoriteDTO favoriteDTO)
			throws ServiceException {
		Favorite favorite = new Favorite();
		favorite.setMovieID(favoriteDTO.getMovieID());
		favorite.setUserID(favoriteDTO.getUserID());

		return favorite;
	}

	static public List<FavoriteDTO> covertToDTO(List<Favorite> favorite) {
		List<FavoriteDTO> results = new ArrayList<FavoriteDTO>();
		for (Favorite fav : favorite) {
			FavoriteDTO favoriteDTO = new FavoriteDTO(fav);
			results.add(favoriteDTO);
		}
		return results;
	}
}
