/**
 * Cette classe consiste de manipuler toutes les caracteristiques du robot a roues
 * C'est une classe fille de la classe Robot
 * @author Equipe
 * @version 1
 */

public class RobotARoues extends Robot{
	int reservoirMax = 5000;
	int vitesseParDefaut = 80;
	
	/**
	 * Initialise un robot a pattes avec sa position et sa vitesse
	 * @param position : la position du robot
	 * @param vitesse : La vitesse du robot
	 */
	public RobotARoues(Case position, double vitesse) {
		super(5000, position, vitesse);
	}
	
	/**
	 * Initialise un robot a pattes avec sa position
	 * dans ce cas la vitesse est connue (80km/h)
	 * @param position : la position du robot
	 */
	public RobotARoues(Case position) {
		super(5000, position, 80);
	}
	
	
	@Override
	void remplirReservoir() {
		setReservoir(5000);
		if (getStrategie()!= null) {
			getStrategie().interventionPlusProcheIncendieNonAffecte(this);
		}
	}

	@Override
	public double deplaceCaseVoisine(Case caseVoisine) {
		// Une méthode qui retourne le temps nécessaire pour se rendre au case voisine
		return (this.carte.getdimCases())/this.getVitesse();
	}

	@Override
	public double coutDeplacementElem(Case caseActu, Case CaseVoisine) {
		return (this.carte.getdimCases())/this.getVitesse();
	}

	@Override
	public String getNature() {
		return "ROUES";
	}
	
	@Override
	public double[] interventionUnitaire() {
		double[] vitesseIntervention = {100, 5};
		return vitesseIntervention;
	}

	@Override
	public double dureeRemplissageComplet() {
		return 600;
	}

	@Override
	public boolean deplacementPossible(Case prochaineCase) {
		if (prochaineCase.getNatureTerrain() == NatureTerrain.TERRAIN_LIBRE || prochaineCase.getNatureTerrain() == NatureTerrain.HABITAT) {
			return true;
		}
		return false;
	}
	
	@Override
	protected boolean reservoirPlein() {
		return getReservoir() == reservoirMax;
	}
}
