package simpleRestTransferAPI.service;


import simpleRestTransferAPI.controller.exception.*;
import simpleRestTransferAPI.domain.entity.Account;
import simpleRestTransferAPI.dto.TransferDto;
import simpleRestTransferAPI.repository.AccountRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;

public class TransferService {

    private final AccountRepository accountRepository;

    @Inject
    public TransferService(final AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional
    public void transfer(final TransferDto transferDto) {
        validateTransferDto(transferDto);

        try {
            final Account accountSender = accountRepository.findBy(transferDto.getSenderAccountId());
            validateAccount(transferDto.getSenderAccountId(), accountSender);

            final Account accountReceiver = accountRepository.findBy(transferDto.getReceiverAccountId());
            validateAccount(transferDto.getReceiverAccountId(), accountReceiver);

            final BigDecimal amount = transferDto.getAmount();

            if (accountSender.getBalance().compareTo(amount) >= 0) {
                accountSender.setBalance(accountSender.getBalance().subtract(amount));
                accountReceiver.setBalance(accountReceiver.getBalance().add(amount));

                accountRepository.save(accountSender);
                accountRepository.save(accountReceiver);
            } else {
                throw new NotEnoughBalanceException();
            }
        } finally {
            accountRepository.flush();
        }

    }

    private void validateAccount(Long accountId, Account account) {
        if (null == account) {
            throw new AccountNotFoundException(accountId);
        }
    }

    private void validateTransferDto(TransferDto transferDto) {
        if (null == transferDto) {
            throw new TransferDtoNullException();
        }
        if (null == transferDto.getSenderAccountId()) {
            throw new SenderAccountIdNullException();
        }
        if (null == transferDto.getReceiverAccountId()) {
            throw new ReceiverAccountIdNullException();
        }
        if (null == transferDto.getAmount() || transferDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException();
        }
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