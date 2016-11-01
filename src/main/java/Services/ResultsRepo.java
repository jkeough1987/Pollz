package Services;


import Entities.Results;
import org.springframework.data.repository.CrudRepository;

public interface ResultsRepo extends CrudRepository<Results, Integer> {
}
