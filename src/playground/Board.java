package playground;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final ArrayList<ArrayList<Cage>> board;

    public Board() {
        board = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            board.add(new ArrayList<>(8));
            for (int j = 0; j < 8; j++) {
                board.get(i).add(new Cage(i, j));
                if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
                    setElement(i, j, Field.BLACK);
                } else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
                    setElement(i, j, Field.WHITE);
                }
            }
        }
    }

    public Cage getElement(int indexX, int indexY) {
        return board.get(indexX).get(indexY);
    }

    public void putNewElement(int indexX, int indexY, Field captureColor) {
        if (isCageAvailable(getElement(indexX, indexY), captureColor)) {
            setElement(indexX, indexY, captureColor);
            ArrayList<Cage> captured = (ArrayList<Cage>) getCapturedCages(new Cage(indexX, indexY, captureColor));
            for (Cage cage : captured) {
                setElement(cage.getX(), cage.getY(), captureColor);
            }
        }
    }

    private void setElement(int indexX, int indexY, Field field) {
        getElement(indexX, indexY).setField(field);
    }

    private void captureTop(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int x = indexX - 1; x >= 0; x--) {
            if (getElement(x, indexY).getFieldColor() == field) {
                break;
            } else {
//                setElement(x, indexY, field);
                captured.add(new Cage(x, indexY, field));
            }
        }
    }

    private void captureBot(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int x = indexX + 1; x < 8; x++) {
            if (getElement(x, indexY).getFieldColor() == field) {
                return;
            } else {
//                setElement(x, indexY, field);
                captured.add(new Cage(x, indexY, field));
            }
        }
    }

    private void captureLeft(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY - 1; y >= 0; y--) {
            if (getElement(indexX, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(indexX, y, field);
                captured.add(new Cage(indexX, y, field));
            }
        }
    }

    private void captureRight(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY + 1; y < 8; y++) {
            if (getElement(indexX, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(indexX, y, field);
                captured.add(new Cage(indexX, y, field));
            }
        }
    }

    private void captureTopLeft(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY - 1, x = indexX - 1; y >= 0 && x >= 0; y--, x--) {
            if (getElement(x, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(x, y, field);
                captured.add(new Cage(x, y, field));
            }
        }
    }

    private void captureTopRight(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY + 1, x = indexX - 1; y < 8 && x >= 0; y++, x--) {
            if (getElement(x, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(x, y, field);
                captured.add(new Cage(x, y, field));
            }
        }
    }

    private void captureBotLeft(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY - 1, x = indexX + 1; y >= 0 && x < 8; y--, x++) {
            if (getElement(x, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(x, y, field);
                captured.add(new Cage(x, y, field));
            }
        }
    }

    private void captureBotRight(int indexX, int indexY, Field field, ArrayList<Cage> captured) {
        for (int y = indexY + 1, x = indexX + 1; y < 8 && x < 8; y++, x++) {
            if (getElement(x, y).getFieldColor() == field) {
                return;
            } else {
//                setElement(x, y, field);
                captured.add(new Cage(x, y, field));
            }
        }
    }

    public List<Cage> getAvailableTurns(Field color) {
        ArrayList<Cage> availableTurns = new ArrayList<>();
        for (ArrayList<Cage> row : board) {
            for (Cage cage : row) {
                if (isCageAvailable(cage, color)) {
                    availableTurns.add(new Cage(cage.getX(), cage.getY(), color));
                }
            }
        }
        return availableTurns;
    }

    public List<Cage> getCapturedCages(Cage cage) {
        Integer indexX = cage.getX();
        Integer indexY = cage.getY();
        Field captureColor = cage.getFieldColor();
        ArrayList<Cage> captured = new ArrayList<>();
        captureCagesInList(indexX, indexY, captureColor, captured);
        return captured;
    }

    private void captureCagesInList(int indexX, int indexY, Field captureColor, ArrayList<Cage> captured) {
        if (checkTopField(indexX, indexY, captureColor)) {
            captureTop(indexX, indexY, captureColor, captured);
        }
        if (checkBotField(indexX, indexY, captureColor)) {
            captureBot(indexX, indexY, captureColor, captured);
        }
        if (checkLeftField(indexX, indexY, captureColor)) {
            captureLeft(indexX, indexY, captureColor, captured);
        }
        if (checkRightField(indexX, indexY, captureColor)) {
            captureRight(indexX, indexY, captureColor, captured);
        }
        if (checkTopLeftField(indexX, indexY, captureColor)) {
            captureTopLeft(indexX, indexY, captureColor, captured);
        }
        if (checkTopRightField(indexX, indexY, captureColor)) {
            captureTopRight(indexX, indexY, captureColor, captured);
        }
        if (checkBotLeftField(indexX, indexY, captureColor)) {
            captureBotLeft(indexX, indexY, captureColor, captured);
        }
        if (checkBotRightField(indexX, indexY, captureColor)) {
            captureBotRight(indexX, indexY, captureColor, captured);
        }
    }

    public void printBoardWithAvailableTurns(Field color) {
        StringBuilder res = new StringBuilder();
        res.append("  0 1 2 3 4 5 6 7\n");
        for (int i = 0; i < board.size(); i++) {
            res.append(i).append(" ");
            for (int j = 0; j < board.get(0).size(); j++) {
                Field field = this.getElement(i, j).getFieldColor();
                if (isCageAvailable(getElement(i, j), color)) {
                    res.append("A ");
                } else {
                    res.append(field.getName());
                    res.append(" ");
                }
            }
            res.append("\n");
        }
        System.out.println(res);
    }

    public void printAvailableTurns(Field color) {
        System.out.println("Возможные ходы:");
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                Cage currentCage = getElement(i, j);
                if (isCageAvailable(currentCage, color)) {
                    System.out.println(currentCage);
                }
            }
        }
    }

    public boolean isCageAvailable(Cage cage, Field color) {
        Integer indexX = cage.getX();
        Integer indexY = cage.getY();
        return cage.getFieldColor() == Field.EMPTY
                && (checkTopField(indexX, indexY, color)
                || checkBotField(indexX, indexY, color)
                || checkLeftField(indexX, indexY, color)
                || checkRightField(indexX, indexY, color)
                || checkTopLeftField(indexX, indexY, color)
                || checkTopRightField(indexX, indexY, color)
                || checkBotRightField(indexX, indexY, color)
                || checkBotLeftField(indexX, indexY, color));
    }

    private boolean checkTopField(int indexX, int indexY, Field color) {
        if (indexX - 2 >= 0
                && getElement(indexX - 1, indexY).getFieldColor() != Field.EMPTY
                && getElement(indexX - 1, indexY).getFieldColor() != color) {
            for (int x = indexX - 2; x >= 0; --x) {
                if (getElement(x, indexY).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, indexY).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBotField(int indexX, int indexY, Field color) {
        if (indexX + 2 < 8
                && getElement(indexX + 1, indexY).getFieldColor() != Field.EMPTY
                && getElement(indexX + 1, indexY).getFieldColor() != color) {
            for (int x = indexX + 2; x < 8; ++x) {
                if (getElement(x, indexY).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, indexY).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkLeftField(int indexX, int indexY, Field color) {
        if (indexY - 2 >= 0
                && getElement(indexX, indexY - 1).getFieldColor() != Field.EMPTY
                && getElement(indexX, indexY - 1).getFieldColor() != color) {
            for (int y = indexY - 2; y >= 0; --y) {
                if (getElement(indexX, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(indexX, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkRightField(int indexX, int indexY, Field color) {
        if (indexY + 2 < 8
                && getElement(indexX, indexY + 1).getFieldColor() != Field.EMPTY
                && getElement(indexX, indexY + 1).getFieldColor() != color) {
            for (int y = indexY + 2; y < 8; ++y) {
                if (getElement(indexX, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(indexX, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTopLeftField(int indexX, int indexY, Field color) {
        if (indexX - 2 >= 0 && indexY - 2 >= 0
                && getElement(indexX - 1, indexY - 1).getFieldColor() != Field.EMPTY
                && getElement(indexX - 1, indexY - 1).getFieldColor() != color) {
            for (int x = indexX - 2, y = indexY - 2; x >= 0 && y >= 0; --x, --y) {
                if (getElement(x, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkTopRightField(int indexX, int indexY, Field color) {
        if (indexX - 2 >= 0 && indexY + 2 < 8
                && getElement(indexX - 1, indexY + 1).getFieldColor() != Field.EMPTY
                && getElement(indexX - 1, indexY + 1).getFieldColor() != color) {
            for (int x = indexX - 2, y = indexY + 2; x >= 0 && y < 8; --x, ++y) {
                if (getElement(x, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBotRightField(int indexX, int indexY, Field color) {
        if (indexX + 2 < 8 && indexY + 2 < 8
                && getElement(indexX + 1, indexY + 1).getFieldColor() != Field.EMPTY
                && getElement(indexX + 1, indexY + 1).getFieldColor() != color) {
            for (int x = indexX + 2, y = indexY + 2; x < 8 && y < 8; ++x, ++y) {
                if (getElement(x, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkBotLeftField(int indexX, int indexY, Field color) {
        if (indexX + 2 < 8 && indexY - 2 >= 0
                && getElement(indexX + 1, indexY - 1).getFieldColor() != Field.EMPTY
                && getElement(indexX + 1, indexY - 1).getFieldColor() != color) {
            for (int x = indexX + 2, y = indexY - 2; x < 8 && y >= 0; ++x, --y) {
                if (getElement(x, y).getFieldColor() == Field.EMPTY) {
                    break;
                } else if (getElement(x, y).getFieldColor() == color) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameOver() {
        return isBoardOneColored() || isBoardCompleted() || !isAnyTurnAvailable();
    }

    private boolean isBoardOneColored() {
        Field field = getElement(3, 3).getFieldColor();
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                if (getElement(i, j).getFieldColor() != Field.EMPTY && getElement(i, j).getFieldColor() != field) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isBoardCompleted() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                if (this.getElement(i, j).getFieldColor() == Field.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isAnyTurnAvailable() {
        for (int i = 0; i < board.size(); i++) {
            for (int j = 0; j < board.get(0).size(); j++) {
                Field field = getElement(i, j).getFieldColor();
                if (field == Field.EMPTY
                        && (isCageAvailable(getElement(i, j), Field.BLACK)
                        || isCageAvailable(getElement(i, j), Field.WHITE))) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getScore(Field color) {
        int sideTracker = 0;
        for (ArrayList<Cage> row : board) {
            for (Cage cage : row) {
                if (cage.getFieldColor() == color) {
                    sideTracker++;
                }
            }
        }
        return sideTracker;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("  0 1 2 3 4 5 6 7\n");
        for (int i = 0; i < board.size(); i++) {
            res.append(i).append(" ");
            for (int j = 0; j < board.get(0).size(); j++) {
                Field field = getElement(i, j).getFieldColor();
                res.append(field.getName());
                res.append(" ");
            }
            res.append("\n");
        }
        return res.toString();
    }
}
