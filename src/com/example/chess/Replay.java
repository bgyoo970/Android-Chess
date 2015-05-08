package com.example.chess;
/*
 * CS 213
 * Android Project
 * Sesh Venugopal
 * 
 * @author Brian Yoo bgy2
 */
import java.io.IOException;

import pieces.Piece;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import control.Control;

public class Replay extends Activity{
	
	/**
	 * Holds the selected record to be replayed
	 */
	protected static Record currentReplayRecord;
	public static String currSrc, currDest;
	public static int currIndex;
	
	public static ImageButton[][] buttonBoard = new ImageButton[8][8];
	Piece piece = new Piece();
	Control control = new Control();
	public static ImageButton 
		a8 = null, b8 = null, c8 = null, d8 = null, e8 = null, f8 = null, g8 = null, h8 = null,
		a7 = null, b7 = null, c7 = null, d7 = null, e7 = null, f7 = null, g7 = null, h7 = null,
		a6 = null, b6 = null, c6 = null, d6 = null, e6 = null, f6 = null, g6 = null, h6 = null,
		a5 = null, b5 = null, c5 = null, d5 = null, e5 = null, f5 = null, g5 = null, h5 = null,
		a4 = null, b4 = null, c4 = null, d4 = null, e4 = null, f4 = null, g4 = null, h4 = null,
		a3 = null, b3 = null, c3 = null, d3 = null, e3 = null, f3 = null, g3 = null, h3 = null,
		a2 = null, b2 = null, c2 = null, d2 = null, e2 = null, f2 = null, g2 = null, h2 = null,
		a1 = null, b1 = null, c1 = null, d1 = null, e1 = null, f1 = null, g1 = null, h1 = null;
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
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_replay);
		
		currIndex = 0;
		control.setBoard(Play.board);
		createImageButtons();
		
		// Go back one move
		Button prev = (Button)findViewById(R.id.prev_button);
		prev.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				currIndex--;
				if (currIndex >= 0 && currIndex < currentReplayRecord.moveList.size()) {
					if(Play.moveList.size() != 0)
						Play.fillBoard(Play.moveList.get(currIndex), Play.board);
					else
						print("Reached the start of the game");
					updateImageBoard();
				} 
				else if (currentReplayRecord.moveList.size() == 0 || currentReplayRecord.moveList.size() == 1) {
					print("Reached the start of the game");
				}
				else if (currIndex >= currentReplayRecord.moveList.size()) {
					currIndex = currentReplayRecord.moveList.size() - 1;
					currIndex--;
					if(Play.moveList.size() != 0)
						Play.fillBoard(Play.moveList.get(currIndex), Play.board);
					else
						print("Reached the start of the game");
					updateImageBoard();
				} else {
					print("Reached the start of the game");
				}
			}
		});
		
		// Go forward one move
		Button next = (Button)findViewById(R.id.next_button);
		next.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				currIndex++;
				if (currIndex < currentReplayRecord.moveList.size() && currIndex >= 0) {
					//movelist size is 1
					if(Play.moveList.size() != 0)
						Play.fillBoard(Play.moveList.get(currIndex), Play.board);
					else
						print("Reached the end of the game");
					updateImageBoard();
				} else if (currIndex < 0) {
					currIndex = 0;
					currIndex++;
					if(Play.moveList.size() != 0)
						Play.fillBoard(Play.moveList.get(currIndex), Play.board);
					else
						print("Reached the end of the game");
					updateImageBoard();
				}
				else {
					print("Reached the end of the game");
				}
				
				/*
				if (currIndex < currentReplayRecord.srcList.size()) {
					currSrc = currentReplayRecord.srcList.get(currIndex);
					currDest = currentReplayRecord.destList.get(currIndex);
					
					piece.movePiece(currSrc, currDest, whitesTurn);
					currIndex++;
					whitesTurn = !whitesTurn;
					updateImageBoard();
				} else {
					print("Reached the end of the game");
				}*/
			}
		});
		
		Button back = (Button)findViewById(R.id.back_button);
		back.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Playback.currentGameSelection = null;
				Intent intent = new Intent(view.getContext(), Playback.class);
				startActivityForResult(intent, 0);
			}
		});
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
	}
	
	public void print(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
	@SuppressLint("NewApi")
	public static void updateImageBoard() {
		Piece currPiece;
		ImageButton currButton;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				currPiece = Play.board[i][j];
				currButton = buttonBoard[i][j];
				// If the current coordinate has an existing piece
				if (Play.board[i][j] != null) {
					
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
				else if (Play.board[i][j] == null) {
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
}
