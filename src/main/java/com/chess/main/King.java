package com.chess.main;

import java.util.ArrayList;

public class King extends Piece{

	public static final int value = 900;
	public static ArrayList<ArrayList<Integer>> getMoves(char piece, int x, int y, String state){
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>();
		int xloc = x-1;
		int yloc = y-1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x-1;
		yloc = y+1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+1;
		yloc = y-1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+1;
		yloc = y+1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x;
		yloc = y-1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x;
		yloc = y+1;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x-1;
		yloc = y;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+1;
		yloc = y;
		if(inBounds(xloc, yloc) && (!ChessBoard.isPiece(xloc, yloc, state) || !isSameColor(piece, xloc, yloc, state))) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		xloc = x+2;
		yloc = y;
		if((ChessBoard.getCastles(state).contains("k") || ChessBoard.getCastles(state).contains("K")) && !ChessBoard.isPiece(xloc, yloc, state) && !ChessBoard.isPiece(xloc-1, yloc, state)) {
			ArrayList<Integer> t = new ArrayList<Integer>();
			t.add(xloc); t.add(yloc);
			legalMoves.add(t);
		}
		if(((ChessBoard.getCastles(state).contains("k") || ChessBoard.getCastles(state).contains("q")) && Character.isLowerCase(piece)) || ((ChessBoard.getCastles(state).contains("K") || ChessBoard.getCastles(state).contains("Q")) && Character.isUpperCase(piece))) {
			xloc = x+2;
			yloc = y;
			if(!ChessBoard.isPiece(xloc, yloc, state) && !ChessBoard.isPiece(xloc-1, yloc, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(xloc); t.add(yloc);
				legalMoves.add(t);
			}
			xloc = x-2;
			yloc = y;
			if(ChessBoard.getCastles(state).toLowerCase().contains("q") && !ChessBoard.isPiece(xloc, yloc, state) && !ChessBoard.isPiece(xloc-1, yloc, state) && inBounds(xloc+1, yloc) && !ChessBoard.isPiece(xloc+1, yloc, state)) {
				ArrayList<Integer> t = new ArrayList<Integer>();
				t.add(xloc); t.add(yloc);
				legalMoves.add(t);
			}
		}
		return legalMoves;
	}
	
}
