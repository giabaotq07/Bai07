package app;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho một giao dịch.
 */
public class Transaction {
  private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Khởi tạo một giao dịch mới.
   *
   * @param type           loại giao dịch
   * @param amount         số tiền giao dịch
   * @param initialBalance số dư trước khi giao dịch
   * @param finalBalance   số dư sau khi giao dịch
   */
  public Transaction(int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
    LOGGER.debug("Khởi tạo Transaction mới: type={}, amount={}, initialBalance={}, finalBalance={}",
                 type, amount, initialBalance, finalBalance);
  }

  /**
   * Lấy loại giao dịch.
   *
   * @return mã loại giao dịch
   */
  public int getType() {
    return type;
  }

  /**
   * Thiết lập loại giao dịch.
   *
   * @param type mã loại giao dịch
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Lấy số tiền giao dịch.
   *
   * @return số tiền giao dịch
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Thiết lập số tiền giao dịch.
   *
   * @param amount số tiền giao dịch
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Lấy số dư ban đầu.
   *
   * @return số dư ban đầu
   */
  public double getInitialBalance() {
    return initialBalance;
  }

  /**
   * Thiết lập số dư ban đầu.
   *
   * @param initialBalance số dư ban đầu
   */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /**
   * Lấy số dư cuối cùng.
   *
   * @return số dư cuối cùng
   */
  public double getFinalBalance() {
    return finalBalance;
  }

  /**
   * Thiết lập số dư cuối cùng.
   *
   * @param finalBalance số dư sau giao dịch
   */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Lấy chuỗi mô tả loại giao dịch từ mã số.
   *
   * @param typeId mã số loại giao dịch
   * @return chuỗi mô tả tiếng Việt
   */
  public static String getTypeString(int typeId) {
    switch (typeId) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Lấy tóm tắt chi tiết về giao dịch.
   *
   * @return chuỗi biểu diễn thông tin giao dịch
   */
  public String getTransactionSummary() {
    String typeDesc = getTypeString(type);
    String initialFormatted = String.format(Locale.US, "%.2f", initialBalance);
    String amountFormatted = String.format(Locale.US, "%.2f", amount);
    String finalFormatted = String.format(Locale.US, "%.2f", finalBalance);

    return String.format(
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%s. Số tiền: $%s. Số dư cuối: $%s.",
        typeDesc,
        initialFormatted,
        amountFormatted,
        finalFormatted
    );
  }
}
