package chessington.model;

public enum PlayerColour {
    WHITE(-1),
    BLACK(1);

    public final int DIRECTION_SCALAR;

    private PlayerColour(int DIRECTION_SCALAR) {
        this.DIRECTION_SCALAR = DIRECTION_SCALAR;
    }
}
