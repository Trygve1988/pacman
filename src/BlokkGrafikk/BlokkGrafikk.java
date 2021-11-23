package BlokkGrafikk;
import javafx.scene.image.*;

/**
 * Alle grafiske elementer som ikke beveger seg arver fra denne klassen               
 * <p>
 * Blokkgrafikk er et imageview som får inn x og y pos og har fast bredde og høyde
 * </p>
 * @author	Trygve Johannessen 
 */
public abstract class BlokkGrafikk extends ImageView {
	protected static final int STR = 30;
	
	public BlokkGrafikk(int x, int y) {
		setX(x);
		setY(y);
		setFitWidth(STR);
		setFitHeight(STR);
	}
	
}
