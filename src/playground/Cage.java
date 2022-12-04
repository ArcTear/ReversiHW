package playground;

public class Cage {
    private Integer x;
    private Integer y;
    private Field field;

    public Cage(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        field = Field.EMPTY;
    }

    public Cage(Integer x, Integer y, Field field) {
        this.x = x;
        this.y = y;
        this.field = field;
    }

    public Field getFieldColor() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
