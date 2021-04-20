package accounts.services;

import accounts.domain.Account;
import accounts.domain.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransferService {
  @Autowired
  private AccountService accountService;

  // Prefer to throw exception as opposed to catching here as ideally the FE would handle the error
  public Boolean transferMoney(String from, String to, String amount) throws Exception {
    Optional<Account> fromAccount = accountService.getAccount(from);
    Optional<Account> toAccount = accountService.getAccount(to);

    if (fromAccount.isPresent() && toAccount.isPresent()) {
      String dateNow = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
      adjustBalance(fromAccount.get(), amount, Transaction.TransactionType.DEBIT, dateNow);
      adjustBalance(toAccount.get(), amount, Transaction.TransactionType.CREDIT, dateNow);
      return true;
    } else {
      throw new Exception("From or to account does not exist");
    }
  }

  private void adjustBalance(Account account, String amount, Transaction.TransactionType transactionType, String dateNow) throws Exception {
    BigDecimal currentBalance = NumberUtils.parseNumber(account.getBalance(), BigDecimal.class).setScale(2, RoundingMode.HALF_UP);
    BigDecimal decimalAmount = NumberUtils.parseNumber(amount, BigDecimal.class).setScale(2, RoundingMode.HALF_UP);
    Transaction transaction = new Transaction(String.valueOf(account.getTransactions().size()), transactionType, decimalAmount.toPlainString(), dateNow);

    switch (transactionType) {
      case DEBIT:
        if (currentBalance.compareTo(decimalAmount) < 0) {
          throw new Exception("Not enough funds!");
        } else {
          account.setBalance(currentBalance.subtract(decimalAmount).toPlainString());
          account.addTransaction(transaction);
          // TODO - call email service
        }
        break;
      case CREDIT:
        account.setBalance(currentBalance.add(decimalAmount).toPlainString());
        account.addTransaction(transaction);
        // TODO - call email service
        break;
    }

  }
}
