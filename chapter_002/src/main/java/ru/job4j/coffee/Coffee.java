package ru.job4j.coffee;

import java.util.Arrays;

/**
 * Задача со сдачей
 * @author Дмитрий Сараев (guitarpro4@mail.ru)
 * @version 1
 */
public class Coffee {
    int[] monets = {10, 5, 2, 1};

    /**
     * Метод выдает покупателю сдачу за кофе монетами номиналом 10, 5, 2, 1
     * @param value Купюра покупателя
     * @param price цена кофе
     * @return массив монет
     */
    int[] changes(int value, int price) {
        int change = value - price;
        int[] res = new int[change];
        int y = 0;
        for (int i = 0; i < monets.length;) {
            if (change / monets[i] >= 1) {
                for (int x = 0; x < change / monets[i]; x++) {
                    if (change - monets[i] >= 0) {
                        change = change - monets[i];
                        res[y] = monets[i];
                        y++;
                    } else {
                        i++;
                    }
                }
            } else {
                i++;
            }
        }
        return Arrays.copyOf(res, y);
    }
}
