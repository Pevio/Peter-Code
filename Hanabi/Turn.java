import java.io.Serializable;

/*
 * This is the object that gets send between the client and server.  It stores all information regarding one person's turn in a game.
 */

public class Turn implements Serializable {
	private static final long serialVersionUID = 4411363647750352039L;

	//The player whose turn it was
	private int player;
	
	//The player we gave a clue to.  If we played a card, this is -1
	private int toPlayer;
	
	//For play/discard: this is true if we attempted to play.  For clues: this is true if this is a color clue
	private boolean info;
	
	//For play/discard: the position in hand we played/discarded from.  For clues: the number or color number we clued
	private int typeSpot;
	
	Turn(int player, boolean play, int spot) {
		//Constructor for playing or discarding a card
		this.player = player;
		this.toPlayer = -1;
		this.info = play;
		this.typeSpot = spot;
	}
	Turn(int player, int toPlayer, boolean color, int type) {
		//Constructor for giving a clue
		this.player = player;
		this.toPlayer = toPlayer;
		this.info = color;
		this.typeSpot = type;
	}
	
	public int getPlayer() {
		return player;
	}
	public boolean isClue() {
		return toPlayer != -1;
	}
	public int getToPlayer() {
		return toPlayer;
	}
	public boolean getInfo() {
		return info;
	}
	public int getTypeSpot() {
		return typeSpot;
	}
}
