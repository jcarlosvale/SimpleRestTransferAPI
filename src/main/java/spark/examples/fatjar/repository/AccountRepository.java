package spark.examples.fatjar.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import spark.examples.fatjar.domain.entity.Account;

@Repository
public interface AccountRepository extends EntityRepository<Account, Long> {
}
