
/* A simulation of the Candy Corn game, a simple dice rolling game.  It does 10000 simulations and prints statistics regarding
 * what happened
 */

public class CandyCorn {
	public static void main(String[] args) {
		final int PLAYERS = 7;
		final int GAME_COUNT = 10000;
		int[] scores = new int[PLAYERS];
		int losingScores = 0;
		int winningScores = 0;
		int totalScores = 0;
		int bellies = 0;
		int rolls = 0;
		
		int highestHigh = 0, lowestHigh = 0; int winningRollCount = 0;
		int totalZeroes = 0;
		int biggestBelly = 0;
		for (int i = 0; i < GAME_COUNT; i++) {
			CandyCorn cc = new CandyCorn(PLAYERS);
			cc.playGame();
			
			int low = cc.corn[0]; int high = cc.corn[0];
			for (int j = 0; j < PLAYERS; j++) {
				if (cc.corn[j] < low) low = cc.corn[j];
				if (cc.corn[j] > high) high = cc.corn[j];
				if (cc.corn[j] > highestHigh) {
					highestHigh = cc.corn[j];
					winningRollCount = cc.rollCount[j];
				}
				totalScores += cc.corn[j];
				scores[j] += cc.corn[j];
				bellies += cc.belly[j];
				rolls += cc.rollCount[j];
				if (cc.belly[j] > biggestBelly) biggestBelly = cc.belly[j];
			}
			losingScores += low;
			if (low == 0) totalZeroes++;
			winningScores += high;
			if (high > highestHigh) highestHigh = high;
			if (i == 0) lowestHigh = high; else if (lowestHigh > high) lowestHigh = high;
			
			//if (high > 70) System.out.println(cc.toString());
		}
		
		System.out.println("In " + GAME_COUNT + " Games:");
		System.out.println("Average Score: " + totalScores * 1.0 / (GAME_COUNT * PLAYERS));
		System.out.println("Average Winning Score: " + winningScores * 1.0/ GAME_COUNT);
		System.out.println("Average Losing Score: " + losingScores * 1.0 / GAME_COUNT);
		System.out.println("Average Corn Eaten: " + bellies * 1.0 / (GAME_COUNT * PLAYERS));
		System.out.println("Average Roll Count: " + rolls * 1.0 / (GAME_COUNT * PLAYERS));
		System.out.println("Most Corn Eaten: " + biggestBelly);
		System.out.println("All-time high: " + highestHigh + " with a roll count of " + winningRollCount);
		System.out.println("Lowest winning score: " + lowestHigh);
		System.out.println("Probability of a zero: " + totalZeroes * 1.0 / GAME_COUNT);
		for (int i = 0; i < PLAYERS; i++) {
			System.out.print((scores[i]) * 1.0 / GAME_COUNT + " ");
		}
	}
	
	CandyCorn(int players) {
		//set up the game
		this.PLAYERS = players;
		corn = new int[players];
		belly = new int [players];
		position = new int[players];
		rollCount = new int[PLAYERS];
		isSkipped = new boolean[players];
	}
	int PLAYERS;
	
	int[] corn = new int[PLAYERS];
	int[] belly = new int[PLAYERS];
	int[] position = new int[PLAYERS];
	int[] rollCount = new int[PLAYERS];
	boolean[] isSkipped = new boolean[PLAYERS];
	
	//0=take, 1=eat, 2=giveLeft, 3=giveRight, 4=giveAll, 5=takeLeft, 6=takeRight, 7=takeAll, 8=gobbleForward, 9=gobbleBack, 10=skip
	static final int[][] board = new int[][]{{9, 1}, {0, 6}, {8, 3}, {0, 3}, {9, 1}, {0, 3}, {10, 1}, {8, 2}, {7, 1}, {1, 2}, {3, 2}, {0, 4}, {0, 3},
	{9, 2}, {1, 1}, {6, 2}, {8, 3}, {0, 3}, {9, 1}, {1, 1}, {0, 6}, {10, 1}, {4, 1}, {0, 3}, {8, 3}, {0, 5}, {1, 2}, {5, 2}, {9, 2}, {10, 1}, {2, 1},
	{0, 6}, {1, 2}, {0, 4}, {9, 2}, {0, 5}, {8, 2}, {7, 1}, {1, 2}, {0, 6}, {0, 3}};
	
	public void playGame() {
		//Initialize the game
		for (int i = 0; i < PLAYERS; i++) {
			position[i] = -1;
			isSkipped[i] = false;
		}
		boolean isDone = false;
		while(!isDone) {
			boolean moved = false;
			//Move each player
			for (int i = 0; i < PLAYERS; i++) {
				if (position[i] < board.length && !isSkipped[i]) {
					move(i, (int)(Math.random() * 6 + 1));
					moved = true;
					rollCount[i]++;
				}
				isSkipped[i] = false;
			}
			//System.out.println(corn[0] + " " + corn[1] + " " + corn[2] + " " + corn[3] + " " + corn[4]);
			//System.out.println(position[0] + " " + position[1] + " " + position[2] + " " + position[3] + " " + position[4]);
			if (!moved) {
				//The game is finished
				isDone = true;
			}
		}
	}
	
	public void move(int player, int spaces) {
		//Move that many spaces.  If we are on the board, perform the action
		position[player] += spaces;
		if (position[player] < 0 || position[player] >= board.length) return;
		
		switch(board[position[player]][0]) {
		case 0:
			//Take from bowl
			take(player, board[position[player]][1]);
			break;
		case 1:
			//Eat
			eat(player, board[position[player]][1]);
			break;
		case 2:
			//Give to player on left
			for (int i = 0; i < board[position[player]][1]; i++) {
				give(player, (player + PLAYERS - 1) % PLAYERS);
			}
			break;
		case 3:
			//Give to player on right
			for (int i = 0; i < board[position[player]][1]; i++) {
				give(player, (player + 1) % PLAYERS);
			}
			break;
		case 4:
			//Give to all
			for (int i = 0; i < board[position[player]][1]; i++) {
				for (int j = 1; j < PLAYERS; j++) {
					give(player, (player + j) % PLAYERS);
				}
			}
			break;
		case 5:
			//Take from player on left
			for (int i = 0; i < board[position[player]][1]; i++) {
				give((player + PLAYERS - 1) % PLAYERS, player);
			}
			break;
		case 6:
			//Take from player on right
			for (int i = 0; i < board[position[player]][1]; i++) {
				give((player + 1) % PLAYERS, player);
			}
			break;
		case 7:
			//Take from all
			for (int i = 0; i < board[position[player]][1]; i++) {
				for (int j = 1; j < PLAYERS; j++) {
					give((player + j) % PLAYERS, player);
				}
			}
			break;
		case 8:
			//Gobble forward
			move(player, board[position[player]][1]);
			break;
		case 9:
			//Gobble back
			move(player, -board[position[player]][1]);
			break;
		case 10:
			//Skip
			isSkipped[player] = true;
		}
	}
	
	public void take(int player, int pieces) {
		//Take from the middle
		corn[player] += pieces;
	}
	public void eat(int player, int pieces) {
		//Eat, if there is corn to eat
		if (corn[player] < pieces) {
			belly[player] += corn[player];
			corn[player] = 0;
		} else {
			belly[player] += pieces;
			corn[player] -= pieces;
		}
	}
	
	public void give(int from, int to) {
		//Used for give and take.  Gives one corn from the from player to the to player
		if (corn[from] > 0) {
			corn[to]++;
			corn[from]--;
		}
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < PLAYERS; i++) {
			result += i + ": " + corn[i] + " corn, " + belly[i] + " eaten\n";
		}
		return result;
	}
}