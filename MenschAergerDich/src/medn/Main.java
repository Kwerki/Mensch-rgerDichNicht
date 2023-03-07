package medn;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		Object[] options = { "  0  ","  1  ", "  2  ", "  3  ", "  4  " };
		int input = JOptionPane.showOptionDialog(null, "Wie viele echte Spieler spielen mit?", "Spielerzahl",JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		System.out.println(input+1);
		Gui.createGui(); // Erstelle Objekt GUI
		Game.createGame(); // Erstelle Objekt Game
		Board.createBoard(); // Erstelle Objekt Board
		Game.getGame().createPlayer(input);
		Game.getGame().play();
	}
}