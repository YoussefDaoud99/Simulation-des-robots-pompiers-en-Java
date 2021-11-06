/**
 * Cette classe consiste a manipuler les robots de notre simulation
 * @author Equipe
 * @version 1
 */

public abstract class Robot{
	protected int reservoir;
	private Case position;
	protected Carte carte;
	private Simulateur simulateur;
	private Strategie strategie;
	private double vitesse;
	private boolean occupe;

	/**
	  * Initialise un robot avec la valeur de son reservoir, la position, la vitesse et la carte
	  * @param reservoir : Un entier indiquant le nombre de litres d'eau contenu dans son reservoir
	  * @param position : La position du robot
	  * @param vitesse : La vitesse du robot
	  * @param carte : La carte a laquelle appartient le robot 
	  */
	public Robot(int reservoir, Case position, double vitesse, Carte carte) {
		setReservoir(reservoir);
		setPosition(position);
		this.carte = carte;
		setVitesse(vitesse);
		setOccupe(false);
	}
	
	/**
	   * Initialise un robot avec la valeur de son reservoir, la position et la vitesse
	   * @param reservoir : Un entier indiquant le nombre de litres d'eau contenu dans son reservoir
	   * @param position : La position du robot
	   * @param vitesse : La vitesse du robot
	*/
	public Robot(int reservoir, Case position, double vitesse) {
		setReservoir(reservoir);
		setPosition(position);
		setVitesse(vitesse);
		setOccupe(false);
	}

	/**
	 * Set la position 
	 * @param position : La position du robot
	 */
	public void setPosition(Case position){
		this.position = position;
	}

	/**
     * Retourne La position
     * @return la position du robot
     */
	public Case getPosition(){
		return this.position;
	}
	
	/**
     * Cette methode consiste a changer le nombre de litres d'eau contenu dans le reservoir
     * apres avoir deverser vol d'eau sur l incendie 
     * @param vol : le volume d'eau a deverser
     */
	public void deverserEau(int vol){
		this.setReservoir(this.getReservoir() - vol);
	}
	
	/**
     * cette methode consiste a remplir le resrvoir du robot
     * cette methode est abstract car le volume du rervoir change selon la nature du robot
     */
	abstract void remplirReservoir();
	//getStrategie().interventionPlusProcheIncendie(this);

	/**
     * retourne la vitesse
     * @return la vitesse du robot
     */
	public double getVitesse(){
		return this.vitesse;
	}

	/**
	 * retourne le nombre de litres d eau existant dans le reservoir
	 * @return le nombre de litres d eau existant dans le reservoir du robot
	 */
	public int getReservoir() {
		return reservoir;
	}

	/**
	 * set un nombre de litres d'eau
	 * @param reservoir : Le nombre de litres d'eau qui va contenir le reservoir 
	 */
	public void setReservoir(int reservoir) {
		this.reservoir = reservoir;
		
	}
	

	public void setReservoirAvance(int reservoir) {
		this.reservoir = reservoir;
		if (getReservoir() <= 0 && getStrategie()!=null && !getNature().equals("PATTES")) {
			getStrategie().rechargerRobot(this, getSimulateur());
		}
	}


	protected abstract boolean reservoirPlein();

	/**
	 * Set la vitesse
	 * @param vitesse : la vitesse du robot
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	/**
	 * Set la carte
	 * @param carte : la carte a laquelle appartient le robot
	 */
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
	/**
	 * retourne la carte
	 * @return la carte a laquelle appartient le robot
	 */
	public Carte getCarte() {
		return carte;
	}
	
	
	/**
	 * cette methode consiste a deplacer le robot a une des cases voisines selon la direction donnee
	 * @param dir : la direction du deplacement du robot
	 */
	public void deplace(Direction dir) {
		Case ProchainePosition = getCarte().getVoisin(getPosition(), dir);
		if (ProchainePosition != getPosition() && ProchainePosition!=null) {
			this.position = ProchainePosition;
		}
		else {
			System.out.println("erreur deplacement invalide");
		}
	}
	
	/**
	 * Cette methode consiste a calculer le temps necessaire pour se rendre a une case voisine donnee
	 * @param CaseVoisine : case d'arrivee (case voisine)
	 * @return : le temps necessaire pour se rendre a CaseVoisine
	 */
	public abstract double deplaceCaseVoisine(Case CaseVoisine);
	
	/**
	 * Cette methode consiste a calculer le temps necessaire pour se rendre a une case voisine donnee a partir d'une case actuelle donnee
	 * @param caseActu : La case de depart
	 * @param CaseVoisine : La case d'arrivee
	 * @return le temps necessaire pour se deplacer de la case de depart a la case d'arrivee
	 */
	public abstract double coutDeplacementElem(Case caseActu, Case CaseVoisine);


	public abstract double[] interventionUnitaire();
	
	public abstract double dureeRemplissageComplet();
	
	public abstract boolean deplacementPossible(Case prochaineCase);
	
	/**
	 * Retourne un boolean si le robot est occupe ou non
	 * @return true si le robot est occupe, false sinon
	 */
	public boolean isOccupe() {
		return occupe;
	}

	/**
	 * Set un boolean qui dit si le robot est occupe ou non
	 * @param occupe : true si le robot est occupe, false sinon
	 */
	public void setOccupe(boolean occupe) {
		this.occupe = occupe;
	}
	
	/**
	 * retourne la nature du robot
	 * @return la nature du robot; drone, chenille, roues ou pattes
	 */
	public abstract String getNature();
	
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
	 * Retourne la strategie
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
	

}