import java.util.*;
import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.*;

/*
 * Networked, multithreaded, GUI Hanabi
 * The Menu class sets up a game, connects to other games, and provides functionality to start a game, 
 * at which point it is handed off to the HanabiGame class
 */

public class HanabiMenu extends Application {
	//Essential GUI panes etc
	private HanabiGame game;
	private Stage stage = new Stage();
	private Scene scene;
	public Pane holderPane = new Pane();
	private Pane pane = new Pane();
	
	//Controls on the Main Menu
	private Button host = new Button("Host Game");
	private Button connect = new Button("Connect to Game");
	private TextArea enterUserName = new TextArea("My Name");
	private TextArea enterIP = new TextArea("127.0.0.1");
	private Button start = new Button("Start");
	private Label playerNameDisplay = new Label("");
	private CheckBox multi = new CheckBox("Multicolor");
	
	//Client and Server and thread information
	private boolean isClient;
	boolean gameGoing = false;
	private ArrayList<ObjectOutputStream> output = new ArrayList<>();
	private ArrayList<String> playerNames = new ArrayList<String>();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage fakeStage) throws Exception {
		//Sets up the menu for connecting
		scene = new Scene(holderPane, 400, 300);
		holderPane.getChildren().add(pane);
		holderPane.setStyle("-fx-background-color: darkgrey;");
		pane.setStyle("-fx-background-color: darkgrey;");
		
		enterIP.setMinHeight(30); enterIP.setMaxHeight(30);
		enterIP.relocate(10, 10);
		enterIP.setMinWidth(300); enterIP.setMaxWidth(enterIP.getMinWidth());
		
		enterUserName.setMinHeight(30); enterUserName.setMaxHeight(30);
		enterUserName.relocate(10, 60);
		enterUserName.setMinWidth(300); enterUserName.setMaxWidth(enterUserName.getMinWidth());
		
		connect.setMinWidth(140); connect.setMaxWidth(140);
		connect.relocate(10, 110);
		
		host.setMinWidth(140); host.setMaxWidth(140);
		host.relocate(170, 110);
		
		start.relocate(10, 150);
		start.setMinWidth(140); start.setMaxWidth(140);
		start.setVisible(false);
		
		playerNameDisplay.setFont(new Font(14));
		playerNameDisplay.relocate(10, 10);
		playerNameDisplay.setVisible(false);
		
		multi.relocate(start.getLayoutX(), start.getLayoutY() + 35);
		multi.setVisible(false);
		
		pane.getChildren().addAll(enterIP, enterUserName, connect, host, start, playerNameDisplay, multi);
		connect.setOnAction(e -> connectToServer(enterIP));
		host.setOnAction(e -> makeServer());
		
		stage.setScene(scene);
		
		stage.show();
	}
	
	@SuppressWarnings("resource")
	private void connectToServer(TextArea ip) {
		isClient = true;
		try {
			Socket socket = new Socket(ip.getText(), 8000);
			output.add(new ObjectOutputStream(socket.getOutputStream()));
			output.get(0).writeObject(enterUserName.getText());
			
			//Receiving messages from the server
			new Thread( () -> {
				try {
					ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
					
					HanabiGameSetup gs = (HanabiGameSetup)fromServer.readObject();
					startGame(gs, this);
					
					while (true) {
						Turn turn = (Turn)fromServer.readObject();
						game.getTurn(turn);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}).start();
			
			playerNameDisplay.setText("Waiting for game to start...");
			proceedToMenu();
		} catch (Exception e) {
			//IP address not availible
			ip.setText("Bad IP");
		}
	
	}
	private void makeServer() {
		isClient = false;
		addName(enterUserName.getText());
		proceedToMenu();
		
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8000);
			
			//Handle connecting to other clients
			new Thread( () ->  {
				while (!gameGoing && playerNames.size() < 5) {
					try {
						Socket socket = serverSocket.accept();
						ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
						
						String name = (String)(input.readObject());
						addName(name);
						
						output.add(new ObjectOutputStream(socket.getOutputStream()));
						
						//Handle recieving messages from clients and relaying them on to anyone else
						new Thread( () -> {
							try {
								while (true) {
									Turn turn = (Turn)(input.readObject());
									sendTurn(turn);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}).start();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private synchronized void addName(String name) {
		//If we are the server, add the new name to the arraylist and display label
		Platform.runLater(new Runnable() {
			public void run() {
				playerNames.add(name);
				playerNameDisplay.setText(playerNameDisplay.getText() + name + "\n");
				start.setDisable(false);
			}
		});
	}
	private void proceedToMenu() {
		//Called to proceed to the screen for waiting for the game to start
		host.setVisible(false);
		connect.setVisible(false);
		enterIP.setVisible(false);
		enterUserName.setVisible(false);
		playerNameDisplay.setVisible(true);
		
		if (!isClient) {
			start.setOnAction(e -> startGame());
			start.setVisible(true);
			start.setDisable(true);
			multi.setVisible(true);
		} else {
			start.setVisible(false);
		}
	}
	
	private void startGame() {
		if (isClient) return;
		
		//This method is called if we are the server and the deck needs to be shuffled
		ArrayList<Card> deck = new ArrayList<Card>();
		
		for (int i = 0; i < (multi.isSelected() ? 6 : 5); i++) {
			deck.add(new Card(i, 1));
			deck.add(new Card(i, 1));
			deck.add(new Card(i, 1));
			deck.add(new Card(i, 2));
			deck.add(new Card(i, 2));
			deck.add(new Card(i, 3));
			deck.add(new Card(i, 3));
			deck.add(new Card(i, 4));
			deck.add(new Card(i, 4));
			deck.add(new Card(i, 5));
		}
		
		Collections.shuffle(deck);
		Collections.shuffle(playerNames);
		HanabiGameSetup gs = new HanabiGameSetup(deck, playerNames, multi.isSelected());
		
		for (int i = 0; i < output.size(); i++) {
			try {
				output.get(i).writeObject(gs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		startGame(gs, this);
	}
	private void startGame(HanabiGameSetup gs, HanabiMenu menu) {
		//Starts the game with the given setup: opens the game form and sends it all the information
		Platform.runLater(new Runnable() {
			public void run() {
				game = new HanabiGame(menu, gs, gs.getPlayers().indexOf(enterUserName.getText()));
				game.load();
				stage.setTitle("Hanabi - " + enterUserName.getText());
				stage.setWidth(1000);
				stage.setHeight(800);
				gameGoing = true;
			}
		});
	}
	
	public void sendTurn(Turn turn) {
		//Sends the turn to all output connections.  If client, sends to just the server; if server, sends to all clients
		for (int i = 0; i < output.size(); i++) {
			try {
				output.get(i).writeObject(turn);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (!isClient) game.getTurn(turn);
	}
	public void stop() {
		//Exits everything in the game
		System.exit(0);
	}
}

