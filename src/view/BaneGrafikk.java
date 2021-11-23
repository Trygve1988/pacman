package view;
import javafx.scene.layout.Pane;
import view.blokkGrafikk.BoosterGrafikk;
import view.blokkGrafikk.FlisGrafikk;
import view.blokkGrafikk.FruktGrafikk;
import view.blokkGrafikk.MatGrafikk;
import view.blokkGrafikk.VeggGrafikk;

/**              
 * Banegrafikk er et grafisk panel som har vegger, fliser, mat og boostere.
 * Banegrafikken lages ut ifra ruter tabellen i banelogikken
 * 
 * @author	Trygve Johannessen 
 */
public class BaneGrafikk extends Pane {
	private final static int STR = 30;
	
	public BaneGrafikk(int ruter[][]) {
		// lager bane
		int y = 0, x = 0;
		for (int yi=0; yi<ruter[0].length; yi++) {
			for (int xi=0; xi<ruter.length; xi++) {
				switch (ruter[xi][yi]) {
					case 0:  	getChildren().addAll(new MatGrafikk(x,y));	 			break;
					case 1:  	getChildren().addAll(new VeggGrafikk(x,y,STR,STR));	 	break;
					case 2:  	getChildren().addAll(new FlisGrafikk(x,y));	 			break;
					//case 3:   spøkelserom
					case 4:	 	getChildren().addAll(new BoosterGrafikk(x,y)); 			break; 
					default: 	
				}
				x += STR;
			}
			x = 0;
			y += STR;
		}
	}
	
	/**
	 * Setter en rute til flis (dvs tom)
	 * 
	 * @param x rutens x-pos 
	 * @param y rutens y-pos 
	 */
	public void setTilFlis(int x, int y) {
		FlisGrafikk flis = new FlisGrafikk(x*STR,y*STR);
		getChildren().addAll(flis);
	}
	
	/**
	 * Setter en rute til frukt
	 * 
	 * @param x rutens x-pos 
	 * @param y rutens y-pos 
	 */
	public void setTilFrukt(int x, int y, int fruktNr) {
		FruktGrafikk frukt = new FruktGrafikk(x*STR, y*STR, fruktNr);
		getChildren().addAll(frukt);
	}

}
