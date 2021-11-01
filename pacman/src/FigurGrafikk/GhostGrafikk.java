package FigurGrafikk;
import javafx.scene.image.Image;

/**
 * Abstrakt Klasse som inneholder variabler og metoder som alle sp�kelsene deler. 
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
	 * setter set sp�kelse til spiselig. Da blir det m�rkebl�tt
	 */
	public void setSpiselig() {
		setImage(EDIBLE); 
		spiselig = true;
	}
	
	/**
	 * setter et sp�kelse til hvit. Det er for � vise at det snart blir normalt igjen.
	 */
	public void setHvit() {
		setImage(WHITE); 
	}
	
	/**
	 * setter et sp�kelse til normalt igjen.
	 */
	public void setNormal() {
		spiselig = true;
	}

}