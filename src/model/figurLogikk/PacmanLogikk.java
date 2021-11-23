package model.figurLogikk;

/**
 * PacmanLogikk inneholder informasjon om et pacman objektet.                 
 * <p>
 * nesteRetning er retningen som brukeren har trykket på med piltstene. Når pacman kommer til et kryss vil han ta til den retningen.
 * </p>
 * @author	Trygve Johannessen 
 */
public class PacmanLogikk extends FigurLogikk {
	String nesteRetning = "LEFT";

	public PacmanLogikk() {
		plasser();
	}
	
	/**
	 * Plasserer pacman ut på brettet
	 */
	public void plasser() {
		x = 13;
		y = 23;
	}
	
	public String getNesteRetning() {
		return nesteRetning;
	}

	public void setNesteRetning(String nesteRetning) {
		this.nesteRetning = nesteRetning;
	}


}
