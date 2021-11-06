import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.awt.Color;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Rectangle;
import gui.Simulable;

/**
 * C'est une classe qui se charge de la simulation
 * Classe fille de Simulable
 * @author Equipe
 * @version 1
 */
public class Simulateur implements Simulable{
	private Robot[] robots;
	private Carte carte;
	private Incendie[] incendies;
	private GUISimulator gui;
	private int tailleCase;
	private long dateSimulation;
	private List<Evenement> evenements = new ArrayList<Evenement>();
	private Map<Integer, List<Evenement>> evenementsDates = new HashMap<Integer, List<Evenement>>();
	private Strategie strategie;



	private Color couleurCarte = Color.decode("#22f128");

	/**
	 * Cree un nouveau objet Simulateur avec la dateSimulation, gui, donnees, tailleCase et la liste des evenements
	 * @param dateSimulation : La date de la simulation
	 * @param gui : L'interface graphique
	 * @param donnees : Toutes les donnees de notre simulations; La carte, la liste des incendies et la liste des robots
	 * @param tailleCase : la taille des cases 
	 * @param evenements : Les evenements a faire
	 * En plus On dessine toute la simulation 
	 */
	public Simulateur(long dateSimulation, GUISimulator gui, DonneesSimulation donnees, int tailleCase, List<Evenement> evenements) {
		setDateSimulation(dateSimulation);
		setGui(gui);
		gui.setSimulable(this);
		setCarte(donnees.getCarte());
		setRobots(donnees.getListeRobot());
		setIncendies(donnees.getListeIncendies());
		setEvenements(evenements);
		setTailleCase(tailleCase);
		draw();
	}
	

	/**
	 * Cree un nouveau objet Simulateur avec la dateSimulation, gui, donnees, tailleCase et la liste des evenements
	 * @param dateSimulation : La date de la simulation
	 * @param gui : L'interface graphique
	 * @param donnees : Toutes les donnees de notre simulations; La carte, la liste des incendies et la liste des robots
	 * @param tailleCase : la taille des cases 
	 * En plus On dessine toute la simulation 
	 */
	public Simulateur(long dateSimulation, GUISimulator gui, DonneesSimulation donnees, int tailleCase) {
		setDateSimulation(dateSimulation);
		setGui(gui);
		gui.setSimulable(this);
		setCarte(donnees.getCarte());
		setRobots(donnees.getListeRobot());
		setIncendies(donnees.getListeIncendies());
		setTailleCase(tailleCase);
		draw();
	}

	/**
	 * Cree un nouveau objet Simulateur avec la dateSimulation, gui, donnees, tailleCase et la liste des evenements
	 * @param dateSimulation : La date de la simulation
	 * @param gui : L'interface graphique
	 * @param donnees : Toutes les donnees de notre simulations; La carte, la liste des incendies et la liste des robots 
	 * @param evenements : Les evenements a faire
	 * En plus On dessine toute la simulation 
	 */
	public Simulateur(long dateSimulation, GUISimulator gui, DonneesSimulation donnees, List<Evenement> evenements) {
	setDateSimulation(dateSimulation);
	setGui(gui);
	gui.setSimulable(this);
	setCarte(donnees.getCarte());
	setRobots(donnees.getListeRobot());
	setIncendies(donnees.getListeIncendies());
	setEvenements(evenements);
	setTailleCase(gui,donnees.getCarte());
	draw();
	}
	
	public void setDonnees(DonneesSimulation donnees) {
		setCarte(donnees.getCarte());
		setRobots(donnees.getListeRobot());
		setIncendies(donnees.getListeIncendies());
		setTailleCase(gui,donnees.getCarte());
		for (Robot robot:donnees.getListeRobot()) {
			robot.setSimulateur(this);
		}
	}

	/**
	 * Cree un nouveau objet Simulateur avec la dateSimulation, gui, donnees, tailleCase et la liste des evenements
	 * @param dateSimulation : La date de la simulation
	 * @param gui : L'interface graphique
	 * @param donnees : Toutes les donnees de notre simulations; La carte, la liste des incendies et la liste des robots
	 * En plus On dessine toute la simulation 
	 */
	public Simulateur(long dateSimulation, GUISimulator gui, DonneesSimulation donnees) {
	setDateSimulation(dateSimulation);
	setGui(gui);
	gui.setSimulable(this);
	setDonnees(donnees);
	draw();
	}

	/**
	 * cette methode consiste a ajouter un nouveau evenement
	 * @param e : l'evenement a ajouter
	 */
	public void ajouteEvenement(Evenement e) {
		//evenements.add(e);
		addEvenementDate(e, e.getDate());
		
	}

	/**
	 * incremente la date de simulation par 1
	 */
	private void incrementeDate() {
		this.dateSimulation += 1;
	}

	@Override
	public void next() {
		draw();
		if (!simulationTerminee()) {
			incrementeDate();
			executionPas(getDateSimulation());
			if (getStrategie()!= null && getStrategie().getClass() == StrategieElementaire.class) {
				if (getDateSimulation()% 30== 10) {
					getStrategie().executionStrategie();
				}
			}
		}
	}

	@Override
	public void restart() {
		setDateSimulation(0);
	}

	/**
	 * 
	 * @param date
	 */
	private void executionPas(long date) {
		Integer dateActu = (int) Math.floor(date);
		if (getEvenementsDates().containsKey(dateActu)) {
			List<Evenement> evenementsAEexecuter = getEvenementsDates().get(dateActu);
			for (Evenement e:evenementsAEexecuter) {
				e.execute();
			}
		}
	}
	
	/**
	 * Cette methode consiste a dessiner la carte, les cases, les incendies et les robots
	 */
	public void draw() {
		draw(getCarte());
		for (Incendie incendie:getIncendies()) {
			draw(incendie);
		}
		for (Robot robot:getRobots()) {
			draw(robot);
		}
	}

	/**
	 * Dessine le robot
	 * @param robot : Le robot a dessiner
	 */
	public void draw(Robot robot) {
		Case position = robot.getPosition();
		String Chenille = "Chenille.png";
		String Roues = "Roues.png";
		String Pattes = "Pattes.png";
		String Drone = "Drone.png";
		if (robot.getNature().equals("CHENILLES")) {
			gui.addGraphicalElement(new ImageElement(position.getColonne()*tailleCase - tailleCase/2 + 50, position.getLigne()*tailleCase  - tailleCase/2 + 50, Chenille, 3*this.tailleCase/4, 3*this.tailleCase/4, gui));
		}
		else if (robot.getNature().equals("DRONE")) {
			gui.addGraphicalElement(new ImageElement(position.getColonne()*tailleCase - tailleCase/2 + 50, position.getLigne()*tailleCase - tailleCase/2 + 50, Drone, 3*this.tailleCase/4, 3*this.tailleCase/4, gui));
		}
		else if (robot.getNature().equals("ROUES")) {
			gui.addGraphicalElement(new ImageElement(position.getColonne()*tailleCase - tailleCase/2 + 50, position.getLigne()*tailleCase - tailleCase/2 + 50, Roues, 3*this.tailleCase/4, 3*this.tailleCase/4, gui));
		}
		else {
			gui.addGraphicalElement(new ImageElement(position.getColonne()*tailleCase - tailleCase/2 + 50, position.getLigne()*tailleCase - tailleCase/2 + 50, Pattes, 3*this.tailleCase/4, 3*this.tailleCase/4, gui));
		} 
    }

	/**
	 * Dessine la carte avec ses cases
	 * @param carte : la ccarte a dessiner
	 */
    private void draw(Carte carte) {
    	gui.reset();
    	Case[][] cases = getCarte().getCases();
	    for (int x=0; x < getCarte().getNbColonnes(); x++) {
	    	for (int y=0; y < getCarte().getNbLignes(); y++) {
	    		int X = x * tailleCase;
	    		int Y = y * tailleCase;
	    		if (cases[x][y].getNatureTerrain() == NatureTerrain.FORET) {
	    			// Forêt => Couleur verte
	    			rectangleDrawer(50+Y, 50+X, couleurCarte, new Color(0,102,0));
	    		}
	    		else if (cases[x][y].getNatureTerrain() == NatureTerrain.EAU) {
	    			// EAU => Couleur blue
	    			rectangleDrawer(50+Y, 50+X, couleurCarte, Color.blue);
	    		}
	    		else if (cases[x][y].getNatureTerrain() == NatureTerrain.ROCHE) {
	    			// Roche => Couleur Jaune
	    			rectangleDrawer(50+Y, 50+X, couleurCarte, new Color(255,204,0));
	    		}
	    		else if (cases[x][y].getNatureTerrain() == NatureTerrain.HABITAT) {
	    			// Habitat => Couleur marron
	    			rectangleDrawer(50+Y, 50+X, couleurCarte, new Color(153,102,0));
	    		}
	    		else {
	    			// Terrain libre => Couleur grise
	    			rectangleDrawer(50+Y, 50+X, couleurCarte, Color.DARK_GRAY);
	    		}
	    	}
	    }
}

    /**
     * Dessine l'incendie
     * @param incendie : l'incendie a dessiner
     */
	public void draw(Incendie incendie) {
		Case position = incendie.getPosition();
		if (incendie.getNbLitres() > 0) {
			gui.addGraphicalElement(new ImageElement(-tailleCase/2+position.getColonne()*tailleCase+50, -tailleCase/2+position.getLigne()*tailleCase+50, "Incendiee.png", this.tailleCase, this.tailleCase, gui) );
		}
	}

	/**
	 * Retourne un boolean determinant si la simulation est terminee ou non 
	 * @return true si la simulation est terminee, false sinon
	 */
	private boolean simulationTerminee() {
		Integer dateMaxi = Collections.max(getEvenementsDates().keySet());
		return getDateSimulation() == dateMaxi;
	}

	/**
	 * Cree un nouvel element rectangulaire de taille "tailleCase"
	 * @param x : l'abscisse 
	 * @param y : l'ordonnee
	 * @param couleur : la couleur de trace
	 * @param fillingColor : la couleur de remplissage
	 */
    public void rectangleDrawer(int x, int y, Color couleur, Color fillingColor) {
    	gui.addGraphicalElement(new Rectangle(x, y , couleur, fillingColor, tailleCase));
    }

    /**
     * Cree un nouvel element rectangulaire de taille "tailleCase" avec la couleur de trace et la couleur de remplissage sont les memes
     * @param x : l'abscisse
     * @param y : l'ordonnee
     * @param fillingColor : la couleur de remplissage
     */
    public void rectangleDrawer(int x, int y, Color fillingColor) {
    	gui.addGraphicalElement(new Rectangle(x, y , fillingColor, fillingColor, tailleCase));
    }

    /**
     * retourne la liste des evenements
     * @return la liste des evenements
     */
	public List<Evenement> getEvenements() {
		return evenements;
	}
	
	/**
	 * Set le liste des evenements
	 * @param evenements : la liste des evenementsa executer
	 */
	public void setEvenements(List<Evenement> evenements) {
		this.evenements = evenements;
	}
	
	/**
	 * retourne la date de simulation
	 * @return la date de simulation
	 */
	public long getDateSimulation() {
		return dateSimulation;
	}

	/**
	 * Set la date de simulation
	 * @param dateSimulation : la date de simulation 
	 */
	public void setDateSimulation(long dateSimulation) {
		this.dateSimulation = dateSimulation;
	}
	
	/**
	 * retourne les robots
	 * @return un tableau unidimensionnel des robots 
	 */
	public Robot[] getRobots() {
		return robots;
	}

	/**
	 * Set un tableau unidimensionnel des robots
	 * @param robots : les robots de notre simulation
	 */
	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

	/**
	 * Retourne la carte
	 * @return la carte de notre simulation
	 */
	public Carte getCarte() {
		return carte;
	}

	/**
	 * Set la carte 
	 * @param carte : la carte de notre simulation
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}

	/**
	 * retourne les incendies
	 * @return un tableau unidimensionnel des incendies de notre simulation
	 */
	public Incendie[] getIncendies() {
		return incendies;
	}

	/**
	 * Set les incendies de notre simulation
	 * @param incendies : un tableau unidimensionnel des incendies de notre simulation
	 */
	public void setIncendies(Incendie[] incendies) {
		this.incendies = incendies;
	}
	
	/**
	 * retourne gui
	 * @return gui
	 */
	public GUISimulator getGui() {
		return gui;
	}

	/**
	 * Set gui
	 * @param gui : l'interface graphique
	 */
	public void setGui(GUISimulator gui) {
		this.gui = gui;
	}
	
	/**
	 * retourne la taille des cases 
	 * @return la tailles des cases dans notre simulation
	 */
	public int getTailleCase() {
		return tailleCase;
	}
	
	/**
	 * Set la taille des cases
	 * @param tailleCase : un entier determinant la taille des cases
	 */
	public void setTailleCase(int tailleCase) {
		this.tailleCase = tailleCase;
	}
	
	/**
	 * Set la taille des cases 
	 * @param gui : l'interface graphique
	 * @param carte la carte de notre simulation
	 */
	public void setTailleCase(GUISimulator gui, Carte carte) {
		int height = (int) Math.floor(gui.getHeight()*0.9);
		int width = (int) Math.floor(gui.getWidth()*0.9);
		this.tailleCase = Math.min(height,width)/Math.max(carte.getNbLignes(),carte.getNbColonnes()) ;
	}
	
	/**
	 * retourne la strategie  
	 * @return la strategie
	 */
	public Strategie getStrategie() {
		return strategie;
	}

	/**
	 * Set la strategie 
	 * @param strategie : la strategie 
	 */
	public void setStrategie(Strategie strategie) {
		this.strategie = strategie;
	}

	/**
	 * Retourne un map des evenements et la date de chaque evenement
	 * @return un map des evenements et la date de chaque evenement
	 */
	public Map<Integer, List<Evenement>> getEvenementsDates() {
		return evenementsDates;
	}
	
	/**
	 * Set un map des evenements et la date de chaque evenement
	 * @param evenementsDates : un map des evenements et la date de chaque evenement
	 */
	public void setEvenementsDates(Map<Integer, List<Evenement>> evenementsDates) {
		this.evenementsDates = evenementsDates;
	}
	
	/**
	 * Ajoute au map EvenementDate un evenement et sa date correspondante
	 * @param evenement : L'evenement a ajouter
	 * @param date : la date de l'evenement
	 */
	public void addEvenementDate(Evenement evenement, double date) {
		Integer dateEvent = (int) Math.floor(date);
		if (!getEvenementsDates().containsKey(dateEvent)) {
			List<Evenement> evenements = new ArrayList<Evenement>();
			evenements.add(evenement);
			getEvenementsDates().put(dateEvent,  evenements);
		}else {
		getEvenementsDates().get(dateEvent).add(evenement);
		}
	}
}
