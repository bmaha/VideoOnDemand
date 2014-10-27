package edu.utdallas.videoOnDemand.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.impl.MovieDAOImpl;
import edu.utdallas.videoOnDemand.entities.Movie;
import edu.utdallas.videoOnDemand.entities.History;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"datasourceContext.xml"})
public class MovieDAOImplTestCase
{
	@Autowired
    DataSource datasource;

	@Test
	public void testCreateMovie() throws Exception 
	{
		Movie movie = buildMovie();
		
		MovieDAOImpl testDAO = new MovieDAOImpl();
		testDAO.setDataSource(datasource);
		testDAO.insertMovie(movie);
		assertNotNull(movie.getMovieId());
	}

	@Test
	public void testRetrieveMovie() throws Exception
	{
		Movie movie = buildMovie();
		
		MovieDAOImpl testDAO = new MovieDAOImpl(); 
		testDAO.setDataSource(datasource);
		testDAO.insertMovie(movie);

		Movie movie2 = testDAO.retrieveMovie(movie.getMovieId());
		assertEquals(movie.getMovieId(), movie2.getMovieId());
	}
	
	@Test
	public void testRetrieveNull() throws Exception
	{
		
		MovieDAOImpl testDAO = new MovieDAOImpl(); 
		testDAO.setDataSource(datasource);

		Movie movie = testDAO.retrieveMovie(112233L);
		assertNull(movie);
	}
	
	@Test
	public void testUpdateMovie() throws Exception
	{
		Movie movie = buildMovie();
		
		MovieDAOImpl testDAO = new MovieDAOImpl(); 
		testDAO.setDataSource(datasource);
		testDAO.insertMovie(movie);
		
		movie.setTitle("mytitle2");
		testDAO.updateMovie(movie);
		
		Movie movie2 = testDAO.retrieveMovie(movie.getMovieId());
		assertEquals(movie.getMovieId(), movie2.getMovieId());
	}
	@Test
	public void testRateMovie() throws Exception
	{
		Movie movie = buildMovie();
		double rating = 4;
		
		int numTimesRated = movie.getNumTimesRated() + 1;
		double average = (movie.getRating() + rating) / numTimesRated;
		
		movie.setRating(average);
		
		MovieDAOImpl testDAO = new MovieDAOImpl(); 
		testDAO.setDataSource(datasource);
		testDAO.updateMovieRating(movie);
				
		Movie movie2 = testDAO.retrieveMovie(movie.getMovieId());
		assertEquals(movie.getMovieId(), movie2.getMovieId());
	}
	@Test
	public void testRetrieveAllMovies() throws Exception
	{
		MovieDAOImpl testDAO = new MovieDAOImpl();
		testDAO.setDataSource(datasource);
		List<Movie> movies = testDAO.retrieveAllMovies();
		assertTrue(movies.size() > 0);
	}
	@Test
	public void insertHistory() throws Exception
	{
		MovieDAOImpl testDAO = new MovieDAOImpl();
		testDAO.setDataSource(datasource);
		
		History history = buildHistory();
		testDAO.insertHistory(history);
		assertNotNull(history.getMovieID());

	}
	@Test
	public void testRetrieveHistory() throws Exception
	{
		MovieDAOImpl testDAO = new MovieDAOImpl();
		testDAO.setDataSource(datasource);
		List<Movie> movies = testDAO.retrieveWatchHistory(new Long(2));
		assertTrue(movies.size() > 0);
	}
	private History buildHistory()
	{
		History history = new History();
        history.setMovieID(new Long(44));
        history.setUserID(new Long(2));
        history.setDate(new Date());
        return history;
	}

	private Movie buildMovie() 
	{
		Random random = new Random();
		int val = random.nextInt();
		
		Movie movie = new Movie();
		movie.setTitle("atitle" + val); // TITLE
		movie.setDescription("a movie description"); // DESCRIPTION
		movie.setDirector("Joe Directory"); // DIRECTOR
		movie.setActors("actor1, actor2, actor3"); // ACTORS
		movie.setCategory("category"); // CATEGORY
		movie.setRating(1); // RATING
		movie.setVideoURL("http://videos.com/atitle"+val); // VIDEO_URL
		movie.setImdbId("t223344"); // IMDB_ID
		movie.setAddedOn(new Date()); // ADDED_ON
		movie.setRentAmount(4.99); // RENT_AMOUNT
		movie.setPurchaseAmount(14.99); // PURCHASE_AMOUNT
		movie.setPosterURL("poster.jpg"); // MOVIE_POSTER
		movie.setIsVisible("Y"); // MOVIE_POSTER
		movie.setNumTimesRated(2);// MOVIE_POSTER
		return movie;
	}
}
