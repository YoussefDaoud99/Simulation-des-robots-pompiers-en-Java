
/**
 * Une classe fille de la classe Robot
 * Classe permettant de manipuler les Robots drone
 * @author Equipe
 * @version 1
 */
public class Drone extends Robot{
	/**
	 * Cree une instance du robot Drone avec la position et sa vitesse
	 * @param position : Position du robot
	 * @param vitesse : Vitesse Du robot
	 */
	int reservoirMax = 10000;
	
	public Drone(Case position, double vitesse) {
		super(10000, position, Math.min(vitesse,  150));
	}
	
	/**
	 * Cree une instance du robot Drone avec sa position
	 * @param position : La position du robot
	 */
	public Drone(Case position) {
		super(10000, position, 100);
	}
	
	/**
	 * Verifie si la case ou se trouve le robot contient de l'eau
	 * @return true si la case ou se trouve le robot contient de l'eau, false sinon
	 */
	public boolean caseEau() {
		return (this.getPosition().getNatureTerrain() == NatureTerrain.EAU);
	}	
	
	/**
	 * Set 10000 litres d'eau dans le rervoir du robot
	 */
	public void remplirReservoir() {
		setReservoir(10000);
		if (getStrategie()!=null) {
			getStrategie().interventionPlusProcheIncendieNonAffecte(this);
		}
	}

	@Override
	public double deplaceCaseVoisine(Case caseVoisine) {
		// Une méthode qui retourne le temps nécessaire pour se rendre au case voisine
		return (this.carte.getdimCases())/this.getVitesse();
	}

	@Override
	public double coutDeplacementElem(Case caseActu, Case caseVoisine) {
		return (this.carte.getdimCases())/this.getVitesse();
	}

	@Override
	public String getNature() {
		String nature = new String("DRONE");
		return nature;
	}

	@Override
	public double[] interventionUnitaire() {
		double[] vitesseIntervention = {10000, 30};
		return vitesseIntervention;
	}

	@Override
	public double dureeRemplissageComplet() {
		return 1800;
	}

	@Override
	public boolean deplacementPossible(Case prochaineCase) {
		return true;
	}

	@Override
	protected boolean reservoirPlein() {
		return getReservoir() == reservoirMax;
	}
}
