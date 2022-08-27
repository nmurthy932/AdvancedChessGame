package com.chess.main;

import java.util.ArrayList;

public class Rook extends Piece{

	public static final int value = 5;
	public static ArrayList<ArrayList<Integer>> getMoves(char piece, int x, int y, String state){
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>();
		for(int i = x-1; i>=0; i--) {
			if(inBounds(i, y) && !ChessBoard.isPiece(i, y, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(i); t.add(y);
				legalMoves.add(t);
			}
			else {
				if(inBounds(i, y) && !isSameColor(piece, i, y, state)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(i); t.add(y);
					legalMoves.add(t);
				}
				break;
			}
		}
		for(int i = x+1; i<=7; i++) {
			if(inBounds(i, y) && !ChessBoard.isPiece(i, y, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(i); t.add(y);
				legalMoves.add(t);
			}
			else {
				if(inBounds(i, y) && !isSameColor(piece, i, y, state)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(i); t.add(y);
					legalMoves.add(t);
				}
				break;
			}
		}
		for(int i = y-1; i>=0; i--) {
			if(inBounds(x, i) && !ChessBoard.isPiece(x, i, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(i);
				legalMoves.add(t);
			}
			else {
				if(inBounds(x, i) && !isSameColor(piece, x, i, state)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x); t.add(i);
					legalMoves.add(t);
				}
				break;
			}
		}
		for(int i = y+1; i<=7; i++) {
			if(inBounds(x, i) && !ChessBoard.isPiece(x, i, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(x); t.add(i);
				legalMoves.add(t);
			}
			else {
				if(inBounds(x, i) && !isSameColor(piece, x, i, state)) {
					ArrayList<Integer> t = new ArrayList<Integer>();
					t.add(x); t.add(i);
					legalMoves.add(t);
				}
				break;
			}
		}
		
		return legalMoves;
	}
	
}
