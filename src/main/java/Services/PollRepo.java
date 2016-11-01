package Services;

import Entities.Poll;

import Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

/**
 * Created by joshuakeough on 10/31/16.
 */
public interface PollRepo extends CrudRepository<Poll, Integer> {
    ArrayList<Poll> findByUser(User user);
}
