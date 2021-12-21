package games;

import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;

import static java.lang.System.out;

public class Drunkard {
    private static final int PARS_TOTAL_COUNT = Par.values().length;
    private static final int CARDS_TOTAL_COUNT = PARS_TOTAL_COUNT * Suit.values().length; //36

    private static final int[][] playersCards = new int[2][CARDS_TOTAL_COUNT];
    private static final int[] playerCardTails = new int[]{0, 0};
    private static final int[] playerCardHeads = new int[]{(CARDS_TOTAL_COUNT / 2) - 1, (CARDS_TOTAL_COUNT / 2) - 1};


    public static void main(String... __) {

        int[] cardDeck = new int[CARDS_TOTAL_COUNT];
        for (int i = 0; i < cardDeck.length; i++) cardDeck[i] = i;
        out.println(Arrays.toString(cardDeck));

        MathArrays.shuffle(cardDeck);
        System.arraycopy(cardDeck, 0, playersCards[0], 0, CARDS_TOTAL_COUNT / 2);
        System.arraycopy(cardDeck, CARDS_TOTAL_COUNT / 2, playersCards[1], 0, CARDS_TOTAL_COUNT / 2);

        int[] playedCard = new int[2];
        int round = 0;
        while (true) {
            round++;

            out.println(Arrays.toString(playersCards[0]));
            out.println(Arrays.toString(playersCards[1]));

            int[] cardNumber = new int[]{
                    getCardNumber(playerCardHeads[0], playerCardTails[0]),
                    getCardNumber(playerCardHeads[1], playerCardTails[1])};


            if (playerCardsIsEmpty(0)) {
                out.printf("Выиграл первый игрок! Количество произведённых итераций: %d.", round);
                break;
            }
            if (playerCardsIsEmpty(1)) {
                out.printf("Выиграл второй игрок! Количество произведённых итераций: %d.", round);
                break;
            }

            out.printf("у игрока №1 %d карт%n", cardNumber[0]);
            out.printf("у игрока №2 %d карт%n", cardNumber[1]);

            for (int i = 0; i < 2; i++) {
                playedCard[i] = playersCards[i][playerCardTails[i]];
                playersCards[i][playerCardTails[i]] = 0;
                playerCardTails[i] = incrementIndex(playerCardTails[i]);
            }
            out.printf("Итерация №%d Игрок №1 карта: %s; игрок №2 карта: %s.%n",
                    round, toString(playedCard[0]), toString(playedCard[1]));
            int winningPlayer = getWinner(playedCard);

            if (winningPlayer == 2) {
                out.println("Спор - каждый остаётся при своих!");
                for (int i = 0; i < 2; i++) {
                    playerCardHeads[i] = incrementIndex(playerCardHeads[i]);
                    playersCards[i][playerCardHeads[i]] = playedCard[i];
                }
            } else {
                out.printf("Выиграл игрок %d! %n", winningPlayer + 1);
                int loserPlayer = winningPlayer == 1 ? 0 : 1;
                for (int playerCard : new int[]{playedCard[winningPlayer], playedCard[loserPlayer]}) {
                    playerCardHeads[winningPlayer] = incrementIndex(playerCardHeads[winningPlayer]);
                    playersCards[winningPlayer][playerCardHeads[winningPlayer]] = playerCard;
                }
            }
        }
    }

    enum Suit {
        SPADES, // пики
        HEARTS, // червы
        CLUBS, // трефы
        DIAMONDS // бубны
    }

    private static Suit getSuit(int cardNumber) {
        return Suit.values()[cardNumber / PARS_TOTAL_COUNT];
    }

    enum Par {
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK, // Валет
        QUEEN, // Дама
        KING, // Король
        ACE // Туз
    }

    private static Par getPar(int cardNumber) {
        return Par.values()[cardNumber % PARS_TOTAL_COUNT];
    }

    private static String toString(int cardNumber) {
        return getPar(cardNumber) + " " + getSuit(cardNumber);
    }

    private static int incrementIndex(int i) {
        return (i + 1) % CARDS_TOTAL_COUNT;
    }

    private static boolean playerCardsIsEmpty(int playerIndex) {
        int tail = playerCardTails[playerIndex];
        int head = playerCardHeads[playerIndex];
        out.printf("%d=%d%n", tail, head);
        return tail == head + 1;
    }

    private static int getCardNumber(int playerCardHead, int playerCardTail) {
        playerCardHead++;
        return playerCardHead >= playerCardTail ? playerCardHead - playerCardTail
                : playerCardHead + CARDS_TOTAL_COUNT - playerCardTail;
    }

    private static int getWinner(int[] playedCard) {
        Par[] pars = new Par[]{getPar(playedCard[0]), getPar(playedCard[1])};
        if (pars[0].ordinal() == pars[1].ordinal()) return 2;
        int winningPlayer = pars[0].ordinal() < pars[1].ordinal() ? 1 : 0;
        int loserPlayer = Math.abs(winningPlayer - 1);
        if (pars[0] == Par.SIX && pars[1] == Par.ACE
                || pars[1] == Par.SIX && pars[0] == Par.ACE
        ) return loserPlayer;
        return winningPlayer;
    }
}
