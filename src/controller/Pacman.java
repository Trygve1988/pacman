package controller;

import javafx.application.*;
import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.*;
import lyd.Lyd;
import model.*;
import model.figurLogikk.*;
import view.*;
import view.FigurGrafikk.*;
import view.paneler.*;


import java.util.*;
import java.io.*;

/**
 * Dette er hovedklassen.                
 * <p>
 * Paneler, Timere, Logikk og grafikk for Banen og figurene  instansieres i denne klassen
 * </p>
 * @author	Trygve Johannessen 
 */
public class Pacman extends Application {

	// 1) Filer
	File baneTekstFil = new File("bane1.txt");
	File highscoreTekstFil = new File("highscore.txt");
	
	// 2) Bane
	BaneLogikk bl = new BaneLogikk(baneTekstFil);
	BaneGrafikk bg = new BaneGrafikk(bl.getRuter());
	
	// 3) Figurer
	PacmanGrafikk pg = new PacmanGrafikk();
	PacmanLogikk pl = new PacmanLogikk();
	ArrayList <GhostLogikk> ghostLogikker = new ArrayList <>();
	ArrayList <GhostGrafikk> ghostGrafikker = new ArrayList <>();
	ArrayList<Text> poengListe = new ArrayList<Text>();
	
	// 4) Paneler
	Pane lerret = new Pane(bg,pg);
	LivPanel livPanel = new LivPanel();
	PoengPanel poengPanel = new PoengPanel(highscoreTekstFil);
	HighscorePanel highscorePanel = new HighscorePanel(highscoreTekstFil);
	LydPanel lydPanel = new LydPanel();
	FruktPanel fruktPanel = new FruktPanel();
	HBox bunnPanel = new HBox(livPanel,poengPanel,highscorePanel,lydPanel,fruktPanel);
	VBox hovedPanel = new VBox(lerret,bunnPanel);
	
	// 5) Timere
	Timeline getReadyTimer,pacmanLogikkTimer,pacmanBildeAnismasjonTimer,pacmanGrafikkTimer,ghostLogikkTimer,ghostGrafikkTimer,sekundTimer;
	
	// 6) Variabler
	boolean saktemodus = false, jaktemodus = false; 
	int getReadyTeller = 0, saktTeller = 0, sekundTeller = 0, baneNr = 1, fruktNr = 1, SPEED = 7;  
	final static int STR = 30;
	
	/**
	 * Starter spillet       
	 * 
	 * Setter inn de siste variablene og endrer på paneler. 
	 * Spiller av lyd, starter timer, lager knappetrykk eventlistener, Lager scene og stage objekt.
	 * 
	 * @param stage 
	 */
	@Override
	public void start(Stage stage) {
		
		// 4) Paneler (endrer)
		ghostLogikker = initGhostLogikker();
		ghostGrafikker = initGhostGrafikker();
		lerret.setStyle("-fx-background-color: #000000;");
		bunnPanel.setStyle("-fx-background-color: #000000;");
		bunnPanel.setSpacing(20);
		
		// 7) action
		Lyd lyd = new Lyd();
		if (lydPanel.erLydPaa()) {
			lyd.spillKlipp(1);	
		}
		startGetReadyTimer();
		lerret.setOnKeyPressed(e -> knappeTrykk(e) );
		
		// 8) scene og stage
		Scene scene = new Scene(hovedPanel,840,960);
		scene.getStylesheets().add("mystyle.css");
		stage.setScene(scene); 
		stage.show();
		lerret.requestFocus();
	}
	
	// initialiserer ghosts
	/**
	* Initialiserer alle GhostLogikker
	* 
	* @return ghostLogikker ArrayList som inneholder alle ghostlogikkene 
	*/
	public ArrayList<GhostLogikk> initGhostLogikker() {
		ghostLogikker = new ArrayList <>();
		ghostLogikker.add(new GhostLogikk(11,13,1,1,false)); // red
		ghostLogikker.add(new GhostLogikk(11,15,1,30,true)); // pink
		ghostLogikker.add(new GhostLogikk(16,13,28,1,false)); // cyan
		ghostLogikker.add(new GhostLogikk(16,15,28,30,true)); // orange
		return ghostLogikker;
	}
	
	/**
	* Initialiserer alle GhostGrafikker
	* 
	* @return ghostGrafikker ArrayList som inneholder alle ghostGrafikkene 
	*/
	public ArrayList<GhostGrafikk> initGhostGrafikker() {
		ghostGrafikker = new ArrayList <>();
		GhostGrafikk[] ghostTab = {new GhostRedGrafikk(), new GhostPinkGrafikk(),new GhostCyanGrafikk(),new GhostOrangeGrafikk()};
		ghostGrafikker.clear();
		for (int i=0; i<ghostTab.length; i++) {
			GhostGrafikk gg = ghostTab[i];
			ghostGrafikker.add(gg);
			lerret.getChildren().add(gg);
		}
		return ghostGrafikker;
	}
	
	// getReadyTimer (starter spillet)
	/**
	* Starter getReadyTimer. Det er den første timeren som startes og eksisterer 
	* kun for å telle ned mens into musikken spilles av før spillet starter.
	*/
	public void startGetReadyTimer() {
		getReadyTimer = new Timeline(new KeyFrame(Duration.millis(720), e -> getReadyTimerTic()));
		getReadyTimer.setCycleCount(Timeline.INDEFINITE);
		getReadyTimer.play();
	}
	
	/**
	* Når denne metoden har blitt kaldt 5 ganger av getReadyTimeren 
	* startes pacman- og ghost-timerene. 
	*/
	public void getReadyTimerTic() {
		saktTeller++;
		if (saktTeller==1) { //5
			startSekundTimer();
			startPacmanTimere();
			startGhostTimere();
			getReadyTimer.stop();
		}
	}
	
	// sekundTimer (setter jaktmodus av/på, slipper ut spøkelsene)
	/**
	* Starter sekundtimeren. Sekundtimeren fyrer av sekunTimerTic metoden hvert sekund. 
	*/
	private void startSekundTimer() {
		sekundTimer = new Timeline(new KeyFrame(Duration.millis(1000), e -> sekundTimerTic()));
		sekundTimer.setCycleCount(Timeline.INDEFINITE);
		sekundTimer.play();
	}
	
	/**
	* Denne metoden kalles av sekundTimeren. 
	* Setter jaktmudus på spøkelsene av og på, og slipper spøkelsene ut en etter en
	*/
	private void sekundTimerTic() {
		// jaktemodus av/på
		sekundTeller++;
		if (sekundTeller==10 || sekundTeller==40 || sekundTeller==70 || sekundTeller==100) {  
			jaktemodus = true;
			System.out.println("jaktemodus");
		}
		if (sekundTeller==30 || sekundTeller==60 || sekundTeller==90) {
			jaktemodus = false;
			System.out.println("ikke jaktemodus");
		}
		// slipper ut spøkelsene
		if (sekundTeller==1) {
			ghostLogikker.get(0).setFri();	
		}
		if (sekundTeller==10) {
			ghostLogikker.get(1).setFri();	
		}
		if (sekundTeller==20) {
			ghostLogikker.get(2).setFri();	
		}
		if (sekundTeller==30) {
			ghostLogikker.get(3).setFri();	
		}
	}
	
	/**
	* Starter alle 3 pacmanTimerene.
	*/
	// pacman
	public void startPacmanTimere() {
		pacmanLogikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED*30), e -> pacmanLogikkTimerTic()));
		pacmanLogikkTimer.setCycleCount(Timeline.INDEFINITE);
		pacmanLogikkTimer.play();
		pacmanBildeAnismasjonTimer = new Timeline(new KeyFrame(Duration.millis(SPEED*15), e -> pacmanBildeAnismasjonTimerTic()));
		pacmanBildeAnismasjonTimer.setCycleCount(Timeline.INDEFINITE);
		pacmanBildeAnismasjonTimer.play();
		pacmanGrafikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED), e -> pacmanGrafikkTimerTic()));
		pacmanGrafikkTimer.setCycleCount(Timeline.INDEFINITE);
		pacmanGrafikkTimer.play();
	}
	
	/**
	* Hver gang denne medoden kalles flyttes pacman 30 px dvs en rute.
	*/
	private void pacmanLogikkTimerTic() {
		flyttPacmanLogikk(pl,pg);
	}
	
	/**
	* Kalles 2 ganger oftere en pacmanLogikkTimerTic. Endrer pacman bildet slik at munnen til pacman beveger seg.
	*/
	private void pacmanBildeAnismasjonTimerTic() {
		pg.bildeAnismasjon();
	}
	
	/**
	* Kalles 30 ganger oftere en pacmanLogikkTimerTic. Flytter pacman figuren 1 px. Følger etter pacman logikk objektet
	*/
	private void pacmanGrafikkTimerTic() {
		flyttGrafikk(pl,pg);
	}
	
	/**
	* Registrerer knappeTrykk fra bruker. Setter neste (ønsket) retning på pacman.
	* 
	* @param keyEvent inneholder informasjon om hvilken tast brukeren trykket på
	*/
	public void knappeTrykk(KeyEvent keyEvent) {
		pl.setNesteRetning(""+keyEvent.getCode());
	}
	
	/**
	* Flytter pacman objektet. 
	* 
	* Sjekker om pacman skal teleportere til den andre siden
	* Prøver å gå "neste retning".
	* Vist det var vegg til den retningen prøver pacman å gå "retning".
	* Kaller spis metoden.
	* 
	* 	@param pl PacmanLogikk
	* 	@param pg PacmanGrafikk
	*/
	private void flyttPacmanLogikk(PacmanLogikk pl, PacmanGrafikk pg) {
		//teleport til høyre?
		if (pl.getX() == 1 && pl.getY() == 14) {
			pl.setX(25);									
			pg.setX(26*STR);
		}
		//teleport til venstre?
		if (pl.getX() == 26 && pl.getY() == 14) {
			pl.setX(2);									
			pg.setX(1*STR);
		}
		// a) prøver å gå neste retning
		int x = 0, y = 0; 
		switch (pl.getNesteRetning()) {
			case "LEFT": 	x--;	break; 
			case "RIGHT": 	x++;	break; 
			case "UP":  	y--; 	break; 
			case "DOWN": 	y++; 	break; 
			default: 
		}
		if (!erVegg(x,y,pl)) {
			pl.setX(pl.getX()+x);
			pl.setY(pl.getY()+y);
			pl.setRetning(pl.getNesteRetning());
			pg.setRetning(pl.getNesteRetning());
		}
		else { // b) prøver å gå retning
			y = 0; 
			x = 0; 
			switch (pl.getRetning()) {
				case "LEFT": 	x = -1;		break;
				case "RIGHT":   x =  1; 	break; 
				case "UP":  	y = -1; 	break;  
				case "DOWN": 	y =  1; 	break; 
				default: 
			}
			if (erVegg(x, y, pl) == false) {				// viss ikke vegg ...
				pl.setX(pl.getX()+x);						// flytt pacman
				pl.setY(pl.getY()+y);
			}
		}	
		spis();
	}
		
	/**
	* Starter de 2 ghostTimerene i normal modus.
	*/
	public void startGhostTimere() {
		if (ghostLogikkTimer != null && ghostLogikkTimer.getStatus() == Animation.Status.RUNNING) {
			ghostLogikkTimer.stop();
			ghostGrafikkTimer.stop();
		}
		ghostLogikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED*30), e -> ghostLogikkTimerTic()));
		ghostLogikkTimer.setCycleCount(Timeline.INDEFINITE);
		ghostLogikkTimer.play();
		ghostGrafikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED), e -> ghostGrafikkTimerTic()));
		ghostGrafikkTimer.setCycleCount(Timeline.INDEFINITE);
		ghostGrafikkTimer.play();
		saktemodus = false;
		saktTeller = 0;
	}
	
	/**
	* Starter de 2 ghostTimerene i sakte modus (når spekelsene er spiselige).
	*/
	public void startGhostTimereSakte() {
		ghostLogikkTimer.stop();
		ghostLogikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED*60), e -> ghostLogikkTimerTic()));
		ghostLogikkTimer.setCycleCount(Timeline.INDEFINITE);
		ghostLogikkTimer.play();
		ghostGrafikkTimer.stop();
		ghostGrafikkTimer = new Timeline(new KeyFrame(Duration.millis(SPEED*2), e -> ghostGrafikkTimerTic()));
		ghostGrafikkTimer.setCycleCount(Timeline.INDEFINITE);
		ghostGrafikkTimer.play();
		saktemodus = true;
		saktTeller = 0;
	}
	
	/**
	* Hver gang denne medoden kalles flyttes alle spøkelsene 30 px dvs en rute.
	* Vis spøkelsene er i saktemodus økes saktetelleren med 1.
	* Saktetelleren styrer når spøkelsene skal blike og når de skal settes tilbake til normal modus.
	*/
	private void ghostLogikkTimerTic() {
		// saktemodus ?
		if (saktemodus) {
			saktTeller++;
			if (saktTeller==11) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setHvit();	
				}	
			}
			if (saktTeller==12) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setSpiselig();	
				}	
			}
			if (saktTeller==14) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setHvit();	
				}	
			}
			if (saktTeller==15) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setSpiselig();	
				}	
			}
			if (saktTeller==17) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setHvit();	
				}	
			}
			if (saktTeller==18) {
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setSpiselig();	
				}	
			}
			if (saktTeller==20) {
				// normal modus igjen
				poengPanel.nullstillGhostP();
				startGhostTimere();
				for (int i=0; i<ghostLogikker.size(); i++) {
					ghostLogikker.get(i).setNormal();	
				}
				for (int i=0; i<ghostGrafikker.size(); i++) {
					ghostGrafikker.get(i).setNormal();	
				}
				for (int i=0; i<poengListe.size(); i++) {
					lerret.getChildren().remove(poengListe.get(i));	
				}
				poengListe.clear();
			}
		}
		for (int i=0; i<ghostLogikker.size(); i++) {
			flyttGhostLogikk(ghostLogikker.get(i), ghostGrafikker.get(i));	
		}
	}
	
	/**
	* Hver gang denne medoden kalles flyttes alle spøkelsene 30 px dvs en rute.
	* Vis spøkelsene er i saktemodus økes saktetelleren med 1.
	* Sakte-telleren styrer når spøkelsene skal blinke og når de skal settes tilbake til normal modus.
	* 
	* 	@param gl GhostLogikk
	* 	@param gg GhostGrafikk
	*/
	private void flyttGhostLogikk(GhostLogikk gl, GhostGrafikk gg) {
		int maalX = 0;
		int maalY = 0;
		//teleport til høyre?
		if (gl.getX() == 1 && gl.getY() == 14) {
			gl.setX(25);									
			gg.setX(26*STR);
		}
		//teleport til venstre?
		if (gl.getX() == 26 && gl.getY() == 14) {
			gl.setX(2);									
			gg.setX(1*STR);
		}
		//er i spøkelserommet?
		if (bl.getRuter()[gl.getX()][gl.getY()] == 3) { 
			maalX = 11;
			maalY = 1;
			// erSpiselig || ikke fri?
			if (gl.erSpiselig() || !gl.erFri()) {
				maalX = gl.getStartX(); //da blir jeg i spøkelserommet
				maalY = gl.getStartY();
			}
		}
		else {
			//jaktmodus?
			if (jaktemodus == true || !gl.erSpiselig()) { //ta pacman
				if (gl.erAmbusher()) {
					int x = 0, y = 0;
					switch (pl.getRetning()) {
						case "LEFT": 	x = -4;		break;
						case "RIGHT":   x =  4; 	break; 
						case "UP":  	y = -4; 	break;  
						case "DOWN": 	y =  4; 	break; 
						default: 
					}
					maalX = pl.getX() + x;
					maalY = pl.getY() + y;
				}
				else {
					maalX = pl.getX();
					maalY = pl.getY();
				}
			}
			else { 
				maalX = gl.getGjemmestedX(); //til hjemmestedet
				maalY = gl.getGjemmestedY();
			}
		}
		double dVisHøyre = 1000;
		double dVisNed = 1000;
		double dVisVenstre = 1000;
		double dVisOpp = 1000;
		// 1) kan vi gå til høyre
		if (!erVegg(1,0,gl) && !erForigeRute(1,0,gl)) {
			dVisHøyre = distanseTilMaalet(1,0,gl,maalX,maalY);
		}
		// 2) kan vi gå nedover
		if (!erVegg(0,1,gl) && !erForigeRute(0,1,gl)) {
			dVisNed = distanseTilMaalet(0,1,gl,maalX,maalY);
		}
		// 3) kan vi gå til venstre
		if (!erVegg(-1,0,gl) && !erForigeRute(-1,0,gl)) {
			dVisVenstre = distanseTilMaalet(-1,0,gl,maalX,maalY);
		}
		// 4) kan vi gå opp
		if (!erVegg(0,-1,gl) && !erForigeRute(0,-1,gl)) {
			dVisOpp = distanseTilMaalet(0,-1,gl,maalX,maalY);
		}
		gl.setForigeX(gl.getX());
		gl.setForigeY(gl.getY());
		
		//flytt
		if (dVisHøyre<=dVisNed && dVisHøyre<=dVisVenstre && dVisHøyre<=dVisOpp) {
			gl.setX(gl.getX()+1);
			gl.setRetning("RIGHT");
		}
		else if (dVisNed<=dVisHøyre && dVisNed<=dVisVenstre && dVisNed<=dVisOpp) {
			gl.setY(gl.getY()+1);
			gl.setRetning("DOWN");
		}
		else if (dVisVenstre<=dVisHøyre && dVisVenstre<=dVisOpp && dVisVenstre<=dVisNed) {
			gl.setX(gl.getX()-1);
			gl.setRetning("LEFT");
		}
		else if (dVisOpp<=dVisHøyre && dVisOpp<=dVisVenstre && dVisOpp<=dVisNed) {
			gl.setY(gl.getY()-1);
			gl.setRetning("UP");
		}
		//animasjon
		if (((GhostLogikk) gl).erSpiselig() == false) {
			gg.bildeAnismasjon();
		}
		gg.setRetning(gl.getRetning());
	}
	
	/**
	* Telle hvor mange steg et spøkelse er fra målet (horisontalt + vertikalt).
	* Kalles fra flyttGhostLogikk-metoden.
	* Målet kan være pacman, 4 stef foran pacman eller et spøkelses hjemmested
	* 
	* @param gl GhostLogikk
	* @param gg GhostGrafikk
	* @return distanseTilMaalet 
	*/
	private double distanseTilMaalet(int x, int y, GhostLogikk gl, int maalX, int maalY) {		
		double testX = gl.getX()+x;
		double distanseX = testX - maalX; //pl.getX();
		distanseX = Math.abs(distanseX);
		double testY = gl.getY()+y;
		double distanseY = testY - maalY; //pl.getY();
		distanseY = Math.abs(distanseY);
		return distanseX+distanseY;
	}
	
	/**
	* Spøkelser må sjekke om ruter er den ruten som spøeklset nettop gikk på. (spøkelser kan aldri snu 180 grader)
	* Kalles fra flyttGhostLogikk-metoden.
	* 
	* @param x et spøkelse sin x posisjon i rutenettet
	* @param y et spøkelse sin y posisjon i rutenettet
	* @return erForigeRute boolean
	*/
	private boolean erForigeRute(int x, int y, GhostLogikk gl) {
		int testX = gl.getX()+x;
		int testY = gl.getY()+y;
		return (testX==gl.getForigeX() && testY==gl.getForigeY());
	}
	
	/**
	* Kalles 30 ganger oftere en ghostLogikkTimerTic. Flytter ghost figurenene 1 px. Følger etter tilsvarende logikk objekteter
	*/
	private void ghostGrafikkTimerTic() {
		for (int i=0; i<ghostGrafikker.size(); i++) {
			flyttGrafikk(ghostLogikker.get(i), ghostGrafikker.get(i));	
		}
	}
	
	/**
	* Brukes for å flytte figur objekter (pacman og spøkelser)
	* @param fl FigurLogikk
	* @param fg FigurGrafikk
	*/
	private void flyttGrafikk(FigurLogikk fl, FigurGrafikk fg) {
		// følger etter tilsvarende figur logikk
		if (fl.getX() > fg.getX()/STR) {
			fg.setX(fg.getX()+1);
		}
		else if (fl.getX() < fg.getX()/STR) {
			fg.setX(fg.getX()-1);
		}
		else if (fl.getY() > fg.getY()/STR) {
			fg.setY(fg.getY()+1);
		}
		else if (fl.getY() < fg.getY()/STR) {
			fg.setY(fg.getY()-1);
		}
		sjekkOmKrasj();
	}
	
	// 6) spis, erKryss, erVegg
	/**
	* Sjekker om den ruten som pacman befinner seg på inneholder 
	* noe som pacman kan spise (mat,booster,frukt).
	*/
	public void spis() {
		// er det mat her?
		if (bl.getRuter()[pl.getX()][pl.getY()] == 0) {
			poengPanel.spisMat(livPanel);
			highscorePanel.oppdaterHighscore(poengPanel.getPoengSum());
			bg.setTilFlis(pl.getX(), pl.getY()); //kode 3
			bl.setTilFlis(pl.getX(), pl.getY());
			// plasser frukt?
			if ((poengPanel.getAntMatSpist()==70 || poengPanel.getAntMatSpist()==170) && !fruktPanel.erFruktPaaBanen()) {
				bl.setTilFrukt(14, 17); 			//kode 5	
				bg.setTilFrukt(14, 17, fruktNr); 
				fruktPanel.setFruktPaaBanen(true); 
			}
			sjekkOmVunnetBane();
		}
		// er det booster her?
		else if (bl.getRuter()[pl.getX()][pl.getY()] == 4) {
			poengPanel.spisBooster(livPanel);
			highscorePanel.oppdaterHighscore(poengPanel.getPoengSum());
			bg.setTilFlis(pl.getX(), pl.getY()); //kode 3
			bl.setTilFlis(pl.getX(), pl.getY());
			for (int i=0; i<ghostLogikker.size(); i++) {
				ghostLogikker.get(i).setSpiselig();	
			}
			for (int i=0; i<ghostGrafikker.size(); i++) {
				ghostGrafikker.get(i).setSpiselig();	
			}
			startGhostTimereSakte();
		}
		// er det frukt her?
		if (fruktPanel.erFruktPaaBanen() && bl.getRuter()[pl.getX()][pl.getY()] == 5) {
			poengPanel.spisFrukt(fruktNr,livPanel);
			highscorePanel.oppdaterHighscore(poengPanel.getPoengSum());
			Text peongtext = new PoengText(pl.getX()*STR,  pl.getY()*STR+20,""+ poengPanel.getSisteØkning());
			peongtext.setFill(Color.WHITE);
			lerret.getChildren().addAll(peongtext);
			poengListe.add(peongtext); 
			bg.setTilFlis(pl.getX(), pl.getY()); //kode 3
			bl.setTilFlis(pl.getX(), pl.getX());	
			fruktPanel.setFruktPaaBanen(false);
			if (lydPanel.erLydPaa()) {
				Lyd lyd = new Lyd();
				lyd.spillKlipp(4);	
			}
		}
	}
	
	// 8) vegg
	/**
	* Sjekker om ruten som en figur vil flytte til er en vegg
	* 
	* @param x Xposisjonen som skal sjekkes
	* @param y Yposisjonen som skal sjekkes
	* @param fl figuren som vil flytte
	* @return vegg boolean verdi
	*/
	public boolean erVegg(int x, int y, FigurLogikk fl) {
		boolean vegg = false;
		if (bl.getRuter()[fl.getX()+x][fl.getY()+y] == 1) {
			vegg = true;
		}
		// spøkelser har bare lov til å gå UT av spøkelserommet
		if (fl instanceof GhostLogikk) {
			if ( (fl.getX()==13 && fl.getY()==11) || (fl.getX()==14 && fl.getY()==11) && bl.getRuter()[fl.getX()+x][fl.getY()+y] == 3) {
				vegg = true;
			}
		}
		// pacman har ikke lov å gå inn til spøkelsene
		if (fl instanceof PacmanLogikk) {
			if (bl.getRuter()[fl.getX()+x][fl.getY()+y] == 3) {
				vegg = true;
			}
		}
		return vegg;
	}
	
	/**
	* Sjekker om pacman befinner seg på samme rute som et av spøkelsene.
	* Viss spøkelsene er i nomral modus blir pacman spist.
	* Viss spøkelsene er i spiselig modus blir spøkelset spist.
	*/
	public void sjekkOmKrasj() {
		for (int i=0; i<ghostLogikker.size(); i++) {
			if (ghostLogikker.get(i).getX() == pl.getX() && ghostLogikker.get(i).getY() == pl.getY()) {
				if (ghostLogikker.get(i).erSpiselig() == true) { //spiselige
					poengPanel.spisSpøkelse(livPanel);
					highscorePanel.oppdaterHighscore(poengPanel.getPoengSum());
					Text peongtext = new PoengText(pl.getX()*STR,pl.getY()*STR+10,""+ poengPanel.getSisteØkning());
					peongtext.setFill(Color.WHITE);
					lerret.getChildren().addAll(peongtext);
					poengListe.add(peongtext);
					ghostLogikker.get(i).plasser();
					ghostGrafikker.get(i).plasser();
					if (lydPanel.erLydPaa()) {
						Lyd lyd = new Lyd();
						lyd.spillKlipp(3);
					}
				}
				else {	//ikke spiselige
					livPanel.mistEtLiv();
					pg.plasser();
					pl.plasser();
					for (int j=0; j<ghostLogikker.size(); j++) {
						ghostLogikker.get(j).plasser();
						ghostGrafikker.get(j).plasser();
					}
					if (lydPanel.erLydPaa()) {
						Lyd lyd = new Lyd();
						lyd.spillKlipp(5);	
					}
					sjekkOmTap();
				}
			}
		}
	}
	
	/**
	* Sjekker om pacman har spsit alle 242 mat objektene
	* Da går vi til neste bane
	*/
	private void sjekkOmVunnetBane() {
		if (poengPanel.getAntMatSpist() == 242) {
			jaktemodus = false; 
			sekundTeller = 0;
			bl = new BaneLogikk(baneTekstFil);
			bg = new BaneGrafikk(bl.getRuter());
			pg.plasser();
			pl.plasser();
			lerret.getChildren().clear();
			lerret.getChildren().addAll(bg,pg);
			ghostLogikker = initGhostLogikker();
			ghostGrafikker = initGhostGrafikker();
			poengPanel.nullstillAntMatSpist();
			baneNr++;
			if (baneNr<13) {
				if (baneNr==2 || baneNr % 2 == 1)  {
					fruktNr++;
				}
			}
			fruktPanel.nesteFrukt(fruktNr);
			fruktPanel.setFruktPaaBanen(false);
		}
	}
	
	/**
	* Sjekker om pacman har flere liv igjen. Visst ikke stopper spillet.
	* Kalles når pacman mister et liv.
	*/
	private void sjekkOmTap() {
		if (livPanel.getLiv() == -1) {
			poengPanel.registrerHighscore();
			lerret.getChildren().remove(pg);
			Text gameOverTxt = new Text(360,530,"GAME OVER");
			gameOverTxt.setFill(Color.YELLOW);
			lerret.getChildren().add(gameOverTxt);
			pacmanLogikkTimer.stop();
			pacmanBildeAnismasjonTimer.stop();
			pacmanGrafikkTimer.stop();
			ghostLogikkTimer.stop();
			ghostGrafikkTimer.stop();
		}
	}
}