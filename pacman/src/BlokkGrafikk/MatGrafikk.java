package BlokkGrafikk;
import javafx.scene.image.*;

/**
 * Grafiske representasjon av en flis med mat
 *          
 * @author	Trygve Johannessen 
 */
public class MatGrafikk extends BlokkGrafikk {
	private Image img = new Image("Bilder/food.png");

	public MatGrafikk(int x, int y) {
		super(x,y);
		setImage(img); 
	}
}