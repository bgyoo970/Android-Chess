package pieces;

import java.io.Serializable;

import chess.Chess;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Rook extends Piece implements Serializable{
	private static final long serialVersionUID = -4750744136569856730L;
	public boolean canCastling = true;
	
	// Constructor
	public Rook(char color, String pieceName, String position) {
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
		
		// WHITE ROOK MOVEMENT
		if(getPiece(source).color == 'w' && whitesTurn) {
			/*
			// Castling
			if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && canCastling) {
				int x = destination.xCoord; int y = destination.yCoord;
				Coordinate left = new Coordinate(x - 1, y);
				Coordinate right = new Coordinate(x + 1, y);
				
				// Short Castling
				if (isKing(left)) {
					King king = (King) getPiece(left);
					
					if (canCastling && !king.hasMadeInitMove && !king.inCheck && isValidSpace(right, whitesTurn)) {
						Chess.board[6][0] = king;
						Chess.board[4][0] = null;
						canCastling = false;
						return true;
					}
				}
				
				// Long Castling
				if (isKing(right)) {
					King king = (King) getPiece(right);
					if (canCastling && !king.hasMadeInitMove && !king.inCheck && isValidSpace(left, whitesTurn)) {
						Chess.board[2][0] = king;
						Chess.board[4][0] = null;
						canCastling = false;
						return true;
					}
				}
				// if (canCastling && !king.hasMadeInitMove && !kingInCheck && !king.dest.InCheck)
				// move the king piece. movepiece will take care of rook movement.
			}
			*/
			
			// Horizontal movement
			if(destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination)) {
				canCastling = false;
				return true;
			}
			// Horizontal capture
			else if(destination.yCoord == source.yCoord && hasExistingPiece(destination) && !hasCollision(source, destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					canCastling = false;
					return true;
				}
			}
			// Vertical Movement
			else if(destination.xCoord == source.xCoord && !hasExistingPiece(destination) && !hasCollision(source, destination)) {
				canCastling = false;
				return true;
			}
			// Vertical capture
			else if(destination.xCoord == source.xCoord && hasExistingPiece(destination) && !hasCollision(source, destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					canCastling = false;
					return true;
				}
			}
		}
			
		// BLACK ROOK MOVEMENT
		else if(getPiece(source).color == 'b' && !whitesTurn) {
			/*
			// Castling
			if (destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination) && canCastling) {
				int x = destination.xCoord; int y = destination.yCoord;
				Coordinate left = new Coordinate(x - 1, y);
				Coordinate right = new Coordinate(x + 1, y);
				
				// Short Castling
				if (isKing(left)) {
					King king = (King) getPiece(left);
					if (canCastling && !king.hasMadeInitMove && !king.inCheck && isValidSpace(right, whitesTurn)) {
						Chess.board[6][7] = king;
						Chess.board[4][7] = null;
						canCastling = false;
						return true;
					}
				}
				// Long Castling
				if (isKing(right)) {
					King king = (King) getPiece(right);
					if (canCastling && !king.hasMadeInitMove && !king.inCheck && isValidSpace(left, whitesTurn)) {
						Chess.board[2][7] = king;
						Chess.board[4][7] = null;
						canCastling = false;
						return true;
					}
				}
				// if (canCastling && !king.hasMadeInitMove && !kingInCheck && !king.dest.InCheck)
				// move the king piece. movepiece will take care of rook movement.
			}
			*/
			// Horizontal movement
			if(destination.yCoord == source.yCoord && !hasExistingPiece(destination) && !hasCollision(source, destination)) {
				canCastling = false;
				return true;
			}
			// Horizontal capture
			else if(destination.yCoord == source.yCoord && hasExistingPiece(destination) && !hasCollision(source, destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					canCastling = false;
					return true;
				}
			}
			// Vertical Movement
			else if(destination.xCoord == source.xCoord && !hasExistingPiece(destination) && !hasCollision(source, destination)) {
				canCastling = false;
				return true;
			}
			// Vertical capture
			else if(destination.xCoord == source.xCoord && hasExistingPiece(destination) && !hasCollision(source, destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					canCastling = false;
					return true;
				}
			}
		}
		return false;
	}
	
	// Checks if the piece at the coordinate is a king
	public boolean isKing(Coordinate coord) {
		// Error Check
		if (coord == null) { return false; }
		Piece piece = getPiece(coord);
		if (piece == null) { return false; }
		
		// Check if the coordinate has a king.
		if (piece.pieceName.equals("wK") || piece.pieceName.equals("bK"))
			return true;
		
		return false;
	}
	
	
}
