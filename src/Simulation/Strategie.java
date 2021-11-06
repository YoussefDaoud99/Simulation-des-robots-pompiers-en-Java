import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

/**
 * Cette classe permet de manipuler la strategie a suivre pour eteindre les incendies
 * @author Equipe
 * @version 1
 */
public abstract class Strategie {
	
	private DonneesSimulation donnees;
	private Simulateur simulateur;
	

	private double pasSimulation = 60;
	

	/**
	 * Initialise la strategie par les donnenes et Simulateur
	 * @param donnees : Les donnees de la simulation; regroupant la carte, les cases, les robots et les incendies
	 * @param simulateur : Le simulateur
	 */
	public Strategie(DonneesSimulation donnees, Simulateur simulateur) {
		setDonnees(donnees);
		setSimulateur(simulateur);
		simulateur.setStrategie(this);
	}

	/**
	 * Retourne les donnees de la simulation
	 * @return les donnees de la simulation
	 */
	public DonneesSimulation getDonnees() {
		return donnees;
	}
	
	/**
	 * Set les donnees de la simulation
	 * @param donnees : les donnees de la simulation
	 */
	public void setDonnees(DonneesSimulation donnees) {
		this.donnees = donnees;	
	}
	
	/**
	 * Cette methode consiste a deplacer le robot a partir de sa position a la case de destination
	 * dans cette methode on fait appel au plus petit cours chemin pour arriver a la cse de destination
	 * @param destination : La case de destination
	 * @param robot :  Le robot a deplacer
	 * @param simulateur : le simulateur
	 * @return la date d arrivee a la case destination
	 */
	 public double deplacerRobot(Case destination, Robot robot, Simulateur simulateur) {
		Carte carte = getDonnees().getCarte();
		Graphe graphe = new Graphe(carte, robot);
		Case depart = robot.getPosition();
		robot.setOccupe(true);
		Case[] chemin = graphe.pcc(depart, destination);
		List<Evenement> events = deplacementRobot(Arrays.asList(chemin), robot, simulateur.getDateSimulation(), carte);
		ajoutEvents(events, simulateur);
		double dateArrivee = events.get(events.size()-1).getDate();
		return dateArrivee;
	}

	/**
	 * Cette methode retourne une liste d'evenements de deplacement du robot 
	 * Un evenement de deplacement contient la date de l'evenement, le robot et le statut du robot (occupee ou non)
	 * @param Chemin : Les cases a traverser par le robot
	 * @param robot : Le robot qui se deplace
	 * @param DateDepart : la date du premier evenement
	 * @param carte : la carte de notre simulation
	 * @return la liste des evenements de deplacement par le robot
	 */
	public List<Evenement> deplacementRobot(List<Case> Chemin, Robot robot, double DateDepart, Carte carte){
		List<Evenement> EvenementsDeplacement = new ArrayList<Evenement>();
		Iterator<Case> IterChemin = Chemin.iterator();
		Case CaseCourante = IterChemin.next();
		double dateActu = DateDepart;
		EvenementsDeplacement.add(new EvenementRobot(dateActu+1, robot, "occuper"));
		while (IterChemin.hasNext()) {
			Case CaseSuivante = IterChemin.next();
			Direction direction = carte.TrouveDirection(CaseCourante, CaseSuivante);
			dateActu += robot.coutDeplacementElem(CaseCourante, CaseSuivante)/pasSimulation;
			EvenementsDeplacement.add(new EvenementRobot(dateActu, robot, "deplacer", direction));
			CaseCourante = CaseSuivante;
		}
		return EvenementsDeplacement;
	}
	
	/**
	 * cette methode consiste a programmer les evenements d'intervention de robot pour le plus proche incendie non affecte
	 * @param robot : le robot qui va intervenir
	 */
	public void interventionPlusProcheIncendieNonAffecte(Robot robot) {
		Incendie incendieCible = plusProcheIncendieNonAffecte(robot, getDonnees());
		if (incendieCible != null) {
			this.intervention(robot, incendieCible, getSimulateur());
		}
	}
	
	/**
	 * cette methode consiste a programmer les evenements d'intervention de robot pour le plus proche incendie
	 * @param robot : le robot qui va intervenir
	 */
	public void interventionPlusProcheIncendie(Robot robot) {
		Incendie incendieCible = plusProcheIncendie(robot, getDonnees());
		if (incendieCible != null) {
			this.intervention(robot, incendieCible, getSimulateur());
		}
	}

	/**
	 * cette methode consiste a programmer les evenements d'intervention de robot
	 * @param robot : le robot qui va intervenir
	 * @param incendie : l'incendie qu'on cherche a eteindre
	 * @param simulateur : le simulateur
	 */
	public void intervention(Robot robot, Incendie incendie, Simulateur simulateur) {
		double dateActuelle = deplacerRobot(incendie.getPosition(), robot, simulateur);
		double[] interventionUnitaire = robot.interventionUnitaire();
		double nbInterventionsUnitaires = 0;
		if (robot.getNature().equals("PATTES")) {
			nbInterventionsUnitaires =  incendie.getNbLitres()/interventionUnitaire[0];
		}
		else {
			nbInterventionsUnitaires =  robot.getReservoir()/interventionUnitaire[0];
		}
		double intervalleEntreInterventions = interventionUnitaire[1]/pasSimulation;
		getSimulateur().ajouteEvenement(new EvenementIncendie(dateActuelle, incendie, robot, "affecter"));
		for (int i = 0; i < nbInterventionsUnitaires; i++) {
			getSimulateur().ajouteEvenement(new EvenementIncendie(dateActuelle, incendie, robot));
			dateActuelle += intervalleEntreInterventions; 
		}
		getSimulateur().ajouteEvenement(new EvenementRobot(dateActuelle, robot, "liberer"));
		getSimulateur().ajouteEvenement(new EvenementIncendie(dateActuelle, incendie, robot, "desaffecter"));
	}
	
	/**
	 * On parcours la liste des robots et on les recharge
	 * @param robotsARecharger : les robots a recharger
	 * @param simulateur : le simulateur
	 */
	public void rechargerRobots(List<Robot> robotsARecharger, Simulateur simulateur) {
		for(Robot robot:robotsARecharger) {
			rechargerRobot(robot, simulateur);
		}
	}

	/**
	 * On parcours tous les robots et on charge leur reservoir
	 * @param simulateur : le simulateur
	 */
	public void rechargerRobots(Simulateur simulateur) {
		for (Robot robot:getDonnees().getListeRobot()) {
			rechargerRobot(robot, simulateur);
		}
	}

	/**
	 * On remplit le reservoir a la plus proche case contenant de l'eau
	 * @param robot : le robot a recharger
	 * @param simulateur : le simulateur
	 */
	public void rechargerRobot(Robot robot, Simulateur simulateur) {
		Carte carte = simulateur.getCarte();
		Case plusProcheRecharge = plusProcheRecharge(robot, carte);
		double dateArrivee = deplacerRobot(plusProcheRecharge, robot, simulateur);
		double tempsRemplissage = robot.dureeRemplissageComplet()/pasSimulation;
		getSimulateur().ajouteEvenement(new EvenementRobot(dateArrivee+tempsRemplissage, robot, "remplir"));
		getSimulateur().ajouteEvenement(new EvenementRobot(dateArrivee+tempsRemplissage, robot, "liberer"));
	}
	
	/**
	 * Recherche de la plus proche case pour recharger de l'eau
	 * @param robot : le robot a recharger
	 * @param carte : la carte
	 * @return la plus proche case a robot pour recharger de l'eau
	 */
	public Case plusProcheRecharge(Robot robot, Carte carte) {
		Graphe graphe = new Graphe(carte, robot);
		Case depart = robot.getPosition();
		graphe.calculePCCDepuisSource(depart);
		Case destination;
		if (robot.getNature().equals("DRONE")) {
			destination = graphe.plusProcheCase(carte.getCasesEau());
		}
		else {
			destination = graphe.plusProcheCase(carte.getCasesBerge());
		}
		return destination;
	}
	
	/**
	 * Cette methode consiste a chercher l'incednie le plus proche non affecte a aucun robot
	 * @param robot : le robot a qui on cherche un plus proche incendie non affecte
	 * @param donnees : les donnees de la simulation
	 * @return le plus proche incedie non affecte
	 */
	public Incendie plusProcheIncendieNonAffecte(Robot robot, DonneesSimulation donnees) {
		Carte carte = donnees.getCarte();
		Graphe graphe = new Graphe(carte, robot);
		Case depart = robot.getPosition();
		graphe.calculePCCDepuisSource(depart);
		List<Case> CasesIncendiesNonAffectes = new ArrayList<Case>();
		for(Incendie incendie:donnees.getIncendiesNonAffectes()) {
			CasesIncendiesNonAffectes.add(incendie.getPosition());
		}
		Case destination = graphe.plusProcheCase(CasesIncendiesNonAffectes);
		if (destination != null) {
			Incendie incendieCible = incendieDansCase(destination, donnees);
			return incendieCible;
		}
		return null;
	}
	
	/**
	 * Cherche le plus proche incendie que ça soit affecte ou non 
	 * @param robot : le robot 
	 * @param donnees : les donnees de la simulation
	 * @return le plus proche incendie que ça soit affecte ou non
	 */
	public Incendie plusProcheIncendie(Robot robot, DonneesSimulation donnees) {
		Carte carte = donnees.getCarte();
		Graphe graphe = new Graphe(carte, robot);
		Case depart = robot.getPosition();
		graphe.calculePCCDepuisSource(depart);
		List<Case> CasesIncendiesNonAffectes = new ArrayList<Case>();
		for(Incendie incendie:donnees.getIncendiesNonEteints()) {
			CasesIncendiesNonAffectes.add(incendie.getPosition());
		}
		Case destination = graphe.plusProcheCase(CasesIncendiesNonAffectes);
		if (destination != null) {
			Incendie incendieCible = incendieDansCase(destination, donnees);
			return incendieCible;
		}
		return null;
	}
	
	/**
	 * Cherche l'incendie contenu dans maCase
	 * @param maCase : La case dont on cherche s'il y a un incendie dedans 
	 * @param donnees : les donnees de la simulation
	 * @return l'incendie existant dans maCase, retourne null si l'incendie n'existe pas
	 */
	public Incendie incendieDansCase(Case maCase, DonneesSimulation donnees) {
		for(Incendie incendie : donnees.getListeIncendies()) {
			if (incendie.getPosition().equals(maCase)) {
				return incendie;
			}
		}
		return null;
	}
	
	/**
	 * On ajoute des nouveaux evenements a la liste des evenements
	 * @param events : les evenements a ajouter
	 * @param simulateur : le simulateur
	 */
	private void ajoutEvents(List<Evenement> events, Simulateur simulateur) {
		for (Evenement event:events) {
			simulateur.ajouteEvenement(event);
		}
		
	}
	
	/**
	 * Retourne le simulateur
	 * @return le simulateur
	 */
	public Simulateur getSimulateur() {
		return simulateur;
	}
	
	/**
	 * Set le simulateur
	 * @param simulateur : le simulateur
	 */
	public void setSimulateur(Simulateur simulateur) {
		this.simulateur = simulateur;
	}
	
	/**
	 * Methode qui consiste a executer notre strategie
	 */
	abstract void executionStrategie();

}
