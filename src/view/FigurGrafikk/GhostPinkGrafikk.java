package view.FigurGrafikk;
import javafx.scene.image.Image;

/**
 * Grafiske representasjon av det rosa spøkelse         
 * <p>
 * Inneholder 8 bilder. 2 for hver retning
 * </p>
 * @author	Trygve Johannessen 
 */
public class GhostPinkGrafikk extends GhostGrafikk {
	private final static Image LEFT_1 = new Image("Bilder/pinkLEFT1.png");
	private final static Image LEFT_2 = new Image("Bilder/pinkLEFT2.png");
	private final static Image RIGHT_1 = new Image("Bilder/pinkRIGHT1.png");
	private final static Image RIGHT_2 = new Image("Bilder/pinkRIGHT2.png");
	private final static Image TOP_1 = new Image("Bilder/pinkUP1.png");
	private final static Image TOP_2 = new Image("Bilder/pinkUP2.png");
	private final static Image DOWN_1 = new Image("Bilder/pinkDOWN1.png");
	private final static Image DOWN_2 = new Image("Bilder/pinkDOWN2.png");

	public GhostPinkGrafikk() {
		super(STR, STR);
		plasser();
		setImage(LEFT_1); 
	}
	
	/**
	 * Plasserer spøkelset ut på brettet
	 */
	@Override
	public void plasser() {
		setX(STR*15);
		setY(STR*15);
	}
	
	/**
	 * Veksler mellom de 2 bildene for den aktuelle retningen som spøkelset går.
	 */
	@Override
	public void bildeAnismasjon() {
		switch (retning) {
			case "LEFT": 		
				if (this.getImage()==LEFT_1) {
					this.setImage(LEFT_2); 	
				}	
				else {
					this.setImage(LEFT_1); 	
				}	
				break;
			case "RIGHT": 
				if (this.getImage()==RIGHT_1) {
					this.setImage(RIGHT_2); 	
				}	
				else {
					this.setImage(RIGHT_1); 	
				}	
				break;
			case "UP":  
				if (this.getImage()==TOP_1) {
					this.setImage(TOP_2); 	
				}	
				else {
					this.setImage(TOP_1); 	
				}	
				break;
			case "DOWN": 
				if (this.getImage()==DOWN_1) {
					this.setImage(DOWN_2); 	
				}	
				else {
					this.setImage(DOWN_1); 	
				}	
				break;
			default: 
		}
	}
}

