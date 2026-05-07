package app;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho một khách hàng.
 */
public class Customer {
  private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);
  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Khởi tạo khách hàng mặc định (phục vụ MyTest).
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Khởi tạo khách hàng với số chứng minh nhân dân và họ tên.
   *
   * @param idNumber số CMND
   * @param fullName họ tên
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<Account>();
  }

  /**
   * Lấy số CMND.
   *
   * @return số CMND
   */
  public long getIdNumber() {
    return idNumber;
  }

  /**
   * Thiết lập số CMND.
   *
   * @param idNumber số CMND mới
   */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /**
   * Lấy họ tên khách hàng.
   *
   * @return họ tên
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Thiết lập họ tên khách hàng.
   *
   * @param fullName họ tên mới
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Lấy danh sách tài khoản của khách hàng.
   *
   * @return danh sách tài khoản
   */
  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Thiết lập danh sách tài khoản cho khách hàng.
   *
   * @param accountList danh sách tài khoản mới
   */
  public void setAccountList(List<Account> accountList) {
    if (accountList == null) {
      this.accountList = new ArrayList<Account>();
    } else {
      this.accountList = accountList;
    }
  }

  /**
   * Thêm một tài khoản cho khách hàng.
   *
   * @param account tài khoản cần thêm
   */
  public void addAccount(Account account) {
    if (account == null) {
      LOGGER.warn("Nỗ lực thêm tài khoản thất bại vì đối tượng tài khoản (account) truyền vào bị null ở khách hàng mang ID: {}", idNumber);
      return;
    }
    if (!accountList.contains(account)) {
      accountList.add(account);
      LOGGER.info("Thêm thành công tài khoản {} cho khách hàng mang ID: {}", account.getAccountNumber(), idNumber);
    } else {
      LOGGER.debug("Tài khoản {} đã tồn tại ở khách hàng mang ID: {}", account.getAccountNumber(), idNumber);
    }
  }

  /**
   * Xóa một tài khoản khỏi khách hàng.
   *
   * @param account tài khoản cần xóa
   */
  public void removeAccount(Account account) {
    if (account == null) {
      LOGGER.warn("Nỗ lực xóa tài khoản thất bại vì đối tượng tài khoản bị null (ID khách hàng: {})", idNumber);
      return;
    }
    if(accountList.remove(account)) {
        LOGGER.info("Xóa thành công tài khoản {} thuộc khách hàng mang ID: {}", account.getAccountNumber(), idNumber);
    } else {
        LOGGER.debug("Không tìm thấy tài khoản {} để xóa khỏi khách hàng mang ID: {}", account.getAccountNumber(), idNumber);
    }
  }

  /**
   * Trả về thông tin khách hàng dưới dạng chuỗi.
   *
   * @return chuỗi chứa số CMND và họ tên
   */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}
