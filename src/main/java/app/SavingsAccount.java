package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tài khoản tiết kiệm - Lớp này thực thi các quy định về nạp và rút tiền.
 */
public class SavingsAccount extends Account {
  private static final Logger LOGGER = LoggerFactory.getLogger(SavingsAccount.class);
  private static final double MAX_WITHDRAW = 1000.0;
  private static final double MIN_BALANCE = 5000.0;

  /**
   * Khởi tạo tài khoản tiết kiệm.
   *
   * @param n số tài khoản
   * @param b số dư ban đầu
   */
  public SavingsAccount(long n, double b) {
    super(n, b);
  }

  /**
   * Thực hiện nạp tiền vào tài khoản tiết kiệm.
   *
   * @param amount số tiền nạp
   */
  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      double finalBalance = getBalance();
      Transaction transaction = new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);
      LOGGER.info("Nạp tiền tài khoản tiết kiệm thành công: số tiền {}, số dư cuối {}", amount, finalBalance);
    } catch (InvalidFundingAmountException e) {
      LOGGER.warn("Lỗi nạp tiền tài khoản tiết kiệm: {}", e.getMessage());
    }
  }

  /**
   * Thực hiện rút tiền từ tài khoản tiết kiệm.
   *
   * @param amount số tiền rút
   */
  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAW) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MIN_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      double finalBalance = getBalance();

      Transaction transaction = new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS, amount, initialBalance, finalBalance);
      addTransaction(transaction);
      LOGGER.info("Rút tiền tài khoản tiết kiệm thành công: số tiền {}, số dư cuối {}", amount, finalBalance);
    } catch (InvalidFundingAmountException | InsufficientFundsException e) {
      LOGGER.warn("Lỗi rút tiền tài khoản tiết kiệm: {}", e.getMessage(), e);
    }
  }
}