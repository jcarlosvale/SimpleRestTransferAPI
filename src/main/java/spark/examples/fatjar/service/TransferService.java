package spark.examples.fatjar.service;

import spark.examples.fatjar.domain.entity.Account;
import spark.examples.fatjar.dto.TransferDto;
import spark.examples.fatjar.repository.AccountRepository;

import javax.inject.Inject;
import java.math.BigDecimal;

public class TransferService {

    @Inject
    AccountRepository accountRepository;

    public boolean transfer(TransferDto transferDto) {
        Account accountOrigin = accountRepository.findById(transferDto.getOriginAccountId());
        Account accountDestiny = accountRepository.findById(transferDto.getDestinyAccountId());
        BigDecimal amount = transferDto.getAmount();

        accountOrigin.setBalance(accountOrigin.getBalance().subtract(amount));
        accountDestiny.setBalance(accountDestiny.getBalance().add(amount));

        return true;
    }
}
