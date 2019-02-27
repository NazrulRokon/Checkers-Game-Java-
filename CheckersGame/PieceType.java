package CheckersGame;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public enum PieceType {
    RED(1), GREEN(-1);

    final int moveDir;

    PieceType(int moveDir) {
        this.moveDir = moveDir;
    }
}
