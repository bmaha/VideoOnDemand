package edu.utdallas.videoOnDemand.movieInfoLookup;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.ImdbMovieLookup;
import edu.utdallas.videoOnDemand.movieManagementSvc.movieLookup.MovieInfoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"serviceContext.xml"})
public class MovieInfoLookupTestCase
{
	@Autowired
	private ImdbMovieLookup movieLookup;

	@Test
	public void testWiring()
	{
		assertNotNull(movieLookup);
	}

	@Test
	public void testGsonDecoding() throws Exception
	{
		String testdata = "ImdbMovieLookup: 52-{'Title':'The Grand Budapest Hotel','Year':'2014','Rated':'R','Released':'28 Mar 2014','Runtime':'100 min','Genre':'Comedy','Director':'Wes Anderson','Writer':'Stefan Zweig (inspired by the works of), Wes Anderson (story), Hugo Guinness (story), Wes Anderson (screenplay)','Actors':'Ralph Fiennes, F. Murray Abraham, Mathieu Amalric, Adrien Brody','Plot':'The adventures of Gustave H, a legendary concierge at a famous European hotel between the wars, and Zero Moustafa, the lobby boy who becomes his most trusted friend.','Language':'English, French','Country':'USA, Germany','Awards':'2 wins & 2 nominations.','Poster':'http://ia.media-imdb.com/images/M/MV5BMzM5NjUxOTEyMl5BMl5BanBnXkFtZTgwNjEyMDM0MDE@._V1_SX300.jpg','Metascore':'88','imdbRating':'8.4','imdbVotes':'71,916','imdbID':'tt2278388','Type':'movie','Response':'True'}";
		testdata = testdata.substring(testdata.indexOf("{"));
		Gson gson = new Gson();
		MovieInfoDTO movieInfo = gson.fromJson(testdata, MovieInfoDTO.class);
		assertNotNull(movieInfo);	
		assertEquals("Ralph Fiennes, F. Murray Abraham, Mathieu Amalric, Adrien Brody", movieInfo.Actors);
		assertNotNull(movieInfo.Title);
		assertNotNull(movieInfo.Year);
		assertNotNull(movieInfo.Rated);
		assertNotNull(movieInfo.Released);
		assertNotNull(movieInfo.Runtime);
		assertNotNull(movieInfo.Director);
		assertNotNull(movieInfo.Writer);
		assertNotNull(movieInfo.Actors);
		assertNotNull(movieInfo.Plot);
		assertNotNull(movieInfo.Awards);
		assertNotNull(movieInfo.Poster);
	}
	
	@Test
	public void testRetrieveAllMoview() throws Exception
	{
		MovieInfoDTO movieInfo = movieLookup.lookupMovie("tt2278388");
		assertNotNull(movieInfo);
		assertNotNull(movieInfo.Actors);
	}
}
