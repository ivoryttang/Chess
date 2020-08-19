//play against computer
package chess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessVsComputer extends JFrame {
    GameFrame draw;

    int potentialX;
    int potentialY;
    public boolean whiteTurn = true;
    public boolean pieceSelected = false;
    //for castling
    public boolean whiteKingSelected = false;
    public boolean whiteQueenSelected = false;
    public boolean blackKingSelected = false;
    public boolean blackQueenSelected = false;

    public ChessVsComputer() {
        this.draw = new GameFrame();
        initializeChess();
        draw.init();
        draw.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println("Clicked");
                //coord x, y when press mouse
                potentialX = e.getX();
                potentialY = e.getY();
                int[] indexPos = draw.pixelToIndex(potentialX, potentialY);
                int indexX = indexPos[0];
                int indexY = indexPos[1];

                /*
                white's turn
                 */
                if (whiteTurn == true){
                    System.out.println("white");
                    //select piece
                    for (int i = 0; i < draw.whitePlayer.length; i++){
                        if (indexX == draw.whitePlayer[i].x && indexY == draw.whitePlayer[i].y){
                            pieceSelected = true;
                            draw.pieceToMove = i;
                            //maybe castle
                            if (!draw.whiteKingMoved && i == 0){
                                whiteKingSelected = true;
                            }
                            if (!draw.whiteQueenMoved && i == 1){
                                whiteQueenSelected = true;
                            }
                            break;
                        }
                    }
                    //check for castle
                    if (whiteKingSelected && draw.castle(draw.whitePlayer[0],indexX,indexY)){
                        //left rook
                        if (indexX == 1 && indexY == 8){
                            draw.whitePlayer[0].setPosition(3,8);
                            draw.whitePlayer[2].setPosition(4,8);
                        }
                        //right rook
                        else{
                            draw.whitePlayer[0].setPosition(7,8);
                            draw.whitePlayer[3].setPosition(6,8);
                        }
                        draw.repaint();
                        whiteTurn = false;
                    }
                    if (whiteQueenSelected && draw.castle(draw.whitePlayer[1],indexX,indexY)){
                        //left rook
                        if (indexX == 1 && indexY == 8){
                            draw.whitePlayer[1].setPosition(2,8);
                            draw.whitePlayer[2].setPosition(3,8);
                        }
                        //right rook
                        else{
                            draw.whitePlayer[1].setPosition(6,8);
                            draw.whitePlayer[2].setPosition(5,8);
                        }
                        draw.repaint();
                        whiteTurn = false;
                    }
                    //move piece
                    if (draw.pieceToMove >= 0 && pieceSelected &&
                            (draw.validMove(draw.whitePlayer[draw.pieceToMove], indexX,indexY) ||
                                    draw.validCapturePawn(draw.whitePlayer[draw.pieceToMove], indexX,indexY))){
                        boolean captured = false;
                        boolean moved = false;
                        //capture black piece
                        if (draw.pieceToMove >= 8 && draw.pieceToMove <= 15 && draw.whitePlayer[draw.pieceToMove].type == ChessPiece.PAWN){ //if pawn
                            for (int i = 0; i < draw.blackPlayer.length; i++){
                                if (indexX == draw.blackPlayer[i].x && indexY == draw.blackPlayer[i].y){
                                    if (draw.validCapturePawn(draw.whitePlayer[draw.pieceToMove],indexX, indexY)){
                                        draw.blackPlayer[i].setPosition(-1,-1);
                                        //move white piece
                                        draw.whitePlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                        captured = true;
                                    }
                                }
                            }
                        }
                        else{ //other pieces
                            for (int i = 0; i < draw.blackPlayer.length; i++){
                                if (indexX == draw.blackPlayer[i].x && indexY == draw.blackPlayer[i].y){
                                    draw.blackPlayer[i].setPosition(-1,-1);
                                    //move white piece
                                    draw.whitePlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                    captured = true;
                                }
                            }
                        }
                        //move onto empty space
                        if (!captured && !draw.validCapturePawn(draw.whitePlayer[draw.pieceToMove], indexX,indexY)){
                            boolean isBlackPiece = false;
                            for (int i = 0; i < draw.blackPlayer.length; i++) {
                                if (indexX == draw.blackPlayer[i].x && indexY == draw.blackPlayer[i].y) {
                                    isBlackPiece = true;
                                }
                            }
                            if (draw.whitePlayer[draw.pieceToMove].type != ChessPiece.PAWN) {
                                //move white piece
                                draw.whitePlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                moved = true;
                            }
                            //pawn can't capture straight
                            if (!isBlackPiece){
                                //move white piece
                                draw.whitePlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                moved = true;
                            }
                        }
                        if (moved || captured){
                            //promote pawn to queen
                            if (draw.pieceToMove >= 8 && draw.pieceToMove <= 15 && draw.whitePlayer[draw.pieceToMove].y == 1){
                                draw.whitePlayer[draw.pieceToMove].promote();
                            }
                            whiteTurn = false;
                            pieceSelected = false;
                            draw.repaint();

                            //can still castle?
                            if (draw.whitePlayer[0].x != 5 || draw.whitePlayer[0].y != 8){
                                draw.whiteKingMoved = true;
                            }
                            if (draw.whitePlayer[1].x != 4 || draw.whitePlayer[1].y != 8){
                                draw.whiteQueenMoved = true;
                            }
                            if (draw.whitePlayer[2].x != 1 || draw.whitePlayer[2].y != 8){
                                draw.whiteRookLeftMoved = true;
                            }
                            if (draw.whitePlayer[3].x != 8 || draw.whitePlayer[3].y != 8){
                                draw.whiteRookRightMoved = true;
                            }
                        }
                    }
                }
                //computer moves
                else{
                    System.out.println("black");
                    int bestMoveScore = 0;
                    int bestMove = -1;
                    for (int i = 0; i < draw.blackPlayer.length; i++){
                        if (draw.computerMove(draw.blackPlayer[i]) > bestMoveScore) {
                            bestMoveScore = draw.computerMove(draw.blackPlayer[i]);
                            bestMove = i;
                        }
                    }

                    //check for castle
                    if (blackKingSelected && draw.castle(draw.blackPlayer[0],indexX,indexY)){
                        //left rook
                        if (indexX == 1 && indexY == 1){
                            draw.blackPlayer[0].setPosition(3,1);
                            draw.blackPlayer[2].setPosition(4,1);
                        }
                        //right rook
                        else{
                            draw.blackPlayer[0].setPosition(7,1);
                            draw.blackPlayer[3].setPosition(6,1);
                        }
                        draw.repaint();
                        whiteTurn = true;
                    }
                    if (blackQueenSelected && draw.castle(draw.blackPlayer[1],indexX,indexY)){
                        //left rook
                        if (indexX == 8 && indexY == 1){
                            draw.blackPlayer[1].setPosition(2,1);
                            draw.blackPlayer[2].setPosition(3,1);
                        }
                        //right rook
                        else{
                            draw.blackPlayer[1].setPosition(6,1);
                            draw.blackPlayer[2].setPosition(5,1);
                        }
                        draw.repaint();
                        whiteTurn = true;
                    }
                    //move piece
                    if (draw.pieceToMove >= 0 && pieceSelected &&
                            (draw.validMove(draw.blackPlayer[draw.pieceToMove], indexX,indexY) ||
                                    draw.validCapturePawn(draw.blackPlayer[draw.pieceToMove], indexX,indexY))){
                        boolean captured = false;
                        boolean moved = false;
                        //capture white piece
                        if (draw.pieceToMove >= 8 && draw.pieceToMove <= 15 && draw.whitePlayer[draw.pieceToMove].type == ChessPiece.PAWN){ //if pawn
                            for (int i = 0; i < draw.whitePlayer.length; i++){
                                if (indexX == draw.whitePlayer[i].x && indexY == draw.whitePlayer[i].y){
                                    if (draw.validCapturePawn(draw.blackPlayer[draw.pieceToMove],indexX, indexY)){
                                        draw.whitePlayer[i].setPosition(-1,-1);
                                        //move black piece
                                        draw.blackPlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                        captured = true;
                                    }
                                }
                            }
                        }
                        else{ //other pieces
                            for (int i = 0; i < draw.whitePlayer.length; i++){
                                if (indexX == draw.whitePlayer[i].x && indexY == draw.whitePlayer[i].y){
                                    draw.whitePlayer[i].setPosition(-1,-1);
                                    //move black piece
                                    draw.blackPlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                    captured = true;
                                }
                            }
                        }
                        //move onto empty space
                        if (!captured && !draw.validCapturePawn(draw.blackPlayer[draw.pieceToMove], indexX,indexY)){
                            boolean isWhitePiece = false;
                            for (int i = 0; i < draw.whitePlayer.length; i++) {
                                if (indexX == draw.whitePlayer[i].x && indexY == draw.whitePlayer[i].y) {
                                    isWhitePiece = true;
                                }
                            }
                            if (draw.blackPlayer[draw.pieceToMove].type != ChessPiece.PAWN) {
                                //move black piece
                                draw.blackPlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                moved = true;
                            }
                            //pawn can't capture straight
                            if (!isWhitePiece){
                                //move white piece
                                draw.blackPlayer[draw.pieceToMove].setPosition(indexX, indexY);
                                moved = true;
                            }
                        }
                        if (moved || captured){
                            if (draw.pieceToMove >= 8 && draw.pieceToMove <= 15 && draw.blackPlayer[draw.pieceToMove].y == 8){
                                draw.blackPlayer[draw.pieceToMove].promote();
                            }
                            whiteTurn = true;
                            pieceSelected = false;
                            draw.repaint();


                            //can still castle?
                            if (draw.blackPlayer[0].x != 5 || draw.blackPlayer[0].y != 1){
                                draw.blackKingMoved = true;
                            }
                            if (draw.blackPlayer[1].x != 4 || draw.blackPlayer[1].y != 1){
                                draw.blackQueenMoved = true;
                            }
                            if (draw.blackPlayer[2].x != 1 || draw.blackPlayer[2].y != 1){
                                draw.blackRookLeftMoved = true;
                            }
                            if (draw.blackPlayer[3].x != 8 || draw.blackPlayer[3].y != 1){
                                draw.blackRookRightMoved = true;
                            }
                        }
                    }
                }
            }
        });
    }

    private void initializeChess() {
        this.setTitle("Chess");
        this.pack();

        //scaling
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        getContentPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                draw.updateFrameSize();
            }
        });
        this.setMinimumSize(new Dimension(400, 480));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().add(this.draw);
        this.setVisible(true);
        this.repaint();
    }

    public static void main(String[] args) {
        ChessVsComputer frame = new ChessVsComputer();
    }
}
