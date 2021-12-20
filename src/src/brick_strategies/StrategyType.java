package src.brick_strategies;

/**
 * this enum handles different kinds of strategy type.
 * indexed by value starting from 0 and so on . (for random choice)
 */
public enum StrategyType {

    ADD_PADDLE(0),
    CHANGE_CAMERA(1),
    PUCK(2),
    WIDE_PADDLE(3),
    DOUBLE_STRATEGY(4),
    DEFAULT(5);

    private final int value;


    /**
     * Contructor
     * @param value value
     */
    StrategyType(int value) {
        this.value = value;
    }

    /**
     *
     * @return value
     */
    public int getValue() {
        return value;
    }
}
