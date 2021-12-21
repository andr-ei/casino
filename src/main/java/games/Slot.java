package games;

import static java.lang.Math.random;
import static java.lang.Math.round;
import static java.lang.System.out;

public class Slot {
    private static final int SIZE = 7;
    private static final int ATTEMPT_COST =  10;
    private static final int WINNING_AMOUNT = 1000;
    private static final int EFFORT_LIMIT = 100;

    public static void main(String... __) {
        int balance = 100;

        out.printf("У Вас %d$, ставка - %d$%n", balance, ATTEMPT_COST);
        int[] counters = new int[3];
        while (balance >= ATTEMPT_COST) {
            for (int i = 0; i < counters.length; i++)
                counters[i] = (counters[i] + (int) round(random() * EFFORT_LIMIT)) % SIZE;

            out.printf("Крутим барабаны!Розыгрыш принёс следующие результаты:%n" +
                    "первый барабан - %d, второй - %d, третий - %d%n", counters[0], counters[1], counters[2]);

            if (counters[0] == counters[1]
                    || counters[1] == counters[2]
                    || counters[0] == counters[2]) {
                out.printf("Выигрышь %d$", WINNING_AMOUNT);
                balance += WINNING_AMOUNT;
            } else {
                out.printf("Проигрыш %d$", ATTEMPT_COST);
                balance -= ATTEMPT_COST;
            }
            out.printf(", ваш капитал теперь составляет: %d$%n", balance);
        }
    }
}
