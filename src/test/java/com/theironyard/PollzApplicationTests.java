package com.theironyard;

import com.theironyard.Entities.Poll;
import com.theironyard.Entities.Result;
import com.theironyard.Entities.User;
import com.theironyard.TestServices.TestPollRepo;
import com.theironyard.TestServices.TestResultRepo;
import com.theironyard.TestServices.TestUserRepo;
import com.theironyard.Utilities.PasswordStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PollzApplicationTests {

	@Autowired
	TestPollRepo polls;

	@Autowired
	TestUserRepo users;

	@Autowired
	TestResultRepo results;





	public Connection startConnection() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/pollztest");
//		users.deleteAll();
//		results.deleteAll();
//		polls.deleteAll();
		return conn;
	}

	public User user = new User("Josh", "123");
//	public Poll poll = new Poll("testPoll", "test", "a", "b",null,null,null,null,user);

//	@Test
//	public void validation() {
//
//		pollValidation("testPoll", "test", "a", "b", user);
//		resultValidation("test", 1, 1, "a");
//	}

	@Test
	public void UserTest() throws PasswordStorage.CannotPerformOperationException {
		userValidation("josh","123");
		results.deleteAll();
		polls.deleteAll();
		users.deleteAll();

	}

	@Test
	public void updateUserTest() throws PasswordStorage.CannotPerformOperationException {
		results.deleteAll();
		polls.deleteAll();
		users.deleteAll();
		User user = new User("josh", "123");
		User user1 = new User("josh", "1234");
		users.save(user);
		users.save(user1);
		Assert.assertTrue(users.count() == 2);
		ArrayList<User> list = (ArrayList)users.findAllByName("josh");
		Assert.assertTrue(list.size() == 2);
		user1.setName("john");
		users.save(user1);
		list = (ArrayList)users.findAllByName("josh");
		Assert.assertTrue(list.size() == 1);
		list = (ArrayList)users.findAll();
		users.delete(list.get(0).getId());
		list = (ArrayList)users.findAll();
		Assert.assertTrue(list.size()==1);

	}





	public void userValidation(String name, String password) {
		User user1 = new User(name, password);
		Assert.assertTrue(user1.getName() != null && user1.getPassword() != null);
	}


//	public void pollValidation(String name, String topic, String answerA, String answerB, User user){
//		Poll poll = new Poll(name, topic, answerA,answerB,null,null,null,null,user);
//		Assert.assertTrue(poll.getPollName() != null);
//	}

	public void resultValidation(String topic, int usersid, int pollid, String answer) {
		Result result = new Result(topic, usersid, pollid, answer);
		Assert.assertTrue(result.getAnswer() != null);
	}

	public void addToDb(User user) throws PasswordStorage.CannotPerformOperationException {
		users.save(user);

	}

//	public void updateUser() {
//		User user = users.findById(1);
//		users.save(new User(user.getName(),user.getPassword()));
//		User user2 = users.findById(2);
//		user2.setName(user2.getName() + "a");
//		users.save(user2);
//		Assert.assertTrue(!users.findOne(1).getName().equals(users.findOne(2).getName()));
//	}







}
