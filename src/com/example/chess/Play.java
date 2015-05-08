package com.example.chess;
/*
 * CS 213 
 * Android Project
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import pieces.Bishop;
import pieces.Coordinate;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import control.Control;

public class Play extends Activity implements Serializable{
	
	private static final long serialVersionUID = 5371658068242695888L;
	private static Piece piece = new Piece();
	public static Control control = new Control();
	public static Piece[][] board = new Piece[8][8];
	public Piece[][] undoBoard = new Piece[8][8];
	public static ImageButton[][] buttonBoard = new ImageButton[8][8];
	
	// List of data structures
	public static ArrayList<Record> recordList = new ArrayList<Record>();
	public static ArrayList<String> srcList = new ArrayList<String>();
	public static ArrayList<String> destList = new ArrayList<String>();
	public static ArrayList<Piece[][]> moveList = new ArrayList<Piece[][]>();
	
	public static Piece prevPiece;
	public static Piece undoPiece;
	
	public static String src;
	public static String dest;
	
	public static boolean firstClick;
	public static boolean whitesTurn;
	public static boolean hasUndone;
	public static boolean gameOver;
	
	static Context context;
	
	// Method to initialize everything to a default setting. Allow to start a fresh, clean game.
	public void reset(){
		board = new Piece[8][8];
		control.setBoard(board);
		
		firstClick = true;
		prevPiece = null;
		src = null; dest = null;
		whitesTurn = true;
		hasUndone = false;
		undoPiece = null;
		gameOver = false;
		Piece temp[][] = new Piece[8][8];
		fillBoard(board, temp);
		moveList.add(temp);

	}
	
	public static ImageButton 
		a8 = null, b8 = null, c8 = null, d8 = null, e8 = null, f8 = null, g8 = null, h8 = null,
		a7 = null, b7 = null, c7 = null, d7 = null, e7 = null, f7 = null, g7 = null, h7 = null,
		a6 = null, b6 = null, c6 = null, d6 = null, e6 = null, f6 = null, g6 = null, h6 = null,
		a5 = null, b5 = null, c5 = null, d5 = null, e5 = null, f5 = null, g5 = null, h5 = null,
		a4 = null, b4 = null, c4 = null, d4 = null, e4 = null, f4 = null, g4 = null, h4 = null,
		a3 = null, b3 = null, c3 = null, d3 = null, e3 = null, f3 = null, g3 = null, h3 = null,
		a2 = null, b2 = null, c2 = null, d2 = null, e2 = null, f2 = null, g2 = null, h2 = null,
		a1 = null, b1 = null, c1 = null, d1 = null, e1 = null, f1 = null, g1 = null, h1 = null;
	
	public static Button undo_button = null;
	public static Button AI_button = null;
	public static Button draw_button = null;
	public static Button resign_button = null;
	
	public static Drawable bRook;
	public static Drawable bKnight;
	public static Drawable bBishop;
	public static Drawable bKing;
	public static Drawable bQueen;
	public static Drawable bPawn;
	public static Drawable wRook;
	public static Drawable wKnight;
	public static Drawable wBishop;
	public static Drawable wKing;
	public static Drawable wQueen;
	public static Drawable wPawn;
	public static Drawable lightbg, darkbg;
	
	// AKIN TO MAIN
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		reset();
		context = getApplicationContext();
		createImageButtons();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void Undo(View view) {
		if (!gameOver) {
			if (undoPiece == null) {
				print("Cannot undo. A move has not been made");
				return;
			}
			
			// if Undo wasn't used before
			if (!hasUndone) {
				fillBoard();
				if (whitesTurn)
					print("Undo Black's turn");
				else
					print("Undo White's turn");
				
				
				Piece temp[][] = new Piece[8][8];
				fillBoard(board, temp);
				moveList.add(temp);
				
				whitesTurn = !whitesTurn;
				hasUndone = true;
				updateImageBoard();
			} else {
				print("Cannot undo twice.");
			}
		}
	}
	
	public void Draw(final View view) {
		if (!gameOver) {
			final AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Draw");
			if (whitesTurn) {
				builder.setMessage("White has requested a draw. Conclude?");
			}
			else {
				builder.setMessage("Black has requested a draw. Conclude?");
			}
			
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					endPrompt(view);
					gameOver = true;
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					//do nothing
				}
			});
			builder.show();
		}
	}
	
	public void Resign(final View view) {
		if (!gameOver) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Resign");
			if (whitesTurn) {
				builder.setMessage("White has resigned");
				gameOver = true;
			}
			else {
				builder.setMessage("Black has resigned");
				gameOver = true;
			}
			
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					endPrompt(view);
				}
			});
			
			builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					endPrompt(view);
				}
			});
			builder.show();
		}
	}
	
	public void createImageButtons(){
		// Instantiate the buttons
		a8 = (ImageButton)findViewById(R.id.a8);	buttonBoard[0][7] = a8;
		b8 = (ImageButton)findViewById(R.id.b8);	buttonBoard[1][7] = b8;
		c8 = (ImageButton)findViewById(R.id.c8);	buttonBoard[2][7] = c8;
		d8 = (ImageButton)findViewById(R.id.d8);	buttonBoard[3][7] = d8;
		e8 = (ImageButton)findViewById(R.id.e8);	buttonBoard[4][7] = e8;
		f8 = (ImageButton)findViewById(R.id.f8);	buttonBoard[5][7] = f8;
		g8 = (ImageButton)findViewById(R.id.g8);	buttonBoard[6][7] = g8;
		h8 = (ImageButton)findViewById(R.id.h8);	buttonBoard[7][7] = h8;
		a7 = (ImageButton)findViewById(R.id.a7);	buttonBoard[0][6] = a7;
		b7 = (ImageButton)findViewById(R.id.b7);	buttonBoard[1][6] = b7;
		c7 = (ImageButton)findViewById(R.id.c7);	buttonBoard[2][6] = c7;
		d7 = (ImageButton)findViewById(R.id.d7);	buttonBoard[3][6] = d7;
		e7 = (ImageButton)findViewById(R.id.e7);	buttonBoard[4][6] = e7;
		f7 = (ImageButton)findViewById(R.id.f7);	buttonBoard[5][6] = f7;
		g7 = (ImageButton)findViewById(R.id.g7);	buttonBoard[6][6] = g7;
		h7 = (ImageButton)findViewById(R.id.h7);	buttonBoard[7][6] = h7;
		a6 = (ImageButton)findViewById(R.id.a6);	buttonBoard[0][5] = a6;
		b6 = (ImageButton)findViewById(R.id.b6);	buttonBoard[1][5] = b6;
		c6 = (ImageButton)findViewById(R.id.c6);	buttonBoard[2][5] = c6;
		d6 = (ImageButton)findViewById(R.id.d6);	buttonBoard[3][5] = d6;
		e6 = (ImageButton)findViewById(R.id.e6);	buttonBoard[4][5] = e6;
		f6 = (ImageButton)findViewById(R.id.f6);	buttonBoard[5][5] = f6;
		g6 = (ImageButton)findViewById(R.id.g6);	buttonBoard[6][5] = g6;
		h6 = (ImageButton)findViewById(R.id.h6);	buttonBoard[7][5] = h6;
		a5 = (ImageButton)findViewById(R.id.a5);	buttonBoard[0][4] = a5;
		b5 = (ImageButton)findViewById(R.id.b5);	buttonBoard[1][4] = b5;
		c5 = (ImageButton)findViewById(R.id.c5);	buttonBoard[2][4] = c5;
		d5 = (ImageButton)findViewById(R.id.d5);	buttonBoard[3][4] = d5;
		e5 = (ImageButton)findViewById(R.id.e5);	buttonBoard[4][4] = e5;
		f5 = (ImageButton)findViewById(R.id.f5);	buttonBoard[5][4] = f5;
		g5 = (ImageButton)findViewById(R.id.g5);	buttonBoard[6][4] = g5;
		h5 = (ImageButton)findViewById(R.id.h5);	buttonBoard[7][4] = h5;
		a4 = (ImageButton)findViewById(R.id.a4);	buttonBoard[0][3] = a4;
		b4 = (ImageButton)findViewById(R.id.b4);	buttonBoard[1][3] = b4;
		c4 = (ImageButton)findViewById(R.id.c4);	buttonBoard[2][3] = c4;
		d4 = (ImageButton)findViewById(R.id.d4);	buttonBoard[3][3] = d4;
		e4 = (ImageButton)findViewById(R.id.e4);	buttonBoard[4][3] = e4;
		f4 = (ImageButton)findViewById(R.id.f4);	buttonBoard[5][3] = f4;
		g4 = (ImageButton)findViewById(R.id.g4);	buttonBoard[6][3] = g4;
		h4 = (ImageButton)findViewById(R.id.h4);	buttonBoard[7][3] = h4;
		a3 = (ImageButton)findViewById(R.id.a3);	buttonBoard[0][2] = a3;
		b3 = (ImageButton)findViewById(R.id.b3);	buttonBoard[1][2] = b3;
		c3 = (ImageButton)findViewById(R.id.c3);	buttonBoard[2][2] = c3;
		d3 = (ImageButton)findViewById(R.id.d3);	buttonBoard[3][2] = d3;
		e3 = (ImageButton)findViewById(R.id.e3);	buttonBoard[4][2] = e3;
		f3 = (ImageButton)findViewById(R.id.f3);	buttonBoard[5][2] = f3;
		g3 = (ImageButton)findViewById(R.id.g3);	buttonBoard[6][2] = g3;
		h3 = (ImageButton)findViewById(R.id.h3);	buttonBoard[7][2] = h3;
		a2 = (ImageButton)findViewById(R.id.a2);	buttonBoard[0][1] = a2;
		b2 = (ImageButton)findViewById(R.id.b2);	buttonBoard[1][1] = b2;
		c2 = (ImageButton)findViewById(R.id.c2);	buttonBoard[2][1] = c2;
		d2 = (ImageButton)findViewById(R.id.d2);	buttonBoard[3][1] = d2;
		e2 = (ImageButton)findViewById(R.id.e2);	buttonBoard[4][1] = e2;
		f2 = (ImageButton)findViewById(R.id.f2);	buttonBoard[5][1] = f2;
		g2 = (ImageButton)findViewById(R.id.g2);	buttonBoard[6][1] = g2;
		h2 = (ImageButton)findViewById(R.id.h2);	buttonBoard[7][1] = h2;
		a1 = (ImageButton)findViewById(R.id.a1);	buttonBoard[0][0] = a1;
		b1 = (ImageButton)findViewById(R.id.b1);	buttonBoard[1][0] = b1;
		c1 = (ImageButton)findViewById(R.id.c1);	buttonBoard[2][0] = c1;
		d1 = (ImageButton)findViewById(R.id.d1);	buttonBoard[3][0] = d1;
		e1 = (ImageButton)findViewById(R.id.e1);	buttonBoard[4][0] = e1;
		f1 = (ImageButton)findViewById(R.id.f1);	buttonBoard[5][0] = f1;
		g1 = (ImageButton)findViewById(R.id.g1);	buttonBoard[6][0] = g1;
		h1 = (ImageButton)findViewById(R.id.h1);	buttonBoard[7][0] = h1;
		
		// Image Buttons to be referenced for their images. Not meant to be modified.
		bRook = a8.getDrawable();
		bKnight = b8.getDrawable();
		bBishop = c8.getDrawable();
		bKing = d8.getDrawable();
		bQueen = e8.getDrawable();
		bPawn = a7.getDrawable();
		
		wRook = a1.getDrawable();
		wKnight = b1.getDrawable();
		wBishop = c1.getDrawable();
		wKing = d1.getDrawable();
		wQueen = e1.getDrawable();
		wPawn = a2.getDrawable();
		
		lightbg = b3.getDrawable();
		darkbg = a3.getDrawable();
		
		// Attach a listener to each button
		a8.setOnClickListener(new ImageButtonListener("a8", a8));
        b8.setOnClickListener(new ImageButtonListener("b8", b8));
        c8.setOnClickListener(new ImageButtonListener("c8", c8));
        d8.setOnClickListener(new ImageButtonListener("d8", d8));
        e8.setOnClickListener(new ImageButtonListener("e8", e8));
        f8.setOnClickListener(new ImageButtonListener("f8", f8));
        g8.setOnClickListener(new ImageButtonListener("g8", g8));
        h8.setOnClickListener(new ImageButtonListener("h8", h8));
        a7.setOnClickListener(new ImageButtonListener("a7", a7));
        b7.setOnClickListener(new ImageButtonListener("b7", b7));
        c7.setOnClickListener(new ImageButtonListener("c7", c7));
        d7.setOnClickListener(new ImageButtonListener("d7", d7));
        e7.setOnClickListener(new ImageButtonListener("e7", e7));
        f7.setOnClickListener(new ImageButtonListener("f7", f7));
        g7.setOnClickListener(new ImageButtonListener("g7", g7));
        h7.setOnClickListener(new ImageButtonListener("h7", h7));
        a6.setOnClickListener(new ImageButtonListener("a6", a6));
        b6.setOnClickListener(new ImageButtonListener("b6", b6));
        c6.setOnClickListener(new ImageButtonListener("c6", c6));
        d6.setOnClickListener(new ImageButtonListener("d6", d6));
        e6.setOnClickListener(new ImageButtonListener("e6", e6));
        f6.setOnClickListener(new ImageButtonListener("f6", f6));
        g6.setOnClickListener(new ImageButtonListener("g6", g6));
        h6.setOnClickListener(new ImageButtonListener("h6", h6));
        a5.setOnClickListener(new ImageButtonListener("a5", a5));
        b5.setOnClickListener(new ImageButtonListener("b5", b5));
        c5.setOnClickListener(new ImageButtonListener("c5", c5));
        d5.setOnClickListener(new ImageButtonListener("d5", d5));
        e5.setOnClickListener(new ImageButtonListener("e5", e5));
        f5.setOnClickListener(new ImageButtonListener("f5", f5));
        g5.setOnClickListener(new ImageButtonListener("g5", g5));
        h5.setOnClickListener(new ImageButtonListener("h5", h5));
        a4.setOnClickListener(new ImageButtonListener("a4", a4));
        b4.setOnClickListener(new ImageButtonListener("b4", b4));
        c4.setOnClickListener(new ImageButtonListener("c4", c4));
        d4.setOnClickListener(new ImageButtonListener("d4", d4));
        e4.setOnClickListener(new ImageButtonListener("e4", e4));
        f4.setOnClickListener(new ImageButtonListener("f4", f4));
        g4.setOnClickListener(new ImageButtonListener("g4", g4));
        h4.setOnClickListener(new ImageButtonListener("h4", h4));
        a3.setOnClickListener(new ImageButtonListener("a3", a3));
        b3.setOnClickListener(new ImageButtonListener("b3", b3));
        c3.setOnClickListener(new ImageButtonListener("c3", c3));
        d3.setOnClickListener(new ImageButtonListener("d3", d3));
        e3.setOnClickListener(new ImageButtonListener("e3", e3));
        f3.setOnClickListener(new ImageButtonListener("f3", f3));
        g3.setOnClickListener(new ImageButtonListener("g3", g3));
        h3.setOnClickListener(new ImageButtonListener("h3", h3));
        a2.setOnClickListener(new ImageButtonListener("a2", a2));
        b2.setOnClickListener(new ImageButtonListener("b2", b2));
        c2.setOnClickListener(new ImageButtonListener("c2", c2));
        d2.setOnClickListener(new ImageButtonListener("d2", d2));
        e2.setOnClickListener(new ImageButtonListener("e2", e2));
        f2.setOnClickListener(new ImageButtonListener("f2", f2));
        g2.setOnClickListener(new ImageButtonListener("g2", g2));
        h2.setOnClickListener(new ImageButtonListener("h2", h2));
        a1.setOnClickListener(new ImageButtonListener("a1", a1));
        b1.setOnClickListener(new ImageButtonListener("b1", b1));
        c1.setOnClickListener(new ImageButtonListener("c1", c1));
        d1.setOnClickListener(new ImageButtonListener("d1", d1));
        e1.setOnClickListener(new ImageButtonListener("e1", e1));
        f1.setOnClickListener(new ImageButtonListener("f1", f1));
        g1.setOnClickListener(new ImageButtonListener("g1", g1));
        h1.setOnClickListener(new ImageButtonListener("h1", h1));
		
		
	}
	
	/**
	 * Click Listener for each Image Button
	 */
	public class ImageButtonListener implements OnClickListener {
		private String coordinate;
		private ImageButton currButton;
		
		// Constructor
		// Takes in the clicked button's coordinate and ImageButton
		public ImageButtonListener(String coord, ImageButton button) {
			this.coordinate = coord;
			this.currButton = button;
		}

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			
			if (!gameOver) {
				Coordinate currCoord = piece.convert(coordinate);
				Piece currPiece = board[currCoord.xCoord][currCoord.yCoord];
				
				// WHITE'S TURN
				if (whitesTurn) {
					// FIRST CLICK on your own piece
					if (currPiece != null && firstClick && currPiece.getPiece(currCoord).color == 'w') {
						src = coordinate;
						prevPiece = currPiece;
						updateImageBoard();
						currButton.setBackgroundColor(Color.YELLOW);
						firstClick = false;
					}
					// FIRST CLICK switching to another piece
					else if (currPiece != null && !firstClick  && currPiece.getPiece(currCoord).color == 'w') {
						src = coordinate;
						prevPiece = currPiece;
						updateImageBoard();
						currButton.setBackgroundColor(Color.YELLOW);
						firstClick = false;
					}
					// SECOND CLICK attempt to capture opposing piece
					else if (currPiece != null && currPiece.getPiece(currCoord).color == 'b' && !firstClick) {
						dest = coordinate;
						fillUndoBoard();
						undoPiece = copyByValue(prevPiece);
						
						// Piece Moving Logic
						if (prevPiece != null && prevPiece.canMove(src, dest, whitesTurn)) {
							prevPiece.movePiece(src, dest, whitesTurn);
							// Check for promotion
							promotionCheck(prevPiece, dest);
							
							// Victory/Check
							piece.checkKing(!whitesTurn);
							if (control.isKingChecked(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Black king in check", Toast.LENGTH_SHORT).show();
							}
							if (control.checkVictory(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Checkmate, White wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							if (!(control.hasKing(!whitesTurn))) {
								Toast.makeText(getApplicationContext(), "White wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							
							Piece temp[][] = new Piece[8][8];
							fillBoard(board, temp);
							moveList.add(temp);
							srcList.add(src);
							destList.add(dest);
							hasUndone = false;
							whitesTurn = !whitesTurn;
						} else {
							// Implement error pop up window.
							Toast.makeText(getApplicationContext(), "Invalid move!", Toast.LENGTH_SHORT).show();
						}
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// SECOND CLICK attempt to move to empty space.
					else if (currPiece == null && !firstClick) {
						dest = coordinate;
						fillUndoBoard();
						undoPiece = copyByValue(prevPiece);
						
						// Piece moving logic
						if (prevPiece != null && prevPiece.canMove(src, dest, whitesTurn)) {
							prevPiece.movePiece(src, dest, whitesTurn);
							// Check for promotion
							promotionCheck(prevPiece, dest);
							
							// Victory/Check
							piece.checkKing(!whitesTurn);
							if (control.isKingChecked(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Black king in check", Toast.LENGTH_SHORT).show();
							}
							if (control.checkVictory(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Checkmate, White wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							if (!(control.hasKing(!whitesTurn))) {
								Toast.makeText(getApplicationContext(), "White wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							
							Piece temp[][] = new Piece[8][8];
							fillBoard(board, temp);
							moveList.add(temp);
							srcList.add(src);
							destList.add(dest);
							hasUndone = false;
							whitesTurn = !whitesTurn;
						} else {
							// Implement error pop up window.
							Toast.makeText(getApplicationContext(), "Invalid move!", Toast.LENGTH_SHORT).show();
						}
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// FIRST CLICK cannot select an opposing piece.
					else if (currPiece != null && currPiece.getPiece(currCoord).color == 'b' && firstClick) {
						src = null; dest = null;
						
						// Error message
						Toast.makeText(getApplicationContext(), "White's turn, cannot move black pieces", Toast.LENGTH_SHORT).show();
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// DEFAULT CLICK CASE
					else {
						src = null; dest = null;
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
				}
				
				// BLACK'S TURN
				else if (!whitesTurn) {
					// FIRST CLICK on your own piece
					if (currPiece != null && firstClick && currPiece.getPiece(currCoord).color == 'b') {
						src = coordinate;
						prevPiece = currPiece;
						updateImageBoard();
						currButton.setBackgroundColor(Color.YELLOW);
						firstClick = false;
					}
					// FIRST CLICK switching to another piece
					else if (currPiece != null && !firstClick && currPiece.getPiece(currCoord).color == 'b') {
						src = coordinate;
						prevPiece = currPiece;
						updateImageBoard();
						currButton.setBackgroundColor(Color.YELLOW);
						firstClick = false;
					}
					// SECOND CLICK attempt to capture opposing piece
					else if (currPiece != null && currPiece.getPiece(currCoord).color == 'w' && !firstClick) {
						dest = coordinate;
						fillUndoBoard();
						undoPiece = copyByValue(prevPiece);
						
						// Piece Moving Logic
						if (prevPiece != null && prevPiece.canMove(src, dest, whitesTurn)) {
							prevPiece.movePiece(src, dest, whitesTurn);
							// Check for promotion
							promotionCheck(prevPiece, dest);
							
							// Victory/Check
							piece.checkKing(!whitesTurn);
							if (control.isKingChecked(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "White king in check", Toast.LENGTH_SHORT).show();
							}
							if (control.checkVictory(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Checkmate, Black wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							if (!(control.hasKing(!whitesTurn))) {
								Toast.makeText(getApplicationContext(), "Black wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							
							Piece temp[][] = new Piece[8][8];
							fillBoard(board, temp);
							moveList.add(temp);
							srcList.add(src);
							destList.add(dest);
							hasUndone = false;
							whitesTurn = !whitesTurn;
						} else {
							// Implement error pop up window.
							Toast.makeText(getApplicationContext(), "Invalid move!", Toast.LENGTH_SHORT).show();
						}
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// SECOND CLICK attempt to move to empty space.
					else if (currPiece == null && !firstClick) {
						dest = coordinate;
						fillUndoBoard();
						undoPiece = copyByValue(prevPiece);
						
						// Piece moving logic
						if (prevPiece != null && prevPiece.canMove(src, dest, whitesTurn)) {
							prevPiece.movePiece(src, dest, whitesTurn);
							// Check for promotion
							promotionCheck(prevPiece, dest);
							
							// Victory/Check
							piece.checkKing(!whitesTurn);
							if (control.isKingChecked(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "White king in check", Toast.LENGTH_SHORT).show();
							}
							if (control.checkVictory(!whitesTurn)) {
								Toast.makeText(getApplicationContext(), "Checkmate, Black wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							if (!(control.hasKing(!whitesTurn))) {
								Toast.makeText(getApplicationContext(), "Black wins", Toast.LENGTH_SHORT).show();
								endPrompt(v);
								gameOver = true;
							}
							
							Piece temp[][] = new Piece[8][8];
							fillBoard(board, temp);
							moveList.add(temp);
							srcList.add(src);
							destList.add(dest);
							hasUndone = false;
							whitesTurn = !whitesTurn;
						} else {
							// Implement error pop up window.
							Toast.makeText(getApplicationContext(), "Invalid move!", Toast.LENGTH_SHORT).show();
						}
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// FIRST CLICK cannot select an opposing piece.
					else if (currPiece != null && currPiece.getPiece(currCoord).color == 'w' && firstClick) {
						src = null; dest = null;
						
						// Error message
						Toast.makeText(getApplicationContext(), "Black's turn, cannot move white pieces", Toast.LENGTH_SHORT).show();
						
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
					// DEFAULT CLICK CASE
					else {
						src = null; dest = null;
						prevPiece = null;
						updateImageBoard();
						firstClick = true;
					}
				}
			}
		}
	}
	
	@SuppressLint("NewApi")
	public void updateImageBoard() {
		Piece currPiece;
		ImageButton currButton;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				currPiece = board[i][j];
				currButton = buttonBoard[i][j];
				// If the current coordinate has an existing piece
				if (board[i][j] != null) {
					
					switch (currPiece.pieceName) {
					case "wp": currButton.setImageDrawable(wPawn); break;
					case "wR": currButton.setImageDrawable(wRook); break;
					case "wN": currButton.setImageDrawable(wKnight); break;
					case "wB": currButton.setImageDrawable(wBishop); break;
					case "wK": currButton.setImageDrawable(wKing); break;
					case "wQ": currButton.setImageDrawable(wQueen); break;
					case "bp": currButton.setImageDrawable(bPawn); break;
					case "bR": currButton.setImageDrawable(bRook); break;
					case "bN": currButton.setImageDrawable(bKnight); break;
					case "bB": currButton.setImageDrawable(bBishop); break;
					case "bK": currButton.setImageDrawable(bKing); break;
					case "bQ": currButton.setImageDrawable(bQueen); break;
					
					default: currButton.setImageDrawable(null); break;
					}
					// This small block fixes some weird formatting error with the size of the background tile color.
					if (((i+j) % 2) == 1)
						currButton.setBackground(lightbg);
					else
						currButton.setBackground(darkbg);
				}
				// If no piece exists at the current spot, just set the background to the color of the board.
				else if (board[i][j] == null) {
					if (((i+j) % 2) == 1) {
						currButton.setImageDrawable(null);
						currButton.setBackground(lightbg);
					}
					else {
						currButton.setImageDrawable(null);
						currButton.setBackground(darkbg);
					}
				}
			}
		}
	}
	
	public Piece copyByValue(Piece curr) {
		// Error check
		if (curr == null) {
			return null;
		}
		
		// Copy by value for pawn.
		if (curr.pieceName.equals("wp")) {
			Pawn ptemp = new Pawn('w', "wp", "null");
			Pawn tempcurr = (Pawn) curr;
			ptemp.hasMadeInitMove = tempcurr.hasMadeInitMove;
			return ptemp;
		}
		else if (curr.pieceName.equals("bp")) {
			Pawn ptemp = new Pawn('b', "bp", "null");
			Pawn tempcurr = (Pawn) curr;
			ptemp.hasMadeInitMove = tempcurr.hasMadeInitMove;
			return ptemp;
		}
		// Copy by value for rook
		else if (curr.pieceName.equals("wR")) {
			Rook rtemp = new Rook('w', "wR", "null");
			Rook tempcurr = (Rook) curr;
			rtemp.canCastling = tempcurr.canCastling;
			return rtemp;
		}
		else if (curr.pieceName.equals("bR")) {
			Rook rtemp = new Rook('b', "bR", "null");
			Rook tempcurr = (Rook) curr;
			rtemp.canCastling = tempcurr.canCastling;
			return rtemp;
		}
		// Copy by value for king
		else if (curr.pieceName.equals("wK")) {
			King ktemp = new King('w', "wK", "null");
			King tempcurr = (King) curr;
			ktemp.hasMadeInitMove = tempcurr.hasMadeInitMove;
			ktemp.inCheck = tempcurr.inCheck;
			ktemp.isCheckmate = tempcurr.isCheckmate;
			return ktemp;
		}
		else if (curr.pieceName.equals("bK")) {
			King ktemp = new King('b', "bK", "null");
			King tempcurr = (King) curr;
			ktemp.hasMadeInitMove = tempcurr.hasMadeInitMove;
			ktemp.inCheck = tempcurr.inCheck;
			ktemp.isCheckmate = tempcurr.isCheckmate;
			return ktemp;
		}
		else {
			// else, no need to copy-by-value for anything else
			// no need to save the state of their booleans (if they have any)
			return curr;
		}
	}
	
	// To be Finished
	public void endPrompt(final View view) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Would you like to save this game?");
		builder.setMessage("Please enter a title for the game");
		final EditText input = new EditText(this); 
		builder.setView(input);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String value = input.getText().toString();
				
				if(value.equals("")) {
					print("Please enter a value for the name");
					endPrompt(view);
				}
				else if(!isDuplicate(value)){
					//Record record = new Record(value, srcList, destList);
					Record record = new Record(value, moveList);
					recordList.add(record);
					
					save(context, value, record);
					
					gameOver = true;
					Intent intent = new Intent(view.getContext(), MainMenu.class);
					startActivityForResult(intent, 0);
				} else {
					print("A game already exists with that name");
					endPrompt(view);
				}
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				gameOver = true;
				Intent intent = new Intent(view.getContext(), MainMenu.class);
				startActivityForResult(intent, 0);
			}
		});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				print("Game has not been saved");
				gameOver = true;
				Intent intent = new Intent(view.getContext(), MainMenu.class);
				startActivityForResult(intent, 0);
			}
		});
		builder.show();
		
	}
	
	/**
	 * Check if the given value already exists as a name in the list of records
	 * @param value name of the record
	 * @return boolean value determining if the value is already an existing record name
	 */
	public static boolean isDuplicate(String value) {
		
		for(int i = 0; i < recordList.size(); i++) {
			String currName = recordList.get(i).recordName;
			
			if (value.equals(currName)) {
				return true;
			}
		}
		return false;
	}

    
    public void save(Context context, String fileName, Record record) {
    	File f = new File(fileName);
    	if (f.exists()) {
    		// File already exists
    		return;
    	}
    	FileOutputStream fos;
    	ObjectOutputStream oos;
    	try {
    		fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
    		oos = new ObjectOutputStream(fos);
    		oos.writeObject(record);
    		oos.close();
    		fos.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
	@SuppressWarnings("unchecked")
	public static Record load(Context context, String fileName) {
		Record record;
		boolean fileNotExists = true;
		String[] files = context.fileList();
		if (files == null) {
			return null;
		}
		for (String file : files) {
			if (file.equals(fileName)) {				
				fileNotExists = false;
			}
		}

		if (fileNotExists)
			return null;
		
		FileInputStream fis;
		ObjectInputStream ois;

		try {
			fis = context.openFileInput(fileName);
			ois = new ObjectInputStream(fis);
			record = (Record) ois.readObject();
			ois.close();
		} catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}

		return record;
	}
    
    
    
	
	public void fillUndoBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece oldPiece = board[i][j];
				Piece newPiece = copyByValue(oldPiece);
				undoBoard[i][j] = newPiece;
			}
		}
	}
	public void fillBoard() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece oldPiece = undoBoard[i][j];
				Piece newPiece = copyByValue(oldPiece);
				board[i][j] = newPiece;
			}
		}
	}
	
	public static void fillBoard(Piece[][] board1, Piece[][] board2){
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece oldPiece = board1[i][j];
				Piece newPiece = oldPiece;
				board2[i][j] = newPiece;
			}
		}
	}
	public void print(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressWarnings("deprecation")
	public void promotionCheck(Piece piece, String dest) {
		// Error check
		if (piece == null || dest == "") {
			return;
		}
		final Coordinate destination = piece.convert(dest);
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		if (piece.pieceName.equals("wp") && destination.yCoord == 7) {
             alertDialog.setTitle("Promote");
             alertDialog.setMessage("Select which piece to promote, cancel for queen");
             
             alertDialog.setButton("Knight", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Knight('w', "wN", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setButton2("Bishop", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Bishop('w', "wB", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setButton3("Rook", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Rook('w', "wR", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
				@Override
				public void onCancel(DialogInterface dialog) {
					Play.board[destination.xCoord][destination.yCoord] = new Queen('w', "wQ", "");
					updateImageBoard();
				}
              });
             alertDialog.show();
		}
		else if (piece.pieceName.equals("bp") && destination.yCoord == 0) {
			 alertDialog.setTitle("Promote");
             alertDialog.setMessage("Select which piece to promote, cancel for queen");
             
             alertDialog.setButton("Knight", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Knight('b', "bN", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setButton2("Bishop", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Bishop('b', "bB", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setButton3("Rook", new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 Play.board[destination.xCoord][destination.yCoord] = new Rook('b', "bR", "");
                	 updateImageBoard();
                 }
              });
             alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
				@Override
				public void onCancel(DialogInterface dialog) {
					Play.board[destination.xCoord][destination.yCoord] = new Queen('b', "bQ", "");
					updateImageBoard();
				}
              });
             alertDialog.show();
		}
	}
}
