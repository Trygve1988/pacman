package Paneler;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;

/**
 * FruktPanel viser hvilken frukt som vil bli plassert ut på brettet på den aktuelle banen.  
 *              
 * @author	Trygve Johannessen 
 */
public class FruktPanel extends HBox {
	private Image[] bildeTab = {
		new Image("Bilder/fruitCherry.png"),
		new Image("Bilder/fruitStrawberry.png"),
		new Image("Bilder/fruitOrange.png"),
		new Image("Bilder/fruitApple.png"),
		new Image("Bilder/fruitPear.png"),
		new Image("Bilder/fruitBanana.png"),
		new Image("Bilder/fruitPretzel.png")
	};
	private int antFrukt = 0;
	private boolean fruktPaaBanen = false;
	
	public FruktPanel() {
		ImageView bilde = new ImageView(bildeTab[antFrukt]);
		bilde.setFitWidth(30);
		bilde.setFitHeight(30);
		getChildren().add(bilde);
		antFrukt++;
		setMinWidth(240);
	}
	
	/**
	* Setter neste frukt i panelet. Metoden kalles når vi bytter til neste bane.
	*/
	public void nesteFrukt(int fruktNr) {
		if (antFrukt<11) {
			ImageView bilde = new ImageView(bildeTab[fruktNr-1]);
			bilde.setFitWidth(30);
			bilde.setFitHeight(30);
			getChildren().add(bilde);
			antFrukt++;
			if (antFrukt>7) {
				getChildren().remove(0);
			}
		}
	}
	
	/**
	* Sjekker om det er frukt på banen
	* 
	* @return erFruktPaaBanen boolean verdi som angir om det er en frukt på banen
	*/
	public boolean erFruktPaaBanen() {
		return fruktPaaBanen;
	}
	
	/**
	* Registrerer om det er frukt på banen eller ikke
	* 
	* @param erFruktPaaBanen boolean verdi som angir om det er en frukt på banen
	*/
	public void setFruktPaaBanen(boolean fruktPaaBanen) {
		this.fruktPaaBanen = fruktPaaBanen;
	}
	
}
