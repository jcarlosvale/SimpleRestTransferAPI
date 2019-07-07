package simpleRestTransferAPI.service;

import simpleRestTransferAPI.entity.Account;
import simpleRestTransferAPI.repository.AccountRepository;

import javax.inject.Inject;
import java.math.BigDecimal;

public class UtilService {

    private final AccountRepository accountRepository;

    @Inject
    public UtilService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public String getAccounts() {
        String accounts = " ";
        for (Account account : accountRepository.findAll()) {
            accounts = accounts + "\n" + account;
        }
        return accounts;
    }

    public void persist() {
        BigDecimal amount = new BigDecimal(500);
        for (long id = 1; id <= 5; id++) {
            Account accountFromDb = accountRepository.findBy(id);
            if (null == accountFromDb) {
                accountRepository.saveAndFlush(new Account(id,amount));
            } else {
                accountFromDb.setBalance(amount);
                accountRepository.saveAndFlush(accountFromDb);
            }
        }
    }
}
