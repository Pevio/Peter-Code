import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

/* Provides a screen showing a visualization of a polar coordinates based city, with tools to move and zoom
 * 
 */

public class PolarCity extends Application {
	private Pane pane = new Pane();
	private Pane holdingPane = new Pane();
	private Scene scene = new Scene(holdingPane);
	
	private double midX = Screen.getPrimary().getBounds().getWidth() / 2;
	private double midY = Screen.getPrimary().getBounds().getHeight() / 2;
	private int rDis = 15;														//Distance from one r to the next
	private final double tau = Math.PI * 2;
	private Line[] theta;
	private Circle[] r;
	
	//Drag and drop the map with the mouse information
	private boolean isDragging = false;
	private double dragX; double dragY;
	
	//The small grey + in the middle
	private Line middleLine1 = new Line(midX - 5, midY, midX + 5, midY);
	private Line middleLine2 = new Line(midX, midY - 5, midX, midY + 5);
	
	public static void main(String[] args) {
		launch(args);
	}
	public void start(Stage stage) {
		//For keyboard shortcuts
		scene.setOnKeyPressed(e -> keyPress(e.getCode().toString()));
		
		//For dragging
		scene.setOnMousePressed(e -> mouseDown(e.getSceneX(), e.getSceneY()));
		scene.setOnMouseDragged(e -> mouseMove(e.getSceneX(), e.getSceneY()));
		scene.setOnMouseReleased(e -> mouseUp());
		
		load();
		
		stage.setScene(scene);
		stage.setTitle("Polar City");
		
		stage.show();
	}
	
	public void load() {
		//Make all the theta's and r's
		theta = new Line[1300];
		r = new Circle[112];

		//r
		for (int i = 1; i < r.length; i++) {
			r[i] = new Circle(i * rDis);
			r[i].setStrokeWidth(1);
			if (i % 6 == 0 || i == 1) r[i].setStrokeWidth(2);
			if ((i % 24) - 18 == 0) r[i].setStrokeWidth(4);
			
			r[i].setCenterX(midX);
			r[i].setCenterY(midY);
			r[i].setFill(Color.TRANSPARENT);
			
			r[i].setStroke(Color.BLACK);
			pane.getChildren().add(r[i]);
		}
		
		//4
		int thetaCount = 0;
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * i / 4, 1, 6);
			thetaCount++;
		}
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * i / 4, 6, 18, 2);
			thetaCount++;
		}
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * i / 4, 18, r.length - 1, 4);
			thetaCount++;
		}
		
		//8
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * (i - 0.5) / 4, 1, 12);
			thetaCount++;
		}
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * (i - 0.5) / 4, 12, 42, 2);
			thetaCount++;
		}
		for (int i = 0; i < 4; i++) {
			makeTheta(thetaCount, tau * (i - 0.5) / 4, 42, r.length - 1, 4);
			thetaCount++;
		}
		
		//pi/8
		for (int i = 0; i < 8; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 8.0, 2, 12);
			thetaCount++;
		}
		for (int i = 0; i < 8; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 8.0, 12, 66, 2);
			thetaCount++;
		}
		for (int i = 0; i < 8; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 8.0, 66, r.length - 1, 4);
			thetaCount++;
		}
		
		//pi/16
		for (int i = 0; i < 16; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 16.0, 4, 18);
			thetaCount++;
		}
		for (int i = 0; i < 16; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 16.0, 18, 90, 2);
			thetaCount++;
		}
		for (int i = 0; i < 16; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 16.0, 90, r.length - 1, 4);
			thetaCount++;
		}
		
		//pi/32
		for (int i = 0; i < 32; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 32.0, 10, 30);
			thetaCount++;
		}
		for (int i = 0; i < 32; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 32.0, 30, r.length - 1, 2);
			thetaCount++;
		}
		
		for (int i = 0; i < 64; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 64.0, 19, 55);
			thetaCount++;
		}
		for (int i = 0; i < 64; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 64.0, 55, r.length - 1, 2);
			thetaCount++;
		}
		
		for (int i = 0; i < 128; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 128.0, 43, r.length - 1);
			thetaCount++;
		}
		
		for (int i = 0; i < 256; i++) {
			makeTheta(thetaCount, tau * (i + 0.5) / 256.0, 55, r.length - 1);
			thetaCount++;
		}
		
		middleLine1.setStroke(Color.GREY);
		middleLine2.setStroke(middleLine1.getStroke());
		pane.getChildren().addAll(middleLine1, middleLine2);
		
		holdingPane.getChildren().add(pane);
	}

	//Create a theta line with the given characteristics
	public void makeTheta(int i, double angle, int startR, int endR) {
		makeTheta(i, angle, startR, endR, 1);
	}
	public void makeTheta(int i, double angle, int startR, int endR, int width) {
		theta[i] = new Line(midX + Math.cos(angle) * startR * rDis, midY + Math.sin(angle) * startR * rDis,
				midX + Math.cos(angle) * endR * rDis, midY + Math.sin(angle) * endR * rDis);
		theta[i].setStrokeWidth(width);
		
		pane.getChildren().add(theta[i]);
	}
	
	public void setCenter() {
		//Relocate the the middle
		relocate(0, 0);
	}
	public void relocate(double x, double y) {
		//Manually move the middle line, then relocate the entire pane
		middleLine1.setStartX(middleLine1.getStartX() + pane.getLayoutX() - x);
		middleLine1.setEndX(middleLine1.getEndX() + pane.getLayoutX() - x);
		middleLine1.setStartY(middleLine1.getStartY() + pane.getLayoutY() - y);
		middleLine1.setEndY(middleLine1.getEndY() + pane.getLayoutY() - y);
		middleLine2.setStartX(middleLine2.getStartX() + pane.getLayoutX() - x);
		middleLine2.setEndX(middleLine2.getEndX() + pane.getLayoutX() - x);
		middleLine2.setStartY(middleLine2.getStartY() + pane.getLayoutY() - y);
		middleLine2.setEndY(middleLine2.getEndY() + pane.getLayoutY() - y);
		
		pane.relocate(x, y);
	}
	public void keyPress(String code) {
		//Move
		if (code == "LEFT") relocate(pane.getLayoutX() + 20, pane.getLayoutY());
		if (code == "RIGHT") relocate(pane.getLayoutX() - 20, pane.getLayoutY());
		if (code == "UP") relocate(pane.getLayoutX(), pane.getLayoutY() + 20);
		if (code == "DOWN") relocate(pane.getLayoutX(), pane.getLayoutY() - 20);
		
		if (code == "A") relocate(pane.getLayoutX() + 60, pane.getLayoutY());
		if (code == "D") relocate(pane.getLayoutX() - 60, pane.getLayoutY());
		if (code == "W") relocate(pane.getLayoutX(), pane.getLayoutY() + 60);
		if (code == "S") relocate(pane.getLayoutX(), pane.getLayoutY() - 60);
		
		if (code == "H") relocate(pane.getLayoutX() + rDis, pane.getLayoutY());
		if (code == "K") relocate(pane.getLayoutX() - rDis, pane.getLayoutY());
		if (code == "U") relocate(pane.getLayoutX(), pane.getLayoutY() + rDis);
		if (code == "J") relocate(pane.getLayoutX(), pane.getLayoutY() - rDis);
		
		if (code == "SPACE") setCenter();
		
		if (code == "R" || code == "T") {
			//Rotate counterclockwise around the center
			double xDis = middleLine2.getStartX() - midX;
			double yDis = midY - middleLine1.getStartY();
			double hyp = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));
			double angle = Math.atan2(yDis, xDis);

			if (angle == 100) angle = Math.atan(yDis/xDis);
			double newAngle = angle + ((code.equals("R") ? tau : -tau) / 70);
			double newX = hyp * Math.cos(newAngle);
			double newY = hyp * Math.sin(newAngle);
			relocate(pane.getLayoutX() - newX + xDis, pane.getLayoutY() - yDis + newY);
		}
		
		//Zoom
		for (int i = 0; i < 10; i++) {
			if (code.equals("DIGIT" + i)) {
				setCenter();
				rDis = (12 - i) * 3;
				pane.setVisible(false);
				pane = new Pane();
				theta = null;
				r = null;
				load();
			}
		}
	}
	
	public void mouseDown(double x, double y) {
		//Starts dragging
		isDragging = true;
		dragX = x;
		dragY = y;
	}
	public void mouseMove(double x, double y) {
		if (isDragging) {
			//Drag
			relocate(pane.getLayoutX() + x - dragX, pane.getLayoutY() + y - dragY);
			dragX = x;
			dragY = y;
		}
	}
	public void mouseUp() {
		//Ends dragging
		isDragging = false;
	}
}
