package view.paneler;
import javafx.geometry.Insets;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;

/**
 * LydPanel lar brukeren skure lyd av/på
 *              
 * @author	Trygve Johannessen 
 */
public class LydPanel extends HBox {
	private boolean lydPaa = true;
	private Image lydPaaBilde = new Image("Bilder/lydPaa.png");
	private Image lydAvBilde = new Image("Bilder/lydAv.png");
	private ImageView iw;
	
	public LydPanel() {
		if (lydPaa) {
			iw = new ImageView(lydPaaBilde);	
		}
		else {
			iw = new ImageView(lydAvBilde);	
		}
		iw.setFitWidth(20);
		iw.setFitHeight(20);
		getChildren().add(iw);
		setOnMouseClicked(e -> endreLyd() );
		setPadding(new Insets(5));
		setMinWidth(50);
	}

	public boolean erLydPaa() {
		return lydPaa;
	}
	
	private void endreLyd() {
		if (lydPaa) {
			lydPaa = false;
			iw.setImage(lydAvBilde);
		}
		else {
			lydPaa = true;
			iw.setImage(lydPaaBilde);
		}
	}
}
