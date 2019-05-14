import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.text.Font;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.control.*;

import java.io.*;
import java.util.Scanner;

/* Provides functionality to play Spyfall on a computer.  Uses the locations.dat file to input all locations, and if Spyfall Settings.dat
 * exists, uses it to store the names of the people playing.
 */

public class Spyfall extends Application {
	private boolean isStarted;
	private int numLocations;
	private String[] locationNames = new String[100];
	private String[][] roles = new String [100][8];
	private int numPlayers;
	private String playerRoles[] = new String[8];
	private int currentLocation;

	private TextField[] names = new TextField[8];
	private CheckBox chkRoles = new CheckBox("Roles");

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) {
		isStarted = false;
		Pane pane = new Pane();
		
		//VBox for setting up a new game
		VBox newGame = new VBox(5);

		for (int i = 0; i < 8; i++) {
			names[i] = new TextField("");
			names[i].setMinWidth(150);
			newGame.getChildren().add(names[i]);
		}
		Button startGame = new Button("Start Game");
		Button seeLocations = new Button("Locations");
		newGame.getChildren().addAll(chkRoles, startGame, seeLocations);
		seeLocations.setOnAction(e -> locationsClick());
		pane.getChildren().add(newGame);
		newGame.relocate(20, 20);
		
		//VBox for things in a current game
		VBox currentGame = new VBox(5);
		Button[] seeLocation = new Button[8];
		for (int i = 0; i < 8; i++) {
			seeLocation[i] = new Button();
			seeLocation[i].setVisible(false);
			seeLocation[i].setMinWidth(150);
			final int fat = i;
			seeLocation[i].setOnAction(e -> seeLocation(fat, seeLocation[fat]));
			currentGame.getChildren().add(seeLocation[i]);
		}
		Button finishGame = new Button("Finish Game");
		finishGame.setVisible(false);
		currentGame.getChildren().add(new Label(""));
		currentGame.getChildren().add(finishGame);
		pane.getChildren().add(currentGame);
		currentGame.relocate(200, 20);

		startGame.setOnAction(e -> startGame(seeLocation, finishGame));
		finishGame.setOnAction(e -> finishGame(seeLocation, finishGame));
		
		//Load locations, and if we can't, exit
		try {
			loadLocations();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (numLocations == 0) {
			System.out.println("Error loading locations.");
			System.exit(0);
		}
		
		//Finish up
		Scene scene = new Scene(pane, 370, 390);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Spyfall");
		primaryStage.show();
	}

	public void startGame(Button[] seeLocation, Button finishGame) {
		String[] players = new String[8];
		numPlayers = 0;
		//Set up the buttons
		for (int i = 0; i < 8; i++) {
			if (names[i].getText().length() > 0) {
				players[numPlayers] = names[i].getText();
				seeLocation[numPlayers].setText(players[numPlayers]);
				seeLocation[numPlayers].setVisible(true);
				seeLocation[numPlayers].setDisable(false);
				numPlayers++;
			}
		}
		for (int i = numPlayers; i < 8; i++) {
			seeLocation[i].setVisible(false);
		}

		//Figure out the location and roles
		String[] rolesUsed = new String[numPlayers];
		currentLocation = (int)(Math.random() * numLocations);

		for (int i = 0; i < numPlayers; i++) {
			if (chkRoles.isSelected() == true || i == 0) {
				rolesUsed[i] = roles[currentLocation][i];
			} else {
				rolesUsed[i] = roles[currentLocation][1];
			}
		}
		for (int i = 0; i < numPlayers; i++) {
			int a = 0;
			while (a == 0) {
				int random = (int)(Math.random() * numPlayers);
				if (rolesUsed[random].length() > 0) {
					playerRoles[i] = rolesUsed[random];
					rolesUsed[random] = "";
					a = 1;
				}
			}
		}

		finishGame.setText("Finish Game");
		finishGame.setVisible(true);
		isStarted = true;
	}

	public void locationsClick() {
		//New stage with list of locations
		
	}
	
	public void seeLocation(int index, Button name) {
		if (isStarted) {
			//New stage with the player's name, location, and role
			Pane pane = new Pane();
			VBox box = new VBox(5);

			Label lblName = new Label(name.getText());
			lblName.setFont(new Font(16));

			Label location = new Label();
			if (playerRoles[index].equals("Spy")) {
				location.setText("???");
			} else {
				location.setText(locationNames[currentLocation]);
			}
			location.setFont(new Font(16));

			Label role = new Label(playerRoles[index]);
			role.setFont(new Font(16));

			box.getChildren().add(lblName);
			box.getChildren().add(location);
			box.getChildren().add(role);
			pane.getChildren().add(box);
			box.relocate(20, 10);

			Scene scene = new Scene(pane, 180, 110);
			Stage newStage = new Stage();
			newStage.setScene(scene);
			newStage.setTitle("");
			newStage.show();
			name.setDisable(true);
		}
	}

	public void finishGame(Button[] seeLocation, Button finishGame) {
		if (isStarted) {
			//Reveal all information and enable a new game to start
			for (int i = 0; i < numPlayers; i++) {
				seeLocation[i].setText(seeLocation[i].getText() + ": " + playerRoles[i]);
				seeLocation[i].setDisable(false);
			}
			isStarted = false;
			finishGame.setText("Location: " + locationNames[currentLocation]);
		}
	}

	public void loadLocations() throws Exception {
		//Loads the locations from the saved file
		File file = new File("Locations.dat");
		Scanner input = new Scanner(file);

		numLocations = 0;
		while (input.hasNext()) {
			locationNames[numLocations] = input.nextLine();
			roles[numLocations][0] = "Spy";
			for (int i = 1; i < 8; i++) {
				roles[numLocations][i] = input.nextLine();
			}
			numLocations++;
		}

		File file2 = new File("Spyfall Settings.dat");
		Scanner input2 = new Scanner(file2);
		try {
			for (int i = 0; i < 8; i++) {
				names[i].setText(input2.nextLine());
			}
			String a = input2.nextLine();
			if (a.equals("true")) {
				chkRoles.setSelected(true);
			} else {
				chkRoles.setSelected(false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		input.close();
		input2.close();
	}

	@Override
	public void stop() throws Exception {
		//When we close, save all settings
		File file = new File("Spyfall Settings.dat");
		PrintWriter writer = new PrintWriter(file);

		try {
			for (int i = 0; i < 8; i++) {
				writer.println(names[i].getText());
			}
			writer.println(chkRoles.isSelected());
		} catch (Exception e) {
			e.printStackTrace();
		}

		writer.close();
	}
}
