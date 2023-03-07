package medn;

import java.util.Vector;

public class Board {
	private static Board board = null;
	private Vector<Peg> fields;
	
	private Board() {
		fields = new Vector<Peg>(56);
		fields.setSize(56);
	}
	
    public static void createBoard() {			// Erstelle Spielbrett
    	board = new Board();
    }
	
	public static Board getBoard() {			// Gebe Spielbrett aus
		return board;
	}

	private int playerStart(int start) {		// Ausnahme für Spieler Grün (-1 + 40 = 39)
		while (start < 0) {
			start = start + 40;
		}
		return start;
	}
	
	public boolean moveValid(Player player, int position, int diceresult) { // Könnte optimiert werden doch läuft erstmal fehlerfrei
		if(fields.get(position) == null) {
			return false;
		}
		int fieldsize = 40;
		int endPosition = (position + diceresult) % fieldsize;
		if (fields.get(position).nearHouse()) {
			if (position + diceresult > playerStart(player.getStart() - 1)) {
				endPosition = 39 + (4 * player.getID()) + position + diceresult - playerStart(player.getStart() - 1);
			}
			fieldsize = fieldsize + 16;
		}
		if (fields.get(position).isHouse()) {
			if (position + diceresult > playerStart(player.getStart() - 1)) {
				endPosition = position + diceresult;
			}
			fieldsize = fieldsize + 16;
		}
		if ((position >= 0) && (position < fieldsize) && (endPosition < fieldsize) && (diceresult > 0) && (diceresult <= 6) && (endPosition < 40 + (player.getID() * 4) + 4) && fields.get(position).getPlayer().equals(player) && (fields.get(endPosition) == null || !fields.get(endPosition).getPlayer().equals(player))) {
			return true;
		}
		else {
			return false;
		}
	}

	public int movePeg(Player player, int position, int diceresult) { // Könnte optimiert werden doch läuft erstmal fehlerfrei
		if (moveValid(player, position, diceresult)) {
			int fieldsize = 40;
			int endPosition = (position + diceresult) % fieldsize;
			if (this.fields.get(position).nearHouse()) {
				if (position + diceresult > playerStart(player.getStart() - 1)) {
					endPosition = 39 + (4 * player.getID()) + position + diceresult - playerStart(player.getStart() - 1);
				}
			}
			if (this.fields.get(position).isHouse()) {
				if (position + diceresult > playerStart(player.getStart() - 1)) {
					endPosition = position + diceresult;
				}
			}
			if (this.fields.get(endPosition) != null) {
				this.fields.get(endPosition).delete();
			}
			this.fields.set(endPosition, fields.get(position));
			this.fields.set(position, null);
			return endPosition;
		}
		else {
			return -1;
		}
	}
	
	public boolean moveFromStart(Player player) {												// Test
		if(fields.get(player.getStart()).getPlayer() == player) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean onStartTest(Player player) {
		if((fields.get(player.getStart()) != null) && (fields.get(player.getStart()).getPlayer() == player)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean goOutTest(Player player, Peg peg) {			// Teste Startfeld
		if((peg.getPosition() != -1) || ((fields.get(player.getStart()) != null) && (fields.get(player.getStart()).getPlayer() == player))) {
			return false;
		}
		else {
			return true;
		}
	}

	public boolean goOut(Player player, Peg peg) {				// Versuche auf Startfeld zu kommen
		if(!goOutTest(player, peg)) {
			return false;
		}
		else if (fields.get(player.getStart()) != null) {
			fields.get(player.getStart()).delete();
		}
		fields.set(player.getStart(), peg);
		return true;
	}
	
	byte[] dicedebug = {6,5,6,1,1,1,1,1,1,1,5,1,1,1,1,1,1,1,2,6,4,1,1,1,1,1,1,6,5,6,4,1,1,1,1,1,1,6,4,5,1,1,1,1,1,1,6,3,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,6,5,1,1,1,1,1,1,1,6,6,4,1,1,1,1,1,1,1,1,1,6,6,4,1,1,1,1,1,1,1,1,1,6,6,6,5,1,1,1,1,1,1,6,6,5,6,6,6,3,1,1,1,1,1,1,5,6,6,6,6,2,1,1,1,1,1,1,5,1,1,1,1,1,1,1,5,2,1,1,1,5,2,1,1,1,1,1,1,5,2,1,1,1,1,1,1,3,6,5,1,1,1,1,1,1,4,5,1,1,1,1,1,1,3,2,4,3};
	int dicedebugcount = -1;
	public byte dice() {										// Würfle eine Zahl zwichen 1 und 6
		Gui.playSound("dice.wav");
		dicedebugcount = dicedebugcount + 1;
		// return dicedebug[i];
		return (byte)(Math.random() * 6 + 1);

	}
}
