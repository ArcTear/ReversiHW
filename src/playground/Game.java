package playground;

import players.EasyComputer;
import players.HumanPlayer;

public class Game {
    private final Board board;
    private Field currentTurn;
    private int score;
    private int bestScore;

    public Game() {
        board = new Board();
        currentTurn = Field.WHITE;
        score = 0;
        bestScore = 0;
    }

    public void playWithEasyComputer(HumanPlayer player, EasyComputer computer) {
        do {
            printWhoTurns(player, computer, currentTurn);
            if (currentTurn == player.getColor()) {
                player.makeTurn(board);
            } else {
                computer.makeTurn(board);
            }
            currentTurn = getOppositeColor(currentTurn);
        } while (!board.isGameOver());
        score = Integer.max(board.getScore(player.getColor()), board.getScore(computer.getColor()));
        int playerScore = board.getScore(player.getColor());
        int computerScore = board.getScore(computer.getColor());
        if (playerScore > computerScore) {
            System.out.println("Победил " + player.getName() + " со счетом " + score);
            bestScore = Integer.max(score, bestScore);
        } else if (computerScore > playerScore) {
            System.out.println("Победил " + computer.getName() + " со счетом " + score);
        } else {
            System.out.println("Ничья! Счет " + playerScore);
            bestScore = Integer.max(score, bestScore);
        }
    }

    public void playWithPlayer(HumanPlayer player1, HumanPlayer player2) {
        do {
            printWhoTurns(player1, player2, currentTurn);
            if (currentTurn == player1.getColor()) {
                player1.makeTurn(board);
            } else {
                player2.makeTurn(board);
            }
            currentTurn = getOppositeColor(currentTurn);
        } while (!board.isGameOver());
        score = Integer.max(board.getScore(player1.getColor()), board.getScore(player2.getColor()));
        int player1Score = board.getScore(player1.getColor());
        int player2Score = board.getScore(player2.getColor());
        if (player1Score > player2Score) {
            System.out.println("Победил " + player1.getName() + " со счетом " + score);
        } else if (player2Score > player1Score) {
            System.out.println("Победил " + player2.getName() + " со счетом " + score);
        } else {
            System.out.println("Ничья! Счет " + score);
        }
        bestScore = Integer.max(score, bestScore);
    }

    public int getBestScore() {
        return bestScore;
    }

    private void printWhoTurns(HumanPlayer player, HumanPlayer computer, Field color) {
        System.out.println((player.getColor() == color ? player.getName() : computer.getName()) + " ходит:");
    }

    private static Field getOppositeColor(Field color) {
        return (color == Field.BLACK ? Field.WHITE : Field.BLACK);
    }

    public void printBoard() {
        System.out.println(board);
    }
}
