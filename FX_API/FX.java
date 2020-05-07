package fx;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class FX {
	public final static double DEFAULT_FONT = 14;
	public final static Color DEFAULT_COLOR = Color.BLACK;
	
	public static void newLabeled (Labeled c) {
		newLabeled(c, "");
	}
	public static void newLabeled(Labeled c, String text) {
		newLabeled(c, text, DEFAULT_COLOR, DEFAULT_FONT);
	}
	public static void newLabeled (Labeled c, String text, Color color) {
		newLabeled(c, text, color, DEFAULT_FONT);
	}
	public static void newLabeled(Labeled c, String text, double font) {
		newLabeled(c, text, DEFAULT_COLOR, font);
	}
	public static void newLabeled(Labeled c, String text, Color color, double font) {
		c.setText(text);
		setFont(c, font, false);
		c.setTextFill(color);
		c.setAlignment(Pos.CENTER);
	}
	
	//Mandates that the width is exactly the width given
	public static void makeWidth(Region r, double width) {
		r.setMinWidth(width);
		r.setMaxWidth(width);
	}
	//Mandates that the height is exactly the height given
	public static void makeHeight(Region r, double height) {
		r.setMinHeight(height);
		r.setMaxHeight(height);
	}
	//Mandates both the height and width
	public static void makeDim(Region r, double width, double height) {
		makeWidth(r, width);
		makeHeight(r, height);
		
	}
	
	//Sets font to the given size and bold setting
	public static void setFont(Control c, double size, boolean bold ) {
		if (c instanceof Labeled) {
			Labeled l = (Labeled)c;
			l.setFont(Font.font(null, bold ? FontWeight.BOLD : FontWeight.NORMAL, size));
			return;
		}
		if (c instanceof TextInputControl) {
			TextInputControl t = (TextInputControl)c;
			t.setFont(Font.font(null, bold ? FontWeight.BOLD : FontWeight.NORMAL, size));
			return;
		}
		throw new IllegalArgumentException("Wrong type of control.");
	}
	//Sets font to the existing size and given bold setting
	public static void setBold(Labeled l, boolean value) {
		setFont(l, l.getFont().getSize(), value);
	}
	public static void setFont(Labeled l, double value) {
		l.setFont(new Font(value));
	}
	
	public static void addLine(Control c, String text) {
		addLine(c, "\n" + text);
	}
	public static void addText(Control c, String text) {
		if (c instanceof Labeled) {
			Labeled l = (Labeled)c;
			l.setText(l.getText() + text);
			return;
		}
		if (c instanceof TextInputControl) {
			TextInputControl t = (TextInputControl)c;
			t.setText(t.getText() + text);
			return;
		}
		throw new IllegalArgumentException("Wrong type of control.");
	}
}
