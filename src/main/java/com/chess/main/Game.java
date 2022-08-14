package com.chess.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -473349850293143017L;

	public static final int WIDTH = 640, HEIGHT = WIDTH/12*9;
	private Thread thread;
	private boolean running = false;
	private ChessBoard board;
		
	public enum STATE {
		Menu,
		Options,
		Pause,
		Checkmate,
		Game
	}
	
	public STATE gameState = STATE.Game;
	
	public Game() {
		// TODO Auto-generated method stub
		this(1);
	}
	
	public Game(int players){
		new Window(WIDTH, HEIGHT, "Chess", this);
		board = new ChessBoard();
		this.addMouseListener(board);
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			if(System.currentTimeMillis() - timer > 1000) {
				timer+=1000;
			}
		}
		stop();
	}
	
	private void tick() {
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		try {
			board.render(g);
		}
		catch(NullPointerException e){
			//e.printStackTrace();
		}
		
		g.dispose();
		bs.show();
	}
}