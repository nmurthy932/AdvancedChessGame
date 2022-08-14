package com.chess.main;

import java.awt.Color;
import java.awt.Graphics;

public class DrawShapes {

	public static void drawArrow(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(50, 50, 30, 30);
		
		g.fillRect(55, 53, 5, 10);
		g.fillRect(60, 53, 10, 5);
		
		int[] x = {70, 75, 70};
		int[] y = {62, 56, 50};
		int points = 3;
		g.fillPolygon(x, y, points);
		
		g.fillRect(70, 65, 5, 10);
		g.fillRect(60, 70, 10, 5);
		
		x = new int[] {60, 55, 60};
		y = new int[] {79, 73, 67};
		points = 3;
		g.fillPolygon(x, y, points);
	}
	
}
