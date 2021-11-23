package view;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
/**              
 * PoengText er tekst objekter som plaseres ute p� banen n�r pacman spiser sp�kelser og frukt
 * 
 * @author	Trygve Johannessen 
 */
public class PoengText extends Text {
	
	public PoengText(int x, int y, String str) {
		super(x,y,str);
		setFill(Color.WHITE);
	}

}


