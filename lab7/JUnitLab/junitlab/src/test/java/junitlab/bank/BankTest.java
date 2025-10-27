package junitlab.bank;

import junitlab.bank.impl.GreatSavingsBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BankTest {

    private Bank bank;

    @BeforeEach
    public void setup() {
        bank = new GreatSavingsBank();
    }

    @Test
    public void testOpenAccount() throws AccountNotExistsException {
        String accountNumber = bank.openAccount();
        Assertions.assertNotNull(accountNumber);
        long balance = bank.getBalance(accountNumber);
        Assertions.assertEquals(0L, balance);
    }

    @Test
    public void testUniqueAccount() {
        String accountNumber1 = bank.openAccount();
        String accountNumber2 = bank.openAccount();
        Assertions.assertNotEquals(accountNumber1, accountNumber2);
    }

    @Test
    public void testInvalidAccount() {
        String nonExistingAccountNumber = "teszt_acc";
        Assertions.assertThrows(
                AccountNotExistsException.class,
                () -> bank.getBalance(nonExistingAccountNumber));
    }

    @Test
    public void testDeposit() throws AccountNotExistsException {
        String accountNumber = bank.openAccount();
        long depositAmount = 2000;
        bank.deposit(accountNumber, depositAmount);
        long actualBalance = bank.getBalance(accountNumber);
        Assertions.assertEquals(depositAmount, actualBalance);
    }
}