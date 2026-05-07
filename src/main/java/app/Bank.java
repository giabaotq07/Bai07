package app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bank {
  private static final Logger LOGGER = LoggerFactory.getLogger(Bank.class);
  private List<Customer> customerList;

  /**
   * Khởi tạo một ngân hàng mới với danh sách khách hàng rỗng.
   */
  public Bank() {
    this.customerList = new ArrayList<>();
  }

  /**
   * Lấy danh sách khách hàng.
   *
   * @return danh sách khách hàng
   */
  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Thiết lập danh sách khách hàng.
   *
   * @param customerList danh sách khách hàng mới
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = customerList;
    }
  }

  /**
   * Đọc danh sách khách hàng từ InputStream.
   *
   * @param inputStream luồng dữ liệu đầu vào chứa thông tin khách hàng và tài khoản
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      LOGGER.error("Không thể đọc danh sách khách hàng vì luồng dữ liệu truyền vào là bị rỗng (null)");
      return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
      String line;
      Customer current = null;
      while ((line = reader.readLine()) != null) {
        line = line.trim();
        if (line.isEmpty()) {
            continue;
        }
        int last = line.lastIndexOf(' ');
        if (last > 0) {
            String token = line.substring(last + 1).trim();
            if (token.matches("\\d{9}")) {
                String name = line.substring(0, last).trim();
                current = new Customer(Long.parseLong(token), name);
                customerList.add(current);
                LOGGER.info("Đã tải khách hàng: {} ID: {}", name, token);
            } else if (current != null) {
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    long num = Long.parseLong(parts[0]);
                    double bal = Double.parseDouble(parts[2]);
                    if (Account.CHECKING_TYPE.equals(parts[1])) {
                        current.addAccount(new CheckingAccount(num, bal));
                        LOGGER.debug("Thêm CheckingAccount hệ thống: {} - {}", num, bal);
                    } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
                        current.addAccount(new SavingsAccount(num, bal));
                        LOGGER.debug("Thêm SavingsAccount hệ thống: {} - {}", num, bal);
                    }
                }
            }
        }
      }
      LOGGER.info("Quá trình phân tích file dữ liệu hoàn tất. Tổng số khách hàng: {}", customerList.size());
    } catch (Exception e) {
      LOGGER.error("Error reading file - Lỗi đọc luồng tín hiệu file", e);
    }
  }

  /**
   * Lấy thông tin tất cả khách hàng được sắp xếp theo số ID.
   *
   * @return chuỗi thông tin của các khách hàng đã sắp xếp theo ID
   */
  public String getCustomersInfoByIdOrder() {
    List<Customer> copy = new ArrayList<>(customerList);
    copy.sort((o1, o2) -> Long.compare(o1.getIdNumber(), o2.getIdNumber()));

    StringBuilder res = new StringBuilder();
    for (int i = 0; i < copy.size(); i++) {
        res.append(copy.get(i).getCustomerInfo());
        if (i < copy.size() - 1) {
            res.append("\n");
        }
    }
    return res.toString();
  }

  /**
   * Lấy thông tin tất cả khách hàng được sắp xếp theo tên.
   *
   * @return chuỗi thông tin của các khách hàng đã sắp xếp theo tên
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> copy = new ArrayList<>(customerList);
    copy.sort((c1, c2) -> {
      int res = c1.getFullName().compareTo(c2.getFullName());
      return res != 0 ? res : Long.compare(c1.getIdNumber(), c2.getIdNumber());
    });

    StringBuilder sb = new StringBuilder();
    for (Customer c : copy) {
      sb.append(c.getCustomerInfo()).append("\n");
    }
    return sb.toString().trim();
  }
}