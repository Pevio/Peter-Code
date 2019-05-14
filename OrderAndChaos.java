import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.control.*;

/*
 * GUI Order and Chaos played on one computer between two players
 */

public class OrderAndChaos extends Application {
	final double boardSize = 30;								//The number of pixels large the physical board is
	final Color[] stoneColors = new Color[] {Color.RED, Color.BLUE};
	private Pane pane = new Pane();
	
	private Label[][] board = new Label[6][6];					//Where the board is displayed on the screen
	private int[][] boardState = new int[6][6];					//0=empty, 1=Player 1, 2=Player 2.  They all start at 0
	private RadioButton[] stoneSelect = new RadioButton[2];				//For selecting which color stone we are placing
	private int whoUp = 1;										//Either 1 or 2: The player moving
	private Label turn = new Label("Order's Turn");				//The turn indicator Label
	private int selectedStone = -1;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//Set up the entire screen
		Scene scene = new Scene(pane, 220, 300);
		
		//Set up the board
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				//The board is represented by Label objects boardSize wide and tall
				board[i][j] = new Label("");
				board[i][j].setMinWidth(boardSize); board[i][j].setMaxWidth(boardSize);
				board[i][j].setMinHeight(boardSize); board[i][j].setMaxHeight(boardSize);
				
				//Adjust location based on i and j, and give them a tan background
				board[i][j].relocate(10 + i * boardSize, 50 + j * boardSize);
				board[i][j].setStyle("-fx-background-color: tan;");
				
				//Add to pane and set up the handler
				pane.getChildren().add(board[i][j]);
				final int row = i; final int column = j;
				board[i][j].setOnMouseClicked(e -> clickSquare(row, column));
				boardState[i][j] = -1;
			}
		}
		
		//Make some lines for the grid
		for (int i = 0; i <= 6; i++) {
			Line hLine = new Line(10, 50 + i * boardSize,  10 + 6 * boardSize, 50 + i * boardSize);
			Line vLine = new Line(10 + i * boardSize, 50, 10 + i * boardSize, 50 + 6 * boardSize);
			
			pane.getChildren().addAll(hLine, vLine);
		}
		
		//Selecting Stones
		String[] stoneLabels = new String []{"Red", "Blue"};
		for (int i = 0; i < 2; i++) {
			stoneSelect[i] = new RadioButton(stoneLabels[i]);
			stoneSelect[i].setFont(new Font(16));
			stoneSelect[i].relocate(10 + i * 70, 250);
			pane.getChildren().add(stoneSelect[i]);
			final int num = i;
			stoneSelect[i].setOnAction(e -> clickStone(num));
		}
		
		//Turn indicator
		turn.setFont(new Font(18));
		turn.relocate(10, 10);
		pane.getChildren().add(turn);
		
		//Finish up
		stage.setScene(scene);
		stage.setTitle("Order and Chaos");
		stage.show();
	}

	private void clickSquare(int r, int c) {
		//If we are allowed to go in this square, place a stone at location (r, c)
		if (boardState[r][c] == -1 && whoUp > 0 && selectedStone >= 0) {
			//The stone is in the middle of the square, 10 pixles in radius
			Circle stone = new Circle(board[r][c].getLayoutX() + boardSize / 2, board[r][c].getLayoutY() + boardSize / 2, 10);
			stone.setFill(stoneColors[selectedStone]);
			
			//Edit board state and put the stone in the pane
			boardState[r][c] = selectedStone;
			pane.getChildren().add(stone);
			
			//Reset stone selection
			for (int i = 0; i < 2; i++) {
				stoneSelect[i].setSelected(false);
			}
			selectedStone = -1;
			
			int gameOver = hasPlayerWon();
			if (gameOver >= 0) {
				//If the game is over, indicate it at all possible lines of 5
				turn.setText(gameOver == 1 ? "Order Wins!" : "Chaos Wins");
				whoUp = 0;
				for (int i = 0; i < 2; i++) {
					stoneSelect[i].setVisible(false);
				}
			} else {
				//If the game is not over, switch to the other player's turn
				if (whoUp == 1) {
					whoUp = 2;
					turn.setText("Chaos's Turn");
				} else {
					whoUp = 1;
					turn.setText("Order's Turn");
				}
			}
		}
	}
	private void clickStone(int i) {
		selectedStone = i;
		stoneSelect[(i + 1) % 2].setSelected(false);
	}
	
	private int hasPlayerWon() {
		//Checks to see if the game is over in either player's favor
		//1 means order wins, 0 means chaos wins, -1 means game is still going
		if (isHorizontalWin() || isVerticalWin() || isDiagonalWin()) return 1;
		if (isBoardFull()) return 0;
		return -1;
	}
	
	private boolean isHorizontalWin() {
		boolean win = false;
		for (int i = 0; i < board.length - 4; i++) {
			for (int j = 0; j < board[i].length; j++) {
				boolean won = true;
				for (int k = i; k < i + 5; k++) {
					if (boardState[k][j] != 0) won = false;
				}
				if (!won) {
					won = true;
					for (int k = i; k < i + 5; k++) {
						if (boardState[k][j] != 1) won = false;
					}
				}
				if (won) {
					for (int k = i; k < i + 5; k++) {
						board[k][j].setStyle("-fx-background-color: black;");
					}
					win = true;
				}
			}
		}
		return win;
	}
	
	private boolean isVerticalWin() {
		boolean win = false;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length - 4; j++) {
				boolean won = true;
				for (int k = j; k < j + 5; k++) {
					if (boardState[i][k] != 0) won = false;
				}
				if (!won) {
					won = true;
					for (int k = j; k < j + 5; k++) {
						if (boardState[i][k] != 1) won = false;
					}
				}
				if (won) {
					for (int k = j; k < j + 5; k++) {
						board[i][k].setStyle("-fx-background-color: black;");
					}
					win = true;
				}
			}
		}
		return win;
	}
	
	private boolean isDiagonalWin() {
		//Diagonal going down right
		boolean win = false;
		for (int i = 0; i < board.length - 4; i++) {
			for (int j = 0; j < board[i + 4].length - 4; j++) {
				boolean won = true;
				for (int k = 0; k < 5; k++) {
					if (boardState[i + k][j + k] != 0) won = false;
				}
				if (!won) {
					won = true;
					for (int k = 0; k < 5; k++) {
						if (boardState[i + k][j + k] != 1) won = false;
					}
				}
				if (won) {
					for (int k = 0; k < 5; k++) {
						board[i + k][j + k].setStyle("-fx-background-color: black;");
					}
					win = true;
				}
			}
		}

		//Diagonal going up right
		for (int i = 4; i < board.length; i++) {
			for (int j = 0; j < board[i - 4].length - 4; j++) {
				boolean won = true;
				for (int k = 0; k < 5; k++) {
					if (boardState[i - k][j + k] != 0) won = false;
				}
				if (!won) {
					won = true;
					for (int k = 0; k < 5; k++) {
						if (boardState[i - k][j + k] != 1) won = false;
					}
				}
				if (won) {
					for (int k = 0; k < 5; k++) {
						board[i - k][j + k].setStyle("-fx-background-color: black;");
					}
					win = true;
				}
			}
		}
		return win;
	}

	private boolean isBoardFull() {
		//Checks to see if the board is full, regardless of whether there is a 5 in a row.  In this case, chaos wins
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				if (boardState[i][i] == -1) return false;
			}
		}
		return true;
	}
}
