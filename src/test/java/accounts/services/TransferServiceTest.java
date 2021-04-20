package accounts.services;

import accounts.domain.Account;
import accounts.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransferServiceTest {
  @InjectMocks
  private TransferService service;

  @Mock
  private AccountService accountService;

  @Mock
  private Account fromAccount;

  @Mock
  private Account toAccount;

  @Before
  public void setUp() {
    when(fromAccount.getBalance()).thenReturn("120.00");
    when(toAccount.getBalance()).thenReturn("120.00");
    when(accountService.getAccount("1")).thenReturn(Optional.of(fromAccount));
    when(accountService.getAccount("2")).thenReturn(Optional.of(toAccount));
  }

  @Test
  public void shouldDebitFromFromAccountAndAddTransaction() throws Exception {
    ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
    service.transferMoney("1","2", "10");

    verify(fromAccount).addTransaction(captor.capture());
    verify(fromAccount).setBalance("110.00");
    assertEquals(captor.getValue().getAmount(), "10.00");
  }

  @Test
  public void shouldCreditToToAccountAndAddTransaction() throws Exception {
    ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
    service.transferMoney("1","2", "10");

    verify(toAccount).addTransaction(captor.capture());
    verify(toAccount).setBalance("130.00");
    assertEquals(captor.getValue().getAmount(), "10.00");
  }

  @Test
  public void shouldErrorIfNoAccount() {
    when(accountService.getAccount("1")).thenReturn(Optional.empty());
    when(accountService.getAccount("2")).thenReturn(Optional.empty());
    try {
      service.transferMoney("1", "2", "some-amount");
      fail("Should not get here");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "From or to account does not exist");
    }
  }

  @Test
  public void shouldErrorIfNoFromAccount() {
    when(accountService.getAccount("1")).thenReturn(Optional.empty());

    try {
      service.transferMoney("1", "2", "some-amount");
      fail("Should not get here");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "From or to account does not exist");
    }
  }

  @Test
  public void shouldErrorIfNoToAccount() {
    when(accountService.getAccount("2")).thenReturn(Optional.empty());

    try {
      service.transferMoney("1", "2", "some-amount");
      fail("Should not get here");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "From or to account does not exist");
    }
  }

  @Test
  public void shouldErrorIfNotEnoughFundsToTransfer() {
    try {
      service.transferMoney("1", "2", "130");
      fail("Should not get here");
    } catch (Exception e) {
      assertEquals(e.getMessage(), "Not enough funds!");
    }
  }
}
