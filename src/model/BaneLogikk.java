package model;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**              
 * Denne klassen inneholder logisk informasjon om brettet.
 * ruter er en 2-dimensjonal tabell som inneholder tall som representerer objekter på banen
 * 
 * @author	Trygve Johannessen 
 */
public class BaneLogikk {
	private final static int RUTER_X = 28; 	//ruter bortover
	private final static int RUTER_Y = 31;	//ruter nedover
	private int[][] ruter = new int[RUTER_X][RUTER_Y];    // todimensjonal int tabell med 0er og 1ere
	
	public BaneLogikk(File fil) {
		// må lage int tabellen ruter ut ifra tekst filen
		fil = new File("bane1.txt"); 
		try {
			Scanner leser = new Scanner(fil);
			for (int y=0; y<ruter[0].length; y++) { //y
				for (int x=0; x<ruter.length; x++) { //x
					int tall = leser.nextInt();
					ruter[x][y] = tall;
				}
			}
			leser.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public int[][] getRuter() {
		return ruter;
	}
	
	/**
	 * Setter en rute til flis (dvs tom)
	 * 
	 * @param x rutens x-pos 
	 * @param y rutens y-pos 
	 */
	public void setTilFlis(int x, int y) {
		ruter[x][y] = 2;
	}
	
	/**
	 * Setter en rute til frukt
	 * 
	 * @param x rutens x-pos 
	 * @param y rutens y-pos 
	 */
	public void setTilFrukt(int x, int y) {
		ruter[x][y] = 5;
	}
	
}
