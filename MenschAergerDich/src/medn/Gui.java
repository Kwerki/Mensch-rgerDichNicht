package medn;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Gui {
	private static Gui gui = null;
	private JTextArea messages = null;
	private GuiBoard board = null;
	private JPanel boardContainer = null;
	private JScrollPane textContainer = null;
	private JFrame window = null;

	// Objekt: User Interface
	private Gui() {
        this.board = new GuiBoard();
		this.board.setPreferredSize(new Dimension(770,770));
		
		this.boardContainer = new JPanel();
		this.boardContainer.setBackground(new Color(0,0,0));
		this.boardContainer.setPreferredSize(new Dimension(780,780));
		this.boardContainer.add(this.board);
		
		this.messages = new JTextArea();
		this.messages.setEditable(false);
		
		this.textContainer = new JScrollPane(this.messages);
		this.textContainer.setPreferredSize(new Dimension(200,0));
		this.textContainer.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
		    public void adjustmentValueChanged(AdjustmentEvent e) {  
		        e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
		    }
		});
		this.window = new JFrame();
		this.window.setLayout(new BorderLayout());
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.window.setTitle("Mensch ärgere Dich nicht");
		this.window.setIconImage(Toolkit.getDefaultToolkit().getImage(".\\icon.png"));
		this.window.setResizable(false);
		this.window.add(boardContainer, BorderLayout.CENTER);
		this.window.add(textContainer, BorderLayout.EAST);
	}
	
	public static void createGui() {		// Erstelle GUI
		gui = new Gui();
		gui.window.pack();
		gui.window.setVisible(true);
	}
	
	public static Gui getGui() {			// Gebe GUI aus
		return gui;
	}
	
	public void repaintBoard() {			// Zeichne Spielfeld neu
		board.repaint();
	}
	
	public void message(String s) {			// Sende Nachricht an Textfeld
		messages.setText(messages.getText() + s + "\n");
	}
	
	private class GuiBoard extends JPanel implements MouseListener {
		private byte [][] boardarray = null;
		private Point[] fields = null;
		
		public GuiBoard(){
			this.setBackground(new Color(255,255,153));
			this.boardarray = new byte[][] {
				{ 2, 2, 0, 0, 1, 1, 3, 0, 0, 3, 3 },
				{ 2, 2, 0, 0, 1, 3, 1, 0, 0, 3, 3 },
				{ 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 3, 1, 0, 0, 0, 0 },
				{ 2, 1, 1, 1, 1, 3, 1, 1, 1, 1, 1 },
				{ 1, 2, 2, 2, 2, 0, 4, 4, 4, 4, 1 },
				{ 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 4 },
				{ 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 1, 5, 1, 0, 0, 0, 0 },
				{ 5, 5, 0, 0, 1, 5, 1, 0, 0, 4, 4 },
				{ 5, 5, 0, 0, 5, 1, 1, 0, 0, 4, 4 }
			};
			this.fields = new Point[] {
				new Point(4,0),new Point(4,1),new Point(4,2),new Point(4,3),new Point(4,4),new Point(3,4),new Point(2,4),new Point(1,4),new Point(0,4),new Point(0,5),new Point(0,6),new Point(1,6),new Point(2,6),new Point(3,6),new Point(4,6),new Point(4,7),new Point(4,8),new Point(4,9),new Point(4,10),new Point(5,10),new Point(6,10),new Point(6,9),new Point(6,8),new Point(6,7),new Point(6,6),new Point(7,6),new Point(8,6),new Point(9,6),new Point(10,6),new Point(10,5),new Point(10,4),new Point(9,4),new Point(8,4),new Point(7,4),new Point(6,4),new Point(6,3),new Point(6,2),new Point(6,1),new Point(6,0),new Point(5,0),new Point(5,1),new Point(5,2),new Point(5,3),new Point(5,4),new Point(1,5),new Point(2,5),new Point(3,5),new Point(4,5),new Point(5,9),new Point(5,8),new Point(5,7),new Point(5,6),new Point(9,5),new Point(8,5),new Point(7,5),new Point(6,5)};
			this.addMouseListener(this);
		}
		
		protected void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			Color tmpColor = g2.getColor();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setStroke(new BasicStroke(4));
			g2.setFont(new Font("Segoe Script", Font.PLAIN, 46));
			g2.drawString("Mensch",50,225);
			g2.drawString("ärgere",545,220);
			g2.drawString("Dich",70,575);
			g2.drawString("nicht",550,575);
			for(int i = 0; i < 11; i = i + 1){
				for(int j = 0; j < 11; j = j + 1) {
					Color currentColor = getColor(boardarray[i][j]);
					if(currentColor != null) {
						g2.setColor(currentColor);
						g2.fillOval(j*70+7, i*70+7, 56, 56);
						g2.setColor(Color.black);
						g2.drawOval(j*70+7, i*70+7, 56, 56);
					}
				}
			}
			for(int i = 0; i < 4; i = i + 1) {
				Color currentColor = getColor(i + 2);
				g2.setColor(currentColor.darker().darker());
				int fillStart = 0;
				for(int figur : Game.getPlayer(i).getPegsPosition()) {
					Point position = new Point();
					if(figur == -1) {
						switch(i) {
							case 0:
								position.x = 0;
								position.y = 0;
								break;
							case 1:
								position.x = 9;
								position.y = 0;
								break;
							case 2:
								position.x = 9;
								position.y = 9;
								break;
							case 3:
							default:
								position.x = 0;
								position.y = 9;
								break;
						}
						switch(fillStart) {
							case 3:
								position.y = position.y + 1;
								break;
							case 1:
								position.x = position.x + 1;
								break;
							case 2:
								position.x = position.x + 1;
								position.y = position.y + 1;
								break;
							case 0:
							default:
								break;
							}
						fillStart = fillStart + 1;
					}
					else {
						position.x = this.fields[figur].y;
						position.y = this.fields[figur].x;
					}
					position.x = position.x * 70;
					position.y = position.y * 70;
					g2.setColor(currentColor.darker().darker());
					g2.fillOval(position.x + 18, position.y + 18, 35, 35);
					g2.setColor(currentColor.darker());
					g2.fillOval(position.x + 24, position.y + 24, 23, 23);
				}
			}
			g2.setColor(tmpColor);
		}

		private Color getColor(int i) {		// Gebe Farbe zurück
			Color currentColor = null;
			switch(i) {
			case 0:
				break;
			case 2:
				currentColor = new Color(0,153,0); // Grün
				break;
			case 3:
				currentColor = new Color(200,0,0); // Rot
				break;
			case 4:
				currentColor = new Color(1,73,199); // Blau
				break;
			case 5:
				currentColor = new Color(229,229,0); // Gelb
				break;
			case 1:
				currentColor = new Color(255,255,255); // Weis
				break;
			}
			return currentColor;
		}
		
		public void mouseClicked(MouseEvent e) { // Gebe Stelle des Mausklicks aus
			Point position = new Point(e.getX()*11/770, e.getY()*11/770);
			position.setLocation(position.y, position.x);
			int spielerNummer = Game.getPlayerOnTurn().getID();
			boolean figurExistiert = false;
			for(int positionFigur: Game.getPlayerOnTurn().getPegsPosition()) {
				if(positionFigur == -1) {
					switch (spielerNummer) {
					case 0:
						if ((position.x == 0 && position.y == 0) || (position.x == 1 && position.y == 0) || (position.x == 1 && position.y == 1) || (position.x == 0 && position.y == 1)) {
							figurExistiert = true;
						}
						break;
					case 1:
						if ((position.x == 0 && position.y == 9) || (position.x == 1 && position.y == 9) || (position.x == 1 && position.y == 10) || (position.x == 0 && position.y == 10)) {
							figurExistiert = true;
						}
						break;
					case 2:
						if ((position.x == 9 && position.y == 9) || (position.x == 9 && position.y == 10) || (position.x == 10 && position.y == 10) || (position.x == 10 && position.y == 9)) {
							figurExistiert = true;
						}
						break;
					case 3:
						if ((position.x == 9 && position.y == 0) || (position.x == 10 && position.y == 0) || (position.x == 10 && position.y == 1) || (position.x == 9 && position.y == 1)) {
							figurExistiert = true;
						}
						break;
					}
				}
				else {
					if(positionFigur >= 0 && position.equals(this.fields[positionFigur])) {
						figurExistiert = true;
					}
				}
				if (figurExistiert) {
					Game.getGame().setTarget(positionFigur);
					synchronized(Game.getGame()) {
			        	Game.getGame().notify();
			        }
					break;
				}
			}
		}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	}
	
	public static void playSound(String soundName) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}
}