package medn;

import java.util.Vector;

public class Player {
	private Vector<Peg> playerPegs = null;
	private int playerStart = -1;
	private int playerID = -1;
	private boolean playerBot = false;
	private String playerName = null;
	
	public Player(String playerName, int playerID, boolean isBot) {
		this.playerStart = playerID * 10;
		this.playerName = playerName;
		this.playerID = playerID;
		this.playerBot = isBot;
		this.playerPegs = new Vector<Peg>(4);
		for (int i = 0; i < 4; i = i + 1) {
			this.playerPegs.add(new Peg(this));
		}
	}
	
	public int getStart() {							// Gebe Spieler Startfeld zurück
		return playerStart;								
	}
	
	public String getName() {						// Gebe Spieler Namen zurück
		return playerName;
	}
	
	public void setPlayer(String name, boolean isbot) {
		this.playerName = name;
		this.playerBot = isbot;
	}
	
	public boolean isBot() {
		return playerBot;
	}
	
	public int getID() {							// Gebe Spieler ID zurück
		return playerID;
	}
	
	public boolean move(int position, int steps) {	
		Peg peg = null;
		for (int i = 0; i < 4; i = i + 1) {
			peg = playerPegs.get(i);
		    if (peg.getPosition() == position) {
		    	break;
		    }
		}
		if (peg == null || peg.getPosition() != position  ) {
		    return false;
		}
		int newPosition = Board.getBoard().movePeg(this, peg.getPosition(), steps);
		if (newPosition > -1) {
			peg.setPosition(newPosition);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isHouse() {							// Hat alle Figuren im Haus?
		for (Peg peg : playerPegs) {
			if (!peg.isHouse())
				return false;
		}
		return true;
	}
	
	public boolean isOutside() {						// Hat Figuren außerhalb?
		for (Peg peg : playerPegs) {
			if ((peg.getPosition() >= 0) && (peg.getPosition() <= 39)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean goOut() {							// Versuche auf Startfeld zu kommen
		for (Peg peg : playerPegs) {
			if (peg.getPosition() == -1) {
				if(Board.getBoard().goOut(this, peg))
				{
					peg.goOut();
					return true;
				}
				else {
					break;
				}
			}
		}
		return false;
	}
	
	public Vector<Peg> getPegs() {						// Gebe Figuren Objekt zurück
		return playerPegs;
	}
	
	public int[] getPegsPosition() {					// Gebe Array mit Figurenpossitionen zurück
		int[] playerPegsArray = new int[4];
		for (int i = 0; i < 4; i = i + 1) {
			playerPegsArray[i] = getPegs().get(i).getPosition();
		}
		return playerPegsArray;
	}
	
    public boolean movePossible(int diceresult) {	// Teste ob noch Züge möglich sind
    	for (Peg peg : playerPegs) {
    		if (((diceresult == 6) && (Board.getBoard().goOutTest(peg.getPlayer(), peg))) || ((peg.getPosition() > -1) && (Board.getBoard().moveValid(this, peg.getPosition(), diceresult))))
    			return true;
    	}
    	return false;
    }
}
