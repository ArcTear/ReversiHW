import java.util.Scanner;

import players.ComputerLevel;
import players.EasyComputer;
import players.HumanPlayer;
import playground.Field;
import playground.Game;

public class Main {
    public static void main(String[] args) {
        int mode;
        Scanner scanner = new Scanner(System.in);
        var game = new Game();
        do {
            System.out.println("Выберите режим игры:");
            System.out.println("1. Легкий");
            System.out.println("2. Продвинутый");
            System.out.println("3. Игрок против игрока");
            System.out.println("Для выхода введите 4");
            mode = scanner.nextInt();
            if (mode == 1) {
                HumanPlayer player = new HumanPlayer("Игрок", chooseColor(scanner));

                EasyComputer computer = new EasyComputer("Легкий компьютер",
                        (player.getColor() == Field.BLACK ? Field.WHITE : Field.BLACK),
                        ComputerLevel.EASY);
                game.playWithEasyComputer(player, computer);
            } else if (mode == 2) {
                System.out.println("Режим игры с продвинутым компьютером не реализован");
            } else if (mode == 3) {
                HumanPlayer player1 = new HumanPlayer("Игрок 1", chooseColor(scanner));
                HumanPlayer player2 = new HumanPlayer("Игрок 2"
                        , getOppositeColor(player1.getColor()));
                game.playWithPlayer(player1, player2);
            } else if (mode == 4) {
                System.out.println("Спасибо за игру!");
            } else {
                System.out.println("Введенного номера нет в списке.");
            }
        } while (mode != 4);
        System.out.println("Лучший счет за сессию: " + game.getBestScore());
    }

    private static Field chooseColor(Scanner scanner) {
        int choice;
        do {
            System.out.println("Выберите цвет:\n1. Белый\n2. Черный");
            choice = scanner.nextInt();
            if (choice == 1) {
                return Field.WHITE;
            } else if (choice == 2) {
                return Field.BLACK;
            } else {
                System.out.println("Выбран неверный номер. Повторите ввод.");
            }
        } while (true);
    }

    private static Field getOppositeColor(Field color) {
        return (color == Field.BLACK ? Field.WHITE : Field.BLACK);
    }
}
