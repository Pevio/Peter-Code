/* @ author Peter Gossell
*/

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.math.*;

public class PascalsTriangle extends Application {
	Button go = new Button("GO");
	TextField txtRows = new TextField("1");
	Button btnExit = new Button("Exit");
	Pane holderPane = new Pane();
	Pane pane = new Pane();
	int y1 = 10; int y2 = 40;
	double centerX = 0;
	double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage stage) {
		//Button and TextField go in holderPane, 
		holderPane.getChildren().addAll(pane, txtRows, go, btnExit);
		Scene scene = new Scene(holderPane, 600, 300);
		txtRows.setOnKeyPressed(e -> keyPress(e.getCode().toString()));
		go.setOnKeyPressed(e -> keyPress(e.getCode().toString()));
		btnExit.setOnKeyPressed(e -> keyPress(e.getCode().toString()));
		txtRows.relocate(10, y1);
		txtRows.setMaxWidth(50);
		go.relocate(70, y1);
		go.setOnAction(e -> clickGO());
		go.setDefaultButton(true);
		btnExit.setOnAction(e -> System.exit(0));
		btnExit.relocate(120, y1);
		
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(null);
		stage.show();
	}
	
	public void clickGO() {
		//Generates the triangle, positioning each label as nessecary and putting them in pane
		try {
			pane.getChildren().clear();
			int rows = Integer.parseInt(txtRows.getText());
			double width = new String(choose(rows - 1, rows / 2) + "").length() * 9;
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j <= i; j++) {
					Label label = new Label(choose(i, j) + "");
					label.relocate((rows - (i / 2.0) + j - (rows / 2.0)) * width - label.getText().length(), y2 + i * 18);
					label.setTextFill(Color.BLACK);
					pane.getChildren().add(label);
					if (i == 0) centerX = label.getLayoutX() - screenWidth / 2;
				}
			}
		} catch (Exception e) {}
		centerPane();
	}
	
	public void keyPress(String code) {
		//Pressing arrow keys moves things around and the space bar centers it
		final int move = 50;
		if (code.equals("UP")) {
			pane.relocate(pane.getLayoutX(), pane.getLayoutY() + move);
		} else if (code.equals("DOWN")) {
			pane.relocate(pane.getLayoutX(), pane.getLayoutY() - move);
		} else if (code.equals("RIGHT")) {
			pane.relocate(pane.getLayoutX() - move, pane.getLayoutY());
		} else if (code.equals("LEFT")) {
			pane.relocate(pane.getLayoutX() + move, pane.getLayoutY());
		} else if (code.equals("SPACE")) {
			centerPane();
		} else if (code.equals("ESCAPE")) {
			System.exit(0);
		}
	}
	public void centerPane() {
		//Relocates the pane on the other pane based on where the top row is
		pane.relocate(-centerX, y1);
	}
	
	public BigInteger choose(long n, long k) {
		//Choose function, used to determine label titles, and which calls factorial
		return factorial(n).divide(factorial(k).multiply(factorial(n - k)));
	}
	public BigInteger factorial(long n) {
		//Returns the factorial of the number, in BigInteger form
		BigInteger result = BigInteger.ONE;
		for (int i = 2; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}
}
