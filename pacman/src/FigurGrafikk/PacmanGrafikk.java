package FigurGrafikk;
import javafx.scene.image.*;

/**
 * Grafiske representasjon av pacman         
 * <p>
 * Inneholder 8 bilder. 2 for hver retning
 * </p>
 * @author	Trygve Johannessen 
 */
public class PacmanGrafikk extends FigurGrafikk {
	private Image LEFT_1 = new Image("Bilder/pacLEFT1.png");
	private Image LEFT_2 = new Image("Bilder/pacLEFT2.png");
	private Image RIGHT_1 = new Image("Bilder/pacRIGHT1.png");
	private Image RIGHT_2 = new Image("Bilder/pacRIGHT2.png");
	private Image TOP_1 = new Image("Bilder/pacUP1.png");
	private Image TOP_2 = new Image("Bilder/pacUP2.png");
	private Image DOWN_1 = new Image("Bilder/pacDOWN1.png");
	private Image DOWN_2 = new Image("Bilder/pacDOWN2.png");

	public PacmanGrafikk() {
		super(STR, STR);
		setImage(LEFT_1); 
		plasser();
	}
	
	/**
	 * Plasserer pacman ut på brettet
	 */
	public void plasser() {
		setX(STR*13);
		setY(STR*23);
	}
	
	/**
	 * Veksler mellom de 2 bildene for den aktuelle retningen som pacman går.
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