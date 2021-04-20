package accounts.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Account {
  private final String id;
  private final String name;
  private final String email;
  private final List<Transaction> transactions;
  private String balance;

  public Account(String id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.balance = "200.00";
    this.transactions = new ArrayList<>();
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getEmail() {
    return email;
  }

  public Boolean addTransaction(Transaction transaction) {
    this.transactions.add(transaction);
    return transactions.contains(transaction);
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }
}
