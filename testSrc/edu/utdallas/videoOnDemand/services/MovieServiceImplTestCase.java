package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.movieManagementSvc.MovieDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"serviceContext.xml"})
public class MovieServiceImplTestCase
{
	@Autowired
	private MovieManagementService movieMngtSvc;

	@Test
	public void testWiring()
	{
		assertNotNull(movieMngtSvc);
	}

	@Test
	public void testRetrieveAllMoview() throws Exception
	{
		List<MovieDTO> movies = movieMngtSvc.retrieveAllMovies();
		assertNotNull(movies);
		assertTrue(movies.size() > 0);
	}
}
