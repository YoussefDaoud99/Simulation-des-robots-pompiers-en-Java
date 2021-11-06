/**
 * Cette classe consiste a gerer les incendies de notre simulation
 * @author Equipe 1
 * @version 1
 */
public class Incendie{
	private Case position;
	private int nbLitres;
	private int nbAffectations=0;
	
	/**
	 * Cree un nouveau incendie avec sa position et le nombre de litres necessaire pour l'eteindre
	 * @param position : la position de l'incendie
	 * @param NbLitres : le nombre de litrres necessaire pour eteindre l'incendie
	 */
	public Incendie(Case position, int NbLitres) {
		this.position = position;
		this.nbLitres = NbLitres;
	}
	
	/**
	 * Set le nombre de litres necessaire pour eteindre l'incendie
	 * @param nvNbLitres : le nombre de litres necessaire pour eteindre l'incendie
	 */
	public void setNbLitres(int nvNbLitres){
		this.nbLitres = nvNbLitres;
	}
	
	/**
	 * retourne le nombre de litres necessaire pour eteindre l'incendie
	 * @return le nombre de litres necessaire pour eteindre l'incendie
	 */
	public int getNbLitres(){
		return this.nbLitres;
	}
	
	/**
	 * retourne la position
	 * @return la position de l'incendie
	 */
	public Case getPosition() {
		return position;
	}

	/**
	 * Set la position 
	 * @param position : la postion de l'incendie
	 */
	public void setPosition(Case position) {
		this.position = position;
	}
	
	/**
	 * retourne le nombre d'affectation 
	 * @return le nombre de robots affecte a cet incendie pour l'eteindre
	 */
	public int getNbAffectations() {
		return nbAffectations;
	}
	
	/**
	 * cette methode consiste a incrementer nbAffectations par 1 si un nouveau robot est affecte a cet incendie pour l'eteindre
	 */
	public void incrementeAffectations() {
		this.nbAffectations += 1;
	}
	
	/**
	 * cette methode consiste a decrementer nbAffectations par 1 si un nouveau robot deja affecte a cet incendie a finit sa mission
	 */
	public void decrementeAffectations() {
		this.nbAffectations -= 1;
	}
}