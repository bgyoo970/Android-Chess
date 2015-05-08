package pieces;

import java.io.Serializable;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Queen extends Piece implements Serializable{
	private static final long serialVersionUID = 4612799259210559546L;

	// Constructor
	public Queen(char color, String pieceName, String position) {
		super(color, pieceName, position);
	}
	
	@Override
	public boolean canMove(String src, String dest, boolean whitesTurn){
		// Convert the string into numerical values. Allows for easier access to the 2D array
		// convert(String position) already error checks for a valid position
		Coordinate source = convert(src);
		Coordinate destination = convert(dest);
		
		// Error check for invalid coordinates
		if (source == null || destination == null) {
			return false;
		}
		// Error check for existing piece at source. Nothing to move.
		if (getPiece(source) == null) {
			return false;
		}
		
		// WHITE QUEEN MOVEMENT
		if(getPiece(source).color == 'w' && whitesTurn) {
			// DIAGONAL MOVEMENT
			if(validDiagonalMove(source, destination) && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
			// VERTICAL MOVEMENT
			if(destination.yCoord == source.yCoord && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
			// HORIZONTAL MOVEMENT
			if(destination.xCoord == source.xCoord && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
		}
		
		// BLACK QUEEN MOVEMENT
		else if(getPiece(source).color == 'b' && !whitesTurn) {
			// DIAGONAL MOVEMENT
			if(validDiagonalMove(source, destination) && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
			// VERTICAL MOVEMENT
			if(destination.yCoord == source.yCoord && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
			// HORIZONTAL MOVEMENT
			if(destination.xCoord == source.xCoord && !hasCollision(source, destination)) {
				if (QLogic(destination)) return true;
			}
		}
		return false;
	}
	
	public boolean validDiagonalMove(Coordinate src, Coordinate dest) {
		int srcx = src.xCoord; int srcy = src.yCoord;
		int destx = dest.xCoord; int desty = dest.yCoord;
		int i, j;
		
		// For NorthEast Diagonal Movement
		if (srcx < destx && srcy > desty) {
			for (i = srcx + 1, j = srcy - 1; (i <= destx) && (j >= desty); i++, j--) {
				if (i == destx && j == desty)
					return true;
			}
		}
		// For SouthEast Diagonal Movement
		else if (srcx < destx && srcy < desty) {
			for (i = srcx + 1, j = srcy + 1; (i <= destx) && (j <= desty); i++, j++) {
				if (i == destx && j == desty)
					return true;
			}
		}
		// For NorthWest Diagonal Movement
		else if (srcx > destx && srcy > desty) {
			for (i = srcx - 1, j = srcy - 1; (i >= destx) && (j >= desty); i--, j--) {
				if (i == destx && j == desty)
					return true;
			}
		}
		// For SouthWest Diagonal Movement
		else if (srcx > destx && srcy < desty) {
			for (i = srcx - 1, j = srcy + 1; (i >= destx) && (j <= desty); i--, j++) {
				if (i == destx && j == desty)
					return true;
			}
		}
		return false;
	}
	
	public boolean QLogic(Coordinate coord) {
		Piece opposingPiece = getPiece(coord);
		// Check if a piece exists in the destination. 
		if (hasExistingPiece(coord)) {
			// If opponent, capture the piece.
			if (!isAlly(this.color, opposingPiece.color)) {
				return true;
			}
			// Otherwise it's an ally. Illegal Move.
			else
				return false;
		} 
		// Otherwise, no conflicting piece exists. Progress normally
		else { return true; }
	}

}
