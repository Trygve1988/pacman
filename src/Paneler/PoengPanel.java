package Paneler;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import java.util.Scanner;
import java.io.*;

/**
 * PoengPanel viser hvor mange poeng brukeren har.
 * <p>
 * Om sp�kelset er spiselig, om det kan g� ut av huset, forigePos, startpos, gjemmestedPos
 * og om sp�kelset er ambusher (dvs om det pr�ver � g� der det tror at pacman skal g�)
 * </p>            
 * @author	Trygve Johannessen 
 */
public class PoengPanel extends HBox {
	private int poengsum = 0; 
	private int siste�kning = 0; 
	private int ghostP = 200; 
	private int antMatSpist = 0; 
	private int[] fruktPoengTab = {0,100,200,300,500,1000,2000,5000};
	private int[] tiKTab = lagTiKTab();
	int tiKTabPos = 1;
	private Label lbl = new Label("1UP: " + poengsum);
	private File fil;

	public PoengPanel(File fil) {
		this.fil = fil;
		lbl.setTextFill(Color.WHITE);
		getChildren().add(lbl);
		setMinWidth(120);
	}
	
	/**
	* Lager en tabell for � holde styr p� poenggrenser som gir pacman et ekstra liv.
	* 
	* @return tiKTab inneholder 10 000, 20 000, 30 000 ... 
	*/
	private int[] lagTiKTab() {
		int[] tiKTab = new int[100];
		for (int i=1; i<tiKTab.length; i++) {
			tiKTab[i] = i*10000;
		}
		return tiKTab;
	}

	public int getPoengSum() {
		return poengsum;
	}
	
	public int getSiste�kning() {
		return siste�kning;
	}
	
	public int getAntMatSpist() {
		return antMatSpist;
	}

	public void spisMat(LivPanel livPanel) {
		�kPoeng(10, livPanel);
		antMatSpist++;
	}
	
	public void spisBooster(LivPanel livPanel) {
		�kPoeng(50, livPanel);
	}

	public void spisFrukt(int fruktNr, LivPanel livPanel) { 
		�kPoeng(fruktPoengTab[fruktNr], livPanel);
	}

	public void spisSp�kelse(LivPanel livPanel) {
		int poeng = ghostP;
		�kPoeng(poeng, livPanel);
		ghostP = ghostP*2;
	}
	
	public void �kPoeng(int poeng, LivPanel livPanel) {
		lbl.setText("1UP: " + (poengsum += poeng));
		siste�kning = poeng;
		if ( poengsum > tiKTab[tiKTabPos] ) {

			livPanel.f�EtLiv();
			tiKTabPos++;
		}
	}
	
	public void nullstillGhostP() {
		ghostP = 200; 
	}
	
	public void nullstillAntMatSpist() {
		antMatSpist = 0; 
	}
	
	/**
	* vis poengsumen er en ny highscore: Skriver poengsumen over den forige highscoren
	*/
	public void registrerHighscore() {
		try {
			Scanner leser = new Scanner(fil);
			//er poengsum h�yere enn highscore i tekstfilen
			int highscore = leser.nextInt();
			if (poengsum > highscore) {
				PrintWriter skriver = new PrintWriter(fil);
				skriver.print(poengsum);
				skriver.close();
			}
			leser.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
