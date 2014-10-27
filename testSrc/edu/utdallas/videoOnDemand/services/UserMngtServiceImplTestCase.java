package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.CreditCard;
import edu.utdallas.videoOnDemand.userManagementSvc.CreditCardDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "serviceContext.xml" })
public class UserMngtServiceImplTestCase {
	@Autowired
	private UserManagementService userManagementSvc;

	@Test
	public void testWiring() {
		assertNotNull(userManagementSvc);
	}

	// @Test
	// public void testAddUser() throws Exception {
	// UserDTO userDTO = buildUserDTO();
	// //userManagementSvc.addUser(userDTO);
	// }
	//
	// @Test
	// public void validateUser() throws Exception {
	//
	// }
	//
	// @Test
	// public void retrieveUserInfo_1() {
	//
	// }
	//
	// @Test
	// public void retrieveUserInfo_2() {
	//
	// }
	//
	// @Test
	// public void updateUser() throws Exception {
	// UserDTO userDTO = buildNewUserDTOForUpdate();
	// userManagementSvc.updateUser(userDTO);
	// ;
	// // assertNotNull(userDTO.getUserID());
	// System.out.println("Update User ID " + userDTO.getUserID());
	// }
	//
	// @Test
	// public void retrieveAllUsers() throws Exception {
	// List<UserDTO> userList = userManagementSvc.retrieveAllUsers();
	// System.out.println("testRetrieveAllUsers");
	// }
	//
	// @Test
	// public void login() throws Exception {
	// String username = "lei";
	// String password = "lei";
	// boolean login = userManagementSvc.login(username, password);
	// System.out.println("test--Login for " + username + " with password:"
	// + password + " and the login result: "
	// + Boolean.toString(login));
	// }
	//
	// @Test
	// public void authenticate() {
	//
	// }
	//
	// private UserDTO buildUserDTO() {
	// Random random = new Random();
	// int val = random.nextInt();
	//
	// UserDTO result = new UserDTO();
	// result.setFirst_name("Fred");
	// result.setLast_name("Flintstone" + val);
	// result.setEmail("fred" + val + "@gmail.com");
	// result.setUsername("theFred" + val);
	// result.setPassword("password");
	// result.setIsAdmin("0");
	// result.setIsActive("1");
	// return result;
	// }
	//
	// private UserDTO buildNewUserDTOForUpdate() {
	// Random random = new Random();
	// int val = random.nextInt();
	//
	// UserDTO result = new UserDTO();
	// result.setFirst_name("Fred");
	// result.setLast_name("Flintstone" + val);
	// result.setEmail("fred" + val + "@gmail.com");
	// result.setUsername("theFred" + val);
	// result.setPassword("password");
	// result.setIsAdmin("0");
	// result.setIsActive("1");
	//
	// result.setUserID(new Long(2));
	// return result;
	// }
	@Test
	public void validateCreditCardTest() throws DAOException, ServiceException {
		CreditCardDTO card = new CreditCardDTO();
		card.setName("KEFEI MsA");
		card.setCreditcardNumber("1234567891011122");
		card.setSecurityCode("113");
		boolean flag = userManagementSvc.validateCreditCard(card);
		if (flag)
			System.out.println("==============true=================");
		else
			System.out.println("===================false====================");
	}
}
