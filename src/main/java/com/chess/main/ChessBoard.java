package com.chess.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class ChessBoard extends MouseAdapter{
	
	private String state = "";
	private ArrayList<String> pastStates = new ArrayList<String>();
	public Map<Character, String> pieceNames =  new HashMap<Character, String>();
	public int[] selected;
	public boolean mouseover;
	public static Map<Character, Integer> squareNames = new HashMap<Character, Integer>();
	public ArrayList<ArrayList<Integer>> options;
	public boolean gameOver;
	public boolean blackPOV;
	//public ChessAI chessAI;
	
	public ChessBoard() {
		//original state: rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1
		this("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
	}

	public ChessBoard(String state){
		this.state = state;

		squareNames.put('a', 0);
		squareNames.put('b', 1);
		squareNames.put('c', 2);
		squareNames.put('d', 3);
		squareNames.put('e', 4);
		squareNames.put('f', 5);
		squareNames.put('g', 6);
		squareNames.put('h', 7);

		pieceNames.put('R', "Rook");
		pieceNames.put('N', "Knight");
		pieceNames.put('B', "Bishop");
		pieceNames.put('K', "King");
		pieceNames.put('Q', "Queen");
		pieceNames.put('P', "Pawn");
		selected = new int[] {-1, -1};

		options = new ArrayList<ArrayList<Integer>>();
		mouseover = true;
		gameOver = false;
		pastStates.add(state);
		blackPOV = false;

		//chessAI = new ChessAI(this);
	}
	
	
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	/*
	 * THREE FUNCTIONS THAT RETURN THE PIECES ON ANY BOARD
	 */
	
	//Returns the current pieces on the board
	public static String getStatePieces(String state) {
		if(state != null) return state.split(" ")[0];
		else return null;
	}
	
	public void setStatePieces(String state) {
		if(getState() != null) {
			setState(getState().replace(getStatePieces(getState()), state));
		}
	}
	
	//Returns the current pieces on the board in a specified row
	public static String getStatePiecesInRow(int i, String state) {
		if(getStatePieces(state) != null && getStatePieces(state).split("/").length > i) {
			return getStatePieces(state).split("/")[i];
		}
		else return null;
	}
	
	public void setStatePiecesInRow(int i, String row) {
		String state = getStatePieces(getState());
		if(state != null & state.split("/").length > i) {
			String newState = "";
			String[] stateArray = state.split("/");
			for(int x = 0; x<state.split("/").length; x++) {
				newState+="/";
				if(x == i) {
					newState+=row;
				}
				else {
					newState+=stateArray[x];
				}
			}
			setStatePieces(newState.substring(1));
		}
	}
	
	//Returns a specific piece on the board
	public static char getStatePiece(int x, int y, String state) {
		if(getStatePiecesInRow(y, state) != null && Piece.inBounds(x, y)) {
			String s = getStatePiecesInRow(y, state);
			int i = 0;
			int place = -1;
			while(i<=x && place+1 < s.length()) {
				place++;
				char piece = s.charAt(place);
				if(Character.isDigit(piece)) {
					i+=Character.getNumericValue(piece);
				}
				else {
					i++;
				}
			}
			if(place >= 0 && place < s.length())
				return getStatePiecesInRow(y, state).charAt(place);
			else return Character.MIN_VALUE;
		}
		else return Character.MIN_VALUE;
	}
	
	//Doesn't return the number of spaces between pieces but a null character
	public static char getStatePieceIfNull(int x, int y, String state) {
		if(getStatePiecesInRow(y, state) != null && Piece.inBounds(x, y)) {
			String s = getStatePiecesInRow(y, state);
			int i = 0;
			int place = -1;
			while(i<=x && place+1 < s.length()) {
				place++;
				char piece = s.charAt(place);
				if(Character.isDigit(piece)) {
					i+=Character.getNumericValue(piece);
				}
				else {
					i++;
				}
			}
			if(place >= 0 && place < s.length() && !Character.isDigit(getStatePiecesInRow(y, state).charAt(place)))
				return getStatePiecesInRow(y, state).charAt(place);
			else return Character.MIN_VALUE;
		}
		else return Character.MIN_VALUE;
	}
	
	public void setStatePiece(int x, int y, char piece) {
		if(getStatePiecesInRow(y, state) != null) {
			String currentRow = getStatePiecesInRow(y, state);
			String newRow;
			char currPiece = 0;
			int i = 0;
			int place = -1;
			while(i<=x && place+1 < currentRow.length()) {
				place++;
				currPiece = currentRow.charAt(place);
				if(Character.isDigit(currPiece)) {
					i+=Character.getNumericValue(currPiece);
				}
				else {
					i++;
				}
			}
			if(piece != Character.MIN_VALUE) {
				if(Character.isDigit(currPiece)) {
					int before = Character.getNumericValue(currPiece)-(i-x);
					if(before > 0)
						if(i-x-1 > 0)
							newRow = currentRow.substring(0,place)+before+piece+(i-x-1)+currentRow.substring(place+1);
						else
							newRow = currentRow.substring(0,place)+before+piece+currentRow.substring(place+1);
					else {
						if(i-x-1 > 0)
							newRow = currentRow.substring(0,place)+piece+(i-x-1)+currentRow.substring(place+1);
						else
							newRow = currentRow.substring(0,place)+piece+currentRow.substring(place+1);
					}
				}
				else {
					newRow = currentRow.substring(0, place)+piece+currentRow.substring(place+1);
				}
			}
			else {
				int val = 1;
				if(place-1>=0) {
					newRow = currentRow.substring(0, place-1);
					if(place-1 >= 0 && Character.isDigit(currentRow.charAt(place-1))) {
						val+=Character.getNumericValue(currentRow.charAt(place-1));
					}
					else if(place >= 1) {
						newRow+=currentRow.charAt(place-1);
					}
				}
				else {
					newRow = "";
				}
				if(place+1 < currentRow.length() && Character.isDigit(currentRow.charAt(place+1))) {
					val += Character.getNumericValue(currentRow.charAt(place+1));
				}
				newRow+=val;
				if(place+1< currentRow.length() && Character.isAlphabetic(currentRow.charAt(place+1))) {
					newRow+=currentRow.charAt(place+1);
				}
				if(place+2< currentRow.length())
					newRow+=currentRow.substring(place+2);
			}
			setStatePiecesInRow(y, newRow);
		}
	}
	
	public static boolean isPiece(int x, int y, String state) {
		if(getStatePiece(x, y, state) != Character.MIN_VALUE)
			return Character.isAlphabetic(getStatePiece(x, y, state));
		else return false;
	}
	
	public static String getActiveColor(String state) {
		if(state != null) {
			return state.split(" ")[1];
		}
		else return null;
	}
	
	public void setActiveColor(String state) {
		if(state != null) {
			String[] fullState = state.split(" ");
			String otherColor;
			if(fullState[1].equals("w")) {
				otherColor = "b";
			}
			else {
				otherColor = "w";
			}
			String newState = fullState[0]+" "+otherColor+" "+fullState[2]+" "+fullState[3]+" "+fullState[4]+" "+fullState[5];
			setState(newState);
		}
	}
	
	public static String getCastles(String state) {
		if(state != null) {
			return state.split(" ")[2];
		}
		else return null;
	}
	
	public void setCastles(String state, String castles) {
		String[] fullState = state.split(" ");
		if(castles == "") {
			castles = "-";
		}
		String newState = fullState[0]+" "+fullState[1]+" "+castles+" "+fullState[3]+" "+fullState[4]+" "+fullState[5];
		setState(newState);
	}
	
	public static String getEnPassant(String state) {
		if(state != null) {
			return state.split(" ")[3];
		}
		else return null;
	}
	
	public void setEnPassant(String state, String squarePawnMovedOver) {
		String[] fullState = state.split(" ");
		String newState = fullState[0]+" "+fullState[1]+" "+fullState[2]+" "+squarePawnMovedOver+" "+fullState[4]+" "+fullState[5];
		setState(newState);
	}
	
	public static String getHalfMoveClock(String state) {
		if(state != null) {
			return state.split(" ")[4];
		}
		else return null;
	}
	
	public void setHalfMoveClock(String state, String halfMoves) {
		String[] fullState = state.split(" ");
		String newState = fullState[0]+" "+fullState[1]+" "+fullState[2]+" "+fullState[3]+" "+halfMoves+" "+fullState[5];
		setState(newState);
	}
	
	public static String getFullMoveClock(String state) {
		if(state != null) {
			return state.split(" ")[5];
		}
		else return null;
	}
	
	public void setFullMoveClock(String state, String fullMoves) {
		String[] fullState = state.split(" ");
		String newState = fullState[0]+" "+fullState[1]+" "+fullState[2]+" "+fullState[3]+" "+fullState[4]+" "+fullMoves;
		setState(newState);
	}

	public static String getActivePieces(String state){
		String[] pieceState = ChessBoard.getStatePieces(state).split("");
		String pieces = "";
		for(String s : pieceState){
			if(s.matches("[a-zA-Z]]")){
				pieces+=s;
			}
		}
		return pieces;
	}
	
	public static int[] stringPieceToCoords(String piece, Map<Character, Integer> squareNames) {
		if(piece == "-") {
			return null;
		}
		return (new int[] {(int) squareNames.get(piece.charAt(0)), 8-Character.getNumericValue(piece.charAt(1))});
	}
	
	public static String coordsToStringPiece(int x, int y) {
		Set<Character> keySet = squareNames.keySet();
		char xLetter = Character.MIN_VALUE;
		for(char c : keySet) {
			if(squareNames.get(c) == x) {
				xLetter = c;
				break;
			}
		}
		String enPassantSquare;
		enPassantSquare = xLetter+Integer.toString(8-y);
		return enPassantSquare;
	}
	
	public ArrayList<Integer> getSquareCoordsAsList(int x, int y){
		ArrayList<Integer> coords = new ArrayList<Integer>();
		coords.add(x);
		coords.add(y);
		return coords;
	}
	
	public ArrayList<Integer> getKingCoordinates(String state, String side) {
		String currentBoard = getStatePieces(state);
		String[] stateArray = currentBoard.split("/");
		ArrayList<Integer> coords = new ArrayList<Integer>();
		for(int i = 0; i<stateArray.length; i++) {
			if((side.equals("w") && stateArray[i].contains("K")) || (side.equals("b") && stateArray[i].contains("k"))) {
				coords.add(i);
				String row = stateArray[i];
				int y = 0;
				for(int x = 0; x<row.length(); x++) {
					if((side.equals("w") && row.charAt(x) == 'K') || (side.equals("b") && row.charAt(x) == 'k')) {
						coords.add(0,y);
						return coords;
					}
					else {
						if(Character.isAlphabetic(row.charAt(x))) {
							y++;
						}
						else {
							y+=Character.getNumericValue(row.charAt(x));
						}
					}
				}
			}
		}
		return null;
	}
	
	public void setSquareColor(Graphics g, Color newColor, int x, int y, boolean reversed) {
		Color c = g.getColor();
		if(newColor != null)
			g.setColor(newColor);
		if(reversed) {
			x = 7-x;
			y = 7-y;
		}
		g.fillRect(Game.WIDTH / 5 + x * 48, 32 + y * 48, 48, 48);
		if(c == Color.gray)
			g.setColor(Color.white);
		else
			g.setColor(Color.gray);
	}
	
	public void clearBoard() {
		selected[0] = -1;
		selected[1] = -1;
		options.clear();
	}
	
	//Updates the state of the game: piece: current chess piece; captured: square piece was moved to; x, y: new coords piece is moved to; row, col: old coords piece was at
	public void updateState(char piece, int x, int y, char captured, int row, int col) {
		//sets piece move
		setStatePiece(x, y, piece);
		setStatePiece(row, col, Character.MIN_VALUE);
		//half move clock
		if(Character.toLowerCase(piece) == 'p' || Character.isAlphabetic(captured)) {
			setHalfMoveClock(getState(), "0");
		}
		else {
			setHalfMoveClock(getState(), Integer.toString(Integer.parseInt(getHalfMoveClock(getState()))+1));
		}
		//full move clock
		setFullMoveClock(getState(), Integer.toString(Integer.parseInt(getFullMoveClock(getState()))+1));
		//castling rules
		if(piece == 'r' && col == 0) {
			if(row == 0) {
				setCastles(getState(), getCastles(getState()).replace("q", ""));
			}
			else if(row == 7) {
				setCastles(getState(), getCastles(getState()).replace("k", ""));
			}
		}
		else if(piece == 'R' && col == 7) {
			if(row == 0) {
				setCastles(getState(), getCastles(getState()).replace("Q", ""));
			}
			else if(row == 7) {
				setCastles(getState(), getCastles(getState()).replace("K", ""));
			}
		}
		else if(piece == 'k' && col == 0 && row == 4) {
			setCastles(getState(), getCastles(getState()).replace("k", ""));
			setCastles(getState(), getCastles(getState()).replace("q", ""));
			if(Math.abs(row-x) == 2) {
				int rookX;
				if(x<row) {
					rookX = x-2;
					char rook = getStatePiece(rookX, y, state);
					setStatePiece(x+1, y, rook);
					setStatePiece(rookX, y, Character.MIN_VALUE);
				}
				else {
					rookX = x+1;
					char rook = getStatePiece(rookX, y, state);
					setStatePiece(x-1, y, rook);
					setStatePiece(rookX, y, Character.MIN_VALUE);
				}
			}
		}
		else if(piece == 'K' && col == 7 && row == 4) {
			setCastles(getState(), getCastles(getState()).replace("K", ""));
			setCastles(getState(), getCastles(getState()).replace("Q", ""));
			if(Math.abs(row-x) == 2) {
				int rookX;
				if(x<row) {
					rookX = x-2;
					char rook = getStatePiece(rookX, y, state);
					setStatePiece(x+1, y, rook);
					setStatePiece(rookX, y, Character.MIN_VALUE);
				}
				else {
					rookX = x+1;
					char rook = getStatePiece(rookX, y, state);
					setStatePiece(x-1, y, rook);
					setStatePiece(rookX, y, Character.MIN_VALUE);
				}
			}
		}
		//check en passant
		if(Character.toLowerCase(piece) == 'p') {
			if(!getEnPassant(getState()).equals("-")) {
				if(Arrays.equals(stringPieceToCoords(getEnPassant(getState()), squareNames), new int[] {x, y})) {
					int enPassantX;
					int enPassantY;
					if(piece == 'P') {
						enPassantX = x;
						enPassantY = y+1;
					}
					else {
						enPassantX = x;
						enPassantY = y-1;
					}
					if(isPiece(enPassantX, enPassantY, getState()) && Character.toLowerCase(getStatePiece(enPassantX, enPassantY, getState())) == 'p') {
						setStatePiece(enPassantX, enPassantY, Character.MIN_VALUE);
					}
				}
			}
		}
		//sets en passant
		if(Character.toLowerCase(piece) == 'p' && Math.abs(col-y) == 2) {
			if((isPiece(x+1, y, state) && Character.toLowerCase(getStatePiece(x+1, y, state)) == 'p' && !Piece.isSameColor(piece, x+1, y, state)) || (isPiece(x-1, y, state) && Character.toLowerCase(getStatePiece(x-1, y, state)) == 'p' && !Piece.isSameColor(piece, x-1, y, state))) {
				if(Character.isLowerCase(piece))
					setEnPassant(getState(), coordsToStringPiece(x, y-1));
				else
					setEnPassant(getState(), coordsToStringPiece(x, y+1));
			}
			else {
				setEnPassant(getState(), "-");
			}
		}
		else {
			setEnPassant(getState(), "-");
		}
		//sets the active color
		setActiveColor(getState());
	}
	
	/**
	 * @return the pastStates
	 */
	public ArrayList<String> getPastStates() {
		return pastStates;
	}
	/**
	 * @param pastStates the pastStates to set
	 */
	public void setPastStates(ArrayList<String> pastStates) {
		this.pastStates = pastStates;
	}
	
	public static ArrayList<ArrayList<Integer>> getChangedSquares(ArrayList<String> pastStates) {
		String mostRecent = pastStates.get(pastStates.size()-1);
		String earlier = pastStates.get(pastStates.size()-2);
		String[] mostRecentList = ChessBoard.getStatePieces(mostRecent).split("/");
		String[] earlierList = ChessBoard.getStatePieces(earlier).split("/");
		ArrayList<Integer> changedLines = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> changedSquares = new ArrayList<ArrayList<Integer>>();
		for(int v = 0; v<mostRecentList.length; v++) {
			if(!mostRecentList[v].equals(earlierList[v])) {
				changedLines.add(v);
				for(int i = 0; i<8; i++) {
					if(ChessBoard.getStatePieceIfNull(i, v, earlier) != ChessBoard.getStatePieceIfNull(i, v, mostRecent)) {
						ArrayList<Integer> t = new ArrayList<Integer>();
						t.add(i); t.add(v);
						changedSquares.add(t);
					}
				}
			}
		}
		return changedSquares;
	}
	
	/*
	 * MOUSE DETECTION
	 */
	
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		// play button
		if (mouseOver(mx, my, Game.WIDTH / 5, 32, 64 * 6, 64 * 6)) {
			int x;
			int y;
			if(!blackPOV) {
				x = (mx-Game.WIDTH/5)/48;
				y = (my-32)/48;
			}
			else {
				x = 7-(mx-Game.WIDTH/5)/48;
				y = 7-(my-32)/48;
			}
			if(selected[1] == x && selected[0] == y) {
				clearBoard();
			}
			else if(options.contains(getSquareCoordsAsList(x, y))) {
				makeMove(x, y);
				clearBoard();
			}
			else {
				if(isPiece(x, y, getState())) {
					if(getActiveColor(getState()).equals("w") && Character.isUpperCase(getStatePiece(x, y, getState()))) {
						options = Piece.getMoves(x, y, state);
					}
					else if(getActiveColor(getState()).equals("b") && Character.isLowerCase(getStatePiece(x, y, getState()))) {
						options = Piece.getMoves(x, y, state);
					}
					else {
						return;
					}
				}
				else {
					clearBoard();
				}
				selected[1] = x;
				selected[0] = y;
			}
			mouseover = true;
		}
		//coordinates of the reverse screen button
		if(mouseOver(mx, my, 50, 50, 30, 30)) {
			blackPOV = !blackPOV;
			mouseover = true;
		}
	}
	
	public void makeMove(int x, int y) {
		int row = selected[1];
		int col = selected[0];
		char piece = getStatePiece(row, col, getState());
		char captured = getStatePiece(x, y, getState());
		updateState(piece, x, y, captured, row, col);
		if(Piece.checkMate(getState(), x, y)) {
			gameOver = true;
		}
		pastStates.add(getState());
		System.out.println("Made move");
	}


	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else
				return false;
		} else
			return false;
	}
	
	/*
	 * GET LEGAL MOVES
	 */
	
	public static ArrayList<ArrayList<Integer>> getAllLegalMoves(String state, String color){
		ArrayList<ArrayList<Integer>> everyMove = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i<8; i++){
			for(int x = 0; x<8; x++){
				char piece = getStatePiece(x, i, state);
				if(isPiece(x, i, state)) {
					if ((color.equals("b") && Character.isLowerCase(piece)) || (color.equals("w") && Character.isUpperCase(getStatePiece(x, i, state)))) {
						ArrayList<Integer> coords = new ArrayList<Integer>();
						coords.add(x); coords.add(i);
						everyMove.add(coords);
					}
				}
			}
		}
		return everyMove;
	}
	
	
	/*
	 * RENDER FUNCTIONS
	 */
	
	//Converts the letter representation into the image
	public BufferedImage toImage(char s) {
		String piece = pieceNames.get(Character.valueOf(Character.toUpperCase(s)));
		if(piece == null) {
			return null;
		}
		File file;
		if(Character.isUpperCase(s)) {
			file = new File("C:/Users/nitin/eclipse-workspace/ChessGameLite/src/Data/"+piece+".png");
		}
		else {
			file = new File("C:/Users/nitin/eclipse-workspace/ChessGameLite/src/Data/"+piece+"2.png");
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	//Creates the border and colors the squares
	public void createChessBoard(Graphics g) {
		g.drawRect(Game.WIDTH / 5, 32, 64 * 6, 64 * 6);
		g.setColor(Color.gray);
		if(!blackPOV) {
			for (int i = 0; i < 8; i++) {
				if(g.getColor() == Color.gray) {
					g.setColor(Color.white);
				}
				else {
					g.setColor(Color.gray);
				}
				for (int x = 0; x < 8; x++) {
					//highlights selected color green
					if(selected[0] == i && selected[1] == x && isPiece(x, i, state)){
						setSquareColor(g, Color.green, x, i, false);
					}
					//highlights options green
					else if(options.contains(getSquareCoordsAsList(x,i))) {
						setSquareColor(g, Color.blue, x, i, false);
					}
					//highlights the last move made
					else if(pastStates.size() >= 2 && ChessBoard.getChangedSquares(pastStates).contains(getSquareCoordsAsList(x,i))) {
						setSquareColor(g, Color.red, x, i, false);
					}
					//colors the majority of the squares
					else {
						setSquareColor(g, null, x, i, false);
					}
					//Draws border around the squares
					Color c = g.getColor();
					g.setColor(Color.black);
					g.drawRect(Game.WIDTH / 5 + x * 48, 32 + i * 48, 48, 48);
					g.setColor(c);
				}
			}
		}
		else {
			for (int i = 0; i < 8; i++) {
				if(g.getColor() == Color.gray) {
					g.setColor(Color.white);
				}
				else {
					g.setColor(Color.gray);
				}
				for (int x = 0; x < 8; x++) {
					//highlights selected color green
					if(selected[0] == i && selected[1] == x && isPiece(x, i, state)){
						setSquareColor(g, Color.green, x, i, true);
					}
					//highlights options green
					else if(options.contains(getSquareCoordsAsList(x,i))) {
						setSquareColor(g, Color.blue, x, i, true);
					}
					//highlights the last move made
					else if(pastStates.size() >= 2 && ChessBoard.getChangedSquares(pastStates).contains(getSquareCoordsAsList(x,i))) {
						setSquareColor(g, Color.red, x, i, true);
					}
					//colors the majority of the squares
					else {
						setSquareColor(g, null, x, i, true);
					}
					//Draws border around the squares
					Color c = g.getColor();
					g.setColor(Color.black);
					g.drawRect(Game.WIDTH / 5 + (7-x) * 48, 32 + (7-i) * 48, 48, 48);
					g.setColor(c);
				}
			}
		}
	}
	
	public void drawPieces(Graphics g) {
		if(!blackPOV) {
			for(int i = 0; i < 8; i++) {
				for(int x = 0; x < 8; x++) {
					char piece = getStatePiece(x,i,getState());
					if(piece != Character.MIN_VALUE && Character.isAlphabetic(piece)) {
						g.drawImage(toImage(piece), Game.WIDTH / 5 + x * 48, 32 + i * 48, 48, 48, null);
					}
					else if(Character.isDigit(piece)){
						x+=Character.getNumericValue(piece)-1;
					}
				}
			}
		}
		else {
			for(int i = 0; i < 8; i++) {
				for(int x = 0; x < 8; x++) {
					char piece = getStatePiece(7-x,7-i,getState());
					if(piece != Character.MIN_VALUE && Character.isAlphabetic(piece)) {
						g.drawImage(toImage(piece), Game.WIDTH / 5 + x * 48, 32 + i * 48, 48, 48, null);
					}
					else if(Character.isDigit(piece)){
						x+=Character.getNumericValue(piece)-1;
					}
				}
			}
		}
	}
	
	public void drawControls(Graphics g) {
		DrawShapes.drawArrow(g);
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		Font fnt = new Font("arial",1,36);
		g.setFont(fnt);
		if(mouseover && !gameOver) {
			createChessBoard(g);
			drawPieces(g);
			if(getActiveColor(getState()).equals("w")) {
				g.setColor(Color.decode("#eeeeee"));
				g.drawString("Turn: Black", 0, 30);
				g.setColor(Color.black);
				g.drawString("Turn: White", 0, 30);
			}
			else {
				g.setColor(Color.decode("#eeeeee"));
				g.drawString("Turn: White", 0, 30);
				g.setColor(Color.black);
				g.drawString("Turn: Black", 0, 30);
			}
			mouseover = false;
		}
		else if(gameOver) {
			createChessBoard(g);
			drawPieces(g);
			g.setColor(Color.red);
			g.drawString("Game Over", Game.WIDTH/2, Game.HEIGHT/2);
		}
		drawControls(g);
	}
	
}
