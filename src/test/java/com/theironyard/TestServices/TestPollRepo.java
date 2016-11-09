package com.theironyard.TestServices;

import com.theironyard.Entities.Poll;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by joshuakeough on 11/8/16.
 */
public interface TestPollRepo extends CrudRepository<Poll, Integer> {
}
