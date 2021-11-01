package FigurLogikk;

/**
 * Alle figurerlogikker arver fra denne klassen                 
 * <p>
 * Den inneholder retningen som figuren går og x og y posisjonen til ruten som figuren befinner seg på.
 * </p>
 * @author	Trygve Johannessen 
 */
public abstract class FigurLogikk {
	public String retning = "LEFT";
	public int x;
	public int y;
	
	public String getRetning() {
		return retning;
	}

	public void setRetning(String retning) {
		this.retning = retning;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public abstract void plasser();

}
