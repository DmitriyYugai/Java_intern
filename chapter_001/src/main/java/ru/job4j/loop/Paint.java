package ru.job4j.loop;

/**
 * StringBuilder, for и if
 * @author Дмитрий Сараев (guitarpro4@mail.ru)
 * @version 1
 */
public class Paint {

    /**
     * Создает пирамиду из символов "^".
     * @param height высота пирамиды
     * @return строка из символов "^" и " ".
     */
    public String piramid(int height) {
        StringBuilder screen = new StringBuilder();
        int weight = 2 * height - 1;
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (row >= height - column - 1 && row + height - 1 >= column) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }
}