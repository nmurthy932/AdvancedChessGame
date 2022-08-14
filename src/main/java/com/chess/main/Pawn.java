package com.chess.main;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	public static ArrayList<ArrayList<Integer>> getMoves(char piece, int x, int y, String state) {
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>();
		if(piece == 'P') {
			if(y == 6 && inBounds(x, y-2) && !ChessBoard.isPiece(x, y-2, state) && !ChessBoard.isPiece(x, y-1, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(y-2);
				legalMoves.add(t);
			}
			if(inBounds(x, y-1) && !ChessBoard.isPiece(x, y-1, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(y-1);
				legalMoves.add(t);
			}
			if(inBounds(x-1, y-1) && ChessBoard.isPiece(x-1, y-1, state) && Character.isLowerCase(ChessBoard.getStatePiece(x-1, y-1, state))){
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x-1); t.add(y-1);
				legalMoves.add(t);
			}
			if(inBounds(x+1, y-1) && ChessBoard.isPiece(x+1, y-1, state) && Character.isLowerCase(ChessBoard.getStatePiece(x+1, y-1, state))){
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x+1); t.add(y-1);
				legalMoves.add(t);
			}
			String enPassant = ChessBoard.getEnPassant(state);
			if(!enPassant.equals("-")) {
				int[] enPassantCoords = ChessBoard.stringPieceToCoords(enPassant, ChessBoard.squareNames);
				if(y-1 == enPassantCoords[1] && (x == enPassantCoords[0]+1 || x == enPassantCoords[0]-1)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(enPassantCoords[0]); t.add(enPassantCoords[1]);
					legalMoves.add(t);
				}
			}
		}
		else if(piece == 'p'){
			if(y == 1 && inBounds(x, y+2) &&  !ChessBoard.isPiece(x, y+2, state) && !ChessBoard.isPiece(x, y+1, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(y+2);
				legalMoves.add(t);
			}
			if(inBounds(x, y+1) && !ChessBoard.isPiece(x, y+1, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(y+1);
				legalMoves.add(t);
			}
			if(inBounds(x-1, y+1) && ChessBoard.isPiece(x-1, y+1, state) && Character.isUpperCase(ChessBoard.getStatePiece(x-1, y+1, state))){
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x-1); t.add(y+1);
				legalMoves.add(t);
			}
			if(inBounds(x+1, y+1) && ChessBoard.isPiece(x+1, y+1, state) && Character.isUpperCase(ChessBoard.getStatePiece(x+1, y+1, state))){
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x+1); t.add(y+1);
				legalMoves.add(t);
			}
			String enPassant = ChessBoard.getEnPassant(state);
			if(!enPassant.equals("-")) {
				int[] enPassantCoords = ChessBoard.stringPieceToCoords(enPassant, ChessBoard.squareNames);
				if(y+1 == enPassantCoords[1] && (x == enPassantCoords[0]+1 || x == enPassantCoords[0]-1)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(enPassantCoords[0]); t.add(enPassantCoords[1]);
					legalMoves.add(t);
				}
			}
		}
		return legalMoves;
	}
	
}
