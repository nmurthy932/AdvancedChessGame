package com.chess.main;

import java.util.ArrayList;

public class ChessAI {

    ChessBoard board;

    public ChessAI(ChessBoard board){
        this.board = board;
    }

    //TODO: Make this actually run kind of
    public void makeMove(){
        String state = board.getState();
        int max = Integer.MIN_VALUE;
        int depth = 2;
        ArrayList<ArrayList<Integer>> startingCoords = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> endingCoords = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> allMoves = ChessBoard.getAllLegalMoves(state, ChessBoard.getActiveColor(state));
        for(ArrayList<Integer> coords : allMoves){
            ArrayList<ArrayList<Integer>> options = Piece.getMoves(coords.get(0), coords.get(1), state);
            for(ArrayList<Integer> optionCoords : options) {
                ChessBoard board = new ChessBoard(state);
                board.selected = new int[]{coords.get(0), coords.get(1)};
                board.makeMove(optionCoords.get(0), optionCoords.get(1));
                int score = -negamax(board.getState(), depth - 1);
                if (score > max) {
                    max = score;
                    startingCoords.clear();
                    startingCoords.add(coords);
                    endingCoords.clear();
                    endingCoords.add(optionCoords);
                }
                else if(score == max){
                    startingCoords.add(coords);
                    endingCoords.add(optionCoords);
                }
            }
        }
        System.out.println(startingCoords.toString());
        System.out.println(endingCoords.toString());
    }

    public int negamax(String state, int depth){
        if(depth == 0 || board.gameOver){
            return value(board);
        }
        int max = Integer.MIN_VALUE;
        ArrayList<ArrayList<Integer>> allMoves = ChessBoard.getAllLegalMoves(state, ChessBoard.getActiveColor(state));
        for(ArrayList<Integer> coords : allMoves){
            ArrayList<ArrayList<Integer>> options = Piece.getMoves(coords.get(0), coords.get(1), state);
            for(ArrayList<Integer> optionCoords : options) {
                ChessBoard board = new ChessBoard(state);
                board.selected = new int[]{coords.get(0), coords.get(1)};
                board.makeMove(optionCoords.get(0), optionCoords.get(1));
                int score = -negamax(board.getState(), depth - 1);
                if (score > max) {
                    max = score;
                }
            }
        }
        return max;
    }

    private int value(ChessBoard board) {
        String allPieces = ChessBoard.getActivePieces(board.getState());
        int materialScore = King.value*(numPieces(allPieces, "K"))
                + Queen.value*(numPieces(allPieces, "Q"))
                + Bishop.value*(numPieces(allPieces, "B"))
                + Knight.value*(numPieces(allPieces, "N"))
                + Rook.value*(numPieces(allPieces, "R"))
                + Pawn.value*(numPieces(allPieces, "P"));
        //Set mobility weight to whatever works best
        int mobilityWeight = 5;
        String state = board.getState();
        int mobilityScore = mobilityWeight*(ChessBoard.getAllLegalMoves(state, "w").size() - ChessBoard.getAllLegalMoves(state, "b").size());
        int whoMoves;
        if(ChessBoard.getActiveColor(board.getState()) == "w"){
            whoMoves = 1;
        }
        else{
            whoMoves = -1;
        }
        return (materialScore+mobilityScore)*whoMoves;
    }

    private int numPieces(String allPieces, String piece){
        return ((allPieces.length())-(allPieces.replace(piece, "").length()))-((allPieces.length())-(allPieces.replace(piece.toLowerCase(), "").length()));
    }
}
