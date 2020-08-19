package chess;

import javax.swing.*;
import java.awt.*;


public class GameFrame extends JComponent {

    public BoardPiece[] whitePlayer = new BoardPiece[16]; // tracks white player positions
    public BoardPiece[] blackPlayer = new BoardPiece[16]; // tracks black player positions
    public static int boardWidth = 8; // width of playable board (number of spaces wide)
    public double scale = 500; // for resizeability
    public int pieceToMove = -1;
    //for castle
    public boolean blackKingMoved = false;
    public boolean blackQueenMoved = false;
    public boolean blackRookLeftMoved = false;
    public boolean blackRookRightMoved = false;
    public boolean whiteKingMoved = false;
    public boolean whiteQueenMoved = false;
    public boolean whiteRookLeftMoved = false;
    public boolean whiteRookRightMoved = false;


    public void init() { // called when game starts
        spawnPawn();
        spawnKnight();
        spawnBishop();
        spawnRook();
        spawnQueen();
        spawnKing();
    }

    /*
    spawn methods
     */
    private void spawnKing() {
        whitePlayer[0] = new BoardPiece(5, 8, Color.WHITE, ChessPiece.KING);
        blackPlayer[0] = new BoardPiece(5, 1, Color.BLACK, ChessPiece.KING);
    }

    private void spawnQueen() {
        whitePlayer[1] = new BoardPiece(4, 8, Color.WHITE, ChessPiece.QUEEN);
        blackPlayer[1] = new BoardPiece(4, 1, Color.BLACK, ChessPiece.QUEEN);
    }

    private void spawnRook() {
        whitePlayer[2] = new BoardPiece(1, 8, Color.WHITE, ChessPiece.ROOK);
        whitePlayer[3] = new BoardPiece(8, 8, Color.WHITE, ChessPiece.ROOK);
        blackPlayer[2] = new BoardPiece(1, 1, Color.BLACK, ChessPiece.ROOK);
        blackPlayer[3] = new BoardPiece(8, 1, Color.BLACK, ChessPiece.ROOK);
    }

    private void spawnKnight() {
        whitePlayer[4] = new BoardPiece(2, 8, Color.WHITE, ChessPiece.KNIGHT);
        whitePlayer[5] = new BoardPiece(7, 8, Color.WHITE, ChessPiece.KNIGHT);
        blackPlayer[4] = new BoardPiece(2, 1, Color.BLACK, ChessPiece.KNIGHT);
        blackPlayer[5] = new BoardPiece(7, 1, Color.BLACK, ChessPiece.KNIGHT);
    }

    private void spawnBishop() {
        whitePlayer[6] = new BoardPiece(3, 8, Color.WHITE, ChessPiece.BISHOP);
        ;
        whitePlayer[7] = new BoardPiece(6, 8, Color.WHITE, ChessPiece.BISHOP);
        blackPlayer[6] = new BoardPiece(3, 1, Color.BLACK, ChessPiece.BISHOP);
        blackPlayer[7] = new BoardPiece(6, 1, Color.BLACK, ChessPiece.BISHOP);
    }

    private void spawnPawn() {
        for (int i = 1; i <= boardWidth; i++) {
            whitePlayer[i + 7] = new BoardPiece(i, 7, Color.WHITE, ChessPiece.PAWN);
            blackPlayer[i + 7] = new BoardPiece(i, 2, Color.BLACK, ChessPiece.PAWN);
        }
    }

    /*
    draws blank chess board
     */
    private void drawChessBoard(Graphics g) {
        //frame
        Color black = new Color(66, 55, 0);
        g.setColor(black);
        g.fillRect(0, 0, (int) scale * 10, (int) scale * 10);
        g.setColor(Color.BLACK);
        g.drawRect((int) scale / 2, (int) scale / 2, (int) scale * 9, (int) scale * 9);
        for (int i = 0; i < 8; i++) {
            for (int n = 0; n < 8; n++) {
                if (i % 2 == 0 && n % 2 == 0 || i % 2 == 1 && n % 2 == 1) {
                    Color cream = new Color(221, 213, 197);
                    g.setColor(cream);
                    g.fillRect((int) (scale * (i + 1)), (int) (scale * (n + 1)), (int) scale, (int) scale);
                } else {
                    Color brown = new Color(95, 79, 0);
                    g.setColor(brown);
                    g.fillRect((int) (scale * (i + 1)), (int) (scale * (n + 1)), (int) scale, (int) scale);
                }
            }
        }
    }

    /*
    draws pieces
     */
    private void drawPlayers(Graphics g) {
        drawKing(g);
        drawQueen(g);
        drawRook(g);
        drawKnight(g);
        drawBishop(g);
        drawPawn(g);
    }

    public void drawKing(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(whitePlayer[0].getImage(), (int) (scale * whitePlayer[0].x), (int) (scale * whitePlayer[0].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[0].getImage(), (int) (scale * blackPlayer[0].x), (int) (scale * blackPlayer[0].y), (int) scale, (int) scale, this);
    }

    private void drawQueen(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(whitePlayer[1].getImage(), (int) (scale * whitePlayer[1].x), (int) (scale * whitePlayer[1].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[1].getImage(), (int) (scale * blackPlayer[1].x), (int) (scale * blackPlayer[1].y), (int) scale, (int) scale, this);
    }

    private void drawRook(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(whitePlayer[2].getImage(), (int) (scale * whitePlayer[2].x), (int) (scale * whitePlayer[2].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[3].getImage(), (int) (scale * blackPlayer[2].x), (int) (scale * blackPlayer[2].y), (int) scale, (int) scale, this);
        g2.drawImage(whitePlayer[2].getImage(), (int) (scale * whitePlayer[3].x), (int) (scale * whitePlayer[3].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[3].getImage(), (int) (scale * blackPlayer[3].x), (int) (scale * blackPlayer[3].y), (int) scale, (int) scale, this);
    }

    private void drawKnight(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(whitePlayer[4].getImage(), (int) (scale * whitePlayer[4].x), (int) (scale * whitePlayer[4].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[5].getImage(), (int) (scale * blackPlayer[4].x), (int) (scale * blackPlayer[4].y), (int) scale, (int) scale, this);
        g2.drawImage(whitePlayer[4].getImage(), (int) (scale * whitePlayer[5].x), (int) (scale * whitePlayer[5].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[5].getImage(), (int) (scale * blackPlayer[5].x), (int) (scale * blackPlayer[5].y), (int) scale, (int) scale, this);
    }

    private void drawBishop(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(whitePlayer[6].getImage(), (int) (scale * whitePlayer[6].x), (int) (scale * whitePlayer[6].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[7].getImage(), (int) (scale * blackPlayer[6].x), (int) (scale * blackPlayer[6].y), (int) scale, (int) scale, this);
        g2.drawImage(whitePlayer[6].getImage(), (int) (scale * whitePlayer[7].x), (int) (scale * whitePlayer[7].y), (int) scale, (int) scale, this);
        g2.drawImage(blackPlayer[7].getImage(), (int) (scale * blackPlayer[7].x), (int) (scale * blackPlayer[7].y), (int) scale, (int) scale, this);
    }

    private void drawPawn(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 8; i <= 15; i++) {
            g2.drawImage(whitePlayer[i].getImage(), (int) (scale * whitePlayer[i].x), (int) (scale * whitePlayer[i].y), (int) scale, (int) scale, this);
            g2.drawImage(blackPlayer[i].getImage(), (int) (scale * blackPlayer[i].x), (int) (scale * blackPlayer[i].y), (int) scale, (int) scale, this);
        }
    }

    public void updateFrameSize() { // resize (similar to flag)
        if (getWidth() > getHeight()) {
            scale = getHeight() / 10;
        } else {
            scale = getWidth() / 10;
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawChessBoard(g);
        drawPlayers(g);
        //game ended message
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, (int) (scale)));
        //game over, captured king
        if (whitePlayer[0].x == -1 && whitePlayer[0].y == -1) {
            blackWon(g);
        }
        if (blackPlayer[0].x == -1 && blackPlayer[0].y == -1) {
            whiteWon(g);
        }
    }

    public boolean validMove(BoardPiece b, int x, int y) {
        //can't move onto other white player
        if (b.color == Color.white) {
            for (int i = 0; i < whitePlayer.length; i++) {
                if (x == whitePlayer[i].x && y == whitePlayer[i].y) {
                    return false;
                }
            }
        }
        if (b.color == Color.black) {
            //can't move onto other black player
            for (int i = 0; i < blackPlayer.length; i++) {
                if (x == blackPlayer[i].x && y == blackPlayer[i].y) {
                    return false;
                }
            }
        }
        if (!moveOutOfBounds(x, y) && !noMove(b, x, y)) {
            if (pieceToMove == 0 && validMoveKing(b, x, y)) {
                return true;
            }
            if (pieceToMove == 1 && validMoveQueen(b, x, y)) {
                return true;
            }
            if ((pieceToMove == 2 || pieceToMove == 3) && validMoveRook(b, x, y)) {
                return true;
            }
            if ((pieceToMove == 4 || pieceToMove == 5) && validMoveKnight(b, x, y)) {
                return true;
            }
            if ((pieceToMove == 6 || pieceToMove == 7) && validMoveBishop(b, x, y)) {
                return true;
            } else {
                if (b.type == ChessPiece.PAWN) {
                    return validMovePawn(b, x, y);
                }
                if (b.type == ChessPiece.QUEEN) {
                    return validMoveQueen(b, x, y);
                }
                return false;
            }
        }
        return false;
    }

    private boolean moveOutOfBounds(int x, int y) {
        return x > 8 || x < 1 || y > 8 || y < 1;
    }

    private boolean noMove(BoardPiece b, int x, int y) {
        return b.x == x && b.y == y;
    }

    private boolean validMoveKing(BoardPiece b, int x, int y) {
        return Math.abs(b.x - x) <= 1 && Math.abs(b.y - y) <= 1;
    }

    private boolean validMoveQueen(BoardPiece b, int x, int y) {
        return validMoveBishop(b, x, y) || validMoveRook(b, x, y);
    }

    private boolean validMoveRook(BoardPiece b, int x, int y) {
        return (b.x == x && openVertical(b, x, y)) || (b.y == y && openHorizontal(b, x, y));
    }

    private boolean validMoveKnight(BoardPiece b, int x, int y) {
        return (Math.abs(b.x - x) == 2 && (Math.abs(b.y - y) == 1) || (Math.abs(b.y - y) == 2 && (Math.abs(b.x - x) == 1)));
    }

    private boolean validMoveBishop(BoardPiece b, int x, int y) {
        return Math.abs(b.x - x) == Math.abs(b.y - y) && openDiagonal(b, x, y);
    }

    private boolean validMovePawn(BoardPiece b, int x, int y) {
        if (b.color == Color.white) {
            return isOpenSquare(x, y) && b.x == x && (b.y - y == 1 || (b.y == 7 && b.y - y == 2 && isOpenSquare(x, y + 1)));
        }
        if (b.color == Color.black) {
            return isOpenSquare(x, y) && b.x == x && (y - b.y == 1 || (b.y == 2 && y - b.y == 2 && isOpenSquare(x, y - 1)));
        }
        return false;
    }

    public boolean validCapturePawn(BoardPiece b, int x, int y) {
        if (b.type == ChessPiece.PAWN && b.color == Color.white) {
            return Math.abs(b.x - x) == 1 && b.y - y == 1;
        }
        if (b.type == ChessPiece.PAWN && b.color == Color.BLACK) {
            return (Math.abs(b.x - x) == 1 && y - b.y == 1);
        }
        return false;
    }

    public int[] pixelToIndex(int x, int y) {
        return new int[]{(int) (x / scale), (int) (y / scale)};
    }

    private boolean openHorizontal(BoardPiece b, int x, int y) {
        if (b.x < x) {
            for (int i = 1; i < Math.abs(b.x - x); i++) {
                if (!isOpenSquare(b.x + i, y)) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < Math.abs(b.x - x); i++) {
                if (!isOpenSquare(b.x - i, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean openVertical(BoardPiece b, int x, int y) {
        if (b.y < y) {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(x, b.y + i)) {
                    return false;
                }
            }
        } else {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(x, b.y - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean openDiagonal(BoardPiece b, int x, int y) {
        //left bottom
        if (b.y < y && b.x > x) {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(b.x - i, b.y + i)) {
                    return false;
                }
            }
        }
        //left top
        if (b.y > y && b.x > x) {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(b.x - i, b.y - i)) {
                    return false;
                }
            }
        }
        //right bottom
        if (b.y < y && b.x < x) {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(b.x + i, b.y + i)) {
                    return false;
                }
            }
        }
        //right top
        if (b.y > y && b.x < x) {
            for (int i = 1; i < Math.abs(b.y - y); i++) {
                if (!isOpenSquare(b.x + i, b.y - i)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isOpenSquare(int x, int y) {
        for (int i = 0; i < blackPlayer.length; i++) {
            if (x == blackPlayer[i].x && y == blackPlayer[i].y) {
                return false;
            }
        }
        for (int i = 0; i < whitePlayer.length; i++) {
            if (x == whitePlayer[i].x && y == whitePlayer[i].y) {
                return false;
            }
        }
        return true;
    }

    public void whiteWon(Graphics g) {
        g.drawString("White Won!", (int) scale * 3, (int) (scale * 11));
    }

    public void blackWon(Graphics g) {
        g.drawString("Black Won!", (int) scale * 3, (int) (scale * 11));
    }

    //to castle, click king, then rook; or queen, then rook
    public boolean castle(BoardPiece b, int x, int y) {
        if (b.color == Color.white) {
            if (b.type == ChessPiece.KING && !whiteKingMoved) {
                //left rook
                if (x == 1 && y == 8 && !whiteRookLeftMoved && isOpenSquare(2, 8) && isOpenSquare(3, 8) && isOpenSquare(4, 8)) {
                    return true;
                }
                //right rook
                if (x == 8 && y == 8 && !whiteRookRightMoved && isOpenSquare(6, 8) && isOpenSquare(7, 8)) {
                    return true;
                }
            }
            if (b.type == ChessPiece.QUEEN && !whiteQueenMoved) {
                //left rook
                if (x == 1 && y == 8 && !whiteRookLeftMoved && isOpenSquare(2, 8) && isOpenSquare(3, 8)) {
                    return true;
                }
                //right rook
                if (x == 8 && y == 8 && !whiteRookRightMoved && isOpenSquare(5, 8) && isOpenSquare(6, 8) && isOpenSquare(7, 8)) {
                    return true;
                }
            }
            return false;
        }
        if (b.color == Color.black) {
            if (b.type == ChessPiece.KING && !blackKingMoved) {
                //left rook
                if (x == 1 && y == 1 && !blackRookLeftMoved && isOpenSquare(2, 1) && isOpenSquare(3, 1) && isOpenSquare(4, 1)) {
                    return true;
                }
                //right rook
                if (x == 8 && y == 1 && !blackRookRightMoved && isOpenSquare(6, 1) && isOpenSquare(7, 1)) {
                    return true;
                }
            }
            if (b.type == ChessPiece.QUEEN && !blackQueenMoved) {
                //left rook
                if (x == 1 && y == 1 && !blackRookLeftMoved && isOpenSquare(2, 1) && isOpenSquare(3, 1)) {
                    return true;
                }
                //right rook
                if (x == 8 && y == 1 && !blackRookRightMoved && isOpenSquare(5, 1) && isOpenSquare(6, 1) && isOpenSquare(7, 1)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    //precondition: whiteTurn=false;
    public int computerMove(BoardPiece b){
        int bestMoveScore = 0;
        if (b.color == Color.black) {

        }
        return bestMoveScore;
    }
}


