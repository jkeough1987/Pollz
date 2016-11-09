package com.theironyard.TestServices;

import com.theironyard.Entities.Result;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by joshuakeough on 11/8/16.
 */
public interface TestResultRepo extends CrudRepository<Result, Integer> {
}
