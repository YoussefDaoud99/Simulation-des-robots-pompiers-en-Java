/**
 * Classe fille de la classe Strategie
 * Dans cette classe le Chef pompier adopte une strategie evoluee
 * @author Equipe
 * @version 1
 */
public class StrategieEvoluee extends Strategie{
	private DonneesSimulation donnees;
	private double pasSimulation = 60;
	
	/**
	 * On initialise la strategie evoluee par les donnees de la simulation et le simulateur 
	 * @param donnees : Les donnees de la simulation; regroupant la carte, les cases, les robots et les incendies
	 * @param simulateur : Le simulateur
	 */
	public StrategieEvoluee(DonneesSimulation donnees, Simulateur simulateur) {
		super(donnees, simulateur);
		for (Robot robot:donnees.getListeRobot()) {
			robot.setStrategie(this);
		}
		executionStrategie();
	}


	@Override
	void executionStrategie() {
		for (Robot robot:getDonnees().getListeRobot()) {
			interventionPlusProcheIncendieNonAffecte(robot);
		}
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

	@Override
	public void intervention(Robot robot, Incendie incendie, Simulateur simulateur) {
		incendie.incrementeAffectations();
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
		for (int i = 0; i < nbInterventionsUnitaires; i++) {
			getSimulateur().ajouteEvenement(new EvenementIncendie(dateActuelle, incendie, robot));
			dateActuelle += intervalleEntreInterventions; 
		}
		getSimulateur().ajouteEvenement(new EvenementRobot(dateActuelle, robot, "liberer"));
		getSimulateur().ajouteEvenement(new EvenementIncendie(dateActuelle, incendie, robot, "desaffecter"));
	}
	
	@Override
	public void rechargerRobot(Robot robot, Simulateur simulateur) {
		Carte carte = simulateur.getCarte();
		robot.setOccupe(true);
		Case plusProcheRecharge = plusProcheRecharge(robot, carte);
		double dateArrivee = deplacerRobot(plusProcheRecharge, robot, simulateur);
		double tempsRemplissage = robot.dureeRemplissageComplet()/pasSimulation;
		getSimulateur().ajouteEvenement(new EvenementRobot(dateArrivee+tempsRemplissage, robot, "remplir"));
		getSimulateur().ajouteEvenement(new EvenementRobot(dateArrivee+tempsRemplissage, robot, "liberer"));
	}
	
	@Override
	public DonneesSimulation getDonnees() {
		return donnees;
	}
	
	/**
	 * Set les donnees de la simulation
	 * @param donnees : les donnees de la simulaton
	 */
	public void setDonnees(DonneesSimulation donnees) {
		this.donnees = donnees;
	}

}
