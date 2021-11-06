/**
 * Cette classe consiste de manipuler toutes les caracteristiques du robot a chenilles
 * C'est une classe fille de la classe Robot
 * @author Equipe
 * @version 1
 */
public class RobotChenilles extends Robot{
	int reservoirMax = 2000;
	int vitesseParDefaut = 60;
	
	/**
	 * Initialise un robot a pattes avec sa position et sa vitesse
	 * @param position : la position du robot
	 * @param vitesse : La vitesse du robot (La vitesse ne doit pas depasser 80km/h, donc on prend le maximum de 80 et la vitesse donnee)
	 */
	public RobotChenilles(Case position, double vitesse) {
		super(2000, position, Math.min(vitesse,  80));
		
	}
	
	/**
	 * Initialise un robot a pattes avec sa position
	 * dans ce cas la vitesse est connue (60km/h)
	 * @param position : la position du robot
	 */
	public RobotChenilles(Case position) {
		super(2000, position, 60);
	}

	@Override
	void remplirReservoir() {
		setReservoir(reservoirMax);
		if (getStrategie()!=null) {
			getStrategie().interventionPlusProcheIncendieNonAffecte(this);
		}
	}
	
	/**
	 * Cette methide consiste a gerer la vitesse du robot a chenilles
	 * On divise la vitesse actuelle sur deux si on se trouve sur la foret
	 */
	public void gererVitesse() {
		if (getPosition().getNatureTerrain() == NatureTerrain.FORET) {
			setVitesse(getVitesse()/2);
		}
	}

	@Override
	public double deplaceCaseVoisine(Case caseVoisine) {
		if (this.getPosition().getNatureTerrain() == caseVoisine.getNatureTerrain()) {
			return (this.carte.getdimCases())/this.getVitesse();
		}
		else {
			if (this.getPosition().getNatureTerrain() == NatureTerrain.FORET){
			return (2*this.carte.getdimCases())/(this.getVitesse() + 2*this.getVitesse());
			}
			else if (caseVoisine.getNatureTerrain() == NatureTerrain.FORET){
				return (2*this.carte.getdimCases())/(this.getVitesse() + this.getVitesse()/2);
				}
			else {
				return (this.carte.getdimCases())/this.getVitesse();
			}
		}
	}

	@Override
	public double coutDeplacementElem(Case caseActu, Case caseVoisine) {
		if (caseActu.getNatureTerrain() == caseVoisine.getNatureTerrain()) {
			return (this.carte.getdimCases())/this.getVitesse();
		}
		else {
			if (caseActu.getNatureTerrain() == NatureTerrain.FORET){
			return (2*this.carte.getdimCases())/(this.getVitesse() + 2*this.getVitesse());
			}
			else if (caseVoisine.getNatureTerrain() == NatureTerrain.FORET){
				return (2*this.carte.getdimCases())/(this.getVitesse() + this.getVitesse()/2);
				}
			else {
				return (this.carte.getdimCases())/this.getVitesse();
			}
		}
		
	}

	@Override
	public String getNature() {
		return "CHENILLES";
	}
	
	@Override
	public double[] interventionUnitaire() {
		double[] vitesseIntervention = {100, 8};
		return vitesseIntervention;
	}

	@Override
	public double dureeRemplissageComplet() {
		return 300;
	}

	@Override
	public boolean deplacementPossible(Case prochaineCase) {
		if (prochaineCase.getNatureTerrain() == NatureTerrain.EAU || prochaineCase.getNatureTerrain() == NatureTerrain.ROCHE) {
			return false;
		}
		return true;
	}
	
	@Override
	protected boolean reservoirPlein() {
		return getReservoir() == reservoirMax;
	}
	
}
