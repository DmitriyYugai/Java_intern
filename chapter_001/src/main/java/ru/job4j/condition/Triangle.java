package ru.job4j.condition;

/** Треугольник
 * @author  Дмитрий Сараев (guitarpro4@mail.ru)
 * @version 1
 */
public class Triangle {
    private Point a;
    private Point b;
    private Point c;

    /**
     * Треугольник с вершинами в координатах
     * @param a
     * @param b
     * @param c
     */
    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Метод вычисления полупериметра по длинам сторон.
     * @param ab расстояние между a и b.
     * @param ac расстояние между a и c.
     * @param bc расстояние между b и c.
     * @return полупериметр.
     */
    public double period(double ab, double ac, double bc) {
        return (ab + ac + bc) / 2;
    }

    /**
     * Метод должен вычислить площадь треугольника.
     * @return Вернуть прощадь, если треугольник существует или -1, если треугольника нет.
     */
    public double area() {
        double rsl = -1;
        double ab = this.a.distance(this.b);
        double ac = this.a.distance(this.c);
        double bc = this.b.distance(this.c);
        double p = this.period(ab, ac, bc);
        if (this.exist(ab, ac, bc)) {
            rsl = Math.sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return rsl;
    }

    /**
     * Метод проверяет можно ли построить треугольник с такими длинами сторон.
     */
    private boolean exist(double ab, double ac, double bc) {
        return (ab + ac > bc && ab + bc > ac && ac + bc > ab) ? true : false;
    }
}
