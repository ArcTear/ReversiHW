package players;

import java.util.Scanner;

import playground.Board;
import playground.Field;

public class HumanPlayer implements Player {
    private final Field color;
    private final String name;

    public HumanPlayer(String name, Field color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public void makeTurn(Board board) {
        if (board.getAvailableTurns(color).isEmpty()) {
            System.out.println("Для пользователя \"" + name + "\" нет доступных ходов");
            return;
        }
        board.printBoardWithAvailableTurns(color);
        board.printAvailableTurns(color);
        System.out.println("Введите через пробел координаты: ");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextLine().split(" ");
        int x = Integer.parseInt(input[0]);
        int y = Integer.parseInt(input[1]);
        if (!board.isCageAvailable(board.getElement(x, y), color)) {
            System.out.println("Выбранная позиция не доступна для хода ");
        } else {
            board.putNewElement(x, y, color);
        }
    }

    public String getName() {
        return name;
    }

    public Field getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "players.Player{" +
                "color=" + color +
                ", name='" + name + '\'' +
                '}';
    }
}
