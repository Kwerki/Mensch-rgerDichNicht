package medn;

import javax.swing.JOptionPane;

public class Game extends Thread {
	private static Game game = null;
	private static Player[] player = null;
	private static Player playeronturn = null;
	private int target;
	
    public Game() {
    	player = new Player[4];
	}
    
    public static void createGame() {					// Erstelle Objekt Game
    	game = new Game();
    }
    
	public static Game getGame() {						// Gebe Game Objekt aus
		return game;
	}
	
	public static Player getPlayer(int playerID) {		// Gebe bestimmten Spieler Objekt zurück
		return player[playerID];
	}
	
	public void setTarget(int target) {					// Mit der Maus angeklickte figur
        this.target = target;
    }
	
	public static Player getPlayerOnTurn() {			// Gebe Spieler ID aus die dran ist
		return playeronturn;
	}
	
	private void rollthedice() {						// Würfle solange eine 6 gewürfelt wird
		int diceresult;
		do {
			diceresult = Board.getBoard().dice();
			Gui.getGui().message(playeronturn.getName() + " hat eine " + diceresult + " gewuerfelt.");
			boolean successful = false;
			try {
			    Thread.sleep(1500);
			}
			catch (InterruptedException e) {
			    e.printStackTrace();
			}
			if (Board.getBoard().onStartTest(playeronturn)) {
				successful = playeronturn.move(playeronturn.getStart(), diceresult);
			}
			if (diceresult == 6) {
				if (playeronturn.goOut()) {
					successful = true;
				}
			}
			while (!successful && playeronturn.movePossible(diceresult))
			{
				if (playeronturn.isBot() == true) {
					int[] intArray = playeronturn.getPegsPosition();
				    int maxNum = intArray[0];
				    for (int j : intArray) {
				    	if (j > maxNum) {
				    		maxNum = j;
				    	}
				    }
					for(int positionY: playeronturn.getPegsPosition()) {
					    if ((positionY != -1) && (playeronturn.move(positionY, diceresult))) {
					    	successful = true;
					    	break;
					    }
					}
				}
				else {
					try {
						synchronized (this) {
							this.wait();
						}
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					successful = playeronturn.move(this.target, diceresult);
				}
				if (!successful) {
					Gui.getGui().message("Zug ungültig! 1");
				}
			}
			Gui.getGui().repaintBoard();
		}
		while (diceresult == 6);
	}
	
    public void play() {										// Spiel solange bis ein Spieler gewonnen hat
		boolean nowinner = true;
        while (nowinner) {
        	for (Player player : player) {
        		playeronturn = player;
        		if (!player.isOutside()) {
        			for (int i = 0; i < 3; i = i + 1) { 		// Würfle 3x um raus zu kommen
        				byte diceresult = Board.getBoard().dice();
        				Gui.getGui().message(player.getName() + " hat eine " + diceresult + " gewuerfelt.");
        				if (diceresult == 6) {
        					player.goOut();
        					rollthedice();
        					break;
        				}
        			}
        		}
        		else {
        			rollthedice();
        		}
	        	if (player.isHouse()) {
	        		JOptionPane.showMessageDialog(null,playeronturn.getName() + " hat gewonnen","",JOptionPane.INFORMATION_MESSAGE);
	        		nowinner = false;
	        	}
	        }
        }
    }
    
    public void createPlayer(int input) {				// Test Funktion
    	switch(input) {
    		case 1:
    		    player[0] = new Player("Grün", 0, false);
    		    player[1] = new Player("Rot", 1, true);
    		    player[2] = new Player("Blau", 2, true);
    		    player[3] = new Player("Gelb", 3, true);
    		    break;
    		case 2:
    		    player[0] = new Player("Grün", 0, false);
    		    player[1] = new Player("Rot", 1, false);
    		    player[2] = new Player("Blau", 2, true);
    		    player[3] = new Player("Gelb", 3, true);
    		    break;
    		case 3:
    		    player[0] = new Player("Grün", 0, false);
    		    player[1] = new Player("Rot", 1, false);
    		    player[2] = new Player("Blau", 2, false);
    		    player[3] = new Player("Gelb", 3, true);
    		    break;
    		case 4:
    		    player[0] = new Player("Grün", 0, false);
    		    player[1] = new Player("Rot", 1, false);
    		    player[2] = new Player("Blau", 2, false);
    		    player[3] = new Player("Gelb", 3, false);
    		    break;
    		default:
    		    player[0] = new Player("Grün", 0, true);
    		    player[1] = new Player("Rot", 1, true);
    		    player[2] = new Player("Blau", 2, true);
    		    player[3] = new Player("Gelb", 3, true);
    		    break;
    	}
    }
}
