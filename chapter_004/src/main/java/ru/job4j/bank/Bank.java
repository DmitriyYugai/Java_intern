package ru.job4j.bank;

import java.util.*;

/**
 * Класс банк. Проводит операции с пользователями и счетами
 * @author Дмитрий Сараев (guitarpro4@mail.ru)
 * @version 1
 */
public class Bank {
    private Map<User, List<Account>> users = new HashMap<>();

    /**
     * Добавить пользователя
     * @param user пользлватель
     */
    public void addUser(User user) {
        users.putIfAbsent(user, new ArrayList<>());
    }

    /**
     * Удалить пользователя
     * @param user пользлватель
     */
    public void deleteUser(User user) {
        users.remove(user);
    }

    /**
     * Добавть счет пользователю
     * @param passport пасспорт пользователя
     * @param account счет пользователю
     */
    public void addAccountToUser(int passport, Account account) {
        if (!Objects.isNull(getUser(passport))) {
            users.get(getUser(passport)).add(account);
        } else {
            System.out.println("Incorrect passport");
        }
    }

    /**
     * Удалить счет пользователя
     * @param passport пасспорт пользователя
     * @param account счет пользователю
     */
    public void deleteAccountFromUser(int passport, Account account) {
        User user = getUser(passport);
        users.get(user).removeIf(acc -> acc.getRequisites() == (account.getRequisites()));
    }

    /**
     * Найти пользователя по пасспорту
     * @param passport пасспорт
     * @return пользователь
     */
    private User getUser(int passport) {
        return users.keySet().stream().filter(user -> user.getPassport() == passport).findFirst().orElse(null);
    }

    /**
     * Найти счет пользователя по реквизитам
     * @param user пользователь
     * @param requisites реквизиты
     * @return счет
     */
    private Account getAccount(User user, int requisites) {
        return users.get(user).stream().filter(acc -> acc.getRequisites() == requisites).findFirst().orElse(null);
    }

    /**
     * Вывести все аккаунты пользователя
     * @param passport пасспорт пользователю
     * @return список аккаунтов
     */
    public List<Account> getUserAccounts(int passport) {
        return this.users.get(getUser(passport));
    }

    /**
     * Перевести сумму со счета пользователя на счет вторго пользователя
     * @param srcPassport пасспорт пользователя
     * @param srcRequisite реквизиты счета пользователя
     * @param destPassport пасспорт второго пользователя
     * @param dstRequisite реквизиты счета второго пользователя
     * @param amount сумма перевода
     * @return успешность перевода
     */
    public boolean transferMoney(int srcPassport, int srcRequisite, int destPassport, int dstRequisite, double amount) {
        boolean result = false;
        Account srcAccount = getAccount(getUser(srcPassport), srcRequisite);
        Account destAccount = getAccount(getUser(destPassport), dstRequisite);
        if (srcAccount != null && destAccount != null) {
            result = getAccount(getUser(srcPassport), srcRequisite).transfer(destAccount, amount);
        }
        return result;
    }
}