/**
 * 
 */
package edu.utdallas.videoOnDemand.userManagementSvc;

import java.util.ArrayList;
import java.util.List;

import edu.utdallas.videoOnDemand.entities.User;
import edu.utdallas.videoOnDemand.services.ServiceException;

/**
 * @author Lei Cui;
 * @date 6/27/2014;
 * @version 1;
 * @job UserDTOValidator;
 */

public class UserDTOValidator {

	static public User convert(UserDTO userDTO) throws ServiceException
	{
		// TODO Validate the contents of the DTO
		
		User user = new User();
		
		user.setUserID(userDTO.getUserID());
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setFirstName(userDTO.getFirst_name());
		user.setLastName(userDTO.getLast_name());
		user.setEmail(userDTO.getEmail());
		user.setIsActive(userDTO.getIsActive());
		user.setIsAdmin(userDTO.getIsAdmin());
		
		return user;
	}
	
	static public List<UserDTO> covertToDTO(List<User> users) 
	{
		List<UserDTO> results = new ArrayList<UserDTO>();
		for(User user: users) {
			UserDTO userDTO = new UserDTO(user);
			results.add(userDTO);
		}
		return results;
	}
	
	static public UserDTO convert(User user) throws ServiceException
	{
		// TODO Validate the contents of the DTO
		
		UserDTO userDTO = new UserDTO();
		
		userDTO.setUserID(user.getUserID());
		userDTO.setUsername(user.getUsername());
		userDTO.setPassword(user.getPassword());
		userDTO.setFirst_name(user.getFirstName());
		userDTO.setLast_name(user.getLastName());
		userDTO.setEmail(user.getEmail());
		userDTO.setIsActive(user.getIsActive());
		userDTO.setIsAdmin(user.getIsAdmin());
		
		return userDTO;
	}
}
