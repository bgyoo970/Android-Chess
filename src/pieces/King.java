package pieces;

import java.io.Serializable;

import com.example.chess.Play;


/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class King extends Piece implements Serializable{
	
	private static final long serialVersionUID = -5285098230144965425L;
	public boolean hasMadeInitMove = false;
	public boolean inCheck = false;
	public boolean isCheckmate = false;
	
	// Constructor
	public King(char color, String pieceName, String position) {
		super(color, pieceName, position);
	}
	
	@Override
	public boolean canMove(String src, String dest, boolean whitesTurn){
		// Convert the string into numerical values. Allows for easier access to the 2D array
		// convert(String position) already error checks for a valid position
		Coordinate source = convert(src);
		Coordinate destination = convert(dest);
		Coordinate[] moveSet = new Coordinate[8];
		
		// Error check for invalid coordinates
		if (source == null || destination == null) {
			return false;
		}
		// Error check for existing piece at source. Nothing to move.
		if (getPiece(source) == null) {
			return false;
		}
		
		// Supposed to check for checkmate. Highly likely to be deleted. Its checked in control.
		/*moveSet = getKingMoveSet(source, whitesTurn);
		if (isCheckMate(moveSet)) {
			return false;
		}*/
		
		// WHITE KING MOVEMENT
		if(getPiece(source).color == 'w' && whitesTurn) {
			// Castling
			if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && !hasMadeInitMove) {
				if (castleLogic(source, destination, whitesTurn)) { hasMadeInitMove = true; return true; }
			}
			// North
			if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn)) { hasMadeInitMove = true; return true; }
			}
			// NorthWest
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// West
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == source.yCoord) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// SouthWest
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// South
			if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// SouthEast
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// East
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == source.yCoord) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// NorthEast
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			
		}
		// BLACK KING MOVEMENT
		else if(getPiece(source).color == 'b' && !whitesTurn) {
			
			// Castling
			if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && !hasMadeInitMove) {
				if (castleLogic(source, destination, whitesTurn)) { hasMadeInitMove = true; return true; }
			}
			// North
			if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn)) { hasMadeInitMove = true; return true; }
			}
			// NorthWest
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// West
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == source.yCoord) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// SouthWest
			if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// South
			if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// SouthEast
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord - 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// East
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == source.yCoord) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
			// NorthEast
			if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord + 1)) {
				if (KingLogic(destination, whitesTurn))  { hasMadeInitMove = true; return true; }
			}
		}
		
		return false;
	}
	
	// Finds all possible moves for the king
	public Coordinate[] getKingMoveSet(Coordinate coord, boolean whitesTurn) {
		Coordinate[] moveSet = new Coordinate[8];
		int moveSetIndex = 0;
		int x = coord.xCoord;
		int y = coord.yCoord;
		Coordinate temp;
		
		// Remove the king temporarily to check for valid spaces for the king.
		// This way, the king does not interfere with collision movement to check for valid spots.
		King piece = (King) Play.board[x][y];
		boolean checkStatus = piece.inCheck;
		Play.board[x][y] = null;
		
		// Check all 8 spots that the king may move
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				// Do not consider the original position. x+0=x; y+0=y Cannot move to the src position.
				if (!(i == 0 && j == 0)) {
					// Do not go out of bounds.
					if (!(x + i > 7) && !(y + j > 7) && !(x + i < 0) && !(y + j < 0)) {
						temp = new Coordinate(x + i, y + j);
						if (KingLogic(temp, whitesTurn)) {
							moveSet[moveSetIndex] = temp;
							moveSetIndex++;
						}
					}
				}
			}
		}
		Play.board[x][y] = piece;
		piece.inCheck = checkStatus;
		return moveSet;
	}
	
	// Check for checkmate
	public boolean isCheckMate(Coordinate[] moveSet) {
		int ct = 0;
		for (int i = 0; i < moveSet.length; i++) {
			if (moveSet[i] != null)
				ct++;
		}
		// If there are possible coordinates to move to in the moveSet, king is in checkmate
		if (ct == 0){
			return true;
		}
		
		return false;
	}
	
	public boolean KingLogic(Coordinate coord, boolean whitesTurn) {
		if (hasExistingPiece(coord)) {
			Piece opposingPiece = getPiece(coord);
			// If the piece at the destination is an opponent, capture it.
			if (!isAlly(this.color, opposingPiece.color) && isValidSpace(coord, whitesTurn)) {
				return true;
			}
		} 
		// Otherwise if the position won't put the king into check and no piece exists in the destination, proceed.
		else if (isValidSpace(coord, whitesTurn)) { return true; }
		
		return false;
	}
	
	// Castling
	public boolean castleLogic(Coordinate source, Coordinate destination, boolean whitesTurn) {
		// White Castling
		if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && !hasMadeInitMove && whitesTurn) {
			Coordinate leftwRook = new Coordinate(0, 0);
			Coordinate rightwRook = new Coordinate(7, 0);
			
			// Short Castling
			if (isRook(leftwRook) && destination.xCoord == 1) {
				Rook rook = (Rook) getPiece(leftwRook);
				Coordinate kingDest = new Coordinate(destination.xCoord, destination.yCoord);
				
				if (rook.canCastling && !inCheck && isValidSpace(kingDest, whitesTurn)) {
					Play.board[2][0] = rook;
					Play.board[0][0] = null;
					rook.canCastling = false;
					hasMadeInitMove = true;
					return true;
				}
			}
			// Long Castling
			if (isRook(rightwRook) && destination.xCoord == 5) {
				Rook rook = (Rook) getPiece(rightwRook);
				Coordinate kingDest = new Coordinate(destination.xCoord, destination.yCoord);
				if (rook.canCastling && !inCheck && isValidSpace(kingDest, whitesTurn)) {
					Play.board[4][0] = rook;
					Play.board[7][0] = null;
					rook.canCastling = false;
					hasMadeInitMove = true;
					return true;
				}
			}
		}
		
		// Black Castling
		else if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && !hasMadeInitMove && !whitesTurn) {
			Coordinate leftbRook = new Coordinate(0, 7);
			Coordinate rightbRook = new Coordinate(7, 7);
			
			// Short Castling
			if (isRook(leftbRook) && destination.xCoord == 1) {
				Rook rook = (Rook) getPiece(leftbRook);
				Coordinate kingDest = new Coordinate(destination.xCoord, destination.yCoord);
				if (rook.canCastling && !inCheck && isValidSpace(kingDest, whitesTurn)) {		// whitesTurn is meant to be left alone. its not !whitesTurn.
					Play.board[2][7] = rook;
					Play.board[0][7] = null;
					rook.canCastling = false;
					hasMadeInitMove = true;
					return true;
				}
			}
			// Long Castling
			if (isRook(rightbRook) && destination.xCoord == 5) {
				Rook rook = (Rook) getPiece(rightbRook);
				Coordinate kingDest = new Coordinate(destination.xCoord, destination.yCoord);
				if (rook.canCastling && !inCheck && isValidSpace(kingDest, whitesTurn)) {
					Play.board[4][7] = rook;
					Play.board[7][7] = null;
					rook.canCastling = false;
					hasMadeInitMove = true;
					return true;
				}
			}
		}
		return false;
	}
	
	// Checks if the piece at the coordinate is a king
	public boolean isRook(Coordinate coord) {
		// Error Check
		if (coord == null) { return false; }
		Piece piece = getPiece(coord);
		if (piece == null) { return false; }
		
		// Check if the coordinate has a king.
		if (piece.pieceName.equals("wR") || piece.pieceName.equals("bR"))
			return true;
		
		return false;
	}
}
