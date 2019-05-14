import java.io.Serializable;
import java.util.ArrayList;

/*
 * Stores all information for the start of a game.  This is calculated and created by the server player and sent to all the client players.
 * All information in this uneditable in this class, but is gathered by other classes that use it.
 */

public class HanabiGameSetup implements Serializable {
	private static final long serialVersionUID = -7735446463584658701L;
	
	//Attributes
	private ArrayList<Card> deck;
	private ArrayList<String> playerNames;
	private boolean multi;
	
	HanabiGameSetup() {
		//Default setup
		this(new ArrayList<Card>(), new ArrayList<String>(), false);
	}
	HanabiGameSetup(ArrayList<Card> deck, ArrayList<String> playerNames) {
		//Constructor that sets the multi setting to false
		this(deck, playerNames, false);
	}
	HanabiGameSetup(ArrayList<Card> deck, ArrayList<String> playerNames, boolean multi) {
		//Setup for each of the properties
		this.deck = deck;
		this.playerNames = playerNames;
		this.multi = multi;
	}
	
	//Getters
	public ArrayList<Card> getDeck() {
		return deck;
	}
	public ArrayList<String> getPlayers() {
		return playerNames;
	}
	public boolean getMulti() {
		return multi;
	}
}
