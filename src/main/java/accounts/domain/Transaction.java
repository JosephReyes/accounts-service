package accounts.domain;

public class Transaction {
  private String id;
  private TransactionType type;
  private String amount;
  private String timestamp;

  public enum TransactionType {
    DEBIT,
    CREDIT
  }

  public Transaction(String id, TransactionType type, String amount, String timestamp) {
    this.id = id;
    this.type = type;
    this.amount = amount;
    this.timestamp = timestamp;
  }

  public String getId() {
    return id;
  }

  public TransactionType getType() {
    return type;
  }

  public String getAmount() {
    return amount;
  }

  public String getTimestamp() {
    return timestamp;
  }
}
