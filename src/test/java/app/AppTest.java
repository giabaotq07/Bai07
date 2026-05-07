package app;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AppTest {

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);

    @Test
    public void testCheckingAccount() {
        logger.info("Bắt đầu thực thi testCheckingAccount");
        CheckingAccount acc = new CheckingAccount(123456, 1000);
        acc.deposit(400);
        assertEquals(1400, acc.getBalance(), 0.01);
        acc.withdraw(100);
        assertEquals(1300, acc.getBalance(), 0.01);
        logger.info("Hoàn thành testCheckingAccount thành công");
    }

    @Test
    public void testSavingsAccount() {
        logger.info("Bắt đầu thực thi testSavingsAccount");
        SavingsAccount acc = new SavingsAccount(654321, 5000);
        acc.deposit(1000);
        assertEquals(6000, acc.getBalance(), 0.01);
        acc.withdraw(200);
        assertEquals(5800, acc.getBalance(), 0.01);
        logger.info("Hoàn thành testSavingsAccount thành công");
    }

    @Test
    public void testTransaction() {
        logger.info("Bắt đầu thực thi testTransaction");
        Transaction t = new Transaction(1, 100, 1000, 1100);
        assertEquals(1100, t.getFinalBalance(), 0.001);
        logger.info("Hoàn thành testTransaction thành công");
    }

    @Test
    public void testInvalidDepositException() {
        logger.info("Bắt đầu thực thi testInvalidDepositException");
        CheckingAccount acc = new CheckingAccount(111, 1000);
        Exception exception = assertThrows(InvalidFundingAmountException.class, () -> {
            acc.doDepositing(-500);
        });
        logger.warn("Bắt được ngoại lệ như mong đợi (warn): {}", exception.getMessage());
    }

    @Test
    public void testInsufficientFundsException() {
        logger.info("Bắt đầu thực thi testInsufficientFundsException");
        SavingsAccount acc = new SavingsAccount(222, 1000);
        Exception exception = assertThrows(InsufficientFundsException.class, () -> {
            acc.doWithdrawing(2000);
        });
        logger.warn("Bắt được ngoại lệ như mong đợi (warn): {}", exception.getMessage());
    }

    @Test
    public void testErrorCaseNullTransactionList() {
        logger.info("Bắt đầu thực thi testErrorCaseNullTransactionList");
        SavingsAccount acc = new SavingsAccount(333, 1000);
        try {
            acc.setTransactionList(null);
            assertNotNull(acc.getTransactionList());
            logger.info("Cơ chế dự phòng khi danh sách giao dịch null hoạt động tốt");
        } catch (Exception e) {
            logger.error("Đã xảy ra lỗi không mong muốn: {}", e.getMessage(), e);
            fail("Không nên tung ra lỗi trong trường hợp này");
        }
    }
    @Test
    public void testFilePathHardcoded() {
        String folder = "logs";
        String fileName = "transaction.txt";

        // Sử dụng java.nio.file.Path để an toàn trên mọi hệ điều hành
        java.nio.file.Path path = java.nio.file.Paths.get(folder, fileName);

        // Kiểm tra xem Java có nhận diện đúng thư mục cha là "logs" không
        assertEquals("logs", path.getParent().toString(), "Thư mục cha phải là logs");
    }
}
