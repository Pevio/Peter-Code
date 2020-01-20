package fx;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LBL extends Label {
	public LBL(String text) {
		super(text);
		setFont(14, false);
		this.setTextFill(Color.BLACK);
	}
	
	public void makeWidth(double width) {
		this.setMinWidth(width);
		this.setMaxWidth(width);
	}
	public void makeHeight(double height) {
		this.setMinHeight(height);
		this.setMaxHeight(height);
	}
	
	public void setFont(double size, boolean bold ) {
		setFont(Font.font(null, bold ? FontWeight.BOLD : FontWeight.NORMAL, size));
	}
	public void setBold(boolean value) {
		this.setFont(this.getFont().getSize(), value);
	}
	public void setFont(double value) {
		this.setFont(new Font(value));
	}
}
