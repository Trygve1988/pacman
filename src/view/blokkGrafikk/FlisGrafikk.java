package view.blokkGrafikk;
import javafx.scene.image.*;

/**
 * Grafiske representasjon av en tom flis    
 *          
 * @author	Trygve Johannessen 
 */
public class FlisGrafikk extends BlokkGrafikk {
	private Image img = new Image("Bilder/tile.png");
	
	public FlisGrafikk(int x, int y) {
		super(x, y);
		setImage(img); 
	}

}
