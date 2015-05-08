package pieces;
import java.io.Serializable;

import android.widget.Toast;

import com.example.chess.Play;

/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Pawn extends Piece implements Serializable{
	
	private static final long serialVersionUID = -6761306268748656799L;
	// Specific properties of the pawn
	public boolean hasMadeInitMove;
	protected boolean canEnPassant;
	
	// Constructor
	public Pawn(char color, String pieceName, String position) {
		super(color, pieceName, position);
		this.hasMadeInitMove = false;
		this.canEnPassant = false;
	}

	@Override
	public boolean canMove(String src, String dest, boolean whitesTurn) {
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
		
		
		// WHITE PAWN MOVEMENT
		if(getPiece(source).color == 'w' && whitesTurn) {
			// Check if the pawn has moved before (check for moving 2 spaces)
			if(destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord + 2) && !hasExistingPiece(destination)) {
				if (!(this.hasMadeInitMove)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = true;
					return true;
				}
			}
			// Move 1 space forward
			else if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord + 1) && !hasExistingPiece(destination)) {
				this.hasMadeInitMove = true;
				this.canEnPassant = false;
				return true;
			}
			// Movement to capture a piece
			else if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord + 1) && hasExistingPiece(destination)) {
				Piece opposingPiece = getPiece(destination); 
				if (!isAlly(this.color, opposingPiece.color)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = false;
					return true;
				}
			}
			else if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord + 1) && hasExistingPiece(destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = false;
					return true;
				}
			}
			// Movement for EnPassant to the left
			else if (getPiece(source.xCoord - 1, source.yCoord) != null) {					// If there exists a piece to the left
				if (getPiece(source.xCoord - 1, source.yCoord).pieceName.equals("bp")){		// If the piece is a black piece
					Pawn newPawn = (Pawn) getPiece(source.xCoord - 1, source.yCoord);
					if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord + 1) && !hasExistingPiece(destination) && newPawn.canEnPassant) {
						if (!isAlly(this.color, newPawn.color)) {
							this.hasMadeInitMove = true;
							this.canEnPassant = false;
							Play.board[source.xCoord - 1][source.yCoord] = null;
							return true;
						}
					} else {
						return false;
					}
				}
			}
			// Movement for EnPassant to the right
			else if (getPiece(source.xCoord + 1, source.yCoord) != null) {
				if (getPiece(source.xCoord + 1, source.yCoord).pieceName.equals("bp")){
					Pawn newPawn = (Pawn) getPiece(source.xCoord + 1, source.yCoord);
					if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord + 1) && !hasExistingPiece(destination) && newPawn.canEnPassant) {
						if (!isAlly(this.color, newPawn.color)) {
							this.hasMadeInitMove = true;
							this.canEnPassant = false;
							Play.board[source.xCoord + 1][source.yCoord] = null;
							return true;
						}
					} else {
						return false;
					}
				}
			}
		}
		
		// BLACK PAWN MOVEMENT
		else if(getPiece(source).color == 'b' && !whitesTurn) {
			// Check if the pawn has moved before (check for moving 2 spaces)
			if(destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord - 2) && !hasExistingPiece(destination)) {
				if (!(this.hasMadeInitMove)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = true;
					return true;
				}
			}
			// Move 1 space forward
			else if (destination.xCoord == source.xCoord && destination.yCoord == (source.yCoord - 1) && !hasExistingPiece(destination)) {
				this.hasMadeInitMove = true;
				this.canEnPassant = false;
				return true;
			}
			// Movement to capture a piece
			else if (destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord - 1) && hasExistingPiece(destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = false;
					return true;
				}
			}
			else if (destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord - 1) && hasExistingPiece(destination)) {
				Piece opposingPiece = getPiece(destination);
				if (!isAlly(this.color, opposingPiece.color)) {
					this.hasMadeInitMove = true;
					this.canEnPassant = false;
					return true;
				}
			}
			// Movement for EnPassant to the left
			else if (getPiece(source.xCoord - 1, source.yCoord) != null && destination.xCoord == (source.xCoord - 1) && destination.yCoord == (source.yCoord - 1)) {
				if (getPiece(source.xCoord - 1, source.yCoord).pieceName.equals("wp")){
					Pawn newPawn = (Pawn) getPiece(source.xCoord - 1, source.yCoord);
					if (!hasExistingPiece(destination) && newPawn.canEnPassant) {
						if (!isAlly(this.color, newPawn.color)) {
							this.hasMadeInitMove = true;
							this.canEnPassant = false;
							Play.board[source.xCoord - 1][source.yCoord] = null;
							return true;
						}
					} else {
						return false;
					}
				}
			}
			// Movement for EnPassant to the right
			else if (getPiece(source.xCoord + 1, source.yCoord) != null && destination.xCoord == (source.xCoord + 1) && destination.yCoord == (source.yCoord - 1) ) {
				if (getPiece(source.xCoord + 1, source.yCoord).pieceName.equals("wp")){
					Pawn newPawn = (Pawn) getPiece(source.xCoord + 1, source.yCoord);
					if (!hasExistingPiece(destination) && newPawn.canEnPassant) {
						if (!isAlly(this.color, newPawn.color)) {
							this.hasMadeInitMove = true;
							this.canEnPassant = false;
							Play.board[source.xCoord + 1][source.yCoord] = null;
							return true;
						}
					} else {
						return false;
					}
				}
			}
		}
		
		// Failure. Cannot move the piece.
		return false;
	}

}
