package simpleRestTransferAPI.repository;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import simpleRestTransferAPI.exception.AccountNotFoundException;
import simpleRestTransferAPI.exception.NotEnoughBalanceException;
import simpleRestTransferAPI.entity.Account;
import simpleRestTransferAPI.dto.TransferDto;
import simpleRestTransferAPI.service.TransferService;

import javax.inject.Inject;
import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(CdiTestRunner.class)
public class AccountRepositoryTest extends EntityManagerTest {

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private TransferService transferService;

    @Test
    public void testInjection() {
        assertNotNull(accountRepository);
    }

    @Test
    public void testTransfer() {
        Account accountSender = new Account(1L, BigDecimal.ONE);
        Account accountReceiver = new Account(2L, BigDecimal.ZERO);

        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.ONE);
        transferDto.setSenderAccountId(accountSender.getId());
        transferDto.setReceiverAccountId(accountReceiver.getId());

        insertOrUpdateData(accountSender, accountReceiver);
        transferService.transfer(transferDto);

        BigDecimal senderBalance = accountRepository.findBy(1L).getBalance();
        BigDecimal receiverBalance = accountRepository.findBy(2L).getBalance();

        assertEquals(BigDecimal.ZERO, senderBalance);
        assertEquals(BigDecimal.ONE, receiverBalance);
    }

    @Test
    public void testNotEnoughBalanceException() {
        Account accountSender = new Account(1L, BigDecimal.ZERO);
        Account accountReceiver = new Account(2L, BigDecimal.ZERO);

        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.ONE);
        transferDto.setSenderAccountId(accountSender.getId());
        transferDto.setReceiverAccountId(accountReceiver.getId());

        insertOrUpdateData(accountSender, accountReceiver);
        try {
            transferService.transfer(transferDto);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof NotEnoughBalanceException);
        }

        BigDecimal senderBalance = accountRepository.findBy(1L).getBalance();
        BigDecimal receiverBalance = accountRepository.findBy(2L).getBalance();

        assertEquals(BigDecimal.ZERO, senderBalance);
        assertEquals(BigDecimal.ZERO, receiverBalance);
    }

    @Test
    public void testAccountNotFoundException() {
        Account accountSender = new Account(1L, BigDecimal.ONE);
        Account accountReceiver = new Account(2L, BigDecimal.ZERO);

        TransferDto transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.ONE);
        transferDto.setSenderAccountId(accountSender.getId());
        transferDto.setReceiverAccountId(accountReceiver.getId());

        insertOrUpdateData(accountSender);
        try {
            transferService.transfer(transferDto);
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof AccountNotFoundException);
        }

        BigDecimal senderBalance = accountRepository.findBy(1L).getBalance();

        assertEquals(BigDecimal.ONE, senderBalance);
    }

    private void insertOrUpdateData(Account ... accounts) {
        for (Account account : accounts) {
            Account accountFromDb = accountRepository.findBy(account.getId());
            if (null == accountFromDb) {
                accountRepository.saveAndFlush(account);
            } else {
                accountFromDb.setBalance(account.getBalance());
                accountRepository.saveAndFlush(accountFromDb);
            }
        }
    }
}