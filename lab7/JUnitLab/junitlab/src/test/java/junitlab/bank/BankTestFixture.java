package junitlab.bank;

import junitlab.bank.impl.GreatSavingsBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class BankTestFixture {

    private Bank bank;
    private String account1;
    private String account2;

    @BeforeEach
    public void setup() throws AccountNotExistsException {
        bank = new GreatSavingsBank();
        account1 = bank.openAccount();
        bank.deposit(account1, 1500);
        account2 = bank.openAccount();
        bank.deposit(account2, 12000);
    }

    @Test
    public void testTransfer()
            throws AccountNotExistsException, NotEnoughFundsException {
        long transferAmount = 3456;
        long expectedBalance1 = 4956;
        long expectedBalance2 = 8544;
        bank.transfer(account2, account1, transferAmount);
        Assertions.assertEquals(expectedBalance1, bank.getBalance(account1),
                "First balance is wrong");
        Assertions.assertEquals(expectedBalance2, bank.getBalance(account2),
                "second balance is wrong");
    }

    @Test
    public void testTransferWithoutEnoughFunds() {
        Assertions.assertThrows(
                NotEnoughFundsException.class,
                () -> bank.transfer(account1, account2, 3456),
                "not thrown exception for lack of funds");

    }
}