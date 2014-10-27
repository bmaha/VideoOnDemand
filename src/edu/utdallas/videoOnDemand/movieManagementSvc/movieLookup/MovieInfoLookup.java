package edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup;

import java.io.IOException;

public interface MovieInfoLookup
{
	MovieInfoDTO lookupMovie(String imdbID) throws IOException;
}
