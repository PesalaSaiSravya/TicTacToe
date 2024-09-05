package game;

import java.util.Random;
import java.util.Scanner;

class Game{
	static char[][] board;
	public Game() {
		board = new char[3][3];
		initBoard();
	}
	
	void initBoard() {
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				board[i][j] = ' ';
			}
		}
	}
	
	static void displayBoard() {
		System.out.println("-------------");
		for(int i=0; i<board.length; i++) {
			System.out.print("| ");
			for(int j=0; j<board[i].length; j++) {
				System.out.print(board[i][j]+" | ");
				
			}
			System.out.println();
			System.out.println("-------------");
		}
	}
	
	static void placeMark(int row, int col, char mark) {
		if(row>=0 && row<=2 && col>=0 && col<=2) {
			board[row][col] = mark; 
		}
		else {
			System.out.println("Invalid Position");
		}
	}
	
	static boolean checkColumnWin() {
		for(int i=0; i<=2; i++) {
			if(board[0][i]!=' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				return true;
			}
		}
		return false;
	}
	
	static boolean checkRowWin() {
		for(int i=0; i<=2; i++) {
			if(board[i][0]!=' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				return true;
			}
		}
		return false;
	}
	
	static boolean checkDiagonalWin() {
			if((board[0][0]!=' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2])||(board[0][2]!=' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
				return true;
			}
		
		return false;
	}
	
	static boolean checkDraw() {
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(board[i][j]==' ') {
					return false;
				}
			}
		}
		return true;
	}
}

abstract class Player{
	String name;
	char mark;
	
	abstract void makeMove();
	
	boolean isValidMove(int row, int col) {
		if(row>=0 && row<=2 && col>=0 && col<=2) {
			if(Game.board[row][col] == ' ') {
				return true;
			}
		}
		return false;
	}
}

class HumanPlayer extends Player{
	
	HumanPlayer(String name, char mark){
		this.name = name; 
		this.mark = mark;
	}
	
	void makeMove() {
		Scanner sc = new Scanner(System.in);
		int row; 
		int col;
		do {
			System.out.println("Enter row and col");
			row = sc.nextInt();
			col = sc.nextInt();
		}while(!isValidMove(row,col));
		
		Game.placeMark(row, col, mark);
	}
}

class AIPlayer extends Player{
	AIPlayer(String name, char mark){
		this.name = name; 
		this.mark = mark;
	}
	
	void makeMove() {
		Scanner sc = new Scanner(System.in);
		int row; 
		int col;
		do {
			Random r = new Random();
			row = r.nextInt(3);
			col = r.nextInt(3);
		}while(!isValidMove(row,col));
		
		Game.placeMark(row, col, mark);
	}
}


public class TicTacToe {
	public static void main(String[] args) {
		Game g = new Game();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String name = sc.next();
		HumanPlayer h1 = new HumanPlayer(name,'O');
		Player h2;
		System.out.println("Do you want to play against your friend or AI? \nEnter 1 to play against friend.\nEnter 2 to play against AI.");
		int n = sc.nextInt();
		
		if(n==1) {
			System.out.println("enter your friend's name: ");
			String fname = sc.next();
			h2 = new HumanPlayer(fname,'X');
		}
		else {
			h2= new AIPlayer("AI",'X');
		}
		
		Player cp;
		cp = h1;
		
		while(true) {
			System.out.println(cp.name + "'s turn");
			cp.makeMove();
			Game.displayBoard();
			if(Game.checkColumnWin() || Game.checkDiagonalWin() || Game.checkRowWin()) {
				System.out.println("Woohoo..!" + cp.name + " wins");
				break;
			}
			else if(Game.checkDraw()) {
				System.out.println("Game is draw");
				break;
			}
			else {
				if(cp==h1) {
					cp = h2;
				}
				else {
					cp = h1;
				}
			}
		}
	}
}
