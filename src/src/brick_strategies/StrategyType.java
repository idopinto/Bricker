package src.brick_strategies;

public enum StrategyType {
    DEFAULT(0),
    ADD_PADDLE(1),
    CHANGE_CAMERA(2),
    PUCK(3),
    WIDE_PADDLE(4);

    private final int value;


    StrategyType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
