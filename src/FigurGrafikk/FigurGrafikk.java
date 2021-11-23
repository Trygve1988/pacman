package FigurGrafikk;
import javafx.scene.image.ImageView;

/**
 * Alle grafiske figurer som beveger seg arver fra denne klassen                 
 * <p>
 * FigurGrafikk er et imageview som får inn x og y pos og har fast bredde og høyde
 * </p>
 * @author	Trygve Johannessen 
 */
public abstract class FigurGrafikk extends ImageView {
	protected static final int STR = 30;
	protected String retning = "LEFT";

	public FigurGrafikk(int x, int y) {
		setX(x);
		setY(y);
		setFitWidth(STR);
		setFitHeight(STR);
	}
	
	public String getRetning() {
		return retning;
	}
	
	public void setRetning(String retning) {
		this.retning = retning;
	}
	
	public abstract void plasser();
	
	public abstract void bildeAnismasjon();
	
}