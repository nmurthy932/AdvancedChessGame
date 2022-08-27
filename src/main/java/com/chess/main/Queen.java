package com.chess.main;

import java.util.ArrayList;

public class Queen extends Piece{

	public static final int value = 9;
	public static ArrayList<ArrayList<Integer>> getMoves(char piece, int x, int y, String state){
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>();
		legalMoves.addAll(Rook.getMoves(piece, x, y, state));
		legalMoves.addAll(Bishop.getMoves(piece, x, y, state));
		return legalMoves;
	}
	
}
