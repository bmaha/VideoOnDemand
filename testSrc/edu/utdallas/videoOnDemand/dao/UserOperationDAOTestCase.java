/**
 * 
 */
package edu.utdallas.videoOnDemand.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.impl.UserOperationDAOImpl;
import edu.utdallas.videoOnDemand.entities.Comment;
import edu.utdallas.videoOnDemand.entities.Favorite;
import edu.utdallas.videoOnDemand.entities.Movie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "datasourceContext.xml" })
/**
 * @author lei
 *
 */
public class UserOperationDAOTestCase {

	@Autowired
	DataSource datasource;

	@Test
	public void testWiring() {
		assertNotNull(datasource);
	}

	@Test
	public void testAddComment() throws Exception {
		Comment comment = buildComment();
		assertTrue(comment.getCommentID() == null);
		UserOperationDAOImpl userMgntSvcImpl = new UserOperationDAOImpl();
		userMgntSvcImpl.setDataSource(datasource);
		userMgntSvcImpl.addComment(comment);
		assertNotNull(comment.getCommentID());
		System.out.println("Added Comment ID " + comment.getCommentID());
	}

	@Test
	public void testAddFavorite() throws Exception {
		Favorite favorite = buildFavorite();
		assertTrue(favorite.getFavoriteID() == null);
		UserOperationDAOImpl userMgntSvcImpl = new UserOperationDAOImpl();
		userMgntSvcImpl.setDataSource(datasource);
		userMgntSvcImpl.addFavorite(favorite);
		assertNotNull(favorite.getFavoriteID());
		System.out.println("Added Favorite ID " + favorite.getFavoriteID());
	}

	@Test
	public void searchMovieByActor() throws Exception {
		String actor = "Wes Anderson";
		UserOperationDAOImpl userOperDAOImpl = new UserOperationDAOImpl();
		userOperDAOImpl.setDataSource(datasource);
		List<Movie> movies = userOperDAOImpl.searchMovieByActor(actor);
		System.out.println(movies);
	}

	@Test
	public void searchMovieByTitle() throws Exception {
		String title = "mytitle2";
		UserOperationDAOImpl userOperDAOImpl = new UserOperationDAOImpl();
		userOperDAOImpl.setDataSource(datasource);
		List<Movie> movies = userOperDAOImpl.searchMovieByDirector(title);
		System.out.println(movies);
	}

	@Test
	public void searchMovieByDirector() throws Exception {
		String director = "Wes Anderson";
		UserOperationDAOImpl userOperDAOImpl = new UserOperationDAOImpl();
		userOperDAOImpl.setDataSource(datasource);
		List<Movie> movies = userOperDAOImpl.searchMovieByDirector(director);
		System.out.println(movies);
	}

	@Test
	public void autoRecommendMovie() throws Exception {
		Long userID = new Long(1);
		UserOperationDAOImpl userOperDAOImpl = new UserOperationDAOImpl();
		userOperDAOImpl.setDataSource(datasource);
		Map<String, List<Movie>> result = userOperDAOImpl
				.autoRecommendMovie(userID);
	}

	private Comment buildComment() {
		Comment comment = new Comment();

		comment.setMovieID(new Long(2));
		comment.setUserID(new Long(5));
		comment.setCommentText("this is a JUnit test for addComment");
		comment.setDate("2014-07-07");

		return comment;
	}

	private Favorite buildFavorite() {
		Favorite favorite = new Favorite();

		favorite.setMovieID(new Long(13));
		favorite.setUserID(new Long(7));

		return favorite;
	}
}
