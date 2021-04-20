package accounts.resolvers;

import accounts.domain.Account;
import accounts.domain.Transaction;
import accounts.services.AccountService;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountsResolver implements GraphQLQueryResolver {
  private final AccountService accountService;

  public AccountsResolver(AccountService accountService) {
    this.accountService = accountService;
  }

  public List<Account> accounts() {
    return accountService.getAllAccounts();
  }

  public List<Transaction> transactions(String id) {
    return accountService.getTransactions(id);
  }

}
