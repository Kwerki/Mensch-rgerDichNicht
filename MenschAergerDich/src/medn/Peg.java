package medn;

public class Peg {
	private Player player;
	private int position;
	
	public Peg(Player playerName) {
		this.player = playerName;
		this.position = -1;
	}
	
	public Player getPlayer() {							// Gebe Figurenbesitzer zurück
		return player;
	}
	
	public int getPosition() {							// Gebe Figuren Possition zurück
		return position;
	}
	
	public void setPosition(int newPosition) {
		this.position = newPosition;
	}
	
	public void delete() {								// Setze Figur Possition auf Start
		position = -1;
	}
	
	public boolean nearHouse() {						// Ist nahe Haus
		switch (player.getID()) {
		case 0:
			return (position > 39 - 6) && (position <= 39);
		case 1:
			return (position > 9 - 6) && (position <= 9);
		case 2:
			return (position > 19 - 6) && (position <= 19);
		case 3:
			return (position > 29 - 6) && (position <= 29);
		default:
			return false;
		}
	}
	
	public boolean isHouse() {							// Figur im Haus?
		return this.position > 39 + (4 * player.getID());
	}

	public void goOut() {								// Setze Figur auf Startfeld
		position = player.getStart();
	}
}
