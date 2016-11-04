package com.theironyard.Services;


import com.theironyard.Entities.Result;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ResultRepo extends CrudRepository<Result, Integer> {
    Result findFirstByPollId(Integer id);

    ArrayList<Result> findAllByPollId(Integer id);
}
