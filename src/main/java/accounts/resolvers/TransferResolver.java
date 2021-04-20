package accounts.resolvers;

import accounts.services.TransferService;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferResolver implements GraphQLMutationResolver {
  @Autowired
  private TransferService transferService;

  public Boolean transfer(TransferParams params) throws Exception {
    return transferService.transferMoney(params.getFrom(), params.getTo(), params.getAmount());
  }
}

class TransferParams {
  private String from;
  private String to;
  private String amount;

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getAmount() {
    return amount;
  }

}