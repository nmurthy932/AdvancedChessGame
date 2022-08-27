package com.chess.main;

import java.util.ArrayList;

public class Piece {

	public static ArrayList<ArrayList<Integer>> getMoves(int x, int y, String state){
		ArrayList<ArrayList<Integer>> options = getOptions(x, y, state);
		for(int i = options.size()-1; i>=0; i--) {
			if(isTheKingInCheck(x, y, options.get(i).get(0), options.get(i).get(1), state)) {
				options.remove(i);
			}
		}
		return options;
	}
	
	private static ArrayList<ArrayList<Integer>> getOptions(int x, int y, String state){
		char piece = ChessBoard.getStatePiece(x, y, state);
		ArrayList<ArrayList<Integer>> options;
		switch(Character.toLowerCase(piece)) {
		case 'p':
			options = Pawn.getMoves(piece, x, y, state);
			break;
		case 'r':
			options = Rook.getMoves(piece, x, y, state);
			break;
		case 'b':
			options = Bishop.getMoves(piece, x, y, state);
			break;
		case 'n':
			options = Knight.getMoves(piece, x, y, state);
			break;
		case 'q':
			options = Queen.getMoves(piece, x, y, state);
			break;
		case 'k':
			options = King.getMoves(piece, x, y, state);
			break;
		default:
			options = new ArrayList<ArrayList<Integer>>();
			break;
		}
		return options;
	}
	
	public static boolean inBounds(int x, int y) {
		if(x<0 || x>7 || y<0 || y>7) {
			return false;
		}
		else return true;
	}
	
	public static boolean isSameColor(char piece, int x, int y, String state) {
		if(Character.isUpperCase(piece)) {
			if(Character.isUpperCase(ChessBoard.getStatePiece(x, y, state))) {
				return true;
			}
			else return false;
		}
		else {
			if(Character.isLowerCase(ChessBoard.getStatePiece(x, y, state))) {
				return true;
			}
			else return false;
		}
	}
	
	public static boolean isTheKingInCheck(int x, int y, int newMoveX, int newMoveY, String state) {
		ChessBoard testBoard = new ChessBoard();
		testBoard.setState(state);
		testBoard.selected = new int[] {y, x};
		char piece = ChessBoard.getStatePiece(x, y, state);
		char captured = ChessBoard.getStatePiece(newMoveX, newMoveY, state);
		testBoard.updateState(piece, newMoveX, newMoveY, captured, x, y);
		for(int i = 0; i<8; i++) {
			for(int z = 0; z<8; z++) {
				char currPiece = ChessBoard.getStatePiece(z, i, testBoard.getState());
				if(Character.isAlphabetic(currPiece) && !isSameColor(currPiece, newMoveX, newMoveY, testBoard.getState())) {
					ArrayList<ArrayList<Integer>> options = Piece.getOptions(z, i, testBoard.getState());
					if(options.contains(testBoard.getKingCoordinates(testBoard.getState(), ChessBoard.getActiveColor(state)))) {
						return true;
					}
				}
				else if(Character.isDigit(currPiece)) {
					z+=Character.getNumericValue(currPiece)-1;
				}
			}
		}
		return false;
	}
	
	//TODO: fix the logic for after check is broken is the king still in check
	public static boolean checkMate(String state, int x, int y) {
		for(int i = 0; i<8; i++) {
			for(int z = 0; z<8; z++) {
				char currPiece = ChessBoard.getStatePiece(z, i, state);
				if(ChessBoard.isPiece(z, i, state) && !Piece.isSameColor(currPiece, x, y, state)) {
					if(Piece.getMoves(z, i, state).size() > 0) {
						for(ArrayList<Integer> thing : Piece.getMoves(z, i, state)) {
							if(!isTheKingInCheck(z, i, thing.get(0), thing.get(1), state)) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}
}
