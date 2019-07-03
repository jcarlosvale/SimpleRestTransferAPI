package spark.examples.fatjar.repository;

import spark.examples.fatjar.domain.entity.Account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository {

    private final Map<Long,Account> accounts = new HashMap<>();

    public AccountRepository(){
        accounts.put(1L,new Account(1L, new BigDecimal(100)));
        accounts.put(2L,new Account(2L, new BigDecimal(100)));
        accounts.put(3L,new Account(3L, new BigDecimal(100)));
        accounts.put(4L,new Account(4L, new BigDecimal(100)));
        accounts.put(5L,new Account(5L, new BigDecimal(100)));
    }

    public Account findById(long accountId) {
        return accounts.get(accountId);
    }
}
