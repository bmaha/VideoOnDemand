/**
 * 
 */
package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.UserOperationMngtSvc.CommentDTO;
import edu.utdallas.videoOnDemand.UserOperationMngtSvc.FavoriteDTO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.Movie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "serviceContext.xml" })
/**
 * @author lei
 *
 */
public class UserOperationMngtSvcImplTestCase {

	@Autowired
	private UserOperationMngtService userOperationMngtService;

	@Test
	public void testWiring() {
		assertNotNull(userOperationMngtService);
	}

	@Test
	public void testAutoReccomendMovie() throws Exception{
		Long userID = new Long(3);
		Map<String, List<Movie>> result = userOperationMngtService.autoRecommendMovie(userID);
	}
	
	/**
	 * Test method for
	 * {@link edu.utdallas.videoOnDemand.UserOperationMngtSvc.UserOperationMngtServiceImpl#addComment(edu.utdallas.videoOnDemand.UserOperationMngtSvc.CommentDTO)}
	 * .
	 * 
	 * @throws DAOException
	 * @throws ServiceException
	 */
	@Test
	public void testAddComment() throws ServiceException, DAOException {
		CommentDTO commentDTO = buildCommentDTO();
		userOperationMngtService.addComment(commentDTO);
	}
	
	@Test
	public void testAddFavorite() throws ServiceException, DAOException {
		FavoriteDTO favoriteDTO = buildFavoriteDTO();
		userOperationMngtService.addFavorite(favoriteDTO);
	}

	private CommentDTO buildCommentDTO() {

		CommentDTO commentDTO = new CommentDTO();
		commentDTO.setMovieID(new Long(2));
		commentDTO.setUserID(new Long(6));
		commentDTO
				.setCommentText("this is JUnit test for UserOperationMngtSvcImpl");
		commentDTO.setDate("2014-07-07");

		return commentDTO;
	}
	
	private FavoriteDTO buildFavoriteDTO() {
		FavoriteDTO favoriteDTO = new FavoriteDTO();
		favoriteDTO.setMovieID(new Long(14));
		favoriteDTO.setUserID(new Long(3));

		return favoriteDTO;
	}

}
