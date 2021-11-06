/**
 * Cette classe consiste de manipuler toutes les caracteristiques du robot a pattes
 * C'est une classe fille de la classe Robot
 * @author Equipe
 * @version 1
 */
public class RobotAPattes extends Robot{

	/**
	 * Initialise un robot a pattes avec sa position et sa vitesse qui est connue
	 * @param position : la position du robot
	 */
	public RobotAPattes(Case position) {
		super(-1, position, 0);
		setPosition(position);
		gererVitesse();
	}
	
	/**
	 * Cette methode consiste a gerer la vitesse du robot a pattes
	 * La vitesse est a 10km/h sur le ROCHE et 30km/h sur les autres terrains
	 */
	public void gererVitesse() {
		if (this.getPosition().getNatureTerrain() != NatureTerrain.ROCHE) {
			super.setVitesse(30);
		}
		else{
			super.setVitesse(10);
			}
		}
	
	@Override
	void remplirReservoir() {
	}

	@Override
	public double deplaceCaseVoisine(Case caseVoisine) {
		// Une méthode qui retourne le temps nécessaire pour se rendre au case voisine
		if (caseVoisine.getNatureTerrain() == this.getPosition().getNatureTerrain()) {
			return (this.carte.getdimCases())/this.getVitesse();
		}
		
		else {
			if ((this.getPosition().getNatureTerrain() == NatureTerrain.ROCHE) || (caseVoisine.getNatureTerrain() == NatureTerrain.ROCHE)){
			return (2*this.carte.getdimCases()/(10 + 30));
			}
			else {
				return (this.carte.getdimCases())/this.getVitesse();
			}
		}
	}

	@Override
	public double coutDeplacementElem(Case caseActu, Case CaseVoisine) {
		if (CaseVoisine.getNatureTerrain() == caseActu.getNatureTerrain()) {
			return (this.carte.getdimCases())/this.getVitesse();
		}
		
		else {
			if ((caseActu.getNatureTerrain() == NatureTerrain.ROCHE) || (CaseVoisine.getNatureTerrain() == NatureTerrain.ROCHE)){
				return (2*this.carte.getdimCases()/(10 + 30));
			}
			else {
				return (this.carte.getdimCases())/this.getVitesse();
			}
		}

	}

	@Override
	public String getNature() {
		return "PATTES";
	}
	
	@Override
	public double[] interventionUnitaire() {
		double[] vitesseIntervention = {10, 1};
		return vitesseIntervention;
	}

	@Override
	public double dureeRemplissageComplet() {
		return 0;
	}
	
	@Override
	public boolean deplacementPossible(Case prochaineCase) {
		if (prochaineCase.getNatureTerrain() == NatureTerrain.EAU) {
			return false;
		}
		return true;
	}

	@Override
	protected boolean reservoirPlein() {
		return false;
	}
}

