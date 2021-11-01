package BlokkGrafikk;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Grafiske representasjon av en firkantet vegg
 *          
 * @author	Trygve Johannessen 
 */
public class VeggGrafikk extends Rectangle{

	public VeggGrafikk(int x, int y, int b, int h) {
		super(x,y,b,h);
		setFill(Color.BLUE);
		setStroke(Color.BLACK);
	}

}
