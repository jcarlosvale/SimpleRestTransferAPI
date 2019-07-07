package simpleRestTransferAPI.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import simpleRestTransferAPI.entity.Account;

@Repository
public interface AccountRepository extends EntityRepository<Account, Long> {
}
