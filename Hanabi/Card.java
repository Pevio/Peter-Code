
import java.io.Serializable;
import javafx.scene.paint.Color;

/*
 * Stores all information for a card in Hanabi
 */

public class Card implements Serializable {
	private static final long serialVersionUID = 6589916395828313054L;

	public static final Color[] colors = new Color[] {Color.RED, Color.WHITE, Color.BLUE, Color.YELLOW, Color.LIMEGREEN, Color.MEDIUMSLATEBLUE};
	
	//The number and color ID of this particular
	private int color;
	private int number;
	
	Card(int color, int number) {
		this.color = color;
		this.number = number;
	}
	
	public int getNumber() {
		return number;
	}
	
	public Color getColor() {
		return numberToColor(color);
	}
	public int getColorNumber() {
		return color;
	}
	
	@Override
	public String toString() {
		return colorName(color) + " " + number;
	}
	
	public static String colorName(int color) {
		if (color == 0) {
			return "Red";
		} else if (color == 1) {
			return "White";
		} else if (color == 2) {
			return "Blue";
		} else if (color == 3) {
			return "Yellow";
		} else if (color == 4) {
			return "Green";
		} else if (color == 5) {
			return "Multi";
		}
		return "";
	}
	
	public static Color numberToColor(int number) {
		if (number >= 0 && number < 6) return colors[number];
		return null;
	}
	public static int colorToNumber(Color color) {
		for (int i = 0; i < colors.length; i++) {
			if (colors[i].equals(color)) return i;
		}
		return -1;
	}
}
