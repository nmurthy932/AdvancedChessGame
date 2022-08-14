package com.chess.main;

import java.util.ArrayList;

public class Knight extends Piece{

	public static ArrayList<ArrayList<Integer>> getMoves(char piece, int x, int y, String state){
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>();
		int xloc = x-1;
		int yloc = y-2;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x-1;
		yloc = y+2;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+1;
		yloc = y-2;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+1;
		yloc = y+2;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x-2;
		yloc = y-1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x-2;
		yloc = y+1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+2;
		yloc = y-1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+2;
		yloc = y+1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		
		return legalMoves;
	}
	
}
