import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Classe permettant de manipuler la carte
 * @author Equipe
 * @version 1
 */

public class Carte{
	
	private int dimCases;
	private int n,m;
	private Case[][] cases;
	
	/**
	 * Initialise le nombre de lignes,
	 * le nombre de colonnes
	 * et la dimension des cases contenus dans la carte
	 * @param nbLignes le nombre de lignes de la carte
	 * @param nbColonnes le nombre de colonne de la carte
	 * @param dimCases dimension des cases de la carte
	 */
	public Carte(int nbLignes,int nbColonnes,int dimCases){  // Creation de Carte vide
		this.dimCases = dimCases;
		this.n = nbLignes;
		this.m = nbColonnes;
	} 
	
	/**
	 * Deuxième constructeur qui initialise les cases aussi 
	 * @param nbLignes le nombre de lignes de la carte
	 * @param nbColonnes le nombre de colonne de la carte
	 * @param dimCases dimension des cases de la carte
	 * @param cases les cases de la carte
	 */
	public Carte(int nbLignes,int nbColonnes,int dimCases, Case[][] cases){  // Deuxieme constructeur, avec les cases
		this.dimCases = dimCases;
		this.n = nbLignes;
		this.m = nbColonnes;
		this.cases = cases;
	} 
		
	/**
	 * Retourne les cases qui contiennent de l'eau
	 * @return une liste array des cases qui contiennent de l'eau dans cette carte
	 */
	public List<Case> getCasesEau(){
		List<Case> CasesEau = new ArrayList<Case>();
		for (Case[] caseligne : this.cases) {
			for (Case casee : caseligne ) {
				if (casee.getNatureTerrain() == NatureTerrain.EAU) {
					CasesEau.add(casee);
				}
			}
		}
		return CasesEau;
	}
	
	
	public List<Case> getCasesBerge(){
		List<Case> casesBerge = new ArrayList<Case>();
		for (Case caseEau:getCasesEau()) {
			for(Case CaseVoisine:voisins(caseEau)) {
				casesBerge.add(CaseVoisine);
			}
		}
		return casesBerge;
	}
	/**
	 * Retourne le nombre de lignes
	 * @return le nombre de lignes de cette carte
	 */
	public int getNbLignes(){
		return this.n;
	}
	
	/**
	 * Retourne le nombre de colonnes
	 * @return le nombre de colonnes de cette carte
	 */
	public int getNbColonnes(){
		return this.m;
	}
	
	/**
	 * Retourne la dimension des cases
	 * @return la dimension des cases de cette carte
	 */
	public int getdimCases(){
		return this.dimCases;
	}
	
	/**
	 * Cette fonction vérifie si le voisin d'une case dans une direction donnée existe
	 * @param ma_case : La case dont on cherche si son voisin existe
	 * @param dir : La direction du voisin cherché par rapport à ma_case
	 * @return boolean si le voisin existe ou pas
	 */
	public boolean voisinExiste(Case ma_case,Direction dir){
		int [] cordonnees = ma_case.getCordonnees();
		boolean voisin_existe = false;
		switch(dir){
			case NORD:
				voisin_existe = (cordonnees[0] >= 1);
				break;
			case SUD:
				voisin_existe = (cordonnees[0] < this.n -1);
				break;
			case EST:
				voisin_existe = (cordonnees[1] < this.m -1);
				break;
			case OUEST:
				voisin_existe = (cordonnees[1] >= 1);
				break;
		}
		return voisin_existe;
	} 
	
	/**
	 * Cette fonction retourne le voisin de ma_case par rapport à la direction dir
	 * @param ma_case : La case dont on cherche le voisin
	 * @param dir : La direction du voisin cherché par rapport à ma_case
	 * @return le voisin de ma_case par rapport à dir
	 */
	public Case getVoisin(Case ma_case,Direction dir){
		int n = ma_case.getCordonnees()[0];
		int m = ma_case.getCordonnees()[1];
		Case voisin = null;
		if (voisinExiste(ma_case,dir)){
			switch(dir){
				case NORD:
					voisin = (this.cases[n-1][m]);
					break;
				case SUD:
					voisin = (this.cases[n+1][m]);
					break;
				case EST:
					voisin = (this.cases[n][m+1]);
					break;
				case OUEST:
					voisin = (this.cases[n][m-1]);
					break;
			}
		}
		return voisin;
	}
	
	/**
	 * Retourne les voisins de ma_case
	 * @param ma_case : La case dont on cherche les voisins
	 * @return Un tableau unidimensionnel des voisins autour de ma_case
	 */
	public List<Case> voisins(Case ma_case) {
		List<Case> mes_voisins = new ArrayList<Case>();
		for (Direction dir : Direction.values()){
			if(voisinExiste(ma_case,dir)){
				mes_voisins.add(getVoisin(ma_case,dir));
			}
		}
		return mes_voisins;
	}

	/**
	 * Cette fonction cherche la direction de la case d'arrivée par rapport à la case de départ
	 * Les deux cases sont 
	 * @param caseDepart : La case de départ
	 * @param caseArrivee : La case d'arrivée
	 * @return La driection de CaseArrivee par rapport à CaseDepart
	 */
	public Direction TrouveDirection(Case caseDepart, Case caseArrivee) {
		int [] Difference_Coordonnees = {caseArrivee.getLigne() - caseDepart.getLigne(), caseArrivee.getColonne() - caseDepart.getColonne()};
		if (Difference_Coordonnees[0] == 1 && Difference_Coordonnees[1] == 0) {
			return Direction.SUD;
		}
		else if (Difference_Coordonnees[0] == 0 && Difference_Coordonnees[1] == 1) {
			return Direction.EST;
		}
		else if (Difference_Coordonnees[0] == -1 && Difference_Coordonnees[1] == 0) {
			return Direction.NORD;
		}
		else if (Difference_Coordonnees[0] == 0 && Difference_Coordonnees[1] == -1) {
			return Direction.OUEST;
		}	
		else {
			return null;
		}
		
	}
	
	/**
	 * Retourne les cases
	 * @return les cases contenues dans la carte
	 */
	public Case[][] getCases() {
		return this.cases;
	}

	/**
	 * Retourne La case à la ligne n el la colonne m
	 * @param n : La ligne 
	 * @param m : La colonne
	 * @return La case qui existe dans la ligne n et la colonne m
	 */
	public Case getCase(int n, int m) {
		return getCases()[n][m];
	}


}

