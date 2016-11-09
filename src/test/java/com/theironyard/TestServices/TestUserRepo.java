package com.theironyard.TestServices;

import com.theironyard.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by joshuakeough on 11/8/16.
 */
public interface TestUserRepo extends CrudRepository<User, Integer> {
    User findById(int i);

    ArrayList<User> findAllByName(String name);
}
