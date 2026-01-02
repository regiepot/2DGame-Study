package main;

import javax.swing.JFrame;

public class MainClass {

	public static void main(String[] args) {
		
		JFrame window = new JFrame("2D game");
		GamePanel GamePanel = new GamePanel();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(GamePanel);
		window.pack();
		window.setResizable(false);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		GamePanel.setupGame();
		GamePanel.startGameThread();
	}

}
