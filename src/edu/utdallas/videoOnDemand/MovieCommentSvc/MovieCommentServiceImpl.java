package edu.utdallas.videoOnDemand.MovieCommentSvc;

/**
 * @author Abhishek Poonia;
 * @date 07/05/2014;
 * @version 1;
 * @job MovieCommentServiceImpl;
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

import edu.utdallas.videoOnDemand.dao.MovieCommentDAO;
import edu.utdallas.videoOnDemand.entities.MovieComment;
import edu.utdallas.videoOnDemand.services.MovieCommentService;
import edu.utdallas.videoOnDemand.services.ServiceException;

public class MovieCommentServiceImpl implements MovieCommentService {

	private static final Logger logger = Logger
			.getLogger(MovieCommentServiceImpl.class);

	private MovieCommentDAO commentDAO;

	public void setCommentDAO(MovieCommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}

	@Override
	public boolean addMovieComment(Long movieID, String comment)
			throws ServiceException {

		boolean flag = false;
		try {
			logger.debug("Adding comment to movie id: " + movieID);

			Long userId = null;
			WebContext ctx = WebContextFactory.get();
			HttpServletRequest request = ctx.getHttpServletRequest();

			HttpSession session = request.getSession();

			if (session.getAttribute("userId") != null) {
				userId = (Long) session.getAttribute("userId");
			}

			if (userId > 0) {
				flag = this.commentDAO
						.addMovieComment(movieID, comment, userId);
			}

		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}

		return flag;
	}

	@Override
	public List<MovieCommentDTO> retrieveMovieComments(Long movieID)
			throws ServiceException {
		try {
			List<MovieComment> comments = this.commentDAO
					.retrieveMovieComments(movieID);
			List<MovieCommentDTO> commentsDTO = MovieCommentDTOValidator
					.covertToDTO(comments);
			return commentsDTO;
		} catch (Exception ex) {
			throw new ServiceException(ex.getMessage(), ex);
		}
	}

}
