import javax.sound.sampled.*;

/**              
 * Lyd-klassen spiller av lyd i spillet
 * 
 * @author	Trygve Johannessen 
 */
public class Lyd {
	private Clip PacBeginning  = hentKlipp("/Sounds/pacman_beginning.wav");
	private Clip PacChomp      = hentKlipp("/Sounds/pacman_chomp.wav");
	private Clip PacGhostEat   = hentKlipp("/Sounds/pacman_eatghost.wav");
	private Clip PacFruitEat   = hentKlipp("/Sounds/pacman_eatfruit.wav");
	private Clip PacDeath      = hentKlipp("/Sounds/pacman_death.wav");

	public Lyd() {	
	}
	
	/**
	 * Initialiserer alle lydklippene
	 * 
	 * @param filnavn 
	 */
	public Clip hentKlipp(String filnavn) {
		Clip clip = null;
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(filnavn));
			clip = AudioSystem.getClip();
			clip.open( audioIn );
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return(clip);
	}
	
	/**
	 * Spiller av et lydklipp.
	 * 
	 * @param index   
	 */
    public void spillKlipp(int index) {
		switch (index) {
			case 1: 	PacBeginning.start();	break;
			case 2: 	PacChomp.start();    	break; 
			case 3:  	PacGhostEat.start();	break; 
			case 4:  	PacFruitEat.start();	break; 
			case 5: 	PacDeath.start();		break; 
			default: 
		}
	}	
}
