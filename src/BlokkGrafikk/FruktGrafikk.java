package BlokkGrafikk;
import javafx.scene.image.Image;

/**
 * Grafiske representasjon av en flis med frukt
 *          
 * @author	Trygve Johannessen 
 */
public class FruktGrafikk extends BlokkGrafikk {
	private Image[] bildeTab = {
		new Image("Bilder/fruitCherry.png"),    //  100
		new Image("Bilder/fruitStrawberry.png"),//  200
		new Image("Bilder/fruitOrange.png"),	//  300
		new Image("Bilder/fruitApple.png"),		//  500
		new Image("Bilder/fruitPear.png"),		// 1000
		new Image("Bilder/fruitBanana.png"),	// 2000
		new Image("Bilder/fruitPretzel.png")	// 5000
	};
	private Image frukt = bildeTab[0];
	
	public FruktGrafikk(int x, int y, int fruktNr) {
		super(x, y);
		if (fruktNr<7) {
			frukt = bildeTab[fruktNr-1];
			setImage(frukt); 
		}
	}
}