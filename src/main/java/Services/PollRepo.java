package Services;

import Entities.Poll;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by joshuakeough on 10/31/16.
 */
public interface PollRepo extends CrudRepository<Poll, Integer> {
}
