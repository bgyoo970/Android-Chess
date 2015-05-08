package control;

import java.util.Scanner;
import java.util.StringTokenizer;

import pieces.Bishop;
import pieces.Coordinate;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import chess.Chess;

import com.example.chess.Play;
/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */

public class Control {
	public static Piece piece = new Piece();
	// Empty constructor needed to access Control methods in main.
	public Control() {}
	
	public void processPlayerInput() {
		Scanner sc = new Scanner(System.in);
		boolean whitesTurn = true;									// Determines who's turn it is
		boolean isDraw = false;										// Draw flag.
		String input = "";
		String src = "", dest = "";
		String tk3;
		Piece currPiece = null;
		Piece piece = new Piece();
		
		// Do while loop to allow for retry on user inputs
		do {
			if (whitesTurn) {
				piece.checkKing(whitesTurn);	// See if the respective king is in check
				// Display Check if king is checked
				if (isKingChecked(whitesTurn)) System.out.println("Check");
				
				// See if the black has won
				if (checkVictory(whitesTurn)) {
					System.out.println("Checkmate");
					System.out.println("Black wins");
					return;
				}
				System.out.print("White's move: ");
			}
			else {
				piece.checkKing(whitesTurn);	// See if the respective king is in check
				// Display Check if king is checked
				if (isKingChecked(whitesTurn)) System.out.println("Check");
				
				// See if the white has won
				if (checkVictory(whitesTurn)) {
					System.out.println("Checkmate");
					System.out.println("White wins");
					return;
				}
				System.out.print("Black's move: ");
			}
			// Obtain the user input, tokenize the string.
			input = sc.nextLine();
			StringTokenizer st = new StringTokenizer(input, " ");
			int stlen = st.countTokens();
			
			// Resigning
			if (input.equalsIgnoreCase("resign")) {
				if (whitesTurn)
					System.out.println("Black wins");
				else
					System.out.println("White wins");
				return;
			}
			
			// Draw
			if (isDraw == true && input.equalsIgnoreCase("draw")) {
				System.out.println("Draw");
				return;
			} else { isDraw = false; }
			
			// If a single argument doesn't match the above (or if it has more than 3 arguments) it's invalid
			if (stlen <= 1 || stlen > 3) {
				System.out.println("Illegal move, try again");
			}
			
			// Moving the piece **CONSIDER STLEN OF THREE. THIRD TOKEN SHOULD BE DRAW OR PROMOTE. UPDATE1: DID DRAW.
			else if (stlen == 2 ||  stlen == 3) {
				src = st.nextToken();
				dest = st.nextToken();
				Coordinate Coord = piece.convert(src);						// Coordinates of the desired piece to move
				currPiece = piece.getPiece(Coord);							// Piece to be moved
				
				// Check if the first 2 arguments are valid
				if (!checkValidInput(input)) {
					System.out.println("Illegal move, try again");
				}
				else { 
					// if the player signals for a draw, change the boolean to allow for a draw.
					if (stlen == 3) {
						tk3 = st.nextToken();
						if (tk3.equalsIgnoreCase("draw?"))
							isDraw = true;
					} else {
						tk3 = "";
					}
					
					// Error Check for null pieces.  See if the existing piece can move from the source to the destination
					if (currPiece != null && currPiece.canMove(src, dest, whitesTurn)) {
						currPiece.movePiece(src, dest, whitesTurn);
						promotion(src, dest, whitesTurn, tk3);
						// Successful at moving piece, change turns.
						whitesTurn = !whitesTurn;
					} else { System.out.println("Illegal move, try again"); }
				}
			}
			
			// Print the board
			printBoard();
		} while (true);
	}

	
	public void setBoard(Piece board[][]) {
		// White Pieces
		Pawn wp1 = new Pawn('w', "wp", "a2"); 		board[0][1] = wp1;
		Pawn wp2 = new Pawn('w', "wp", "b2"); 		board[1][1] = wp2;
		Pawn wp3 = new Pawn('w', "wp", "c2"); 		board[2][1] = wp3;
		Pawn wp4 = new Pawn('w', "wp", "d2"); 		board[3][1] = wp4;
		Pawn wp5 = new Pawn('w', "wp", "e2"); 		board[4][1] = wp5;
		Pawn wp6 = new Pawn('w', "wp", "f2"); 		board[5][1] = wp6;
		Pawn wp7 = new Pawn('w', "wp", "g2"); 		board[6][1] = wp7;
		Pawn wp8 = new Pawn('w', "wp", "h2"); 		board[7][1] = wp8;
		
		Rook wR1 = new Rook('w', "wR", "a1"); 		board[0][0] = wR1;
		Knight wN1 = new Knight('w', "wN", "b1"); 	board[1][0] = wN1;
		Bishop wB1 = new Bishop('w', "wB", "c1"); 	board[2][0] = wB1;
		King wK = new King('w', "wK", "d1"); 		board[3][0] = wK;
		Queen wQ = new Queen('w', "wQ", "e1"); 		board[4][0] = wQ;
		Bishop wB2 = new Bishop('w', "wB", "f1"); 	board[5][0] = wB2;
		Knight wN2 = new Knight('w', "wN", "g1"); 	board[6][0] = wN2;
		Rook wR2 = new Rook('w', "wR", "h1"); 		board[7][0] = wR2;
		
		// Black Pieces
		Pawn bp1 = new Pawn('b', "bp", "a7"); 		board[0][6] = bp1;
		Pawn bp2 = new Pawn('b', "bp", "b7"); 		board[1][6] = bp2;
		Pawn bp3 = new Pawn('b', "bp", "c7"); 		board[2][6] = bp3;
		Pawn bp4 = new Pawn('b', "bp", "d7"); 		board[3][6] = bp4;
		Pawn bp5 = new Pawn('b', "bp", "e7"); 		board[4][6] = bp5;
		Pawn bp6 = new Pawn('b', "bp", "f7"); 		board[5][6] = bp6;
		Pawn bp7 = new Pawn('b', "bp", "g7"); 		board[6][6] = bp7;
		Pawn bp8 = new Pawn('b', "bp", "h7"); 		board[7][6] = bp8;
		
		Rook bR1 = new Rook('b', "bR", "a8"); 		board[0][7] = bR1;
		Knight bN1 = new Knight('b', "bN", "b8"); 	board[1][7] = bN1;
		Bishop bB1 = new Bishop('b', "bB", "c8"); 	board[2][7] = bB1;
		King bK  = new King('b', "bK", "d8");		board[3][7] = bK;
		Queen bQ  = new Queen('b', "bQ", "e8"); 	board[4][7] = bQ;
		Bishop bB2 = new Bishop('b', "bB", "f8"); 	board[5][7] = bB2;
		Knight bN2 = new Knight('b', "bN", "g8"); 	board[6][7] = bN2;
		Rook bR2 = new Rook('b', "bR", "h8"); 		board[7][7] = bR2;
		
		board[0][5] = null;
		board[1][5] = null;
		board[2][5] = null;
		board[3][5] = null;
		board[4][5] = null;
		board[5][5] = null;
		board[6][5] = null;
		board[7][5] = null;
		board[0][4] = null;
		board[1][4] = null;
		board[2][4] = null;
		board[3][4] = null;
		board[4][4] = null;
		board[5][4] = null;
		board[6][4] = null;
		board[7][4] = null;
		board[0][3] = null;
		board[1][3] = null;
		board[2][3] = null;
		board[3][3] = null;
		board[4][3] = null;
		board[5][3] = null;
		board[6][3] = null;
		board[7][3] = null;
		board[0][2] = null;
		board[1][2] = null;
		board[2][2] = null;
		board[3][2] = null;
		board[4][2] = null;
		board[5][2] = null;
		board[6][2] = null;
		board[7][2] = null;
		
	}
	
	public void printBoard() {
		Piece temp; System.out.println("");
		for(int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				temp = Chess.board[j][i];
				if (temp != null)
					System.out.print(temp.pieceName + " ");
				if (temp == null) {
					if (((i+j) % 2) == 1)
						System.out.print("   ");
					else
						System.out.print("## ");
				}
			}
			System.out.print(i + 1);
			System.out.println("");
		}
		for(int k = 0; k < 8; k++) {
			System.out.print(" " + (char)(97 + k));
			System.out.print(" ");
		}
		System.out.println(""); 
		System.out.println("");
	}
	
	public boolean checkValidInput(String input) {
		StringTokenizer st = new StringTokenizer(input, " ");
		int stlen = st.countTokens();
		String str1 = st.nextToken();
		String str2 = st.nextToken();
		
		// Check if theres the right amount of arguments
		if (stlen < 1 || stlen > 3) {
			return false;
		}
		
		// Check if the file rank strings are of proper length
		if (str1.length() != 2 || str2.length() != 2) {
			return false;
		}
		
		// Check if the first 2 string inputs are valid
		if (Character.isLetter(str1.charAt(0)) && Character.isDigit(str1.charAt(1))) {
			if (Character.isLetter(str2.charAt(0)) && Character.isDigit(str2.charAt(1))) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean checkVictory(boolean whitesTurn) {
		Piece piece;
		Coordinate[] moveSet;
		Coordinate coord;
		//boolean hasWhiteK = false;
		//boolean hasBlackK = false;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				piece = Play.board[i][j];
				coord = new Coordinate(i, j);
				
				// Get the kings plausible moveSet. If the king cannot move and is under check, then the player loses.
				if (piece != null && piece.pieceName.equals("wK")) {
					//hasWhiteK = true;
					if (whitesTurn) {
						moveSet = ((King) piece).getKingMoveSet(coord, whitesTurn);
						if (((King) piece).isCheckMate(moveSet) && ((King) piece).inCheck == true)
							return true;
					}
				}
				else if (piece != null && piece.pieceName.equals("bK")) {
					//hasBlackK = true;
					if (!whitesTurn) {
						moveSet = ((King) piece).getKingMoveSet(coord, whitesTurn);
						if (((King) piece).isCheckMate(moveSet) && ((King) piece).inCheck == true)
							return true;
					}
				}
			}
		}
		/*
		if (whitesTurn && !hasBlackK) { 
			return true;
		}
		else if (!whitesTurn && !hasWhiteK) {
			return true;
		}
		*/
		return false;
		
	}
	
	// Helper method to print out Check in the prompt.
	public boolean isKingChecked(boolean whitesTurn) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				piece = Play.board[i][j];
				
				if (piece != null && piece.pieceName.equals("wK") && whitesTurn) {
					King king = (King) piece;
					if(king.inCheck)
						return true;
				}
				
				else if (piece != null && piece.pieceName.equals("bK") && !whitesTurn) {
					King king = (King) piece;
					if(king.inCheck)
						return true;
				}
			}
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	public void promotionCheck(Piece piece, String dest) {
		// Error check
		if (piece == null || dest == "") {
			return;
		}
		Coordinate destination = piece.convert(dest);
		
		if (piece.pieceName.equals("wp") && destination.yCoord == 7) {
			// prompt to premote
			// use a diaglog fragment.
			// depending on the button used, use that as input to promote the pawn.
			/*final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Save Game?");
			builder.setMessage("Would you like to save this game?");
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					
				}
			});
			*/
			
			
		}
		
		
		else if (piece.pieceName.equals("bp") && destination.yCoord == 0) {
			
		}
		
		
		/*Piece piece = new Piece();
		Coordinate coord = piece.convert(dest);
		Piece currPiece = piece.getPiece(coord);
		*/

		/*
		if (tk3.isEmpty()) {
			if (currPiece != null && currPiece.pieceName.equals("wp")) {
				Coordinate c = currPiece.convert(dest);
				int file = c.xCoord; int rank = c.yCoord;
				if (rank == 7) {
					Chess.board[file][rank] = new Queen('w', "wQ", dest);
				}
			}
			else if (currPiece != null && currPiece.pieceName.equals("bp")) {
				Coordinate c = currPiece.convert(dest);
				int file = c.xCoord; int rank = c.yCoord;
				if (rank == 0) {
					Chess.board[file][rank] = new Queen('b', "bQ", dest);
				}
			}
		} 
		else {
			Coordinate c = currPiece.convert(dest);
			int file = c.xCoord; int rank = c.yCoord;
			if (currPiece != null && currPiece.pieceName.equals("wp")) {
				if (rank == 7) {
					switch (tk3) {
					case "R":
						Chess.board[file][rank] = new Rook('w', "wR", dest);
						break;
					case "B":
						Chess.board[file][rank] = new Bishop('w', "wB", dest);
						break;
					case "N":
						Chess.board[file][rank] = new Knight('w', "wN", dest);
						break;
					case "Q":
						Chess.board[file][rank] = new Queen('w', "wQ", dest);
						break;
					default:
						Chess.board[file][rank] = new Queen('w', "wQ", dest);
						break;
					}
				}
			}
			else if (currPiece != null && currPiece.pieceName.equals("bp")) {
				if (rank == 0) {
					switch (tk3) {
					case "R":
						Chess.board[file][rank] = new Rook('b', "bR", dest);
						break;
					case "B":
						Chess.board[file][rank] = new Bishop('b', "bB", dest);
						break;
					case "N":
						Chess.board[file][rank] = new Knight('b', "bN", dest);
						break;
					case "Q":
						Chess.board[file][rank] = new Queen('b', "bQ", dest);
						break;
					default:
						Chess.board[file][rank] = new Queen('b', "bQ", dest);
						break;
					}
				}
			}
			*/
		}
	
	@SuppressLint("NewApi")
	public void promotion(String src, String dest, boolean whitesTurn, String tk3) {
		Piece piece = new Piece();
		Coordinate coord = piece.convert(dest);
		Piece currPiece = piece.getPiece(coord);
		tk3 = tk3.toUpperCase();
		
		if (tk3.isEmpty()) {
			if (currPiece != null && currPiece.pieceName.equals("wp")) {
				Coordinate c = currPiece.convert(dest);
				int file = c.xCoord; int rank = c.yCoord;
				if (rank == 7) {
					Chess.board[file][rank] = new Queen('w', "wQ", dest);
				}
			}
			else if (currPiece != null && currPiece.pieceName.equals("bp")) {
				Coordinate c = currPiece.convert(dest);
				int file = c.xCoord; int rank = c.yCoord;
				if (rank == 0) {
					Chess.board[file][rank] = new Queen('b', "bQ", dest);
				}
			}
		} 
		else {
			Coordinate c = currPiece.convert(dest);
			int file = c.xCoord; int rank = c.yCoord;
			if (currPiece != null && currPiece.pieceName.equals("wp")) {
				if (rank == 7) {
					switch (tk3) {
					case "R":
						Chess.board[file][rank] = new Rook('w', "wR", dest);
						break;
					case "B":
						Chess.board[file][rank] = new Bishop('w', "wB", dest);
						break;
					case "N":
						Chess.board[file][rank] = new Knight('w', "wN", dest);
						break;
					case "Q":
						Chess.board[file][rank] = new Queen('w', "wQ", dest);
						break;
					default:
						Chess.board[file][rank] = new Queen('w', "wQ", dest);
						break;
					}
				}
			}
			else if (currPiece != null && currPiece.pieceName.equals("bp")) {
				if (rank == 0) {
					switch (tk3) {
					case "R":
						Chess.board[file][rank] = new Rook('b', "bR", dest);
						break;
					case "B":
						Chess.board[file][rank] = new Bishop('b', "bB", dest);
						break;
					case "N":
						Chess.board[file][rank] = new Knight('b', "bN", dest);
						break;
					case "Q":
						Chess.board[file][rank] = new Queen('b', "bQ", dest);
						break;
					default:
						Chess.board[file][rank] = new Queen('b', "bQ", dest);
						break;
					}
				}
			}
		}
	}

	public boolean hasKing(boolean whitesTurn) {
		Piece piece;
		boolean hasWhiteK = false;
		boolean hasBlackK = false;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				piece = Play.board[i][j];
				
				// Get the kings plausible moveSet. If the king cannot move and is under check, then the player loses.
				if (piece != null && piece.pieceName.equals("wK")) {
					hasWhiteK = true;
				}
				else if (piece != null && piece.pieceName.equals("bK")) {
					hasBlackK = true;
				}
			}
		}
		if (!whitesTurn && hasBlackK) { 
			return true;
		}
		else if (whitesTurn && hasWhiteK) {
			return true;
		}
		
		return false;
	}
}
