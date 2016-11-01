package com.theironyard.Services;


import com.theironyard.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Integer> {
    User findFirstByName(String name);
}
