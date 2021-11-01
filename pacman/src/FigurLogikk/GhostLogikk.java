package FigurLogikk;

/**
 * GhostLogikk inneholder informasjon om et spøkelse objekt.                 
 * <p>
 * Om spøkelset er spiselig, om det kan gå ut av huset, forigePos, startpos, gjemmestedPos
 * og om spøkelset er ambusher (dvs om det prøver å gå der det tror at pacman skal gå)
 * </p>
 * @author	Trygve Johannessen 
 */
public class GhostLogikk extends FigurLogikk {
	public boolean spiselig = false;
	public boolean fri = false;
	public int forigeX;
	public int forigeY;
	public int startX;
	public int startY;
	public int gjemmestedX;
	public int gjemmestedY;
	public boolean ambusher;
	
	public GhostLogikk(int startX, int startY, int gjemmestedX, int gjemmestedY, boolean ambusher) {
		this.startX = startX;
		this.startY = startY;
		this.gjemmestedX = gjemmestedX;
		this.gjemmestedY = gjemmestedY;
		this.ambusher = ambusher;
		plasser();
	}
	
	/**
	 * Plasserer spøkelset ut på brettet
	 */
	public void plasser() {
		x = startX;
		y = startY;
	}
	
	public boolean erSpiselig() {
		return spiselig;
	}
	
	public void setSpiselig() {
		spiselig = true;
	}
	
	public void setNormal() {
		spiselig = false;
	}
	
	public boolean erFri() {
		return fri;
	}

	public void setFri() {
		fri = true;
	}

	public int getForigeX() {
		return forigeX;
	}

	public void setForigeX(int forigeX) {
		this.forigeX = forigeX;
	}

	public int getForigeY() {
		return forigeY;
	}

	public void setForigeY(int forigeY) {
		this.forigeY = forigeY;
	}

	public int getStartX() {
		return startX;
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public int getStartY() {
		return startY;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public int getGjemmestedX() {
		return gjemmestedX;
	}

	public void setGjemmestedX(int gjemmestedX) {
		this.gjemmestedX = gjemmestedX;
	}

	public int getGjemmestedY() {
		return gjemmestedY;
	}

	public void setGjemmestedY(int gjemmestedY) {
		this.gjemmestedY = gjemmestedY;
	}

	public boolean erAmbusher() {
		return ambusher;
	}
}