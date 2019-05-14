import java.util.*;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/*
 * The gameplay part of the Hanabi Application
 * This class is started from the Menu class, and contains all gameplay features.
 */

public class HanabiGame {
	private HanabiMenu menu;
	
	private Pane pane = new Pane();
	final int CARD_FONT_SIZE = 56;
	
	//GUI on the screen
	private Label[] handNames;
	private Label[][] handLabels;
	private Rectangle[][] handColorClue;
	private Label[][] handNumberClue;
	private Rectangle turnRec = new Rectangle();
	
	private Label[] inPlay;
	private Button[] actionButton = new Button[4];
	private HBox clueBox = new HBox(3);
	private RadioButton[] clueOption = new RadioButton[5];
	private Label actionHelp = new Label("");
	private Button cancel = new Button("Cancel");
	private Label lblDeckSize = new Label("");
	private Label lblClues = new Label("8 Clue(s)"); int clues = 8;
	private Label lblStrikes = new Label("0 Strike(s)"); int strikes = 0;
	private Label[] discard;
	private ScrollPane history = new ScrollPane();
	private VBox historyBox = new VBox(1);
	
	//Keeping track of the game
	private ArrayList<Card> deck = new ArrayList<Card>();					//The deck
	private int cardsPerHand;												//Either 4 or 5, dependant on how many players we have
	private int colors;														//5 with no multi; 6 with multi
	private int playerCount = 1; 											//Includes the current player
	private int playerID;													//Which of the playerNames I am
	private int whoUp = 0;													//The player whose turn it is
	private int lastPlayer = -1;											//The player who will play last at the end of the game
	private ArrayList<String> playerNames = new ArrayList<String>();		//The names of all the players, copied from the game setup
	private Card[][] hands;													//Array of card representation of the cards in everyone's hands
	private int[] cardsInPlay;												//The current piles in play
	private int actionButtonClicked = -1;
	private int selectedColorNumber = -1;
	
	HanabiGame(HanabiMenu menu, HanabiGameSetup gs, int playerID) {
		this.menu = menu;
		this.deck = gs.getDeck();
		this.colors = gs.getMulti() ? 6 : 5;
		this.playerID = playerID;
		this.playerNames = gs.getPlayers();
		this.playerCount = playerNames.size();
	}
	
	public void load() {
		//Sets up all the GUI on the screen
		
		//Sets up the pane that the game is played in
		menu.holderPane.getChildren().clear();
		menu.holderPane.getChildren().add(pane);
		
		cardsPerHand = playerCount < 4 ? 5 : 4;
		cardsInPlay = new int[colors];
		inPlay = new Label[colors];
		discard = new Label[colors];
		
		//Hand displays
		pane.getChildren().add(turnRec);
		hands = new Card[playerCount][cardsPerHand];
		handNames = new Label[playerCount];
		handLabels = new Label[playerCount][cardsPerHand];
		handColorClue = new Rectangle[playerCount][cardsPerHand];
		handNumberClue = new Label[playerCount][cardsPerHand];
		for (int i = 0; i < playerCount; i++) {
			handNames[i] = new Label(playerNames.get(i));
			handNames[i].setFont(new Font(20));
			handNames[i].setMinHeight(30); handNames[i].setMaxHeight(handNames[i].getMinHeight());
			handNames[i].relocate(490, 10 + (handNames[i].getMinHeight() + 120) * i);
			handNames[i].setTextFill(Color.BLACK);
			
			for (int j = 0; j < cardsPerHand; j++) {
				//The cards in the hands
				hands[i][j] = drawCard();
				handLabels[i][j] = new Label(i == playerID ? "" : " " + hands[i][j].getNumber());
				handLabels[i][j].setFont(new Font(CARD_FONT_SIZE));
				handLabels[i][j].setStyle("-fx-background-color: black;");
				handLabels[i][j].setTextFill(hands[i][j].getColor());
				handLabels[i][j].setMinWidth(65); handLabels[i][j].setMaxWidth(handLabels[i][j].getMinWidth());
				handLabels[i][j].setMinHeight(90); handLabels[i][j].setMaxHeight(handLabels[i][j].getMinHeight());
				handLabels[i][j].relocate(handNames[i].getLayoutX() + (10 + handLabels[i][j].getMinWidth()) * j,
						40 + (30 + handLabels[i][j].getMinHeight() + handNames[i].getMinHeight()) * i);
				
				final int player = i; final int spot = j;
				handLabels[i][j].setOnMouseClicked(e -> clickHand(player, spot));
				
				//Clue information
				handColorClue[i][j] = new Rectangle(handLabels[i][j].getLayoutX(), handLabels[i][j].getLayoutY(),
						handLabels[i][j].getMaxWidth(), 10);
				handColorClue[i][j].setFill(Color.RED);
				handNumberClue[i][j] = new Label("1");
				handNumberClue[i][j].relocate(handLabels[i][j].getLayoutX() + 3, handLabels[i][j].getLayoutY() + handLabels[i][j].getMaxHeight() - 23);
				handNumberClue[i][j].setTextFill(Color.WHITE);
				handNumberClue[i][j].setFont(new Font(16));
				handColorClue[i][j].setVisible(false);
				handNumberClue[i][j].setVisible(false);
				
				pane.getChildren().addAll(handLabels[i][j], handColorClue[i][j], handNumberClue[i][j]);
			}
			
			pane.getChildren().addAll(handNames[i]);
		}
		turnRec.setWidth(handLabels[0][cardsPerHand-1].getLayoutX() + handLabels[0][cardsPerHand-1].getMinWidth() -
				handLabels[0][0].getLayoutX() + 15);
		turnRec.setHeight(130);
		turnRec.relocate(handNames[0].getLayoutX() - 10, handNames[0].getLayoutY());
		turnRec.setFill(Color.TRANSPARENT);
		turnRec.setStrokeWidth(3); turnRec.setStroke(Color.BLACK);
		
		//Action buttons
		String[] actionButtonTitles = new String[] {"Play", "Discard", "Color Clue", "Number Clue"};
		for (int i = 0; i < actionButton.length; i++) {
			actionButton[i] = new Button(actionButtonTitles[i]);
			actionButton[i].setMaxWidth(120); actionButton[i].setMinWidth(actionButton[i].getMaxWidth());
			pane.getChildren().add(actionButton[i]);
			final int index = i;
			actionButton[index].setOnAction(e -> clickActionButton(index));
			actionButton[i].setVisible(playerID == 0);
		}
		actionButton[1].setVisible(false);
		actionButton[0].relocate(10, 10); actionButton[1].relocate(140, 10);
		actionButton[2].relocate(10, 50); actionButton[3].relocate(140, 50);
		actionHelp.relocate(10, 10);
		actionHelp.setVisible(false);
		cancel.setVisible(false);
		cancel.setMinWidth(actionButton[0].getMinWidth()); cancel.setMaxWidth(cancel.getMinWidth());
		cancel.relocate(10, 50);
		cancel.setOnAction(e -> clickCancel());
		for (int i = 0; i < 5; i++) {
			clueOption[i] = new RadioButton(i + "");
			clueOption[i].setMinWidth(50);
			clueOption[i].setVisible(false);
			final int index = i;
			clueOption[i].setOnAction(e -> clickClueOption(index));
			clueBox.getChildren().add(clueOption[i]);
		}
		clueBox.relocate(10, 10);
		pane.getChildren().addAll(cancel, actionHelp, clueBox);
		
		//Deck, Clue and Strike information
		lblDeckSize.setText(deck.size() + " card(s) in deck");
		lblDeckSize.setFont(new Font(16));
		lblDeckSize.relocate(10, 90);
		lblClues.setFont(new Font(16));
		lblClues.relocate(10, 110);
		lblStrikes.setFont(new Font(16));
		lblStrikes.relocate(10, 130);
		pane.getChildren().addAll(lblDeckSize, lblClues, lblStrikes);
		
		for (int i = 0; i < colors; i++) {
			//In Play
			inPlay[i] = new Label(" 0");
			inPlay[i].setTextFill(Card.colors[i]);
			inPlay[i].setFont(new Font(CARD_FONT_SIZE));
			inPlay[i].setMinWidth(65); inPlay[i].setMaxWidth(inPlay[i].getMinWidth());
			inPlay[i].setMinHeight(90); inPlay[i].setMaxHeight(inPlay[i].getMinHeight());
			inPlay[i].setStyle("-fx-background-color: black;");
			inPlay[i].relocate(10 + (10 + inPlay[i].getMinWidth()) * i, 200);
			
			//Discard
			discard[i] = new Label("");
			discard[i].setTextFill(Card.colors[i]);
			discard[i].setFont(new Font(22));
			discard[i].setMinWidth(170); inPlay[i].setMaxWidth(discard[i].getMinWidth());
			discard[i].setMinHeight(20); inPlay[i].setMaxHeight(discard[i].getMinHeight());
			discard[i].setStyle("-fx-background-color: black;");
			discard[i].relocate(10, 320 + ((20 + discard[i].getMinHeight()) * i));
			
			pane.getChildren().addAll(inPlay[i], discard[i]);
		}
		
		//History
		history.setContent(historyBox);
		history.relocate(discard[0].getLayoutX() + discard[0].getMinWidth() + 10, 300);
		history.setMinWidth(260); history.setMaxWidth(history.getMinWidth());
		history.setMinHeight(350); history.setMaxHeight(history.getMinHeight());
		pane.getChildren().add(history);
		
		pane.setStyle("-fx-background-color: darkgrey;");
	}
	
	private void clickActionButton(int index) {
		for (int i = 0; i < 4; i++) {
			actionButton[i].setVisible(false);
		}
		cancel.setVisible(true);
		actionButtonClicked = index;
		actionHelp.setVisible(true);
		switch (index) {
		case 0:
			actionHelp.setText("Choose a card from your hand to play.");
			break;
		case 1:
			actionHelp.setText("Choose a card from your hand to discard.");
			break;
		case 2:
			actionHelp.setText("");
			break;
		case 3:
			actionHelp.setText("");
			break;
		}
		if (index > 1) {
			//Clue
			selectedColorNumber = -1;
			clueBox.setVisible(true);
			for (int i = 0; i < 5; i++) {
				clueOption[i].setVisible(true);
				clueOption[i].setSelected(false);
			}
			if (index == 2) {
				for (int i = 0; i < 5; i++) {
					clueOption[i].setText(Card.colorName(i));
				}
			} else {
				for (int i = 0; i < 5; i++) {
					clueOption[i].setText((i + 1) + "");
				}
			}
		}
	}
	private void clickClueOption(int index) {
		selectedColorNumber = index + (actionButtonClicked == 3 ? 1 : 0);
		for (int i = 0; i < 5; i++) {
			clueOption[i].setSelected(i == index);
		}
	}
	private void clickCancel() {
		for (int i = 0; i < 4; i++) {
			actionButton[i].setVisible(true);
		}
		cancel.setVisible(false);
		actionButtonClicked = -1;
		actionHelp.setVisible(false);
		for (int i = 0; i < 5; i++) {
			clueOption[i].setVisible(false);
		}
	}
	
	private void clickHand(int player, int spot) {
		if (menu.gameGoing && whoUp == playerID && actionButtonClicked > -1) {
			Turn turn = null;
			switch (actionButtonClicked) {
			case 0:
				//Play a card
				if (player != whoUp) return;
				turn = new Turn(whoUp, true, spot);
				
				break;
			case 1:
				//Discard a card
				if (player != whoUp) return;
				turn = new Turn(whoUp, false, spot);
				break;
			case 2:
				//Color Clue
				if (player == whoUp || selectedColorNumber == -1) return;
				turn = new Turn(whoUp, player, true, selectedColorNumber);
				
				break;
			case 3:
				//Number Clue
				if (player == whoUp || selectedColorNumber == -1) return;
				turn = new Turn(whoUp, player, false, selectedColorNumber);
				
				//break;
			}
			sendTurn(turn);
			cancel.setVisible(false);
			actionHelp.setVisible(false);
			
		}
	}
	
	private void sendTurn(Turn turn) {
		menu.sendTurn(turn);
	}
	public void getTurn(Turn turn) {
		Platform.runLater(new Runnable() {
			public void run() {
				String historyEntry = "";
				if (turn.getToPlayer() == -1) {
					//Play or Discard
					if (turn.getInfo()) {
						//Attempt to Play
						if (cardsInPlay[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()] + 1
								== hands[turn.getPlayer()][turn.getTypeSpot()].getNumber()) {
							//Successful play
							inPlay[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].setText
									(" " + ++cardsInPlay[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()]);
							if (cardsInPlay[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()] == 5 && clues < 8) clues++;
							historyEntry = playerNames.get(whoUp) + " plays " + hands[turn.getPlayer()][turn.getTypeSpot()].toString();
						} else {
							//Strike
							strikes++;
							lblStrikes.setText(strikes + " Strike(s)");
							//Update the discard list
							discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].setText
								(discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].getText()
									+ (discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].getText().length() > 0 ? ", " : "")
									+ hands[turn.getPlayer()][turn.getTypeSpot()].getNumber());
							historyEntry = playerNames.get(whoUp) + " fails to play " + hands[turn.getPlayer()][turn.getTypeSpot()].toString();
							//Bomb out
							if (strikes == 3) {
								endGame();
								return;
							}
						}
					} else {
						//Discard
						discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].setText
							(discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].getText()
							+ (discard[hands[turn.getPlayer()][turn.getTypeSpot()].getColorNumber()].getText().length() > 0 ? ", " : "")
							+ hands[turn.getPlayer()][turn.getTypeSpot()].getNumber());
						clues++;
						historyEntry = playerNames.get(whoUp) + " discards " + hands[turn.getPlayer()][turn.getTypeSpot()].toString();
					}
					
					//Move cards in hand with clue information
					for (int i = turn.getTypeSpot(); i > 0; i--) {
						hands[turn.getPlayer()][i] = hands[turn.getPlayer()][i - 1];
						handLabels[turn.getPlayer()][i].setText(" " + hands[turn.getPlayer()][i - 1].getNumber());
						handLabels[turn.getPlayer()][i].setTextFill(hands[turn.getPlayer()][i - 1].getColor());
						handColorClue[turn.getPlayer()][i].setVisible(handColorClue[turn.getPlayer()][i - 1].isVisible());
						handColorClue[turn.getPlayer()][i].setFill(handColorClue[turn.getPlayer()][i - 1].getFill());
						handNumberClue[turn.getPlayer()][i].setVisible(handNumberClue[turn.getPlayer()][i - 1].isVisible());
						handNumberClue[turn.getPlayer()][i].setText(handNumberClue[turn.getPlayer()][i - 1].getText());
					}
					
					//Draw new card on the left
					hands[turn.getPlayer()][0] = drawCard();
					if (hands[turn.getPlayer()][0] != null) {
						handLabels[turn.getPlayer()][0].setText(" " + hands[turn.getPlayer()][0].getNumber());
						handLabels[turn.getPlayer()][0].setTextFill(hands[turn.getPlayer()][0].getColor());
						
						lblDeckSize.setText(deck.size() + " Card(s) in Deck");
					} else {
						//No more cards to draw
						handLabels[turn.getPlayer()][0].setVisible(false);
					}
					handColorClue[turn.getPlayer()][0].setVisible(false);
					handNumberClue[turn.getPlayer()][0].setVisible(false);
					
					if (turn.getPlayer() == playerID) {
						for (int i = 0; i < cardsPerHand; i++) {
							handLabels[turn.getPlayer()][i].setText("");
						}
					}
				} else {
					//Clue
					clues--;
					if (turn.getInfo()) {
						//Color clue
						for (int i = 0; i < cardsPerHand; i++) {
							if (hands[turn.getToPlayer()][i].getColorNumber() == turn.getTypeSpot()) {
								//Not multi
								handColorClue[turn.getToPlayer()][i].setVisible(true);
								handColorClue[turn.getToPlayer()][i].setFill(Card.colors[turn.getTypeSpot()]);
							}
							if (hands[turn.getToPlayer()][i].getColorNumber() == 5) {
								//Multi
								if (!handColorClue[turn.getToPlayer()][i].isVisible()) {
									handColorClue[turn.getToPlayer()][i].setVisible(true);
									handColorClue[turn.getToPlayer()][i].setFill(Card.colors[turn.getTypeSpot()]);
								} else {
									if (!(handColorClue[turn.getToPlayer()][i].getFill() == Card.colors[turn.getTypeSpot()])) {
										handColorClue[turn.getToPlayer()][i].setFill(Card.colors[5]);
									}
								}
							}
						}
						historyEntry = playerNames.get(whoUp) + " gives clue " + Card.colorName(turn.getTypeSpot()) +
								" to " + playerNames.get(turn.getToPlayer());
					} else {
						//Number clue
						for (int i = 0; i < cardsPerHand; i++) {
							if (hands[turn.getToPlayer()][i].getNumber() == turn.getTypeSpot()) {
								handNumberClue[turn.getToPlayer()][i].setVisible(true);
								handNumberClue[turn.getToPlayer()][i].setText(hands[turn.getToPlayer()][i].getNumber() + "");
							}
						}
						historyEntry = playerNames.get(whoUp) + " gives clue " + turn.getTypeSpot() +
								" to " + playerNames.get(turn.getToPlayer());
					}
				}
				
				//History entry
				Label label = new Label(historyEntry);
				label.setTextFill(Color.BLACK);
				historyBox.getChildren().add(0, label);
				
				if (lastPlayer != whoUp) {
					//Set up next turn
					whoUp = (whoUp + 1) % playerNames.size();
					turnRec.setLayoutY(handNames[whoUp].getLayoutY());
					
					lblClues.setText(clues + " Clue(s)");
					clueBox.setVisible(false);
					cancel.setVisible(false);
					for (int i = 0; i < 4; i++) {
						actionButton[i].setVisible(whoUp == playerID);
					}
					if (clues == 0) {
						actionButton[2].setVisible(false);
						actionButton[3].setVisible(false);
					}
					if (clues == 8) actionButton[1].setVisible(false);
					
					actionButtonClicked = -1;
					selectedColorNumber = -1;
					
					if (deck.size() == 0 && lastPlayer == -1) {
						//Start end of game
						lastPlayer = (whoUp - 1 + playerNames.size()) % playerNames.size();
						lblDeckSize.setText(playerNames.get(lastPlayer) + " will have the last turn.");
						historyEntry += "\n" + playerNames.get(lastPlayer) + " will have the last turn.";
						handNames[lastPlayer].setFont(Font.font(null, FontWeight.BOLD, 20));
					}
				} else {
					//Game is over
					endGame();
				}
			}
		});
	}
	private Card drawCard() {
		//Returns the first card in the deck and removes it.  Works similarly to a pull method on a queue
		if (deck.size() == 0) return null;
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}
	private void endGame() {
		//Finishes the game and makes sure no more moves can be made
		whoUp = -1;
		menu.gameGoing = false;
		for (int i = 0; i < 4; i++) {
			actionButton[i].setVisible(false);
		}
		for (int i = 0; i < cardsPerHand; i++) {
			if (hands[playerID][i] != null) handLabels[playerID][i].setText(" " + hands[playerID][i].getNumber());
		}
		turnRec.setVisible(false);
		
		int points = 0;
		if (strikes != 3) {
			//Calculate score
			for (int i = 0; i < colors; i++) {
				points += cardsInPlay[i];
			}
		}
		Label label = new Label("You scored " + points + " points!");
		label.setTextFill(Color.BLACK);
		historyBox.getChildren().add(0, label);
	}
}
