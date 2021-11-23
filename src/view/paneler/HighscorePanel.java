package view.paneler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.Scanner;
import java.io.*;

/**
 * HighscorePanel viser den høyeste poengsumen som er registrert.
 *              
 * @author	Trygve Johannessen 
 */
public class HighscorePanel extends HBox {
	private int highscore = 0; 
	private Label lbl = new Label("HIGH SCORE" + highscore);
	private File fil = new File("highscore.txt");
	
	public HighscorePanel(File fil) {
		lbl.setText("HIGH SCORE: " + lesInnHighscore()); 
		lbl.setTextFill(Color.WHITE);
		getChildren().add(lbl);
		setMinWidth(210);
	}
	
	/**
	* leser inn Highscore fra en tekstfil
	*/
	public int lesInnHighscore() {
		try {
			Scanner leser = new Scanner(fil);
			highscore = leser.nextInt();
			leser.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return highscore;
	}
	
	/**
	* oppdaterer highscore viss poengsum er større enn den allerede registrerte highscoren
	* 
	* @param poengsum 
	*/
	public void oppdaterHighscore(int poengsum) {
		if (poengsum > highscore) {
			highscore = poengsum;
			lbl.setText("HIGH SCORE: " + highscore); 
		}
	}

}
