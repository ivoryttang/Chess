package chess;

import java.awt.*;

enum ChessPiece {
    KING, QUEEN, ROOK, KNIGHT,
    BISHOP, PAWN;
}

public class BoardPiece {
    Color color;
    ChessPiece type;
    int x;
    int y;

    public BoardPiece(int x, int y, Color c, ChessPiece cp){
        this.x = x;
        this.y = y;
        this.color = c;
        this.type = cp;
    }

    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void promote(){
        type = ChessPiece.QUEEN;
    }

    public Image getImage(){
        if (color == Color.WHITE) {
            switch (type) {
                case KING:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhiteKing.png");
                case QUEEN:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhiteQueen.png");
                case ROOK:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhiteRook.png");
                case KNIGHT:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhiteKnight.png");
                case BISHOP:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhiteBishop.png");
                case PAWN:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/WhitePawn.png");
                default: {
                    return null;
                }
            }
        }
        else {
            switch (type) {
                case KING:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackKing.png");
                case QUEEN:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackQueen.png");
                case ROOK:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackRook.png");
                case KNIGHT:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackKnight.png");
                case BISHOP:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackBishop.png");
                case PAWN:
                    return Toolkit.getDefaultToolkit().getImage("/Users/itang/Documents/workspace/Chess/src/chess/BlackPawn.png");
                default: {
                     return null;
                }
            }
        }
    }
}
