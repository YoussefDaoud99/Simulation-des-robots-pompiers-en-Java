import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Une classe modelisant un graphe
 * @author Equipe
 * @version 1
 */
public class Graphe {
	
    private List<Sommet> sommets = new ArrayList<Sommet>();
    private int[] dimCarte;
    
    /**
     * Initialise un graphe par ses sommets
     * @param sommets : Les sommets du graphe
     */
    public Graphe(List<Sommet> sommets) {
    	setSommets(sommets);
    }
    
    /**
     * Initialise un graphe a partir de la carte et le robot
     * les cases sont les sommets et le cout sur les aretes 
     * est egale au cout de deplacement de robot d'une case a l'autre
     * @param carte : La carte a modeliser
     * @param robot : Le robot 
     */
    public Graphe(Carte carte, Robot robot) {
    	setDimCarte(carte);
    	setSommets(grapheDepuisCarte(carte, robot));
    }

    /**
     * Cette methode consiste a chercher le plus court chemin de la case de depart a la case de destination
     * @param maSource : La case de depart
     * @param maDestination : La case de destination
     * @return Un tableau unidimensionnel contenant les cases a traverser pour aller de "maSource" a "maDestination" avec un cout minimum 
     */
	public Case[] pcc(Case maSource, Case maDestination) {
    	Sommet destination = caseEnSommet(maDestination);
    	Sommet source = caseEnSommet(maSource);
    	calculePCCDepuisSource(source);
    	List<Sommet> listeChemin = destination.getPccs();
    	int n = listeChemin.size();
    	Case[] cheminOpti = new Case[n+1];
    	for (int i =0; i < n; i++) {
    		cheminOpti[i] = listeChemin.get(i).getMaCase();
    	}
    	cheminOpti[n] = maDestination;
    	return cheminOpti;
    }
    
	/**
	 * Cette methode consiste a appliquer l'algo de Djikstra sur le graphe
	 * @param source : Sommet de depart
	 */
    public  void  calculePCCDepuisSource(Sommet source) {
        source.setDistance(0.0);

        List<Sommet> settledSommets = new ArrayList<>();
        List<Sommet> unsettledSommets = new ArrayList<>();

        unsettledSommets.add(source);

        while (unsettledSommets.size() != 0) {
            Sommet currentSommet = getLowestDistanceSommet(unsettledSommets);
            unsettledSommets.remove(currentSommet);

            
            for (Map.Entry < Sommet, Double> adjacencyPair:currentSommet.getSommetsAdjacents().entrySet()) {
                Sommet adjacentSommet = adjacencyPair.getKey();
                Double edgeWeight = adjacencyPair.getValue();
                if (!settledSommets.contains(adjacentSommet)) {
                    calculateMinimumDistance(adjacentSommet, edgeWeight, currentSommet);
                    unsettledSommets.add(adjacentSommet);
                }
            }
            settledSommets.add(currentSommet);
        }
    }
    
    /**
     * Cette methode consiste a appliquer l'algo de Djikstra sur le graphe
     * @param caseSource : Case de depart
     */
    public  void  calculePCCDepuisSource(Case caseSource) {
    	Sommet source = caseEnSommet(caseSource);
        this.calculePCCDepuisSource(source);
    }
    

    /**
     * On cherche le plus proche sommet de la source
     * @param unsettledSommets :  UnsettledSommets de l'algo de Djikstra
     * @return le plus proche sommet de la source
     */
    private static Sommet getLowestDistanceSommet(List < Sommet > unsettledSommets) {
        Sommet lowestDistanceSommet = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Sommet Sommet: unsettledSommets) {
            double SommetDistance = Sommet.getDistance();
            if (SommetDistance < lowestDistance) {
                lowestDistance = SommetDistance;
                lowestDistanceSommet = Sommet;
            }
        }
        return lowestDistanceSommet;
    }
    
    /**
     * On compare la distance actuelle de 'evaluationSommet' avec 'sourceDistance + edgeWeigh'
     * Si la distance actuelle ast plus grande que 'sourceDistance + edgeWeigh', on ajoute
     * On ajoute sourceSommet au chemin optimal de evaluationSommet
     * @param evaluationSommet : Le sommet a evaluer
     * @param edgeWeigh 
     * @param sourceSommet : le sommet de source 
     */
    private static void calculateMinimumDistance(Sommet evaluationSommet, Double edgeWeigh, Sommet sourceSommet) {
	    Double sourceDistance = sourceSommet.getDistance();
	    if (sourceDistance + edgeWeigh < evaluationSommet.getDistance()) {
	        evaluationSommet.setDistance(sourceDistance + edgeWeigh);
	        LinkedList<Sommet> pcc = new LinkedList<>(sourceSommet.getPccs());
	        pcc.add(sourceSommet);
	        evaluationSommet.setPccs(pcc);
	    }
	}
    
    /**
     * Constitue un graphe depuis la carte
     * @param carte : La carte
     * @param robot : Le robot
     * @return la liste des sommets constituant le graphe
     */
    public  List<Sommet> grapheDepuisCarte(Carte carte, Robot robot) {
		List<Sommet> sommets = new ArrayList<Sommet>();
		for (Case[] ligneActu:carte.getCases()) {
			for (Case caseActu:ligneActu) {
				Sommet nouveauSommet = new Sommet(caseActu);
				sommets.add(nouveauSommet);
			}
		}
		for (Sommet sommetActu:sommets) {
			Case caseActu = sommetActu.getMaCase();
			Case voisinDroite = carte.getVoisin(caseActu, Direction.EST);
			Case voisinDessous = carte.getVoisin(caseActu, Direction.SUD);
			Case voisinGauche = carte.getVoisin(caseActu, Direction.OUEST);
			Case voisinDessus = carte.getVoisin(caseActu, Direction.NORD);
			if (voisinDroite != null && robot.deplacementPossible(voisinDroite)) {
				double coutDeplacement = robot.coutDeplacementElem(caseActu, voisinDroite);
				Sommet sommetDestination = caseEnSommet(voisinDroite, sommets);
				sommetActu.ajouterDestination(sommetDestination, coutDeplacement);
			};
			if (voisinDessous != null && robot.deplacementPossible(voisinDessous)) {
				double coutDeplacement = robot.coutDeplacementElem(caseActu, voisinDessous);
				Sommet sommetDestination = caseEnSommet(voisinDessous, sommets);
				sommetActu.ajouterDestination(sommetDestination, coutDeplacement);
			};
			if (voisinDessus != null && robot.deplacementPossible(voisinDessus)) {
				double coutDeplacement = robot.coutDeplacementElem(caseActu, voisinDessus);
				Sommet sommetDestination = caseEnSommet(voisinDessus, sommets);
				sommetActu.ajouterDestination(sommetDestination, coutDeplacement);
			};
			if (voisinGauche != null && robot.deplacementPossible(voisinGauche)) {
				double coutDeplacement = robot.coutDeplacementElem(caseActu, voisinGauche);
				Sommet sommetDestination = caseEnSommet(voisinGauche, sommets);
				sommetActu.ajouterDestination(sommetDestination, coutDeplacement);
			};
		}
		
    	return sommets;
    }
    
    /**
     * Cherche le sommet qui represente maCase
     * @param maCase
     * @param sommets2
     * @return le sommet qui represente maCase
     */
    private Sommet caseEnSommet(Case maCase, List<Sommet> sommets2) {
    	int n= maCase.getCordonnees()[0];
    	int m= maCase.getCordonnees()[1];
    	int[] dims = getDimsCarte();
    	Sommet monSommet = sommets2.get(n*dims[1] + m);
    	return monSommet;
	}
    
    /**
     * On constitue le graphe a partir de la carte sans savoir les couts
     * @param carte la carte
     * @return la liste des sommets representant le graphe
     */
	public List<Sommet> grapheDepuisCarte(Carte carte) {
		List<Sommet> sommets = new ArrayList<Sommet>();
		for (Case[] ligneActu:carte.getCases()) {
			for (Case caseActu : ligneActu) {
				sommets.add(new Sommet(caseActu));
			}
		}
		int indiceSommetActu = 0;
		for (Sommet sommetActu:sommets) {
			Case caseActu = sommetActu.getMaCase();
			Case voisinDroite = carte.getVoisin(caseActu, Direction.EST);
			Case voisinDessous = carte.getVoisin(caseActu, Direction.SUD);
			if (voisinDroite != null) {
				Sommet sommetDestination = sommets.get(indiceSommetActu);
				sommetActu.ajouterDestination(sommetDestination);
			}
			if (voisinDessous != null) {
				Sommet sommetDestination = sommets.get(indiceSommetActu);
				sommetActu.ajouterDestination(sommetDestination);
			}
			indiceSommetActu += 1; 
		}
		setSommets(sommets);
    	return sommets;
    }
    
	/**
     * Cherche le sommet qui represente maCase
     * @param maCase : ma case
     * @return le sommet qui represente maCase
     */
    public Sommet caseEnSommet(Case maCase) {
    	int n= maCase.getCordonnees()[0];
    	int m= maCase.getCordonnees()[1];
    	int[] dims = getDimsCarte();
    	Sommet monSommet = getSommets().get(n*dims[1] + m);
    	return monSommet;
    }
    
    /**
     * applique la methode precedente sur chaque case
     * @param  cases : un tableau unidimensionnel de cases
     * @return la liste des sommets representant les cases
     */
    public List<Sommet> casesEnSommets(Case[] cases) {
    	List<Sommet> sommets = new ArrayList<Sommet>();
    	for (Case maCase:cases) {
    		sommets.add(caseEnSommet(maCase));
    	}
    	return sommets;
    }
    
    /**
     * applique la methode avant precedente sur chaque case
     * @param cases : une liste de cases
     * @return la liste des sommets representant les cases
     */
    public List<Sommet> casesEnSommets(List<Case> cases) {
    	List<Sommet> sommets = new ArrayList<Sommet>();
    	for (Case maCase:cases) {
    		sommets.add(caseEnSommet(maCase));
    	}
    	return sommets;
    }
    
    /**
     * Cherche le plus proche sommet du sommet sources 
     * @param sommets la liste de sommets dont on cherche le plus proche a la source
     * @return le plus proche sommet du sommet sources 
     */
    public Sommet plusProcheSommet(List<Sommet> sommets) {
    	Double distanceMin = Double.MAX_VALUE;
    	if (sommets.size() == 0) {
    		return null;
    	}
    	Sommet sommetPlusProche = sommets.get(0);
    	for (Sommet sommetActuel:sommets) {
    		if (sommetActuel.getDistance() < distanceMin) {
    			distanceMin = sommetActuel.getDistance();
    			sommetPlusProche = sommetActuel;
    		}
    	}
    	return sommetPlusProche;
    }
    
    /**
     * Cherche la plus proche case de la case represente par le sommet source  
     * @param cases la liste des cases dont on cherche la plus proche
     * @return la plus proche case de la case represente par le sommet source
     */
	public Case plusProcheCase(List<Case> cases) {
		List<Sommet> sommets = casesEnSommets(cases);
		Sommet plusProcheSommet = plusProcheSommet(sommets);
		if (plusProcheSommet != null) {
			return plusProcheSommet.getMaCase();
		}
		return null;
	}
    
    /**
     * Set dimension de la carte
     * @param carte
     */
    private void setDimCarte(Carte carte) {
    	int[] dims = {carte.getNbLignes(), carte.getNbColonnes()};
		this.dimCarte = dims;
	}
    
    /**
     * Ajoute un sommet a la liste des sommets
     * @param sommet le sommet a ajouter
     */
    public void ajouterSommet(Sommet sommet) {
        sommets.add(sommet);
    }
    
    /**
     * Retourne la dimension de la carte
     * @return la dimension de la carte
     */
    private int[] getDimsCarte() {
		return this.dimCarte;
	}

    /**
     * Retourne la liste des sommets
     * @return la liste des sommets
     */
	public List<Sommet> getSommets(){
    	return this.sommets;
    }
    
	/**
	 * Set la liste des sommets
	 * @param sommets notre liste de sommets
	 */
    public void setSommets(List<Sommet> sommets) {
    	this.sommets = sommets;
    }
    
    @Override
    public String toString() {
    	return "graphe :"+ getSommets().size();
    }

}
