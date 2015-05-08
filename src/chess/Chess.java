package chess;
import java.util.Scanner;
import java.util.StringTokenizer;

import control.Control;

import pieces.Pawn;
import pieces.Piece;
/*
 * CS 213 
 * Assignment 2: Chess
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
public class Chess {
	public static Control control = new Control();
	public static Piece[][] board = new Piece[8][8]; 

	
	public static void main(String[] args) {
		
		//control.setBoard();
		control.printBoard();
		control.processPlayerInput();
		
	}

}