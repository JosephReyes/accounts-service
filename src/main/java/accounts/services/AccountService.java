package accounts.services;

import accounts.domain.Account;
import accounts.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {
  List<Account> accounts = new ArrayList<>();

  public List<Account> getAllAccounts() {
    return accounts;
  }

  public Account addAccount(String name, String email) {
    Account account = new Account(String.valueOf(accounts.size()), name, email);
    accounts.add(account);
    return account;
  }

  public Optional<Account> getAccount(String id) {
    return accounts.stream().filter(account -> Objects.equals(account.getId(), id)).findFirst();
  }

  public List<Transaction> getTransactions(String id) {
    Optional<Account> account = this.getAccount(id);
    if (account.isPresent()) {
      return account.get().getTransactions();
    } else {
      return Collections.emptyList();
    }
  }
}
