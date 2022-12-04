package playground;

public enum Field {
    BLACK("□"),
    WHITE("■"),
    EMPTY("_");

    private final String name;

    Field(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}