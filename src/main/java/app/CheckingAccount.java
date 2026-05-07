package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản vãng lai.
 */
public class CheckingAccount extends Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Khởi tạo tài khoản vãng lai.
   *
   * @param accountNumber số tài khoản
   * @param balance       số dư ban đầu
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  /**
   * Thực hiện nạp tiền vào tài khoản vãng lai.
   *
   * @param amount số tiền nạp
   */
  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction t = new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(t);
      LOGGER.info("Nạp tiền tài khoản vãng lai thành công: số tiền {}, số dư cuối {}", amount, finalBalance);
    } catch (BankException e) {
      LOGGER.warn("Lỗi nạp tiền tài khoản vãng lai: {}", e.getMessage());
    }
  }

  /**
   * Thực hiện rút tiền từ tài khoản vãng lai.
   *
   * @param amount số tiền rút
   */
  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      double finalBalance = getBalance();
      Transaction t = new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          finalBalance);
      addTransaction(t);
      LOGGER.info("Rút tiền tài khoản vãng lai thành công: số tiền {}, số dư cuối {}", amount, finalBalance);
    } catch (BankException e) {
      LOGGER.warn("Lỗi rút tiền tài khoản vãng lai: {}", e.getMessage());
    }
  }
}
