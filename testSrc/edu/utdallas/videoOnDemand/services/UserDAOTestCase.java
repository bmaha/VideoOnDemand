package edu.utdallas.videoOnDemand.services;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.UserDAO;
import edu.utdallas.videoOnDemand.dao.impl.DAOException;
import edu.utdallas.videoOnDemand.entities.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "serviceContext.xml" })
public class UserDAOTestCase {
	@Autowired
	private UserDAO userDAO;

	@Test
	public void testWiring() {
		assertNotNull(userDAO);
	}

	// @Test
	// public void testAddUser() throws Exception {
	// User user = buildUser();
	// assertTrue(user.getUserID() == null);
	// //userDAO.addUser(user);
	// assertNotNull(user.getUserID());
	// System.out.println("Added User ID " + user.getUserID());
	// }
	//
	// @Test
	// public void testRetrieveAllUsers() throws Exception {
	// List<User> userList = userDAO.retrieveAllUsers();
	// System.out.println("testRetrieveAllUsers");
	// }
	//
	// @Test
	// public void testUpdateUser() throws Exception {
	// User user = buildNewUserForUpdate();
	// userDAO.updateUser(user);
	// ;
	// assertNotNull(user.getUserID());
	// System.out.println("Update User ID " + user.getUserID());
	// }
	//
	// @Test
	// public void Login() throws Exception {
	// String username = "lei";
	// String password = "lei";
	// userDAO.login(username, password);
	// System.out.println("test--Login for " + username + " with password:"
	// + password);
	// }
	//
	// private User buildUser() {
	// Random random = new Random();
	// int val = random.nextInt();
	//
	// User result = new User();
	// result.setFirstName("Fred");
	// result.setLastName("Flintstone" + val);
	// result.setEmail("fred" + val + "@gmail.com");
	// result.setUsername("theFred" + val);
	// result.setPassword("password");
	// result.setIsAdmin("0");
	// result.setIsActive("1");
	// return result;
	// }
	//
	// private User buildNewUserForUpdate() {
	// Random random = new Random();
	// int val = random.nextInt();
	//
	// User result = new User();
	// result.setFirstName("Fred");
	// result.setLastName("Flintstone" + val);
	// result.setEmail("fred" + val + "@gmail.com");
	// result.setUsername("theFred" + val);
	// result.setPassword("password");
	// result.setIsAdmin("0");
	// result.setIsActive("1");
	//
	// result.setUserID(new Long(1));
	// return result;
	// }
	@Test
	public void validateCreditCardTest() throws DAOException {
		CreditCard card = new CreditCard();
		card.setName("KEFEI MA");
		card.setCreditcardNumber("1234567891011122");
		card.setSecurityCode("113");
		boolean flag = userDAO.validateCreditCard(card);
		if (flag)
			System.out.println("==============true=================");
		else
			System.out.println("===================false====================");
	}

}
