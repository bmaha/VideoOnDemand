/**
 * 
 */
package edu.utdallas.videoOnDemand.UserOperationMngtSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.Comment;
import edu.utdallas.videoOnDemand.entities.Transaction;
import edu.utdallas.videoOnDemand.services.ServiceException;
import edu.utdallas.videoOnDemand.transactionSvc.TransactionDTO;

/**
 * @author lei
 *
 */
public class CommentDTOValidator {

	static public Comment convert(CommentDTO commentDTO) throws ServiceException
	{		
		Comment comment = new Comment();
		
		comment.setMovieID(commentDTO.getMovieID());
		comment.setUserID(commentDTO.getUserID());
		comment.setCommentText(commentDTO.getCommentText());
		comment.setDate(commentDTO.getDate());
		
		return comment;
	}
	
	static public List<CommentDTO> covertToDTO(List<Comment> comment) 
	{
		List<CommentDTO> results = new ArrayList<CommentDTO>();
		for(Comment com: comment) {
			CommentDTO commentDTO = new CommentDTO(com);
			results.add(commentDTO);
		}
		return results;
	}
}
