package app;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho tài khoản.
 */
public abstract class Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(Account.class);

  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  protected List<Transaction> transactionList;

  /**
   * Khởi tạo một tài khoản mới.
   *
   * @param accountNumber số tài khoản
   * @param balance       số dư ban đầu
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactionList = new ArrayList<>();
  }

  /**
   * Lấy số tài khoản.
   *
   * @return số tài khoản
   */
  public long getAccountNumber() {
    return accountNumber;
  }

  /**
   * Thiết lập số tài khoản.
   *
   * @param accountNumber số tài khoản mới
   */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Lấy số dư hiện tại của tài khoản.
   *
   * @return số dư
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Thiết lập số dư tài khoản.
   *
   * @param balance số dư mới
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Lấy danh sách giao dịch.
   *
   * @return danh sách các giao dịch
   */
  public List<Transaction> getTransactionList() {
    return transactionList;
  }

  /**
   * Thiết lập danh sách giao dịch.
   *
   * @param transactionList danh sách các giao dịch mới
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactionList = new ArrayList<>();
    } else {
      this.transactionList = transactionList;
    }
  }

  /**
   * Nạp tiền vào tài khoản.
   *
   * @param amount số tiền nạp
   */
  public abstract void deposit(double amount);

  /**
   * Rút tiền khỏi tài khoản.
   *
   * @param amount số tiền rút
   */
  public abstract void withdraw(double amount);

  /**
   * Thực hiện xử lý logic nạp tiền.
   *
   * @param amount số tiền cần nạp
   * @throws InvalidFundingAmountException nếu số tiền nạp không hợp lệ (<= 0)
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      LOGGER.warn("Quá trình nạp tiền thất bại: Số tiền không hợp lệ {}", amount);
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
    LOGGER.debug("Account {}: DoDepositing thành công {}", accountNumber, amount);
  }

  /**
   * Thực hiện xử lý logic rút tiền.
   *
   * @param amount số tiền cần rút
   * @throws InvalidFundingAmountException nếu số tiền rút không hợp lệ (<= 0)
   * @throws InsufficientFundsException    nếu số dư không đủ để rút
   */
  protected void doWithdrawing(double amount)
      throws InvalidFundingAmountException, InsufficientFundsException {
    if(amount <= 0)
    {
      LOGGER.warn("Quá trình rút tiền thất bại: Số tiền không hợp lệ {}", amount);
      throw new InvalidFundingAmountException(amount);
    }
    if(amount > balance)
    {
      LOGGER.warn("Quá trình rút tiền thất bại: Tài khoản không đủ số dư để rút {} (Số dư hiện tại: {})", amount, balance);
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
    LOGGER.debug("Account {}: DoWithdrawing thành công {}", accountNumber, amount);
  }

  /**
   * Thêm một giao dịch mới vào tài khoản.
   *
   * @param transaction giao dịch cần thêm
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactionList.add(transaction);
      LOGGER.info("Ghi nhận giao dịch hệ thống mới vào lịch sử tài khoản {}", accountNumber);
    } else {
      LOGGER.error("Giao dịch bị null, không thể thêm vào lịch sử của tài khoản {}", accountNumber);
      throw new IllegalArgumentException("Transaction truyền vào không được phép null đối với tài khoản " + accountNumber);
    }
  }

  /**
   * Trả về lịch sử các giao dịch của tài khoản dưới dạng chuỗi.
   *
   * @return lịch sử giao dịch
   */
  public String getTransactionHistory() {
    StringBuilder s = new StringBuilder("Lịch sử giao dịch của tài khoản " + accountNumber + ":\n");
    for (int i = 0; i < transactionList.size(); i++) {
        s.append(transactionList.get(i).getTransactionSummary());
        if (i < transactionList.size() - 1) {
            s.append("\n");
        }
    }
    return s.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (!(obj instanceof Account)) {
        return false;
    }
    Account other = (Account) obj;
    return this.accountNumber == other.accountNumber;
  }

  @Override
  public int hashCode() {
    return (int) (accountNumber ^ (accountNumber >>> 32));
  }
}
