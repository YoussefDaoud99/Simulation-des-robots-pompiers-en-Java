/**
 * Classe permettant de manipuler les cases
 * @author Equipe 
 * @version 1
 */
public class Case{
	
	private int ligne,colonne;
	private NatureTerrain nature;

	/**
	 * Initialise une case avec la valeur de la ligne, la colonne et la nature de la case
	 * @param x : Ligne
	 * @param y : Colonne 
	 * @param nature : Nature de la case
	 */
	public Case(int x,int y,NatureTerrain nature){
		this.ligne = x;
		this.colonne = y;
		this.nature = nature;
	}
	
	/**
	 * Initialise une case avec la valeur de la ligne et la colonne.
	 * @param x : Ligne
	 * @param y : Colonne
	 */
	public Case(int x,int y){
		this.ligne = x;
		this.colonne = y;
	}
	
	/**
	 * Retourne la ligne
	 * @return la ligne de cette case
	 */
	public int getLigne(){
		return this.ligne;
	}
	
	/**
	 * Retourne la colonne
	 * @return la colonne de cette case
	 */
	public int getColonne(){
		return this.colonne;
	}

	/**
	 * Retourne la nature
	 * @return La nature de cette case 
	 */
	public NatureTerrain getNatureTerrain(){
		return this.nature;
	}
	
	/**
	 * Retourne Les coordonnees 
	 * @return les coordonnees de cette case
	 */
	public int[] getCordonnees() {
		int[] coords = {getLigne(), getColonne()};
		return coords;
	}
	
	@Override
	public String toString() {
		return "[" + ligne + ", " + colonne + "]";  
	}
	
	/**
	 * Verifie si cette case a les memes coordonnees avec autreCase
	 * @param autreCase la cases a tester
	 * @return true si les deux cases ont les memes coordonnees, false sinon
	 */
	public boolean equals(Case autreCase) {
		return (getLigne()==autreCase.getLigne()) && (getColonne() == autreCase.getColonne());
		
	}
}
