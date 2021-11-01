package FigurGrafikk;
import javafx.scene.image.Image;

/**
 * Abstrakt Klasse som inneholder variabler og metoder som alle spøkelsene deler. 
 * 
 * @author	Trygve Johannessen 
 */
public abstract class GhostGrafikk extends FigurGrafikk {
	protected final static Image EDIBLE  = new Image("Bilder/edibleGhost.gif");
	protected final static Image WHITE = new Image("Bilder/whiteGhost.jpg");
	protected boolean spiselig = false;
	
	public GhostGrafikk(int x, int y) {
		super(x, y);
	}
	
	public abstract void plasser();
	
	public abstract void bildeAnismasjon();
	
	/**
	 * setter set spøkelse til spiselig. Da blir det mørkeblått
	 */
	public void setSpiselig() {
		setImage(EDIBLE); 
		spiselig = true;
	}
	
	/**
	 * setter et spøkelse til hvit. Det er for å vise at det snart blir normalt igjen.
	 */
	public void setHvit() {
		setImage(WHITE); 
	}
	
	/**
	 * setter et spøkelse til normalt igjen.
	 */
	public void setNormal() {
		spiselig = true;
	}

}