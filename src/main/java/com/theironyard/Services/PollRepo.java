package com.theironyard.Services;

import com.theironyard.Entities.Poll;

import com.theironyard.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by joshuakeough on 10/31/16.
 */
public interface PollRepo extends CrudRepository<Poll, Integer> {
    //update users set is_admin = true where id = [user id];

    ArrayList<Poll> findByUser(User user);
    ArrayList<Poll> findAllByUserId(int id);

}
