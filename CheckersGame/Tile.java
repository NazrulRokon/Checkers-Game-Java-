package CheckersGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public class Tile extends Rectangle {

    private Piece piece;

    public boolean hasPiece() {
        return piece != null;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Tile(boolean deep, int x, int y) {
        setWidth(CheckersGame.TILE_SIZE);
        setHeight(CheckersGame.TILE_SIZE);

        relocate(x * CheckersGame.TILE_SIZE, y * CheckersGame.TILE_SIZE);

        setFill(deep ? Color.valueOf("#222") : Color.valueOf("#ddc"));
    }
}
