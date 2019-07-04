package spark.examples.fatjar.service;

import lombok.extern.java.Log;
import spark.examples.fatjar.domain.entity.Account;
import spark.examples.fatjar.dto.TransferDto;
import spark.examples.fatjar.repository.AccountRepository;

import javax.inject.Inject;
import java.math.BigDecimal;

@Log
public class TransferService {


    @Inject
    AccountRepository accountRepository;

    public boolean transfer(TransferDto transferDto) {
        Account accountOrigin = accountRepository.findBy(transferDto.getOriginAccountId());
        Account accountDestiny = accountRepository.findBy(transferDto.getDestinyAccountId());

        if (accountOrigin == null) {
            accountOrigin = accountRepository.save(new Account(new BigDecimal(100)));
        }

        if (accountDestiny == null) {
            accountDestiny = accountRepository.save(new Account(new BigDecimal(100)));
        }

        listAccounts();

        BigDecimal amount = transferDto.getAmount();

        accountOrigin.setBalance(accountOrigin.getBalance().subtract(amount));
        accountDestiny.setBalance(accountDestiny.getBalance().add(amount));

        listAccounts();

        return true;
    }

    public void listAccounts() {
        log.info("******Accounts***********");
       // System.out.println("******Accounts***********");
        //accountRepository.findAll().stream().forEach(System.out::println);
        accountRepository.findAll().stream().forEach((s) -> log.info(s.toString()));
    }

    public String getAccounts() {
        String accounts = " ";
        for (Account account : accountRepository.findAll()) {
            accounts = accounts + "\n" + account;
        }
        return accounts;
    }
}
