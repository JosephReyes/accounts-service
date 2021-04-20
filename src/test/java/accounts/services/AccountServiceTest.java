package accounts.services;

import accounts.domain.Account;
import accounts.domain.Transaction;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
  @InjectMocks
  AccountService service;

  @Test
  public void shouldRetrieveEmptyListIfNoAccounts() {
    List<Account> result = service.getAllAccounts();
    assertEquals(result, Collections.EMPTY_LIST);
  }

  @Test
  public void shouldRetrieveAllAccountsWhenAccountsExist() {
    service.addAccount("some-name", "some-email");

    List<Account> result = service.getAllAccounts();
    assertEquals(result.get(0).getName(),"some-name");
    assertEquals(result.get(0).getEmail(), "some-email");
  }

  @Test
  public void shouldGetASingleAccount() {
    service.addAccount("some-name", "some-email");
    service.addAccount("some-other-name", "some-other-email");

    Optional<Account> result = service.getAccount("0");
    if (result.isPresent()) {
      assertEquals(result.get().getId(), "0");
      assertEquals(result.get().getName(), "some-name");
      assertEquals(result.get().getEmail(), "some-email");
      assertEquals(result.get().getBalance(), "200.00");
    } else {
      fail("Should return account");
    }
  }

  @Test
  public void shouldReturnTransactionsForAccount() {
    Account account = service.addAccount("some-name", "some-email");
    account.addTransaction(new Transaction("1", Transaction.TransactionType.DEBIT, "3", "some-timestamp"));

    List<Transaction> result = service.getTransactions("0");
    assertEquals(result.get(0).getId(), "1");
    assertEquals(result.get(0).getType(), Transaction.TransactionType.DEBIT);
    assertEquals(result.get(0).getAmount(), "3");
    assertEquals(result.get(0).getTimestamp(), "some-timestamp");
  }

  @Test
  public void shouldReturnEmptyListOfTransactionsWhenAccountDoesNotExist() {
    Account account = service.addAccount("some-name", "some-email");
    account.addTransaction(new Transaction("1", Transaction.TransactionType.DEBIT, "3", "some-timestamp"));

    List<Transaction> result = service.getTransactions("5");
    assertEquals(result, Collections.EMPTY_LIST);
  }
}
