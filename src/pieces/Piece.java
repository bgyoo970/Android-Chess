package pieces;

import java.io.Serializable;

import chess.Chess;

import com.example.chess.Play;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
public class Piece implements Serializable{
	private static final long serialVersionUID = 2576314836382328085L;
	public char color;
	public String pieceName;
	Coordinate coordinate;
	String position;
	
	// Needed for using methods in other classes.
	public Piece() {
	}
	
	protected Piece(char color, String pieceName, String position) {
		this.color = color;
		this.pieceName = pieceName;
		//this.position = position;
		this.coordinate = convert(position);
	}
	
	public void movePiece(String src, String dest, boolean whitesTurn){
		Coordinate source = convert(src);
		Coordinate destination = convert(dest);
		//Piece piece = Chess.board[source.xCoord][source.yCoord];
		//Chess.board[source.xCoord][source.yCoord] = null;
		//Chess.board[destination.xCoord][destination.yCoord] = piece;
		
		Piece piece = Play.board[source.xCoord][source.yCoord];
		Play.board[source.xCoord][source.yCoord] = null;
		Play.board[destination.xCoord][destination.yCoord] = piece;
	}
	public boolean canMove(String src, String dest, boolean whitesTurn){
		// To be Overwritten by other classes (Override)
		return false;
	}
	
	/**
	 * Get the piece at the given coordinate
	 * @param coord variable that holds the coordinates to the piece
	 * @return piece at the coordinate
	 */
	public Piece getPiece(Coordinate coord) {
		// Error Check
		if (coord == null) {
			return null;
		}
		// Return piece in the coordinate
		int x = coord.xCoord; int y = coord.yCoord;
		Piece piece = Play.board[x][y];
		return piece;
	}
	/**
	 * Get the piece at the given x and y values of the board
	 * @param xCoord x value of the board
	 * @param yCoord y value of the board
	 * @return the piece at the given x and y value
	 */
	public Piece getPiece(int xCoord, int yCoord) {
		// Error Check
		if (!(xCoord < 8 && xCoord >= 0 && yCoord < 8 && yCoord >= 0)) {
			return null;
		}
		// Return piece in the coordinates
		Piece piece = Play.board[xCoord][yCoord];
		return piece;
	}
	
	/**
	 * Convert the string into a valid coordinate variable
	 * @param position string value that determines the position
	 * @return Coordinate
	 */
	public Coordinate convert(String position) {
		// Error Check
		if (position == "") {
			return null;
		}
		
		else {
			String first = position.substring(0,1);
			String second = position.substring(1,2);
			int x,y;
			
			switch (first) {
			case "a":
				x = 0;
				break;
			case "b":
				x = 1;
				break;
			case "c":
				x = 2;
				break;
			case "d":
				x = 3;
				break;
			case "e":
				x = 4;
				break;
			case "f":
				x = 5;
				break;
			case "g":
				x = 6;
				break;
			case "h":
				x = 7;
				break;
			default:
				x = -1;
				break;
			}
			// Check if the column is a correct value
			if (x == -1) {
				return null;
			}
			// Check if the row is a correct value
			y = Integer.parseInt(second);
			if (y < 1 || y > 8) {
				return null;
			} else { y--; }	// This shifts the number so it corresponds to the 2d Array.
			// If no errors, return a coordinate.
			Coordinate temp = new Coordinate(x,y);
			return temp;
		}
	}
	
	// Converts a coordinate to a string
	public String convertToString(Coordinate coord) {
		String str = "";
		String file, rank;
		// Error Check
		if (coord == null) {
			return null;
		}
		else {
			int x = coord.xCoord, y = coord.yCoord;
			switch(x) {
			case 0: file = "a"; break;
			case 1: file = "b"; break;
			case 2: file = "c"; break;
			case 3: file = "d"; break;
			case 4: file = "e"; break;
			case 5: file = "f"; break;
			case 6: file = "g"; break;
			case 7: file = "h"; break;
			default: file = ""; break;
			}
			
			switch(y) {
			case 0: rank = "1"; break;
			case 1: rank = "2"; break;
			case 2: rank = "3"; break;
			case 3: rank = "4"; break;
			case 4: rank = "5"; break;
			case 5: rank = "6"; break;
			case 6: rank = "7"; break;
			case 7: rank = "8"; break;
			default: rank = ""; break;
			}
		}
		str = str.concat(file);
		str = str.concat(rank);
		
		if (str.length() != 2)
			return null;
		
		return str;
	}
	
	boolean isAlly(char myColor, char otherColor) {
		// Error Check
		if (myColor == '\0' || otherColor == '\0')
			return false;
		
		if(myColor == otherColor)
			return true;
		
		return false;
	}
	
	public boolean hasExistingPiece(Coordinate coord) {
		int xCoord = coord.xCoord;
		int yCoord = coord.yCoord;
		
		if (Play.board[xCoord][yCoord] != null) {
			return true;
		}
		return false;
	}
	public boolean hasExistingPiece(int xCoord, int yCoord) {
		// Error Check, within the board boundries
		if (!(xCoord < 8 && xCoord >= 0 && yCoord < 8 && yCoord >= 0)) {
			return false;
		}
		if (Play.board[xCoord][yCoord] != null) {
			return true;
		}
		return false;
	}
	
	// This Inherited form of hasCollision is generally meant for Straight movement (not diagonal). Override for diagonal.
	// Update, hasCollision is meant for straight and diagonal movement.
	public boolean hasCollision(Coordinate src, Coordinate dest) {
		int srcx = src.xCoord; int srcy = src.yCoord;
		int destx = dest.xCoord; int desty = dest.yCoord;
		int i, j;
		
		// For Horizontal movement
		if (srcx < destx && srcy == desty) {
			for (i = srcx + 1; i < destx; i++) {
				if (hasExistingPiece(i, srcy))
					return true;
			}
		}
		else if (srcx > destx && srcy == desty) {
			for (i = srcx - 1; i > destx; i--) {
				if (hasExistingPiece(i, srcy))
					return true;
			}
		}
		
		// For vertical movement
		else if (srcy < desty && srcx == destx) {
			for (j = srcy + 1; j < desty; j++) {
				if (hasExistingPiece(srcx, j))
					return true;
			}
		}
		else if (srcy > desty && srcx == destx) {
			for (j = srcy - 1; j > desty; j--) {
				if (hasExistingPiece(srcx, j))
					return true;
			}
		}
		
		// For NorthEast Diagonal Movement
		else if (srcx < destx && srcy > desty) {
			for (i = srcx + 1, j = srcy - 1; (i < destx) && (j > desty); i++, j--) {
				if (hasExistingPiece(i, j))
					return true;
			}
		}
		
		// For SouthEast Diagonal Movement
		else if (srcx < destx && srcy < desty) {
			for (i = srcx + 1, j = srcy + 1; (i < destx) && (j < desty); i++, j++) {
				if (hasExistingPiece(i, j))
					return true;
			}
		}
		
		// For NorthWest Diagonal Movement
		else if (srcx > destx && srcy > desty) {
			for (i = srcx - 1, j = srcy - 1; (i > destx) && (j > desty); i--, j--) {
				if (hasExistingPiece(i, j))
					return true;
			}
		}
		
		// For SouthWest Diagonal Movement
		else if (srcx > destx && srcy < desty) {
			for (i = srcx - 1, j = srcy + 1; (i > destx) && (j < desty); i--, j++) {
				if (hasExistingPiece(i, j))
					return true;
			}
		}
		return false;
	}
	
	public boolean checkKing(boolean whitesTurn) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece piece = Play.board[i][j];
				Coordinate Kcoord = new Coordinate(i, j);
				
				// If it's black's turn and the piece found is a white king
				if (piece != null && piece.pieceName.equals("wK") && whitesTurn) {
					// remove the king
					// check if the king's spot is a valid spot
					// if not, then the king should be checked
					// place the king back.
					King king = (King) piece;
					Play.board[i][j] = null;
					if (isValidSpace(Kcoord, whitesTurn)) {
						king.inCheck = false;
						Play.board[i][j] = king;
						return false;
					}
					else {
						king.inCheck = true;
						Play.board[i][j] = king;
						return true;
					}
				}
				// If it's white's turn and the piece found is a black king
				else if (piece != null && piece.pieceName.equals("bK") && !whitesTurn) {
					King king = (King) piece;
					Play.board[i][j] = null;
					if (isValidSpace(Kcoord, whitesTurn)) {
						king.inCheck = false;
						Play.board[i][j] = king;
						return false;
					}
					else {
						king.inCheck = true;
						Play.board[i][j] = king;
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
	// See if there is a piece or an opponent that can attack in this specified coordinate. Used for the king.
	public boolean isValidSpace(Coordinate coord, boolean whitesTurn) {
		String src = ""; String dest = convertToString(coord);
		Piece piece;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// If a piece exists at the coordinate, obtain the coordinate.
				piece = getPiece(i, j);
				if (piece != null) {
					Coordinate currPieceCoord = new Coordinate(i, j);
					src = convertToString(currPieceCoord);
					
					// If an opposing piece can get to that space, then it is not a valid space
					// Check if the piece is a pawn first, their movement and capture displacements differ
					
					if (piece.pieceName.equals("wp") && !whitesTurn) {
						// Movement to capture a piece
						if ( (coord.yCoord == currPieceCoord.yCoord + 1) && (coord.xCoord == currPieceCoord.xCoord)) {
							// do nothing. The pawn poses no threat since it cannot capture moving straight forward
						}
						else if ( (coord.yCoord == currPieceCoord.yCoord + 1) && (coord.xCoord == currPieceCoord.xCoord + 1)) {
							// Pawn can capture diagonally forward and to the right
							return false;
						}
						else if ( (coord.yCoord == currPieceCoord.yCoord + 1) && (coord.xCoord == currPieceCoord.xCoord - 1)) {
							// Pawn can capture diagonally forward and to the left
							return false;
						}
					}
					else if (piece.pieceName.equals("bp") && whitesTurn) {
						// Movement to capture a piece
						if ( (coord.yCoord == currPieceCoord.yCoord - 1) && (coord.xCoord == currPieceCoord.xCoord)) {
							// do nothing. The pawn poses no threat since it cannot capture moving straight forward
						}
						else if ( (coord.yCoord == currPieceCoord.yCoord - 1) && (coord.xCoord == currPieceCoord.xCoord + 1)) {
							// Pawn can capture diagonally forward and to the right
							return false;
						}
						else if ( (coord.yCoord == currPieceCoord.yCoord - 1) && (coord.xCoord == currPieceCoord.xCoord - 1)) {
							// Pawn can capture diagonally forward and to the left
							return false;
						}
					}
					
					else if (!(piece.pieceName.equals("wp") || piece.pieceName.equals("bp"))){
						Coordinate destination = coord;
						Piece temp = getPiece(destination);
						Play.board[destination.xCoord][destination.yCoord] = null;
						if (piece.canMove(src, dest, !whitesTurn)) {
							Play.board[destination.xCoord][destination.yCoord] = temp;
							return false;
						}
						else {
							Play.board[destination.xCoord][destination.yCoord] = temp;
						}
					}
					
				}
				
			}
		}
		return true;
	}
}
