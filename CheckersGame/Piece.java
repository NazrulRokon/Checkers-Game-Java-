package CheckersGame;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import static CheckersGame.CheckersGame.TILE_SIZE;

/**
 * @author Almas Baimagambetov (almaslvl@gmail.com)
 */
public final class Piece extends StackPane {

    private final PieceType type;

    private double mouseX, mouseY;
    private double oldX, oldY;
    
    public PieceType getType() {
        return type;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public Piece(PieceType type, int x, int y) {
        this.type = type;

        move(x, y);

        Circle circle = new Circle(TILE_SIZE * 0.3);
        circle.setFill(type == PieceType.RED ? Color.valueOf("#c00") : Color.valueOf("#070"));

        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(TILE_SIZE * 0.03);

        circle.setTranslateX((TILE_SIZE - TILE_SIZE * 0.315 * 2) / 2);
        circle.setTranslateY((TILE_SIZE - TILE_SIZE * 0.315 * 2) / 2);

        getChildren().addAll(circle);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });

        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }
}
