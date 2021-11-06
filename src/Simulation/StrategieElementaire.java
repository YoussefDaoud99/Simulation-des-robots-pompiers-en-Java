import java.util.List;

/**
 * Classe fille de la classe Strategie
 * Dans cette classe on manipule la strategie elementaire demandee dans le sujet
 * @author Equipe
 * @version 1
 */
public class StrategieElementaire extends Strategie{
	private DonneesSimulation donnees;
	private Simulateur simulateur;
	
	/**
	 * On initialise la strategie elementaire par les donnees de la simulation et le simulateur 
	 * @param donnees : Les donnees de la simulation; regroupant la carte, les cases, les robots et les incendies
	 * @param simulateur : Le simulateur
	 */
	public StrategieElementaire(DonneesSimulation donnees, Simulateur simulateur) {
		super(donnees, simulateur);
		executionStrategie();
	}
	
	/**
	 * C'est la methode qui se charge de l'execution de la strategie elementaire
	 */
	public void executionStrategie() {
		List<Robot> robotsARecharger = getDonnees().getRobotsARecharger();
		rechargerRobots(robotsARecharger, getSimulateur());
		List<Incendie> incendiesNonAffectes = getDonnees().getIncendiesNonAffectes();
		List<Robot> robotsNonOccupes = getDonnees().getRobotsNonOccupes();
		int nbAffectations = Math.min(incendiesNonAffectes.size(), robotsNonOccupes.size());
		for (int i = 0; i < nbAffectations; i++) {
			this.intervention(robotsNonOccupes.get(i), incendiesNonAffectes.get(i), getSimulateur());
		}
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
	
	
}
