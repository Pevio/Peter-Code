package fx;

import javafx.scene.layout.*;

public class FX {
	
	public static void makeWidth(Region r, double width) {
		r.setMinWidth(width);
		r.setMaxWidth(width);
	}
	public static void makeHeight(Region r, double height) {
		r.setMinHeight(height);
		r.setMaxHeight(height);
	}
	
}
