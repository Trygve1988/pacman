package BlokkGrafikk;
import javafx.scene.image.*;

/**
 * Grafiske representasjon av en flis med booster
 *          
 * @author	Trygve Johannessen 
 */
public class BoosterGrafikk extends BlokkGrafikk {
	private Image img = new Image("Bilder/booster.png");
	
	public BoosterGrafikk(int x, int y) {
		super(x, y);
		setImage(img); 
	}
}