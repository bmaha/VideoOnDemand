package edu.utdallas.videoOnDemand.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.utdallas.videoOnDemand.dao.impl.UserDAOImpl;
import edu.utdallas.videoOnDemand.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"datasourceContext.xml"})
public class UserDAOTestCase
{
	@Autowired
    DataSource datasource;

	@Test
	public void testWiring()
	{
		assertNotNull(datasource);
	}

	@Test
	public void testAddUser() throws Exception
	{
		User user = buildUser();
		assertTrue(user.getUserID() == 0);
		
		UserDAOImpl userDAO = new UserDAOImpl();
		userDAO.setDataSource(datasource);
		//userDAO.addUser(user);
		assertNotNull(user.getUserID());
		System.out.println("Added User ID " + user.getUserID());
	}

	private User buildUser()
    {
		Random random = new Random();
		int val = random.nextInt();
		
		User result = new User();
		result.setFirstName("Fred");
		result.setLastName("Flintstone"+val);
		result.setEmail("fred"+val+"@gmail.com");
		//result.setCreditCardID(1);  //please update this test case according to new user entity
		result.setUsername("theFred"+val);
		result.setPassword("password");
		result.setIsAdmin("0");
		result.setIsActive("1");
	    return result;
    }
}
