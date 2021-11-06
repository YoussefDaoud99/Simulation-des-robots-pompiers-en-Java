import java.util.ArrayList;
import java.util.List;

/**
 * Cette Classe consiste a regrouper toutes les donnees de notre simulation
 * @author Equipe
 * @version 1
 */
public class DonneesSimulation {
	private Carte carte;
	private Incendie[] listeIncendies;
	private Robot[] listeRobot;
	
	/**
	 * Initialise DonneesSimulation avec la carte, la liste des incendies et la liste des robots
	 * @param carte : carte
	 * @param listeIncendies : liste des incendies
	 * @param listeRobot : liste des robots
	 */
	public DonneesSimulation(Carte carte, Incendie[] listeIncendies, Robot[] listeRobot) {
		setCarte(carte);
		setListeIncendies(listeIncendies);
		setListeRobot(listeRobot);
	}

	/**
	 * Retourne la liste des positions des incendies contenue dans la carte
	 * @return une liste array des cases contenat un incendie
	 */
	public List<Case> getCasesIncendies(){
		List<Case> CasesIncendie = new ArrayList<Case>();
		for (Incendie incendie : this.listeIncendies) {
			if (incendie.getNbLitres() > 0) {
			CasesIncendie.add(incendie.getPosition());
			}
		}
		return CasesIncendie;
	}
	
	
	/**
	 * Retourne une liste des incendies non affectes à aucun robot
	 * @return une liste array des incendies non affectes a aucun robot
	 */
	public List<Incendie> getIncendiesNonAffectes(){
		List<Incendie> IncendiesNonAffectes = new ArrayList<Incendie>();
		for (Incendie incendie : getListeIncendies()) {
			if (incendie.getNbAffectations() == 0 && incendie.getNbLitres() >0)
				IncendiesNonAffectes.add(incendie);
		}
		return IncendiesNonAffectes;
	}
	
	/**
	 * Retourne une liste des robots non occupes
	 * @return une liste array des robots non occupes
	 */
	public List<Robot> getRobotsNonOccupes() {
		List<Robot> RobotsNonOccupes = new ArrayList<Robot>();
		for (Robot robot : getListeRobot()) {
			if (!robot.isOccupe())
				RobotsNonOccupes.add(robot);
		}
		return RobotsNonOccupes;
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
	 * @param carte : Carte
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
	/**
	 * Retourne la liste des incendies
	 * @return la liste des incendies
	 */
	public Incendie[] getListeIncendies() {
		return listeIncendies;
	}
	
	public List<Incendie> getIncendiesNonEteints() {
		List<Incendie> incendiesNonEteints = new ArrayList<Incendie>();
		for (Incendie incendie:getListeIncendies()) {
			if (incendie.getNbLitres() > 0) {
				incendiesNonEteints.add(incendie);
			}
		}
		return incendiesNonEteints;
	}

	/**
	 * Set la liste des incendies
	 * @param listeIncendies : la listes des incendies
	 */
	public void setListeIncendies(Incendie[] listeIncendies) {
		this.listeIncendies = listeIncendies;
	}

	/**
	 * Retourne la liste des robots	
	 * @return La liste des robots
	 */
	public Robot[] getListeRobot() {
		return listeRobot;
	}
	
	/**
	 * Set la liste des robots
	 * @param listeRobot : Liste des robots de notre simulation
	 */
	public void setListeRobot(Robot[] listeRobot) {
		this.listeRobot = listeRobot;
	}

	/**
	 * Retourne une liste des robots dont le réservoir est vide
	 * @return une liste array des robots dont le réservoir est vide
	 */
	public List<Robot> getRobotsARecharger() {
		List<Robot> robotsARecharger = new ArrayList<Robot>();
		for (Robot robot:getListeRobot()) {
			if (robot.getReservoir() == 0) {
				robotsARecharger.add(robot);
			}
		}
		return robotsARecharger;
	}



}
