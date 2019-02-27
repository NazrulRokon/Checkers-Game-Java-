package CheckersGame;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class CheckersGame extends Application {

    public static final int TILE_SIZE = 80;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;
    
    int recentMove;
    int setPrevType = -1;
    public static int greenScore=0;
    public static int redScore=0;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    private Parent createContent() {
        Pane mainFrame = new Pane();
        mainFrame.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        mainFrame.getChildren().addAll(tileGroup, pieceGroup);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                Tile tile = new Tile((col + row) % 2 == 0, col, row);
                board[col][row] = tile;

                tileGroup.getChildren().add(tile);

                Piece piece = null;

                if (row <= 2 && (col + row) % 2 != 0) {
                    piece = makePiece(PieceType.RED, col, row);
                }

                if (row >= 5 && (col + row) % 2 != 0) {
                    piece = makePiece(PieceType.GREEN, col, row);
                }

                if (piece != null) {
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return mainFrame;
    }
    
    private MoveResult tryMove(Piece piece, int newX, int newY) {
        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0 ||
            (piece.getType()==PieceType.GREEN && setPrevType==1) ||
            (piece.getType()==PieceType.RED && setPrevType==0)) {
            /**** if (!board[newX][newY].hasPiece() && (newX + newY) % 2 != 0){
                if(piece.getType()==PieceType.GREEN && setPrevType==1){
                    JOptionPane.showMessageDialog(null, "Chance for the green..!");
                }
                else{
                    JOptionPane.showMessageDialog(null, "Chance for the green..!");
                }
                return new MoveResult(MoveType.NONE);
            }
            else  ***/
            return new MoveResult(MoveType.NONE);
        }

        int oldX = toBoard(piece.getOldX());
        int oldY = toBoard(piece.getOldY());

        if (Math.abs(newX - oldX) == 1) {
            if(piece.getType()==PieceType.GREEN)
                setPrevType=1;
            else
                setPrevType=0;
            return new MoveResult(MoveType.NORMAL);
        } else if (Math.abs(newX - oldX) == 2 && newY - oldY == piece.getType().moveDir * 2) {

            int midX = oldX + (newX - oldX) / 2;
            int midY = oldY + (newY - oldY) / 2;

            if (board[midX][midY].hasPiece() && board[midX][midY].getPiece().getType() != piece.getType()) {
                if(piece.getType()==PieceType.GREEN){
                    setPrevType=1;
                    greenScore++;
                }
                else{
                    setPrevType=0;
                    redScore++;
                }
                return new MoveResult(MoveType.KILL, board[midX][midY].getPiece());
            }
        }
        return new MoveResult(MoveType.NONE);
    }

    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> {
            int newX = toBoard(piece.getLayoutX());
            int newY = toBoard(piece.getLayoutY());

            MoveResult result;

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
                result = new MoveResult(MoveType.NONE);
            } else {
                result = tryMove(piece, newX, newY);
            }

            int oldX = toBoard(piece.getOldX());
            int oldY = toBoard(piece.getOldY());

            switch (result.getType()) {
                case NONE:
                    piece.abortMove();
                    break;
                case NORMAL:
                    piece.move(newX, newY);
                    board[oldX][oldY].setPiece(null);
                    board[newX][newY].setPiece(piece);
                    break;
                case KILL:
                    piece.move(newX, newY);
                    board[oldX][oldY].setPiece(null);
                    board[newX][newY].setPiece(piece);

                    Piece otherPiece = result.getPiece();
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
                    pieceGroup.getChildren().remove(otherPiece);
                    break;
            }
        });

        return piece;
    }

    public static void main(String[] args) {
        if(greenScore==7 || redScore==7){
            if(greenScore==7)
                JOptionPane.showMessageDialog(null, "Player with green piece Won..!");
            else
                JOptionPane.showMessageDialog(null, "Player with red piece Won..!");
        }
        else
            launch(args);
    }
}
