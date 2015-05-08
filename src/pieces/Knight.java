package pieces;

import java.io.Serializable;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Knight extends Piece implements Serializable{
	
	private static final long serialVersionUID = -6968109718750197611L;

	// Constructor
	public Knight(char color, String pieceName, String position) {
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
		
		// WHITE KNIGHT MOVEMENT
		if(getPiece(source).color == 'w' && whitesTurn) {
			// Upper Right
			if(destination.yCoord == (source.yCoord + 2) && destination.xCoord == (source.xCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Upper Left
			if(destination.yCoord == (source.yCoord + 2) && destination.xCoord == (source.xCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Left (Up)
			if(destination.xCoord == (source.xCoord - 2) && destination.yCoord == (source.yCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Left (Down)
			if(destination.xCoord == (source.xCoord - 2) && destination.yCoord == (source.yCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Bottom Right
			if(destination.yCoord == (source.yCoord - 2) && destination.xCoord == (source.xCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Bottom Left
			if(destination.yCoord == (source.yCoord - 2) && destination.xCoord == (source.xCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Right (Up)
			if(destination.xCoord == (source.xCoord + 2) && destination.yCoord == (source.yCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Right (Down)
			if(destination.xCoord == (source.xCoord + 2) && destination.yCoord == (source.yCoord - 1)) {
				if (KLogic(destination)) return true;
			}
		}
		
		// BLACK KNIGHT MOVEMENT
		else if(getPiece(source).color == 'b' && !whitesTurn) {
			// Bottom Right
			if(destination.yCoord == (source.yCoord + 2) && destination.xCoord == (source.xCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Bottom Left
			if(destination.yCoord == (source.yCoord + 2) && destination.xCoord == (source.xCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Left (Up)
			if(destination.xCoord == (source.xCoord - 2) && destination.yCoord == (source.yCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Left (Down)
			if(destination.xCoord == (source.xCoord - 2) && destination.yCoord == (source.yCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Upper Right
			if(destination.yCoord == (source.yCoord - 2) && destination.xCoord == (source.xCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Upper Left
			if(destination.yCoord == (source.yCoord - 2) && destination.xCoord == (source.xCoord - 1)) {
				if (KLogic(destination)) return true;
			}
			// Right (Up)
			if(destination.xCoord == (source.xCoord + 2) && destination.yCoord == (source.yCoord + 1)) {
				if (KLogic(destination)) return true;
			}
			// Right (Down)
			if(destination.xCoord == (source.xCoord + 2) && destination.yCoord == (source.yCoord - 1)) {
				if (KLogic(destination)) return true;
			}
		}
		return false;
	}
	
	public boolean KLogic(Coordinate coord) {
		if (hasExistingPiece(coord)) {
			Piece opposingPiece = getPiece(coord);
			// If the piece at the destination is an opponent, capture it.
			if (!isAlly(this.color, opposingPiece.color)) {
				return true;
			}
		} 
		// Otherwise just proceed normally. No piece exists in the destination.
		else { return true;}
		
		return false;
	}
	
}
