package edu.utdallas.videoOnDemand.movieManagementSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.MovieInfoDTO;
import edu.utdallas.videoOnDemand.services.ServiceException;

public class MovieDTOValidator {
	static public List<String> validate(MovieDTO movieDTO) {
		List<String> results = new ArrayList<String>();
		// TODO
		return results;
	}

	static public Movie convert(MovieDTO movieDTO) throws ServiceException {
		// TODO Validate the contents of the DTO

		Movie result = new Movie();
		result.setTitle(movieDTO.getTitle());
		result.setDescription(movieDTO.getDescription());
		result.setActors(movieDTO.getActors());
		result.setDirector(movieDTO.getDirector());
		result.setCategory(movieDTO.getCategory());
		result.setRating(movieDTO.getRating());
		result.setPosterURL(movieDTO.getPosterURL());
		result.setImdbId(movieDTO.getImdbId());
		result.setVideoURL(movieDTO.getVideoURL());
		result.setAddedOn(movieDTO.getAddedOn());
		result.setRentAmount(movieDTO.getRentAmount());
		result.setPurchaseAmount(movieDTO.getPurchaseAmount());
		result.setIsVisible(movieDTO.getIsVisible());
		return result;
	}
	
	static public List<MovieDTO> covertToDTO(List<Movie> movies) {
		List<MovieDTO> results = new ArrayList<MovieDTO>();
		for (Movie movie : movies) {
			MovieDTO movieDTO = new MovieDTO(movie);
			results.add(movieDTO);
		}
		return results;
	}
}
