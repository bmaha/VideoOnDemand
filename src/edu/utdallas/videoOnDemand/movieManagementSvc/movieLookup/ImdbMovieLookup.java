package edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

/**
 * Uses the JSON / RESTFUL service provided by omdbapi.com
 */
public class ImdbMovieLookup implements MovieInfoLookup {
	private static final Logger logger = Logger
			.getLogger(ImdbMovieLookup.class);

	final String imdbURL = "http://www.omdbapi.com/?i=";

	public MovieInfoDTO lookupMovie(String imdbID) throws IOException {
		String jsonString = retrieveJSON(imdbID);
		logger.debug(jsonString);

		Gson gson = new Gson();
		// Remove some non-json data added by the service before parsing.
		jsonString = jsonString.substring(jsonString.indexOf("{"));
		MovieInfoDTO movieInfoDTO = gson.fromJson(jsonString,
				MovieInfoDTO.class);
		return movieInfoDTO;
	}

	private String retrieveJSON(String imdbID) throws ClientProtocolException,
			IOException {
		String url = imdbURL + imdbID;
		logger.info("Using URL " + url);

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			String responseBody = httpclient.execute(httpget,
					new StringResponseHandler());
			return responseBody;
		} finally {
			httpclient.close();
		}
	}

	class StringResponseHandler implements ResponseHandler<String> {
		@Override
		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		}
	}
}
