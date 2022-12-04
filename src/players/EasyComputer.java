package players;

import java.util.ArrayList;

import playground.Board;
import playground.Cage;
import playground.Field;

public class EasyComputer extends HumanPlayer {
    private final ComputerLevel level;

    public EasyComputer(String name, Field color, ComputerLevel level) {
        super(name, color);
        this.level = level;
    }

    @Override
    public void makeTurn(Board board) {
        ArrayList<Cage> availableTurns = (ArrayList<Cage>) board.getAvailableTurns(getColor());
        if (availableTurns.isEmpty()){
            System.out.println("Для " + getName() + " нет доступных ходов");
            return;
        }
        int indexCornerCage = findCornerCage(availableTurns);
        if (indexCornerCage != -1) {
            Cage cornerCage = availableTurns.get(indexCornerCage);
            board.putNewElement(cornerCage.getX(), cornerCage.getY(), cornerCage.getFieldColor());
            return;
        }
        int indexMaxRate = -1;
        double maxRate = -1D;
        for (int i = 0; i < availableTurns.size(); i++) {
            Cage cage = availableTurns.get(i);
            ArrayList<Cage> captured = (ArrayList<Cage>) board.getCapturedCages(cage);
            double currentRate = rateTurn(cage.getX(), cage.getY(), captured);
            if (currentRate > maxRate) {
                maxRate = Double.max(rateTurn(cage.getX(), cage.getY(), captured), maxRate);
                indexMaxRate = i;
            }
        }
        Cage maxCage = availableTurns.get(indexMaxRate);
        System.out.println("Легкий компьютер ходит на " + maxCage);
        board.putNewElement(maxCage.getX(), maxCage.getY(), maxCage.getFieldColor());
    }

    private double rateTurn(int indexX, int indexY, ArrayList<Cage> captured) {
        int sum = 0;
        if (indexX == 7 && indexY == 7) {
            sum += 0.8;
        } else if (indexX == 7 || indexY == 7) {
            sum += 0.4;
        }
        for (Cage cage : captured) {
            if (cage.getX() == 7 || cage.getY() == 7) {
                sum += 2;
            } else {
                sum += 1;
            }
        }
        return sum;
    }

    private Integer findCornerCage(ArrayList<Cage> cages) {
        for (int i = 0; i < cages.size(); i++) {
            Cage cage = cages.get(i);
            if (cage.getX() == 7 && cage.getY() == 7
                    || cage.getX() == 7 && cage.getY() == 0
                    || cage.getX() == 0 && cage.getY() == 7
                    || cage.getX() == 0 && cage.getY() == 0) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "color=" + getColor() +
                ", name='" + getName() + '\'' +
                "level=" + level +
                '}';
    }
}
