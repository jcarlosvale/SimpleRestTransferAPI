package simpleRestTransferAPI.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import simpleRestTransferAPI.exception.*;
import simpleRestTransferAPI.entity.Account;
import simpleRestTransferAPI.dto.TransferDto;
import simpleRestTransferAPI.repository.AccountRepository;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TransferServiceTest {

    TransferService service;
    AccountRepository mockAccountrepository;

    @Before
    public void setup() {
        mockAccountrepository = mock(AccountRepository.class);
        service = new TransferService(mockAccountrepository);
    }

    @Test(expected = TransferDtoNullException.class)
    public void testNullTransfer() {
        service.transfer(null);
    }

    @Test(expected = SenderAccountIdNullException.class)
    public void testNullSenderAccountId() {
        TransferDto mockTransferDto = new TransferDto(null, 0L, BigDecimal.ZERO);
        service.transfer(mockTransferDto);
    }

    @Test(expected = ReceiverAccountIdNullException.class)
    public void testNullReceiverAccountId() {
        TransferDto mockTransferDto = new TransferDto(0L, null, BigDecimal.ZERO);
        service.transfer(mockTransferDto);
    }

    @Test(expected = InvalidAmountException.class)
    public void testNullAmount() {
        TransferDto mockTransferDto = new TransferDto(0L, 0L, null);
        service.transfer(mockTransferDto);
    }

    @Test(expected = InvalidAmountException.class)
    public void testNegativeAmount() {
        TransferDto mockTransferDto = new TransferDto(0L, 0L, new BigDecimal(-0.09));
        service.transfer(mockTransferDto);
    }

    @Test(expected = AccountNotFoundException.class)
    public void testAccountNotFound() {
        TransferDto mockTransferDto = new TransferDto(0L, 0L, new BigDecimal(0.01));
        service.transfer(mockTransferDto);
    }

    @Test(expected = NotEnoughBalanceException.class)
    public void testNotEnoughBalance() {
        Long senderAccountId = 1L;
        Long receiverAccountId = 2L;
        BigDecimal amount = BigDecimal.ONE;
        Account senderAccount = new Account(senderAccountId, amount.subtract(new BigDecimal(0.01)));
        Account receiverAccount = new Account(receiverAccountId, BigDecimal.ZERO);

        TransferDto mockTransferDto = new TransferDto(senderAccountId, receiverAccountId, amount);
        when(mockAccountrepository.findBy(senderAccountId)).thenReturn(senderAccount);
        when(mockAccountrepository.findBy(receiverAccountId)).thenReturn(receiverAccount);

        service.transfer(mockTransferDto);
    }

    @Test
    public void testTransfer() {
        Long senderAccountId = 1L;
        Long receiverAccountId = 2L;
        BigDecimal amount = BigDecimal.ONE;

        Account senderAccount = mock(Account.class);
        when(senderAccount.getBalance()).thenReturn(amount);

        Account receiverAccount = mock(Account.class);
        when(receiverAccount.getBalance()).thenReturn(BigDecimal.ZERO);

        TransferDto mockTransferDto = new TransferDto(senderAccountId, receiverAccountId, amount);
        when(mockAccountrepository.findBy(senderAccountId)).thenReturn(senderAccount);
        when(mockAccountrepository.findBy(receiverAccountId)).thenReturn(receiverAccount);

        service.transfer(mockTransferDto);

        ArgumentCaptor<BigDecimal> captorSender = ArgumentCaptor.forClass(BigDecimal.class);
        verify(senderAccount).setBalance(captorSender.capture());
        assertEquals(captorSender.getValue(), BigDecimal.ZERO);

        ArgumentCaptor<BigDecimal> captorReceiver = ArgumentCaptor.forClass(BigDecimal.class);
        verify(receiverAccount).setBalance(captorReceiver.capture());
        assertEquals(captorReceiver.getValue(), BigDecimal.ONE);

    }
}