package accounts.resolvers;

import accounts.domain.Account;
import accounts.services.AccountService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddAccountResolver implements GraphQLMutationResolver {
  @Autowired
  private AccountService accountService;

  public Account addAccount(AddAccountParams params) {
    return accountService.addAccount(params.getName(), params.getEmail());
  }

}

class AddAccountParams {
  private String name;
  private String email;

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }
}