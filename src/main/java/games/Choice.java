package games;

import java.io.IOException;

import static java.lang.System.in;
import static java.lang.System.out;


public class Choice {
    public static void main(String[] args) throws IOException {
        out.println("Выберите игру:\n1 - \"однорукий бандит\", 2 - \"пьяница\"");
        switch (in.read()) {
            case '1' -> Slot.main();
            case '2' -> Drunkard.main();
            default -> out.println("Игры с таким номером нет!");
        }
    }
}
