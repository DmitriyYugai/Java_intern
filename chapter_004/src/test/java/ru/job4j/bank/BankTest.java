package ru.job4j.bank;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class BankTest {
    Bank bank = new Bank();
    List<Account> accounts = new ArrayList<>();
    User user1 = new User("Mike", 12312454);
    User user2 = new User("Jack", 23444322);
    private final ByteArrayOutputStream mem = new ByteArrayOutputStream();
    private final PrintStream out = System.out;

    @Before
    public void init() {
        accounts.add(new Account(500, 511));
        accounts.add(new Account(330, 611));
        accounts.add(new Account(200, 522));
        accounts.add(new Account(150, 622));
        bank.addUser(user1);
        bank.addUser(user2);
        bank.addAccountToUser(user1.getPassport(), accounts.get(0));
        bank.addAccountToUser(user1.getPassport(), accounts.get(1));
        bank.addAccountToUser(user2.getPassport(), accounts.get(2));
        bank.addAccountToUser(user2.getPassport(), accounts.get(3));
        System.setOut(new PrintStream(this.mem));
    }

    @After
    public void loadSys() {
        System.setOut(this.out);
    }

    @Test
    public void whenAddUsersAndAccountsThenTrue() {
        List<Account> accountsUser1 = bank.getUserAccounts(user1.getPassport());
        List<Account> accountsUser2 = bank.getUserAccounts(user2.getPassport());
        List<Account> result = new ArrayList<Account>() {
            {
                addAll(accountsUser1);
                addAll(accountsUser2);
            }
        };

        assertThat(result, is(accounts));
    }

    @Test
    public void whenDeleteUsersThenNull() {
        bank.deleteUser(user2);
        List<Account> result = bank.getUserAccounts(user2.getPassport());
        Assert.assertThat(result, is(nullValue()));
    }

    @Test
    public void whenAddUsersAndAccountThenTrue() {
        User user3 = new User("Mark", 233345);
        Account account = new Account(23, 711);
        bank.addUser(user3);
        bank.addAccountToUser(user3.getPassport(), account);
        List<Account> result = bank.getUserAccounts(user3.getPassport());
        Assert.assertThat(result.get(0).getRequisites(), is(account.getRequisites()));
    }

    @Test
    public void whenAddAccountToInvalidPassport() {
        Account account = new Account(23, 711);
        bank.addAccountToUser(123, account);
        assertThat(this.mem.toString(), is(String.format("Incorrect passport%n")));
    }

    @Test
    public void whenDeleteAccountThenTrue() {
        Account deleteAccount = new Account(500, 511);
        bank.deleteAccountFromUser(user1.getPassport(), deleteAccount);
        List<Account> result = bank.getUserAccounts(user1.getPassport());
        Assert.assertThat(result.size(), is(1));
    }

    @Test
    public void whenTransferBetweenAccountsThenTransferred() {
        bank.transferMoney(
                user1.getPassport(),
                511,
                user2.getPassport(),
                622,
                450
        );
        double[] result = {
                bank.getUserAccounts(user1.getPassport()).get(0).getValue(),
                bank.getUserAccounts(user2.getPassport()).get(1).getValue(),
        };
        double[] expected = {50, 600};
        assertThat(result, is(expected));

    }

    @Test
    public void whenTransferBetweenAccountsThenTrue() {
        boolean result = bank.transferMoney(
                user1.getPassport(),
                511,
                user2.getPassport(),
                622,
                450
        );
        assertThat(result, is(true));

    }

    @Test
    public void whenTransferBetweenAccountsThenNotTransferred() {
        bank.transferMoney(
                user1.getPassport(),
                511,
                user2.getPassport(),
                622,
                501
        );
        double[] result = {
                bank.getUserAccounts(user1.getPassport()).get(0).getValue(),
                bank.getUserAccounts(user2.getPassport()).get(1).getValue(),
        };
        double[] expected = {500, 150};
        assertThat(result, is(expected));
    }

    @Test
    public void whenTransferBetweenAccountsThenFalse() {
        boolean result = bank.transferMoney(
                user1.getPassport(),
                511,
                user2.getPassport(),
                622,
                501
        );
        assertThat(result, is(false));

    }
}