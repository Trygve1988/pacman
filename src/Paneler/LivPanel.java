package Paneler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * LivPanel viser hvor mange liv pacman har.  
 *              
 * @author	Trygve Johannessen 
 */
public class LivPanel extends HBox {
	private int liv = 2; 
	private Image img = new Image("Bilder/pacRIGHT1.png");
	private ImageView iw;
	
	public LivPanel() {
		for (int i=0; i<liv; i++) {
			iw = new ImageView(img);
			getChildren().add(iw);
		}
		setPadding(new Insets(5));
		setSpacing(1);
		setMinWidth(120);
	}
	
	public int getLiv() {
		return liv;
	}
	
	public void fåEtLiv() {
		liv++;
		if (liv<5) {
			ImageView iw = new ImageView(img);
			getChildren().add(iw);
		}
	}

	public void mistEtLiv() {
		liv--;
		if (liv >= 0 && liv <= 3) { //viss liv er 0 - 3
			getChildren().remove(0);
		}
		System.out.println(liv);
	}
}
